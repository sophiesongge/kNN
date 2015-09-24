/**
 * 
 * @author gsong
 */
package kNN;

import java.util.Comparator;

public class RecordComparator implements Comparator<ListElem>{

	@Override
	public int compare(ListElem o1, ListElem o2) {
		
		// TODO Auto-generated method stub
		
		int res = 0;
		float dist = o2.getDist() - o1.getDist();
		if(Math.abs(dist)<1E-6){
			res = o2.getId() - o1.getId();
		}else if(dist > 0){
			res = 1;
		}else if(dist < 0){
			res = -1;
		}
		
		return -res;
	}
	
	public int compare(ListElemS o1, ListElemS o2){
		// TODO Auto-generated method stub
		
				int res = 0;
				float dist = o2.getDist() - o1.getDist();
				if(Math.abs(dist)<1E-6){
					res = o2.getId() - o1.getId();
				}else if(dist > 0){
					res = 1;
				}else if(dist < 0){
					res = -1;
				}
				
				return -res;
	}
	
}