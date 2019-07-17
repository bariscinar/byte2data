package net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class Knife implements IWeaponBehavior {

    @Override
    public void fire() {
        System.out.println("WEAPON:KNIFE");
    }
}
