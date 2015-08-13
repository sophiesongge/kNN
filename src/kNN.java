import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class kNN {
	static int k;
	static String filePathR;
	static String filePathS;
	
	public kNN(int k, String filePathR, String fielPathS){
		this.k = k;
		this.filePathR = filePathR;
		this.filePathS = fielPathS;
	}
	
	public static ArrayList<String> Reader(String filePath){
		File file = new File(filePath);
		BufferedReader reader = null;
		ArrayList<String> readList = new ArrayList<String>();
		try{
			reader = new BufferedReader(new FileReader(file));
			String tempsString = null;
			while((tempsString = reader.readLine())!=null){
				readList.add(tempsString);
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
		
	public static Map<String, Integer> distMap4R(String r, ArrayList<String> S){
		Map<String, Integer> dist41RMap  = new HashMap<String, Integer>();
		for(int i=0; i<S.size(); i++){
			int dist = Math.abs(Integer.valueOf(r)-Integer.valueOf((String)S.get(i)));
			dist41RMap.put(S.get(i), dist);
		}
		return dist41RMap;
	}
	
	public static Map<String, Integer> topKSort(Map<String, Integer> M, int k){
		List<Map.Entry<String,Integer>> list = new ArrayList<Map.Entry<String,Integer>>(M.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>(){
			@Override
			public int compare(Entry<String, Integer> o1,
					Entry<String, Integer> o2) {
				// TODO Auto-generated method stub
				return (o1.getValue()-o2.getValue());
			}
		});
		Map<String, Integer> kNNMap4R = new HashMap<String, Integer>();
		for(int i=0; i<k; i++){
			String key = (String)list.get(i).getKey();
			Integer value = (Integer)list.get(i).getValue();
			kNNMap4R.put(key,value);
		}
		return kNNMap4R;
	}

	
	public Map<String, Map<String, Integer>> kNN4All(ArrayList<String> R, ArrayList<String> S){
		Map<String, Map<String, Integer>> finalMap = new HashMap<String, Map<String, Integer>>();
		for(int i=0; i<R.size(); i++){
			finalMap.put((String) R.get(i), topKSort(distMap4R((String) R.get(i),S), k));
		}
		return finalMap;
	}
	
	
	public static void main(String[] args){
		kNN test = new kNN(3, "/Users/songsophie/Documents/workspace/kNN/R.txt", "/Users/songsophie/Documents/workspace/kNN/S.txt");
		Map<String, Map<String, Integer>> testMap = test.kNN4All(Reader(kNN.filePathR), Reader(kNN.filePathS));
		for(int i=0; i< kNN.Reader(kNN.filePathR).size(); i++){
			System.out.println("R is: "+Reader(kNN.filePathR).get(i)+" And its top "+kNN.k+" neighbors are: "+testMap.get(Reader(kNN.filePathR).get(i)));
			System.out.println("##########");
		}
	}
	
}