package pl.sensilabs.security;

public record AuthRequest(
    String username,
    String password
) {

}
