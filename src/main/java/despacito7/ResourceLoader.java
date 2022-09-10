package despacito7;

import java.util.HashMap;
import java.util.Map;
import java.awt.Image;
import java.io.File;
import java.nio.file.Paths;

public class ResourceLoader {
    public static Map<String,Image> itemImages = new HashMap<String,Image>();
    public static Map<String,Image> monsterImages = new HashMap<String,Image>();
    public static Image[] tileImages;
    String resourcesDirectoryPath = Paths.get("").toAbsolutePath().toString() + "/src/main/resources/images";

    public void loadResources(){
        loadItems();
        loadMonsters();
        loadTiles();
    }

    public void loadItems(){
        File itemsDirectory = new File(resourcesDirectoryPath+"/items");
        File[] itemsDirectories = itemsDirectory.listFiles();
        for(File object: itemsDirectories){
            if(object.isFile()){
                System.out.println(object.getName());
                String filename = object.getName();
                String filepath = "images/items/"+filename;
                System.out.println(filepath);
                itemImages.put(filename.substring(0,filename.length()-4), App.instance.loadImage(filepath));
            }
        }
    }

    public void loadMonsters(){
        File monstersDirectory = new File(resourcesDirectoryPath+"/monsters");
        File[] monstersDirectories = monstersDirectory.listFiles();
        for(File object: monstersDirectories){
            if(object.isFile()){
                System.out.println(object.getName());
                String filename = object.getName();
                String filepath = "images/monsters/"+filename;
                System.out.println(filepath);
                itemImages.put(filename.substring(0,filename.length()-4), App.instance.loadImage(filepath));
            }
        }
    }

    public void loadTiles(){
        File tilesDirectory = new File(resourcesDirectoryPath+"/tiles");
        File[] tilesDirectories = tilesDirectory.listFiles();
        tileImages = new Image[tilesDirectories.length];
        int i=0;
        for(File object: tilesDirectories){
            if(object.isFile()){
                String filename = object.getName();
                String filepath = "images/tiles/"+filename;
                tileImages[i] = App.instance.loadImage(filepath);
                
            }
        }
    }
}
