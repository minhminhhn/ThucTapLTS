package store.polyfood.thuctap.services;

import store.polyfood.thuctap.models.entities.Account;

public interface IForgotPasswordService {
    public void updateResetPasswordToken(String token, String email);
    public Account getByResetPasswordToken(String token);
    public void updatePassword(Account account, String newPassword);
}
