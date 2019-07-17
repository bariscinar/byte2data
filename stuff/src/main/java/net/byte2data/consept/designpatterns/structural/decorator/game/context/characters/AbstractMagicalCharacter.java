package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters;


import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class AbstractMagicalCharacter extends AbstractCharacter {

    protected AbstractMagicalCharacter(IWeaponBehavior feature) {
        super(feature);
    }
}
