package saxon;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface SaxsonServices extends Remote {
    List<Member> getAllMembers() throws RemoteException;

    Map<Integer, Integer> getVisitorReportCounts() throws RemoteException;

    Map<Integer, Integer> getEntryCredits() throws RemoteException;

    Member getMemberById(int id) throws RemoteException;

    void addMember(Member member) throws RemoteException;

    void deleteMember(int id) throws RemoteException;

    void submitVisitorReport(Integer memberId) throws RemoteException;
}
