/**
 * This is the random partition method used for block nested loop
 * @author gsong
 */
package Partition;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Partition.PartitionBase;
import Partition.PartitionValue;

import kNN.Element;

public class RandomPartition{
	private static int d; //dimension
	private static int numberOfPartition; //the number of partition
	static String filePath; //file path
	
	public RandomPartition(int d, int numberOfPartition, String filePath){
		this.d = d;
		this.numberOfPartition = numberOfPartition;
		this.filePath = filePath;
	}
	
	public void Partition() throws IOException{
		List<String> fileList = new ArrayList<String>();
		File file = new File(filePath+"/input");
		fileList = PartitionBase.getFileList(file);
		int fileID = 0;
		ArrayList<Element> context = new ArrayList<Element>();
		
		for(Iterator<String> iter = fileList.iterator(); iter.hasNext();){

			String path = (String)iter.next();
			String[] split = path.split("/");
			int n = split.length;
			String name = split[n-1]; //将每一个在fileList中的文件的文件名读出来
			if(name.matches("data1.txt")){
				fileID = 0;
			}else if(name.matches("data2.txt")){
				fileID = 1;
			}
			if(fileID == 0){
				System.out.println("data1");
			}else if(fileID == 1){
				System.out.println("data2");
			}
			for(int i=0; i<numberOfPartition*numberOfPartition; i++){
				File newFile = new File(filePath+"/output/partition"+i+".txt");
				if(!newFile.exists()){
					newFile = PartitionBase.CreateFile(filePath+"/output/partition"+i+".txt");
				}
			}
			
			context = PartitionBase.Reader(path, d, 0); //这个是把文件每一行读出来变成一个记录，每个记录就是一个Element
			
			
			/**
			 * 为每个partition生成一个文件，如果这个文件不存在则create一个
			 */
			
			
			for(int i=0; i<context.size(); i++){
				Random r = new Random();
				int partID = r.nextInt(numberOfPartition); //partition ID
				int groupID = 0; //每个block会去n个partition，组合的ID
				int recID = (int)context.get(i).getId();
				float[] coord = context.get(i).getCoord();
				
				PartitionValue pv = new PartitionValue(recID, coord, (byte)fileID, d);
				
				Scanner sc = null;
				PrintWriter pw = null;

				for(int k=0; k<numberOfPartition; k++){
					if(fileID == 0){
						groupID = partID*numberOfPartition + k;
						File outputFile = new File(filePath+"/output/partition"+groupID+".txt");
						sc = new Scanner(outputFile);
						StringBuilder sb = new StringBuilder();
						//先读出旧文件内容，并暂时存储在sb中
						while(sc.hasNextLine()){
							sb.append(sc.nextLine());
							sb.append("\n");//换行符作为间隔，扫描器读不出来，需要自己添加
						}
						sc.close();
						
						pw = new PrintWriter(new FileWriter(outputFile), true);
						pw.println(sb.toString());//写入旧文件内容
						pw.println("<"+"Partition,"+groupID+","+"<"+pv.toString(2)+">"+">"+" "+"Line No."+i+" "+"fileID: "+fileID);
						pw.close();
						System.out.println("<"+"Partition,"+groupID+","+"<"+pv.toString(2)+">"+">"+" "+"Line No."+i+" "+"fileID: "+fileID);
					}else if(fileID == 1){
						groupID = partID + k*numberOfPartition;
						File outputFile = new File(filePath+"/output/partition"+groupID+".txt");
						sc = new Scanner(outputFile);
						StringBuilder sb = new StringBuilder();
						while(sc.hasNextLine()){
							sb.append(sc.nextLine());
							sb.append("\n");
						}
						sc.close();
						
						pw = new PrintWriter(new FileWriter(outputFile), true);
						pw.println(sb.toString());//写入旧文件内容
						pw.println("<"+"Partition,"+groupID+","+"<"+pv.toString(2)+">"+">"+" "+"Line No."+i+" "+"fileID: "+fileID);
						pw.close();
						System.out.println("<"+"Partition,"+groupID+","+"<"+pv.toString(2)+">"+">"+" "+"Line No."+i+" "+"fileID: "+fileID);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		RandomPartition rp = new RandomPartition(2, 3, "./data");
		rp.Partition();
	}

}