package AlexBogdan.OrderSystem.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Schema(description = "Отмена заказа")
public class OrderCancelDto {
    @Schema(description = "Идентификатор заказа", example = "13")
    Long orderId;
    @Schema(description = "причина отмены", example = "ресторан закрывается")
    String cancellationReason;
}
