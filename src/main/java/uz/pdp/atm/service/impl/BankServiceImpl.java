package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.atm.dto.request.BankRequest;
import uz.pdp.atm.dto.view.BankView;
import uz.pdp.atm.entity.Bank;
import uz.pdp.atm.exception.ExistsByNameException;
import uz.pdp.atm.exception.NotFoundByIdException;
import uz.pdp.atm.mapper.BankMapper;
import uz.pdp.atm.repository.BankRepository;
import uz.pdp.atm.service.BankService;

@Service
public class BankServiceImpl implements BankService {
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private BankMapper bankMapper;

    @Override
    public Page<BankView> getAll(Pageable pageable) {
        return bankRepository.findAll(pageable).map(bankMapper::mapToView);
    }

    @Override
    public BankView getById(Long id) {
        return bankMapper.mapToView(findById(id));
    }

    @Override
    public BankView create(BankRequest request) {
        if (bankRepository.existsByName(request.getName())) {
            throw new ExistsByNameException(Bank.class.getSimpleName(), request.getName());
        }
        Bank bank = new Bank();

        bankMapper.mapToEntity(bank, request);

        return bankMapper.mapToView(save(bank));
    }

    @Override
    public BankView update(BankRequest request, Long id) {
        if (bankRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new ExistsByNameException(Bank.class.getSimpleName(), request.getName());
        }
        Bank bank = findById(id);

        bankMapper.mapToEntity(bank, request);

        return bankMapper.mapToView(save(bank));
    }

    @Override
    public Bank findById(Long id) {
        return bankRepository.findById(id).orElseThrow(
                () -> new NotFoundByIdException(Bank.class.getSimpleName(), id)
        );
    }

    @Override
    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }

    @Override
    public void deleteById(Long id) {
        if (!bankRepository.existsById(id)) {
            throw new NotFoundByIdException(Bank.class.getSimpleName(), id);
        }
        bankRepository.deleteById(id);
    }
}
