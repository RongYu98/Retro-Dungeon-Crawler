import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;

public class Map {

	private boolean stopped = true;

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
		checkOverLap(c);
		dungeon.addCharacter(c);
	}
	
	private void checkOverLap(Character c) {
		int times = 0;
		ObservableList<Node> chars = dungeon.getChildren();
		for ( int i=0; i< chars.size(); i++) {
			if ( times > 10 ) {
				return;
			}
			Node c1 = chars.get(i);
			if ( c1 instanceof Character && !(c1 instanceof Monster) &&
					!(c1 instanceof Player)) {
				((Character)c1).setAtkRadius( ((Character)c1).getAtkRadius()*6);
				if ( ((Character) c1).interactsWith(c) ) {
					c.randomizeLocation();
					i=0;
					times++;
				}
				((Character)c1).setAtkRadius( ((Character)c1).getAtkRadius()/6);
			}
		}
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
					if (((c1 instanceof Monster && c2 instanceof Player) || 
					     (c2 instanceof Monster && c1 instanceof Player)) &&
						 ((Character) c1).readyToAttack()) {
						Attack line = ((Character) c1).attack( (Character) c2 );
						if ( line!=null) {
							attacks.add(line);
						}
					} else if ( c1 instanceof Portal && c2 instanceof Player) {
						portalToEnter = (Portal) c1;
						break;
					} else if ( c1 instanceof Powerup && c2 instanceof Player) {
						((Character) c1).attack((Character) c2);
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
		// boolean playerRemoved = false;
		for ( int i=0; i<chars.size(); i++){
			Node n1 = chars.get(i);
			if ( n1 instanceof Character) {
				if ( ((Character) n1).getCurrentHealth() < 1) {
					if ( n1 instanceof Player ) {
						((Player) n1).reborn();
						break;
					}
					if ( n1 instanceof SuperBoss ) {
						((SuperBoss) n1).reborn();
						break;
					}
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
	public void pause() {
		animation.pause();
	}
	/**
	 * Adds WASD movement via EventListener
	 * @param c  The character to which to add the eventlistener
	 */
	public void addControls( Player c ) {
		c.setLocation( World.sceneSizeX/2, World.sceneSizeY*.9);
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
		dungeon.setFocusTraversable(true);
	}
	public void unFocus() {
		central.setFocusTraversable(false);
		dungeon.setFocusTraversable(false);
	}
	
	public String toString() {
		return String.valueOf(System.identityHashCode(this));
	}
	public void addText( Text text ) {
		dungeon.addExtras(text);
	}

	public boolean stopped() {
		return this.stopped;
	}
}
