package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.atm.dto.request.CardRequest;
import uz.pdp.atm.dto.view.CardView;
import uz.pdp.atm.entity.Bank;
import uz.pdp.atm.entity.Card;
import uz.pdp.atm.exception.ExistsByNumberException;
import uz.pdp.atm.exception.NotFoundByIdException;
import uz.pdp.atm.exception.NotFoundByNumberException;
import uz.pdp.atm.mapper.CardMapper;
import uz.pdp.atm.repository.CardRepository;
import uz.pdp.atm.service.BankService;
import uz.pdp.atm.service.CardService;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BankService bankService;
    @Autowired
    private CardMapper cardMapper;
    @Override
    public Page<CardView> getAll(Pageable pageable) {
        return cardRepository.findAll(pageable).map(cardMapper::mapToView);
    }

    @Override
    public Page<CardView> getAllByBankId(Pageable pageable, Long bankId) {
        return cardRepository.findAllByBankId(pageable, bankId).map(cardMapper::mapToView);
    }

    @Override
    public CardView getById(Long id) {
        return cardMapper.mapToView(findById(id));
    }

    @Override
    public CardView create(CardRequest request) {
        if (cardRepository.existsByNumber(request.getNumber().toString())) {
            throw new ExistsByNumberException(Card.class.getSimpleName(), request.getNumber().toString());
        }
        Card card = new Card();

        cardMapper.mapToEntity(card, request);

        return cardMapper.mapToView(save(card));
    }

    @Override
    public CardView update(CardRequest request, Long id) {
        if (cardRepository.existsByNumberAndIdNot(request.getNumber().toString(), id)) {
            throw new ExistsByNumberException(Card.class.getSimpleName(), request.getNumber().toString());
        }
        Card card = findById(id);

        cardMapper.mapToEntity(card, request);

        return cardMapper.mapToView(save(card));
    }

    @Override
    public CardView setBank(Long cardId, Long bankId) {
        Card card = findById(cardId);
        Bank bank = bankService.findById(bankId);

        card.setBank(bank);

        return cardMapper.mapToView(save(card));
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> new NotFoundByIdException(Card.class.getSimpleName(), id)
        );
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number).orElseThrow(
                () -> new NotFoundByNumberException(Card.class.getSimpleName(), number)
        );
    }

    @Override
    public Card save(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public void deleteById(Long id) {
        if (!cardRepository.existsById(id)) {
            throw new NotFoundByIdException(Card.class.getSimpleName(), id);
        }
        cardRepository.deleteById(id);
    }

    @Override
    public void increaseAttemptsByNumber(String number) {
        Card card = findByNumber(number);

        card.setAttempts(card.getAttempts() + 1);

        save(card);
    }

    @Override
    public void resetAttemptsByNumber(String number) {
        Card card = findByNumber(number);

        card.setAttempts(0);

        save(card);
    }
}
