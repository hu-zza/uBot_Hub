import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HubApplication {
  public static void main(String[] args) {
    System.out.println("Scan...");
    for (var s : scanWiFi()) {
      System.out.println(s);
    }
  }

  public static ArrayList<String> scanWiFi() {
    ArrayList<String> networkList = new ArrayList<>();
    try {
      // Execute command
      String command = "netsh wlan show networks mode=Bssid";
      Process p = Runtime.getRuntime().exec(command);
      try {
        p.waitFor();
      } catch (InterruptedException ex) {
        ex.printStackTrace();
      }
      BufferedReader reader = new BufferedReader(
          new InputStreamReader(p.getInputStream())
      );
      String line;
      StringBuilder sb = new StringBuilder();
      String ssidArr[];

      while ((line = reader.readLine()) != null) {
        //System.out.println(line);
        if (line.contains("SSID ") && !line.contains("BSSID ")) {
          sb.append(line);
          networkList.add(line.split(":")[1]);
          //System.out.println("data : " + ssidArr[1]);
        }
      }
      //System.out.println(networkList);
    } catch (IOException e) {
    }
    return networkList;
  }
}
