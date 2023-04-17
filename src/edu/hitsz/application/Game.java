package edu.hitsz.application;

import edu.hitsz.SwingUI.RankingBoard;
import edu.hitsz.aircraft.AbstractAircraft;
import edu.hitsz.aircraft.BossEnemy;
import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.factory.*;
import edu.hitsz.props.AbstractProps;
import edu.hitsz.ranking.Score;
import edu.hitsz.ranking.ScoreDAO;
import edu.hitsz.ranking.ScoreDAOImpl;
import edu.hitsz.soundEffect.MusicThread;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public class Game extends JPanel {

    private int backGroundTop = 0;

    /**
     * sound effect enable
     */
    public static boolean soundEffectEnable;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private final int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProps> props;
    private EnemyFactory enemyFactory;
    public static PropFactory propFactory;
    /**
     * 游戏背景图片
     */
    public static BufferedImage background;

    /**
     * 屏幕中出现的敌机最大数量
     */
    private final int enemyMaxNumber = 5;
    /**
     * 标志是否有boss机存在
     */

    private Boolean isBossExist = false;

    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private final int cycleDuration = 600;
    private int cycleTime = 0;

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    /**
     * ranking list DAO
     */
    private ScoreDAO scoreDAO;

    /**
     * 分数数组
     */
    public List<Score> scores;

    /**
     * 游戏背景音乐线程
     */
    private MusicThread bgMusic;

    /**
     * boss机线程
     */
    private MusicThread bossMusic;


    public Game() {
        heroAircraft = HeroAircraft.getInstance();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();
        //启动背景音乐
        if (soundEffectEnable) {
            bgMusic = new MusicThread("src/videos/bgm.wav", true);
            bgMusic.start();
        }

        /*
          Scheduled 线程池，用于定时任务调度
          关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
          apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {

        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        Runnable task = () -> {

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新周期，敌机入场
                enemyIn();
                // 飞机射出子弹
                shootAction();
            }


            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            // 道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                if (soundEffectEnable) {
                    new MusicThread("src/videos/game_over.wav", false).start();
                    // stop bg music
                    bgMusic.stopMusic();
                    if (isBossExist) {
                        bossMusic.stopMusic();
                    }
                }

                scoreDAO = new ScoreDAOImpl();
                String name = JOptionPane.showInputDialog("Please input your name:");
                Score scoreForThisGame = new Score(this.score, name);
                System.out.println(scoreForThisGame);
                scoreDAO.addScore(scoreForThisGame);
                scoreDAO.sortScore();

                // 切换页面至RankingBoard
                Main.cardPanel.add(new RankingBoard(scoreDAO).getMainPanel(), "RankingBoard");
                Main.cardLayout.show(Main.cardPanel, "RankingBoard");

            }

        };



/*
  以固定延迟时间进行执行
  本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
 */

        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    private void enemyIn() {
//         新敌机产生
//        随机产生MobEnemy或者EliteEnemy,当分数是200的整数倍时，产生BossEnemy
        if (score % 200 == 0 & score != 0 & isBossExist == false) {
            enemyFactory = new BossFactory();
            enemyAircrafts.add(enemyFactory.createEnemy());
            isBossExist = true;
            if (soundEffectEnable) {
                bossMusic = new MusicThread("src/videos/bgm_boss.wav", true);
                bossMusic.start();
            }
        }
        if (enemyAircrafts.size() < enemyMaxNumber) {
            if (Math.random() < 0.8) {
                enemyFactory = new MobFactory();
                enemyAircrafts.add(enemyFactory.createEnemy());
            } else {
                enemyFactory = new EliteFactory();
                enemyAircrafts.add(enemyFactory.createEnemy());
            }
        }
    }


    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // TODO 敌机射击
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {

                enemyBullets.addAll(enemyAircraft.shoot());

        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction() {
        for (AbstractProps prop : props) {
            prop.forward();
        }
    }


    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // TODO 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets) {
            if (bullet.notValid()) {
                continue;
            }
            if (heroAircraft.crash(bullet)) {
                // 英雄机损失一定生命值
                heroAircraft.decreaseHp(bullet.getPower());
                bullet.vanish();
            }
        }

        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (soundEffectEnable) {
                        new MusicThread("src/videos/bullet_hit.wav", false).start();
                    }
                    if (enemyAircraft.notValid()) {
                        // TODO 获得分数，产生道具补给
                        // BossEnemy死亡，设置标志为false
                        if (enemyAircraft instanceof BossEnemy) {
                            isBossExist = false;
                            if (soundEffectEnable) {
                                bossMusic.stopMusic();
                            }
                        }
                        //dropProp
                        List<AbstractProps> tempProps = new LinkedList<>();
                        tempProps.addAll(enemyAircraft.dropProp(enemyAircraft.getLocationX(),
                                enemyAircraft.getLocationY(),
                                0,
                                (int) (enemyAircraft.getSpeedY() * 0.5)
                        ));
                        if (!tempProps.isEmpty()) {
                            //drop a prop
                            props.addAll(tempProps);
                        }
                        score += 10;
                    }
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    //hack
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // Todo: 我方获得道具，道具生效
        for (AbstractProps prop : props) {
            if (prop.notValid()) {
                continue;
            }
            if (heroAircraft.crash(prop)) {
                prop.active(heroAircraft);
                prop.vanish();
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(background, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(background, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);

        paintImageWithPositionRevised(g, enemyAircrafts);
        paintImageWithPositionRevised(g, props);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
