package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.ATMBanknote;

@Repository
public interface ATMBanknoteRepository extends JpaRepository<ATMBanknote, Long> {
}
