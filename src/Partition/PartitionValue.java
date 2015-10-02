package Partition;
public class PartitionValue{
	private int first; //Record ID
	private float[] second; //Record's Coordonates
	private Byte third; //Record Partition
	
	public PartitionValue(int first, float[] second, byte third){
		this.setFirst(first);
		this.setSecond(second);
		this.setThird(third);
	}
	
	public PartitionValue(int first, float[] second, byte third, int dimension){
		this.setFirst(first);
		this.setSecond(second);
		this.setThird(third);
	}
	
	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public float[] getSecond() {
		return second;
	}

	public void setSecond(float[] second) {
		this.second = second;
	}

	public Byte getThird() {
		return third;
	}

	public void setThird(Byte third) {
		this.third = third;
	}
	
	public String toString(int dimension){
		int n = first;
		String result;
		result = Integer.toString(first)+" ";
		float[] f = second;
		for(int i=0; i<f.length; i++){
			result = result + f[i] + " ";
		}
		byte b = third;
		result = result + Byte.toString(b);
		return result;
	}
}