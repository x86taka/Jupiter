package cn.nukkit.plugin;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import cn.nukkit.Server;

public class PluginCompiler{
	
	private InputStream in = null;   	
  	
  	private InputStream ein = null;
  	
  	private OutputStream out = null;
  	
  	private BufferedReader br = null;
  	
  	private BufferedReader ebr = null;
	
  	private Process process = null;
	
  	private String line = null;
	
  	private String errLine = null;
	
  	private Thread stdRun  = null;
    
  	private Thread errRun  = null;
  	
  	private Server server = Server.getInstance();
    

  public PluginCompiler() {	
  }	
	
  private boolean execCmd(File file) throws IOException, InterruptedException{
		
	String[] cmd = {"javac","-classpath", server.getJupiterConfigString("jupiter-name"), file.toPath()};
	
	process = Runtime.getRuntime().exec(cmd);
	in = process.getInputStream(); 
	ein = process.getErrorStream();
	out = process.getOutputStream();
			
	try {
	Runnable inputStreamThread = new Runnable(){
		
	  public void run(){	
		  
	  	try {
		  	br = new BufferedReader(new InputStreamReader(in));
		  	while ((line = br.readLine()) != null) {
		  	  System.out.println(line);
		  	}
	  	} catch (Exception e) {		
	  		e.printStackTrace();
	 	}
	  	
	  }
	  
	};

	Runnable errStreamThread = new Runnable(){
		
	  public void run(){	
		  
	  	try {
		  	ebr = new BufferedReader(new InputStreamReader(ein));
		  	while ((errLine = ebr.readLine()) != null) {
		  	  System.err.println(errLine);
		  	}
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  	}    
	  	
	  }
	  
	};
		
	stdRun = new Thread(inputStreamThread);
	errRun = new Thread(errStreamThread);
	
	stdRun.start();        
	errRun.start();

	process.waitFor();

	stdRun.join();
	errRun.join();
		
	} catch (Exception e) {		
	  	e.printStackTrace();	
	  	return false;
	}    
	
	finally{
	  	if(br!=null)br.close();
	  	if(ebr!=null)ebr.close();
		
	  	if(in!=null)in.close();
	  	if(ein!=null)ein.close();
	  	if(out!=null)out.close();		
	}
	
	return true;
	
  }
  	public boolean Compile(File file){
  		try {
			if(execCmd(file))return true;
			else return false;
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
  		
  	}
}
