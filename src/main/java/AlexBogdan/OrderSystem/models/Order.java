package AlexBogdan.OrderSystem.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "orders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor()
public class Order {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "client_id")
    Long clientId; // в будущем можно реализовать таблицу clients

    @Column(name = "worker_id")
    Integer workerId; // в будущем можно реализовать таблицу workers

    @Column(name = "item_id")
    Long itemId; // в будущем можно реализовать таблицу items

    @NotNull
    @Column(name = "status")
    Integer status;

}
