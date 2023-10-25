package store.polyfood.thuctap.models.verification;


import jakarta.persistence.*;
import lombok.Data;
import store.polyfood.thuctap.models.entities.Account;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;


    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    private Date expiryDate;

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(expiryTimeInMinutes);
        return Date.from(expiryTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
