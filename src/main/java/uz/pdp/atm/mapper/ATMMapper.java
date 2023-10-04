package uz.pdp.atm.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.entity.ATM;
import uz.pdp.atm.entity.Address;
import uz.pdp.atm.enums.CardType;

@Component
public class ATMMapper {
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private ATMBanknoteMapper atmBanknoteMapper;

    public ATMView mapToView(ATM atm) {
        if (atm == null) return null;

        return ATMView.builder()
                    .id(atm.getId())
                    .cardTypes(atm.getCardTypes())
                    .maxWithdrawalAmount(atm.getMaxWithdrawalAmount())
                    .warningAmount(atm.getWarningAmount())
                    .commissionForWithdrawOwnCard(atm.getCommissionForWithdrawOwnCard())
                    .commissionForTopUpOwnCard(atm.getCommissionForTopUpOwnCard())
                    .commissionForWithdrawOtherCard(atm.getCommissionForWithdrawOtherCard())
                    .commissionForTopUpOtherCard(atm.getCommissionForTopUpOtherCard())
                    .bank(bankMapper.mapToView(atm.getBank()))
                    .address(addressMapper.mapToView(atm.getAddress()))
                    .atmBanknotes(atm.getAtmBanknotes().stream().map(atmBanknoteMapper::mapToView).collect(Collectors.toSet()))
                    .enabled(atm.isEnabled())
                    .totalMoney(atm.getTotalMoney())
                    .build();
    }

    public void mapToEntity(ATM atm, ATMRequest request) {
        if (request.getMaxWithdrawalAmount() != null) {
            atm.setMaxWithdrawalAmount(request.getMaxWithdrawalAmount());
        }
        if (request.getWarningAmount() != null) {
            atm.setWarningAmount(request.getWarningAmount());
        }
        if (request.getCommissionForWithdrawOwnCard() != null) {
            atm.setCommissionForWithdrawOwnCard(request.getCommissionForWithdrawOwnCard());
        }
        if (request.getCommissionForTopUpOwnCard() != null) {
            atm.setCommissionForTopUpOwnCard(request.getCommissionForTopUpOwnCard());
        }
        if (request.getCommissionForWithdrawOtherCard() != null) {
            atm.setCommissionForWithdrawOtherCard(request.getCommissionForWithdrawOtherCard());
        }
        if (request.getCommissionForTopUpOtherCard() != null) {
            atm.setCommissionForTopUpOtherCard(request.getCommissionForTopUpOtherCard());
        }
        if (request.getCardTypes() != null && !request.getCardTypes().isEmpty()) {
            atm.getCardTypes().addAll(request.getCardTypes().stream().map(c -> CardType.valueOf(c.toUpperCase())).collect(Collectors.toSet()));
        }
        if (request.getAddress() != null) {
            Address address = new Address();

            if (atm.getAddress() != null) {
                address = atm.getAddress();
            }

            addressMapper.mapToEntity(address, request.getAddress());

            atm.setAddress(address);
        }
    }
}
