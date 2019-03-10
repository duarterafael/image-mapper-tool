package image.mapper.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;

import image.mapper.model.ImageMapping;
import image.mapper.model.ImageRegion;


public class MainView {
	private static JFrame frame;
	public static final Color transparetColor = new Color(1.0f,1.0f,1.0f, 0.01f);
	private static ImageMapping imageMapping = new ImageMapping("", "");
	private static ImageMapping backImageMapping = new ImageMapping("", "");
	private static ImageRegion currentImageRegion = null;

	public static void createAndShowGUI()
	{		
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		frame = new JFrame("Model Mapper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setBackground(transparetColor);
		
		MenuPanel buttonPanel = new MenuPanel(frame);
		
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

	}

	static class MenuPanel extends JPanel implements ActionListener
	{
		private DrawingArea drawingArea;
		private JFrame frame;
		private JTextField regionLabelTF; 
				
		private String CLEAR_DRAWING_LABEL = "Clear";
		private String BACK_STEP_LABEL = "<<Back step";
		private String NEXT_STEP_LABEL = "Next step>>";
		private String SAVE_REGION_LABEL = "Save Region";
		private String SAVE_MAPPING_LABEL = "Save mapping";
		
		public MenuPanel(JFrame frame)
		{
			this.frame = frame;
			regionLabelTF = new JTextField(20);
			
			addDrawingArea();
			add(createButton(CLEAR_DRAWING_LABEL, null));
			add(createButton(BACK_STEP_LABEL, null));
			add(createButton(NEXT_STEP_LABEL, null));
			add(createButton(SAVE_REGION_LABEL, null));
			add(regionLabelTF);
			add(createButton(SAVE_MAPPING_LABEL, null));
		}

		private JButton createButton(String text, Color background)
		{
			JButton button = new JButton( text );
			button.setBackground( background );
			button.addActionListener( this );

			return button;
		}

		public void actionPerformed(ActionEvent e)
		{
			JButton button = (JButton)e.getSource();

			if (CLEAR_DRAWING_LABEL.equals(e.getActionCommand()))
			{
				clearDrawingAreaAction();
			}else if(BACK_STEP_LABEL.equals(e.getActionCommand()))
			{
				backStepAction();
			}else if(NEXT_STEP_LABEL.equals(e.getActionCommand()))
			{	
				nextStepAction();
			}else if(SAVE_MAPPING_LABEL.equals(e.getActionCommand()))
			{	
				
			}else if(SAVE_REGION_LABEL.equals(e.getActionCommand()))
			{
				if(imageMapping.getLastRegion().getPointList().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Please select a region before saving action.", "Warning Message", JOptionPane.WARNING_MESSAGE);	
				}else if(imageMapping.getLastRegion().getPointList().size() <= 2)
				{
					JOptionPane.showMessageDialog(frame, "A region must be composed of at least three points.", "Warning Message", JOptionPane.WARNING_MESSAGE);	
				}else if(regionLabelTF.getText().trim().isEmpty())
				{
					JOptionPane.showMessageDialog(frame, "Input a region label.", "Warning Message", JOptionPane.WARNING_MESSAGE);	
				}else
				{
					saveRegionAction();
				}
			}
		}
		
		public void buildDrawingArea()
		{
			removerDrawingArea();
			addDrawingArea();
			drawingArea.drawPointList();	
		}
		
		private void saveRegionAction()
		{
			imageMapping.getLastRegion().setLabel(regionLabelTF.getText().trim());
			currentImageRegion = null;
			backImageMapping.getRegionsList().clear();
			buildDrawingArea();
			regionLabelTF.setText("");
		}
		
		private void nextStepAction() {
			if(!backImageMapping.getRegionsList().isEmpty())
			{
				int lastIndex = backImageMapping.getLastRegion().getPointList().size()==1 ? 0 :  backImageMapping.getLastRegion().getPointList().size()-1;
				if(imageMapping.getRegionsList().isEmpty())
				{
					imageMapping.addRegionsList(new ImageRegion(backImageMapping.getLastRegion().getLabel()));
				}else
				{
					imageMapping.getLastRegion().setLabel(backImageMapping.getLastRegion().getLabel());
				}
				imageMapping.getLastRegion().getPointList().add(backImageMapping.getLastRegion().getPointList().get(lastIndex));
				backImageMapping.getLastRegion().getPointList().remove(lastIndex);	
				buildDrawingArea();
			}
			
		}
		
		
		private void backStepAction() {
			if(!imageMapping.getRegionsList().isEmpty() && !imageMapping.getLastRegion().getPointList().isEmpty())
			{
				int lastIndex = imageMapping.getLastRegion().getPointList().size()==1 ? 0 :  imageMapping.getLastRegion().getPointList().size()-1;
				if(backImageMapping.getRegionsList().isEmpty())
				{
					backImageMapping.getRegionsList().add(new ImageRegion(imageMapping.getLastRegion().getLabel()));	
				}
				backImageMapping.getLastRegion().getPointList().add(imageMapping.getLastRegion().getPointList().get(lastIndex));
				imageMapping.getLastRegion().getPointList().remove(lastIndex);
				if(lastIndex == 0)
				{
					if(imageMapping.getRegionsList().size() != 1)
					{
						backImageMapping.addRegionsList(new ImageRegion(null));
					}
					imageMapping.removeLastRegion();
					
				}
				if(backImageMapping.getLastRegion().getPointList().isEmpty())
				{
					backImageMapping.removeLastRegion();
				}
				
				buildDrawingArea();
			}
				
		}
		
		private void clearDrawingAreaAction()
		{
			imageMapping.getRegionsList().clear();
			backImageMapping.getRegionsList().clear();
			currentImageRegion = null;
			removerDrawingArea();
			addDrawingArea();
		}
		
		private void removerDrawingArea() {
			frame.getContentPane().remove(drawingArea);
			frame.repaint();
			repaint();
		}
		
		private void addDrawingArea() {
			drawingArea = new DrawingArea(frame.getWidth(), frame.getHeight());
			frame.getContentPane().add(drawingArea);
			frame.revalidate();
			frame.repaint();
			repaint();
		}
	}

	static class DrawingArea extends JPanel
	{
		private BufferedImage image;
		
		private void drawPointList() {
			Graphics2D g2d = (Graphics2D)image.getGraphics();
			System.out.println("--------------imageMapping-------------");
			System.out.println(imageMapping.toString());
			System.out.println("---------------------------");
			System.out.println("--------------backImageMapping-------------");
			System.out.println(backImageMapping.toString());
			System.out.println("---------------------------");
			
			
			if(!imageMapping.getRegionsList().isEmpty())
			{
				for (ImageRegion imageRegion  : imageMapping.getRegionsList()) {
					if(!imageRegion.getPointList().isEmpty())
					{
						drawPoints(g2d, imageRegion.getPointList());
						//g2d.drawLine(imageRegion.getPointList().get(imageRegion.getPointList().size()-1).x, imageRegion.getPointList().get(imageRegion.getPointList().size()-1).y, imageRegion.getPointList().get(0).x, imageRegion.getPointList().get(0).y);
			
//						Polygon polygon = new Polygon();
//						for (Point point : imageRegion.getPointList()) {
//							polygon.addPoint(point.x, point.y); 
//						}
//						
//						g2d.setColor(new Color(0.0f,0.0f,1.0f,0.01f));
//						g2d.fillPolygon(polygon);
						
					}
				}
			}
			
			repaint();
			
		}
		
		private void drawPoints(Graphics2D g2d, List<Point> points)
		{
			if(!points.isEmpty())
			{
				
				drawPoint(g2d, points.get(0));
				
				for(int i = 1; i < points.size(); i++)
				{
					drawPoint(g2d, points.get(i));
					g2d.drawLine(points.get(i-1).x, points.get(i-1).y, points.get(i).x, points.get(i).y);
				}
			}
		}
		
		public DrawingArea(int width, int height)
		{
			image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			setBackground(transparetColor);
			
			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);
			addMouseMotionListener(ml);
		}

		@Override
		public Dimension getPreferredSize()
		{
			return isPreferredSizeSet() ?
				super.getPreferredSize() : new Dimension(image.getWidth(), image.getHeight());
		}

		@Override
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			if (image != null)
			{
				g.drawImage(image, 0, 0, null);
			}
		}
		
		class MyMouseListener extends MouseInputAdapter
		{

			public void mousePressed(MouseEvent e)
			{
				Point p = e.getPoint();
				if(imageMapping.getRegionsList().isEmpty())
				{
					if(currentImageRegion == null)
					{
						currentImageRegion = new ImageRegion(null);
					}
					currentImageRegion.getPointList().add(p);
					imageMapping.addRegionsList(currentImageRegion);
					
				}else
				{
					if(currentImageRegion == null)
					{
						currentImageRegion = new ImageRegion(null);
						currentImageRegion.getPointList().add(p);
						imageMapping.addRegionsList(currentImageRegion);
					}else
					{
						imageMapping.getLastRegion().addPointList(p);
					}
					
				}
				drawPointList();
				repaint();
			}

			public void mouseDragged(MouseEvent e)
			{
			}

			public void mouseReleased(MouseEvent e)
			{
			}
			
		}
		private void drawPoint(Graphics g, Point p) {
			g.setColor(Color.black);
			g.drawRect(p.x-5,p.y-5,10,10);
			g.setColor(Color.GREEN);
			g.fillRect(p.x-4,p.y-4,9,9);
			g.setColor(Color.black);
			g.fillRect(p.x-1,p.y-1,2,2);
		}
	
	}
}
