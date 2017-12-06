import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MapFactory {
	private static CharacterFactory charFact;
	private static double diff = 0;
	
	public MapFactory() {
		charFact = new CharacterFactory();
	}
	
	public static Map createMap( double difficulty, int numOfMonsters ) {
		Map map = new Map();
		for (int i=0;i<numOfMonsters;i++) {
			addMonsterTo( map, difficulty+MapFactory.diff);
		}
		chanceAddPowerupTo(map);
		addDungeonPortalTo(map, difficulty);
		if ( Math.random()>.75) {
			MapFactory.addPortalToOverworld(map);
		}
		return map;
	}
	
	private static void chanceAddPowerupTo(Map map) {
		if ( Math.random() > .5 ) {
			addPowerupTo(map);
		}
	}
	public static void addPowerupTo(Map map) {
		Powerup pu = createPowerup();
		map.addCharacter(pu);
	}
	public static Powerup createPowerup() {
		return(new Powerup(Powerup.getRandomType(), 1));
	}

	public static Map createOverworld() {
		Map overworld = new Map();
		for (int i=0;i<10;i++) {
			addDungeonPortalTo(overworld, i/10.+diff);
		}
		addSuperBoss( overworld );
		return overworld;
	}
	
	private static void addSuperBoss(Map overworld) {
		overworld.addCharacter(charFact.createBoss(diff));
	}

	public static Player getAPlayer() {
		return CharacterFactory.createPlayer("MC");
	}
	
	private static void addMonsterTo( Map map, double difficulty ) {
		map.addCharacter( charFact.createMonster((difficulty)));
	}
	private static void addDungeonPortalTo( Map map, double difficulty) {
		Portal portal = charFact.createPortal();
		portal.setDestDif(difficulty);
		map.addCharacter( portal );
	}
	
	public static Map makeDeathMap() {
		Map map = new Map();
		Text text = new Text("You Died!");
		text.setFont(new Font(150));
		text.setFill(Color.RED);
		text.setLayoutX((World.sceneSizeX/2-
				(text.layoutBoundsProperty().getValue().getMaxX()-
				 text.layoutBoundsProperty().getValue().getMinX())/2));
		text.setLayoutY((World.sceneSizeY/2));
		map.addText(text);
		addPortalToOverworld( map );
		MapFactory.decreaseDifficulty();
		return map;
	}
	public static Map makeVictoryMap() {
		Map map = new Map();
		Text text = new Text("You Won!");
		text.setFont(new Font(150));
		Text t2 = new Text("But there is still more...");
		text.setFill(Color.BLUE);
		t2.setFill(Color.BLUE);
		text.setLayoutX((World.sceneSizeX/2-
				(text.layoutBoundsProperty().getValue().getMaxX()-
				 text.layoutBoundsProperty().getValue().getMinX())/2));
		t2.setLayoutX((World.sceneSizeX/2-
				(t2.layoutBoundsProperty().getValue().getMaxX()-
				 t2.layoutBoundsProperty().getValue().getMinX())/2));
		text.setLayoutY((World.sceneSizeY/2));
		t2.setLayoutY((World.sceneSizeY/3*2));
		map.addText(text);
		map.addText(t2);
		MapFactory.increaseDifficulty();
		addPortalToOverworld( map );
		return map;
	}

	public static void increaseDifficulty() {
		MapFactory.increaseDifficulty(1.0);
	}
	public static void decreaseDifficulty() {
		MapFactory.diff  -= 1;
		if ( MapFactory.diff < 1) {
			MapFactory.diff = 1;
		}
	}
	public static void increaseDifficulty(double diff2) {
		MapFactory.diff  += diff2;
	}

	private static void addPortalToOverworld(Map map) {
		Portal portal = charFact.createPortal();
		portal.setName("OVERWORLD");
		portal.setNoLevelName();
		portal.setDestination(MapFactory.createOverworld());
		map.addCharacter(portal);
	}
	
	private static void addPortalToMenuScreen(Map map) {
		Portal portal = charFact.createPortal();
		portal.setName("MENU BAR");
		portal.setNoLevelName();
		portal.setDestination(MapFactory.createMenuScreen());	
		portal.setCenterX(World.sceneSizeX*.5);
		portal.setCenterY(World.sceneSizeY*.3);
		map.addCharacter(portal);
	}
	
	public static double getDifficulty() {
		return MapFactory.diff;
	}
	
	
	public static Map createMenuScreen() {
		Map map = new Map();
		Portal p1 = charFact.createStartPortal(210,130,10);
		Portal p2 = charFact.createLoadPortal(310,130,10);
		Portal p3 = charFact.createQuitPortal(390,130,10);
		Portal p4 = charFact.createHelpPortal(490,130,10);
		p1.setNoLevelName();
		p2.setNoLevelName();
		p3.setNoLevelName();
		p4.setNoLevelName();
		map.addCharacter(p1);
		map.addCharacter(p2);
		map.addCharacter(p3);
		map.addCharacter(p4);
		return map;
	}
	
	public static Map makeTutorial() {
		Map m1 = new Map();
		Map m2 = new Map(); 
		Map m23 = new Map();
		Map m3 = new Map();
		Map m4 = new Map();
		
		Text t1, t2, t3, t4;
		Portal p1, p2, p3;
	
		t1 = new Text("Move with WASD to the Yellow Portal");
		t1.setFont(new Font(35));
		t1.setFill(Color.BLUEVIOLET);
		t1.setLayoutX(World.sceneSizeX*.03);
		t1.setLayoutY(World.sceneSizeY*.1);
		m1.addText(t1);
		p1 = charFact.createPortal();
		p1.setNoLevelName();
		p1.setDestination(m2);
		p1.setCenterX(World.sceneSizeX*.5);
		p1.setCenterY(World.sceneSizeY*.5);
		m1.addCharacter(p1);
		
		t2 = new Text("You automatically attack enemies by moving close enough to them"
				+ "\nThey have monstrous names because they're monsters");
		t2.setFont(new Font(25));
		t2.setFill(Color.BLUEVIOLET);
		t2.setLayoutX(World.sceneSizeX*.02);
		t2.setLayoutY(World.sceneSizeY*.07);
		Monster mon1 = charFact.createMonster(0.1);
		mon1.moveUnit = 1;
		mon1.setCenterX(World.sceneSizeX*.5);
		mon1.setCenterY(World.sceneSizeY*.5);
		m2.addText(t2);
		m2.addCharacter(mon1);
		Monster mon = charFact.createMonster(0.0);
		mon.moveUnit = 0;
		p2 = charFact.createPortal();
		p2.setCenterX(World.sceneSizeX*.5);
		p2.setCenterY(World.sceneSizeY*.25);
		p2.setNoLevelName();
		p2.setDestination(m23);
		m2.addCharacter(p2);
		
		Text t5 = new Text("You can save by pressing F");
		t5.setFont(new Font(30));
		t5.setFill(Color.BLUEVIOLET);
		t5.setLayoutX(World.sceneSizeX*.02);
		t5.setLayoutY(World.sceneSizeY*.09);
		Portal p5 = charFact.createPortal();
		p5.setCenterX(World.sceneSizeX*.5);
		p5.setCenterY(World.sceneSizeY*.25);
		p5.setNoLevelName();
		p5.setDestination(m3);
		m23.addCharacter(p5);
		m23.addText(t5);
		
		t3 = new Text("You power up by eating veggies and powerups\n Or by beating monsters");
		t3.setFont(new Font(25));
		t3.setFill(Color.BLUEVIOLET);
		t3.setLayoutX(World.sceneSizeX*.02);
		t3.setLayoutY(World.sceneSizeY*.07);
		m3.addText(t3);
		Powerup pu = MapFactory.createPowerup();
		pu.setCenterX(World.sceneSizeX*.5);
		pu.setCenterY(World.sceneSizeY*.65);
		m3.addCharacter(pu);
		p3 = charFact.createPortal();
		p3.setCenterX(World.sceneSizeX*.5);
		p3.setCenterY(World.sceneSizeY*.25);
		p3.setNoLevelName();
		p3.setDestination(m4);
		m3.addCharacter(p3);
		
		
		t4 = new Text("You win by beating the SUPER BOSS");
		t4.setFont(new Font(38));
		t4.setFill(Color.BLUEVIOLET);
		t4.setLayoutX(World.sceneSizeX*.02);
		t4.setLayoutY(World.sceneSizeY*.09);
		m4.addText(t4);
		Monster boss = charFact.createMonster(0.1);
		boss.setCenterX(World.sceneSizeX*.5);
		boss.setCenterY(World.sceneSizeY*.5);
		boss.setName("SUPER BOSS");
		boss.moveUnit = 0;
		m4.addCharacter(boss);
		addPortalToMenuScreen(m4);
		
		return m2;
	}
}
