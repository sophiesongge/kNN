/**
 * Single Machine Version for processing kNN Join
 * 
 * @author gsong
 */

package kNN;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kNN.ListElem;


public class kNN {
	static int k;//the number of nearest neighbors
	static int d;//dimension
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
	public kNN(int k, int d, String filePathR, String fielPathS){
		this.k = k;
		this.d = d;
		this.filePathR = filePathR;
		this.filePathS = fielPathS;
	}
	
	/**
	 * The method for reading the input file and transform it into an ArrayList
	 * 
	 * @param filePath
	 * @param d
	 * @return
	 */
	public static ArrayList<ListElem> Reader(String filePath, int d){
		File file = new File(filePath);
		BufferedReader reader = null;
		ArrayList<ListElem> readList = new ArrayList<ListElem>();
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
				
				ListElem le = new ListElem(id, coord);
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
	 * The method for calculating the distance between an element r and all element S, the result is a HashMap, with s_i as its key, 
	 * and the distance between r and s_i as its value 	
	 * 
	 * @param r
	 * @param S
	 * @param d
	 * @return
	 */
	public static Map<Integer, Float> distMap4R(ListElem r, ArrayList<ListElem> S, int d){
		Map<Integer, Float> dist41RMap  = new HashMap<Integer, Float>();
		for(int i=0; i<S.size(); i++){
			int sum = 0;
			for(int j=0; j<d; j++){
				float diff = Math.abs(r.getCoord()[j]-S.get(i).getCoord()[j]);
				float square = (float) Math.pow(diff, 2);
				sum += square;
			}
			float dist = (float) Math.sqrt(sum);
			dist41RMap.put(S.get(i).getId(), dist);
		}
		return dist41RMap;
	}
	
	
	/**
	 * The method to sort the result of the distance between r and s_i, the result is a LindedHashMap (sorted),
	 * with s_i as its key and the distance between r and s_i as its value (it only contains k elements)
	 * @param M
	 * @param k
	 * @return
	 */
	public static LinkedHashMap<Integer, Float> topKSort(Map<Integer, Float> M, int k){
		List<Map.Entry<Integer,Float>> list = new ArrayList<Map.Entry<Integer,Float>>(M.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Float>>(){
			@Override
			public int compare(Entry<Integer, Float> o1,
					Entry<Integer, Float> o2) {
				// TODO Auto-generated method stub
				//return (int) (o1.getValue()-o2.getValue());
				
				return ((o1.getValue() - o2.getValue() == 0) ? 0: (o1.getValue() - o2.getValue() > 0) ? 1: -1);
			}
		});
		Map<Integer, Float> kNNMap4R = new LinkedHashMap<Integer, Float>();
		for(int i=0; i<k; i++){
			int key = (int)list.get(i).getKey();
			float value = (float)list.get(i).getValue();
			kNNMap4R.put(key,value);
		}
		return (LinkedHashMap<Integer, Float>) kNNMap4R;
	}

	/**
	 * The method to get the kNN for all the elements in R and all the elements in S
	 * the return is a HashMap with r as its key and the top k nearest elements as its value
	 * @param R
	 * @param S
	 * @return
	 */
	public Map<Integer, LinkedHashMap<Integer, Float>> kNN4All(ArrayList<ListElem> R, ArrayList<ListElem> S){
		Map<Integer, LinkedHashMap<Integer, Float>> finalMap = new HashMap<Integer, LinkedHashMap<Integer, Float>>();
		for(int i=0; i<R.size(); i++){
			finalMap.put((Integer) R.get(i).getId(), (LinkedHashMap<Integer, Float>) topKSort(distMap4R(R.get(i),S,this.d), k));
		}
		return finalMap;
	}
	
	
	public static void main(String[] args){
		kNN test = new kNN(3, 2,  "./data/test1.txt", "./data/test2.txt");
		Map<Integer, LinkedHashMap<Integer, Float>> testMap = test.kNN4All(Reader(kNN.filePathR, kNN.d), Reader(kNN.filePathS, kNN.d));
		for(int i=0; i<kNN.Reader(filePathR, k).size(); i++){
			System.out.println("R is: "+Reader(kNN.filePathR, kNN.d).get(i).getId()+" And its top "+kNN.k+" neighbors are: "+testMap.get(Reader(kNN.filePathR, kNN.d).get(i).getId()).keySet().toString()+"\n With the distance of: "+testMap.get(Reader(kNN.filePathR, kNN.d).get(i).getId()));
			System.out.println("#################################");
		}

	}
	
}