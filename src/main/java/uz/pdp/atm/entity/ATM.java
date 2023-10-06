package uz.pdp.atm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uz.pdp.atm.enums.CardType;

import java.util.*;

@Data
@Entity(name = "atms")
public class ATM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<CardType> cardTypes = new HashSet<>();

    @Column(nullable = false)
    private Long maxWithdrawalAmount;

    @Column(nullable = false)
    private Long warningAmount;

    @Column(nullable = false)
    private Double commissionForWithdrawOwnCard;

    @Column(nullable = false)
    private Double commissionForTopUpOwnCard;

    @Column(nullable = false)
    private Double commissionForWithdrawOtherCard;

    @Column(nullable = false)
    private Double commissionForTopUpOtherCard;

    @ManyToOne
    private Bank bank;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "atm", cascade = CascadeType.ALL)
    private Set<ATMBanknote> atmBanknotes = new TreeSet<>();

    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "atm", cascade = CascadeType.REMOVE)
    private Set<Operation> operations = new HashSet<>();

    public Map<Currency, Long> getTotalMoney() {
        Map<Currency, Long> map = new HashMap<>();
        
        for (ATMBanknote atmBanknote : atmBanknotes) {
            Long sum = atmBanknotes.stream()
                            .filter(b -> b.getBanknote().getCurrency().equals(atmBanknote.getBanknote().getCurrency()))
                            .map(b -> (long) b.getCount() * b.getBanknote().getAmount())
                            .reduce(0L, Long::sum);
            map.put(atmBanknote.getBanknote().getCurrency(), sum);
        }

        return map;
    }

    public boolean isEnabled() {
        return bank != null;
    }
}
