package vc;

import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Dir;
import model.Game;
import model.State;
import model.Entity;

public class ViewController extends Application implements Observer{

	private Game game;

	private Image imgFloor;
	private Image imgDoor;
	private Image imgWall;
	private Image imgSmall;
	private Image imgBig;
	private Image imgPacR;
	private Image imgPacL;
	private Image imgPacU;
	private Image imgPacB;
	private Image imgG1F;
	private Image imgG1B;
	private Image imgG2F;
	private Image imgG2B;
	private Image imgG3F;
	private Image imgG3B;
	private Image imgG4F;
	private Image imgG4B;
	private Image imgAG;
	private Image imgWin;
	private Image imgLoose;
	
	private ImageView[][] ivs;

	private int sceneSize;
	private int spriteSize;
	private int size;

	public ViewController() {
		imgFloor = new Image("res/PacManSprite/Floor.png");
		imgDoor = new Image("res/PacManSprite/Door.png");
		imgWall = new Image("res/PacManSprite/Wall.png");
		imgSmall = new Image("res/PacManSprite/Small.jpg");
		imgBig = new Image("res/PacManSprite/Big.jpg");
		imgPacR = new Image("res/PacManSprite/PacMan1.png");
		imgPacL = new Image("res/PacManSprite/PacMan1Back.png");
		imgPacU = new Image("res/PacManSprite/PacMan1Up.png");
		imgPacB = new Image("res/PacManSprite/PacMan1Bottom.png");
		imgG1F = new Image("res/PacManSprite/RedGhostF.png");
		imgG2F = new Image("res/PacManSprite/PinkGhostF.png");
		imgG3F = new Image("res/PacManSprite/YellowGhostF.png");
		imgG4F = new Image("res/PacManSprite/BlueGhostF.png");
		imgG1B = new Image("res/PacManSprite/RedGhostB.png");
		imgG2B = new Image("res/PacManSprite/PinkGhostB.png");
		imgG3B = new Image("res/PacManSprite/YellowGhostB.png");
		imgG4B = new Image("res/PacManSprite/BlueGhostB.png");
		imgAG = new Image("res/PacManSprite/AfraidGhost.png");
		imgWin = new Image("res/PacManSprite/winScreen.jpg");
		imgLoose = new Image("res/PacManSprite/looseScreen.jpg");

		size = 21;
		sceneSize = 672;
		spriteSize = 32;
		ivs = new ImageView[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				ivs[i][j] = new ImageView(imgFloor);
			}
		}
	}
	
	

	@Override
	public void start(Stage primaryStage) {
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-image: url(\"res/PacManSprite/menu.jpg\");-fx-background-size: 672, 672;");
		
		Scene scene = new Scene(bp, sceneSize, sceneSize);
		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
			public void handle(javafx.scene.input.KeyEvent event) {
				startGame(primaryStage);
		    }    
	    });
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent t) {
		        System.exit(0);
		    }
		});
	}
	
	public void endMenu(boolean isWin) {
		ImageView iv = ivs[0][0];
		iv.setFitHeight(672);
		iv.setFitWidth(672);
		if(isWin) {
			iv.setImage(imgWin);
		}
		else {
			iv.setImage(imgLoose);
		}
	}

	

	@Override
	public void update(Observable o, Object arg) {
		if(game.getState() == State.LOOSE) {
			endMenu(false);
		}
		else if(game.getState() == State.WIN) {
			endMenu(true);
		}
		else {
			try {
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						ImageView iv = ivs[i][j];
						
						switch (game.getGrid().getGrid()[i][j]) {
						case ' ':
							iv.setImage(imgFloor);
							break;
						case 'o':
							iv.setImage(imgSmall);
							break;
						case 'O':
							iv.setImage(imgBig);
							break;
						default:
							break;
						}
					}
				}
				
				Entity pacman = game.getGrid().getList().get(0);
				Entity g1 = game.getGrid().getList().get(1);
				Entity g2 = game.getGrid().getList().get(2);
				Entity g3 = game.getGrid().getList().get(3);
				Entity g4 = game.getGrid().getList().get(4);
				
				switch(pacman.getDir()) {
				case E:
					ivs[pacman.getX()][pacman.getY()].setImage(imgPacR);
					break;
				case N:
					ivs[pacman.getX()][pacman.getY()].setImage(imgPacU);
					break;
				case S:
					ivs[pacman.getX()][pacman.getY()].setImage(imgPacB);
					break;
				case W:
					ivs[pacman.getX()][pacman.getY()].setImage(imgPacL);
					break;
				default:
					break;
				
				}
				if(game.getGrid().getBonus()) {
					ivs[g1.getX()][g1.getY()].setImage(imgAG);
					ivs[g2.getX()][g2.getY()].setImage(imgAG);
					ivs[g3.getX()][g3.getY()].setImage(imgAG);
					ivs[g4.getX()][g4.getY()].setImage(imgAG);
				}
				else {
					if(g1.getIsLookingForward()) {
						ivs[g1.getX()][g1.getY()].setImage(imgG1F);
					}
					else ivs[g1.getX()][g1.getY()].setImage(imgG1B);
					if(g2.getIsLookingForward()) {
						ivs[g2.getX()][g2.getY()].setImage(imgG2F);
					}
					else ivs[g2.getX()][g2.getY()].setImage(imgG2B);
					if(g3.getIsLookingForward()) {
						ivs[g3.getX()][g3.getY()].setImage(imgG3F);
					}
					else ivs[g3.getX()][g3.getY()].setImage(imgG3B);
					if(g4.getIsLookingForward()) {
						ivs[g4.getX()][g4.getY()].setImage(imgG4F);
					}
					else ivs[g4.getX()][g4.getY()].setImage(imgG4B);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startGame(Stage primaryStage) {
		game = new Game(size);
		game.addObserver(this);
		try {
			GridPane gPane = new GridPane();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					ImageView iv = ivs[i][j];
					
					switch (game.getGrid().getGrid()[i][j]) {
					case ' ':
						iv.setImage(imgFloor);
						break;
					case '#':
						iv.setImage(imgWall);
						break;
					case '-':
						iv.setImage(imgDoor);
						break;
					case 'o':
						iv.setImage(imgSmall);
						break;
					case 'O':
						iv.setImage(imgBig);
						break;
					default:
						break;
					}
					
					iv.setFitHeight(spriteSize);
					iv.setFitWidth(spriteSize);
					gPane.add(iv, i, j);
				}
			}
			
			
			Scene scene = new Scene(gPane, sceneSize, sceneSize);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
			scene.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() {
				
				
				public void handle(javafx.scene.input.KeyEvent event) {
			        if (event.getCode() == KeyCode.RIGHT) {
			        	game.getGrid().setNextDir(Dir.E);
			        }else if (event.getCode() == KeyCode.LEFT) {
			        	game.getGrid().setNextDir(Dir.W);
			        }else if (event.getCode() == KeyCode.UP) {
			        	game.getGrid().setNextDir(Dir.N);
			        }else if (event.getCode() == KeyCode.DOWN) {
			        	game.getGrid().setNextDir(Dir.S);
			        }
			        if(game.getState() != State.PLAYING) {
			        	startGame(primaryStage);
			        }
			    }    
		    });
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(game).start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
