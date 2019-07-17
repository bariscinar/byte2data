package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.AbstractCharacter;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.concretes.Blanka;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.concretes.ChunLi;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context.concretes.Ken;
import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.impl.*;

public class StreetFighterTest {

    public static void main(String... args){
        AbstractCharacter abstractCharacter = new Ken();
        abstractCharacter.display();
        abstractCharacter.applyJump();
        abstractCharacter.setJumpBehaviour(new LongJump());
        abstractCharacter.applyJump();
        abstractCharacter.setKickBehaviour(new HighKick());
        abstractCharacter.applyKick();

        abstractCharacter = new Blanka(new Slap(), new ShortJump(), new AirRoll(), new LowKick());
        abstractCharacter.display();
        abstractCharacter.applyRoll();
        abstractCharacter.applyPunch();


        abstractCharacter = new ChunLi();
        abstractCharacter.display();
        abstractCharacter.applyPunch();
    }

}
