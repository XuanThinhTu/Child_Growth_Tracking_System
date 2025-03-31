package com.swp.project.controller;

import com.swp.project.dto.response.ApiResponse;
import com.swp.project.dto.response.ChildrenDTO;
import com.swp.project.service.IChildrenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/children")
@RequiredArgsConstructor
public class ChildrenController {

    private final IChildrenService childrenService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ChildrenDTO> addChildren(
            @RequestParam("name") String name,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender){
        ChildrenDTO childrenDTO = childrenService.addChildren(name, birthDate, gender);
        return ApiResponse.<ChildrenDTO>builder()
                .message("Children added")
                .data(childrenDTO)
                .build();
    }

    @GetMapping("/info/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ChildrenDTO> getChildrenById(@PathVariable int id){
        ChildrenDTO childrenDTO = childrenService.getChildrenById(id);
        return ApiResponse.<ChildrenDTO>builder()
                .message("Children profile retrieved")
                .data(childrenDTO)
                .build();
    }

    @Operation(summary = "Get all children of the authenticated user")
    @GetMapping("/list")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<List<ChildrenDTO>> getChildrenByCurrentUser(){
        List<ChildrenDTO> childrenDTOList = childrenService.getChildrenByAuthenticatedUser();
        return ApiResponse.<List<ChildrenDTO>>builder()
                .message("Children list retrieved")
                .data(childrenDTOList)
                .build();
    }

    @PutMapping("/update/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<ChildrenDTO> updateChildren(
            @PathVariable int id,
            @RequestParam("name") String name,
            @RequestParam("birthDate") String birthDate,
            @RequestParam("gender") String gender){
        ChildrenDTO childrenDTO = childrenService.updateChildren(id, name, birthDate, gender);
        return ApiResponse.<ChildrenDTO>builder()
                .message("Children updated")
                .data(childrenDTO)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<Void> deleteChildren(@PathVariable int id){
        childrenService.deleteChildren(id);
        return ApiResponse.<Void>builder()
                .message("Children deleted")
                .build();
    }
}
