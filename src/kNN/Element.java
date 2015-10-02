/**
 * This is an element in R or in S with ID represents its ID, and coord[] represents its coordoner
 * @author gsong
 */
package kNN;

public class Element{
	private int id;
	private float[] coord;

	
	public Element(int dimension, int id){
		this.id = id;
	}
	
	public Element(int id, float[] coord){
		this.id = id;
		this.setCoord(coord);
	}
	
	public void setId(int id) { this.id = id; }

	public int getId() { return this.id; }

	public float[] getCoord() {
		return coord;
	}

	public void setCoord(float[] coord) {
		this.coord = coord;
	}
	
	public String toString() 
	{
		return Integer.toString(this.id);	
	}
}