/**
 * This is the List of result of R, with R_ID and its top k S(S_id, S_Coord, S_dist)
 * @author gsong
 */
package kNN;

import java.util.ArrayList;

public class ListResultR{
	private int id;
	private float[] coord;
	private ArrayList<ListElemS> topNeighbor;
	
	public ListResultR(int id, float[] coord){
		this.id = id;
		this.setCoord(coord);
	}
	
	public ListResultR(int dimension, int id, float[] coord){
		this.id = id;
		this.setCoord(coord);;
	}
	
	public ListResultR(int id, float[] coord, ArrayList<ListElemS> neighbors){
		this.id = id;
		this.setCoord(coord);
		this.setTopNeighbor(neighbors);
	}
	
	void setId(int id) { this.id = id; }

	int getId() { return this.id; }

	public float[] getCoord() {
		return coord;
	}

	public void setCoord(float[] coord) {
		this.coord = coord;
	}


	public ArrayList<ListElemS> getTopNeighbor() {
		return topNeighbor;
	}

	public void setTopNeighbor(ArrayList<ListElemS> topNeighbor) {
		this.topNeighbor = topNeighbor;
	}
	
/*	public String toStringArray(){
		String result = "[";
		for(int i=0; i<this.topNeighbor.size(); i++){
			result += topNeighbor.toString(); 
		}
		result += "]";
		return result;
		
	}*/
	
	public String toString() 
	{
		return "R_ID: "+Integer.toString(this.id) + " \n"+ "Its neighbors are: \n" + topNeighbor.toString();	
	}
}