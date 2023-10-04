package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;

import java.util.Currency;

@Data
@Builder
public class BanknoteView {
    private Long id;
    private Currency currency;
    private Integer amount;
}
