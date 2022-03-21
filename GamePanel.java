import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;
import java.awt.font.*;

public class GamePanel extends JPanel implements ActionListener
{
	//declare constants
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25; //how big do we want each object
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE); //how many objects can fit
	static final int DELAY = 100; //delay for timer (higher the number slower the game
	
	//array for coordinates for body parts of planet
	final int x[] = new int[GAME_UNITS]; //x coordinates of body parts
	final int y[] = new int[GAME_UNITS]; //y coordinates of body parts
	int bodyParts = 3; //begins with six body parts on planet
	int planetsEaten;
	int planetX; //x coordinate where apple appears (random)
	int planetY;
	int starX;int starY;
	int starA; int starB;
	int starQ; int starR;
	char direction = 'R'; //planet begins going right 'U' up, 'D' down, 'L' left
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(new Color(25,29,87));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame()
	{
		newPlanet();
		newStar1();
		newStar2();
		newStar3();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		if(running)
		{
			//draw the grid using for loop to draw lines across the x and y access
			
			/*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) 
			{
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			
			//draw the planet
			g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			g.fillOval(planetX, planetY, UNIT_SIZE, UNIT_SIZE);
			
			//draw the stars
			g.setColor(Color.white);
			g.fillOval(starX, starY, 5, 5);
			
			g.setColor(Color.white);
			g.fillOval(starA, starB, 2, 2);
			
			g.setColor(Color.white);
			g.fillOval(starQ, starR, 2, 2);
			
				
			//draw the worm
			for(int i = 0; i< bodyParts;i++)
			{
				if(i == 0)
				{
					g.setColor(new Color(255, 153, 204));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //head of worm
				}
				else 
				{
					g.setColor(new Color(255, 204, 229)); //rgb value
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);//body of worm
				}
			}	
				
			//show score on game screen
			g.setColor(new Color(0,76,153));
			g.setFont(new Font("budmo",Font.BOLD, 20));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + planetsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + planetsEaten))/2, g.getFont().getSize());
			
		}
			else
				{
					gameOver(g);
				}
		}
	
	
	public void newPlanet() 
	{
		planetX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		planetY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void newStar1()
	{
		starX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		starY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void newStar2()
	{
		starA = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		starB = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void newStar3()
	{
		starQ = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		starR = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move() 
	{
		for(int i = bodyParts;i>0;i--) 
		{
			x[i] = x[i-1]; //shifting all coordinates in array over by 1
			y[i] = y[i-1];
		}
		
		switch(direction) 
		{
			case 'U':
				y[0] = y[0] - UNIT_SIZE; //coordinates for head of worm
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
	
	public void checkPlanet() 
	{
		if((x[0] == planetX) && (y[0] == planetY))
		{
			//body increases by 1 with each planet eaten
			bodyParts++;
			planetsEaten++;
			newPlanet();
			newStar1();
			newStar2();
			newStar3();
		}
	}
	
	public void checkCollisions() 
	{
		for(int i = bodyParts; i > 0; i--)
		{
			//checks if head collides with body
			if ((x[0] == x[i])&& (y[0] == y[i]))
				{	
					running = false; //triggers gameOver method
				}
			//checks if head touches left border
			if(x[0] < 0)
				{
					running = false;
				}
			if(x[0] == starX)
				{
					running = true;
					newStar1();
					newStar2();
					newStar3();
				}
			if(y[0] == starY)
				{
					running = true;
					newStar1();
					newStar2();
					newStar3();
				}
			
			if(x[0] > SCREEN_WIDTH)
				{
					running = false;
				}
			if(y[0] < 0)
				{	
					running = false;
				}
			if (y[0] > SCREEN_HEIGHT)
				{
					running = false;
				}
			if(!running)
				{
					timer.stop();
				}
		}
			
	}
	
	public void gameOver(Graphics g) 
	{
		//Game Over text
		g.setColor(new Color(255, 153, 204));
		g.setFont(new Font("budmo", Font.BOLD, 50));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
		//show score on game over screen
		g.setColor(new Color(0,76,153));
		g.setFont(new Font("budmo", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + planetsEaten, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + planetsEaten))/2, g.getFont().getSize());
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(running)
		{
			move();
			checkPlanet();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
					if(direction != 'R')
				{
					direction = 'L';
				}
				break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L')
				{
					direction = 'R';
				}
				break;
				case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction = 'U';
				}
				break;
				case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction = 'D';
				}
				break;
			}
		}
	}
}
	
