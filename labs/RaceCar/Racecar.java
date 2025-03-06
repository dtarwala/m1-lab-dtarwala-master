/*THIS IS THE BEST GAME YOU HAVE EVERY PLAYED. You play as a sloth that has to avoid elephants on the road. The road also moves making this even harder. 
 * You can win this game if you survive long enough! If you collide with the wall or an elephant you die and the game ends! 
 */

package RaceCar;

import java.util.Scanner;

import bridges.base.NamedColor;
import bridges.base.NamedSymbol;
import bridges.games.NonBlockingGame;
import credentials.User;

class Racecar extends NonBlockingGame {

	static int gridCol = 30;
	static int gridRow = 30; 
	char[][] grid = new char [gridRow][gridCol]; 
	int carRow;
	int carCol; 
	int leftWall;
	int rightWall; 
	int wins; 
	int frameLimiter=1; 


	public Racecar(int assid, String login, String apiKey, int c, int r) {
		super(assid, login, apiKey, c, r);
		setTitle("Race Car");
		setDescription("");
		
		
		// start running the game
		start();
		
		
		
	}

	public void placeCar(int row, int col) {
		drawSymbol(row,col,NamedSymbol.sloth,NamedColor.beige); 
	}

	public void initialize() {
		// Initialize the game state
		
		
		wins = 0; 
		carRow = gridRow - 2; 
		carCol = (int) gridCol / 2; 
		leftWall = 8; 
		rightWall = gridCol - 9; 

		// Fill grid
		for (int i = 0; i < gridRow; i++) {
			for (int j = 0; j < gridCol; j++) {
				setBGColor(i,j,NamedColor.black);
			}
		} 


		// Place the road walls
		if (leftWall >= 0 && rightWall < gridCol) {
			   buildWall(leftWall, rightWall);
		}

		// Place the car
		placeCar(carRow,carCol); 


		paintScreen(); 
	}
	
	public void setBG() { //set background to black
		for (int i = 0; i < gridRow; i++) {
			for (int j = 0; j < gridCol; j++) {
				setBGColor(i,j,NamedColor.black);
			}
		} 
	}
	
	public void buildWall(int leftW,int rightW) {
		leftW = Math.max(2, leftW);
		rightW = Math.min(gridCol - 2, rightW);
		
		for (int i = 0; i < gridRow; i++) {
			setBGColor(i,leftW,NamedColor.white); 
			setBGColor(i,rightW,NamedColor.white); 
		}
		for (int i=0; i<gridRow; i++) {
			for (int j=0; j<gridCol; j++) {
				if ((j<leftW)||(j>rightW)) {
					setBGColor(i,j,NamedColor.darkseagreen); 
				}
			}
		}
	}

	public void handleInput() {
		// Handle user input (move the car left and right)
		if ((keyA() || keyLeft()) && carCol > 0) {
			drawSymbol(carRow,carCol,NamedSymbol.none,NamedColor.black); 
			carCol--;
			wins+=1;
			placeCar(carRow,carCol); 
		} else if ((keyD() || keyRight()) && carCol < gridCol - 1) {
			drawSymbol(carRow,carCol,NamedSymbol.none,NamedColor.black);
			carCol++;
			wins+=1; 
			placeCar(carRow,carCol); 
		} else if (keyQ()) { // Quit the game
			System.out.println("Exiting the game...");
			die(); 
		}
	}

	public void die() { //called when the game ends and displays the end screen
		paintScreen();
		setBG(); 
		clearSymbols(); 
		System.out.println("Game Over!!");
		drawSymbol(0,1,NamedSymbol.l,NamedColor.white); 
		drawSymbol(0,2,NamedSymbol.o,NamedColor.white); 
		drawSymbol(0,3,NamedSymbol.s,NamedColor.white); 
		drawSymbol(0,4,NamedSymbol.e,NamedColor.white); 
		quit();
	}

