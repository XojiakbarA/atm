package uz.pdp.atm.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.validator.IsValidEnum;

import java.time.LocalDate;

@Data
public class CardRequest {
    @Positive(message = "balance must be a positive")
    private Double balance;

    @NotNull(message = "number must not be null", groups = OnCreate.class)
    @Min(value = 1000_0000_0000_0000L, message = "number must be 16 digits")
    @Max(value = 9999_9999_9999_9999L, message = "number must be 16 digits")
    private Long number;

    @NotNull(message = "cvv must not be null", groups = OnCreate.class)
    @Min(value = 100, message = "cvv must be 3 digits")
    @Max(value = 999, message = "cvv must be 3 digits")
    private Integer cvv;

    @NotNull(message = "firstName must not be null", groups = OnCreate.class)
    @NotBlank(message = "firstName must not be blank", groups = OnCreate.class)
    @Length(min = 3, max = 50, message = "firstName's length must be in 3 and 50")
    private String firstName;

    @NotNull(message = "lastName must not be null", groups = OnCreate.class)
    @NotBlank(message = "lastName must not be blank", groups = OnCreate.class)
    @Length(min = 3, max = 50, message = "lastName's length must be in 3 and 50")
    private String lastName;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    @Future(message = "expirationDate must be future")
    private LocalDate expirationDate;

    @NotNull(message = "password must not be null", groups = OnCreate.class)
    @Min(value = 1000, message = "password must be 4 digits")
    @Max(value = 9999, message = "password must be 4 digits")
    private Integer password;

    @NotNull(message = "cardType must not be null", groups = OnCreate.class)
    @NotBlank(message = "cardType must not be blank", groups = OnCreate.class)
    @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA")
    private String cardType;
}
