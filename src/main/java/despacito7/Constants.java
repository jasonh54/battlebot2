package despacito7;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;
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
        MAX_HEALTH,
        HEALTH,
        DEFENSE,
        DAMAGE,
        AGILITY;

        public static Map<Stat, Float> toMap(JsonObject jo) {
            return jo.entrySet().stream()
                .map(e -> Map.<Stat, Float>entry(Stat.valueOf(e.getKey()), e.getValue().getAsFloat()))
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
        UP, DOWN, LEFT, RIGHT, IDLE;
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

    public static enum GameState {WORLD, BATTLE, MENU, TALK}

    // public static enum 

    public static boolean leftMouseClick = false;

    //tile arrays for all "special" tiles
    public static final ArrayList<Integer> collideTiles = new ArrayList<Integer>(Arrays.asList(170,171,172,189,190,191,192,193,194,195,196,197,198,199,216,217,218,219,220,221,222,223,224,225,226,237,238,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,270,271,272,273,274,275,276,278,279,280,286,287,288,289,290,291,292,297,298,299,300,301,302,303,304,305,306,307,327,328,329,330,331,332,333,334,335,336,337,338,340,341,342,344,345,346,354,355,356,357,358,359,360,361,362,363,364,365,367,368,369,370,371,372,373,381,382,383,384,385,386,387,388,389,390,391,392,414,415,416,417,418,419,420,421,422,423,424,425,426,427,443,444,445,446,453,454,470,471,472,473,474,475,476,477,478,479,480,481));
    public static final ArrayList<Integer> monsterTiles = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5,6,7,27,28,29,30,31,32,33,34,54,55,56,57,58,59,60,61));
}
