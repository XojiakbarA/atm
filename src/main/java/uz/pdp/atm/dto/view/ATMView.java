package uz.pdp.atm.dto.view;

import java.util.Currency;
import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Data;
import uz.pdp.atm.enums.CardType;

@Data
@Builder
public class ATMView {
    private Long id;
    private Set<CardType> cardTypes;
    private Long maxWithdrawalAmount;
    private Long warningAmount;
    private Double commissionForWithdrawOwnCard;
    private Double commissionForTopUpOwnCard;
    private Double commissionForWithdrawOtherCard;
    private Double commissionForTopUpOtherCard;
    private BankView bank;
    private AddressView address;
    private Boolean enabled;
    private Map<Currency, Long> totalMoney;
}
