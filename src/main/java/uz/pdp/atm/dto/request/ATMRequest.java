package uz.pdp.atm.dto.request;

import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.validator.IsValidEnum;

@Data
public class ATMRequest {
    @NotNull(message = "cardTypes must not be null", groups = OnCreate.class)
    @NotEmpty(message = "cardTypes must not be empty", groups = OnCreate.class)
    private Set<
        @NotNull(message = "cardType must not be null", groups = OnCreate.class)
        @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA")
        String> cardTypes;

    @NotNull(message = "maxWithdrawalAmount must not be null", groups = OnCreate.class)
    @Positive(message = "maxWithdrawalAmount must be a positive")
    private Double maxWithdrawalAmount;

    @NotNull(message = "warningAmount must not be null", groups = OnCreate.class)
    @Positive(message = "warningAmount must be a positive")
    private Double warningAmount;

    @NotNull(message = "commissionForWithdrawOwnCard must not be null", groups = OnCreate.class)
    @Positive(message = "commissionForWithdrawOwnCard must be a positive")
    private Double commissionForWithdrawOwnCard;

    @NotNull(message = "commissionForTopUpOwnCard must not be null", groups = OnCreate.class)
    @Positive(message = "commissionForTopUpOwnCard must be a positive")
    private Double commissionForTopUpOwnCard;

    @NotNull(message = "commissionForWithdrawOtherCard must not be null", groups = OnCreate.class)
    @Positive(message = "commissionForWithdrawOtherCard must be a positive")
    private Double commissionForWithdrawOtherCard;

    @NotNull(message = "commissionForTopUpOtherCard must not be null", groups = OnCreate.class)
    @Positive(message = "commissionForTopUpOtherCard must be a positive")
    private Double commissionForTopUpOtherCard;

    @Valid
    private AddressRequest address;
}
