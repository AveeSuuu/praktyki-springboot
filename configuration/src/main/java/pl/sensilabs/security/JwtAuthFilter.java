package pl.sensilabs.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final UserAccountRepository userAccountRepository;
  private final JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt = authorizationHeader.substring(7);
    final String username;

    try {
      username = jwtUtils.extractUsername(jwt);
    } catch (Exception e) {
      log.info("Invalid JWT token: {}", jwt);
      filterChain.doFilter(request, response);
      return;
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var userDetails = userAccountRepository.loadUserByUsername(username);
      final boolean isTokenValid = jwtUtils.isTokenValid(jwt, userDetails);

      log.info("Sprawdzenie tokena: username={}, valid={}", username, isTokenValid);

      if (isTokenValid) {
        var authToken = new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}

