
public class CharacterFactory { // Done
	

	public static String vowels = "AEIOU";
	public static String consonants = "BCDFGHJKLMNPQRSTVWXYZ";
	
	/**
	 * method returns a monster with numerical attributes proportional to difficulty
	 * @param difficulty  double from 0 to 1.0.
	 * @return mon  Monster subclass of Character
	 */
	public Monster createMonster( double difficulty ) {
		String name = this.makeName();
		// Monster mon = new Monster(name, (int)(1000*difficulty), (int)(100*difficulty));
		Monster mon = new Monster(name, difficulty);
		return mon;
	}
	/**
	 * creates a super boss
	 */
	public Monster createBoss( double diff ) {
		diff+=1;
		// SuperBoss mon = new SuperBoss("SUPER BOSS", (int)(100*diff), (int)(10*diff));
		SuperBoss mon = new SuperBoss("SUPER BOSS", diff);
		return mon;
		
	}
	/**
	 * method returns a player-subclass of character, with
	 * the specified name.
	 * @param name  the desired name of the player
	 * @return player  Player subclass of Character
	 */
	public static Player createPlayer( String name ) {
		Player player = new Player( name );
		return player;
	}
	/**
	 * method returns a player according to the specifications of the parameters
	 * @param name  String name of player
	 * @param health  numerical attribute desired of player
	 * @param atk  numerical attribute desired of player
	 * @return player  Player with desired attributes
	 */
	public static Player createPlayer( String name, int health, int atk ) {
		return CharacterFactory.createPlayer( name, health, atk, 3);
	}
	public static Player createPlayer( String name, int health, int atk, double atkRadius ) {
		Player player = new Player( name, health, atk, atkRadius );
		return player;
	}
	
	/**
	 * creates a generic portal
	 * @return portal
	 */
	public Portal createPortal() {
		Portal portal = new Portal();
		return portal;
	}
	public Portal createPortal( double x, double y, double radius, String name ) {
		Portal portal = new Portal(name);
		portal.setCenterX(x);
		portal.setCenterY(y);
		portal.setRadius(radius);
		return portal;
	}
	private MetaPortal createMetaPortal( double x, double y, double radius, String name, int type ) {
		MetaPortal portal = new MetaPortal(name, type);
		portal.setCenterX(x);
		portal.setCenterY(y);
		portal.setRadius(radius);
		return portal;
	}
	public Portal createStartPortal( double x, double y, double radius ) {
		Portal portal = this.createPortal(x, y, radius, "START");
		portal.setNoLevelName();
		portal.setDestination( MapFactory.createOverworld());
		return portal;
	}
	public Portal createLoadPortal( double x, double y, double radius ) {
		Portal portal = createMetaPortal( x,y,radius, "LOAD", MetaPortal.LOAD); 
		return portal;
	}
	public Portal createQuitPortal( double x, double y, double radius ) {
		Portal portal = createMetaPortal( x,y,radius, "QUIT", MetaPortal.QUIT); 
		return portal;
	}
	public Portal createHelpPortal( double x, double y, double radius ) {
		Portal portal = createMetaPortal( x,y,radius, "HELP", MetaPortal.HELP);
		portal.setOnMouseClicked( e->{
			portal.entered();
		});
		return portal;
	}
	
	private String makeName() {
		String name = "";
		int length = (int) (Math.random()*5+3);
		for ( int i=0; i<length; i++) {
			if ( Math.random()<.7) {
				name+=consonants.charAt( (int) (Math.random()*21) );
			} else {
				name+=vowels.charAt( (int) (Math.random()*5) );
			}
		}
		return name;
	}
}
