package net.byte2data.score.db;

import net.byte2data.score.item.League;

import java.util.Map;

/**
 * Created by barisci on 07.04.2016.
 */
public class LeagueDBOperation {

    private DBOperations dbOperation;
    public LeagueDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }

    public void checkLeague(League leagueItem){
        int countryID;
        if(null!=leagueItem){
            try{
                countryID=dbOperation.findCountryByName(leagueItem.getCountry());
                if(countryID>0){
                    if(!dbOperation.findLeagueByCountryIdAndLeagueName(countryID,leagueItem.getName())){
                        dbOperation.addLeague(leagueItem, countryID);
                    }else{
                        System.out.println("CHECK -> CountryId:LeagueName->"+countryID+":"+leagueItem.getName()+ " is already exist in the DB!");
                    }
                }else{
                    dbOperation.addLeague(leagueItem,dbOperation.addCountryByName(leagueItem.getCountry()));
                }
            }catch(Exception ex){
                System.out.println("CHECK -> exception occurred in check league operation: "+ex);
            }
        }else{
            System.out.println("CHECK -> leagueItem is NULL or EMPTY");
        }
    }

    public Map<Integer,LeagueStatement> getAllLeagues(){
        return dbOperation.getAllLeagues();
    }

}
