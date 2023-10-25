package store.polyfood.thuctap.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import store.polyfood.thuctap.config.JwtTokenProvider;
import store.polyfood.thuctap.models.requestobject.LoginRequest;
import store.polyfood.thuctap.models.responobject.JwtAuthenticationResponse;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody String loginRequest1) throws UnsupportedEncodingException {
        Gson gson = new Gson();
        LoginRequest loginRequest = gson.fromJson(loginRequest1, LoginRequest.class);
        // Xử lý xác thực thông tin đăng nhập
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String token = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

