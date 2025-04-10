package io.github.jogo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Wall {
    private Rectangle bounds;
    private Texture texture;

    public Wall(float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
        this.texture = new Texture("images/wall.png");
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, bounds.x, bounds.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Texture getTexture() {
        return texture;
    }
}