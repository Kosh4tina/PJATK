package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.enums.FormOfEmployment;
import java.time.LocalDate;


/**
 * This is abstract class, which stores data about employee.
 * Class created to inherit data from here.
 *
 * Inherits from Person
 */
public abstract class Employee extends Person
{
    private LocalDate employmentDate;
    private int salary;
    private FormOfEmployment formOfEmployment;

    public Employee(String name, String surname, Address address,
                    LocalDate employmentDate, int salary, FormOfEmployment formOfEmployment) throws Exception
    {
        super(name, surname, address);
        this.employmentDate = employmentDate;
        this.salary = salary;
        setFormOfEmployment(formOfEmployment);
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }
    public void setEmploymentDate(LocalDate employmentDate) {
        this.employmentDate = employmentDate;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    public FormOfEmployment getFormOfEmployment() {
        return formOfEmployment;
    }

    // set form of employment from available forms of employment
    public void setFormOfEmployment(FormOfEmployment formOfEmployment) throws Exception
    {
        if (formOfEmployment.equals(FormOfEmployment.UOP) || formOfEmployment.equals(FormOfEmployment.UZ))
            this.formOfEmployment = formOfEmployment;
        else
            throw new Exception("There is no such form of employment!");
    }

    @Override
    public String toString()
    {
        String personToString = super.toString();

        return personToString + "\n"
                + "hired in: " + getEmploymentDate() + ", salary: " + getSalary() + ", form of employment: " + getFormOfEmployment();
    }

    public int calculateSalary()
    {
        return getSalary();
    }

}
