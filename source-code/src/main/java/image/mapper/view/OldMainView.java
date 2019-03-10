package image.mapper.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Area;
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

import image.mapper.model.ImageMapping;

public class OldMainView extends JPanel implements MouseMotionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private BufferedImage img;
	private JFrame frame;
	private List<Point> pointList = new LinkedList<Point>();
	private ImageMapping imageMapping = new ImageMapping("SampleLable", "SamplePath");
	
	public OldMainView() throws IOException {

		frame = new JFrame();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);

		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		frame.add(this);

		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		//Set background screen to transparent
		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		img = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);

		Graphics g = img.getGraphics();

		//Draw a red rectangle for indicating the region mapping. 
//		g.setColor(Color.red);
//		g.fillRect(2,2,frame.getWidth()-4,frame.getHeight()-4);
//		g.drawRect(1,1,frame.getWidth()-2,frame.getHeight()-2);
//		g.drawRect(0,0,frame.getWidth(),frame.getHeight());
	
		g.setColor(Color.red);
		g.fillRect(100, 100, 200, 200);
		g.finalize();
		g.dispose();
		repaint();
		
		String saveRegionKey = "Save Region";
		addShortKey(saveRegionKey, new AbstractAction(saveRegionKey) {
		    public void actionPerformed(ActionEvent evt) {
		    	Graphics g = img.getGraphics();
		    	g.setColor(new Color(1.0f,1.0f,1.0f,0.1f));
		    	setBackground(new Color(1.0f,1.0f,1.0f,0.1f));
		    	g.clearRect(150, 150, 50, 50);
		    	g.dispose();
		    	repaint();
//		    	BufferedImage newImage = new BufferedImage(
//		    	        img.getWidth(), img.getHeight(), img.getType());
//
//		    	    Graphics2D g2 = newImage.createGraphics();
//
//		    	    Rectangle entireImage =
//		    	        new Rectangle(img.getWidth(), img.getHeight());
//
//		    	    Area clip = new Area(entireImage);
//		    	    Rectangle r = new Rectangle(img.getMinX(), img.getMinY(), img.getWidth(), img.getHeight());
//		    	    clip.subtract(new Area(r));
//
//		    	    g2.clip(clip);
//		    	    //g2.drawImage(newImage, 0, 0, null);
//
//		    	    g2.dispose();
//		    	    img = newImage;
//		    	    repaint();

				
		    	//saveRegion();   
//		    	Graphics g = img.getGraphics();
//		    	g.finalize();
//		    	g.setColor(new Color(1.0f,1.0f,1.0f,0.1f));
//		    	g.fillRect(100, 100, 500, 500);
////		    	g.setColor(new Color(1.0f,1.0f,1.0f,0.01f));
////		    	g.fillRect(100, 100, 200, 200);
//		    	g.dispose();
		    	
		    }
		}, KeyEvent.VK_F2 );
		
		String saveRegionKey2 = "Save Region2";
		addShortKey(saveRegionKey2, new AbstractAction(saveRegionKey2) {
		    public void actionPerformed(ActionEvent evt) {
		    	//saveRegion();   
		    	Graphics g = img.getGraphics();
		    	g.setColor(Color.gray);
				g.fillRect(300, 300, 200, 200);	
				System.out.println("F3");
				g.dispose();
				repaint();

		    }
		}, KeyEvent.VK_F3 );
		
	}

	public void mouseDragged(MouseEvent e) {
	}

	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}
	

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			Graphics g = img.getGraphics();
			Point p = e.getPoint();
			drawPressedPoint(g, p);

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
	
	private void drawPressedPoint(Graphics g, Point p) {
		g.setColor(Color.black);
		g.drawRect(p.x-5,p.y-5,10,10);
		g.setColor(Color.GREEN);
		g.fillRect(p.x-4,p.y-4,9,9);
		g.setColor(Color.black);
		g.fillRect(p.x-1,p.y-1,2,2);
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
	
	private void saveRegion() {
		if(pointList.size() >= 3)
    	{
    		Graphics g = img.getGraphics();
	    	g.setColor(Color.black);
	    	g.drawLine(pointList.get(pointList.size()-1).x, pointList.get(pointList.size()-1).y, pointList.get(0).x, pointList.get(0).y);
	    	
	    	Polygon polygon = new Polygon();
			for (Point point : pointList) {
				polygon.addPoint(point.x, point.y); 
			}
			g.setColor(new Color(0.0f,0.0f,1.0f,0.1f));
			g.fillPolygon(polygon);
			g.dispose();
			
			repaint();
			
			pointList.clear();
	   	
    	}
	}
}
