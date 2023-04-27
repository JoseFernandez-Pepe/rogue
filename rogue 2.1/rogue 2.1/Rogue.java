import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Rogue extends JPanel implements ActionListener{

	static int[][] maze;

	static final int SCREEN_WIDTH = 1300;
	static final int SCREEN_HEIGHT = 750;
	static final int UNIT_SIZE = 25;

	static final int NUM_POINTS = 30;
	static final int NUM_ROOMS = 3;
	static final int NUM_BRANCHES = 4;

	static final int MAX_ROOM_WIDTH = 8;
	static final int MAX_ROOM_HEIGHT = 8;

	final int[] playerPosition = {650, 375};
	final int[][] point = new int[NUM_POINTS][2];

	boolean running = false;
	Random random;


	Rogue(){
		maze = RandomArrayGenerator.generateRandomArray(SCREEN_WIDTH / UNIT_SIZE, SCREEN_HEIGHT / UNIT_SIZE);
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();

	}

	public void startGame() {


		JLabel titleLabel = new JLabel("           Rogue");
		titleLabel.setAlignmentX(JLabel.CENTER);
		titleLabel.setFont(new Font("Serif", Font.BOLD, 150));
		JButton startButton = new JButton("Start");
		startButton.setFont(new Font("SansSerif", Font.ITALIC, 100));
		startButton.setForeground(Color.RED);
		startButton.setBackground(Color.black);


		this.setLayout(new GridLayout(2, 1, 10, 10));
		this.add(titleLabel);
		this.add(startButton);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Code to start game goes here
				titleLabel.setVisible(false);
				startButton.setVisible(false);
				startButton.setEnabled(false);
				createPoints();
				connectPoints();
				running = true;
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		repaint();
	}

	public void draw(Graphics g) {

		if(running) {
			//draws the lines for context


			//fillPoints(g);

			fillPoints(g);

            /*
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, UNIT_SIZE, UNIT_SIZE);
			*/
			//draws player

			g.setColor(Color.darkGray);
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			for(int i=0;i<SCREEN_WIDTH/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);

			}


			g.setColor(Color.green);
			g.drawOval(playerPosition[0], playerPosition[1], UNIT_SIZE, UNIT_SIZE);
		}
	}


	public void createPoints(){

		point[0][0] = SCREEN_WIDTH / UNIT_SIZE / 2;
		point[0][1] = SCREEN_HEIGHT / UNIT_SIZE / 2;
		maze[point[0][0]][point[0][1]] = 1;

		for(int i = 1; i < NUM_POINTS; i++){
			point[i][0] = random.nextInt(SCREEN_WIDTH / UNIT_SIZE - 2) + 1;
			point[i][1] = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE - 2) + 1;

			maze[point[i][0]][point[i][1]] = 1;

		}

	}

	public void connectPoints(){
		int randP;
		for(int i = 0; i < NUM_POINTS; i++){
			for(int j = 0; j < NUM_BRANCHES; j++){
				randP = random.nextInt(NUM_POINTS);
				while(randP == i){
					randP = random.nextInt(NUM_POINTS);
				}
				fillPaths(point[i], point[randP], random.nextInt(2) == 1);
			}
		}
	}

	public void fillPoints(Graphics g){
		g.setColor(Color.gray);
		for(int i = 0; i < maze.length; i++){
			for(int j = 0; j < maze[0].length;j++){
				if(maze[i][j] == 1){
					g.fillRect(i * UNIT_SIZE, j * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
	}

	public void fillPaths(int[] a, int[] b, boolean down){
		if(down){
			for(int i = 0; i < b[1] - a[1]; i++){
				maze[a[0]][a[1] + i] = 1;
			}
			for(int i = 0; i < b[0] - a[0]; i++){
				maze[a[0] + i][a[1] + b[1] - a[1]] = 1;
			}
		} else{
			for(int i = 0; i < b[0] - a[0]; i++){
				maze[a[0] + i][a[1]] = 1;
			}
			for(int i = 0; i < b[1] - a[1]; i++){
				maze[a[0] + b[0] - a[0]][a[1] + i] = 1;
			}
		}
	}


	public boolean checkCollisions(int moveX, int moveY) {
		return maze[(playerPosition[0] / UNIT_SIZE) + moveX][(playerPosition[1] / UNIT_SIZE) + moveY] == 1;
	}

	public void gameOver(Graphics g) {

		//Game Over text
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(checkCollisions(-1, 0)){
						playerPosition[0] -= UNIT_SIZE;
					}
					break;
				case KeyEvent.VK_RIGHT:
					if(checkCollisions(1, 0)){
						playerPosition[0] += UNIT_SIZE;
					}
					break;
				case KeyEvent.VK_UP:
					if(checkCollisions(0, -1)){
						playerPosition[1] -= UNIT_SIZE;
					}
					break;
				case KeyEvent.VK_DOWN:
					if(checkCollisions(0, 1)){
						playerPosition[1] += UNIT_SIZE;
					}
					break;
			}
		}
	}

	public static void main(String[] args) {
	}
}