package com.openlab.tables.dto;

import jakarta.validation.constraints.Max;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TablesDTO {
    private Long id;

    @Max(2)
    private long numero;
    //private Long userId;
}
