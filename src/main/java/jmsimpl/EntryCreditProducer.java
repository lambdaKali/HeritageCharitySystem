package jmsimpl;

import common.EntryCredit;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;

public class EntryCreditProducer {

    private String url;
    private String jmsQueue;

    public EntryCreditProducer() {
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setJmsQueue(String jmsQueue) {
        this.jmsQueue = jmsQueue;
    }

    public void sendEntryCredit(EntryCredit entryCredit) throws JMSException {
        // start jms connection to activemq
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        try (Connection connection = connectionFactory.createConnection()) {
            connection.start();
            
            // session created.
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // queue created
            Destination destination = session.createQueue(jmsQueue);
            
            // producer for sending
            MessageProducer producer = session.createProducer(destination);
            
            // type object
            ObjectMessage message = session.createObjectMessage(entryCredit);
            
            // send!!!!
            producer.send(message);
            // ofc success msg :)
            System.out.println("JMS Message Sent successfully with EntryCredit:: " + entryCredit);
        }
    }
}
