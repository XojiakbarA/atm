package uz.pdp.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.atm.entity.Bank;
import uz.pdp.atm.entity.Banknote;
import uz.pdp.atm.entity.Card;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.service.BankService;
import uz.pdp.atm.service.BanknoteService;
import uz.pdp.atm.service.CardService;

import java.util.Currency;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BankService bankService;
    @Autowired
    private BanknoteService banknoteService;
    @Autowired
    private CardService cardService;

    @Override
    public void run(String... args) {
        createBank("Bank 1");
        createBank("Bank 2");
        createBank("Bank 3");
        createBanknote(Currency.getInstance("UZS"), 1000);
        createBanknote(Currency.getInstance("UZS"), 5000);
        createBanknote(Currency.getInstance("UZS"), 10_000);
        createBanknote(Currency.getInstance("UZS"), 50_000);
        createBanknote(Currency.getInstance("UZS"), 100_000);
        createBanknote(Currency.getInstance("USD"), 1);
        createBanknote(Currency.getInstance("USD"), 2);
        createBanknote(Currency.getInstance("USD"), 5);
        createBanknote(Currency.getInstance("USD"), 10);
        createBanknote(Currency.getInstance("USD"), 20);
        createBanknote(Currency.getInstance("USD"), 50);
        createBanknote(Currency.getInstance("USD"), 100);
        createCard("1111222233334444", "1122", "123",
                "User1", "User1", CardType.UZCARD, 1L);
    }

    public void createBank(String name) {
        Bank bank = new Bank();
        bank.setName(name);
        bankService.save(bank);
    }

    public void createBanknote(Currency currency, Integer amount) {
        Banknote banknote = new Banknote();
        banknote.setCurrency(currency);
        banknote.setAmount(amount);
        banknoteService.save(banknote);
    }

    public void createCard(String number, String password, String cvv, String firstName, String lastName, CardType cardType, Long bankId) {
        Card card = new Card();
        card.setNumber(number);
        card.setPassword(passwordEncoder.encode(password));
        card.setCvv(cvv);
        card.setFirstName(firstName);
        card.setLastName(lastName);
        card.setCardType(cardType);
        card.setBank(bankService.findById(bankId));
        cardService.save(card);
    }
}
