package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.OrderStatus;
import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This abstract class stores data about order.
 *
 * Linked with:
 * 1. Customer with cardinality 0..* - 0..1
 * 2. Payment with cardinality 1 - *
 * 3. Dish list with cardinality 0..1 - 1..*
 * 4. Delivery with cardinality 1 - *
 */
public abstract class Order extends ObjectPlusPlus {
    String orderNumber; // unique
    LocalDate orderCreationDate;
    int finalPrice;
    OrderStatus orderStatus;

    public Order(LocalDate orderCreationDate, OrderStatus orderStatus) throws Exception
    {
        this.orderNumber = UUID.randomUUID().toString();
        this.orderCreationDate = orderCreationDate;
        setFinalPrice(finalPrice);
        setOrderStatus(orderStatus);
    }

    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public LocalDate getOrderCreationDate() {
        return orderCreationDate;
    }
    public void setOrderCreationDate(LocalDate orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    public int getFinalPrice() {
        return finalPrice;
    }
    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    // set order status from available order statuses
    public void setOrderStatus(OrderStatus orderStatus) throws Exception
    {
        if (orderStatus.equals(OrderStatus.Created) || orderStatus.equals(OrderStatus.Paid) || orderStatus.equals(OrderStatus.InProgress) || orderStatus.equals(OrderStatus.Completed))
            this.orderStatus = orderStatus;
        else
            throw new Exception("There is no such order status!");
    }

    @Override
    public String toString()
    {
        return getOrderNumber() + ", creation date: " + getOrderCreationDate() + ", status: " + getOrderStatus();
    }
}
