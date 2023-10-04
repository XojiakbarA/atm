package uz.pdp.atm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.atm.dto.request.BanknoteRequest;
import uz.pdp.atm.dto.view.BanknoteView;
import uz.pdp.atm.entity.Banknote;

public interface BanknoteService {
    Page<BanknoteView> getAll(Pageable pageable);

    BanknoteView getById(Long id);

    BanknoteView create(BanknoteRequest request);

    BanknoteView update(BanknoteRequest request, Long id);

    Banknote findById(Long id);

    Banknote save(Banknote banknote);

    void deleteById(Long id);
}
