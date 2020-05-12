package Game;

import Engine.Game;
import Engine.GameLoop;
import Engine.HitBox;
import Engine.Static;

import java.awt.*;
import java.util.Arrays;

public class Dodger implements Game {

    public static HitBox boarder;

    private GameLoop gl;
    private Player player;
    private BlockSpawner spawner;
    private int[] pixels;

    private int width, height;

    public Dodger() {

        width = 800;
        height = 600;

        gl = new GameLoop(this, "Dodger", width, height, 30, 60);
        player = new Player();
        spawner = new BlockSpawner(player, width, height);

        pixels = new int[width * height];

        boarder = new HitBox(0, 0, width, height);

        gl.start();
    }

    @Override
    public void update() {
        player.update(spawner.getBlocks());
        spawner.update();
        if (!player.isAlive()) {
            endGame();
        }
    }

    @Override
    public int[] render() {
        Arrays.fill(pixels, Color.gray.getRGB());
        for (Block block : spawner.getBlocks()) {
            Static.addPixels(pixels, width, height, block.render(), block.getX(), block.getY(), block.getWidth(),
                    block.getHeight());
        }
        Static.addPixels(pixels, width, height, player.render(), player.getX(), player.getY(),
                player.getHitBox().getWidth(), player.getHitBox().getHeight());
        return pixels;
    }

    private void endGame() {
    }

    public static void main(String[] args) {
        new Dodger();
    }
}
