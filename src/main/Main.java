package main;

public class Main {
	
	public static void main(String args[]){
		
		TimingAttack ta = new TimingAttack("Kalle", "5");
		
		for(int i=0; i<20; i++){
			System.out.println("gissad symbol: "+ta.guessSymbol());
		}
		
		System.out.println("Final URL (signature in the end): " + ta.getURL());

	}
}
