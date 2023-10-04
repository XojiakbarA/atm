package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.atm.dto.request.BankRequest;
import uz.pdp.atm.dto.view.BankView;
import uz.pdp.atm.entity.Bank;

public interface BankService {
    Page<BankView> getAll(Pageable pageable);

    BankView getById(Long id);

    BankView create(BankRequest request);

    BankView update(BankRequest request, Long id);

    Bank findById(Long id);

    Bank save(Bank bank);

    void deleteById(Long id);
}
