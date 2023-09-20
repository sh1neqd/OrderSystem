package AlexBogdan.OrderSystem.repositories;

import AlexBogdan.OrderSystem.models.OrderDone;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDoneRepository extends CrudRepository<OrderDone, Long> {
    public OrderDone findByOrderId(Long orderId);
}
