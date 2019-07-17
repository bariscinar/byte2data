package net.byte2data.consept.designpatterns.behavioral.strategy.duck.context;

import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IFlyBehavior;
import net.byte2data.consept.designpatterns.behavioral.strategy.duck.behavior.intf.IQuackBehavior;

public abstract class Duck {

    /*
    TODO: Bu biraz strateji paterni ile ilgili olmasa da
    aşağıda abstract base class içinde tüm behavior'ların
    tanımlanmasını doğru bulmuyorum.
    Eğer Duck'ın daha sonra farkedeceğim bir behavior'ı olursa
    onu nasıl ekleyeceğim?
    Decorator pattern düşündüğüm gibi işime yaramadı.
    Çünkü kendisi zaten var olan behavior/function/method/interface
    artık adına ne deniyorsa onları genişletiyor,
    daha doğrusu extend ediyor.
    Ben behavior'ların extend edilmesinden brand new behavior'lar
    eklenmesini anlıyorum ve bunun için bir yöntem bulamadım.
    */
    private IFlyBehavior flyBehavior;
    private IQuackBehavior quackBehavior;

    /*
    TODO: Eğer bu abstract class içinde aşağıdaki gibi
    constructor eklemezsek bu behaviorların initial değerleri
    null olacağı için applyFly ve applyQuack null pointer atabilir.
    */
    public Duck(IFlyBehavior flyBehavior, IQuackBehavior quackBehavior){
        this.flyBehavior=flyBehavior;
        this.quackBehavior=quackBehavior;
    }

    public abstract void display();

    public void setFlyBehavior(IFlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(IQuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }

    public void applyFly(){
        flyBehavior.fly();
    }

    public void applyQuack(){
        quackBehavior.quack();
    }
}
