package edu.hitsz.bullet;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyBulletTest {
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

    @DisplayName("test getPower")
    @Test
    void getPower() {
        EnemyBullet enemyBullet = new EnemyBullet(0, 0, 0, 0, 10);
        assertEquals(10, enemyBullet.getPower());
    }

    @DisplayName("test vanish")
    @Test
    void vanish() {
        EnemyBullet enemyBullet = new EnemyBullet(0, 0, 0, 0, 10);
        enemyBullet.vanish();
        assertTrue(enemyBullet.notValid());
    }
}