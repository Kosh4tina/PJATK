/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Tools {
    public static Options createOptionsFromYaml(String fileName) throws FileNotFoundException {
        Map<String, Object> map = new Yaml().load(new FileInputStream(fileName));
        return new Options((String) map.get("host"),
                (Integer) map.get("port"),
                (Boolean) map.get("concurMode"),
                (Boolean) map.get("showSendRes"),
                (LinkedHashMap<String, java.util.List<String>>) map.get("clientsMap"));
    }
}
