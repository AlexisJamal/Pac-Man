package model;

import java.util.ArrayList;
import java.util.Random;

public class Grid {
	
	private char[][] grid;
	private int size;
	private ArrayList<Entity> list;
	private Dir nextDir;
	private boolean bonus;
	private boolean resetBonus;
	private int countCoin;
	
	private final char[][] map = {
			(" ################### ").toCharArray(),
			(" #oooooooo#oooooooo# ").toCharArray(),
			(" #O##o###o#o###o##O# ").toCharArray(),
			(" #ooooooooooooooooo# ").toCharArray(),
			(" #o##o#o#####o#o##o# ").toCharArray(),
			(" #oooo#ooo#ooo#oooo# ").toCharArray(),
			(" ####o### # ###o#### ").toCharArray(),
			("    #o#       #o#    ").toCharArray(),
			("#####o# ##-## #o#####").toCharArray(),
			("     o  #   #  o     ").toCharArray(),
			("#####o# ##### #o#####").toCharArray(),
			("    #o#       #o#    ").toCharArray(),
			(" ####o# ##### #o#### ").toCharArray(),
			(" #oooooooo#oooooooo# ").toCharArray(),
			(" #o##o###o#o###o##o# ").toCharArray(),
			(" #Oo#ooooo ooooo#oO# ").toCharArray(),
			(" ##o#o#o#####o#o#o## ").toCharArray(),
			(" #oooo#ooo#ooo#oooo# ").toCharArray(),
			(" #o######o#o######o# ").toCharArray(),
			(" #ooooooooooooooooo# ").toCharArray(),
			(" ################### ").toCharArray()
	};
	
