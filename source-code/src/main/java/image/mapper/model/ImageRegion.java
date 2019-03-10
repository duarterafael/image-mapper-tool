package image.mapper.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class ImageRegion {
	
	private String label;
	private List<Point> pointList = null;
	
	public ImageRegion(String label) {
		super();
		this.label = label;
		this.pointList = new LinkedList<Point>();
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Point> getPointList() {
		return pointList;
	}
	public void addPointList(Point point) {
		this.pointList.add(point);
	}
	
	@Override
	public String toString() {
		String str = "\nLabel: "+label;
		if(pointList.isEmpty())
		{
			str += "\npointList is empty";
		}else
		{
			for(int i = 0; i < pointList.size(); i++)
			{
				Point p = pointList.get(i);
				str += "\nPoint["+i+"]: "+"("+p.x+","+p.y+")";
			}	
		}
		return str;
	}

}
