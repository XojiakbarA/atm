package uz.pdp.atm.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.atm.dto.request.BanknoteRequest;
import uz.pdp.atm.dto.view.BanknoteView;
import uz.pdp.atm.entity.Banknote;

import java.util.Currency;

@Component
public class BanknoteMapper {
    public BanknoteView mapToView(Banknote banknote) {
        if (banknote == null) return null;

        return BanknoteView.builder()
                .id(banknote.getId())
                .currency(banknote.getCurrency())
                .amount(banknote.getAmount())
                .build();
    }

    public void mapToEntity(Banknote banknote, BanknoteRequest request) {
        if (request.getCurrency() != null && !request.getCurrency().isBlank()) {
            banknote.setCurrency(Currency.getInstance(request.getCurrency()));
        }
        if (request.getAmount() != null) {
            banknote.setAmount(request.getAmount());
        }
    }
}
