package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.validator.IsValidCurrency;

@Data
public class BanknoteRequest {
    @NotNull(message = "currency must not be null", groups = OnCreate.class)
    @NotBlank(message = "currency must not be empty", groups = OnCreate.class)
    @IsValidCurrency
    private String currency;

    @NotNull(message = "amount must not be null", groups = OnCreate.class)
    @Positive(message = "amount must be a positive")
    private Integer amount;
}
