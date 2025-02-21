package com.swp.project.dto.response;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChildrenDTO {
    private int id;
    private String name;
    private Date birthDate;
    private String gender;
    private int parentId;
    private String parentName;
}
