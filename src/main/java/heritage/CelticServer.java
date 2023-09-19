package heritage;

import celtic.CelticImpl;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import jmsimpl.EntryCreditConsumer;
import javax.jms.JMSException;
import celtic.CelticServices;
import java.rmi.RemoteException;
//Celtic RMI server
public class CelticServer {

    public static final int PORT = 1021;
    public static final String HOST = "localhost";
    public static final String GUEST_SERVICE_NAME = "CELTIC_SERVICE";

    public static void main(String[] args) {
        try {
            System.out.println("CELTIC SERVER: Creating RMI registry...");
            LocateRegistry.createRegistry(PORT);
            Registry registry = LocateRegistry.getRegistry(PORT);

            System.out.println("CELTIC SERVER: Registering remote object...");
            CelticServices celitcService = new CelticImpl();
            registry.rebind("rmi://" + HOST + ":" + PORT + "/" + GUEST_SERVICE_NAME, celitcService);

            // process new messages
            EntryCreditConsumer entryCreditReceiver = new EntryCreditConsumer((CelticImpl) celitcService);

            System.out.println("CELTIC SERVER: Ready...");
        } catch (RemoteException | JMSException e) {
            System.out.println("CELTIC SERVER: Failed to start: " + e.getMessage());
        }
    }
}
