package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl.NoWeapon;

public class Queen extends AbstractCharacter {

    public Queen(){
        super(new NoWeapon());
    }


}
