package net.byte2data.consept.jms.activemq;

import net.byte2data.tutorial.tutorials.learningthejavalanguage.classesandobjects.nested.innernested.anonymous.x2.HelloWorld;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.LockableServiceSupport;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.store.jdbc.JDBCPersistenceAdapter;
import org.apache.activemq.thread.TaskRunnerFactory;

import javax.jms.*;
import java.io.File;
import java.net.URI;
import java.util.*;

public class App {
    //URL of the JMS server. DEFAULT_BROKER_URL will just mean that JMS server is on localhost
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    // default broker URL is : tcp://localhost:61616"
    private static String subject = "TEST_QUEUE";
    // Queue Name.You can create any/many queue names as per your requirement.


    public static void main(String[] args) throws Exception {
        App app = new App();

        thread(app. new HelloWorldConsumer(), false);
        thread(app. new HelloWorldProducer(), false);

    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public class HelloWorldProducer implements Runnable {
        public void run() {
            try {
                // Create a ConnectionFactory
                //ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("admin", "admin",url);
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("nio://localhost:54800");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(subject);

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                //String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                String text = String.valueOf(new Random().nextInt(1000));
                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                //System.out.println("Sent message: "+ this.hashCode() + " : " + Thread.currentThread().getName());
                System.out.println("Sent message: "+ message + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            }
            catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }

    public class HelloWorldConsumer implements Runnable, ExceptionListener {
        public void run() {
            try {

                // Create a ConnectionFactory
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("nio://localhost:54800");

                // Create a Connection
                Connection connection = connectionFactory.createConnection();
                //Connection connection = ((ConnectionFactory)getConnectionFactory().getConnectionFactory()).createConnection();
                connection.start();

                connection.setExceptionListener(this);

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                // Create the destination (Topic or Queue)
                Destination destination = session.createQueue(subject);

                // Create a MessageConsumer from the Session to the Topic or Queue
                MessageConsumer consumer = session.createConsumer(destination);

                // Wait for a message
                Message message = consumer.receive(1000);
                System.out.println("Received Message: " + message);

                /*
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received Text: " + text);
                }
                */

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }
}
