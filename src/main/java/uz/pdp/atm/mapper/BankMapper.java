package uz.pdp.atm.mapper;

import org.springframework.stereotype.Component;
import uz.pdp.atm.dto.request.BankRequest;
import uz.pdp.atm.dto.view.BankView;
import uz.pdp.atm.entity.Bank;

@Component
public class BankMapper {
    public BankView mapToView(Bank bank) {
        if (bank == null) return null;

        return BankView.builder()
                .id(bank.getId())
                .name(bank.getName())
                .build();
    }

    public void mapToEntity(Bank bank, BankRequest request) {
        if (request.getName() != null && !request.getName().isBlank()) {
            bank.setName(request.getName());
        }
    }
}
