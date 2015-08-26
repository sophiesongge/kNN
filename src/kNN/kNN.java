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
	static String filePathR;
	static String filePathS;
	
	public kNN(int k, int d, String filePathR, String fielPathS){
		this.k = k;
		this.d = d;
		this.filePathR = filePathR;
		this.filePathS = fielPathS;
	}
	
	
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
				
				int st = 1;
				float[] coord = null;	
				for (int i = 0; i < d; i++){
					coord[i] = Float.valueOf(parts[st + i]);
				}
					
				
				
/*				float[] coord = new float[d];
				for(int ii=0; ii<d; ii++){
					coord[ii] = Float.valueOf(parts[1+ii]);

				}*/
				
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
	
	public static Map<Integer, Float> topKSort(Map<Integer, Float> M, int k){
		List<Map.Entry<Integer,Float>> list = new ArrayList<Map.Entry<Integer,Float>>(M.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Float>>(){
			@Override
			public int compare(Entry<Integer, Float> o1,
					Entry<Integer, Float> o2) {
				// TODO Auto-generated method stub
				return (int) (o1.getValue()-o2.getValue());
			}
		});
		Map<Integer, Float> kNNMap4R = new LinkedHashMap<Integer, Float>();
		for(int i=0; i<k; i++){
			int key = (int)list.get(i).getKey();
			float value = (float)list.get(i).getValue();
			kNNMap4R.put(key,value);
		}
		return kNNMap4R;
	}

	
	public Map<Integer, Map<Integer, Float>> kNN4All(ArrayList<ListElem> R, ArrayList<ListElem> S){
		Map<Integer, Map<Integer, Float>> finalMap = new LinkedHashMap<Integer, Map<Integer, Float>>();
		for(int i=0; i<R.size(); i++){
			finalMap.put((Integer) R.get(i).getId(), topKSort(distMap4R(R.get(i),S,this.d), k));
		}
		return finalMap;
	}
	
	
	public static void main(String[] args){
		kNN test = new kNN(3, 2,  "/Users/gsong/Documents/workspace/kNN/data1.txt", "/Users/gsong/Documents/workspace/kNN/data2.txt");
		Map<Integer, Map<Integer, Float>> testMap = test.kNN4All(Reader(kNN.filePathR, kNN.d), Reader(kNN.filePathS, kNN.d));
		for(int i=0; i<kNN.Reader(filePathR, k).size(); i++){
			System.out.println("R is: "+Reader(kNN.filePathR, kNN.d).get(i).getId()+" And its top "+kNN.k+" neighbors are: "+testMap.get(Reader(kNN.filePathR, kNN.d).get(i).getId()).keySet().toString()+"\n With the distance of: "+testMap.get(Reader(kNN.filePathR, kNN.d).get(i).getId()));
			System.out.println("#################################");
		}
	}
	
}