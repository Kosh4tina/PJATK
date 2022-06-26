/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad3;


import javax.swing.*;
// zadanie: spowodować, by po naciśnięciu klawisza myszki na przycisku, 
// na konsoli zostało wypisane "ok". Plik Main.java jest niemodyfikowalny.
public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater( ()-> {
        JFrame f = new JFrame();
        JButton b = new JButton("Myszą ciśnij");
        b.addMouseListener ( (MousePressListener) e -> System.out.println("ok") );
        f.add(b);
        f.pack();
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setVisible(true);
      }
    );
  }
}