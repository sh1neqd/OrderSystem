package AlexBogdan.OrderSystem.repositories;

import AlexBogdan.OrderSystem.models.OrderTaken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTakenRepository extends CrudRepository<OrderTaken, Long> {
    public OrderTaken findByOrderId(Long orderId);
}
