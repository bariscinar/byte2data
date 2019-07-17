package net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.context;

import net.byte2data.consept.designpatterns.behavioral.strategy.streetfighter.strategies.*;

public abstract class AbstractCharacter {

    /*
    TODO: Bu interface'leri bu şekilde default implemented methodlarıyla
    koymamızın sebebi pervasızca bu abstract class'dan concrete edilen
    instance'lar applyX methodlarını çağırırsa null pointer hatası yememek!
    */
    /*
    TODO: TEKRAR SÖYLÜYORUM!
    BEN BU FEATURE'LARI BU ŞEKİLDE TANIMLAMAK İSTEMİYORUM!
    DAHA DOĞRUSU BU ABSTRACT CLASS YAZILIRKEN DÜŞÜNEMEDİĞİM
    VE OYUN GELİŞTİKÇE SONRADAN EKLENEN FEATURE'LAR İÇİN NE YAPACAĞIM?
    DECORATOR DESIGN PATTERN BU İŞ İÇİN DİYE DÜŞÜNMÜŞTÜM
    AMA YANILMIŞIM!
     */
    /*
     TODO: Bu adamların protected/private olmaları da ayrı bir baş ağrısı mevzusu!
    */
    protected IPunchBehavior punchBehavior = new IPunchBehavior() {};
    protected IJumpBehaviour jumpBehaviour = new IJumpBehaviour() {};
    protected IRollInterface rollBehavior = new IRollInterface() {};
    protected IKickBehaviour kickBehaviour = new IKickBehaviour() {};

    /*
    TODO: Bir de bu sorun çıktı;
    bunca encapsulated strategies/behaviour/algorithms
    nasıl constructor içine eklenecek?
    initialize edilmezse direk çağıran adamlar patlamayacak mı?
    peki burada initialize etsek ve default interface method'larına güvensek
    bu sefer de abstract class definition ile ters düşmüyor mu?
    */
    public AbstractCharacter(IPunchBehavior punch,
                             IJumpBehaviour jump,
                             IRollInterface roll,
                             IKickBehaviour kick){
        this.punchBehavior=punch;
        this.jumpBehaviour=jump;
        this.kickBehaviour=kick;
        this.rollBehavior=roll;
    }

    public AbstractCharacter(){
    }


    public void applyPunch(){
        punchBehavior.punch();
    }
    public void applyJump(){
        jumpBehaviour.jump();
    }
    public void applyRoll(){
        rollBehavior.roll();
    }
    public void applyKick(){
        kickBehaviour.kick();
    }

    public void setJumpBehaviour(IJumpBehaviour jumpBehaviour) {
        this.jumpBehaviour = jumpBehaviour;
    }

    public void setKickBehaviour(IKickBehaviour kickBehaviour) {
        this.kickBehaviour = kickBehaviour;
    }

    public void setPunchBehavior(IPunchBehavior punchBehavior) {
        this.punchBehavior = punchBehavior;
    }

    public void setRollBehavior(IRollInterface rollBehavior) {
        this.rollBehavior = rollBehavior;
    }

    public abstract void display();
}
