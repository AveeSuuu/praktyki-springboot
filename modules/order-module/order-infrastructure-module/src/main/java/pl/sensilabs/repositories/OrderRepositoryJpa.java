package pl.sensilabs.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sensilabs.OrderEntity;

@Repository
public interface OrderRepositoryJpa extends JpaRepository<OrderEntity, UUID> {

}
