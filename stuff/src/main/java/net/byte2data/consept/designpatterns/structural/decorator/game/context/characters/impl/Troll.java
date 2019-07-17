package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class Troll extends AbstractCharacter {

    public Troll(IWeaponBehavior weapon){
        super(weapon);
    }

}
