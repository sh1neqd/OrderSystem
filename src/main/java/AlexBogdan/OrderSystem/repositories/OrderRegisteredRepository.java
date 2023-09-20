package AlexBogdan.OrderSystem.repositories;

import AlexBogdan.OrderSystem.models.OrderRegistered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRegisteredRepository extends CrudRepository<OrderRegistered, Long> {
    public OrderRegistered findByOrderId(Long orderId);
}
