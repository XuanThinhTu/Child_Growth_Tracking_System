package com.swp.project.dto.request;

import java.util.Set;

public record MembershipPackageRequest(
    String name,
    String description,
    double price,
    int duration,
    Set<String> permissions
){
}

