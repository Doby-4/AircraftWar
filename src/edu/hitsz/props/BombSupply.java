package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.application.Game;
import edu.hitsz.soundEffect.MusicThread;

public class BombSupply extends AbstractProps {
    public BombSupply(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void active(HeroAircraft heroAircraft) {
        if (Game.soundEffectEnable) {
            new MusicThread("src/videos/get_supply.wav", false).start();
            new MusicThread("src/videos/bomb_explosion.wav", false).start();
        }
        System.out.println("BombSupply active!");
    }

}
