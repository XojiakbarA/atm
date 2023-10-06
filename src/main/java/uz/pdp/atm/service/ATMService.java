package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.request.TopUpRequest;
import uz.pdp.atm.dto.request.WithdrawRequest;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.entity.ATM;

import java.util.Set;

public interface ATMService {
    Page<ATMView> getAll(Pageable pageable);

    ATMView getById(Long id);

    ATMView create(ATMRequest request);

    ATMView update(ATMRequest request, Long id);

    ATMView setBank(Long atmId, Long bankId);

    ATMView addCardType(Long id, String cardType);

    ATMView removeCardType(Long id, String cardType);

    ATMView addATMBanknotes(TopUpRequest request, Long id, String username);

    ATM findById(Long id);

    ATM save(ATM atm);

    void deleteById(Long id);

    Long withdraw(Long id, WithdrawRequest request, String cardNumber);

    Set<ATMBanknoteRequest> topUp(Long id, TopUpRequest request, String cardNumber);
}
