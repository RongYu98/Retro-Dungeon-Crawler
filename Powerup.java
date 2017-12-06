import javafx.scene.paint.Color;


public class Powerup extends Character{

	public static final int HEALTH = 1;
	public static final int ATTACK = 2;
	public static final int RANGE = 3;
	private int type;
	private int level = 1;
	
	public Powerup(int type, int level) {
		super();
		if ( type == HEALTH ) {
			this.setName("Health Powerup");
		} else if ( type == ATTACK ) {
			this.setName("Attack Powerup");
		} else {
			this.setName("Range Powerup");
		}
		this.type = type;
		this.level = level;
		this.updatePower();
	}
	
	public void act() {
		// super.decreaseHealth(super.getMaxHealth()*-1);
	}
	@Override
	public Attack attack( Character other ) {
		if ( this.type==HEALTH) {
			other.setMaxHealth(other.getMaxHealth()+50*level);
		} else if ( this.type==ATTACK) {
			other.setAttack(other.getAttack()+5*level);
		} else if ( this.type==RANGE) {
			other.setAtkRadius(other.getRadius()+atkCooldown+.5*level);
			// other.setRadius(other.getRadius()+.5*level);
		}
		other.updatePower();
		this.decreaseHealth(this.getMaxHealth());
		return null;
	}
	@Override
	public void updatePower() {
		super.power = this.level;
	}
	@Override
	protected void updatePowerColor() {
		super.setFill(Color.BLUEVIOLET);
	}
	
	public static int getRandomType() {
		return (int)(Math.random()*3+1);
	}

}
