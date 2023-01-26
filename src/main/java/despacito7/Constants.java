package despacito7;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.JsonObject;

public final class Constants {
    public static final int tilesize = 16;

    public static final int TPS = 20;
    public static final int FPS = 60;

    public static class Images {
        public static final Image MonsterIcon = null;
        public static final Image ItemsIcon = null;
        public static final Image SettingsIcon = null;
        public static final Image ExitIcon = null;
    }

    public static enum Stat {
        MAX_HEALTH, HEALTH,
        DEFENSE,
        DAMAGE,
        AGILITY;

        public static Map<Stat, Number> toMap(JsonObject jo) {
            return jo.entrySet().stream()
                .map(e -> Map.<Stat, Number>entry(Stat.valueOf(e.getKey()), e.getValue().getAsNumber()))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        }
    }

    public static enum Type {
        WATER, EARTH, FIRE, AIR, // long ago, the four nations lived together in harmony
        NORMAL, FIGHTING
    }
    
    private static Map<Character, Direction> keyToDir = new HashMap<>() {{
        put('w', Direction.UP);
        put('a', Direction.LEFT);
        put('s', Direction.DOWN);
        put('d', Direction.RIGHT);
    }};
    public static enum Direction {
        UP, DOWN, LEFT, RIGHT;
        int toInt() {
            Direction[] dirs = Direction.values();
            for (int i = 0; i < dirs.length; i++) {
                if (this.equals(dirs[i])) return i;
            }
            return -1;
        }
        static Direction fromKey(char key) {
            return keyToDir.get(key);
        }
    }

    public static enum MoveState {IDLE, WALK}

    // public static enum 
}
