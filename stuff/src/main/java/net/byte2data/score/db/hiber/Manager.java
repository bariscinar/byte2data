package net.byte2data.score.db.hiber;

/**
 * Created by barisci on 15.11.2016.
*/

import java.util.List;
import java.util.Iterator;

import net.byte2data.score.db.pojo.League;
import net.byte2data.score.db.pojo.Country;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Manager {
    private static SessionFactory factory;
    public static void main(String[] args) {
        try{
            factory = new Configuration().configure().buildSessionFactory();
        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
        Manager ME = new Manager();

        /* Add few employee records in database */
        System.out.println("new country id: " +  ME.addCountry("test"));
    }

    public Integer addCountry(String countryName){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer countryId = null;
        try{
            tx = session.beginTransaction();
            Country country = new Country(countryName);
            countryId = (Integer) session.save(country);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return countryId;
    }

    /* Method to CREATE an league in the database */
    public Integer addLeague(int countryId, String leagueName){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer leagueId = null;
        try{
            tx = session.beginTransaction();
            League league = new League(countryId, leagueName);
            leagueId = (Integer) session.save(league);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return leagueId;
    }
    /* Method to  READ all the leagues */
    public void lisLeagues( ){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            List leagues = session.createQuery("FROM LEAGUE").list();
            for (Iterator iterator = leagues.iterator(); iterator.hasNext();){
                League league = (League) iterator.next();
                System.out.print("League Name: " + league.getLeagueName());
            }
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to UPDATE league name for an league */
    public void updateLeague(Integer leagueId, String leagueName){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            League league = (League)session.get(League.class, leagueId);
            league.setLeagueName(leagueName);
            session.update(league);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    /* Method to DELETE an league from the records */
    public void deleteLeague(Integer leagueId){
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            League league = (League)session.get(League.class, leagueId);
            session.delete(league);
            tx.commit();
        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
}



