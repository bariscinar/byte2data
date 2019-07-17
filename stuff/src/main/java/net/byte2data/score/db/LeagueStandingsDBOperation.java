package net.byte2data.score.db;

import net.byte2data.score.item.TeamStanding;

/**
 * Created by barisci on 07.04.2016.
 */
public class LeagueStandingsDBOperation {

    private DBOperations dbOperation;
    public LeagueStandingsDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }

    public void checkTeamStanting(TeamStanding teamStandingItem, int lId, int seasonID){
        if(null!=teamStandingItem){
            try{
                TeamStatement teamStatement = new TeamStatement();
                teamStatement.setTeamID(teamStandingItem.getId());
                teamStatement.setTeamName(teamStandingItem.getTeam());
                    if(!dbOperation.findTeam(teamStatement)){
                        System.out.println("CHECK -> TeamId:TeamName->"+teamStandingItem.getId()+":"+teamStandingItem.getTeam()+ " is NOT found in TEAM table! But there is a standing for this team!");
                    }else{
                        //System.out.println("OK TeamId:TeamName->"+teamStandingItem.getId()+":"+teamStandingItem.getTeam()+ " is FOUND in TEAM table");
                        dbOperation.addTeamStanding(teamStandingItem, lId, seasonID);
                    }
            }catch(Exception ex){
                System.out.println("CHECK -> exception occurred in check team standing operation: "+ex);
            }
        }else{
            System.out.println("CHECK -> teamStandingItem is NULL or EMPTY");
        }

    }

}
