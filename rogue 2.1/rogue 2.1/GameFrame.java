import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame{
	ImageIcon img = new ImageIcon("icon.png");

	GameFrame(){
			
		this.add(new Rogue());
		this.setTitle("Rogue");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(img.getImage());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
	}


}
