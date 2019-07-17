package net.byte2data.score.db;

import net.byte2data.score.item.Team;

import java.util.Map;

/**
 * Created by barisci on 07.04.2016.
 */
public class TeamDBOperation {

    private DBOperations dbOperation;
    public TeamDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }

    public void checkTeam(Team teamItem){
        int countryID;
        if(null!=teamItem){
            try{
                countryID=dbOperation.findCountryByName(teamItem.getCountry());
                TeamStatement teamStatement = new TeamStatement();
                teamStatement.setCountryID(countryID);
                teamStatement.setTeamName(teamItem.getName());
                if(countryID>0){
                    if(!dbOperation.findTeam(teamStatement)){
                        dbOperation.addTeamByCountryId(teamItem, countryID);
                    }else{
                        System.out.println("CHECK -> CountryId:TeamName->"+countryID+":"+teamItem.getName()+ " is already exist in the MAP!");
                    }
                }else{
                    dbOperation.addTeamByCountryId(teamItem,dbOperation.addCountryByName(teamItem.getCountry()));
                }
            }catch(Exception ex){
                System.out.println("CHECK -> exception occurred in check team operation: "+ex);
            }
        }else{
            System.out.println("CHECK -> teamItem is NULL or EMPTY");
        }

    }

    public Map<Integer,TeamStatement> getAllTeams(){
        return dbOperation.getAllTeams();
    }

}
