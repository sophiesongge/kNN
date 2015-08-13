public class LeftShift{
	public static void main(String [] argv){
		LeftShift l = new LeftShift();
		//System.out.println(l);
		//l.leftShiftOne("Sophie");
		l.leftShift("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 26);
		
	}
	
	//Attributes
	
	//Constructors
	
	//Method
	
	public String leftShiftOne(String a){
		int l = a.length();
		int i = 1;
		char [] aa = a.toCharArray();
		char c = aa[0];
		for(i=1;i<l;i++){
			aa[i-1]=aa[i];
		}
		aa[l-1]=c;
		a = String.valueOf(aa);
		System.out.println(a);
		return a;
	}
	
	public void leftShift(String a, int m){
		LeftShift l = new LeftShift();
		int j = 0;
		while(j<m){
			a = l.leftShiftOne(a);
			j++;
		}
		
	}
}