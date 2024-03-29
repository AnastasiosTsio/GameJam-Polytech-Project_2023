package info3.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import info3.game.Camera;
import info3.game.Game;

public class EntityView {

    public BufferedImage[] images;
    public int imageIndex;
    int width, height;

    public Entity entity;

    public EntityView(Entity entity) {
        this.entity = entity;
    }

    public EntityView(BufferedImage[] images, Entity entity) {
        this.images = images;
        this.width = images[0].getWidth();
        this.height = images[0].getHeight();
        this.entity = entity;
    }

    public EntityView(String filename, int nrows, int ncols, Entity entity) {
        try {
            images = Entity.loadSprite(filename, nrows, ncols);
            this.width = images[0].getWidth();
            this.height = images[0].getHeight();
            this.entity = entity;
        } catch (Exception e) {
            System.out.println("Error loading sprite: " + filename);
            System.exit(1);
        }
    }

    public BufferedImage getImage() {
        return images[imageIndex];
    }

    public void tick(long elapsed) {
    }

    public void paint(Graphics g) {
        Camera.drawEntity(entity, g);
    }

}
