import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Attack extends Line{
	public Attack( double d, double e, double f, double g, Color c) {
		super(d, e, f, g);
		super.setFill(Color.BLUE);
	}
	public void fade() {
		if ( super.getOpacity()>=0.005) {
			super.setOpacity( this.getOpacity()-.005);
		} 
	}
	public boolean faded() {
		return super.getOpacity()<0.005;
	}
}
