package uz.pdp.atm.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class TopUpRequest {
    @NotEmpty(message = "banknotes must not be empty")
    @NotNull(message = "banknotes must not be null")
    Set<@Valid ATMBanknoteRequest> banknotes;
}
