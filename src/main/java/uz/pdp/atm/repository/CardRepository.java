package uz.pdp.atm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.Card;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Page<Card> findAllByBankId(Pageable pageable, Long bankId);
    Optional<Card> findByNumber(String number);
    boolean existsByNumber(String number);
    boolean existsByNumberAndIdNot(String number, Long id);
}
