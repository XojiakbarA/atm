package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.Banknote;

import java.util.Currency;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
    boolean existsByCurrencyAndAmount(Currency currency, Integer amount);
    boolean existsByCurrencyAndAmountAndIdNot(Currency currency, Integer amount, Long id);
}
