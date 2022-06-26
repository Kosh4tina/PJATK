package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.utils.ObjectPlusPlus;

/**
 * This class stores data about address.
 *
 * Linked with:
 * 1. Person with cardinality 1 - 1
 * 2. DeliveryCompany with cardinality 1 - 1
 */
public class Address extends ObjectPlusPlus
{
    private String street;
    private String buildingNumber;
    private String zipCode;
    private String city;
    private String state;

    public Address(String street, String buildingNumber, String zipCode, String city, String state)
    {
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }

    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }
    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString()
    {
        return getStreet() + " " + getBuildingNumber() + ", " + getZipCode() + ", " + getCity() + ", " + getState();
    }

    public void setAddress(String street, String buildingNumber, String zipCode, String city, String state)
    {
        setStreet(street);
        setBuildingNumber(buildingNumber);
        setZipCode(zipCode);
        setCity(city);
        setState(state);
    }

}