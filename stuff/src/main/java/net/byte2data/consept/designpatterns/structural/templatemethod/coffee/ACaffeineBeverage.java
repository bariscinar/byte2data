package net.byte2data.consept.designpatterns.structural.templatemethod.coffee;

public abstract class ACaffeineBeverage {

    /*
    private IRecipe recipe;

    public ACaffeineBeverage(IRecipe recipe){
        this.recipe=recipe;
    }

    public void setRecipe(IRecipe recipe){
        this.recipe=recipe;
    }
    */

    public final void prepareRecipe(){
        boilWater();
        brew();
        pourInCup();
        if(customerRequestsCondiment())
            addContiments();
    }

    public void boilWater(){
        System.out.println("abstract boilWater");
    }

    public abstract void brew();


    public void pourInCup(){
        System.out.println("abstract pourInCup");
    }

    public abstract void addContiments();

    //This is the concrete HOOK Method!
    public boolean customerRequestsCondiment(){
        return true;
    }


}
