package net.byte2data.consept.designpatterns.structural.decorator.game;

import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.AbstractCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl.King;
import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl.Knight;
import net.byte2data.consept.designpatterns.structural.decorator.game.context.characters.impl.MagicalCharacter;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl.Bow;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl.MagicalKnife;
import net.byte2data.consept.designpatterns.structural.decorator.game.strategies.features.weapons.impl.Sword;

public class GameTest {

    public static void main(String... args){

        AbstractCharacter character = new King(new Sword());
        character.executeFeature();

        character.setFeature(new Bow());
        character.executeFeature();

        character=new MagicalCharacter(character,new Bow());
        character.executeFeature();

        character = new Knight();
        character.executeFeature();

        character = new MagicalCharacter(character, new Bow());
        character.executeFeature();

    }

}
