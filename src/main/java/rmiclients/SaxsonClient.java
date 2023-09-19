package rmiclients;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import GUIS.SaxsonGUI;
import saxon.SaxsonServices;

public class SaxsonClient {
    public static final int PORT = 1020;
    public static final String HOST = "localhost";
    public static final String SAXON_SERVICE_NAME = "SAXON_SERVICE";

    public static void main(String[] args) {
        Registry registry;
        SaxsonServices service;

        try {
            registry = LocateRegistry.getRegistry(PORT);
            service = (SaxsonServices) registry.lookup("rmi://" + HOST + ":" + PORT + "/" + SAXON_SERVICE_NAME);

            SaxsonGUI gui = new SaxsonGUI(service);
            gui.showGUI();
        } catch (Exception e) {
            System.err.println("SaxsonClient exception:");
            e.printStackTrace();
        }
    }
}
