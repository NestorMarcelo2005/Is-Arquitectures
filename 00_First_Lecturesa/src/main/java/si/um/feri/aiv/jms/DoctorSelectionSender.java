package si.um.feri.aiv.jms;

import jakarta.ejb.Stateless;
import jakarta.jms.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;


@Stateless
public class DoctorSelectionSender {

    private static final String USERNAME = "nelson";
    private static final String PASSWORD = "nelson123.";

    public void send(DoctorSelectionMessage msg) throws NamingException, JMSException {
        Context ctx = getInitialContext();
        ConnectionFactory connectionFactory = (ConnectionFactory) ctx.lookup("jms/RemoteConnectionFactory");
        Queue queue = (Queue) ctx.lookup("jms/queue/DoctorSelectionQueue");

        try (JMSContext jmsContext = connectionFactory.createContext(USERNAME, PASSWORD)) {
            ObjectMessage objectMessage = jmsContext.createObjectMessage(msg);
            jmsContext.createProducer().send(queue, objectMessage);
            System.out.println("✅ Message sent to JMS queue.");
        }
    }

    private Context getInitialContext() throws NamingException {
        Hashtable<String, String> props = new Hashtable<>();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        props.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        props.put(Context.SECURITY_PRINCIPAL, USERNAME);
        props.put(Context.SECURITY_CREDENTIALS, PASSWORD);

        return new InitialContext(props);
    }
}
