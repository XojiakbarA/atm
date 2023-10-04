package uz.pdp.atm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uz.pdp.atm.dto.view.ATMBanknoteView;
import uz.pdp.atm.entity.ATMBanknote;

@Component
public class ATMBanknoteMapper {
    @Autowired
    private BanknoteMapper banknoteMapper;

    public ATMBanknoteView mapToView(ATMBanknote atmBanknote) {
        if (atmBanknote == null) return null;

        return ATMBanknoteView.builder()
                    .id(atmBanknote.getId())
                    .banknote(banknoteMapper.mapToView(atmBanknote.getBanknote()))
                    .count(atmBanknote.getCount())
                    .build();
    }
}
