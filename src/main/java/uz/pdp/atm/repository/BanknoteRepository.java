package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.Banknote;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
}
