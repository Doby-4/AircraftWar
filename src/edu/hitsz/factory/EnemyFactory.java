package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;

/**
 * @author Doby
 */

public interface EnemyFactory {
    AbstractAircraft createEnemy();

}
