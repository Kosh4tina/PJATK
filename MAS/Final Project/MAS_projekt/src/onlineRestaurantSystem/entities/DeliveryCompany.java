package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.utils.ObjectPlusPlus;


/**
 * This class stores data about publishing company.
 *
 * Linked with:
 * 1. Delivery with cardinality 1 - 1..*
 */
public class DeliveryCompany extends ObjectPlusPlus
{
    private String name;
    private Address address;

    public DeliveryCompany(String name, Address address)
    {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return getName() + "\n"
                + "Address: " + getAddress().toString();
    }

}