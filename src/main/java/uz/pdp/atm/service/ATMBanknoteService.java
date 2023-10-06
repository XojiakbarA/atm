package uz.pdp.atm.service;

import uz.pdp.atm.dto.view.ATMBanknoteView;

import java.util.List;

public interface ATMBanknoteService {
    List<ATMBanknoteView> getAllByATMId(Long atmId);
}
