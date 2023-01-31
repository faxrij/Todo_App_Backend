package com.example.demo.controllers.auth;

import com.example.demo.model.request.AuthenticationRequest;
import com.example.demo.model.request.RegisterRequest;
import com.example.demo.model.response.AuthenticationResponse;
import com.example.demo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request
    ) {
        log.info(request.getEmail() + " is being registered");
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        log.info(request.getEmail() + " is being searched");
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
