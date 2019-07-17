package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class King extends AbstractCharacter {

    public King(IWeaponBehavior feature) {
        super(feature);
    }
}
