package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.request.TopUpRequest;
import uz.pdp.atm.dto.request.WithdrawRequest;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.entity.*;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.enums.OperationType;
import uz.pdp.atm.exception.ATMIsNotEnabledException;
import uz.pdp.atm.exception.BalanceIsInsufficientException;
import uz.pdp.atm.exception.NotFoundByIdException;
import uz.pdp.atm.exception.CardIsNotSupportedException;
import uz.pdp.atm.mapper.ATMMapper;
import uz.pdp.atm.repository.ATMRepository;
import uz.pdp.atm.service.*;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ATMServiceImpl implements ATMService {
    @Autowired
    private ATMRepository atmRepository;
    @Autowired
    private BankService bankService;
    @Autowired
    private BanknoteService banknoteService;
    @Autowired
    private CardService cardService;
    @Autowired
    private OperationService operationService;
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
    public ATMView addATMBanknotes(TopUpRequest request, Long id, String username) {
        ATM atm = findById(id);

        for (ATMBanknoteRequest banknoteRequest : request.getBanknotes()) {
            addATMBanknote(banknoteRequest, atm);
        }

        operationService.create(OperationType.INPUT, atm, request.getBanknotes(), username);

        return atmMapper.mapToView(save(atm));
    }

    private void addATMBanknote(ATMBanknoteRequest request, ATM atm) {
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

    @Transactional
    @Override
    public Long withdraw(Long id, WithdrawRequest request, String cardNumber) {
        ATM atm = findById(id);
        Card card = cardService.findByNumber(cardNumber);

        if (!atm.isEnabled()) {
            throw new ATMIsNotEnabledException();
        }
        if (!atm.getCardTypes().contains(card.getCardType())) {
            throw new CardIsNotSupportedException(atm.getCardTypes(), card.getCardType());
        }
        if (card.getBalance() < request.getAmount()) {
            throw new BalanceIsInsufficientException(Card.class.getSimpleName(), card.getBalance());
        }
        Long remainder = withdrawFromATM(atm, card, request.getAmount());
        Long withdrawalAmount = request.getAmount() - remainder;
        card.setBalance(card.getBalance() - withdrawalAmount);

        cardService.save(card);
        save(atm);

        return withdrawalAmount;
    }

    @Override
    public Set<ATMBanknoteRequest> topUp(Long id, TopUpRequest request, String cardNumber) {
        ATM atm = findById(id);
        Card card = cardService.findByNumber(cardNumber);

        if (!atm.isEnabled()) {
            throw new ATMIsNotEnabledException();
        }
        if (!atm.getCardTypes().contains(card.getCardType())) {
            throw new CardIsNotSupportedException(atm.getCardTypes(), card.getCardType());
        }

        int sumOfSupportedBanknotes = 0;
        Set<ATMBanknoteRequest> unsupportedBanknotes = new HashSet<>();

        for (ATMBanknoteRequest b : request.getBanknotes()) {
            Banknote banknote = banknoteService.findById(b.getBanknoteId());
            if (banknote.getCurrency().equals(card.getCardType().getCurrency())) {
                sumOfSupportedBanknotes += banknote.getAmount() * b.getCount();
                addATMBanknote(b, atm);
            } else {
                unsupportedBanknotes.add(b);
            }
        }

        card.setBalance(card.getBalance() + sumOfSupportedBanknotes);

        cardService.save(card);
        save(atm);

        operationService.create(OperationType.INPUT, atm, request.getBanknotes(), card.getNumber());
        return unsupportedBanknotes;
    }

    private Long withdrawFromATM(ATM atm, Card card, Long amount) {
        Long atmBalance = atm.getTotalMoney().get(card.getCardType().getCurrency());
        if (atmBalance < amount) {
            throw new BalanceIsInsufficientException(ATM.class.getSimpleName(), atmBalance);
        }

        Set<ATMBanknote> atmBanknotes = atm.getAtmBanknotes().stream()
                .filter(b -> b.getBanknote().getCurrency().equals(card.getCardType().getCurrency()))
                .collect(Collectors.toCollection(TreeSet::new));

        Set<ATMBanknoteRequest> banknotes = new HashSet<>();

        for (ATMBanknote atmBanknote : atmBanknotes) {
            int banknoteAmount = atmBanknote.getBanknote().getAmount();
            int banknoteCount = atmBanknote.getCount();

            long withdrawalCount = amount / banknoteAmount;
            long remainingAmount = amount % banknoteAmount;

            if (withdrawalCount <= banknoteCount) {
                atmBanknote.setCount((int) (banknoteCount - withdrawalCount));
                amount = remainingAmount;

                ATMBanknoteRequest atmBanknoteRequest = new ATMBanknoteRequest(
                        atmBanknote.getBanknote().getId(),(int) withdrawalCount
                );
                banknotes.add(atmBanknoteRequest);

                if (amount == 0) {
                    operationService.create(OperationType.OUTPUT, atm, banknotes, card.getNumber());
                    return 0L;
                };
            } else {
                atmBanknote.setCount(0);
                amount = amount - (long) banknoteAmount * banknoteCount;

                ATMBanknoteRequest atmBanknoteRequest = new ATMBanknoteRequest(
                        atmBanknote.getBanknote().getId(), banknoteCount
                );
                banknotes.add(atmBanknoteRequest);
            }
        }

        operationService.create(OperationType.OUTPUT, atm, banknotes, card.getNumber());
        return amount;
    }
}
