package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.ATMBanknote;

import java.util.List;

@Repository
public interface ATMBanknoteRepository extends JpaRepository<ATMBanknote, Long> {
    List<ATMBanknote> findAllByAtmId(Long atmId);
}
