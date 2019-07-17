package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters;

import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public abstract class AbstractCharacter {

    protected IWeaponBehavior weaponBehavior;

    protected AbstractCharacter(IWeaponBehavior feature){
        this.weaponBehavior=feature;
    }

    public void setFeature(IWeaponBehavior feature) {
        this.weaponBehavior = feature;
    }

    public void executeFeature(){
        weaponBehavior.fire();
    }




}
