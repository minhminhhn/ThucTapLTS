package store.polyfood.thuctap.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import store.polyfood.thuctap.models.entities.Orders;

import java.util.Set;

@Entity
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int orderStatusId;
    @Column
    private String statusName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderStatus")
    @JsonManagedReference
    private Set<Orders> listOrders;


    public Set<Orders> getListOrders() {
        return listOrders;
    }

    public void setListOrders(Set<Orders> listOrders) {
        this.listOrders = listOrders;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
