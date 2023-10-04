package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankView {
    private Long id;
    private String name;
}
