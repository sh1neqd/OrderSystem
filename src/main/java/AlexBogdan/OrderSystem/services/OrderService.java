package AlexBogdan.OrderSystem.services;

import AlexBogdan.OrderSystem.exceptions.OrderNotCreatedException;
import AlexBogdan.OrderSystem.models.Order;
import AlexBogdan.OrderSystem.models.OrderEvent;
import AlexBogdan.OrderSystem.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class OrderService{
    private final OrderRepository orderRepository;


    public Order findOrder(Long id) {
        Optional<Order> foundOrder = orderRepository.findById(id);
        return foundOrder.orElse(null);
    }

    @Transactional
    public void registerOrder(Order order) {
        if (order.getStatus()==null) {
            Random random = new Random();
            order.setStatus(1);
            order.setClientId(order.getClientId());
            order.setItemId(order.getItemId());
            order.setWorkerId(random.nextInt());
            orderRepository.save(order);
        } else throw new OrderNotCreatedException("order is already registered");
    }

    public void updateOrder(Order order, Integer status) {
        order.setStatus(status);
        orderRepository.save(order);
    };



}
