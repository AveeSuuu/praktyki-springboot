package pl.sensilabs;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUserAccountRepository extends JpaRepository<UserAccount, UUID> {

  UserAccount findByUsername(String username);

  Boolean existsByUsername(String username);
}
