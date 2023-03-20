package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * @author Doby
 * <p>
 * HealingPackage class
 * when the hero touches it, it will recover 50 HP
 */
public class HealingPackage extends AbstractProps {
    public HealingPackage(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        heroAircraft.increaseHp(50);
    }
}
