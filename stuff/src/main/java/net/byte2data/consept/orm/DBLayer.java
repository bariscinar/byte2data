package net.byte2data.consept.orm;

import net.byte2data.consept.orm.utilities.NullCheck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBLayer {

    private static DBLayer instance;
    private static SessionFactory sessionFactory;

    private DBLayer(){

    }

    //double checked lazy singleton
    public static DBLayer getInstance(){
        if(null==instance){
            synchronized (DBLayer.class){
                if(null==instance) {
                    instance = new DBLayer();
                    createFactory();
                }
            }
        }
        return instance;
    }

    /*
    There should be the only one factory instance!
    todo: i am not sure about its location!?
    */
    private static void createFactory(){
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }

    public void putSession(Session session){
        session.close();
        //session.disconnect();
    }

    public void destroyFacyory(){
        if(null!=sessionFactory){

            //Hibernate 5.2.4.Final
            //if(sessionFactory.isOpen()) {

            //Hibernate 4.2.2.Final
            if(!sessionFactory.isClosed()) {
                //System.out.println("Session Factory is closing...");
                sessionFactory.close();
                //System.out.println("Session Factory is closed");
            }else{
                //System.out.println("Session Factory is not open!");
            }
        }else{
            //System.out.println("Session Factory is null!");
        }
    }

}
