/**
 * 
 * @author gsong
 */
package kNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import kNN.RecordComparator;
import kNN.ListResultR;
import kNN.Element;
import kNN.ListElemS;

public class KNNSingleVersion{
	private static int k;//the number of nearest neighbors
	private static int d;//dimension
	static String filePathR; //the file path for data set R
	static String filePathS; //the file path for data set S
	
	/**
	 * Constructor
	 * 
	 * @param k
	 * @param d
	 * @param filePathR
	 * @param fielPathS
	 */
	public KNNSingleVersion(int k, int d, String filePathR, String fielPathS){
		this.setK(k);
		this.setD(d);
		this.filePathR = filePathR;
		this.filePathS = fielPathS;
	}

	public static int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public static int getD() {
		return d;
	}

	public void setD(int d) {
		this.d = d;
	}
	
	/**
	 * 
	 * @param filePath
	 * @param d
	 * @return
	 */
	public static ArrayList<Element> Reader(String filePath, int d){
		File file = new File(filePath);
		BufferedReader reader = null;
		ArrayList<Element> readList = new ArrayList<Element>();
		try{
			reader = new BufferedReader(new FileReader(file));
			String tempsString = null;
			while((tempsString = reader.readLine())!=null){
				String parts[] = tempsString.split(" +");
				int id= Integer.parseInt(parts[0]);
				/*System.out.print("id: "+parts[0]);
				System.out.print(" x: "+parts[0+1]);
				System.out.println(" y: "+parts[1+1]);*/				
				float[] coord = new float[d];
				for(int ii=0; ii<d; ii++){
					try{
						coord[ii] = Float.valueOf(parts[1+ii]);
					}catch(NumberFormatException ex){
						
					}
				}
				Element le = new Element(id, coord);
				le.setId(id);
				le.setCoord(coord);
				readList.add(le);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader != null){
				try{
					reader.close();
				}catch(IOException e1){
					//Do nothing
				}
			}
		}
		return readList;
	}
	
	/**
	 * 
	 * @param r: an element in R
	 * @param S: dataset S
	 * @param d: dimensionality
	 * @return
	 */
	public static ArrayList<ListElemS> distRListS(Element r, ArrayList<Element> S){
		ArrayList<ListElemS> SWithDistance = new ArrayList<ListElemS>();
		for(int i=0; i<S.size(); i++){
			int sum = 0;
			for(int j=0; j<d; j++){
				float diff = Math.abs(r.getCoord()[j]-S.get(i).getCoord()[j]);
				float square = (float)Math.pow(diff, 2);
				sum += square;
			}
			float dist = (float) Math.sqrt(sum);
			ListElemS les = new ListElemS(S.get(i).getId(), S.get(i).getCoord(), dist);
			SWithDistance.add(les);
		}
		return SWithDistance;
	}
	
	/**
	 * 
	 * @param r
	 * @param S
	 * @param k
	 * @return
	 */
	public static ListResultR topKForOneR(Element r, ArrayList<Element> S){
		ArrayList<ListElemS> interm = distRListS(r, S);
		ListResultR kNNQueueR = new ListResultR(r.getId(), r.getCoord());
		RecordComparator rc = new RecordComparator();
		PriorityQueue<ListElemS> knnQueue = new PriorityQueue<ListElemS>(k + 1, rc);
		for(int i=0; i<interm.size(); i++){
			knnQueue.add(interm.get(i));
		}
		ArrayList<ListElemS> kNNQueue = new ArrayList<ListElemS>();
		for(int i=0; i<k; i++){
			kNNQueue.add(i, knnQueue.poll());
		}
		kNNQueueR.setTopNeighbor(kNNQueue);
		return kNNQueueR;
	}
	
	/**
	 * 
	 * @param R
	 * @param S
	 * @return
	 */
	public static ArrayList<ListResultR> kNNJoinRS(ArrayList<Element> R, ArrayList<Element> S){
		ArrayList<ListResultR> result = new ArrayList<ListResultR>();
		for(int i=0; i<R.size(); i++){
			result.add(topKForOneR(R.get(i), S));
		}
		return result;
	}
	
	public static void main(String[] args){
		KNNSingleVersion knn = new KNNSingleVersion(5, 2, "./data/databig1.txt", "./data/databig2.txt");
		ArrayList<Element> R = Reader(filePathR, d);
		ArrayList<Element> S = Reader(filePathS, d);
		ArrayList<ListResultR> result = kNNJoinRS(R, S);
		for(int i=0; i<result.size(); i++){
			System.out.println(result.get(i).toString()+ "\n");
		}
		
		
		
	}
	
}