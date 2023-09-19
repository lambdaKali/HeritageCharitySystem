package jmsimpl;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

import celtic.CelticImpl;
import common.EntryCredit;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
// JMS LISTENER -----> ASYNC
public class EntryCreditConsumer implements MessageListener {
    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;
    private CelticImpl celticImpl;

    public EntryCreditConsumer(CelticImpl celticImpl) throws JMSException {
        
        //---------JMS LAYERS-------->
        this.celticImpl = celticImpl;
        //factory
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        factory.setTrustAllPackages(true);
        //connection
        connection = factory.createConnection();
        connection.start();
        //session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("entryCreditQueue");
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);
    }

    // EntryCreditConsumer
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage objMsg = (ObjectMessage) message;
                EntryCredit entryCredit = (EntryCredit) objMsg.getObject();

                if (!entryCredit.getExpirationDate().isBefore(LocalDate.now())) {
                    celticImpl.addEntryCredit(entryCredit.getMemberId(), entryCredit.getSiteObtained(), entryCredit.getExpirationDate());
                }
            } else {
                System.out.println("Received message of unsupported type: " + message.getClass());
            }
        } catch (JMSException e) {
            System.out.println("JMS Exception: " + e.getMessage());
            e.printStackTrace();
        } catch (RemoteException ex) {
            Logger.getLogger(EntryCreditConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//close connection

    public void close() throws JMSException {
        consumer.close();
        session.close();
        connection.close();
    }
}
