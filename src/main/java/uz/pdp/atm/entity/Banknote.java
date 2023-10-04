package uz.pdp.atm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Currency;

@Data
@Entity(name = "banknotes")
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"currency", "amount"})})
public class Banknote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Currency currency;

    @Column(nullable = false)
    private Integer amount;
}
