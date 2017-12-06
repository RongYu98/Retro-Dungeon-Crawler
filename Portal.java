import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Portal extends Character { // needs world to do rest

	Map destination;
	
	double destDifficulty;
	
	public Portal( double destinationDifficulty) {
		super( "Dungeon");
		this.destDifficulty = destinationDifficulty;
		super.setFill(Color.YELLOW);
		super.setAtkRadius(.9);
		this.updatePower();
	}
	public Portal( String name ) {
		super(name);
		super.setFill(Color.YELLOW);
		super.setAtkRadius(.9);
		this.updatePower();
	}

	public Portal() {
		this( .5);
	}
	
	public void setDestDif(double dif) {
		this.destDifficulty = dif;
		updatePower();
	}
	
	public void setDestination(Map map) {
		this.destination = map;
		this.updatePower();
	}
	
	private void changeMap() {
		if ( destination == null ) {
			destination = MapFactory.createMap(destDifficulty+.01, 10);
		}
		if ( destination!=null) {
			World.setDungeonMap( destination ); // pass over descriptions in future
		}
	}

	@Override 
	public void updatePower() {
		super.power = (int) (destDifficulty*10);		
		// System.out.println(destDifficulty);
	}
	@Override
	public void act() {
		super.decreaseHealth(super.getMaxHealth()*-1);
	}
	
	public void entered() {
		changeMap();
	}
	@Override
	protected void updatePowerColor() {
		super.setFill( Color.rgb((int)(255*destDifficulty), (int)(255*destDifficulty), (int)(255*destDifficulty)));
	}
}
