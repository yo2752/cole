package escrituras.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

	public class ficheroTest {

	    public static void main(String[]args) throws IOException{

	        byte[]datosEntrada=null;
	        File fichero = new File("C://Users/rocio.martin/Documents/prueba.txt");
	        File file = new File("C://Users/rocio.martin/Documents/prueba2.txt");
	        //File fichero=new File("C://xml_mensajes_CORPME//Corpme_eDoc_APR_conPDF.xml");
	        FileInputStream fileInputStream = new FileInputStream(fichero);
	        FileOutputStream fileOutputStream = new FileOutputStream(file);
	        int x = 0;
	        
	        while((x = fileInputStream.read())!=-1){
	            fileOutputStream.write(x);
	        }
	        
//	        fileOutputStream.flush();
	        fileOutputStream.close();
	        fileInputStream.close();

	        
//	        LeeFicheros leeFicheros=new LeeFicheros();
//	        String lectura= leeFicheros.leer(fichero);
//	        datosEntrada=lectura.getBytes();
//	        GestionComunicacion gestionComunicacion=new GestionComunicacion();
//	        byte[] respuesta=gestionComunicacion.wsOegam(datosEntrada);
	    }

}
