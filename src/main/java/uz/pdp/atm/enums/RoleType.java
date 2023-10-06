package uz.pdp.atm.enums;

import lombok.Getter;

import java.util.Set;

import static uz.pdp.atm.enums.AuthorityType.*;

@Getter
public enum RoleType {
    DIRECTOR(Set.of(CRUD_ALL)),
    EMPLOYEE(Set.of(ADD_ATM_BANKNOTES)),
    USER(Set.of(WITHDRAW, TOP_UP));

    private final Set<AuthorityType> authorities;

    RoleType(Set<AuthorityType> authorities) {
        this.authorities = authorities;
    }
}
