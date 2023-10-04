package uz.pdp.atm.mapper;

import org.springframework.stereotype.Component;

import uz.pdp.atm.dto.request.AddressRequest;
import uz.pdp.atm.dto.view.AddressView;
import uz.pdp.atm.entity.Address;

@Component
public class AddressMapper {
    public AddressView mapToView(Address address) {
        if (address == null) return null;

        return AddressView.builder()
                    .id(address.getId())
                    .region(address.getRegion())
                    .district(address.getDistrict())
                    .street(address.getStreet())
                    .home(address.getHome())
                    .build();
    }

    public void mapToEntity(Address address, AddressRequest request) {
        if (request.getRegion() != null && !request.getRegion().isBlank()) {
            address.setRegion(request.getRegion());
        }
        if (request.getDistrict() != null && !request.getDistrict().isBlank()) {
            address.setDistrict(request.getDistrict());
        }
        if (request.getStreet() != null && !request.getStreet().isBlank()) {
            address.setStreet(request.getStreet());
        }
        if (request.getHome() != null && !request.getHome().isBlank()) {
            address.setHome(request.getHome());
        }
    }
}
