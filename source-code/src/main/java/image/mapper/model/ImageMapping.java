package image.mapper.model;

import java.awt.Point;
import java.awt.Polygon;
import java.util.LinkedList;
import java.util.List;

public class ImageMapping {
	private String label;
	private String imageFilePath;
	private List<ImageRegion> regionsList = null;
	
	public ImageMapping(String label, String imageFilePath) {
		super();
		this.label = label;
		this.imageFilePath = imageFilePath;
		this.regionsList = new LinkedList<ImageRegion>();
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getImageFilePath() {
		return imageFilePath;
	}
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	
	public ImageRegion getLastRegion() {
		return regionsList.get(regionsList.size() - 1);
	}
	
	public void removeLastRegion() {
		regionsList.remove(getLastRegion());
	}
	
	public List<ImageRegion> getRegionsList() {
		return regionsList;
	}
	public void addRegionsList(ImageRegion imageRegions) {
		this.regionsList.add(imageRegions);
	}
	
	@Override
	public String toString() {
		String str = "Label: "+label;
		str += "\nimageFilePath: "+imageFilePath;
		
		for(int i = 0; i < regionsList.size(); i++)
		{
			ImageRegion ir = regionsList.get(i);
			str += "\nRegionsList["+i+"]: "+ir.toString();
		}
		return str;
	}
	
	public List<ImageRegion> getImageRegionsContainsPoint(Point point)
	{
		List<ImageRegion> imageRegionsContainsPoint = new LinkedList<ImageRegion>();
		for (ImageRegion imageRegion : regionsList) {
			Polygon polygon = new Polygon();
			for (Point pointItem : imageRegion.getPointList()) {
				polygon.addPoint(pointItem.x, pointItem.y);
			}
			if(polygon.contains(point))
			{
				imageRegionsContainsPoint.add(imageRegion);
			}
		}
		return imageRegionsContainsPoint;
	}
	
}
