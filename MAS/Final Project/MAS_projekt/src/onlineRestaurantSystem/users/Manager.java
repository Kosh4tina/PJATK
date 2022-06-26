package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.entities.Problem;
import onlineRestaurantSystem.enums.FormOfEmployment;
import onlineRestaurantSystem.enums.ProblemStatus;
import java.time.LocalDate;


/**
 * This class stores data about manager worker.
 * Manager worker can support problems created by customer
 *
 * Inherits from Employee
 *
 * Linked with:
 * 1. Problem with cardinality 1 - 0..*
 */
public class Manager extends Employee
{

    public Manager(String name, String surname, Address address,
                   LocalDate employmentDate, int baseSalary, FormOfEmployment formOfEmployment) throws Exception
    {
        super(name, surname, address, employmentDate, baseSalary, formOfEmployment);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }

    public void proceedProblem(Problem problem) throws Exception
    {
        if (problem.getNotificationStatus().equals(ProblemStatus.Opened))
        {
            System.out.println("Problem: " + problem.getTitle() + " has been proceeded");
            problem.setNotificationStatus(ProblemStatus.Closed);
        }
        else
            throw new Exception("This problem has been already closed!");
    }

}