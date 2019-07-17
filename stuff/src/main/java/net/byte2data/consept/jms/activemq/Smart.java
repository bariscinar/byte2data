package net.byte2data.consept.jms.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.LockableServiceSupport;
import org.apache.activemq.broker.TransportConnector;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.util.IdGenerator;

import javax.jms.*;
import java.io.File;
import java.net.URI;
import java.util.*;

public class Smart {

    private static final IdGenerator ID_GENERATOR = new IdGenerator("peer-");

    private volatile Thread initThread;
    private Thread publishQueueSizesThread;


    private  PooledConnectionFactory pooledFactory;
    private volatile BrokerService inittedBroker;
    private volatile BrokerService nonInittedBroker;


    public void init() {
        System.out.println("init starting");
        initBrokerInThread();
        startListening();
        System.out.println("init completed");
    }
    private void initBrokerInThread() {
        System.out.println("initBrokerInThread starting");
        //initMonitor.enter();
        try {
            initThread = new Thread("ActiveMQ Broker MasterShip") {
                public void run() {
                    initLoop();
                }
            };
            initThread.start();
        } finally {
            //initMonitor.leave();
        }
        System.out.println("initBrokerInThread completed");
    }
    private void initLoop() {
        System.out.println("initLoop starting");
        String bindAddresses = null;
        if (bindAddresses == null) {
            bindAddresses = "127.0.0.1";
        }
        final File storeDirectory = new File("/home/barisci/activemq");
        final String groupName = "localtest";
        try {
            Collection<String> localMatches = new ArrayList<>();
            localMatches.add("127.0.0.1");

            if (!localMatches.isEmpty() & !"true".equals(System.getenv("SMARTADS_NO_AMQBROKER"))) {
                String sanitizedId = ID_GENERATOR.generateSanitizedId();
                System.out.println("sanitizedId:"+sanitizedId);
                System.out.println("Initializing BrokerService - Embed a BROKER in ActiveMQ");
                nonInittedBroker = new BrokerService();
                nonInittedBroker.setBrokerName(sanitizedId);

                //Check below!
                nonInittedBroker.setUseJmx(true);

                TransportConnector vmConnector = nonInittedBroker.addConnector("vm://" + sanitizedId);
                // vmConnector.setDiscoveryUri(new URI("multicast://default?group=" + groupName));
                for (String localIp : localMatches) {
                    System.out.println("initLoop - localIp:"+localIp);
                    TransportConnector tcpConnector;
                    tcpConnector = nonInittedBroker.addConnector("nio://" + localIp + ":" + 54800);
                    tcpConnector.setDiscoveryUri(new URI("multicast://default?group=" + groupName));
                }

                //boolean kahaDb = !extraParameters.getBoolean("jdbc");
                //if (kahaDb) {
                nonInittedBroker.getPersistenceAdapter().setDirectory(storeDirectory);
                //} else {
                //    JDBCPersistenceAdapter jdbcPersistenceAdapter = new JDBCPersistenceAdapter(Preconditions.checkNotNull(
                //            amqDataSource.get(), "Data source is not set"), new OpenWireFormat());
                //    nonInittedBroker.setPersistenceAdapter(jdbcPersistenceAdapter);
                //}
                // Log every two minutes if this is not the main
                ((LockableServiceSupport) nonInittedBroker.getPersistenceAdapter()).getLocker().setLockAcquireSleepInterval(2 * 60 * 1000);
                nonInittedBroker.addNetworkConnector("multicast://default?group=" + groupName);

                nonInittedBroker.setSchedulePeriodForDestinationPurge(15000);

                // deleting inactive destinations: http://bit.ly/10dyQbo
                PolicyEntry peQ = new PolicyEntry();
                peQ.setOptimizedDispatch(true);
                peQ.setUseCache(false);
                peQ.setGcInactiveDestinations(true);
                peQ.setInactiveTimoutBeforeGC(300000);
                //peQ.setInactiveTimeoutBeforeGC(300000);
                peQ.setQueue("SmartAds" + ".>");
                PolicyEntry peT = new PolicyEntry();
                peT.setGcInactiveDestinations(true);
                peT.setInactiveTimoutBeforeGC(300000);
                //peT.setInactiveTimeoutBeforeGC(300000);
                peQ.setUseCache(false);

                peT.setTopic("SmartAds" + ".>");
                nonInittedBroker.setDestinationPolicy(new PolicyMap());
                nonInittedBroker.getDestinationPolicy().setPolicyEntries(Arrays.asList(peQ, peT));

                nonInittedBroker.getSystemUsage().getMemoryUsage().setLimit(1024 * 1024 * 1024);
                //if (extraParameters.getBoolean("sendFail")) {
                nonInittedBroker.getSystemUsage().setSendFailIfNoSpace(true);
                //}

                TaskRunnerFactory taskRunnerFactory = new TaskRunnerFactory("ActiveMQ BrokerService["+
                        nonInittedBroker.getBrokerName() +
                        "] Task", Thread.NORM_PRIORITY, true, 1000, false, 100);

                nonInittedBroker.setTaskRunnerFactory(taskRunnerFactory);

                nonInittedBroker.start();
                boolean started = nonInittedBroker.waitUntilStarted();

                if (started) {
                    inittedBroker = nonInittedBroker;
                    // F*ck the race
                    nonInittedBroker = null;
                    startPublishing(inittedBroker);
                } else {
                    System.out.println("initLoop - Could not start broker, probably exiting");
                }

            }
        } catch (Error e) {
            System.out.println("initLoop - Error during broker initialization:"+ e);
        } catch (Exception e) {
            if (e instanceof InterruptedException) {
                InterruptedException iex = (InterruptedException) e;
                System.out.println("initLoop - Interrupted during broker init: " + iex);
            } else {
                System.out.println("initLoop - Exception during broker initialization: " + e);
            }
        }
        System.out.println("initLoop completed");

    }
    void startPublishing(BrokerService inittedBroker) {
        this.inittedBroker = inittedBroker;
        publishQueueSizesThread = new Thread("Publish Queue Sizes") {
            @Override
            public void run() {
                publishQueueSizesLoop();
            }
        };
        publishQueueSizesThread.start();
    }
    private void publishQueueSizesLoop() {
        Session session = null;
        Topic topic = null;
        MessageProducer producer = null;
        Connection queueSizePublisherConnection = null;
        //Closer brenda = Closer.create();
        try {
            try {
                queueSizePublisherConnection = createNewManualConnection();
                //brenda.register(QueueAdapter.closeable(queueSizePublisherConnection));
                session = queueSizePublisherConnection.createSession(false, ActiveMQSession.AUTO_ACKNOWLEDGE);
                //brenda.register(QueueAdapter.closeable(session));
                topic = session.createTopic("QUEUESIZES");
                producer = session.createProducer(topic);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                producer.setTimeToLive(2 * 60 * 1000);
                //brenda.register(QueueAdapter.closeable(producer));
            } catch (JMSException e) {
                System.out.println("Error initializing queue sizes: " + e);
                return;
            }
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    break;
                }
                try {
                    MapMessage mapMessage = session.createMapMessage();
                    String prefix = "SmartAds";
                    for (Map.Entry<ActiveMQDestination, org.apache.activemq.broker.region.Destination> e : inittedBroker.getBroker().getDestinationMap().entrySet()) {
                        String qualifiedName = e.getKey().getQualifiedName();
                        if (qualifiedName.indexOf(prefix) == -1 || qualifiedName.indexOf("ActiveMQ") != -1) {
                            // TODO implement a better check
                            continue;
                        }
                        long enqueuCount = e.getValue().getDestinationStatistics().getEnqueues().getCount();
                        long dequeCount = e.getValue().getDestinationStatistics().getDequeues().getCount();
                        long queueSize = enqueuCount - dequeCount;
                        mapMessage.setLongProperty(qualifiedName, queueSize);
                    }
                    producer.send(mapMessage);
                } catch (JMSException e) {
                    System.out.println("Error publishing queue sizes: " + e);
                } catch (Exception e) {
                    System.out.println("Error publishing queue sizes: " + e);
                }
            }
        }catch (Exception ex){
            System.out.println("**");
        }
    }

    void startListening() {
        System.out.println("startListening starting");
        try {
            //queueSizesClientConnection = initerProvider.get().createNewManualConnection();
            Connection connection = createNewManualConnection();
            Session session = connection.createSession(false, ActiveMQSession.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("QUEUESIZES");
            MessageConsumer messageConsumer = session.createConsumer(topic);
            messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    @SuppressWarnings("rawtypes")
                    Enumeration names;
                    try {
                        names = message.getPropertyNames();
                        while (names.hasMoreElements()) {
                            String name = (String) names.nextElement();
                            long size = message.getLongProperty(name);
                            //logger.trace("Queue size {} is {}", name, size);
                            //sizeCache.put(name, size);
                        }
                    } catch (JMSException e) {
                        System.out.println("startListening - Error: " +  e);
                    }
                }
            });
        } catch (JMSException e) {
            System.out.println("startListening - Error: " + e);
            return;
        }
        System.out.println("startListening completed");
    }
    private Connection createNewManualConnection() throws JMSException {
        System.out.println("createNewManualConnection starting");
        Connection connection = (((ConnectionFactory) getConnectionFactory().getConnectionFactory())).createConnection();
        connection.start();
        System.out.println("createNewManualConnection completed");
        return connection;
    }
    private PooledConnectionFactory getConnectionFactory() {
        //forceSync=true,prefetch=1000,syncRead=true,jdbc=false,jdbcPoolSize=100
        System.out.println("getConnectionFactory...");
        if (pooledFactory == null) {
            synchronized (this) {
                if (pooledFactory == null) {
                    // wait initialization to finish on this host
                    String url;
                    if (inittedBroker != null) {
                        url = "vm://" + inittedBroker.getBrokerName();
                    } else {
                        String discovery = "default";
                        url = "discovery:(multicast://" + discovery + "?group=" + "hbc" + ")";
                    }
                    System.out.println("getConnectionFactory url: " + url);
                    ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin",url);
                    //factory.setTrustedPackages();

                    factory.getPrefetchPolicy().setQueuePrefetch(1000);
                    factory.setUseAsyncSend(true);


                    pooledFactory = new PooledConnectionFactory(factory);
                    pooledFactory.setMaxConnections(30);
                    pooledFactory.setCreateConnectionOnStartup(true);
                    pooledFactory.start();
                    System.out.println("getConnectionFactory started: " + pooledFactory.getConnectionFactory().toString());
                }
            }
        }
        return pooledFactory;
    }

    public static void main(String[] args) throws Exception {
        Smart app = new Smart();
        app.init();

    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

}
