package rmiclients;

import GUIS.CelticGUI;
import celtic.CelticServices;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CelticClient {
    public static final int PORT = 1021;
    public static final String HOST = "localhost";
    public static final String GUEST_SERVICE_NAME = "CELTIC_SERVICE";

    public static void main(String args[]) {
        String registryName = "rmi://" + HOST + ":" + PORT + "/" + GUEST_SERVICE_NAME;
        System.out.println("CLIENT: Looking up " + registryName + " ...");

        try {
            //locate & lookup registry
            Registry registry = LocateRegistry.getRegistry(PORT);
            CelticServices service = (CelticServices) registry.lookup(registryName);

            // Create and show the GUI
            CelticGUI gui = new CelticGUI(service);
            gui.showGUI();

        } catch (Exception e) {
            System.err.println("CelticClient exception:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
