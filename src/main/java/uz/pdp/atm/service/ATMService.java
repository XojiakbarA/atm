package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.entity.ATM;

public interface ATMService {
    Page<ATMView> getAll(Pageable pageable);

    ATMView getById(Long id);

    ATMView create(ATMRequest request);

    ATMView update(ATMRequest request, Long id);

    ATMView setBank(Long atmId, Long bankId);

    ATMView addCardType(Long id, String cardType);

    ATMView removeCardType(Long id, String cardType);

    ATMView addATMBanknote(ATMBanknoteRequest request, Long id);

    ATM findById(Long id);

    ATM save(ATM atm);

    void deleteById(Long id);

    Long withdraw(Long id, Long amount, String cardNumber);
}
