package uz.pdp.atm.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.pdp.atm.enums.CardType;

import java.time.LocalDateTime;

@Data
@Entity(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 16)
    private String number;

    @ManyToOne
    private Bank bank;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(nullable = false, length = 4)
    private Integer password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false)
    private Boolean active = false;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
