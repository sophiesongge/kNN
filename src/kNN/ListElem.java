package kNN;

public class ListElem{
	private int id;
	private float[] coord;
	private float dist;
	
	public ListElem(int dimension, float distance, int id){
		this.id = id;
		this.dist = distance;
	}
	
	public ListElem(int id, float[] coord){
		this.id = id;
		this.setCoord(coord);
	}
	
	public ListElem(int dimension, float distance){
		this.dist = distance;
	}
	void setDist(float value) { this.dist = value; }

	float getDist() { return this.dist; }
	
	public void setId(int id) { this.id = id; }

	int getId() { return this.id; }

	public float[] getCoord() {
		return coord;
	}

	public void setCoord(float[] coord) {
		this.coord = coord;
	}
	
	public String toString() 
	{
		return Integer.toString(this.id) + " " + Float.toString(this.dist);	
	}
	
}