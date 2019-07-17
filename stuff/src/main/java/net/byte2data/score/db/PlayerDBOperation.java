package net.byte2data.score.db;

import net.byte2data.score.item.Player;

import java.util.Map;

/**
 * Created by barisci on 07.04.2016.
 */
public class PlayerDBOperation {

    private DBOperations dbOperation;
    public PlayerDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }

    public void checkPlayer(Player playerItem, int teamID){
        if(null!=playerItem){
            try{
                if(dbOperation.findPlayerByPlayerIdAndPlayerName(playerItem.getId(),playerItem.getName())){
                    System.out.println("HOWCOME CHECK -> PlayerID:"+playerItem.getId()+" is already exist in the DB!");
                }else{
                    dbOperation.addPlayer(playerItem,teamID);
                }
            }catch(Exception ex){
                System.out.println("CHECK -> exception occurred in check player operation: "+ex);
            }
        }else{
            System.out.println("CHECK -> playerItem is NULL or EMPTY");
        }
    }

    public Map<Integer,PlayerStatement> getAllPlayers(){
        return dbOperation.getAllPlayers();
    }

}
