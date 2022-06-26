/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class Tools {

    static Options createOptionsFromYaml(String fileName) throws Exception {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(fileName);
        Map<String, Object> map = yaml.load(inputStream);

        @SuppressWarnings("unchecked")
        Map<String, List<String>> clientsMap = (Map<String, List<String>>) map.get("clientsMap");

        return new Options(
                map.get("host").toString(),
                Integer.parseInt(map.get("port").toString()),
                Boolean.parseBoolean(map.get("concurMode").toString()),
                Boolean.parseBoolean(map.get("showSendRes").toString()),
                clientsMap
        );
    }
}