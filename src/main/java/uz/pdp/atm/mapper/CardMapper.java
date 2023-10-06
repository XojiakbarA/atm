package uz.pdp.atm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.atm.dto.request.CardRequest;
import uz.pdp.atm.dto.view.CardView;
import uz.pdp.atm.entity.Card;
import uz.pdp.atm.enums.CardType;

@Component
public class CardMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BankMapper bankMapper;
    public CardView mapToView(Card card) {
        if (card == null) return null;

        return CardView.builder()
                .id(card.getId())
                .balance(card.getBalance())
                .bank(bankMapper.mapToView(card.getBank()))
                .number(card.getNumber())
                .cvv(card.getCvv())
                .firstName(card.getFirstName())
                .lastName(card.getLastName())
                .expirationDate(card.getExpirationDate())
                .cardType(card.getCardType())
                .enabled(card.isEnabled())
                .build();
    }

    public void mapToEntity(Card card, CardRequest request) {
        if (request.getNumber() != null) {
            card.setNumber(request.getNumber().toString());
        }
        if (request.getPassword() != null) {
            card.setPassword(passwordEncoder.encode(request.getPassword().toString()));
        }
        if (request.getCvv() != null) {
            card.setCvv(request.getCvv().toString());
        }
        if (request.getFirstName() != null && !request.getFirstName().isBlank()) {
            card.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null && !request.getLastName().isBlank()) {
            card.setLastName(request.getLastName());
        }
        if (request.getExpirationDate() != null) {
            card.setExpirationDate(request.getExpirationDate());
        }
        if (request.getCardType() != null && !request.getCardType().isBlank()) {
            card.setCardType(CardType.valueOf(request.getCardType().toUpperCase()));
        }
    }
}
