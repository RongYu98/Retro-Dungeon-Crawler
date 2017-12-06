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
	
	private int expCounter = 0;
	
	
	public Player( String name ) {
		super( name );
		super.setFill(Color.BLUE);
		super.moveUnit = super.moveUnit * 2;
		super.alterAtkRate(2);
		super.setAtkRadius(3);
	}
	public Player( String name, int health, int atk, double atkRadius ) {
		super( name, health, atk );
		this.setAtkRadius(atkRadius);
		super.setFill(Color.BLUE);
		super.moveUnit = super.moveUnit * 2;
		super.alterAtkRate(2);
		System.out.println("Power: "+super.power);
		super.getNameText();
		System.out.println("Power: "+power);
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
		} else if ( e.getCode().equals( KeyCode.F ) ||
					e.getCode().equals( KeyCode.K )) {
			World.save();
		} else if ( e.getCode().equals( KeyCode.P )) {
			World.pause();
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
	
	public void reborn() {
		this.setMaxHealth( this.getMaxHealth()-this.getMaxHealth()/10 );
		this.setAttack(this.attack - this.attack/10);
		this.setRadius(this.getRadius() - this.getRadius()/10);
		this.updatePower();
		World.setDeathScene();
	}
	@Override
	public Attack attack( Character other ) {
		Attack atk = super.attack(other);
		if ( other.getCurrentHealth() < 1) {
			this.incExpCount( other.power );
		}
		if ( atk == null ) {
			return atk;
		}
		atk.setStroke(Color.BLUE);
		return atk;
	}
	
	private void incExpCount(int power) {
		this.expCounter += power;
		if ( this.expCounter>10 ) {
			this.expCounter-=10;
			MapFactory.createPowerup().attack(this);
		}
	}

	@Override
	protected void updatePowerColor() {
		this.power = (int) (this.getMaxHealth()/10+this.attack+super.getRadius());
		if ( this.power > 255 ) {
			this.power = 255;
		}
		super.setFill( Color.rgb(power, power, power) );
	}

	public void setLocation(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
		checkBounds();
	}
}
