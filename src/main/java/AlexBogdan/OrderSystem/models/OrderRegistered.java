package AlexBogdan.OrderSystem.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Duration;

@Entity
@Table(name = "registeredOrders")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class OrderRegistered extends OrderEvent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "client_id")
    Long clientId;

    @Column(name = "worker_id")
    Integer workerId;

    @Column(name = "item_id")
    Long itemId;

    @Column(name = "issue_time")
    Duration issueTime;

    @Column(name = "timestamp")
    Timestamp timestamp;
}
