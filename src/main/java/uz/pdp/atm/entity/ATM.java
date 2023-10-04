package uz.pdp.atm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uz.pdp.atm.enums.CardType;

import java.util.Set;

@Data
@Entity(name = "atms")
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Set<CardType> cardTypes;

    @Column(nullable = false)
    private Double maxWithdrawalAmount;

    @Column(nullable = false)
    private Double warningAmount;

    @Column(nullable = false)
    private Double commissionForOwnCardSender;

    @Column(nullable = false)
    private Double commissionForOwnCardReceiver;

    @Column(nullable = false)
    private Double commissionForOtherCardSender;

    @Column(nullable = false)
    private Double commissionForOtherCardReceiver;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "atm", cascade = CascadeType.REMOVE)
    private Set<ATMBanknote> atmBanknotes;
}
