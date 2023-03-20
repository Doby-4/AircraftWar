package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.BombFactory;
import edu.hitsz.factory.FireFactory;
import edu.hitsz.factory.HealingFactory;
import edu.hitsz.factory.PropFactory;
import edu.hitsz.props.AbstractProps;

import java.util.LinkedList;
import java.util.List;

/**
 * EliteEnemy
 * able to shoot
 * if destroied, drop one prop
 *
 * @author Doby
 */
public class EliteEnemy extends AbstractAircraft{
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp){
        super(locationX, locationY, speedX, speedY, hp);
    }
    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT) {
            vanish();
        }
    }

    @Override
    public AbstractProps DropProp(int locationX, int locationY, int speedX, int speedY) {
        //30%掉落火力道具、30%掉落加血道具、 30%掉落炸弹道具,10%不掉落道具
        int random = (int) (Math.random() * 100);
        if (random < 30) {
            PropFactory FireF = new FireFactory();
            return FireF.createProp(locationX, locationY, speedX, speedY);
        } else if (random < 60) {
            PropFactory HealingF = new HealingFactory();
            return HealingF.createProp(locationX, locationY, speedX, speedY);
        } else if (random < 90) {
            PropFactory BombF = new BombFactory();
            return BombF.createProp(locationX, locationY, speedX, speedY);
        } else {
            return null;
        }

    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> res = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet bullet;
        for (int i = 0; i < shootNum; i++) {
            bullet = new EnemyBullet(x + (i*2 - shootNum + 1)*10, y, speedX, speedY, power);
            res.add(bullet);
        }
        return res;
    }




}
