package uz.pdp.atm.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uz.pdp.atm.dto.request.LoginRequest;
import uz.pdp.atm.dto.response.AuthResponse;
import uz.pdp.atm.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);

        return new AuthResponse(token, HttpStatus.OK.name());
    }
}
