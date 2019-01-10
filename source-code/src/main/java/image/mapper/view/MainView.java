package image.mapper.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainView extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private JFrame frame;

	public MainView() throws IOException {

		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		frame.add(this);

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics g = img.getGraphics();

		g.setColor(Color.red);
		g.drawRect(2,2,frame.getWidth()-4,frame.getHeight()-4);

		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.01f));
	}


	public void mouseDragged(MouseEvent e) {
		System.out.println("mouseDragged");
	}

	public void mouseMoved(MouseEvent e) {
		System.out.println("Moved");
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			saySomething("Mouse released; # of clicks: "
					+ e.getClickCount(), e);
			Graphics g = img.getGraphics();
			g.setColor(Color.black);
			Point p = e.getPoint();
			g.drawRect(p.x,p.y,5,5);
			g.setColor(Color.GREEN);
			g.fillRect(p.x+1,p.y+1,4,4);

			g.dispose();
			repaint();
		}

	}

	public void mouseReleased(MouseEvent e) {
		if (e.isPopupTrigger()) {
			showMenu(e);
		}


	}

	public void mouseEntered(MouseEvent e) {
		saySomething("Mouse entered", e);
	}

	public void mouseExited(MouseEvent e) {
		saySomething("Mouse exited", e);
	}

	public void mouseClicked(MouseEvent e) {
		saySomething("Mouse clicked (# of clicks: "
				+ e.getClickCount() + ")", e);
	}

	void saySomething(String eventDescription, MouseEvent e) {
		System.out.println(eventDescription + " detected on "
				+ e.getComponent().getClass().getName()
				+ "." +"\n");
	}

	private void showMenu(MouseEvent e){
		Menu menu = new Menu(this);
		menu.show(e.getComponent(), e.getX(), e.getY());
	}
}
