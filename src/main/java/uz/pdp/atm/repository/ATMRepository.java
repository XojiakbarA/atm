package uz.pdp.atm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.ATM;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {
}
