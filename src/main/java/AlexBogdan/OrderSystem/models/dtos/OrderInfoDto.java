package AlexBogdan.OrderSystem.models.dtos;

import AlexBogdan.OrderSystem.models.OrderEvent;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderInfoDto {
    Long id;

    Long clientId;

    Long workerId;

    Long itemId;

    Integer status;

    List<OrderEvent> orderEventList;
}
