package Partition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kNN.Element;
import kNN.ListElem;

public class PartitionBase{
	
	public static List<String> getFileList(File file){
		List<String> result = new ArrayList<String>();
		if(!file.isDirectory()){
			System.out.println(file.getAbsolutePath());
			result.add(file.getAbsolutePath());
		}else{
			File[] directoryList = file.listFiles(new FileFilter(){
				public boolean accept(File file){
					if(file.isFile() && file.getName().indexOf("txt") > -1){
						return true;
					}else{
						return false;
					}
				}
			});
			for(int i=0; i<directoryList.length; i++){
				result.add(directoryList[i].getAbsolutePath());
			}
		}
		return result;
	}
	
	
	
	public static String ReadFile(String Path) throws IOException{
		File fileName = new File(Path);
		FileReader reader = new FileReader(fileName);
		int fileLen = (int)fileName.length();
		char[] chars = new char[fileLen];
		reader.read(chars);
		String txt = String.valueOf(chars);
		return txt;
	}

	
	public static ArrayList<Element> Reader(String filePath, int d, int recordIdOffset){
		File file = new File(filePath);
		BufferedReader reader = null;
		ArrayList<Element> readList = new ArrayList<Element>();
		try{
			reader = new BufferedReader(new FileReader(file));
			String tempsString = null;
			while((tempsString = reader.readLine())!=null){
				String parts[] = tempsString.split(" +");
				int id= Integer.parseInt(parts[recordIdOffset]);			
				float[] coord = new float[d];
				for(int ii=0; ii<d; ii++){
					try{
						coord[ii] = Float.valueOf(parts[recordIdOffset+1+ii]);
					}catch(NumberFormatException ex){
						
					}
				}
				Element le = new Element(id, coord);
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
	
	public void WriteToFile(String str, File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.append(str);
		bw.close();
	}
	
	public static File CreateFile(String Path) throws IOException{
		File newFile = new File(Path);
		if(!newFile.exists()){
			newFile.createNewFile();
		}
		else{
			newFile.delete();
			System.out.println("Old File Delete Success!");
			newFile.createNewFile();
			System.out.println("New File Create Success!");
		}
		return newFile;
	}
	
}