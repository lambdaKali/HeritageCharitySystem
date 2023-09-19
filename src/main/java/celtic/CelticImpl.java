package celtic;

import common.DataHolder;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

//Remote object with methods

public class CelticImpl extends UnicastRemoteObject implements CelticServices, Serializable {
    private final Map<Integer, List<DataHolder>> entryCredits = new HashMap<>();

    public CelticImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized Map<Integer, List<DataHolder>> getEntryCredits() throws RemoteException {
        Map<Integer, List<DataHolder>> copy = new HashMap<>();
        for (Map.Entry<Integer, List<DataHolder>> entry : entryCredits.entrySet()) {
            copy.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return copy;
    }

    public synchronized void addEntryCredit(int memberId, String siteObtained, LocalDate expirationDate) throws RemoteException {
        List<DataHolder> memberCredits = entryCredits.get(memberId);
        if (memberCredits == null) {
            memberCredits = new ArrayList<>();
        }
        memberCredits.add(new DataHolder(siteObtained, expirationDate));
        entryCredits.put(memberId, memberCredits);
        System.out.println("Entry credit added for member ID " + memberId + " at " + LocalDateTime.now());
    }

    @Override
    public synchronized void redeemEntryCredit(int memberId) throws RemoteException {
        List<DataHolder> memberCredits = entryCredits.get(memberId);
        if (memberCredits != null && !memberCredits.isEmpty()) {
            memberCredits.remove(0);
            if (memberCredits.isEmpty()) {
                entryCredits.remove(memberId);
            }
            System.out.println("Entry credit redeemed for member ID " + memberId + " at " + LocalDateTime.now());
        } else {
            System.out.println("No entry credit found for member ID " + memberId);
        }
    }
}