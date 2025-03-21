package com.swp.project.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserCreationRequest {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
}
