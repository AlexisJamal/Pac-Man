package model;

public class Entity{
	
	private int x,y;
	private Grid grid;
	private Dir direction;
	private Type type;
	private boolean isLookingForward;
	

	
	public Entity(int x, int y, Grid grid, Type type) {
		this.x = x;
		this.y = y;
		this.grid = grid;
		this.direction = Dir.N;
		this.type = type;
		this.isLookingForward = true;
	}
	
	public boolean movement(Dir DIR) {
		boolean res = false;
		int destX = x;
		int destY = y;
		
		switch(DIR) {
		case N:
			destY--;
			break;
		case S:
			destY++;
			break;
		case E:
			destX++;
			isLookingForward = true;
			break;
		case W:
			destX--;
			isLookingForward = false;
			break;
		}
		if(grid.getGrid()[destX][destY] != '#' && grid.getGrid()[destX][destY] != '-') {
			x = destX;
			y = destY;
			res = true;
		}
		return res;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int X) {
		x = X;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int Y) {
		y = Y;
	}
	
	public void setDir(Dir dir) {
		direction = dir;
	}
	
	public Dir getDir() {
		return direction;
	}
	
	public boolean getIsLookingForward() {
		return isLookingForward;
	}
}