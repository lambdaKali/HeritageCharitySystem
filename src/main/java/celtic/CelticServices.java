package celtic;

import common.DataHolder;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
//remote interface
public interface CelticServices extends Remote, java.io.Serializable {
    public Map<Integer, List<DataHolder>> getEntryCredits() throws RemoteException;
    void redeemEntryCredit(int memberId) throws RemoteException;
}
