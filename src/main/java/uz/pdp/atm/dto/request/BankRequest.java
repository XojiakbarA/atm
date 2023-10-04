package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class BankRequest {
    @NotNull(message = "name must not be null")
    @NotBlank(message = "name must not be blank")
    @Length(min = 3, max = 50, message = "name's length must be in 3 and 50")
    private String name;
}
