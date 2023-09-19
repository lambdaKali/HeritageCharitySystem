package heritage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import saxon.SaxonImpl;
import saxon.SaxsonServices;
//SaxsonRMI server
public class SaxonServer {

    public static final int PORT = 1020;
    public static final String HOST = "localhost";
    public static final String MEMBER_SERVICE_NAME = "SAXON_SERVICE";

    public static void main(String[] args) {
        try {
            System.out.println("SAXON SERVER: Creating RMI registry...");
            LocateRegistry.createRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(PORT);

            System.out.println("SAXON SERVER: Registering remote object...");
            SaxsonServices memberService = new SaxonImpl();
            registry.rebind("rmi://" + HOST + ":" + PORT + "/" + MEMBER_SERVICE_NAME, memberService);

            System.out.println("SAXON SERVER: Ready...");
        } catch (RemoteException e) {
            System.out.println("SAXON SERVER: Failed to start: " + e.getMessage());
        }
    }
}
