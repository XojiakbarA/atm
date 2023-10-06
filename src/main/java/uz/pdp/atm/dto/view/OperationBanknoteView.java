package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OperationBanknoteView {
    private Long id;
    private BanknoteView banknote;
    private Integer count;
}
