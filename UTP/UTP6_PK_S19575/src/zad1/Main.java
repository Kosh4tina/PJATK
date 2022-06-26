/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


public class Main {

  public static void main(String ... args) throws Exception  {
    String fname  = System.getProperty("user.home")+"/Test.java";
    Finder finder = new Finder(fname);
    int nif = finder.getIfCount();
    int nwar = finder.getStringCount("wariant");
    System.out.println("Liczba instrukcji if: " + nif);
    System.out.println("Liczba napisow wariant: " + nwar);
  }

}
