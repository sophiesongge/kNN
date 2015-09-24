/**
 * This is the Element List of S, with S_ID, the coordoner of S, and its distance with an R
 * @author gsong
 */
package kNN;

public class ListElemS{
	private int id;
	private float[] coord;
	private float dist;
	
	public ListElemS(int dimension, float distance, int id){
		this.id = id;
		this.dist = distance;
	}
	
	public ListElemS(int id, float[] coord){
		this.id = id;
		this.setCoord(coord);
	}
	
	public ListElemS(int id, float[] coord, float distance){
		this.id = id;
		this.setCoord(coord);
		this.dist = distance;
	}
	
	public ListElemS(int dimension, float distance){
		this.dist = distance;
	}
	void setDist(float value) { this.dist = value; }

	float getDist() { return this.dist; }
	
	void setId(int id) { this.id = id; }

	int getId() { return this.id; }

	public float[] getCoord() {
		return coord;
	}

	public void setCoord(float[] coord) {
		this.coord = coord;
	}
	
	public String toString() 
	{
		return Integer.toString(this.id) + ":" + Float.toString(this.dist);	
	}
}