package uz.pdp.atm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.atm.entity.Operation;
import uz.pdp.atm.enums.OperationType;

import java.time.LocalDate;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {
    Page<Operation> findAllByAtmId(Long atmId, Pageable pageable);

    Page<Operation> findAllByAtmIdAndTypeAndCreatedAtDate(Long atmId, OperationType type, LocalDate date, Pageable pageable);
}
