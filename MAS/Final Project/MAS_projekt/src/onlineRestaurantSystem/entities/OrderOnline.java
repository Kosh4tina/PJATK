package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.OrderStatus;
import java.time.LocalDate;
import java.util.UUID;

/**
 * This class stores data about online orders.
 *
 * Inherits from Order

 * Linked with:
 * 1. Cashier with cardinality 1 - 0..*
 * 2. Customer with cardinality 1 - 0..1
 * 3. Delivery with cardinality 1 - *
 */

public class OrderOnline extends Order
{
    public OrderOnline(LocalDate orderCreationDate, OrderStatus orderStatus) throws Exception
    {
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

    public void addLinkDishCopy(DishList dishList)
    {
        this.addLink("includes","is included", dishList);
    }

    public void addLinkPayment(Payment payment)
    {
        this.addLink("dotyczy", "zakonczone jest", payment);
    }

}