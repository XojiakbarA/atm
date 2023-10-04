package uz.pdp.atm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.atm.dto.request.BanknoteRequest;
import uz.pdp.atm.dto.view.BanknoteView;
import uz.pdp.atm.entity.Banknote;
import uz.pdp.atm.exception.ExistsByCurrencyAndAmountException;
import uz.pdp.atm.exception.NotFoundByIdException;
import uz.pdp.atm.mapper.BanknoteMapper;
import uz.pdp.atm.repository.BanknoteRepository;
import uz.pdp.atm.service.BanknoteService;

import java.util.Currency;

@Service
public class BanknoteServiceImpl implements BanknoteService {
    @Autowired
    private BanknoteRepository banknoteRepository;
    @Autowired
    private BanknoteMapper banknoteMapper;

    @Override
    public Page<BanknoteView> getAll(Pageable pageable) {
        return banknoteRepository.findAll(pageable).map(banknoteMapper::mapToView);
    }

    @Override
    public BanknoteView getById(Long id) {
        return banknoteMapper.mapToView(findById(id));
    }

    @Override
    public BanknoteView create(BanknoteRequest request) {
        Currency currency = Currency.getInstance(request.getCurrency());
        if (banknoteRepository.existsByCurrencyAndAmount(currency, request.getAmount())) {
            throw new ExistsByCurrencyAndAmountException(
                    Banknote.class.getSimpleName(), request.getCurrency(), request.getAmount()
            );
        }
        Banknote banknote = new Banknote();

        banknoteMapper.mapToEntity(banknote, request);

        return banknoteMapper.mapToView(save(banknote));
    }

    @Override
    public BanknoteView update(BanknoteRequest request, Long id) {
        Currency currency = Currency.getInstance(request.getCurrency());
        if (banknoteRepository.existsByCurrencyAndAmountAndIdNot(currency, request.getAmount(), id)) {
            throw new ExistsByCurrencyAndAmountException(
                    Banknote.class.getSimpleName(), request.getCurrency(), request.getAmount()
            );
        }
        Banknote banknote = findById(id);

        banknoteMapper.mapToEntity(banknote, request);

        return banknoteMapper.mapToView(save(banknote));
    }

    @Override
    public Banknote findById(Long id) {
        return banknoteRepository.findById(id).orElseThrow(
                () -> new NotFoundByIdException(Banknote.class.getSimpleName(), id)
        );
    }

    @Override
    public Banknote save(Banknote banknote) {
        return banknoteRepository.save(banknote);
    }

    @Override
    public void deleteById(Long id) {
        if (!banknoteRepository.existsById(id)) {
            throw new NotFoundByIdException(Banknote.class.getSimpleName(), id);
        }
        banknoteRepository.deleteById(id);
    }
}
