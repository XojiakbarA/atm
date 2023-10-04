package uz.pdp.atm.service;

import uz.pdp.atm.dto.request.LoginRequest;

public interface AuthService {
    String login(LoginRequest request);
}
