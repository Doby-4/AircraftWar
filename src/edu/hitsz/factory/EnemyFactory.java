package edu.hitsz.factory;

import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.MobEnemy;

public abstract class EnemyFactory {
    public static AbstractAircraft createEnemy(String type, int locationX, int locationY, int speedX, int speedY, int hp){
        if(type == null){
            return null;
        }
        if(type.equalsIgnoreCase("MOB")){
            return new MobEnemy(locationX, locationY, speedX, speedY, hp);
        } else if(type.equalsIgnoreCase("ELITE")){
            return new EliteEnemy(locationX, locationY, speedX, speedY, hp);
        }
        return null;
    }
}
