package main;

import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    final int orignialTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = orignialTileSize * scale; // 48x48 tile

    public final int maxScreenColumn = 16;
    public final int maxScreenRow = 12; // 4x3 ratio
    final int screenWidth = tileSize * maxScreenColumn; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; //576 pixels

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    // FPS
    int FPS = 24;

    Player player = new Player(this, keyH);
    TileManager tileManager = new TileManager(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);

        g2.dispose();

    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta --;
            }

        }
    }
}