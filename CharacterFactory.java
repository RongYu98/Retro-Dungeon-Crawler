
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
		Monster mon = new Monster(name, (int)(1000*difficulty), (int)(100*difficulty));
		return mon;
	}
	/**
	 * method returns a player-subclass of character, with
	 * the specified name.
	 * @param name  the desired name of the player
	 * @return player  Player subclass of Character
	 */
	public Player createPlayer( String name ) {
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
	public Player createPlayer( String name, int health, int atk ) {
		Player player = new Player( name );
		player.setMaxHealth(health);
		player.setAttack(atk);
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
