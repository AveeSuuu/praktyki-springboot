package pl.sensilabs;

public interface UserAccountRepository {
  UserAccount loadUserByUsername(String username);
  UserAccount saveUserDetails(UserAccount userAccount);
  Boolean existsByUsername(String username);
}