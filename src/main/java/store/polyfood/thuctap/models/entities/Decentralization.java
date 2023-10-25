package store.polyfood.thuctap.models.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import store.polyfood.thuctap.models.entities.Account;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "Decentralization")
public class Decentralization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int decentralizationId;

    @Column
    private String authorityName;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "decentralization", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts;

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public int getDecentralizationId() {
        return decentralizationId;
    }

    public void setDecentralizationId(int decentralizationId) {
        this.decentralizationId = decentralizationId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
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
