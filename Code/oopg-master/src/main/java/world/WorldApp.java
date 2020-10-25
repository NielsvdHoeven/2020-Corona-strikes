package world;

// deze 3 classes moet je geimporteerd hebben om het te laten werken.

import nl.han.ica.oopg.engine.GameEngine;
import nl.han.ica.oopg.objects.Sprite;
import nl.han.ica.oopg.tile.TileMap;
import nl.han.ica.oopg.tile.TileType;
import nl.han.ica.oopg.view.View;

public class WorldApp extends GameEngine {

    public static String MEDIA_URL = "src/main/java/media/";

    Player player;
    Human[] humans = new Human[2];
    Knop knop;
    Maps maps;
    Sanitizer sanitizer;

    public int level = 0;
    private int hiddenPlatform = -1;

    public static void main(String[] args) {
        WorldApp wa = new WorldApp();

        // deze methode start de game.
        wa.runSketch();
    }

    @Override
    public void setupGame() {
        int worldWidth = 1200;
        int worldHeight = 800;

        player = new Player(this);
        knop = new Knop(this, 1);
        maps = new Maps(this);
        sanitizer = new Sanitizer(800, 100);
        player.setY(500);
        player.setX(700);
        initializeHumans();

        addGameObject(sanitizer);
        addGameObject(knop);
        addGameObject(player);

        View view = new View(worldWidth, worldHeight);

        view.setBackground(loadImage(MEDIA_URL.concat("background.jpg")));

        setView(view);

        size(worldWidth, worldHeight);
        initializeStandardTileMap();
    }

    @Override
    public void update() {
        maps.setMap();
        initializeTileMap();
        hiddenPlatform = knop.getPlatform();
    }

    @Override
    public void keyPressed() {
        player.keyPressed();
    }

    public void keyReleased() {
        player.keyReleased();
    }

    private void initializeTileMap() {
        // Load Sprites
        Sprite floorSprite = new Sprite(this.MEDIA_URL.concat("platformPack_tile040.png"));
        // Create tile types with the right Tile class and sprite
        TileType<FloorTile> floorTileType = new TileType<>(FloorTile.class, floorSprite);

        TileType[] tileTypes = {floorTileType};
        int tileSize = 50;
        int tilesMap[][] = maps.getMap();
        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    private void initializeStandardTileMap() {
        // Load Sprites
        Sprite floorSprite = new Sprite(this.MEDIA_URL.concat("platformPack_tile040.png"));
        // Create tile types with the right Tile class and sprite
        TileType<FloorTile> floorTileType = new TileType<>(FloorTile.class, floorSprite);

        TileType[] tileTypes = {floorTileType};
        int tileSize = 50;
        int tilesMap[][] = {
                {-1},
                {-1},
        };
        tileMap = new TileMap(tileSize, tileTypes, tilesMap);
    }

    public void initializeHumans() {
        for (int i = 0; i < humans.length; i++) {
            humans[i] = new Human(this);
        }

        if (level == 0) {
            humans[0].setPosition(500, 300);
            humans[1].setPosition(100, 600);
            addGameObject(humans[0]);
            addGameObject(humans[1]);
        }

        if (level == 1) {
            for (int i = 0; i < humans.length; i++) {
                humans[i].setInfected(false);
            }
            humans[0].setPosition(600, 400);
            humans[1].setPosition(100, 100);
            addGameObject(humans[0]);
            addGameObject(humans[1]);
        }
    }
}


/**
 * {
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1},
 * {-1, hiddenPlatform, hiddenPlatform, hiddenPlatform, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0},
 * {-1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
 * {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
 * };
 */
