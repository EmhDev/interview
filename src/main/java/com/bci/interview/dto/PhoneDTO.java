package com.bci.interview.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {

    @NotBlank(message = "El NUM de telefono no puede estar vacio")
    private String number;
    @NotBlank(message = "El COD de ciudad no puede estar vacio")
    private String citycode;
    @NotBlank(message = "El COD del pais no puede estar vacio")
    private String countrycode;
}
