import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class MapGUI extends Pane{
	
	Map owner;
	public MapGUI( Map map ) {
		owner = map;
	}
	public void addCharacter( Character c ) {
		this.getChildren().add(c);
		this.getChildren().add(c.getNameText());
	}
	public void addAttack( Attack c ) {
		this.getChildren().add(c);
	}
	
	public void removeCharacter( Node n1) {
		this.getChildren().remove(n1);
	}
	public void removeAllCharacters() {
		this.getChildren().remove(0, this.getChildren().size());
	}
}
