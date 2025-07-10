package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            mv1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/mv1.png")));
            mv2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/mv2.png")));
            mv3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/mv3.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
        
    }
    
    public void update() {
        // Sprite Animator

        if (keyH.upPressed ||  keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNo == 0) {
                    spriteNo = 1;
                }
                else if (spriteNo == 1) {
                    spriteNo = 0;
                }
                else if (spriteNo == 2) {
                    spriteNo = 0;
                }
                spriteCounter = 0;
            }
        }
        else {
            spriteNo = 2;
            spriteCounter = 0;
        }

        // Character Sprite movement
        if (keyH.upPressed) {
            y -= speed;
            direction = "up";
        }
        if (keyH.downPressed) {
            y += speed;
            direction = "down";
        }
        if (keyH.leftPressed) {
            x -= speed;
            direction = "left";
        }
        if (keyH.rightPressed) {
            x += speed;
            direction = "right";
        }
    }
    
    public void draw (Graphics2D g2) {
        BufferedImage img = null;

        if (spriteNo == 0) {
            img = mv1;
        }
        if (spriteNo == 1) {
            img = mv2;
        }

        if (spriteNo == 2) {
            img = mv3;
        }

        g2.drawImage(img, x, y, gp.tileSize,  gp.tileSize, null);

    }
}
