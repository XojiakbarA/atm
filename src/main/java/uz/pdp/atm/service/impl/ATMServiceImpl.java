package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.entity.ATM;
import uz.pdp.atm.entity.ATMBanknote;
import uz.pdp.atm.entity.Bank;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.exception.NotFoundByIdException;
import uz.pdp.atm.mapper.ATMMapper;
import uz.pdp.atm.repository.ATMRepository;
import uz.pdp.atm.service.ATMService;
import uz.pdp.atm.service.BankService;
import uz.pdp.atm.service.BanknoteService;

@Service
public class ATMServiceImpl implements ATMService {
    @Autowired
    private ATMRepository atmRepository;
    @Autowired
    private BankService bankService;
    @Autowired
    private BanknoteService banknoteService;
    @Autowired
    private ATMMapper atmMapper;

    @Override
    public Page<ATMView> getAll(Pageable pageable) {
        return atmRepository.findAll(pageable).map(atmMapper::mapToView);
    }

    @Override
    public ATMView getById(Long id) {
        return atmMapper.mapToView(findById(id));
    }

    @Override
    public ATMView create(ATMRequest request) {
        ATM atm = new ATM();

        atmMapper.mapToEntity(atm, request);

        return atmMapper.mapToView(save(atm));
    }

    @Override
    public ATMView update(ATMRequest request, Long id) {
        ATM atm = findById(id);

        atmMapper.mapToEntity(atm, request);

        return atmMapper.mapToView(save(atm));
    }

    @Override
    public ATMView setBank(Long atmId, Long bankId) {
        ATM atm = findById(atmId);
        Bank bank = bankService.findById(bankId);

        atm.setBank(bank);

        return atmMapper.mapToView(save(atm));
    }

    @Override
    public ATMView addCardType(Long id, String cardType) {
        ATM atm = findById(id);

        atm.getCardTypes().add(CardType.valueOf(cardType.toUpperCase()));

        return atmMapper.mapToView(save(atm));
    }

    
    @Override
    public ATMView removeCardType(Long id, String cardType) {
        ATM atm = findById(id);

        atm.getCardTypes().remove(CardType.valueOf(cardType.toUpperCase()));

        return atmMapper.mapToView(save(atm));
    }

    @Override
    public ATMView addATMBanknote(ATMBanknoteRequest request, Long id) {
        ATM atm = findById(id);

        if (atm.getAtmBanknotes().stream().anyMatch(b -> b.getBanknote().getId().equals(request.getBanknoteId()))) {
            for (ATMBanknote atmBanknote : atm.getAtmBanknotes()) {

                if (atmBanknote.getBanknote().getId().equals(request.getBanknoteId())) {

                    atmBanknote.setCount(atmBanknote.getCount() + request.getCount());
                    break;

                }

            }
        } else {
            ATMBanknote atmBanknote = new ATMBanknote();

            atmBanknote.setAtm(atm);
            atmBanknote.setBanknote(banknoteService.findById(request.getBanknoteId()));
            atmBanknote.setCount(request.getCount());

            atm.getAtmBanknotes().add(atmBanknote);
        }

        return atmMapper.mapToView(save(atm));
    }

    @Override
    public ATM findById(Long id) {
        return atmRepository.findById(id).orElseThrow(
                () -> new NotFoundByIdException(ATM.class.getSimpleName(), id)
        );
    }

    @Override
    public ATM save(ATM atm) {
        return atmRepository.save(atm);
    }

    @Override
    public void deleteById(Long id) {
        if (!atmRepository.existsById(id)) {
            throw new NotFoundByIdException(ATM.class.getSimpleName(), id);
        }
        atmRepository.deleteById(id);
    }    
}
