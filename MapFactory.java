
public class MapFactory {
	private static CharacterFactory charFact;
	
	public MapFactory() {
		charFact = new CharacterFactory();
	}
	
	public static Map createMap( double difficulty, int numOfMonsters ) {
		Map map = new Map();
		for (int i=0;i<numOfMonsters;i++) {
			addMonsterTo( map, difficulty);
		}
		addPortalTo(map);
		// addDungeonsTo(map, difficulty, numOfMonsters);
		return map;
	}
	
	public static Map createOverworld() {
		Map overworld = new Map();
		// addPortalTo( overworld );
		for (int i=0;i<5;i++) {
			addMonsterTo( overworld, (double)(i/10)+.1 );
			addDungeonsTo(overworld, i/10, 10);
		}
		return overworld;
	}
	
	public static Player getAPlayer() {
		return charFact.createPlayer("MAIN CHARACTER");
	}
	
	private static void addMonsterTo( Map map, double difficulty ) {
		map.addCharacter( charFact.createMonster((difficulty)));
	}
	private static void addPortalTo( Map map ) {
		map.addCharacter( charFact.createPortal()); //  doesn't attach stuff
	}
	private static void addDungeonsTo( Map map, double difficulty, int numOfMonsters ) {
		Portal portal = charFact.createPortal();
		portal.setDestination( MapFactory.createMap(difficulty, numOfMonsters));
		map.addCharacter( portal );
	}
}
