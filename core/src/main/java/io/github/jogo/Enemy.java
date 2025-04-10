package io.github.jogo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    private float x, y;
    private int health = 20;
    private Texture texture;

    public Enemy(float x, float y) {
        this.x = x;
        this.y = y;
        texture = new Texture("images/skeleton.png");
    }

    public void render(SpriteBatch batch) {
        if (isAlive()) batch.draw(texture, x, y);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, 32, 32);
    }

    public void dispose() {
        texture.dispose();
    }
}
