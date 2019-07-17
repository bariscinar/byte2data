package net.byte2data.score.db;

/**
 * Created by barisci on 12.04.2016.
 */
public class SeasonStatement implements Comparable<SeasonStatement>{

    private int ID;
    private String seasonTerm;

    public int getID() {
        return ID;
    }

    public void setID(int seasonID) {
        this.ID = seasonID;
    }

    public String getSeasonTerm() {
        return seasonTerm;
    }

    public void setSeasonTerm(String seasonTerm) {
        this.seasonTerm = seasonTerm;
    }

    @Override
    public int compareTo(SeasonStatement seasonStatement) {
        //System.out.println("this:"+this.seasonID);
        //System.out.println("other:"+seasonStatement.seasonID);
        int compare=Integer.compare(seasonStatement.getID(),this.getID());
        //System.out.println("compare:"+compare);
        return compare;

    }
}
