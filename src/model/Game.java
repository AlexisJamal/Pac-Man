package model;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable implements Runnable{
	
	private Grid grid;
	private State state;
	private ArrayList<Integer> lockedGhost;
	
	public Game(int size) {
		grid = new Grid(size);
		grid.addEntity(10, 15, Type.PacMan);
		grid.addEntity(10, 7, Type.Ghost);
		grid.addEntity(9, 9, Type.Ghost);
		grid.addEntity(10, 9, Type.Ghost);
		grid.addEntity(11, 9, Type.Ghost);
		lockedGhost = new ArrayList<Integer>();
		state = State.PLAYING;
	}

	@Override
	public void run() {
		int count = 0;
		int countBonus = 0;
		int countReleaseGhost = 0;
		int checkCollision = 0;
		boolean quit = false;
		while(!quit) {
			count++;
			checkCollision = grid.checkCollision();
			if(count == 50) {
				grid.freeGhost(2);
			}
			else if(count == 100) {
				grid.freeGhost(3);
			}
			else if(count == 150) {
				grid.freeGhost(4);
			}
			if(grid.getResetBonus()) {
				countBonus = 0;
				grid.setResetBonus(false);
			}
			if(countBonus == 100) {
				countBonus = 0;
				grid.setBonus(false);
				countReleaseGhost = 1;
			}
			if(countReleaseGhost > 0) {
				countReleaseGhost++;
			}
			if(countReleaseGhost == 20) {
				for(int i = 0; i < lockedGhost.size(); i++) {
					grid.freeGhost(lockedGhost.get(i));
				}
				lockedGhost.clear();
			}
			if(grid.getBonus()) {
				countBonus++;
				if(checkCollision != 0) {
					grid.lockGhost(checkCollision);
					lockedGhost.add(checkCollision);
				}
				if(count % 2 == 0) {
					switch(grid.getList().get(0).getDir()) {
					case E:
						grid.goRight(0);
						break;
					case N:
						grid.goUp(0);
						break;
					case S:
						grid.goDown(0);
						break;
					case W:
						grid.goLeft(0);
						break;
					default:
						break;
					}
				}
			}
			else {
				if(checkCollision != 0) {
					state = State.LOOSE;
					quit = true;
				}
				if(count % 3 == 0) {
					switch(grid.getList().get(0).getDir()) {
					case E:
						grid.goRight(0);
						break;
					case N:
						grid.goUp(0);
						break;
					case S:
						grid.goDown(0);
						break;
					case W:
						grid.goLeft(0);
						break;
					default:
						break;
					}
				}
			}
			if(count % 3 == 0) {
				for (int i = 1; i < grid.getList().size(); i++) {
					switch(grid.getList().get(i).getDir()) {
					case E:
						grid.goRight(i);
						break;
					case N:
						grid.goUp(i);
						break;
					case S:
						grid.goDown(i);
						break;
					case W:
						grid.goLeft(i);
						break;
					default:
						break;
					}
				}
			}
			if(grid.getCountCoin() == 146) {
				state = State.WIN;
				quit = true;
			}
			notifyObservers();
			setChanged();
			try {
				Thread.sleep(50);
			}catch (InterruptedException e) {
				
			}
		}
	}
	
	public Grid getGrid() {
		return this.grid;
	}
	
	public State getState() {
		return this.state;
	}

}
