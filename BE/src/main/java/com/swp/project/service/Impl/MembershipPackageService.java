package com.swp.project.service.Impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.swp.project.dto.request.MembershipPackageRequest;
import com.swp.project.dto.response.MembershipPackageResponse;
import com.swp.project.dto.response.MembershipSubscriptionResponse;
import com.swp.project.dto.response.PaymentDTO;
import com.swp.project.entity.MembershipPackage;
import com.swp.project.entity.MembershipSubscription;
import com.swp.project.entity.User;
import com.swp.project.enums.MembershipSubscriptionStatus;
import com.swp.project.enums.PaymentStatus;
import com.swp.project.exception.ResourceNotFoundException;
import com.swp.project.mapper.MembershipPackageMapper;
import com.swp.project.mapper.MembershipSubscriptionMapper;
import com.swp.project.repository.MembershipPackageRepository;
import com.swp.project.repository.MembershipSubscriptionRepository;
import com.swp.project.repository.PermissionRepository;
import com.swp.project.service.IMembershipPackageService;
import com.swp.project.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MembershipPackageService implements IMembershipPackageService {
    private final MembershipPackageRepository packageRepository;
    private final MembershipPackageMapper packageMapper;
    private final IUserService userService;
    private final String CURRENCY = "USD";
    private final String METHOD = "paypal";
    private final String INTENT = "sale";
    private final APIContext apiContext;

    @Value("${client.domain}")
    private String clientDomain;
    private final MembershipSubscriptionRepository membershipSubscriptionRepository;
    private final MembershipSubscriptionMapper membershipSubscriptionMapper;
    private final PermissionRepository permissionRepository;

    @Override
    public MembershipPackageResponse createMembershipPackage(MembershipPackageRequest request) {
        if(request.duration() > 365){
            throw new RuntimeException("Duration must be less than 365 days");
        }
        MembershipPackage membershipPackage = packageMapper.toMembershipPackage(request);
        var permissions = permissionRepository.findAllById(request.permissions());
        membershipPackage.setPermissions(new HashSet<>(permissions));
        membershipPackage = packageRepository.save(membershipPackage);
        return packageMapper.toDto(membershipPackage);
    }

    @Override
    public MembershipPackageResponse updateMembershipPackage(int id,MembershipPackageRequest request) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Package with id " + id + " not found"));
        membershipPackage.setName(request.name());
        membershipPackage.setDescription(request.description());
        membershipPackage.setPrice(request.price());
        membershipPackage.setDuration(request.duration());
        membershipPackage = packageRepository.save(membershipPackage);
        return packageMapper.toDto(membershipPackage);
    }

    @Override
    public void deleteMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setDeleted(true);
        packageRepository.save(membershipPackage);
    }

    @Override
    public MembershipPackageResponse getMembershipPackageById(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        return packageMapper.toDto(membershipPackage);
    }

    @Override
    public List<MembershipPackageResponse> getAllMembershipPackages() {
        return packageRepository.findAllByIsDeletedFalse()
                .stream()
                .map(packageMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void enableMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setEnable(true);
        packageRepository.save(membershipPackage);
    }

    @Override
    public void disableMembershipPackage(int id) {
        MembershipPackage membershipPackage = packageRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Membership Package id: " + id + " not found"));
        membershipPackage.setEnable(false);
        packageRepository.save(membershipPackage);
    }

    @Override
    public PaymentDTO createMembershipPayment(int membershipId) throws PayPalRESTException {
        Payment payment = processPayment(membershipId);
        PaymentDTO paymentDTO = new PaymentDTO();
        payment.getLinks().stream().filter(link -> link.getRel().equals("approval_url"))
                .findFirst()
                .ifPresent(link -> {
                    paymentDTO.setPaymentUrl(link.getHref());
                });
        return paymentDTO;
    }

    @Override
    public String executeMembershipPayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = executePayment(paymentId, payerId);
        if (payment.getState().equals("approved")) {
            User user = userService.getAuthenticatedUser();
            //disable older subscription
            if(membershipSubscriptionRepository.findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.AVAILABLE).isPresent()){
                MembershipSubscription olderSubscription = membershipSubscriptionRepository.findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.AVAILABLE).get();
                olderSubscription.setStatus(MembershipSubscriptionStatus.UNAVAILABLE);
                membershipSubscriptionRepository.save(olderSubscription);
            }
            //create new subscription
            MembershipSubscription membershipSubscription = membershipSubscriptionRepository
                    .findByUserIdAndPaymentStatusAndStatus(user.getId(), PaymentStatus.PENDING, MembershipSubscriptionStatus.UNAVAILABLE);
            membershipSubscription.setPaymentStatus(PaymentStatus.SUCCESS);
            membershipSubscription.setStartDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
            membershipSubscription.setEndDate(Date.valueOf(LocalDateTime.now().plusDays(membershipSubscription.getMembershipPackage().getDuration()).toLocalDate()));
            membershipSubscription.setStatus(MembershipSubscriptionStatus.AVAILABLE);
            membershipSubscriptionRepository.save(membershipSubscription);
            return "Payment success";
        }else {
            log.info("Payment is failed");
            User user = userService.getAuthenticatedUser();
            MembershipSubscription membershipSubscription = membershipSubscriptionRepository
                    .findByUserIdAndPaymentStatusAndStatus(user.getId(), PaymentStatus.PENDING, MembershipSubscriptionStatus.UNAVAILABLE);
            membershipSubscription.setPaymentStatus(PaymentStatus.FAILED);
            membershipSubscription.setStatus(MembershipSubscriptionStatus.UNAVAILABLE);
            membershipSubscriptionRepository.save(membershipSubscription);
            return "Payment failed";
        }
    }

    private Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }

    private Payment processPayment(int membershipId) throws PayPalRESTException {
        MembershipPackage membershipPackage = packageRepository.findById(membershipId)
                .orElseThrow(() -> new ResourceNotFoundException("Membership Package not found"));
        //membership subscription
        MembershipSubscription membershipSubscription = new MembershipSubscription();
        membershipSubscription.setStartDate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        membershipSubscription.setEndDate(Date.valueOf(LocalDateTime.now().plusDays(membershipPackage.getDuration()).toLocalDate()));
        membershipSubscription.setStatus(MembershipSubscriptionStatus.UNAVAILABLE);
        membershipSubscription.setCreatedAt(Date.valueOf(LocalDateTime.now().toLocalDate()));
        membershipSubscription.setPaymentStatus(PaymentStatus.PENDING);
        membershipSubscription.setMembershipPackage(membershipPackage);
        membershipSubscription.setUser(userService.getAuthenticatedUser());
        membershipSubscriptionRepository.save(membershipSubscription);
        //paypal payment
        Amount amount = new Amount();
        amount.setCurrency(CURRENCY);
        amount.setTotal(String.format(Locale.forLanguageTag(CURRENCY), "%.2f", membershipPackage.getPrice()));

        Transaction transaction = new Transaction();
        transaction.setDescription(membershipPackage.getName());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(METHOD);

        Payment payment = new Payment();
        payment.setIntent(INTENT);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(clientDomain+"paypal/cancel");
        redirectUrls.setReturnUrl(clientDomain+"paypal/success");

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public void cancelMembershipPayment(String token) {
        User user = userService.getAuthenticatedUser();
        MembershipSubscription membershipSubscription = membershipSubscriptionRepository
                .findByUserIdAndPaymentStatusAndStatus(user.getId(), PaymentStatus.PENDING, MembershipSubscriptionStatus.UNAVAILABLE);
        membershipSubscription.setPaymentStatus(PaymentStatus.FAILED);
        membershipSubscription.setStatus(MembershipSubscriptionStatus.UNAVAILABLE);
        membershipSubscriptionRepository.save(membershipSubscription);
    }

    @Override
    public MembershipSubscriptionResponse getUserMembership() {
        User user = userService.getAuthenticatedUser();
        MembershipSubscription membershipSubscription = membershipSubscriptionRepository
                .findByUserIdAndStatus(user.getId(), MembershipSubscriptionStatus.AVAILABLE)
                .get();
        return membershipSubscriptionMapper.toMembershipSubscriptionResponse(membershipSubscription);
    }

    @Scheduled(fixedRate = 10000*6) // 1 minute
    private void checkMembershipSubscription(){
        List<MembershipSubscription> membershipSubscriptions = membershipSubscriptionRepository.findAllByStatus(MembershipSubscriptionStatus.AVAILABLE);
        for (MembershipSubscription membershipSubscription : membershipSubscriptions){
            if(membershipSubscription.getEndDate().before(Date.valueOf(LocalDate.now()))){
                membershipSubscription.setStatus(MembershipSubscriptionStatus.EXPIRED);
                membershipSubscriptionRepository.save(membershipSubscription);
                log.info("Validated Membership Subscription");
            }
        }
    }
}
