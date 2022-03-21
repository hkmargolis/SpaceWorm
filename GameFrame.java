import javax.swing.JFrame;

public class GameFrame extends JFrame
{
	
	GameFrame()
	{
	this.add(new GamePanel()); //instance of new GamePanel
	this.setTitle("SpaceWorm");
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setResizable(false);
	this.pack(); //takes Jframe and fits components
	this.setVisible(true);
	this.setLocationRelativeTo(null); //allows us to see panel centered on screen
	}
}
