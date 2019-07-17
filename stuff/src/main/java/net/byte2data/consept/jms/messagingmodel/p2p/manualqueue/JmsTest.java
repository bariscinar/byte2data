package net.byte2data.consept.jms.messagingmodel.p2p.manualqueue;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.byte2data.consept.designpatterns.Constants;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.BasicConfigurator;


public class JmsTest {

    private static class Producer {
        public Producer() throws JMSException, NamingException {
            // Obtain a JNDI connection
            InitialContext jndi = new InitialContext();
            // Look up a JMS connection factory
            ConnectionFactory conFactory = (ConnectionFactory) jndi.lookup("connectionFactory");
            Connection connection;
            // Getting JMS connection from the server and starting it
            connection = conFactory.createConnection();
            try {
                connection.start();
                // JMS messages are sent and received using a Session. We will
                // create here a non-transactional session object. If you want
                // to use transactions you should set the first parameter to 'true'
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = (Destination) jndi.lookup("MYQUEUE");
                // MessageProducer is used for sending messages (as opposed
                // to MessageConsumer which is used for receiving them)
                MessageProducer producer = session.createProducer(destination);
                // We will send a small text message saying 'Hello World!'
                TextMessage message = session.createTextMessage("Hello World!");
                // Here we are sending the message!
                producer.send(message);
                System.out.println("Sent message '" + message.getText() + "'");
            } finally {
                connection.close();
            }
        }

    }

    private static class Consumer {
        // URL of the JMS server
        private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
        // Name of the queue we will receive messages from
        private static String subject = "MYQUEUE";

        public Consumer() throws JMSException{
            BasicConfigurator.configure();
            // Getting JMS connection from the server
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(Consumer.url);
            Connection connection = connectionFactory.createConnection();
            connection.start();
            // Creating session for seding messages
            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);
            // Getting the queue
            Destination destination = session.createQueue(Consumer.subject);
            // MessageConsumer is used for receiving (consuming) messages
            MessageConsumer consumer = session.createConsumer(destination);
            // Here we receive the message.
            // By default this call is blocking, which means it will wait
            // for a message to arrive on the queue.
            Message message = consumer.receive();
            // There are many types of Message and TextMessage
            // is just one of them. Producer sent us a TextMessage
            // so we must cast to it to get access to its .getText()
            // method.
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Received message '" + textMessage.getText()
                        + "'");
            }
            connection.close();
        }

    }

    public static void main(String[] args) throws JMSException, NamingException {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
    }
}
