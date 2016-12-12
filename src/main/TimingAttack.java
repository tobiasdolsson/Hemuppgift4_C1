package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;

public class TimingAttack {
	HttpURLConnection connection = null;
	private String name, grade;
	private String signature;
	private long totalTime = 0;
	private String targetURL = "https://eitn41.eit.lth.se:3119/ha4/addgrade.php?name=Kalle&grade=5&signature=";
	private String startURL = "https://eitn41.eit.lth.se:3119/ha4/addgrade.php?name=Kalle&grade=5&signature=";
	public TimingAttack(String name, String grade){
		this.name = name;
		this.grade=grade;
		//signature="6823ea50b133c58cba36"
		//signature="6823000000000000ba36";
		//signature="";
		//targetURL = targetURL + name + "&grade=" + grade + "&signature=" + signature;
	}
	
	public String getURL(){
		return targetURL;
	}
	
	public void setTargetURL(String URL){
		targetURL = URL;
	}
	
	public void setStartURL(String URL){
		startURL = URL;
	}
	
	public String getSignature(){
		return signature;
	}
	
	public String guessSymbol(){
		BigInteger hex = new BigInteger("-1",16);
		String guessedSymbol="";
		float meanTime;
		float currMeanTime=0;
		for(int i=0; i<17; i++){
			meanTime = responseTime(hex.toString(16));
			setTargetURL(targetURL);
			if(meanTime>currMeanTime && i!=0){
				currMeanTime=meanTime;
				guessedSymbol=hex.toString(16);
				System.out.println(guessedSymbol);
			}
			hex=hex.add(BigInteger.ONE);
		}
		setTargetURL(targetURL+guessedSymbol);
		setStartURL(targetURL+guessedSymbol);
		return guessedSymbol;
	}
	
	public long responseTime(String signature){
		startURL = targetURL +signature;
		System.out.println(startURL);
		long startTime = System.nanoTime();
		totalTime=0;
		try {
			
		    //Create connection
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			for(int i = 0; i<5; i++){
				
			
			URL url = new URL(startURL);
			
		    connection = (HttpURLConnection) url.openConnection();
		    

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		  //  wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    
		    //System.out.println("Time: "+ (totalTime));
		}
			long endTime = System.nanoTime();
		    totalTime = (endTime-startTime);
			//System.out.println(startURL);
		    System.out.println("Mean time " +signature+" :"+ (totalTime/5));
		    return totalTime/5;
			
		  } catch (Exception e) {
		    e.printStackTrace();
		    return 0;
		  }
		
	}
	
}
