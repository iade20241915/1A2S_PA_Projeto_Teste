/* Estrutura base de um Dungeon Krawler com tema de cavaleiro e espadas usando Java e libGDX */

// Main.java
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new GameScreen());
    }

    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Knight's Dungeon";
        config.width = 800;
        config.height = 600;
        new LwjglApplication(new Main(), config);
    }
}

// Entity.java
public abstract class Entity {
    protected Vector2 position;
    protected int health;
    protected int attack;

    public Entity(Vector2 position, int health, int attack) {
        this.position = position;
        this.health = health;
        this.attack = attack;
    }

    public abstract void update(float delta);
    public abstract void render(SpriteBatch batch);

    public void takeDamage(int damage) {
        this.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}

// Player.java
public class Player extends Entity {
    private Inventory inventory;
    private Texture texture;

    public Player(Vector2 startPosition) {
        super(startPosition, 100, 15);
        this.inventory = new Inventory();
        this.texture = new Texture("knight.png"); // sprite de cavaleiro
    }

    @Override
    public void update(float delta) {
        // lógica de input de movimento
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public Inventory getInventory() {
        return inventory;
    }
}

// Enemy.java
public class Enemy extends Entity {
    private Texture texture;

    public Enemy(Vector2 position) {
        super(position, 50, 10);
        this.texture = new Texture("skeleton.png"); // sprite de inimigo
    }

    @Override
    public void update(float delta) {
        // lógica básica de IA
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }
}

// Item.java
public abstract class Item {
    protected String name;

    public Item(String name) {
        this.name = name;
    }

    public abstract void use(Player player);

    public String getName() {
        return name;
    }
}

// Weapon.java
public class Weapon extends Item {
    private int bonusAttack;

    public Weapon(String name, int bonusAttack) {
        super(name);
        this.bonusAttack = bonusAttack;
    }

    @Override
    public void use(Player player) {
        player.attack += bonusAttack;
    }
}

// Potion.java
public class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Player player) {
        player.health += healAmount;
    }
}

// Inventory.java
public class Inventory {
    private List<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void useItem(int index, Player player) {
        if (index >= 0 && index < items.size()) {
            items.get(index).use(player);
            items.remove(index);
        }
    }
}

// Room.java
public class Room {
    private List<Enemy> enemies;
    private List<Item> items;

    public Room() {
        enemies = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void spawnEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void placeItem(Item item) {
        items.add(item);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Item> getItems() {
        return items;
    }
}

// Dungeon.java
public class Dungeon {
    private Room[][] rooms;

    public Dungeon(int width, int height) {
        rooms = new Room[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rooms[x][y] = new Room();
            }
        }
    }

    public Room getRoom(int x, int y) {
        return rooms[x][y];
    }
}

// GameScreen.java
public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Player player;
    private Dungeon dungeon;
    private int currentRoomX = 0;
    private int currentRoomY = 0;

    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new Player(new Vector2(100, 100));
        dungeon = new Dungeon(5, 5);
        dungeon.getRoom(0, 0).spawnEnemy(new Enemy(new Vector2(200, 200)));
        dungeon.getRoom(0, 0).placeItem(new Weapon("Espada Longa", 5));
        dungeon.getRoom(0, 0).placeItem(new Potion("Poção de Vida", 20));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        player.render(batch);
        for (Enemy enemy : getCurrentRoom().getEnemies()) {
            enemy.render(batch);
        }
        batch.end();
    }

    public Room getCurrentRoom() {
        return dungeon.getRoom(currentRoomX, currentRoomY);
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); }
}
