package com.enoca.cartApi.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {
    @NotBlank
    @Size(min = 3, message = "İsim en az 3 karakterli olmalıdır.")
    private String name;

    @NotBlank
    @Size(min = 3, message = "Soyisim en az 3 karakterli olmalıdır.")
    private String surname;

}
