import javafx.scene.paint.Color;

public class Monster extends Character{ // DONE
	
	int destinationX;
	int destinationY;
	private double sqrt2 = 0.707;
	
	public Monster() {
		this("Bob",.5);
	}
	public Monster( String name, double difficulty) {
		this(name, (int)(1000*difficulty), (int)(100*difficulty));
		// super.setAtkRadius(super.getAtkRadius()+difficulty/5);
	}
	public Monster( String name, int health, int attack ) {
		super( name, health, attack );
		setNewDestinations();
		updatePowerColor();
	}

	@Override
	public void act() {  // refine movement later
		super.act();
		double x = super.getCenterX();
		double y = super.getCenterY();
		int diffX = (int) (destinationX-x);
		int diffY = (int) (destinationY-y);
		
		
		if ( Math.abs(diffX) < super.moveUnit/2 ) {
			diffX = 1;
		}
		if ( Math.abs(diffY) < super.moveUnit/2) {
			diffY = 1;
		}
		
		if ( Math.abs(diffX) < super.moveUnit*2 &&
			 Math.abs(diffY) < super.moveUnit*2 ) {
			setNewDestinations(); // move onto next target
		}
		if ( Math.abs(diffX) >= super.moveUnit && Math.abs(diffY) >= super.moveUnit ) {
			this.setCenterX( this.getCenterX()+(diffX/Math.abs(diffX) *moveUnit*sqrt2)); // divide by 0 exception
			this.setCenterY( this.getCenterY()+(diffY/Math.abs(diffY) *moveUnit*sqrt2)); 
		} else if ( Math.abs(diffX) >= super.moveUnit ){
			this.setCenterX( this.getCenterX()+(diffX/Math.abs(diffX)*moveUnit));
		} else{
			this.setCenterY( this.getCenterY()+(diffY/Math.abs(diffY)*moveUnit)); 
		}
		checkBounds();
		
	}
	
	@Override
	public Attack attack( Character other ) {
		Attack atk = super.attack(other);
		if ( atk == null ) {
			return atk;
		}
		atk.setStroke(super.getFill());
		return atk;
		
	}
	
	protected void updatePowerColor() {
		super.updatePower();
		int powerColor = super.power;
		if ( powerColor > 255 );
			powerColor = 255;
		super.setFill( Color.rgb(powerColor, 0, 0) );
	}
	
	private void setNewDestinations() {
		destinationX = (int) (Math.random()*(boundX-40)+20);
		destinationY = (int) (Math.random()*(boundY-40)+20);
	}
}
