package io.github.jogo;

public class Dungeon {
    private Room[][] rooms;

    public Dungeon(int width, int height, Player player) {
        rooms = new Room[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rooms[x][y] = new Room(player);
            }
        }
    }

    public Room getRoom(int x, int y) {
        return rooms[x][y];
    }
}