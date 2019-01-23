package image.mapper.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class MainView extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private JFrame frame;
	private List<Point> pointList = new LinkedList<Point>();

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

		//Draw a red rectangle for indicating the region mapping. 
		g.setColor(Color.red);
		g.drawRect(2,2,frame.getWidth()-4,frame.getHeight()-4);
		g.drawRect(1,1,frame.getWidth()-2,frame.getHeight()-2);
		g.drawRect(0,0,frame.getWidth(),frame.getHeight());

		//Set background screen to transparent
		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.01f));
		
		String saveRegionKey = "Save Region";
		addShortKey(saveRegionKey, new AbstractAction(saveRegionKey) {
		 
		    public void actionPerformed(ActionEvent evt) {
		    	Graphics g = img.getGraphics();
		    	Polygon polygon = new Polygon();
				for (Point point : pointList) {
					polygon.addPoint(point.x, point.y); 
				}
				g.setColor(new Color(0.0f,1.0f,0.0f,0.3f));
				g.fillPolygon(polygon);
				g.dispose();
				repaint();
				pointList.clear();
		        
		    }
		}, KeyEvent.VK_F2 );
		
	}


	private void addShortKey(String key, Action buttonAction, int keyEvent ) {
		JButton button = new JButton();
		 
		button.setAction(buttonAction);
		buttonAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		        KeyStroke.getKeyStroke(keyEvent, 0), key);
		button.getActionMap().put(key, buttonAction);
		
		frame.add(button);
	}


	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
//			saySomething("Mouse released; # of clicks: "
//					+ e.getClickCount(), e);
			Graphics g = img.getGraphics();
			g.setColor(Color.black);
			Point p = e.getPoint();
			g.drawRect(p.x-5,p.y-5,10,10);
			g.setColor(Color.GREEN);
			g.fillRect(p.x-4,p.y-4,9,9);
			g.setColor(Color.black);
			g.fillRect(p.x-1,p.y-1,2,2);

			pointList.add(p);

			for(int i = 1; i < pointList.size(); i++)
			{
				g.drawLine(pointList.get(i-1).x, pointList.get(i-1).y, pointList.get(i).x, pointList.get(i).y);
			}

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
