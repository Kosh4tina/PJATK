/**
 *
 *  @author Puchko Konstantsin S19575
 *
 */

package zad1;


import java.util.*;

public class Options {

  private String host;
  private int port;
  private boolean concurMode;
  private boolean showSendRes;
  private Map<String, List<String>> clientsMap = new LinkedHashMap<>();

  public Options(String host, int port, boolean concurMode, boolean showSendRes, 
                 Map<String, List<String>> clientsMap) {
    this.host = host;
    this.port = port;
    this.concurMode = concurMode;
    this.showSendRes = showSendRes;
    this.clientsMap = clientsMap;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public boolean isConcurMode() {
    return concurMode;
  }

  public boolean isShowSendRes() {
    return showSendRes;
  }

  public Map<String, List<String>> getClientsMap() {
    return clientsMap;
  }

  public String toString() {
    String out = "";  // StringBuilder bardziej efektywny, ale za dużo pisania
    out += host + " " + port + " " + concurMode + " " + showSendRes + "\n";
    for (Map.Entry<String, List<String>> e : clientsMap.entrySet()) {
      out += e.getKey() + ": " + e.getValue() + "\n";
    }
    return out;
  }

}
