package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.utils.ObjectPlusPlus;


/**
 * This is abstract class, which stores data about person.
 * Class created to inherit data from here.
 *
 * Linked with:
 * 1. Address with cardinality 1 - 1
 */
public abstract class Person extends ObjectPlusPlus
{
    private String name;
    private String surname;
    private Address address;

    public Person(String name, String surname, Address address)
    {
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
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
        return getName() + " " + getSurname() + "\n"
                + "Address: " + getAddress().toString();
    }

}
