package despacito7;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.google.gson.JsonObject;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Paths;

public class ResourceLoader {
    private static Map<String,Image> itemSprites;
    private static Map<String,Image> monsterSprites;
    private static Image[] tileSprites;

    public static Image getItemSprite(String id) {return itemSprites.get(id);}
    public static Image getMonsterSprite(String id) {return monsterSprites.get(id);}
    public static Image getTileSprite(int id) {return tileSprites[id];}
    
    private String resourcesDirectoryPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/sprites";

    public void loadResources() {
        loadItems();
        loadMonsters();
        loadTiles();
    }

    private void loadItems() {
        File dir = new File(resourcesDirectoryPath+"/items");
        List<File> files = Arrays.stream(dir.listFiles()).filter(e->e.isFile()).toList();
        itemSprites = new HashMap<>(files.size(), 0.99f);
        for(File file : files) {
            itemSprites.put(removeExtension(file.getName()), this.loadImage("sprites/items/"+file.getName()));
        }
    }

    private void loadMonsters() {
        File dir = new File(resourcesDirectoryPath+"/monsters");
        List<File> files = Arrays.stream(dir.listFiles()).filter(e->e.isFile()).toList();
        monsterSprites = new HashMap<>(files.size(), 0.99f);
        for(File file : files) {
            monsterSprites.put(removeExtension(file.getName()), this.loadImage("sprites/monsters/"+file.getName()));
        }
    }

    private void loadTiles() {
        File dir = new File(resourcesDirectoryPath+"/tiles");
        List<File> files = Arrays.stream(dir.listFiles()).filter(e->e.isFile()).toList();
        tileSprites = new Image[files.size()];
        int i = 0;
        for(File file : files) {
            tileSprites[++i] = this.loadImage("sprites/tiles/"+file.getName());
        }
    }

    private String removeExtension(String filename) {return filename.replaceFirst("[.][^.]+$", "");}

    public JsonObject loadJson(String filename) {
        try (Reader reader = new InputStreamReader(getClass().getClassLoader().getResourceAsStream(filename))) {
            String text = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
            return App.gson.fromJson(text, JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private java.awt.Image loadImage(String filename) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            System.out.println("Image Failed To Load: "+filename);
            return null;
        }
    }
}
