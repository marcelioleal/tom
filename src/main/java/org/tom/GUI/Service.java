package org.tom.GUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;

//Nao est‡ funcionando.

public class Service {

	public Service(){
	}
	
	public boolean testService(){
		try{
			SocketOpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
			connection.connect();
			connection.disconnect();
			return true;
		}catch (ConnectException e2) {
			System.out.println("No started OpenOffice Conection.");
			return false;
		}
	}
	
	public void startService() throws IOException{
		// On Linux $OOFFICE "-accept=socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -norestore -nofirststartwizard -nologo -headless  &
		// On Mac soffice -headless -accept='socket,host=127.0.0.1,port=8100;urp;' -nofirststartwizard
		//String ExecutedCmd = "soffice -headless -accept='socket,host=127.0.0.1,port=8100;urp;' -nofirststartwizard &";
		String ExecutedCmd = "soffice -accept=\"socket,host=localhost,port=8100;urp;StarOffice.ServiceManager\" -norestore -nofirststartwizard -nologo -headless";
		System.out.println(ExecutedCmd);
		Process p = Runtime.getRuntime().exec(ExecutedCmd);
		
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
		
		
		BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		System.out.println("Starting OpenOffice Service.");
		
		int i = 0;
		String s;
		
		
		i = 0;
		while (((s = stdError.readLine()) != null) && i < 4) 
		{ 
			System.out.println("Error Command Service:\n"); System.out.println(s);
			new IOException(s);
			i++;
		}
	}
	
	public void stopService(){
		
	}
	
}
