package onlineRestaurantSystem.entities;

import onlineRestaurantSystem.enums.ProblemStatus;
import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.time.LocalDate;


/**
 * This class stores data about notification.
 *
 * Linked with:
 * 1. Customer with cardinality 0..* - 1
 * 2. Support with cardinality 0..* - 1
 */
public class Problem extends ObjectPlusPlus
{
    private String title;
    private String description;
    private LocalDate creationDate;
    private ProblemStatus problemStatus;

    public Problem(String title, String description, LocalDate creationDate, ProblemStatus problemStatus)
    {
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.problemStatus = problemStatus;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public ProblemStatus getNotificationStatus() {
        return problemStatus;
    }
    public void setNotificationStatus(ProblemStatus problemStatus) {
        this.problemStatus = problemStatus;
    }

    @Override
    public String toString()
    {
        return "Title: " + getTitle() + ", created at: " + getCreationDate() + ", status: " + getNotificationStatus() + "\n"
                + getDescription();
    }

}