package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.factory.BombFactory;
import edu.hitsz.factory.FireFactory;
import edu.hitsz.factory.HealingFactory;
import edu.hitsz.props.AbstractProps;

import java.util.LinkedList;
import java.util.List;

public class BossEnemy extends AbstractAircraft {
    public BossEnemy(int locationX, int locationY, int speedX, int speedY, int hp) {
        super(locationX, locationY, speedX, speedY, hp);
    }

    private int shootnum = 3;

    private int power = 50;

    private int direction = 1;

    @Override
    public List<AbstractProps> DropProp(int locationX, int locationY, int speedX, int speedY) {
        //掉落三个随机道具
        List<AbstractProps> res = new LinkedList<>();
        for (int i = -1; i < 2; i++) {
            int random = (int) (Math.random() * 90);
            if (random < 30) {
                Game.propFactory = new FireFactory();
                res.add(Game.propFactory.createProp(locationX + i * 40, locationY, 0, 7));
            } else if (random < 60) {
                Game.propFactory = new HealingFactory();
                res.add(Game.propFactory.createProp(locationX + i * 40, locationY, 0, 7));
            } else if (random < 90) {
                Game.propFactory = new BombFactory();
                res.add(Game.propFactory.createProp(locationX + i * 40, locationY, 0, 7));
            }
        }
        return res;
    }

    @Override
    public List<BaseBullet> shoot() {
        List<BaseBullet> bullets = new LinkedList<>();
        int x = this.getLocationX();
        int y = this.getLocationY() + direction * 2;
        int speedX = 0;
        int speedY = this.getSpeedY() + direction * 5;
        BaseBullet bullet;
        //扇形发射三个子弹
        for (int i = 0; i < shootnum; i++) {
            bullet = new EnemyBullet(x + (i * 2 - shootnum + 1) * 10, y, speedX + (i - 1) * 2, speedY, power);
            bullets.add(bullet);
        }
        return bullets;
    }


}
