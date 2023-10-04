package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.atm.dto.request.CardRequest;
import uz.pdp.atm.dto.view.CardView;
import uz.pdp.atm.entity.Card;

public interface CardService {
    Page<CardView> getAll(Pageable pageable);

    Page<CardView> getAllByBankId(Pageable pageable, Long bankId);

    CardView getById(Long id);

    CardView create(CardRequest request);

    CardView update(CardRequest request, Long id);

    CardView setBank(Long cardId, Long bankId);

    Card findById(Long id);

    Card save(Card card);

    void deleteById(Long id);
}
