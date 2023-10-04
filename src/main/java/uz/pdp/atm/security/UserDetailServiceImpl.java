package uz.pdp.atm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.atm.repository.CardRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private CardRepository cardRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return cardRepository.findByNumber(username).orElseThrow(
                () -> new UsernameNotFoundException(String.format("Card by number '%s' not found", username))
        );
    }
}
