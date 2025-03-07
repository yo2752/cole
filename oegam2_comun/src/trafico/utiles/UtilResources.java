package trafico.utiles;

import java.io.File;
import java.net.URL;

public class UtilResources {
	
	
	public String getFilePath(String src) {
		URL ruta = this.getClass().getResource(src);
		String MY_FILE = ruta.getFile();
		return MY_FILE;
	}
	
	public File getFileFromSrc( String src){

		if(null==src||src.equals("")){
			return null;
		}
		
		String MY_FILE = getFilePath(src);
		File fichero = new File(MY_FILE);
		
		return fichero;
	}	
	
}
