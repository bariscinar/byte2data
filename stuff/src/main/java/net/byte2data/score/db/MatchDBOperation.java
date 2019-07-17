package net.byte2data.score.db;

import net.byte2data.score.item.Match;

import java.util.Map;

/**
 * Created by barisci on 07.04.2016.
 */
public class MatchDBOperation {

    private DBOperations dbOperation;
    public MatchDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }

    public void checkMatch(Match matchItem, int sId){
        if(null!=matchItem){
            int fixtureID=0;
            int lId=0;
            try{
                fixtureID=dbOperation.findMatchByMatchFixtureID(matchItem.getFixtureMatchId());
                if(fixtureID==0){
                    lId=dbOperation.findLeagueByLeagueName(matchItem.getLeague());
                    if(lId>0){
                        //System.out.println("LEAGUE FOUND leagueID: "+leagueID);
                        dbOperation.addMatch(matchItem, lId, sId);
                    }else{
                        System.out.println("CHECK -> LEAGUE CANNOT BE FOUND IN THE DB FOR THIS MATCH -> "+matchItem.getLeague());
                    }
                }else{
                    System.out.println("CHECK -> THIS FIXTURE MATCH is ALREADY EXIST IN THE DB -> "+fixtureID);
                }
            }catch(Exception ex){
                System.out.println("CHECK -> EXCEPTION occurred in the MATCH OPERATION -> "+ex);
            }
        }else{
            System.out.println("CHECK -> matchItem is NULL or EMPTY");
        }
    }

    public Map<Integer,LeagueStatement> getAllLeagues(){
        return dbOperation.getAllLeagues();
    }

}
