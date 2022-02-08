/**
 * @File: UserInterface.java
 * @Author: Lin Rozenszajn - rozensl
 * @Date: April 16, 2021
 * @Description: a view module for displaying the game and enabling user interaction
 */

package a4;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
 
public class UserInterface extends JPanel {
	
	private static UserInterface visual = null;
 
    final static Color[] colorTable = {
        new Color(0x701710), new Color(0xFFE4C3), new Color(0xfff4d3),
        new Color(0xffdac3), new Color(0xe7b08e), new Color(0xe7bf8e),
        new Color(0xffc4c3), new Color(0xE7948e), new Color(0xbe7e56),
        new Color(0xbe5e56), new Color(0x9c3931), new Color(0x701710)};
 
    private static Color gridColor = new Color(0xBBADA0);
    private static Color emptyColor = new Color(0xCDC1B4);
    private static Color startColor = new Color(0xFFEBCD);
 
    private static Random rand = new Random();
 
    public static UserInterface getInstance() {
    	if (visual == null) {
    		return visual = new UserInterface();
    	}
    	return visual;
    }
    
    private UserInterface() {
    	Board model = Board.getInstance();
    	GameController control = GameController.getInstance(model);
        setPreferredSize(new Dimension(900, 700));
        setBackground(new Color(0xFAF8EF));
        setFont(new Font("SansSerif", Font.BOLD, 48));
        setFocusable(true);
 
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                control.startGame();
                repaint();
            }
        });
 
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        control.moveUp();
                        break;
                    case KeyEvent.VK_DOWN:
                        control.moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        control.moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT:
                        control.moveRight();
                        break;
                }
                repaint();
            }
        });
    }
 
    @Override
    public void paintComponent(Graphics graphic1) {
        super.paintComponent(graphic1);
        Graphics2D graphic2 = (Graphics2D) graphic1;
        graphic2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
 
        drawGrid(graphic2);
    }
 
    void drawGrid(Graphics2D graphic2) {
        graphic2.setColor(gridColor);
        graphic2.fillRoundRect(200, 100, 499, 499, 15, 15);
 
        if (Board.getGamestate() == Board.State.running) {
 
            for (int r = 0; r < Board.getSide(); r++) {
                for (int c = 0; c < Board.getSide(); c++) {
                    if (Board.gameboard[r][c] == null) {
                        graphic2.setColor(emptyColor);
                        graphic2.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);
                    } else {
                        drawTile(graphic2, r, c);
                    }
                }
            }
        } else {
            graphic2.setColor(startColor);
            graphic2.fillRoundRect(215, 115, 469, 469, 7, 7);
 
            graphic2.setColor(gridColor.darker());
            graphic2.setFont(new Font("SansSerif", Font.BOLD, 128));
            graphic2.drawString("2048", 310, 270);
 
            graphic2.setFont(new Font("SansSerif", Font.BOLD, 20));
 
            if (Board.getGamestate() == Board.State.won) {
                graphic2.drawString("you made it!", 390, 350);
 
            } else if (Board.getGamestate() == Board.State.over)
                graphic2.drawString("game over", 400, 350);
 
            graphic2.setColor(gridColor);
            graphic2.drawString("click to start a new game", 330, 470);
            graphic2.drawString("(use arrow keys to move model)", 310, 530);
        }
    }
 
    static void drawTile(Graphics2D graphic2, int r, int c) {
        int value = Board.gameboard[r][c].getValue();
 
        graphic2.setColor(colorTable[(int) (Math.log(value) / Math.log(2)) + 1]);
        graphic2.fillRoundRect(215 + c * 121, 115 + r * 121, 106, 106, 7, 7);
        String s = String.valueOf(value);
 
        graphic2.setColor(value < 128 ? colorTable[0] : colorTable[1]);
 
        FontMetrics fm = graphic2.getFontMetrics();
        int asc = fm.getAscent();
        int dec = fm.getDescent();
 
        int x = 215 + c * 121 + (106 - fm.stringWidth(s)) / 2;
        int y = 115 + r * 121 + (asc + (106 - (asc + dec)) / 2);
 
        graphic2.drawString(s, x, y);
    }
 
     static void addRandomTile() {
        int pos = rand.nextInt(Board.getSide() * Board.getSide());
        int row, col;
        do {
            pos = (pos + 1) % (Board.getSide() * Board.getSide());
            row = pos / Board.getSide();
            col = pos % Board.getSide();
        } while (Board.gameboard[row][col] != null);
 
        int val = rand.nextInt(10) == 0 ? 4 : 2;
        Board.gameboard[row][col] = new TileT(val);
    }