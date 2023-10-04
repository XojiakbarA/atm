package uz.pdp.atm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.atm.dto.request.BanknoteRequest;
import uz.pdp.atm.dto.response.Response;
import uz.pdp.atm.dto.view.BanknoteView;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.service.BanknoteService;

@Validated
@RestController
@RequestMapping("/banknotes")
public class BanknoteController {
    @Autowired
    private BanknoteService banknoteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<BanknoteView> banknotes = banknoteService.getAll(PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), banknotes);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getById(@PathVariable Long id) {
        BanknoteView banknote = banknoteService.getById(id);

        return new Response(HttpStatus.OK.name(), banknote);
    }

    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody BanknoteRequest request) {
        BanknoteView banknote = banknoteService.create(request);

        return new Response(HttpStatus.CREATED.name(), banknote);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateById(@Valid @RequestBody BanknoteRequest request, @PathVariable Long id) {
        BanknoteView banknote = banknoteService.update(request, id);

        return new Response(HttpStatus.OK.name(), banknote);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteById(@PathVariable Long id) {
        banknoteService.deleteById(id);

        return new Response(HttpStatus.ACCEPTED.name());
    }
}
