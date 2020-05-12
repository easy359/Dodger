package Game;

import Engine.Alarm;
import Engine.AlarmTask;
import Engine.HitBox;
import Engine.Static;

import java.util.ArrayList;

public class BlockSpawner implements AlarmTask {

    private ArrayList<Block> blocks;
    private Player player;
    private Alarm spawn, dif;
    private int width, height;

    public BlockSpawner(Player player, int width, int height) {
        blocks = new ArrayList<Block>();
        this.player = player;
        spawn = new Alarm(this, 3);
        dif = new Alarm(this, 3.0);
        Static.TIMER.addAlarm(spawn);
        Static.TIMER.addAlarm(dif);
        this.width = width;
        this.height = height;
    }

    public void update() {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            block.update(player, blocks);
            if (block.getX() > width || block.getX() < (0 - block.getWidth())) {
                blocks.remove(block);
                i--;
            } else if (block.getY() > height || block.getY() < (0 - block.getHeight())) {
                blocks.remove(block);
                i--;
            }
        }
    }

    public void alarm(int alarmCode) {
        if (alarmCode == spawn.getCode()) {
            spawnBlocks();
        } else if (alarmCode == dif.getCode()) {
            if (spawn.getDelay() > 1.0) {
                spawn.changeDelay(((spawn.getDelay() * 10) - 4) / 10);
                System.out.println(spawn.getDelay());
            }
        }
    }

    public void spawnBlocks() {
        int s1 = Static.RAND.nextInt(61) + 20;
        int x1 = Static.RAND.nextInt(width - s1);
        HitBox one = new HitBox(x1, 0, s1, s1);
        if (one.intersects(player.getHitBox())) {
            if (x1 - (s1 / 2) > (width / 2)) {
                one.setX(player.getX() - one.getWidth());
            } else {
                one.setX(player.getX() + player.getHitBox().getWidth());
            }
        }
        int s2 = Static.RAND.nextInt(61) + 20;
        int x2 = Static.RAND.nextInt(width - s2);
        HitBox two = new HitBox(x2, (height - 1) - s2, s2, s2);
        if (two.intersects(player.getHitBox())) {
            if (x2 - (s2 / 2) > (width / 2)) {
                two.setX(player.getX() - two.getWidth());
            } else {
                two.setX(player.getX() + player.getHitBox().getWidth());
            }
        }

        int s3 = Static.RAND.nextInt(61) + 20;
        int y1 = Static.RAND.nextInt(height - s3);
        HitBox three = new HitBox(0, y1, s3, s3);
        if (three.intersects(player.getHitBox())) {
            if (y1 - (s3 / 2) > (height / 2)) {
                three.setY(player.getY() - player.getHitBox().getHeight());
            } else {
                three.setY(player.getY() + player.getHitBox().getHeight());
            }
        }

        int s4 = Static.RAND.nextInt(61) + 20;
        int y2 = Static.RAND.nextInt(height - s4);
        HitBox four = new HitBox((width - 1) - s4, y2, s4, s4);
        if (y2 - (s4 / 2) > (height / 2)) {
            three.setY(player.getY() - player.getHitBox().getHeight());
        } else {
            three.setY(player.getY() + player.getHitBox().getHeight());
        }

        int speed = 4000;

        blocks.add(new Block(Block.Direction.DOWN, one, speed / s1));
        blocks.add(new Block(Block.Direction.UP, two, speed / s2));
        blocks.add(new Block(Block.Direction.RIGHT, three, speed / s3));
        blocks.add(new Block(Block.Direction.LEFT, four, speed / s4));
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

}
