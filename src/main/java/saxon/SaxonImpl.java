package saxon;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import jmsimpl.EntryCreditProducer;
import common.EntryCredit;

public class SaxonImpl extends UnicastRemoteObject implements SaxsonServices {
    
    private static final long serialVersionUID = 1L;
    private final List<Member> members = new ArrayList<>();
    private final Map<Integer, Integer> visitorReportCounts = new HashMap<>();
    private final Map<Integer, Integer> entryCredits = new HashMap<>();
    
    public SaxonImpl() throws RemoteException {
        super();
    }
    
    @Override
    public List<Member> getAllMembers() throws RemoteException {
        return members;
    }
    
    @Override
    public Map<Integer, Integer> getVisitorReportCounts() throws RemoteException {
        return visitorReportCounts;
    }

    @Override
    public Map<Integer, Integer> getEntryCredits() throws RemoteException {
        return entryCredits;
    }

    @Override
    public Member getMemberById(int id) throws RemoteException {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }
    
    @Override
    public void addMember(Member member) throws RemoteException {
        if (getMemberById(member.getId()) == null) {
            members.add(member);
            System.out.println("Added member. ID:" + member.getId() + " Date and Time: " + LocalDateTime.now());
        } else {
            System.out.println("Member with ID " + member.getId() + " already exists.");
        }
    }
    
    @Override
    public void deleteMember(int id) throws RemoteException {
        Member memberToDelete = getMemberById(id);
        if (memberToDelete != null) {
            members.remove(memberToDelete);
            // remove member from maps as well
            visitorReportCounts.remove(id);
            entryCredits.remove(id);
            System.out.println("Deleted member. ID:" + id + " Date and Time: " + LocalDateTime.now());
        }
    }

   @Override
    public void submitVisitorReport(Integer memberId) throws RemoteException {
        int count = visitorReportCounts.getOrDefault(memberId, 0);
        // increment the count
        count++;
        visitorReportCounts.put(memberId, count);
        // check if the member is eligible for free entry
        System.out.println("Successfully Added Visitor report");
        if (count >= 5) {
            System.out.println("Successfully Added 5 Visitor Reports. Generating entryCredit .....");
            int creditCount = entryCredits.getOrDefault(memberId, 0);
            creditCount++;
            entryCredits.put(memberId, creditCount);
            visitorReportCounts.put(memberId, 0); // reset the count

            // Send entry credit to ActiveMQ queue
            try {
                LocalDate expirationDate = LocalDate.now().plusMonths(3);
                LocalDateTime creditSentTime = LocalDateTime.now();
                EntryCredit entryCredit = new EntryCredit(memberId, expirationDate, "saxon");
                EntryCreditProducer producer = new EntryCreditProducer();
                producer.setUrl("tcp://localhost:61616"); // replace with the actual URL of ActiveMQ server
                producer.setJmsQueue("entryCreditQueue"); // replace with the actual name of the queue
                producer.sendEntryCredit(entryCredit);

                // Print the time, date and member id when credit was sent
                System.out.println("Credit sent on " + creditSentTime + " for Member ID: " + memberId);

                // Remove the credit from the entry credits map
                entryCredits.remove(memberId);
            } catch (JMSException e) {
                System.out.println("JMS Exception: " + e.getMessage());
            }
        }
    }
}