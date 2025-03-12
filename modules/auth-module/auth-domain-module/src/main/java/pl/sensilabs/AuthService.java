package pl.sensilabs;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final BCryptPasswordEncoder encoder;
  private final UserAccountRepository userAccountRepository;
  private final JwtUtils jwtUtils;

  public String authenticateUserRequest(AuthRequest request) {
    final var user = userAccountRepository.loadUserByUsername(request.username());
    if (user == null) {
      throw new UserNotFoundException(request.username());
    }

    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        request.username(), request.password()
    ));

    return jwtUtils.generateToken(user);
  }

  public UserAccount registerNewUser(AuthRequest request) {
    if (Boolean.TRUE.equals(userAccountRepository.existsByUsername(request.username()))) {
      throw new UserAlreadyExistsException(request.username());
    }

    var userdata = new UserAccount(
        request.username(),
        encoder.encode(request.password()));

    return userAccountRepository.saveUserDetails(userdata);
  }
}

