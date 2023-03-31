package edu.hitsz.aircraft;

import edu.hitsz.factory.FireFactory;
import edu.hitsz.factory.HealingFactory;
import edu.hitsz.props.FireSupply;
import edu.hitsz.props.HealingPackage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeroAircraftTest {
    @BeforeAll
    static void setUp() {
        System.out.println("HeroAircraftTest");
    }

    @BeforeEach
    void init() {
        System.out.println("init");
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("tearDownAll");
    }

    @DisplayName("test crash")
    @Test
    void crash() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setLocation(200, 200);
        FireFactory fireFactory = new FireFactory();
        FireSupply fireSupply = (FireSupply) fireFactory.createProp(
                200,
                200,
                0,
                0
        );
        assertTrue(heroAircraft.crash(fireSupply));
    }

    @DisplayName("test increaseHp")
    @Test
    void increaseHp() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        heroAircraft.setLocation(200, 200);
        HealingFactory healingFactory = new HealingFactory();
        HealingPackage healingPackage = (HealingPackage) healingFactory.createProp(
                200,
                200,
                0,
                0
        );
        heroAircraft.decreaseHp(50);
        int hp = heroAircraft.getHp();
        assertTrue(heroAircraft.crash(healingPackage));
        healingPackage.active(heroAircraft);
        assertEquals(hp + 50, heroAircraft.getHp());
    }

    @DisplayName("test shoot")
    @Test
    void shoot() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        assertEquals(3, heroAircraft.shoot().size());
    }
}