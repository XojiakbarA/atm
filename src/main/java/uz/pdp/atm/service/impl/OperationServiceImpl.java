package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.view.OperationView;
import uz.pdp.atm.entity.ATM;
import uz.pdp.atm.entity.Operation;
import uz.pdp.atm.entity.OperationBanknote;
import uz.pdp.atm.enums.OperationType;
import uz.pdp.atm.mapper.OperationMapper;
import uz.pdp.atm.repository.OperationRepository;
import uz.pdp.atm.service.BanknoteService;
import uz.pdp.atm.service.OperationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationServiceImpl implements OperationService {
    @Autowired
    private OperationRepository operationRepository;
    @Autowired
    private BanknoteService banknoteService;
    @Autowired
    private OperationMapper operationMapper;

    @Override
    public Page<OperationView> getAllByATMId(Long atmId, Pageable pageable) {
        return operationRepository.findAllByAtmId(atmId, pageable).map(operationMapper::mapToView);
    }

    @Override
    public Page<OperationView> getDailyAllByATMIdAndType(Long id, OperationType operationType, Pageable pageable) {
        return operationRepository.findAllByAtmIdAndTypeAndCreatedAtDate(id, operationType, LocalDate.now(), pageable).map(operationMapper::mapToView);
    }

    @Override
    public Operation create(OperationType operationType, ATM atm, Set<ATMBanknoteRequest> banknotes, String createdBy) {
        Operation operation = new Operation();

        operation.setType(operationType);
        operation.setAtm(atm);
        operation.setCreatedBy(createdBy);

        Set<OperationBanknote> operationBanknotes = banknotes.stream()
                .map(b -> {
                    OperationBanknote operationBanknote = new OperationBanknote();
                    operationBanknote.setBanknote(banknoteService.findById(b.getBanknoteId()));
                    operationBanknote.setCount(b.getCount());
                    operationBanknote.setOperation(operation);
                    return operationBanknote;})
                .collect(Collectors.toSet());

        operation.setBanknotes(operationBanknotes);

        return save(operation);
    }

    @Override
    public Operation save(Operation operation) {
        return operationRepository.save(operation);
    }
}
