package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.OrderStatus;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class stores data about orders on place.
 *
 * Inherits from Order

 * Linked with:
 * 1. Cashier with cardinality 1 - 0..*
 * 2. Customer with cardinality 1 - 0..1
 */

public class OrderOnPlace extends Order{

    public OrderOnPlace(LocalDate orderCreationDate, OrderStatus orderStatus) throws Exception {
        super(orderCreationDate, orderStatus);
        orderNumber = UUID.randomUUID().toString();
        setFinalPrice(finalPrice);
    }

    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getFinalPrice() {
        return finalPrice;
    }
    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Override
    public String toString()
    {
        return getOrderNumber() + ", creation date: " + getOrderCreationDate() + ", status: " + getOrderStatus();
    }
}
