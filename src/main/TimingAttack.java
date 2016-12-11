package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TimingAttack {
	HttpURLConnection connection = null;
	private String name, grade;
	private String signature;
	private String targetURL = "https://eitn41.eit.lth.se:3119/ha4/addgrade.php?name=Kalle&grade=5&signature=";
	public TimingAttack(String name, String grade){
		this.name = name;
		this.grade=grade;
		signature="6823000000000000ba36";
		targetURL = targetURL + name + "&grade=" + grade + "&signature=" + signature;
	}
	
	public String getSignature(){
		return signature;
	}
	
	public String response(){
		try {
		    //Create connection
		    URL url = new URL(targetURL);
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
		    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  }
	}
	
}
