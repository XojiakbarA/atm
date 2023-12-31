package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.atm.marker.OnCreate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ATMBanknoteRequest {
    @NotNull(message = "banknoteId must not be null", groups = OnCreate.class)
    @Positive(message = "banknoteId must be a positive")
    private Long banknoteId;

    @NotNull(message = "count must not be null", groups = OnCreate.class)
    @Positive(message = "count must be a positive")
    private Integer count;
}
