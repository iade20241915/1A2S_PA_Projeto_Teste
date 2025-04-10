package io.github.jogo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private Dungeon dungeon;
    private int currentRoomX = 0;
    private int currentRoomY = 0;
    private BitmapFont font;
    private Stage stage;
    private ProgressBar healthBar;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        player = new Player(100, 100);
        dungeon = new Dungeon(3, 1, player);
        dungeon.getRoom(0, 0).spawnEnemy(new Enemy(400, 300));
        dungeon.getRoom(0, 0).addWall(new Wall(300, 300, 32, 32));
        dungeon.getRoom(0, 0).addWall(new Wall(500, 200, 32, 32));
        dungeon.getRoom(0, 0).addWall(new Wall(400, 120, 32, 32));

        // Progress bar manual
        stage = new Stage(new ScreenViewport());
        Pixmap pixGreen = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixGreen.setColor(0, 1, 0, 1);
        pixGreen.fill();
        Texture tex = new Texture("images/knight.png");
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(new Texture("images/wall.png")));
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(tex));
        healthBar = new ProgressBar(0, player.getMaxHealth(), 1, false, style);
        healthBar.setValue(player.getHealth());
        healthBar.setSize(200, 20);
        healthBar.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(healthBar);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.update(Gdx.graphics.getDeltaTime(), getCurrentRoom());

        batch.begin();
        for (Wall wall : getCurrentRoom().getWalls()) {
            batch.draw(wall.getTexture(), wall.getBounds().x, wall.getBounds().y);
        }

        for (Enemy enemy : getCurrentRoom().getEnemies()) {
            enemy.update(Gdx.graphics.getDeltaTime(), getCurrentRoom());
            enemy.render(batch);
        }

        player.render(batch);

        if (player.getHealth() <= 0) {
            font.draw(batch, "GAME OVER", 350, 300);
        }

        batch.end();

        healthBar.setValue(player.getHealth());
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private Room getCurrentRoom() {
        return dungeon.getRoom(currentRoomX, currentRoomY);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        stage.dispose();
    }
}
