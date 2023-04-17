package edu.hitsz.aircraft;

import edu.hitsz.application.Game;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.soundEffect.MusicThread;
import edu.hitsz.strategy.HeroStraightShoot;
import edu.hitsz.strategy.ShootStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 *
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {
    private static HeroAircraft instance = null;

    /**攻击方式 */
    /**
     * is firesupply on?
     */
    public boolean isFireSupply = false;
    /**
     * 子弹一次发射数量
     */
    private final int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;

    /**
     * shoot strategy
     */
    private ShootStrategy shootStrategy;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY    英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp        初始生命值
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp);
        this.shootStrategy = shootStrategy;
    }

    public static synchronized HeroAircraft getInstance() {
        if (instance == null) {
            instance = new HeroAircraft(Main.WINDOW_WIDTH / 2,
                    Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight(),
                    0, 0, 5000,
                    new HeroStraightShoot()
            );
        }
        return instance;
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }

    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */

    public List<BaseBullet> shoot() {
        if (Game.soundEffectEnable) {
            new MusicThread("src/videos/bullet.wav", false).start();
        }
        return shootStrategy.shoot(this.getLocationX(), this.getLocationY(), this.getSpeedY(), this.power, this.shootNum, this.direction);
    }

    public void setShootStrategy(ShootStrategy shootStrategy) {
        this.shootStrategy = shootStrategy;
    }

}
