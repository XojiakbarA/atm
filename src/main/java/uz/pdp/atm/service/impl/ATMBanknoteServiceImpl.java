package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.atm.dto.view.ATMBanknoteView;
import uz.pdp.atm.mapper.ATMBanknoteMapper;
import uz.pdp.atm.repository.ATMBanknoteRepository;
import uz.pdp.atm.service.ATMBanknoteService;

import java.util.List;

@Service
public class ATMBanknoteServiceImpl implements ATMBanknoteService {
    @Autowired
    private ATMBanknoteRepository atmBanknoteRepository;
    @Autowired
    private ATMBanknoteMapper atmBanknoteMapper;
    @Override
    public List<ATMBanknoteView> getAllByATMId(Long atmId) {
        return atmBanknoteRepository.findAllByAtmId(atmId).stream().map(atmBanknoteMapper::mapToView).toList();
    }
}
