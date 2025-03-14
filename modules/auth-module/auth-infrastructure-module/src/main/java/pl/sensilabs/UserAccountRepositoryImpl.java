package pl.sensilabs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserAccountRepositoryImpl implements UserAccountRepository {

  private final UserAccountRepositoryJpa userAccountRepositoryJpa;

  @Override
  public UserAccount loadUserByUsername(String username) {
    return userAccountRepositoryJpa.findByUsername(username);
  }

  @Override
  public UserAccount saveUserDetails(UserAccount userAccount) {
    return userAccountRepositoryJpa.save(userAccount);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userAccountRepositoryJpa.existsByUsername(username);
  }
}
