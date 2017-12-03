import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


public class Player extends Character{
	
	private boolean[] toMove = new boolean[4]; // use this to record what is pressed, what to move
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	
	private int regenCounter = 0;
	private int regenDivider = 10;
	
	
	public Player( String name ) {
		super( name );
		super.setFill(Color.BLUE);
		super.moveUnit = super.moveUnit * 3;
		super.alterAtkRate(2);
		super.setAtkRadius(3);
	}
	
	/**
	 * Method moves according to the compiled requests sent to it.
	 */
	public void act() {
		super.act();
		if ( this.toMove[LEFT] ) {
			this.setCenterX( this.getCenterX()-moveUnit);
		}
		if ( this.toMove[RIGHT] ) {
			this.setCenterX( this.getCenterX()+moveUnit);
		}
		if ( this.toMove[UP] ) {
			this.setCenterY( this.getCenterY()-moveUnit);
		}
		if ( this.toMove[DOWN] ) {
			this.setCenterY( this.getCenterY()+moveUnit);
		}
		regenCounter++;
		if ( regenCounter>regenDivider ) {
			super.decreaseHealth(this.getMaxHealth()/-100);
			regenCounter=0;
		}
		super.checkBounds();
	}
	public void addKeyPressed(KeyEvent e) {
		if ( e.getCode().equals( KeyCode.UP ) || 
			 e.getCode().equals( KeyCode.W )) {
			this.toMove[Player.UP] = true;
		} else if ( e.getCode().equals( KeyCode.DOWN ) ||
					e.getCode().equals( KeyCode.S )) {
			this.toMove[Player.DOWN] = true;
		} else if ( e.getCode().equals( KeyCode.LEFT) ||
					e.getCode().equals( KeyCode.A )) {
			this.toMove[Player.LEFT] = true;
		} else if ( e.getCode().equals( KeyCode.RIGHT) ||
					e.getCode().equals( KeyCode.D )) {
			this.toMove[Player.RIGHT] = true;
		}
	}
	public void removeKeyPressed(KeyEvent e) {
		if ( e.getCode().equals( KeyCode.UP ) || 
				e.getCode().equals( KeyCode.W )) {
			this.toMove[Player.UP] = false;
		} else if ( e.getCode().equals( KeyCode.DOWN ) ||
				e.getCode().equals( KeyCode.S )) {
			this.toMove[Player.DOWN] = false;
		} else if ( e.getCode().equals( KeyCode.LEFT) ||
				e.getCode().equals( KeyCode.A )) {
			this.toMove[Player.LEFT] = false;
		} else if ( e.getCode().equals( KeyCode.RIGHT) ||
				e.getCode().equals( KeyCode.D )) {
			this.toMove[Player.RIGHT] = false;
		}

	}
	@Override
	public Attack attack( Character other ) {
		Attack atk = super.attack(other);
		if ( atk == null ) {
			return atk;
		}
		atk.setStroke(Color.BLUE);
		return atk;
		
	}
	
	@Override
	protected void updatePowerColor() {
		this.power = this.getMaxHealth()/10+this.attack;
		if ( this.power > 255 ) {
			this.power = 255;
		}
		super.setFill( Color.rgb(power, power, power) );
	}
}
