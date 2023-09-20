package AlexBogdan.OrderSystem.repositories;

import AlexBogdan.OrderSystem.models.Order;
import AlexBogdan.OrderSystem.models.OrderReady;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface OrderReadyRepository extends CrudRepository<OrderReady,Long> {
    public OrderReady findByOrderId(Long orderId);
}
