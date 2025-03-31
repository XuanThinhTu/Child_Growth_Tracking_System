package com.swp.project.dto.response;

import java.util.Set;

public record MembershipPackageResponse(
    int id,
    String name,
    String description,
    double price,
    int duration,
    boolean isEnable,
    Set<String> permissions
){

}
