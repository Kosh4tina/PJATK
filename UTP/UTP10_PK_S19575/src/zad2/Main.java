/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad2;


import java.beans.PropertyVetoException;

public class Main {
  public static void main(String[] args) {

    Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
    System.out.println(purch);

    // --- tu należy dodać odpowiedni kod
    purch.addPropertyChangeListener(evt -> {
      if (!evt.getNewValue().equals(evt.getOldValue())) {
        System.out.printf("Change value of: %s from: %s to: %s\n",
                evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
      }
    });

    purch.addVetoableChangeListener(evt -> {
      if (evt.getPropertyName().equals("price")) {
        Double doubleValue = Double.valueOf(evt.getNewValue().toString());
        if (doubleValue < 1000) {
          throw new PropertyVetoException("Price change to: " + doubleValue + " not allowed", evt);
        }
      }
    });
    // ----------------------------------

    try {
      purch.setData("w promocji");
      purch.setPrice(2000.00);
      System.out.println(purch);

      purch.setPrice(500.00);

    } catch (PropertyVetoException exc) {
      System.out.println(exc.getMessage());
    }
    System.out.println(purch);

  }
}
