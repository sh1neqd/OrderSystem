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
@Schema(description = "Регистрация заказа")
public class OrderRegistrationDto {
    @Schema(description = "Идентификатор клиента", example = "5135")
    Long clientId;
    @Schema(description = "Идентификатор товара", example = "1313")
    Long itemId;
}
