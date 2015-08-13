public class Light{
	
	public static void main(String [] argv){
		Light l1 = new Light("Black", "L1");
		Light l2 = new Light(10, "L2");
		l1.turnOn();
		l2.turnOff();
		l1.checkName();
		l1.checkStatus();
		l1.checkColour();
		l2.checkName();
		l2.checkStatus();
		l2.checkColour();
		
		
	}
	
	//Attributes
	String colour = null;
	int hight = 0;
	String name = null;
	boolean status = true;
	
	//Constructor
	
	public Light(){
		
	}
	
	public Light(String colour, String name){
		this.colour = colour;
		this.name = name;
	}
	
	public Light(int hight, String name){
		this.hight = hight;
		this.name = name;
	}
	
	//Methods
	public void turnOn(){
		this.status = true;
	}
	
	public void turnOff(){
		this.status = false;
	}
	
	public void checkName(){
		System.out.println("My name is "+this.name);
	}
	
	public void checkColour(){
		System.out.println("I am "+this.colour);
	}
	
	public void checkStatus(){
		if(this.status == true){
			System.out.println("I am on. ");
		}
		else{
			System.out.println("I am off. ");
		}
	}
	
	
	
}