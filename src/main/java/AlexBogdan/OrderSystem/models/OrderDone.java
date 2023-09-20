package AlexBogdan.OrderSystem.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "doneOrders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor

public class OrderDone extends OrderEvent{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "order_id")
    Long orderId;
    @Column(name = "worker_id")
    Integer workerId;
    @Column(name = "timestamp")
    Timestamp timestamp;
}
