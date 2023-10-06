package uz.pdp.atm.enums;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityType implements GrantedAuthority {
    CRUD_ALL, ADD_ATM_BANKNOTES, WITHDRAW, TOP_UP;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
