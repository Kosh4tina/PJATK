package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.entities.OrderOnline;
import onlineRestaurantSystem.enums.FormOfEmployment;
import java.time.LocalDate;
import java.util.List;


/**
 * This class stores data about cashier.
 *
 * Inherits from Employee
 *
 * Linked with:
 * 1. OrderOnPlace with cardinality 1 - *
 */

public class Cashier extends Employee
{
    private List<String> roles;

    public Cashier(String name, String surname, Address address,
                   LocalDate employmentDate, int baseSalary, FormOfEmployment formOfEmployment,
                   List<String> roles) throws Exception
    {
        super(name, surname, address, employmentDate, baseSalary, formOfEmployment);
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString()
    {
        String employeeToString = super.toString();

        return employeeToString + "\n"
                + "roles: " + getRoles();
    }

    /**
     * This method adds role to the roles list
     *
     * @param newRole
     * @throws Exception
     */
    public void addRole(String newRole) throws Exception
    {
        if (!roles.contains(newRole))
            roles.add(newRole);
        else
            throw new Exception("This cashier has already got such role!");
    }

    public void proceedOrder(OrderOnline orderOnline)
    {
        //TODO Functionality of proceedOrder method for Cashier class
    }

    public int calculateSalary()
    {
        int bonusToSalary = getRoles().size() * 500;
        return getSalary() + bonusToSalary;
    }

}