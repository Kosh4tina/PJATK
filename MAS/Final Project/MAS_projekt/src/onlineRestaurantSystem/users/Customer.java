package onlineRestaurantSystem.users;

import onlineRestaurantSystem.entities.Address;
import onlineRestaurantSystem.entities.Problem;
import onlineRestaurantSystem.entities.OrderOnline;
import onlineRestaurantSystem.entities.Payment;
import onlineRestaurantSystem.enums.ProblemStatus;
import onlineRestaurantSystem.enums.OrderStatus;
import onlineRestaurantSystem.utils.ObjectPlusPlus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * This class stores data about customer.
 *
 * Inherits from Person

 * Linked with:
 * 1. Problem with cardinality 1 - 0..*
 * 2. Credit Card with cardinality 1 - 0..1
 * 3. Payment with cardinality 1 - *
 */
public class Customer extends Person
{
    private String phoneNumber;
    private String emailAddress;
    private static Map<String, CreditCard> creditCardMap = new HashMap<>(); // map for storing credit cards with unique card numbers

    public Customer(String name, String surname, Address address,
                    String phoneNumber, String emailAddress)
    {
        super(name, surname, address);
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public String toString()
    {
        String personToString = super.toString();

        return personToString + "\n"
                + "phone number: " + getPhoneNumber() + ", email address: " + getEmailAddress();
    }

    public OrderOnline createOrder(OrderOnline orderOnlineToCreate, LocalDate localDate, OrderStatus orderStatus) throws Exception
    {
        orderOnlineToCreate = new OrderOnline(localDate, orderStatus);
        addLinkOrder(orderOnlineToCreate);
        return orderOnlineToCreate;
    }

    public Problem createProblem(String title, String description)
    {
        Problem problem = new Problem(title, description, LocalDate.now(), ProblemStatus.Opened);
        addLinkProblem(problem);
        return problem;
    }

    public void addLinkOrder(OrderOnline orderOnline)
    {
        this.addLink("sklada","jest skladane", orderOnline);
    }

    public void addLinkPayment(Payment payment)
    {
        this.addLink("pays", "is paid", payment);
    }

    public void addLinkProblem(Problem problem)
    {
        this.addLink("opens", "is opened", problem);
    }

    public void addCreditCard(String cardNumber, int codeCVC, String expirationDate) throws Exception
    {
        CreditCard creditCard = new CreditCard(cardNumber, codeCVC, expirationDate);
        this.addPart("has got", "is in possesion", creditCard);
    }

    public boolean hasCreditCard()
    {
        return !this.isLink("has got");
    }

    public String returnCreditCardNumber() throws Exception
    {
        ArrayList<CreditCard> creditCards = returnLinks("has got");

        return creditCards.get(0).getCardNumber();
    }

    public String returnCreditCardExpirationDate() throws Exception
    {
        ArrayList<CreditCard> creditCards = returnLinks("has got");

        return creditCards.get(0).getExpirationDate();
    }

    public int returnCreditCardCodeCVC() throws Exception
    {
        ArrayList<CreditCard> creditCards = returnLinks("has got");

        return creditCards.get(0).getCodeCVC();
    }

    /**
     * This class stored data about credit card.
     * The class inner class of customer.
     *
     * Linked with:
     * 1. Customer with cardinality 0..1 - 1
     */
    class CreditCard extends ObjectPlusPlus
    {
        private String cardNumber; // unique
        private int codeCVC;
        private String expirationDate;

        private CreditCard(String cardNumber, int codeCVC, String expirationDate)
        {
            this.cardNumber = cardNumber;
            this.codeCVC = codeCVC;
            this.expirationDate = expirationDate;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        // set unique value of credit card number
        public void setCardNumber(String cardNumber) throws Exception
        {
            if (!creditCardMap.containsKey(cardNumber))
            {
                if (String.valueOf(cardNumber).length() == 16)
                {
                    creditCardMap.put(cardNumber, this);
                    this.cardNumber = cardNumber;
                }
                else
                    throw new Exception("card number must have 16 digits!");
            }
            else
                throw new Exception("Card with this number already exists!");
        }

        public int getCodeCVC() {
            return codeCVC;
        }
        public void setCodeCVC(int codeCVC) {
            this.codeCVC = codeCVC;
        }

        public String getExpirationDate() {
            return expirationDate;
        }
        public void setExpirationDate(String expirationDate) {
            this.expirationDate = expirationDate;
        }

        @Override
        public String toString()
        {
            return cardNumber + ", CVC: " + codeCVC + ", expiration date: " + expirationDate;
        }
    }

}