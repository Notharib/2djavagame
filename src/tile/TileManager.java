package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNo;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];
        mapTileNo = new int[gp.maxScreenColumn][gp.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/maps/tileMap");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxScreenColumn && row < gp.maxScreenRow) {
                String line = br.readLine();
                while (col < gp.maxScreenColumn) {
                    String[] numbers = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNo[col][row] = num;
                    col ++;
                }
                if (col == gp.maxScreenColumn) {
                    col = 0;
                    row ++;
                }
            }
            br.close();
        }
        catch (Exception e) {
            System.err.println("Error loading tile map!");
            System.out.println(e.getMessage());
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.maxScreenColumn && row < gp.maxScreenRow) {
            int tileNo = mapTileNo[col][row];

            g2.drawImage(tile[tileNo].image, x, y, gp.tileSize, gp.tileSize, null);
            col ++;
            x += gp.tileSize;

            if (col == gp.maxScreenColumn) {
                col = 0;
                x = 0;
                row ++;
                y += gp.tileSize;
            }

        }

    }
}
