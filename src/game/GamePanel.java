package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Iterator;
import java.util.Random;


import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int BodyParts = 6;
	int applesEaten;
	int AppleX;
	int AppleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	public GamePanel() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics G) {
		super.paintComponent(G);
		draw(G);
	}
	public void draw(Graphics G) {
		
		if(running) {
		for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
			G.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
			G.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH,i*UNIT_SIZE);
		}
		
		G.setColor(Color.red);
		G.fillOval(AppleX, AppleY, UNIT_SIZE, UNIT_SIZE);
		
		for(int i = 0; i< BodyParts; i++ ) {
			if(i == 0) {
				G.setColor(Color.green);
				G.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
			}
			
			else {
				G.setColor(new Color(45,180,0));
				G.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}	
		G.setColor(Color.red);
		G.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics Metrics = getFontMetrics(G.getFont());
		G.drawString("Score: "+ applesEaten, (SCREEN_WIDTH- Metrics.stringWidth("Score: "+ applesEaten))/2, G.getFont().getSize());
		
		}
		else {
			gameOver(G);
		}
	}
	
	public void newApple() {
		AppleX = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		AppleY = random.nextInt((SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() {
		for(int i = BodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i -1];
		}
		
		switch(direction) {
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			
		}
		
	}
	public void checkApple() {
		if((x[0] == AppleX)&&(y[0] == AppleY)) {
			BodyParts++;
			applesEaten++;
			newApple();
		}
	}
	public void checkColisions() {
		// cehca se a cabeça bateu no corpo
				for(int i = BodyParts; i > 0; i--) {
					if((x[0] == x[i])&&(y[0] == y[i])) {
						running = false;
					}
				}
				
				//verifica se bateu na borda esquerda 
				if(x[0] < 0) {
					running = false;
				}
				
				//verifica se bateu na direita
				if(x[0] > SCREEN_WIDTH) {
					running = false;
				}
				
				//verifica se bateu no topo
				if(y[0] < 0) {
					running = false;
				}
				
				//verifica se bateu no topo
				if(y[0] > SCREEN_HEIGHT) {
					running = false;
				}
				
				if(!running) {
					timer.stop();
				}
					
				
				
	}
	
	public void gameOver(Graphics G) {
		//GAME OVER SCORE
		G.setColor(Color.red);
		G.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics Metrics1 = getFontMetrics(G.getFont());
		G.drawString("Score: "+ applesEaten, (SCREEN_WIDTH- Metrics1.stringWidth("Score: "+ applesEaten))/2, G.getFont().getSize());
		
		//GAME OVER TEXT
		G.setColor(Color.red);
		G.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics Metrics2 = getFontMetrics(G.getFont());
		G.drawString("GAME-OVER", (SCREEN_WIDTH- Metrics2.stringWidth("GAME-OVER"))/2, SCREEN_HEIGHT/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkColisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			
			}
		}
	}
}

