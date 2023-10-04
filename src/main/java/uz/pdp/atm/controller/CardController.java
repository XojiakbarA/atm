package uz.pdp.atm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.pdp.atm.dto.request.CardRequest;
import uz.pdp.atm.dto.response.Response;
import uz.pdp.atm.dto.view.CardView;
import uz.pdp.atm.marker.OnCreate;
import uz.pdp.atm.service.CardService;

@Validated
@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Response getAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        Page<CardView> cards = cardService.getAll(PageRequest.of(page, size));

        return new Response(HttpStatus.OK.name(), cards);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response getById(@PathVariable Long id) {
        CardView bank = cardService.getById(id);

        return new Response(HttpStatus.OK.name(), bank);
    }

    @Validated(OnCreate.class)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@Valid @RequestBody CardRequest request) {
        CardView bank = cardService.create(request);

        return new Response(HttpStatus.CREATED.name(), bank);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response updateById(@Valid @RequestBody CardRequest request, @PathVariable Long id) {
        CardView bank = cardService.update(request, id);

        return new Response(HttpStatus.OK.name(), bank);
    }

    @PutMapping("/{cardId}/bank/{bankId}")
    @ResponseStatus(HttpStatus.OK)
    public Response setBank(@PathVariable Long cardId, @PathVariable Long bankId) {
        CardView bank = cardService.setBank(cardId, bankId);

        return new Response(HttpStatus.OK.name(), bank);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Response deleteById(@PathVariable Long id) {
        cardService.deleteById(id);

        return new Response(HttpStatus.ACCEPTED.name());
    }
}
