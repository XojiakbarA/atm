package uz.pdp.atm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.pdp.atm.enums.OperationType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity(name = "operations")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne(optional = false)
    private ATM atm;

    @OneToMany(mappedBy = "operation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<OperationBanknote> banknotes = new HashSet<>();

    @Column(nullable = false)
    private String createdBy;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
