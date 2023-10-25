package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import store.polyfood.thuctap.models.entities.Product;
import store.polyfood.thuctap.models.entities.User;

import java.time.LocalDateTime;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int productReviewId;
    @Column(name = "product_id", updatable = false, insertable = false)
    private int productId;
    @Column(name = "user_id", updatable = false, insertable = false)
    private int userId;
    @Column
    private String contentRated;
    @Column
    private int pointEvaluation;
    @Column
    private String contentSeen;
    @Column
    private int status;
    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getProductReviewId() {
        return productReviewId;
    }

    public void setProductReviewId(int productReviewId) {
        this.productReviewId = productReviewId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContentRated() {
        return contentRated;
    }

    public void setContentRated(String contentRated) {
        this.contentRated = contentRated;
    }

    public int getPointEvaluation() {
        return pointEvaluation;
    }

    public void setPointEvaluation(int pointEvaluation) {
        this.pointEvaluation = pointEvaluation;
    }

    public String getContentSeen() {
        return contentSeen;
    }

    public void setContentSeen(String contentSeen) {
        this.contentSeen = contentSeen;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
