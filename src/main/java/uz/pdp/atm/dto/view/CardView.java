package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;
import uz.pdp.atm.enums.CardType;

import java.time.LocalDate;

@Data
@Builder
public class CardView {
    private Long id;
    private Double balance;
    private String number;
    private BankView bank;
    private String cvv;
    private String firstName;
    private String lastName;
    private LocalDate expirationDate;
    private CardType cardType;
    private Boolean enabled;
}
