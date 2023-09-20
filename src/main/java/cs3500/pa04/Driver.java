package cs3500.pa04;

import cs3500.pa04.controller.Controller;
import cs3500.pa04.controller.ProxyController;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {

  private static void runClient(String host, int port) throws IOException, IllegalStateException {
    Socket server = new Socket(host, port);
    ProxyController proxyController = new ProxyController(server);
    proxyController.run();
  }

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {
      if (args.length == 0) {
        Readable buffer = new InputStreamReader(System.in);

        Controller controller = new Controller(buffer);
        controller.run();

      } else if (args.length == 2) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // run the proxy controller
        try {
          runClient(host, port);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      } else {
        throw new IllegalArgumentException("A host and a port is required");
      }
    }
  }

}