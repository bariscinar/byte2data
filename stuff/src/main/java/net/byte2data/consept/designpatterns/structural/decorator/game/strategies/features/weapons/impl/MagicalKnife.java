package net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IMagicalWeaponBehavior;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class MagicalKnife implements IMagicalWeaponBehavior {

    @Override
    public void fire() {
        System.out.println("WEAPON:KNIFE");
    }

    @Override
    public void magic() {
        System.out.println("MAGIC:WEAPON:KNIFE");
    }
}
