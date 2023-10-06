package uz.pdp.atm.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uz.pdp.atm.dto.view.OperationView;
import uz.pdp.atm.entity.Operation;

import java.util.stream.Collectors;

@Component
public class OperationMapper {
    @Autowired
    private OperationBanknoteMapper operationBanknoteMapper;

    public OperationView mapToView(Operation operation) {
        if (operation == null) return null;

        return OperationView.builder()
                .id(operation.getId())
                .type(operation.getType())
                .banknotes(operation.getBanknotes().stream().map(operationBanknoteMapper::mapToView).collect(Collectors.toSet()))
                .createdBy(operation.getCreatedBy())
                .createdAt(operation.getCreatedAt())
                .updatedAt(operation.getUpdatedAt())
                .build();
    }
}
