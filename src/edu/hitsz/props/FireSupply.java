package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

/**
 * @author Doby
 * <p>
 * FireSupply class
 * when the hero touches it, it will  upgrade the hero's fire power
 */
public class FireSupply extends AbstractProps {
    public FireSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        System.out.println("FireSupply active!");
    }
}
