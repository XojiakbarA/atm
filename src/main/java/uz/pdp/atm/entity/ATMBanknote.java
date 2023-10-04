package uz.pdp.atm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "atms_banknotes")
public class ATMBanknote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private ATM atm;

    @ManyToOne(optional = false)
    private Banknote banknote;

    @Column(nullable = false)
    private Integer count;
}
