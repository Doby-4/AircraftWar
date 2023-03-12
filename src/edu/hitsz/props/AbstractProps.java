package edu.hitsz.props;

import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.application.Main;
import edu.hitsz.basic.AbstractFlyingObject;

import java.util.List;

/**
 * Abstract parent class of all kinds of props:
 * HealingPackage, FireSupply, BombSupply
 *
 * @author Doby
 */
public abstract class AbstractProps extends AbstractFlyingObject{
    public AbstractProps(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
    }

}
