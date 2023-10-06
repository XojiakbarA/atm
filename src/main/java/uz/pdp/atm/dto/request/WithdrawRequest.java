package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WithdrawRequest {
    @NotNull(message = "amount must not be null")
    @Positive(message = "amount must be a positive")
    private Long amount;
}
