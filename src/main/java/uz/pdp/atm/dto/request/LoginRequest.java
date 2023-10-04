package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "number must not be null")
    @NotBlank(message = "number must not be empty")
    private String number;

    @NotNull(message = "password must not be null")
    @NotBlank(message = "password must not be empty")
    private String password;
}
