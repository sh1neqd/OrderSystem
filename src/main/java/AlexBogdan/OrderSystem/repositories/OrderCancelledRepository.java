package AlexBogdan.OrderSystem.repositories;

import AlexBogdan.OrderSystem.models.Order;
import AlexBogdan.OrderSystem.models.OrderCancelled;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCancelledRepository extends CrudRepository<OrderCancelled, Long> {
    public OrderCancelled findByOrderId(Long orderId);
}
