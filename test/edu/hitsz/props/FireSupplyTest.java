package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FireSupplyTest {
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();

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
        System.setOut(System.out);
        System.out.println("tearDown");
    }

    @AfterAll
    static void tearDownAll() {
        System.setOut(System.out);
        System.out.println("tearDownAll");
    }

    @DisplayName("test crash")
    @Test
    void crash() {
        HeroBullet heroBullet = new HeroBullet(0, 0, 0, 0, 10);
        FireSupply fireSupply = new FireSupply(0, 0, 0, 0);
        assertTrue(fireSupply.crash(heroBullet));
    }

    @DisplayName("test active")
    @Test
    void active() {
        HeroAircraft heroAircraft = HeroAircraft.getInstance();
        FireSupply fireSupply = new FireSupply(0, 0, 0, 0);

        System.setOut(new PrintStream(outContent));
        fireSupply.active(heroAircraft);
        System.setOut(System.out);
        assertEquals("FireSupply active!", outContent.toString().trim());
    }


}