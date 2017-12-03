import javafx.scene.paint.Color;

public class Portal extends Character { // needs world to do rest

	Map destination;
	
	double destDifficulty;
	
	public Portal( double destinationDifficulty) {
		this.destDifficulty = destinationDifficulty;
		super.setAtkRadius(.5);
	}
	public Portal() {
		super.setFill(Color.YELLOW);
		super.setAtkRadius(.5);
	}
	
	public void setDestination(Map map) {
		this.destination = map;
	}
	
	private void changeMap() {
		if ( destination == null ) {
			destination = MapFactory.createMap(destDifficulty, 10);
		}
		if ( destination!=null) {
			World.setDungeonMap(); // pass over descriptions in future
		}
	}

	@Override
	public void act() {
		super.decreaseHealth(super.getMaxHealth()*-1);
	}
	@Override
	public Attack attack( Character other ) {
		if ( other instanceof Player) {
			changeMap();
		}
		return null;
	}
	public void entered() {
		changeMap();
	}
	@Override
	protected void updatePowerColor() {
		super.setFill( Color.rgb((int)(255*destDifficulty), (int)(255*destDifficulty), (int)(255*destDifficulty)));
	}
	
}
