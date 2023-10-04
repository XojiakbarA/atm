package uz.pdp.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import uz.pdp.atm.dto.request.ATMRequest;
import uz.pdp.atm.dto.request.ATMBanknoteRequest;
import uz.pdp.atm.dto.response.Response;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.service.ATMService;
import uz.pdp.atm.validator.IsValidEnum;


@Validated
@RestController
@RequestMapping("/atms")
public class ATMController {
    @Autowired
    private ATMService atmService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ATMView> banks = atmService.getAll(PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), banks);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getById(@PathVariable Long id) {
        ATMView atm = atmService.getById(id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody ATMRequest request) {
        ATMView atm = atmService.create(request);

        return new Response(HttpStatus.CREATED.name(), atm);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateById(@Valid @RequestBody ATMRequest request, @PathVariable Long id) {
        ATMView atm = atmService.update(request, id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PutMapping("/{atmId}/bank/{bankId}")
    @ResponseStatus(HttpStatus.OK)
    public Response setBank(@PathVariable Long atmId, @PathVariable Long bankId) {
        ATMView atm = atmService.setBank(atmId, bankId);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PutMapping("/{id}/card-types/{cardType}")
    @ResponseStatus(HttpStatus.OK)
    public Response addCardType(@PathVariable Long id, @PathVariable @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA") String cardType) {
        ATMView atm = atmService.addCardType(id, cardType);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @DeleteMapping("/{id}/card-types/{cardType}")
    @ResponseStatus(HttpStatus.OK)
    public Response removeCardType(@PathVariable Long id, @PathVariable @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA") String cardType) {
        ATMView atm = atmService.removeCardType(id, cardType);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PutMapping("/{id}/atm-banknotes")
    @ResponseStatus(HttpStatus.OK)
    public Response addBanknote(@Valid @RequestBody ATMBanknoteRequest request, @PathVariable Long id) {
        ATMView atm = atmService.addATMBanknote(request, id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteById(@PathVariable Long id) {
        atmService.deleteById(id);

        return new Response(HttpStatus.ACCEPTED.name());
    }
}
