package es.globaltms.gestorDocumentos.test;

import java.io.IOException;

public class Prueba {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		//Datos Fichero
//		GuardarDocumentosInterfaz guardarDocumento = new GuardarDocumentoImpl();
//
//		FicheroBean fichero = new FicheroBean();
//		fichero.setExtension(".xml");
//		fichero.setFichero(new File("C:\\Users\\julio.molina\\Desktop\\juanma\\Entidades Financieras\\XMLMatr222.xml"));
//			
//		fichero.setNombreDocumento("eeffLiberacion_"+"205612101300001");
//		fichero.setTipoDocumento(ConstantesGestorFicheros.EEFF);
//		//fichero.setSubTipo("liberacion");
//		fichero.setNumExpediente("205612101300001");
//		fichero.setSobreescribir(true);
//		
//		
//		FicheroBean fichero2 = new FicheroBean();
//		//Datos Fichero
//		
//
//		try {
//		
//		guardarDocumento.guardarFichero(fichero);
//		guardarDocumento.guardarByte(fichero2);
//		
//		
//		FicheroBean zip = new FicheroBean();
//		fichero.setExtension(".xml");
//		fichero.setFichero(new File("C:\\Users\\julio.molina\\Desktop\\juanma\\Entidades Financieras\\XMLMatr222.xml"));
//		
//		List<File> lista = new ArrayList<File>();
//		lista.add(new File("C:\\Users\\julio.molina\\Desktop\\juanma\\Entidades Financieras\\XMLMatr222.xml"));
//		lista.add(new File("C:\\Users\\julio.molina\\Desktop\\juanma\\Entidades Financieras\\XMLMatr.xml"));
//		lista.add(new File("C:\\Users\\julio.molina\\Desktop\\juanma\\Entidades Financieras\\XMLMatr555.xml"));
//		
//		zip.setListaFicheros(lista);
//		zip.setNombreZip("pruebaZip");
//		
//		zip.setNombreDocumento("eeffLiberacion_"+"205612101300001");
//		zip.setTipoDocumento("TARTADEQUESO");
//	//	zip.setSubTipo(ConstantesGestorFicheros.EEFFLIBERACION);
//		zip.setNumExpediente("205612101300001");
//		
//		try {
//			guardarDocumento.empaquetarEnZipFile(zip);
//		} catch (OegamExcepcion e1) {
//			e1.printStackTrace();
//		}
//		
//		
//		
//			List<File> list =guardarDocumento.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.EEFF,null,new BigDecimal("205612101300001"));
//			System.out.println("====================================================================");
//			for(File fil : list){
//				System.out.println(fil.getName());
//			}
//			list =guardarDocumento.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.EEFF,ConstantesGestorFicheros.EEFFLIBERACION,new BigDecimal("205612101300001"));
//			System.out.println("=============================222222==================================");
//			for(File fil : list){
//				System.out.println(fil.getName());
//			}
//		} catch (OegamExcepcion e) {
//			e.printStackTrace();
//		}
		
	}

}
