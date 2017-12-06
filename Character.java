/**
 * This class will have all the base characteristics of all NPC and PC
 */

// import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * @author rongyu
 *  Version: 17.11.04
 */
public abstract class Character extends Circle { // DONE
	protected int attack;
	protected int atkRate = 20;
	protected int atkCooldown = 0;
	protected int boundX = World.sceneSizeX;
	protected int boundY = World.sceneSizeY;
	protected int currentHealth;
	protected int maxHealth;
	protected int moveUnit;
	protected String name;
	protected Text text;
	protected double attackRadius = 2;
	protected int power;

	private boolean noLevelName=false;
	
	public Text getNameText() {
		updateNameText();
		return text;

	}
	public Character() {
		this("Character");
	}
	public Character( String name) {
		this( name, 100, 10);
	}
	public Character( String name, int health, int attack ) {
		this.name = name;
		this.maxHealth = health;
		this.currentHealth = health;
		this.attack = attack;
		super.setRadius(8);
		setAtkRadius(2);
		this.moveUnit = 1;
		randomizeLocation();
		this.text = new Text(this.name+" Lv"+this.power);
		updatePower();
	}
	
	protected abstract void updatePowerColor();
	public void act() {
		if ( this.atkCooldown>0) {
			this.atkCooldown--;
		}
	};
	
	protected void alterAtkRate(int num) {
		if ( atkRate - num > 0) {
			atkRate -= num;
		}
	}
	public Attack attack( Character other ) {
		if ( this.atkCooldown <= 0) {
			other.decreaseHealth( this.getAttack() );
			this.atkCooldown += this.atkRate;
			return new Attack( this.getCenterX(), this.getCenterY(),
							   other.getCenterX(), other.getCenterY(),
							   Color.BLACK);
		}
		return null;
	}
	
	protected void decreaseHealth( int amount ) {
		this.currentHealth -= amount;
		if ( this.currentHealth<0) {
			this.currentHealth = 0;
		} else if ( this.currentHealth>this.maxHealth) {
			this.currentHealth = this.maxHealth;
		} 
		this.updateColor();
	}
	
	public String getName() {
		return this.name;
	}
	public int getMaxHealth() {
		return this.maxHealth;
	}
	public int getCurrentHealth() {
		return this.currentHealth;
	}
	
	/**
	 * if it is out of bounds, it moves it within bounds
	 */
	protected void checkBounds() {
		if ( this.getCenterX()-moveUnit<0) {
			this.setCenterX(2*moveUnit);
			// System.out.println("STUCK!");
		} if ( this.getCenterY()-moveUnit<0) {
			this.setCenterY(2*moveUnit);
		} if ( this.getCenterX()+moveUnit>boundX) {
			this.setCenterX(boundX-moveUnit);
		} if ( this.getCenterY()+moveUnit>boundY) {
			this.setCenterY(boundY-moveUnit);
		}
	}
	
	public void setAttack( int attack ) {
		this.attack = attack;
	}
	public void setMaxHealth( int amount ) {
		this.maxHealth = amount;
		this.currentHealth = amount;
	}
	public void setName( String name ) {
		this.name = name;
	}
	
	protected void updateColor() {
		this.setOpacity( (this.getCurrentHealth()*1.0)/this.getMaxHealth());
	}
	public boolean interactsWith( Character other ) {
		double dX = Math.pow(this.getCenterX() - other.getCenterX(), 2);
		double dY = Math.pow(this.getCenterY() - other.getCenterY(), 2);
		double dis = Math.sqrt( dX+dY );
		double radius = attackRadius*(this.getRadius()+other.getRadius());
		return (dis<radius); //&& !this.equals(other));
	}
	
	public boolean equals( Character other ) {
		if ( System.identityHashCode(this) == (System.identityHashCode(other))) {
			return true;
		}
		return false;
	}
	
	public boolean readyToAttack() {
		return atkCooldown<1;
	}
	public void setAtkRadius(double d) {
		this.attackRadius = d;
		if ( this.attackRadius < .25) {
			this.attackRadius = .25;
		} else if ( this.attackRadius > 10 ) {
			this.attackRadius = 10;
		}
	}
	public double getAtkRadius() {
		return this.attackRadius;
	}
	public void updatePower() {
		this.power = (int) (this.getMaxHealth()/100+this.attack/10+this.getRadius()-2);
		this.updateNameText();
	}
	
	private void updateNameText() {
		this.text.setText(name+" Lv"+power);
		if ( this.noLevelName ) {
			this.text.setText(name);
		}
		double offSetX = text.layoutBoundsProperty().getValue().getMaxX()-text.layoutBoundsProperty().getValue().getMinX();
		double offSetY = text.layoutBoundsProperty().getValue().getMaxY()-text.layoutBoundsProperty().getValue().getMinY();
		this.text.layoutXProperty().bind(this.centerXProperty().subtract(offSetX/2));
		this.text.layoutYProperty().bind(this.centerYProperty().add(offSetY+10));
	}
	public int getAttack() {
		return attack;
	}
	public void setNoLevelName() {
		this.noLevelName = true;
	}
	
	protected void randomizeLocation() {
		super.setCenterX(Math.random()*this.boundX*.9+this.boundX*.05);
		super.setCenterY(Math.random()*this.boundY*.70+this.boundY*.05);
	}
}
