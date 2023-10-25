package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class VoucherUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucheruserid")
    private int voucherUserId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @Column(name = "voucher_id", insertable = false, updatable = false)
    private int voucherId;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @ManyToOne()
    @JoinColumn(name = "voucher_id")
    @JsonBackReference
    private Voucher voucher;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
