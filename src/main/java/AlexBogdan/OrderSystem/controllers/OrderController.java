package AlexBogdan.OrderSystem.controllers;

import AlexBogdan.OrderSystem.exceptions.OrderException;
import AlexBogdan.OrderSystem.exceptions.OrderNotCreatedException;
import AlexBogdan.OrderSystem.models.Order;
import AlexBogdan.OrderSystem.models.dtos.OrderCancelDto;
import AlexBogdan.OrderSystem.models.dtos.OrderInfoDto;
import AlexBogdan.OrderSystem.models.dtos.OrderRegistrationDto;
import AlexBogdan.OrderSystem.services.EventsService;
import AlexBogdan.OrderSystem.services.OrderService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@OpenAPIDefinition(info = @Info(
        title = "Order system"),
        servers = {@Server(url = "http://localhost:7071", description = "Local Server")})
@Tag(name = "Основной контроллер")
@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final EventsService eventsService;

    @PostMapping("/makeorder")
    public ResponseEntity<HttpStatus> registerOrder(@RequestBody @Valid OrderRegistrationDto orderRegistrationDto,
                                                    BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            responseError(bindingResult);
        } else {
            Order order = convertFromDto(orderRegistrationDto);
            orderService.registerOrder(order);
            eventsService.orderIsRegistered(order);
            eventsService.setOrderTakenRepository(order);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @GetMapping("/setorderready/{id}")
    public ResponseEntity<HttpStatus> setOrderReady(@PathVariable("id") Long id) {
        try {
            if(orderService.findOrder(id).getStatus().equals(3)) makeOrderReady(orderService.findOrder(id));
            else throw new OrderException("order is not taken or already done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/cancelorder/{id}")
    public ResponseEntity<HttpStatus> cancelOrder(@PathVariable("id") @RequestBody @Valid
                                                      Long id, OrderCancelDto orderCancelDto,
                                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            responseError(bindingResult);
        } else {
            Order order = orderService.findOrder(id);
            eventsService.setOrderCancelledRepository(order, orderCancelDto.getCancellationReason());
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getyourorder/{id}")
    public ResponseEntity<HttpStatus> getOrder(@PathVariable("id") Long id) {
       eventsService.setOrderDoneRepository(orderService.findOrder(id));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getorder/{id}")
    public OrderInfoDto getOrderInfo(@PathVariable("id") Long id) {
        Order order = orderService.findOrder(id);
        OrderInfoDto orderInfoDto = convertToDto(order);
        orderInfoDto.setOrderEventList(eventsService.getEvents(order));
        return orderInfoDto;
    }

    private Order convertFromDto(OrderRegistrationDto orderRegistrationDto) {
        Order order = new Order();
        order.setClientId(orderRegistrationDto.getClientId());
        order.setItemId(orderRegistrationDto.getItemId());
        return order;
    }

    private OrderInfoDto convertToDto(Order order) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(order, OrderInfoDto.class);
    }

    private void makeOrderReady(Order order) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                eventsService.setOrderReadyRepository(order);
            }
        });
        long delay  = eventsService.getIssueTime(order).toMillis();
        Thread.sleep(delay);
        thread.start();
        thread.interrupt();
        thread.join();
    }

    private void responseError(BindingResult bindingResult) {
        StringBuilder errorMsg = new StringBuilder();

        List<FieldError> errors = bindingResult.getFieldErrors();

        for(FieldError error:errors) {
            errorMsg.append(error.getField()).append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        throw new OrderNotCreatedException(errorMsg.toString());
    }
}
