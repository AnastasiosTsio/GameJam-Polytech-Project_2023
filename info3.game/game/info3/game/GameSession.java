package info3.game;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import entity.Player;
import entity.Entity;


public class GameSession {
    public Game game;
    public static GameSession gameSession;

    public Player player1;
    public Player player2;

    public Camera camera;

    List<Entity> entities;
    public Map map;

    boolean camView = false;

    public GameSession(Game game, String mapPath) throws IOException {
        this.game = game;
        map = new Map(mapPath);
        loadEntities(mapPath);

        player1 = new Player();
        player2 = new Player();

        camera = new Camera();

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
            entities.add(IdToEntity(id, x, y));
        }
    }

    private Entity IdToEntity(String id, int x, int y) {
        switch (id) {
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
        if (camView) {
            camPaint(g);
        } else {
            normalPaint(g);
        }
    }
    public void normalPaint(Graphics g) {
        camera.paint(g);
        map.paint(g);
        for (Entity entity : entities) {
            entity.paint(g);
        }
        player1.paint(g);
        player2.paint(g);
    }

    public void camPaint(Graphics g) {
        map.camPaint(g);
        for (Entity entity : entities) {
            entity.camPaint(g);
        }
        player1.camPaint(g);
        player2.camPaint(g);
    }

    int getLevelWidth() {
        return map.realWidth();
    }

    int getLevelHeight() {
        return map.realHeight();
    }

    public void toggleCameraDebug() {
        camView = !camView;
    }



}
