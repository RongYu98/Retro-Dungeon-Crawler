import javafx.scene.paint.Color;

public class Monster extends Character{ // DONE
	
	int destinationX;
	int destinationY;
	private double sqrt2 = 0.707;
	
	public Monster() {
		super();
		setNewDestinations();
		super.setFill(Color.RED);
		// updatePowerColor();
		
	}
	public Monster( String name, int health, int attack ) {
		super( name, health, attack );
		setNewDestinations();
		// super.setFill(Color.RED);
		// updatePowerColor();
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
		atk.setStroke(Color.RED);
		return atk;
		
	}
	
	protected void updatePowerColor() {
		super.power = this.getMaxHealth()/10+this.attack;
		if ( super.power > 255 );
			super.power = 255;
		super.setFill( Color.rgb(power, 0, power/10) );
	}
	
	private void setNewDestinations() {
		destinationX = (int) (Math.random()*(boundX-40)+20);
		destinationY = (int) (Math.random()*(boundY-40)+20);
	}
}
