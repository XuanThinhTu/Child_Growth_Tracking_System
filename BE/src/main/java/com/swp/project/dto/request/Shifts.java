package com.swp.project.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shifts {

    boolean morning = false;
    boolean afternoon = false;
    boolean evening = false;

}
