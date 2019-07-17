package net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractMagicalCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.IWeaponBehavior;

public class MagicalCharacter extends AbstractMagicalCharacter {

    private AbstractCharacter abstractCharacter;
    public MagicalCharacter(AbstractCharacter character, IWeaponBehavior weaponBehavior) {
        super(weaponBehavior);
        this.abstractCharacter=character;
    }

    @Override
    public void executeFeature() {
        System.out.print("Magical -> ");
        super.executeFeature();

    }

}
