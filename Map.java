import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;

public class Map {

	private MapGUI dungeon;
	
	private Circle central;
	private Timeline animation;
	
	public Map() {
		central = new Circle(); 
		central.setFill(Color.WHITE);
		
		dungeon = new MapGUI( this );
		
		
		animation = new Timeline(new KeyFrame(Duration.millis(10), e-> {
			charactersMove();
			checkInteractions();
		})); ///  pause when leave, or just delete to make new map?
		animation.setCycleCount(Timeline.INDEFINITE);
		
		
	}
	
	public MapGUI getMapGUI() {
		return this.dungeon;
	}

	/**
	 * Adds a character to the list of characters this class holds.
	 * @param c  the character to be added
	 */
	public void addCharacter(Character c) {
		dungeon.addCharacter(c);
	}
	
	
	public void charactersMove() {
		ObservableList<Node> chars = dungeon.getChildren();
		for ( Node c: chars ) {
			if ( c instanceof Character ) {
				((Character)c).act();
			}
		}
	}
	
	private void checkInteractions() {
		// loops twice, so c1 attacks c2, and c2 attacks c1! genius!!!
		Portal portalToEnter = null;
		ObservableList<Node> chars = dungeon.getChildren();
		ArrayList<Node> attacks = new ArrayList<Node>();
		for ( Node c1: chars ) {
			// System.out.println( chars.size());
			//System.out.println( c1.getClass());
			for ( Node c2: chars ) {
				if ( c1 instanceof Attack) {
					((Attack) c1).fade();
					continue;
				} else if ( c2 instanceof Attack ) {
					continue;
				}
				if ( c1==null || c2 ==null || !(c1 instanceof Character) || !(c2 instanceof Character) || 
					((Character) c1).getCurrentHealth()<1 || ((Character) c2).getCurrentHealth()<1) {
					continue;
				}
				if ( c1.getClass()!=c2.getClass() && ((Character) c1).interactsWith((Character) c2)) { 
					if ( !(c1 instanceof Portal) && !(c2 instanceof Portal) && ((Character) c1).readyToAttack() ) {  // not mon and mon,  and not portal
						Attack line = ((Character) c1).attack( (Character) c2 );
						System.out.println(c1.getClass());
						System.out.println(((Character) c1).getCurrentHealth());
						if ( line!=null) {
							attacks.add(line);
						}
						// continue;
					} else if ( c1 instanceof Portal && c2 instanceof Player) {
						portalToEnter = (Portal) c1;
						break;
					}
				}
			}
		}
		if ( portalToEnter!=null) {
			portalToEnter.entered();
		}
		removeDeadStuff(chars);
		addAttack( attacks );
	}
	
	private void removeDeadStuff(ObservableList<Node> chars) {
		for ( int i=0; i<chars.size(); i++){
			Node n1 = chars.get(i);
			if ( n1 instanceof Character) {
				if ( ((Character) n1).getCurrentHealth() < 1) {
					this.removeCharacter((Character) n1); 
					this.removeCharacter(((Character) n1).getNameText());
					i--;
				}
				// continue;
			} else if ( n1 instanceof Attack) {
				if ( ((Attack) n1).faded()) {
					this.removeCharacter(n1);
					i--;
				}
			}
		}
		
	}

	private void addAttack(ArrayList<Node> attacks) {
		for ( Node a: attacks) {
			if ( a != null ) {
				dungeon.addAttack((Attack) a);
				System.out.println(((Shape) a).getFill());
			}
		}
		
	}

	private void removeCharacter(Node n1) {
		dungeon.removeCharacter(n1);
	}
	public void removeAllChildren() {
		// this.getChildren().remove(0, this.getChildren().size());
		dungeon.removeAllCharacters();
		animation.stop();
	}
	
	public void start() {
		animation.play();
	}
	
	/**
	 * Adds WASD movement via EventListener
	 * @param c  The character to which to add the eventlistener
	 */
	public void addControls( Player c ) {
		c.setOnKeyPressed( e-> {
			c.addKeyPressed( e );
		});
		c.setOnKeyReleased(e->{
			c.removeKeyPressed(e);
		});
	}
	
	/**
	 * Forcefully sets the focus onto this pane's circle
	 */
	public void focus() {
		central.setFocusTraversable(true);
	}
	public void unFocus() {
		central.setFocusTraversable(false);
	}
	
	public String toString() {
		return String.valueOf(System.identityHashCode(this));
	}
}
