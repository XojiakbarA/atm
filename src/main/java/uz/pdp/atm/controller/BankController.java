package uz.pdp.atm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.pdp.atm.dto.request.BankRequest;
import uz.pdp.atm.dto.response.Response;
import uz.pdp.atm.dto.view.BankView;
import uz.pdp.atm.service.BankService;

@RestController
@RequestMapping("/banks")
public class BankController {
    @Autowired
    private BankService bankService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<BankView> banks = bankService.getAll(PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), banks);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getById(@PathVariable Long id) {
        BankView bank = bankService.getById(id);

        return new Response(HttpStatus.OK.name(), bank);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody BankRequest request) {
        BankView bank = bankService.create(request);

        return new Response(HttpStatus.CREATED.name(), bank);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateById(@Valid @RequestBody BankRequest request, @PathVariable Long id) {
        BankView bank = bankService.update(request, id);

        return new Response(HttpStatus.OK.name(), bank);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteById(@PathVariable Long id) {
        bankService.deleteById(id);

        return new Response(HttpStatus.ACCEPTED.name());
    }
}
