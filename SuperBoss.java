
public class SuperBoss extends Monster{

	public SuperBoss(String name, double diff) {
		super(name, (int)(10000*diff), (int)(1000*diff));
		super.setRadius(super.getRadius()*2);
	}

	public void reborn() {
		World.setVictoryScene(); // change to victory
		// get stronger, make everything else strong
		// set a multiplier
	}
	
}
