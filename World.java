import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class World extends Application{
	
	private static MapFactory mapFact;
	private static Scene currentScene;
	private static Map currentMap;
	private static Player player; // will either make or load one in the bgeining 
	private static int sceneSizeX = 510;
	private static int sceneSizeY= 510;
	
	private static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		World.primaryStage = primaryStage;
		mapFact = new MapFactory(); // when out, it causes error;
		
		player = MapFactory.getAPlayer();
		
		setOverworldMap();
		//setDungeonMap();
	}


	private static void setFocus( Map map ) {
		map.focus();
	}
	
	public static void setOverworldMap() { // ask if map is overworld, if, so make overworld, if not, make it normal
		System.out.println("setOverworldMap has been called");
		eraseCurrentMap(); // clear some memory?
		
		currentMap = MapFactory.createOverworld();
		currentMap.addCharacter(player);
		
		setScene();
	}
	public static void setDungeonMap() { // ask if map is overworld, if, so make overworld, if not, make it normal
		System.out.println("setDungeonMap has been called");
		eraseCurrentMap(); // clear some memory?
		currentMap = MapFactory.createMap(.1, 5); // have map change destination thing create this
		//currentMap = MapFactory.createMap(.1, 1); // testing monster
		currentMap.addCharacter(player);
		
		setScene();
	}
	private static void setScene() {
		currentScene = new Scene( currentMap.getMapGUI(), sceneSizeX, sceneSizeY );
		primaryStage.setScene( currentScene ); 
		primaryStage.show();
		
		System.out.println(currentMap.getMapGUI().getChildren().size());
		currentMap.addControls(player);
		player.setFocusTraversable(true);
		currentMap.start();
	}
	
	private static void eraseCurrentMap() {
		if ( currentMap!=null) {
			currentMap.removeAllChildren();
			currentMap = null;
		}
		// do other memory cleaning stuff?
	}
	
	public static void main(String[] args) {
		launch(	args);
	}

}
