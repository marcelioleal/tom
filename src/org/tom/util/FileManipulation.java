package org.tom.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.sun.xml.internal.stream.writers.UTF8OutputStreamWriter;

public class FileManipulation {

	public void writeFile(File file, String text) {
		try {
			file.delete();
			FileWriter writer = new FileWriter(file,true);
			
			//BufferedWriter bufWriter = new BufferedWriter(writer);
			writer.write(text);
			//bufWriter.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public String fileToString (File file){
    	StringBuffer text = new StringBuffer();
    	try
    	{
			BufferedReader reader = new BufferedReader( new FileReader(file));
		 
		    String line;
			while ((line = reader.readLine()) != null)			
				text.append(line).append("\n");
			reader.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return text.toString();
    }
    
	/**
	 * 
	 * @param option
	 * @param path
	 * @return
	 */
	public String getFilePath(int option, String path) {
		JFileChooser fileChooser = new JFileChooser();

		if(option == 0) fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		else fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	
		int result = fileChooser.showOpenDialog(null);
		
		// se o usuario clicar em cancelar retorna a home do usu�rio
		if (result == JFileChooser.CANCEL_OPTION ){
			if ( path == null ) return null;
			else return path;
		}

		String fileName = fileChooser.getSelectedFile().getAbsolutePath();
		if ((fileName == null) || (fileName.equals(""))) {
			JOptionPane.showMessageDialog(null, "Invalid File","Invalid File", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		return fileName;
	}
	
	public static String clearRtfFormat(String x){
		Pattern patI 		= Pattern.compile("[}]*[{]*\\\\([^'].*?)[}]*\\s");
		Pattern patII 		= Pattern.compile("[{]\\\\.\\\\.*?[}]");
		Pattern patIII 		= Pattern.compile("[}\\n]");
		Pattern patIV 		= Pattern.compile("\\\\\\~");
		Pattern patV 		= Pattern.compile("[{]\\\\rtlch");
		Matcher matcher = patII.matcher(x);
		matcher = patI.matcher(matcher.replaceAll(""));
		matcher = patIII.matcher(matcher.replaceAll(""));
		matcher = patIV.matcher(matcher.replaceAll(""));
		matcher = patV.matcher(matcher.replaceAll(""));
		return convertASCII(matcher.replaceAll("")).trim();
	}
	
	public static String convertASCII(String x){ 
		Pattern patASCII = Pattern.compile("\\\\\\'(..)");
		Matcher matcher = patASCII.matcher(x);
		while (matcher.find()) {	
			int i = Integer.parseInt(matcher.group(1), 16);  
			String aChar = new Character((char)i).toString();
			x = x.replaceAll("\\"+matcher.group(),aChar);
		}
		return x;
	}
	
	public static String cleanName(String x, int type){
		switch(type){
			case 1:
				return cleanNeedName(x);
			case 2:
				return cleanFeatureName(x);
			case 3:
				return cleanActorName(x);
			case 4:
				return cleanUCName(x);
			case 5:
				return cleanBRName(x);
			default:
				return x;
		}
		
	}
	
	public static String cleanPoints(String x){
		Pattern patI 		= Pattern.compile("[\\.]*[\\\"]*");
		Matcher matcher = patI.matcher(x);
		return matcher.replaceAll("").trim();
	}
	
	public static String cleanUCName(String x){
		Pattern patI 		= Pattern.compile("CDU[\\d]*");
		Matcher matcher = patI.matcher(x);
		return FileManipulation.cleanPoints(matcher.replaceAll("").trim());
	}
	
	public static String cleanActorName(String x){
		Pattern patI 		= Pattern.compile("ATO[\\d]*");
		Matcher matcher = patI.matcher(x);
		return FileManipulation.cleanPoints(matcher.replaceAll("").trim());
	}
	
	public static String cleanFeatureName(String x){
		Pattern patI 		= Pattern.compile("FUN[\\d]*");
		Matcher matcher = patI.matcher(x);
		return FileManipulation.cleanPoints(matcher.replaceAll("").trim());
	}
	
	public static String cleanNeedName(String x){
		Pattern patI 		= Pattern.compile("NEC[\\d]*");
		Matcher matcher = patI.matcher(x);
		return FileManipulation.cleanPoints(matcher.replaceAll("").trim());
	}
	
	public static String cleanBRName(String x){
		Pattern patI 		= Pattern.compile("RNG[\\d]*");
		Matcher matcher = patI.matcher(x);
		return FileManipulation.cleanPoints(matcher.replaceAll("").trim());
	}
	
	public File convertFile(File file, OpenOfficeConnection c){
		String newFileName = file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4);
		if(file.getAbsolutePath().endsWith(".doc"))
			return this.convert(file,newFileName,".rtf", c);
		else
			return new File("error");
	}
	
	public File convert(File fileDoc, String newFileName,String extension, OpenOfficeConnection c){
		File inputFile = fileDoc;
		File outputFile = new File(newFileName+extension);
		outputFile.setWritable(true);
		try{
			DocumentConverter converter = new OpenOfficeDocumentConverter(c);
			converter.convert(inputFile, outputFile);
        }catch (Exception e) {
			e.printStackTrace();
		}
		return outputFile;
	}
}
