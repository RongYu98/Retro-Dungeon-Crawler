
public class MetaPortal extends Portal{
	
	public static final int START = 1;
	public static final int LOAD = 2;
	public static final int HELP = 3;
	public static final int QUIT = 4;
	private int type;
	public MetaPortal(String name, int type) {
		super(name);
		this.type = type;
		super.setNoLevelName();
	}
	public Player loadCharacter() {
		return CharacterFactory.createPlayer("MC");
	}
	
	public void quit() {
		System.exit(0);
	}

	private void effects() {
		if ( this.type == LOAD ) {
			World.load();
		} else if ( this.type == HELP ) {
			World.setTutorial();
		} else if ( this.type == QUIT ) {
			this.quit();
		} else if ( this.type == START ) {
			this.setDestination( MapFactory.createOverworld());
			super.entered();
		}
	}
	
	@Override()
	public void entered() {
		effects();
	}
}