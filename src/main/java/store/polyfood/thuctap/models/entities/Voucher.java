package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voucherId;

    @Column(name = "vouchername")
    private String voucherName;
    @Column(name = "vouchercode")
    private String voucherCode;
    @Column(name = "voucherValue")
    private String voucherValue;
    @Column(name = "expirationdate")
    private LocalDateTime expirationDate;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    private int countVoucher;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "voucher")
    @JsonManagedReference
    private Set<VoucherUser> voucherUsers;

}
