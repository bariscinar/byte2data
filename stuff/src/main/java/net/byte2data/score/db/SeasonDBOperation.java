package net.byte2data.score.db;

import java.util.List;

/**
 * Created by barisci on 07.04.2016.
 */
public class SeasonDBOperation {

    private DBOperations dbOperation;
    public SeasonDBOperation(String driver, String url, String user, String pass){
        dbOperation=new DBOperations(driver,url,user,pass);
    }


    public List<SeasonStatement> getAllSeasons(){
        return dbOperation.getAllActiveSeasons();
    }

}