	public void obstacle() {
		// Create obstacles and update their position
		
		int ran = -1; 
		if (Math.random() < 0.25) {
			ran = (int) (gridCol * Math.random()); 
			if (ran <= leftWall) {
				ran = leftWall + 1; 
			} else if (ran >= rightWall) {
				ran = rightWall - 1; 
			}
		}
		if ((ran != -1)&&(ran<gridCol)) {
			drawSymbol(0,ran,NamedSymbol.elephant,NamedColor.white); 
		}

		// Update old obstacles
		for (int i = gridRow-1; i >= 0; i--) {
			for (int j = 0; j < gridCol; j++) {
				if (getSymbol(i,j)==NamedSymbol.elephant) { 
					if (i+1<gridRow) {
						int col = j; 
						if (col<=leftWall) {
							col = leftWall+1; 
						}
						if (col>=rightWall) {
							col = rightWall-1; 
						}
						drawSymbol(i+1,col,NamedSymbol.elephant,NamedColor.white); 
					}

					// Clear the old position
					drawSymbol(i,j,NamedSymbol.none,NamedColor.black); 

				}
			}
		} 
	}

	public void checkCollision() {
		// If the car collides with either wall or obstacle, end the game
		if (carCol >= 0 && carCol < gridCol) {
			   if (getBGColor(carRow, carCol) == NamedColor.white || getSymbol(carRow, carCol) == NamedSymbol.elephant) {
			      die(); 
			   }
		}
	}

	public void paintScreen() {
		// Draw the car, move the road and draw obstacles, and build wall
		setBG(); 
		moveRoad(); 
		placeCar(carRow,carCol); 
		obstacle();
		buildWall(leftWall,rightWall);

	}
	
	public void clearSymbols() {
		for (int i=0; i<gridRow;i++) {
			for (int j=0; j<gridCol;j++) {
				drawSymbol(i,j,NamedSymbol.none,NamedColor.black); 
			}
		}
	}

	public void win(int wins) {
		// Check if the player has won 
		if (wins >= 50) {
			paintScreen(); 
			setBG();
			clearSymbols(); 
			drawSymbol(0,1,NamedSymbol.w,NamedColor.white); 
			drawSymbol(0,2,NamedSymbol.i,NamedColor.white); 
			drawSymbol(0,3,NamedSymbol.n,NamedColor.white); 
			System.out.println("You win!");
			int timer = 0; 
			if (timer<10) {
				timer +=1; 
			}
			else {
				System.exit(0); 
			}
			
		}
	}

	private void moveRoad() {
		// Move the road left and right so that the player has to move the car to stay on the road
		// Random move left, right, or stay in place
		int direction = 0;
		double genRan = Math.random(); 
		if (genRan<0.5) {
			direction = -1; 
			if (genRan<0.25) {
				direction = 1; 
			}
		}
		
		int newLeftWall = leftWall; 
		int newRightWall = rightWall; 
		if ((Math.max(leftWall + direction, 0)==0)){
			direction = 0;
		}
		if ((Math.min(rightWall + direction, gridCol - 1))==gridCol-1) {
			direction = 0;
		}
		
		newLeftWall = leftWall + direction;
		newRightWall = rightWall + direction;
		
		leftWall = newLeftWall;
		rightWall = newRightWall;
		
	}

	public void gameLoop() {
		// Main game loop
		
		if (frameLimiter%3==0) {
			handleInput();  // Check for player input
			paintScreen();    //paints screen
			checkCollision(); // Check if the car collided with walls or obstacles
			win(wins);        // Check if the player won
			System.out.println("leftWall: " + leftWall + " | rightWall: " + rightWall);
		}
		frameLimiter+=1;
		

	}

	public static void main(String[] args) throws Exception {
		Racecar game = new Racecar(21, User.USERNAME, User.APIKEY, gridCol, gridRow);
		game.initialize();
		

		// Start the main game loop
		game.gameLoop(); // Call this once for the game loop
	}
}