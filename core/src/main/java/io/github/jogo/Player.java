package io.github.jogo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Vector2 position;
    private Texture texture;
    private int health = 100;
    private int maxHealth = 100;



    public Player(float x, float y) {
        this.position = new Vector2(x, y);
        this.texture = new Texture("images/knight.png");
    }

    public void update(float deltaTime, Room room) {
        float speed = 200 * deltaTime;
        float newX = position.x;
        float newY = position.y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) newX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) newX += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) newY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) newY -= speed;

        if (!room.isCollidingWithWalls(new Vector2(newX, newY))) {
            position.set(newX, newY);
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }
}
