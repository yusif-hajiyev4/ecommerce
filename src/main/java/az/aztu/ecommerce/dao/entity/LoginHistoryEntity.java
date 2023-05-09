package az.aztu.ecommerce.dao.entity;

import az.aztu.ecommerce.model.enums.LogType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@Table(name = "login_history")
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity account;

    @Enumerated(value = EnumType.STRING)
    private LogType type;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
