package uz.pdp.atm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.pdp.atm.dto.view.OperationBanknoteView;
import uz.pdp.atm.entity.OperationBanknote;

@Component
public class OperationBanknoteMapper {
    @Autowired
    private BanknoteMapper banknoteMapper;

    public OperationBanknoteView mapToView(OperationBanknote operationBanknote) {
        if (operationBanknote == null) return null;

        return OperationBanknoteView.builder()
                .id(operationBanknote.getId())
                .banknote(banknoteMapper.mapToView(operationBanknote.getBanknote()))
                .count(operationBanknote.getCount())
                .build();
    }
}
