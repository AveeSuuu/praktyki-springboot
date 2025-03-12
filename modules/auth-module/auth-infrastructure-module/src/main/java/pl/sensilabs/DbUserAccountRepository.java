package pl.sensilabs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DbUserAccountRepository implements UserAccountRepository {

  private final JpaUserAccountRepository jpaUserAccountRepository;

  @Override
  public UserAccount loadUserByUsername(String username) {
    return jpaUserAccountRepository.findByUsername(username);
  }

  @Override
  public UserAccount saveUserDetails(UserAccount userAccount) {
    return jpaUserAccountRepository.save(userAccount);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return jpaUserAccountRepository.existsByUsername(username);
  }
}
