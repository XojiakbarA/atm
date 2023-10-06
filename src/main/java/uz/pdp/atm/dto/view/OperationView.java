package uz.pdp.atm.dto.view;

import lombok.Builder;
import lombok.Data;
import uz.pdp.atm.enums.OperationType;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class OperationView {
    private Long id;
    private OperationType type;
    private Set<OperationBanknoteView> banknotes;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
