package edu.hitsz.factory;

import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.BombSupply;
import edu.hitsz.props.FireSupply;
import edu.hitsz.props.HealingPackage;

public abstract class PropFactory {
    public static AbstractProps createProp(int locationX, int locationY, int speedX, int speedY){
        //30%掉落火力道具、30%掉落加血道具、 30%掉落炸弹道具,10%不掉落道具
        int random = (int)(Math.random() * 100);
        if(random < 30){
            return new FireSupply(locationX, locationY, speedX, speedY);
        } else if(random < 60){
            return new HealingPackage(locationX, locationY, speedX, speedY);
        } else if(random < 90){
            return new BombSupply(locationX, locationY, speedX, speedY);
        } else {
            return null;
        }
    }
}
