import processing.core.PApplet;

public class Sketch extends PApplet {
	
  // snow flakes
	float[] snowX;
	float[] snowY;
	float[] snowSpeed;
	boolean[] ballHideStatus;
	int snowFlakes = 10;
	float fltSpeed = 1;

  // player (the ball)
	float playerX, playerY; 
	float playerSize = 20;
	int playerLives = 3;

  // movement and gameover
	boolean moveLeft, moveRight, moveUp, moveDown; 
	boolean gameOver = false; 

	public void settings() {
		size(400, 400);
	}

	public void setup() {
		snowX = new float[snowFlakes];
		snowY = new float[snowFlakes];
		snowSpeed = new float[snowFlakes];
		ballHideStatus = new boolean[snowFlakes];

		for (int i = 0; i < snowFlakes; i++) {
			snowX[i] = random(width);
			snowY[i] = random(height);
			snowSpeed[i] = random(1, 3);
			ballHideStatus[i] = false;
		}

    // adjust player size
		playerX = width / 2;
		playerY = height / 2;
	}

	public void draw() {
		if (gameOver) {
			background(255);
			return;
		}

		background(50);

		// Draw player
		fill(0, 0, 255);
		ellipse(playerX, playerY, playerSize, playerSize);

		// Draw lives
		fill(255, 0, 0);
		for (int i = 0; i < playerLives; i++) {
			rect(width - 20 * (i + 1), 20, 10, 10);
		}

		// Movement 
		if (moveLeft) {
			playerX -= 5;
		}
		if (moveRight) {
			playerX += 5;
		}
		if (moveUp) {
			playerY -= 5;
		}
		if (moveDown) {
			playerY += 5;
		}

		// Check player position using size
		if (playerX < playerSize / 2) {
			playerX = playerSize / 2;
		}
		if (playerX > width - playerSize / 2) {
			playerX = width - playerSize / 2;
		}
		if (playerY < playerSize / 2) {
			playerY = playerSize / 2;
		}
		if (playerY > height - playerSize / 2) {
			playerY = height - playerSize / 2;
		}

		// Draw snowflakes
		fill(255);
		for (int i = 0; i < snowFlakes; i++) {
			if (!ballHideStatus[i]) {
				ellipse(snowX[i], snowY[i], 20, 20); 
				snowY[i] += snowSpeed[i] * fltSpeed;

				if (snowY[i] > height) {
					snowY[i] = 0;
					snowX[i] = random(width);
					snowSpeed[i] = random(1, 3);
				}

				// Collision with player
				if (dist(playerX, playerY, snowX[i], snowY[i]) < playerSize / 2 + 10) {
					snowY[i] = 0;
					snowX[i] = random(width);
					snowSpeed[i] = random(1, 3);
					playerLives--;
				}
			}
		}

		// Check game over
		if (playerLives <= 0) {
			gameOver = true;
		}
	}

	public void keyPressed() {
		if (keyCode == UP) {
			fltSpeed = 0.5f; 
		} else if (keyCode == DOWN) {
			fltSpeed = 2; 
		} else if (key == 'a' || key == 'A') {
			moveLeft = true; 
		} else if (key == 'd' || key == 'D') {
			moveRight = true; 
		} else if (key == 'w' || key == 'W') {
			moveUp = true; 
		} else if (key == 's' || key == 'S') {
			moveDown = true; 
		}
	}

	public void keyReleased() {
		if (keyCode == UP || keyCode == DOWN) {
			fltSpeed = 1; 
		} else if (key == 'a' || key == 'A') {
			moveLeft = false; 
		} else if (key == 'd' || key == 'D') {
			moveRight = false; 
		} else if (key == 'w' || key == 'W') {
			moveUp = false; 
		} else if (key == 's' || key == 'S') {
			moveDown = false; 
		}
	}

	public void mousePressed() {
		for (int i = 0; i < snowFlakes; i++) {
			if (!ballHideStatus[i] && dist(mouseX, mouseY, snowX[i], snowY[i]) < 10) {
				ballHideStatus[i] = true;
			}
		}
	}

  public static void main(String[] args) {
		PApplet.main("Sketch");
	}
}