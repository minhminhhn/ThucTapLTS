package store.polyfood.thuctap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import store.polyfood.thuctap.models.entities.Account;
import store.polyfood.thuctap.models.entities.User;
import store.polyfood.thuctap.repositories.AccountRepo;
import store.polyfood.thuctap.repositories.UserRepo;

import javax.security.auth.login.AccountNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ForgotPasswordService implements IForgotPasswordService{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if(user != null) {
            Account account = user.getAccount();
            account.setResetPasswordToken(token);
            account.setResetPasswordTokenExpiry(LocalDateTime.now().plus(Duration.ofMinutes(15)));
            accountRepo.save(account);
        } else {
            throw new UsernameNotFoundException("Could not find any account with the email " + email);
        }
    }

    @Override
    public Account getByResetPasswordToken(String token) {
        return accountRepo.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(Account account, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        account.setPassword(encodedPassword);
        account.setResetPasswordToken(null);
        account.setResetPasswordTokenExpiry(null);

        accountRepo.save(account);
    }
}
