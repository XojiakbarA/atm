package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ATMBanknoteView {
    private Long id;
    private BanknoteView banknote;
    private Integer count;
}
