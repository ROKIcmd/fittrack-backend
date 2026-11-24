package com.fittrack.fittrack.controller;

import com.fittrack.fittrack.model.Member;
import com.fittrack.fittrack.model.User;
import com.fittrack.fittrack.repository.DataStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    public record LoginRequest(String email, String password, String role) {}
    public record RegisterRequest(String firstName, String lastName, String email, String password) {}
    public record AuthResponse(String id, String fullName, String email, String role) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var found = DataStore.getInstance().users().findByEmail(req.email());
        if (found.isEmpty()
                || !found.get().verifyPassword(req.password())
                || !found.get().getRole().equalsIgnoreCase(req.role())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email, password, or role"));
        }
        return ResponseEntity.ok(toResponse(found.get()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        var users = DataStore.getInstance().users();
        if (users.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered"));
        }
        String id = "M" + UUID.randomUUID().toString().substring(0, 8);
        Member member = new Member(id, req.firstName(), req.lastName(), req.email(), req.password(),
                null, "", "", 0.0, 0.0);
        users.save(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(member));
    }

    private AuthResponse toResponse(User u) {
        return new AuthResponse(u.getId(), u.getFullName(), u.getEmail(), u.getRole());
    }
}