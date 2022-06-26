package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.time.LocalDate;


/**
 * This class stores data about payment.
 *
 * Linked with:
 * 1. Customer with cardinality * - 1
 * 2. Order with cardinality * - 1
 */
public class Payment extends ObjectPlusPlus
{
    private int totalPrice;
    private LocalDate paymentDate;

    public Payment(int totalPrice, LocalDate paymentDate)
    {
        this.totalPrice = totalPrice;
        this.paymentDate = paymentDate;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString()
    {
        return getTotalPrice() + ", payment Date: " + getPaymentDate();
    }

}