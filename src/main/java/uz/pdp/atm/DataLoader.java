package uz.pdp.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import uz.pdp.atm.entity.ATM;
import uz.pdp.atm.entity.ATMBanknote;
import uz.pdp.atm.entity.Bank;
import uz.pdp.atm.entity.Banknote;
import uz.pdp.atm.entity.Card;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.enums.RoleType;
import uz.pdp.atm.service.ATMService;
import uz.pdp.atm.service.BankService;
import uz.pdp.atm.service.BanknoteService;
import uz.pdp.atm.service.CardService;

import java.util.Currency;
import java.util.Set;
import java.util.TreeSet;

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
    @Autowired
    private ATMService atmService;

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
        createCard(5_000_000D, "1111222233334444", "1122", "123",
                "User1", "User1", CardType.UZCARD, 1L, RoleType.USER);
        createCard(5_000_000D, "2222333344441111", "1122", "123",
                "User1", "User1", CardType.UZCARD, 1L, RoleType.DIRECTOR);
        createCard(5_000_000D, "3333444411112222", "1122", "123",
                "User1", "User1", CardType.UZCARD, 1L, RoleType.EMPLOYEE);
        createATM(3_000_000L, 10_000_000L, Set.of(CardType.HUMO, CardType.UZCARD), 1L);
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

    public void createCard(Double balance, String number, String password, String cvv, String firstName, String lastName, CardType cardType, Long bankId, RoleType roleType) {
        Card card = new Card();
        card.setBalance(balance);
        card.setNumber(number);
        card.setPassword(passwordEncoder.encode(password));
        card.setCvv(cvv);
        card.setFirstName(firstName);
        card.setLastName(lastName);
        card.setCardType(cardType);
        card.setBank(bankService.findById(bankId));
        card.setRoleType(roleType);
        cardService.save(card);
    }

    public void createATM(Long maxWithdrawalAmount, Long warningAmount, Set<CardType> cardTypes, Long bankId) {
        ATM atm = new ATM();
        atm.setMaxWithdrawalAmount(maxWithdrawalAmount);
        atm.setWarningAmount(warningAmount);
        atm.setCardTypes(cardTypes);
        atm.setCommissionForWithdrawOwnCard(0.5);
        atm.setCommissionForTopUpOwnCard(0.25);
        atm.setCommissionForWithdrawOtherCard(1.0);
        atm.setCommissionForTopUpOtherCard(0.5);
        atm.setBank(bankService.findById(bankId));

        ATMBanknote atmBanknote1 = new ATMBanknote();
        atmBanknote1.setBanknote(banknoteService.findByAmount(1_000));
        atmBanknote1.setCount(100);
        atmBanknote1.setAtm(atm);

        ATMBanknote atmBanknote2 = new ATMBanknote();
        atmBanknote2.setBanknote(banknoteService.findByAmount(5_000));
        atmBanknote2.setCount(100);
        atmBanknote2.setAtm(atm);

        ATMBanknote atmBanknote3 = new ATMBanknote();
        atmBanknote3.setBanknote(banknoteService.findByAmount(10_000));
        atmBanknote3.setCount(50);
        atmBanknote3.setAtm(atm);   

        ATMBanknote atmBanknote4 = new ATMBanknote();
        atmBanknote4.setBanknote(banknoteService.findByAmount(50_000));
        atmBanknote4.setCount(50);
        atmBanknote4.setAtm(atm);

        ATMBanknote atmBanknote5 = new ATMBanknote();
        atmBanknote5.setBanknote(banknoteService.findByAmount(50));
        atmBanknote5.setCount(50);
        atmBanknote5.setAtm(atm);

        ATMBanknote atmBanknote6 = new ATMBanknote();
        atmBanknote6.setBanknote(banknoteService.findByAmount(100));
        atmBanknote6.setCount(70);
        atmBanknote6.setAtm(atm);

        atm.setAtmBanknotes(new TreeSet<>(Set.of(atmBanknote1, atmBanknote2, atmBanknote3, atmBanknote4, atmBanknote5, atmBanknote6)));

        atmService.save(atm);
    }
}
