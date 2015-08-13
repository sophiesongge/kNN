import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
	
	public static ArrayList Reader(String filePath){
		File file = new File(filePath);
		BufferedReader reader = null;
		ArrayList readList = new ArrayList();
		try{
			reader = new BufferedReader(new FileReader(file));
			String tempsString = null;
			int line = 1;
			while((tempsString = reader.readLine())!=null){
				readList.add(tempsString);
				line++;
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
	
/*	//等会儿注释掉
	public static Map distMap(ArrayList R, ArrayList S){
		Map rMap = new HashMap();
		for(int i = 0; i < R.size(); i++){
			Map riMap = new HashMap();
			for(int j = 0; j<S.size(); j++){
				int dist = Math.abs(Integer.valueOf((String) R.get(i))-Integer.valueOf((String) S.get(j)));
				riMap.put(S.get(j), dist);
			}
			rMap.put(R.get(i), riMap);
		}
		return rMap;
	}
	//一直到这儿
*/	
	public static Map<String, Integer> distMap4R(String r, ArrayList S){
		Map dist41RMap  = new HashMap();
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
		Map<String, Integer> kNNMap4R = new HashMap();
		for(int i=0; i<k; i++){
			String key = (String)list.get(i).getKey();
			Integer value = (Integer)list.get(i).getValue();
			kNNMap4R.put(key,value);
		}
		return kNNMap4R;
	}

	
	public static Map kNN4All(ArrayList R, ArrayList S){
		Map<String, Map<String, Integer>> finalMap = new HashMap();
		for(int i=0; i<R.size(); i++){
			finalMap.put((String) R.get(i), topKSort(distMap4R((String) R.get(i),S), k));
		}
		return finalMap;
	}
	
	
	public static void main(String[] args){
		kNN test = new kNN(3, "/Users/gsong/Documents/workspace/PracticeJava/R.txt", "/Users/gsong/Documents/workspace/PracticeJava/S.txt");
		Map testMap = test.kNN4All(Reader(kNN.filePathR), Reader(kNN.filePathS));
		for(int i=0; i< test.Reader(test.filePathR).size(); i++){
			System.out.println("R is: "+Reader(test.filePathR).get(i)+" And its top "+test.k+" neighbors are: "+testMap.get(Reader(test.filePathR).get(i)));
			System.out.println("##########");
		}
	}
	
}