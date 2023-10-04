package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
