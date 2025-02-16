package com.swp.project.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String avatar;
    private boolean isActive;
    private boolean isBanned;
    private String role;
}
