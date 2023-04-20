package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.strategy.EnemyStraightShoot;


/**
 * @author Doby Shao
 */
public class EliteFactory implements EnemyFactory {
    @Override
    public AbstractAircraft createEnemy() {
        return new EliteEnemy((int) (Math.random() * (Main.WINDOW_WIDTH - ImageManager.ELITE_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.05),
                0,
                7,
                30,
                new EnemyStraightShoot()
        );
    }
}
