package uz.pdp.atm.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "banks")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


}
