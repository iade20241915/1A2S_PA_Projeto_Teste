package io.github.jogo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture knight, background, wall;
    float x, y;
    Room[][] rooms;
    int roomX = 0, roomY = 0;


    @Override
    public void create() {
        //batch = new SpriteBatch();
        //image = new Texture("libgdx.png");
        batch = new SpriteBatch();
        knight = new Texture("images/knight.png");
        background = new Texture("images/background_dungeon.png");
        wall = new Texture("images/wall.png");


        rooms = new Room[3][1]; // 3 salas na horizontal
        for (int i = 0; i < 3; i++) {
            rooms[i][0] = new Room();
            rooms[i][0].generate(); // geração das casas
        }

        x = 100;
        y = 100;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 200 * delta;
        float newX = x, newY = y;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) newX -= speed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) newX += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) newY += speed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) newY -= speed;

        Rectangle future = new Rectangle(newX, newY, 32, 32);
        boolean collision = false;
        for (Rectangle r : getCurrentRoom().obstacles) {
            if (r.overlaps(future)) {
                collision = true;
                break;
            }
        }

        if (!collision) {
            x = newX;
            y = newY;
        }

        // Transição de salas
        if (x > 800) {
            if (roomX < rooms.length - 1) { roomX++; x = 0; }
        } else if (x < 0) {
            if (roomX > 0) { roomX--; x = 800; }
        }

        // Combate
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Rectangle playerRect = new Rectangle(x, y, 32, 32);
            for (Enemy e : getCurrentRoom().enemies) {
                if (e.isAlive() && e.getRect().overlaps(playerRect)) {
                    e.takeDamage(10);
                    break;
                }
            }
        }

        // Render
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        for (Rectangle r : getCurrentRoom().obstacles) {
            batch.draw(wall, r.x, r.y);
        }
        for (Enemy e : getCurrentRoom().enemies) {
            e.render(batch);
        }
        batch.draw(knight, x, y);
        batch.end();
    }

    public Room getCurrentRoom() {
        return rooms[roomX][roomY];
    }

    @Override
    public void dispose() {
        batch.dispose();
        knight.dispose();
        background.dispose();
        wall.dispose();
        for (Room[] row : rooms)
            for (Room r : row)
                if (r != null)
                    r.dispose();
    }
}
