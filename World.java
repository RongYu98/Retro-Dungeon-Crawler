import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class World extends Application{
	
	private static MapFactory mapFact;
	private static Scene currentScene;
	private static Map currentMap;
	private static Player player; // will either make or load one in the bgeining 
	public static int sceneSizeX = 710;
	public static int sceneSizeY= 510;
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		World.primaryStage = primaryStage;
		mapFact = new MapFactory(); // when out, it causes error;
		player = MapFactory.getAPlayer();
		setMenuMap();
	}


	private static void setFocus( Map map ) {
		map.focus();
	}
	
	public static void setMenuMap() { // ask if map is overworld, if, so make overworld, if not, make it normal
		System.out.println("setMenuMap has been called");
		eraseCurrentMap(); // clear some memory?
		currentMap = MapFactory.createMenuScreen();
		setScene();
	}
	public static void setDungeonMap() { 
		System.out.println("setDungeonMap without stuff has been called");
		Map map = MapFactory.createMap(.1, 5);
		setDungeonMap(map);
	}
	public static void setDungeonMap( Map map ) {
		System.out.println("setDungeonMap with param whas been called");
		eraseCurrentMap(); // clear some memory?
		currentMap = map;
		setScene();
	}
	
	private static void setScene() {
		currentScene = new Scene( currentMap.getMapGUI(), sceneSizeX, sceneSizeY );
		primaryStage.setScene( currentScene ); 
		primaryStage.show();
		currentMap.addCharacter(player);
		currentMap.addControls(player);
		player.setFocusTraversable(true);
		currentMap.start();
	}
	
	private static void eraseCurrentMap() {
		if ( currentMap!=null) {
			currentMap.removeAllChildren();
			currentMap.unFocus();
			currentMap = null;
		}
		// do other memory cleaning stuff?
	}
	
	public static void setDeathScene() {
		eraseCurrentMap();
		currentMap = MapFactory.makeDeathMap();
		setScene();
	}
	public static void setVictoryScene() {
		eraseCurrentMap();
		currentMap = MapFactory.makeVictoryMap();
		setScene();
	}
	
	public static void setPlayer( Player player ) {
		World.player = player;
	}
	
	public static void main(String[] args) {
		launch(	args);
	}

	public static void save() {
		try { 
			FileWriter fw = new FileWriter("save.txt");
			BufferedWriter writer = new BufferedWriter( fw );
			writer.write(player.getName());
			writer.newLine();
			writer.write(String.valueOf(player.getMaxHealth()));
			writer.newLine();
			writer.write(String.valueOf(player.getAttack()));
			writer.newLine();
			writer.write(String.valueOf(player.getAtkRadius()));
			writer.newLine();
			writer.write(String.valueOf(MapFactory.getDifficulty()));
			writer.close();
			addText("SAVED");
		} catch (Exception e) {
			addText("NOT SAVED");
		}
	}
	public static void load() {
		boolean loaded = false;
		try {
			FileReader fr = new FileReader("save.txt");
			BufferedReader reader = new BufferedReader( fr );
			String name = reader.readLine();
			int health = Integer.valueOf(reader.readLine());
			int atk = Integer.valueOf(reader.readLine());
			double atkRadius = Double.valueOf(reader.readLine());
			player = CharacterFactory.createPlayer(name, health, atk, atkRadius);
			
			double diff = Double.valueOf(reader.readLine());
			MapFactory.increaseDifficulty(diff);
			loaded = true;
			reader.close();
		} catch (Exception e) {
			addText("NOT LOADED");
		}
		setDungeonMap(MapFactory.createOverworld());
		if ( loaded ) {
			addText("LOADED");
		} else {
			addText("NOT LOADED");
		}
	}

	public static void pause() {
		if ( currentMap.stopped() ) {
			currentMap.start();
		} else {
			currentMap.pause();
		}
	}
	public static void addText(String stuff) {
		Text txt = new Text(stuff);
		txt.setFill(Color.MEDIUMVIOLETRED);
		txt.setLayoutX(World.sceneSizeX*.01);
		txt.setLayoutY(World.sceneSizeY*.03);
		currentMap.addText( txt );
	}
	public static void setTutorial() {
		eraseCurrentMap();
		currentMap = MapFactory.makeTutorial();
		System.out.println("SET TUTORIAL");
		setScene();
	}
}
