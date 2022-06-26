package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.DeliveryStatus;
import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.time.LocalDate;

/**
 * This class stores data about delivery.
 *
 * Linked with:
 * 1. Order with cardinality * - 1
 */
public class Delivery extends ObjectPlusPlus
{
    private Address deliveryAddress;
    private LocalDate postingDate;
    private LocalDate expectedDeliveryDate;
    private DeliveryStatus deliveryStatus;

    public Delivery(Address deliveryAddress, LocalDate postingDate, LocalDate expectedDeliveryDate, DeliveryStatus deliveryStatus) throws Exception
    {
        this.deliveryAddress = deliveryAddress;
        this.postingDate = postingDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        setDeliveryStatus(deliveryStatus);
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public LocalDate getPostingDate() {
        return postingDate;
    }
    public void setPostingDate(LocalDate postingDate) {
        this.postingDate = postingDate;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }
    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) throws Exception
    {
        if (deliveryStatus.equals(DeliveryStatus.Sent) || deliveryStatus.equals(DeliveryStatus.Delivered))
            this.deliveryStatus = deliveryStatus;
        else
            throw new Exception("There is no such delivery status!");
    }

    @Override
    public String toString()
    {
        return getDeliveryAddress() + "\n"
                + "date of posting: " + getPostingDate() + ", expected delivery date: " + getExpectedDeliveryDate() + ", status: " + getDeliveryStatus();
    }

}
