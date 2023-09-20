package AlexBogdan.OrderSystem.services;

import AlexBogdan.OrderSystem.exceptions.OrderException;
import AlexBogdan.OrderSystem.exceptions.OrderNotCreatedException;
import AlexBogdan.OrderSystem.models.*;
import AlexBogdan.OrderSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventsService {
    private final OrderCancelledRepository orderCancelledRepository;
    private final OrderDoneRepository orderDoneRepository;
    private final OrderReadyRepository orderReadyRepository;
    private final OrderRegisteredRepository orderRegisteredRepository;
    private final OrderTakenRepository orderTakenRepository;
    private final OrderService orderService;
    @Transactional
    public void orderIsRegistered(Order order) {
        OrderRegistered orderRegistered = new OrderRegistered();
        orderRegistered.setOrderId(order.getId());
        orderRegistered.setClientId(order.getClientId());
        orderRegistered.setItemId(order.getItemId());
        orderRegistered.setWorkerId(order.getWorkerId());
        orderRegistered.setIssueTime(Duration.ofMinutes(1));
        orderRegistered.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        orderRegisteredRepository.save(orderRegistered);
    }

    @Transactional
    public void setOrderCancelledRepository(Order order, String cancellationReason) {
        if(!order.getStatus().equals(2) && !order.getStatus().equals(5) && !order.getStatus().equals(4)) {
        OrderCancelled orderCancelled = new OrderCancelled();
        orderService.updateOrder(order, 2);
        orderCancelled.setOrderId(order.getId());
        orderCancelled.setWorkerId(order.getWorkerId());
        orderCancelled.setCancellationReason(cancellationReason);
        orderCancelled.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        orderCancelledRepository.save(orderCancelled);
        } else throw new OrderException("order is already cancelled or can't be cancelled");
    }

    @Transactional
    public void setOrderTakenRepository(Order order) {
        if(order.getStatus().equals(1)) {
            OrderTaken orderTaken = new OrderTaken();
            orderService.updateOrder(order, 3);
            orderTaken.setOrderId(order.getId());
            orderTaken.setWorkerId(order.getWorkerId());
            orderTaken.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            orderTakenRepository.save(orderTaken);
        } else throw new OrderException("order is not registered or already ready");
    }

    @Transactional
    public void setOrderReadyRepository(Order order) {
        if(order.getStatus().equals(3)) {
            OrderReady orderReady = new OrderReady();
            orderService.updateOrder(order, 4);
            orderReady.setOrderId(order.getId());
            orderReady.setWorkerId(order.getWorkerId());
            orderReady.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
            orderReadyRepository.save(orderReady);
        } else throw new OrderException("order is not taken or already done");
    }

    @Transactional
    public void setOrderDoneRepository(Order order) {
        if (order.getStatus().equals(4)){
        OrderDone orderDone = new OrderDone();
        orderService.updateOrder(order, 5);
        orderDone.setOrderId(order.getId());
        orderDone.setWorkerId(order.getWorkerId());
        orderDone.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        orderDoneRepository.save(orderDone);}
        else {
            throw new OrderException("order is not ready");
        }
    }

    public List<OrderEvent> getEvents(Order order) {
        Long orderId = order.getId();
        List<OrderEvent> listOfEvents = new ArrayList<>();
        switch(order.getStatus()) {
            case 2: listOfEvents.add(orderRegisteredRepository.findByOrderId(orderId));
                listOfEvents.add(orderCancelledRepository.findByOrderId(orderId));
                break;
            case 3: listOfEvents.add(orderRegisteredRepository.findByOrderId(orderId));
                listOfEvents.add(orderTakenRepository.findByOrderId(orderId));
                break;
            case 4: listOfEvents.add(orderRegisteredRepository.findByOrderId(orderId));
                listOfEvents.add(orderTakenRepository.findByOrderId(orderId));
                listOfEvents.add(orderReadyRepository.findByOrderId(orderId));
                break;
            case 5:
                listOfEvents.add(orderRegisteredRepository.findByOrderId(orderId));
                listOfEvents.add(orderTakenRepository.findByOrderId(orderId));
                listOfEvents.add(orderReadyRepository.findByOrderId(orderId));
                listOfEvents.add(orderDoneRepository.findByOrderId(orderId));
                break;
        }
        return listOfEvents;
    }

    public Duration getIssueTime(Order order) {
        return orderRegisteredRepository.findByOrderId(order.getId()).getIssueTime();
    }

}
