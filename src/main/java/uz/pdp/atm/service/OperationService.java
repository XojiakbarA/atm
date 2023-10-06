package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.view.OperationView;
import uz.pdp.atm.entity.ATM;
import uz.pdp.atm.entity.Operation;
import uz.pdp.atm.enums.OperationType;

import java.util.Set;

public interface OperationService {

    Page<OperationView> getAllByATMId(Long atmId, Pageable pageable);

    Page<OperationView> getDailyAllByATMIdAndType(Long id, OperationType operationType, Pageable pageable);

    Operation create(OperationType operationType, ATM atm, Set<ATMBanknoteRequest> banknotes, String createdBy);

    Operation save(Operation operation);
}
