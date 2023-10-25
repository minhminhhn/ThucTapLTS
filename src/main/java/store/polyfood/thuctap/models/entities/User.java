package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int userId;
    @Column
    private String fullName;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String address;
    @Column(name = "account_id",updatable = false, insertable = false)
    private int accountId;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "account_id")
    private Account account;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private Set<Orders> listOrders;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private Set<Carts> carts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private Set<ProductReview> productReviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private Set<VoucherUser> voucherUsers;

    public Set<VoucherUser> getVoucherUsers() {
        return voucherUsers;
    }

    public void setVoucherUsers(Set<VoucherUser> voucherUsers) {
        this.voucherUsers = voucherUsers;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public Set<Carts> getCarts() {
        return carts;
    }

    public void setCarts(Set<Carts> carts) {
        this.carts = carts;
    }

    public Set<Orders> getListOrders() {
        return listOrders;
    }

    public void setListOrders(Set<Orders> listOrders) {
        this.listOrders = listOrders;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
