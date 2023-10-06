package uz.pdp.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
import uz.pdp.atm.dto.request.*;
import uz.pdp.atm.dto.response.Response;
import uz.pdp.atm.dto.view.ATMBanknoteView;
import uz.pdp.atm.dto.view.ATMView;
import uz.pdp.atm.dto.view.OperationView;
import uz.pdp.atm.enums.CardType;
import uz.pdp.atm.enums.OperationType;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.service.ATMBanknoteService;
import uz.pdp.atm.service.ATMService;
import uz.pdp.atm.service.OperationService;
import uz.pdp.atm.validator.IsValidEnum;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Validated
@RestController
@RequestMapping("/atms")
public class ATMController {
    @Autowired
    private ATMService atmService;
    @Autowired
    private ATMBanknoteService atmBanknoteService;
    @Autowired
    private OperationService operationService;

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<ATMView> banks = atmService.getAll(PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), banks);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getById(@PathVariable Long id) {
        ATMView atm = atmService.getById(id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping("/{id}/operations")
    @ResponseStatus(HttpStatus.OK)
    public Response getAllOperationsByATMId(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<OperationView> operations = operationService.getAllByATMId(id, PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), operations);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping("/{id}/operations/daily-inputs")
    @ResponseStatus(HttpStatus.OK)
    public Response getDailyAllInputOperationsByATMId(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<OperationView> atm = operationService.getDailyAllByATMIdAndType(id, OperationType.INPUT, PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping("/{id}/operations/daily-outputs")
    @ResponseStatus(HttpStatus.OK)
    public Response getDailyAllOutputOperationsByATMId(@PathVariable Long id, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<OperationView> atm = operationService.getDailyAllByATMIdAndType(id, OperationType.OUTPUT, PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @GetMapping("/{id}/atm-banknotes")
    @ResponseStatus(HttpStatus.OK)
    public Response getAllATMBanknotesByATMId(@PathVariable Long id) {
        List<ATMBanknoteView> atm = atmBanknoteService.getAllByATMId(id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody ATMRequest request) {
        ATMView atm = atmService.create(request);

        return new Response(HttpStatus.CREATED.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateById(@Valid @RequestBody ATMRequest request, @PathVariable Long id) {
        ATMView atm = atmService.update(request, id);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @PutMapping("/{atmId}/bank/{bankId}")
    @ResponseStatus(HttpStatus.OK)
    public Response setBank(@PathVariable Long atmId, @PathVariable Long bankId) {
        ATMView atm = atmService.setBank(atmId, bankId);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @PutMapping("/{id}/card-types/{cardType}")
    @ResponseStatus(HttpStatus.OK)
    public Response addCardType(@PathVariable Long id, @PathVariable @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA") String cardType) {
        ATMView atm = atmService.addCardType(id, cardType);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @DeleteMapping("/{id}/card-types/{cardType}")
    @ResponseStatus(HttpStatus.OK)
    public Response removeCardType(@PathVariable Long id, @PathVariable @IsValidEnum(enumClazz = CardType.class, message = "cardType must be any of UZCARD, HUMO, VISA") String cardType) {
        ATMView atm = atmService.removeCardType(id, cardType);

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).ADD_ATM_BANKNOTES)")
    @PutMapping("/{id}/atm-banknotes")
    @ResponseStatus(HttpStatus.OK)
    public Response addBanknote(@Valid @RequestBody TopUpRequest request, @PathVariable Long id, Authentication authentication) {
        ATMView atm = atmService.addATMBanknotes(request, id, authentication.getName());

        return new Response(HttpStatus.OK.name(), atm);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).WITHDRAW)")
    @PutMapping("/{id}/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public Response withdraw(@Valid @RequestBody WithdrawRequest request, @PathVariable Long id, Authentication authentication) {
        Long withdrawalAmount = atmService.withdraw(id, request, authentication.getName());

        return new Response(HttpStatus.OK.name(), Map.of("withdrawalAmount", withdrawalAmount));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).TOP_UP)")
    @PutMapping("/{id}/top-up")
    @ResponseStatus(HttpStatus.OK)
    public Response topUp(@PathVariable Long id, @RequestBody TopUpRequest request, Authentication authentication) {
        Set<ATMBanknoteRequest> unsupportedBanknotes = atmService.topUp(id, request, authentication.getName());

        return new Response(HttpStatus.OK.name(), Map.of("unsupportedBanknotes", unsupportedBanknotes));
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.atm.enums.AuthorityType).CRUD_ALL)")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteById(@PathVariable Long id) {
        atmService.deleteById(id);

        return new Response(HttpStatus.ACCEPTED.name());
    }
}
