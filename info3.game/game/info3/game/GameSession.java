package info3.game;

import java.awt.Graphics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import info3.game.entity.Block;
import info3.game.entity.Entity;
import info3.game.entity.Player;
import info3.game.entity.blocks.MovingPlatform;


public class GameSession {
    public Game game;
    public static GameSession gameSession;

    public Player player1;
    public Player player2;

    public Camera camera;

    List<Entity> entities;
    public Map map;

    public GameSession(Game game, String mapPath) throws IOException {
        this.game = game;
        map = new Map(mapPath);
        loadEntities(mapPath);
        camera = new Camera();
        player1 = new Player(1);
        player2 = new Player(2);
        gameSession = this;
    }

    private void loadEntities(String filename) throws IOException {
        entities = new ArrayList<Entity>();
        String content = Map.readFile(filename);
        JSONObject json = new JSONObject(content);
        JSONArray jsonEntities = json.getJSONArray("entities");
        for (int i = 0; i < jsonEntities.length(); i++) {
            JSONObject jsonEntity = jsonEntities.getJSONObject(i);
            String id = jsonEntity.getString("id");
            int x = jsonEntity.getInt("x");
            int y = jsonEntity.getInt("y");
            JSONObject tags = jsonEntity.getJSONObject("tags");
            // If it need somes tags...
            entities.add(IdToEntity(id, x*Block.BLOCK_SIZE, y*Block.BLOCK_SIZE, tags));
        }
    }

    private Entity IdToEntity(String id, int x, int y, JSONObject tags) throws IOException {
        switch (id) {
            case "MovingPlatform" :
                int moveX = tags.getInt("blockMove");
                int speed = tags.getInt("speed");
                return new MovingPlatform(x, y, moveX*Block.BLOCK_SIZE, speed);
            default :
                return null ;
        }
    }

    public void tick(long elapsed) {
        player1.tick(elapsed);
        player2.tick(elapsed);
        for (Entity entity : entities) {
            entity.tick(elapsed);
        }
        camera.tick(elapsed);
    }

    public void paint(Graphics g) {
        camera.paint(g);
        map.paint(g);
        for (Entity entity : entities) {
            entity.paint(g);
        }
        player1.paint(g);
        player2.paint(g);
    }

    int getLevelWidth() {
        return map.realWidth();
    }

    int getLevelHeight() {
        return map.realHeight();
    }




}
