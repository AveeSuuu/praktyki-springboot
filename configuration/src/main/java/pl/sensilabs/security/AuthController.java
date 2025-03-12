package pl.sensilabs.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @GetMapping
  public ResponseEntity<String> authenticate(@RequestBody AuthRequest request) {
    log.info("Received request to authenticate user: {}", request);
    return ResponseEntity.ok(authService.authenticateUserRequest(request));
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerNewUser(@RequestBody AuthRequest request) {
    log.info("Received request to register new user: {}", request);
    return ResponseEntity.accepted().build();
  }
}