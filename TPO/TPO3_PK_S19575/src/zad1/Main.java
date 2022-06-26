/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import org.json.JSONObject;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    Service.initCountries();
    GUI.launch(args);
  }
}

