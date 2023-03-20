package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;

public class BombSupply extends AbstractProps {
    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        System.out.println("BombSupply active!");
    }

}
