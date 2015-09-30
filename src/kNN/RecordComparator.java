/**
 * 
 * @author gsong
 */
package kNN;

import kNN.ListElemS;

import java.util.Comparator;

public class RecordComparator implements Comparator<ListElemS>{

	public int compare(ListElemS o1, ListElemS o2) 
	{
		
		/**
		 * "ret" is used to see o1 and o2 which is closer to rid, 
		 * if the difference between the two distance of o1 and o2 is 0, then "ret" is the difference of o1-sid and o2-sid
		 * if the difference is bigger than 0, which means that o2 is closer than o1 to rid, then "ret" is 1
		 * if the difference is smaller than 0, which means that o1 is closer than o2 to rid, then "ret" is -1
		 */
		int ret = 0;
		
		//"dist" is the difference between the distance of o1 and the distance of o2
		float dist = o1.getDist() - o2.getDist();
		if (Math.abs(dist) < 1E-6) {
			ret = 0;
			//ret = o1.getId() - o2.getId();	
		} else if (dist > 0)
			ret = 1;
		else if (dist < 0)
			ret = -1;

		return ret;  //Descending order
	}

}