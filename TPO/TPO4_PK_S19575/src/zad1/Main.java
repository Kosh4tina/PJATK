/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


public class Main {

  public static void main(String[] args) throws Exception {
    String fileName = System.getProperty("user.home") + "/PassTimeOptions.yaml";
    Options opts = Tools.createOptionsFromYaml(fileName);
    System.out.println(opts);
    opts.getClientsMap().forEach( (id, dates) -> {
      System.out.println(id);
      dates.forEach( dpair -> {
        String[] d = dpair.split(" +");
        String info = Time.passed(d[0], d[1]);
        System.out.println(info);
      });
    });
  }

}
