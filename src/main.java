import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

public class main {

	// This main method runs the game

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame f = new JFrame();
	            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            f.setTitle("2048");
	            f.setResizable(true);
	            f.add(UserInterface.getInstance(), BorderLayout.CENTER);
	            f.pack();
	            f.setLocationRelativeTo(null);
	            f.setVisible(true);
	        });
	    }
	}