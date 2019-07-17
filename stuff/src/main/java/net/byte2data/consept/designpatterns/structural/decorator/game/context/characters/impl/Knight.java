package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl.Sword;

public class Knight extends AbstractCharacter {

    public Knight(){
        super(new Sword());
    }

}
