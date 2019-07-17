package net.byte2data.tutorial.tutorials.learningthejavalanguage.generics.bounded;

/**
 * Created by barisci on 26.09.2017.
 */
public class Koli extends Kutu {

    private int includedKutuCount;

    public Koli(double hight, double length, double witdh, String name, Standart standart, int includedKutuCount) {
        super(hight, length, witdh, name, standart);
        this.includedKutuCount = includedKutuCount;
    }

    public int getIncludedKutuCount() {
        return includedKutuCount;
    }

    public void setIncludedKutuCount(int includedKutuCount) {
        this.includedKutuCount = includedKutuCount;
    }
}
