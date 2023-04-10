package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.EnemySectorShoot;

/**
 * Boss工厂
 * 用于生成Boss
 *
 * @author doby
 */
public class BossFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        return new BossEnemy(
                (int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.BOSS_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05 + 90),
                9,
                0,
                650,
                new EnemySectorShoot()
        );
    }
}
