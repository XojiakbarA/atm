package uz.pdp.atm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity(name = "operation_banknotes")
public class OperationBanknote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(optional = false)
    private Operation operation;

    @ManyToOne(optional = false)
    private Banknote banknote;

    @Column(nullable = false)
    private Integer count;
}
