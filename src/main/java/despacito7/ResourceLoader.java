package despacito7;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ResourceLoader {
    private static Map<String,Image> itemSprites;
    private static Map<String,Image> monsterSprites;
    private static Image[] tileSprites;
    private static boolean loaded = false;

    public static Image getItemSprite(String id) {return itemSprites.get(id);}
    public static Image getMonsterSprite(String id) {return monsterSprites.get(id);}
    public static Image getTileSprite(int id) {return id < 0 ? tileSprites[tileSprites.length+id] : tileSprites[id];}
    
    private String resourcesDirectoryPath = java.nio.file.Paths.get("").toAbsolutePath().toString() + "/src/main/resources/sprites";

    public boolean isLoaded() {
        return ResourceLoader.loaded;
    }

    public void load() {
        loadItems();
        loadMonsters();
        loadTiles();
        ResourceLoader.loaded = true;
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
        ResourceLoader.tileSprites = new Image[files.size()];
        for(File file : files) {
            ResourceLoader.tileSprites[Integer.parseInt(file.getName().replaceAll("\\D", ""))] = this.loadImage("sprites/tiles/"+file.getName());
        }
    }

    private String removeExtension(String filename) {return filename.replaceFirst("[.][^.]+$", "");}

    private java.awt.Image loadImage(String filename) {
        try {
            return ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename));
        } catch (IOException e) {
            System.out.println("Image Failed To Load: "+filename);
            return null;
        }
    }

    public static Image[] createCharacterSprites(int playerNum){
        int i = 0;
        Image[] characterSprites = new Image[12];
        //locate which row in the tilelayerguide the player starts at
        int row = 0 + playerNum*3;
        //locate which tile num col in the tilelayerguide the player starts at
        int tilenum = (27*(row+1))-4;
        //for loop that will go through each column
        for(int c = tilenum; c<tilenum+4; c++){
          for(int r = 0; r< 3;r++){
            characterSprites[i] = tileSprites[c + (r*27)];
            i++;
          }
        }
        return characterSprites;
    }
    public static Image[] cutSprites(Image spritesheet){
        BufferedImage spriteImage = (BufferedImage) spritesheet;
        List<Image> sprites = new ArrayList<>();
        for (int r = 0; r < spriteImage.getWidth(); r+=Constants.tilesize) {
            for (int c = 0; c < spriteImage.getHeight(); r+=Constants.tilesize) {
                sprites.add(spriteImage.getSubimage(r, c, Constants.tilesize, Constants.tilesize));
            }
        }
        return sprites.toArray(new Image[sprites.size()]);
    }
}
