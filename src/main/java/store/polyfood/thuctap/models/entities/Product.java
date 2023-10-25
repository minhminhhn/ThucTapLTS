package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int productId;
    @Column(name = "product_type_id", updatable = false, insertable = false)
    private int productTypeId;
    @Column
    private String productName;
    @Column
    private double price;
    @Column
    private String avartarImageProduct;
    @Column
    private String title;
    @Column
    private int discount;
    @Column
    private int status;
    @Column
    private int numberOfView;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    @JsonBackReference
    private ProductType productType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonManagedReference
    private Set<ProductImage> productImages;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonManagedReference
    private Set<ProductReview> productReviews;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    @JsonManagedReference
    private Set<CartItem> cartItems;


    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Set<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Set<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public Set<ProductReview> getProductReviews() {
        return productReviews;
    }

    public void setProductReviews(Set<ProductReview> productReviews) {
        this.productReviews = productReviews;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(int productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvartarImageProduct() {
        return avartarImageProduct;
    }

    public void setAvartarImageProduct(String avartarImageProduct) {
        this.avartarImageProduct = avartarImageProduct;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberOfView() {
        return numberOfView;
    }

    public void setNumberOfView(int numberOfView) {
        this.numberOfView = numberOfView;
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
