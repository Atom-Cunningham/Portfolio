package Main;
import javafx.animation.PathTransition;
import javafx.scene.shape.Path;

import java.util.Random;

import Enemies.*;

public class Round {
	
	private PathTransition pathTransition;
	private Path path;
	private TowerDefController controller;
	private Random rand = new Random();
	
	public Round(PathTransition pathTransition, Path path, TowerDefController controller) {
		super();
		this.pathTransition = pathTransition;
		this.path = path;
		this.controller = controller;
	}
	
	public void createEnemies() {
		GenericBaddie baddie = new EasyBaddie(0, 437);
//		path.getElements().add(baddie);
	}

}