	public Grid(int size) {
		this.size = size;
		grid = new char[size][size];
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				grid[j][i] = map[i][j];
			}
		}
		
		list = new ArrayList<Entity>();
		nextDir = Dir.N;
		bonus = false;
	}
	
	public char[][] getGrid(){
		return this.grid;
	}
	
	public ArrayList<Entity> getList() {
		return list;
	}
	
	public int getSize(){
		return this.size;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public Dir getNextDir(){
		return nextDir;
	}
	
	public void setNextDir(Dir nd) {
		this.nextDir = nd;
	}
	
	public void addEntity(int x, int y, Type type) {
		list.add(new Entity(x, y, this, type));
	}
	
	public void setChar(int x, int y, char c) {
		grid[x][y] = c;
	}
	
	public void changeDir(int n, Dir dir) {
		list.get(n).setDir(dir);
	}
	
	public boolean getBonus(){
		return this.bonus;
	}
	
	public void setBonus(boolean b) {
		this.bonus = b;
	}
	
	public boolean getResetBonus(){
		return this.resetBonus;
	}
	
	public void setResetBonus(boolean b) {
		this.resetBonus = b;
	}
	
	public int getCountCoin(){
		return this.countCoin;
	}
	
	public void goRight(int n) {
		Entity e = list.get(n);
		int x = e.getX();
		int y = e.getY();
		if(n == 0) {
			if(grid[x][y] == 'O') {
				bonus = true;
				resetBonus = true;
			}
			if(grid[x][y] == 'o') {
				countCoin++;
			}
			grid[x][y] = ' ';
		}
		if(x == 20) {
			x = 0;
			e.setX(0);
		}
		if(n != 0 || !e.movement(nextDir)) {
			e.movement(Dir.E);
		}
		else {
			e.setDir(nextDir);
		}
		if(n > 0 && x < 19 && x > 1) {
			randGhostMove(x+1,y,n);
		}
	}
	
	public void goLeft(int n) {
		Entity e = list.get(n);
		int x = e.getX();
		int y = e.getY();
		if(n == 0) {
			if(grid[x][y] == 'O') {
				bonus = true;
				resetBonus = true;
			}
			if(grid[x][y] == 'o') {
				countCoin++;
			}
			grid[x][y] = ' ';
		}
		if(x == 0) {
			x = 20;
			e.setX(20);
		}
		if(n != 0 || !e.movement(nextDir)) {
			e.movement(Dir.W);
		}
		else {
			e.setDir(nextDir);
		}
		if(n > 0 && x < 19 && x > 1) {
			randGhostMove(x-1,y,n);
		}
	}
	
	public void goUp(int n) {
		Entity e = list.get(n);
		int x = e.getX();
		int y = e.getY();
		if(n == 0) {
			if(grid[x][y] == 'O') {
				bonus = true;
				resetBonus = true;
			}
			if(grid[x][y] == 'o') {
				countCoin++;
			}
			grid[x][y] = ' ';
		}
		if(n != 0 || !e.movement(nextDir)) {
			e.movement(Dir.N);
		}
		else {
			e.setDir(nextDir);
		}
		if(n > 0 && x < 19 && x > 1) {
			randGhostMove(x,y-1,n);
		}
	}
	
	public void goDown(int n) {
		Entity e = list.get(n);
		int x = e.getX();
		int y = e.getY();
		if(n == 0) {
			if(grid[x][y] == 'O') {
				bonus = true;
				resetBonus = true;
			}
			if(grid[x][y] == 'o') {
				countCoin++;
			}
			grid[x][y] = ' ';
		}
		if(n != 0 || !e.movement(nextDir)) {
			e.movement(Dir.S);
		}
		else {
			e.setDir(nextDir);
		}
		if(n > 0 && x < 19 && x > 1) {
			randGhostMove(x,y+1,n);
		}
	}
	
	public void randGhostMove(int x, int y, int n) {
		boolean r = false;
		boolean l = false;
		boolean t = false;
		boolean b = false;
		int count = 0;
		
		Entity e = list.get(n);
		
		if(grid[x-1][y] != '#' && grid[x-1][y] != '-') {
			l = true;
			count++;
		}
		if(grid[x+1][y] != '#' && grid[x+1][y] != '-') {
			r = true;
			count++;
		}
		if(grid[x][y-1] != '#' && grid[x][y-1] != '-') {
			t = true;
			count++;
		}
		if(grid[x][y+1] != '#' && grid[x][y+1] != '-') {
			b = true;
			count++;
		}
		
		if(count >= 2) {
			ArrayList<Dir> arr = new ArrayList<Dir>();
			if(r) arr.add(Dir.E);
			if(l) arr.add(Dir.W);
			if(t) arr.add(Dir.N);
			if(b) arr.add(Dir.S);
			switch(e.getDir()) {
			case E:
				arr.remove(Dir.W);
				break;
			case N:
				arr.remove(Dir.S);
				break;
			case S:
				arr.remove(Dir.N);
				break;
			case W:
				arr.remove(Dir.E);
				break;
			default:
				break;
			
			}
			
			Random rand = new Random();
			int ra = rand.nextInt(arr.size());
			e.setDir(arr.get(ra));
		}
	}
	
	public void freeGhost(int n) {
		list.get(n).setY(list.get(n).getY() - 2);
		list.get(n).setX(10);
		Random rand = new Random();
		int r = rand.nextInt(2);
		if(r == 0) {
			list.get(n).setDir(Dir.E);
		}
		else {
			list.get(n).setDir(Dir.W);
		}
	}
	
	public void lockGhost(int n) {
		list.get(n).setY(9);
		list.get(n).setX(10);
	}
	
	public void disp() {
		String tmp = "";
		for (int i=0; i<size; i++) {
			tmp = "";
			for (int j=0; j<size; j++) {
				tmp += grid[i][j];
			}
			System.out.println(tmp);
		}
	}
	
	public int checkCollision() {
		int res = 0;
		boolean quit = false;
		for(int i = 1; i < 5 && !quit; i++) {
			if(list.get(0).getX() == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
				res = i;
				quit = true;
			}
			switch(list.get(0).getDir()) {
			case E:
				if((list.get(0).getX() + 1) == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case N:
				if(list.get(0).getX() == list.get(i).getX() && (list.get(0).getY() - 1) == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case S:
				if(list.get(0).getX() == list.get(i).getX() && (list.get(0).getY() + 1) == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case W:
				if((list.get(0).getX() - 1) == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			default:
				break;
			
			}
			switch(nextDir) {
			case E:
				if((list.get(0).getX() + 1) == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case N:
				if(list.get(0).getX() == list.get(i).getX() && (list.get(0).getY() - 1) == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case S:
				if(list.get(0).getX() == list.get(i).getX() && (list.get(0).getY() + 1) == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			case W:
				if((list.get(0).getX() - 1) == list.get(i).getX() && list.get(0).getY() == list.get(i).getY()) {
					res = i;
					quit = true;
				}
				break;
			default:
				break;
			
			}
		}
		return res;
	}

}
