package org.gestoresmadrid.oegam2comun.datosSensibles.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoAltaBastidorBean;
import org.gestoresmadrid.oegam2comun.datosSensibles.views.beans.ResultadoImportacionSantanderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class BuildExcelSantanderFTP implements Serializable{

	private static final long serialVersionUID = 2055079740293879825L;
	
	private ILoggerOegam log = LoggerOegam.getLogger(BuildExcelSantanderFTP.class);
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	public void generarExcel(ResultadoImportacionSantanderBean resultado, Fecha fecha, String nombreFichero, Boolean esRetail) {
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
			fichero.setNombreDocumento(nombreFichero);
			fichero.setFichero(new File(nombreFichero));
			fichero.setTipoDocumento(ConstantesGestorFicheros.TIPO_DOC_IMPORTACION_DATOS_SENSIBLES);
			fichero.setSubTipo("INFORMES");
			fichero.setFecha(fecha);
			fichero.setSobreescribir(true);
			File archivo = gestorDocumentos.guardarFichero(fichero);
			generarFicheroExcel(archivo, resultado, esRetail);
			
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setNombreYExtension(nombreFichero + ConstantesGestorFicheros.EXTENSION_XLS);
			ficheroBean.setFicheroByte(gestorDocumentos.transformFiletoByte(archivo));
			if(esRetail){
				resultado.setFicheroExcelRetail(ficheroBean);
			} else {
				resultado.setFicheroExcelCart(ficheroBean);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el fichero " + (esRetail ? "de Carterizados" : "de Retail") + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el fichero " + (esRetail ? "de Carterizados" : "de Retail"));
		}
	}

	private void generarFicheroExcel(File archivo, ResultadoImportacionSantanderBean resultado, Boolean esRetail) {
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Hoja1", 0);
			for (int i = 0; i < 7; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}
			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 4; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}
			// Generamos las cabeceras de la hoja Excel con el formato indicado
			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);
			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.CENTRE);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);
			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.CENTRE);
			
			// Formato de las celdas de datos			
			WritableCellFormat formatoDatosIzquierda = new WritableCellFormat(fuenteDatos);

			formatoDatosIzquierda.setAlignment(Alignment.LEFT);

			Label label = null;

			try {
				label = new Label(0, 0, "Fichero", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Bastidor", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Operación", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Resultado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Comentario", formatoCabecera);
				sheet.addCell(label);
				int i = 1;
				if(!esRetail){
					if(resultado.getListaResultadoAltaBastCart() != null && !resultado.getListaResultadoAltaBastCart().isEmpty()){
						generarCelda(resultado.getNombreFichAltaCart(),"Alta",resultado.getListaResultadoAltaBastCart(),sheet, i, formatoDatos, formatoDatosIzquierda);
					}
					if(resultado.getListaResultadoBajaBastCart() != null && !resultado.getListaResultadoBajaBastCart().isEmpty()){
						generarCelda(resultado.getNombreFichAltaCart(),"Baja",resultado.getListaResultadoBajaBastCart(),sheet, i, formatoDatos, formatoDatosIzquierda);
					}
				} else {
					if(resultado.getListaResultadoAltaBastRetail() != null && !resultado.getListaResultadoAltaBastRetail().isEmpty()){
						generarCelda(resultado.getNombreFichAltaCart(),"Alta",resultado.getListaResultadoAltaBastRetail(),sheet, i, formatoDatos, formatoDatosIzquierda);
					}
					if(resultado.getListaResultadoBajaBastRetail() != null && !resultado.getListaResultadoBajaBastRetail().isEmpty()){
						generarCelda(resultado.getNombreFichAltaCart(),"Baja",resultado.getListaResultadoBajaBastRetail(),sheet, i, formatoDatos, formatoDatosIzquierda);
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			} catch (WriteException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			}
			copyWorkbook.write();
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de generar el fichero excel, error: " ,e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el fichero excel.");
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el fichero excel, error: " ,e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el fichero excel.");
		}
		try {
			copyWorkbook.close();
		} catch (WriteException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		
	}

	private void generarCelda(String nombreFichero, String operacion,	List<ResultadoAltaBastidorBean> listaResultado, WritableSheet sheet, int numFila, WritableCellFormat formatoDatos, WritableCellFormat formatoDatosIzquierda) throws RowsExceededException, WriteException {
		Label label = null;
		for(ResultadoAltaBastidorBean resultadoBastidor : listaResultado){
			label = new Label(0, numFila, nombreFichero, formatoDatosIzquierda);
			sheet.addCell(label);
		
			label = new Label(1, numFila, resultadoBastidor.getBastidor(), formatoDatos);
			sheet.addCell(label);

			label = new Label(2, numFila, operacion, formatoDatos);
			sheet.addCell(label);
			
			label = new Label(3, numFila, resultadoBastidor.getErrorFormateado(), formatoDatos);
			sheet.addCell(label);
					
			label = new Label(4, numFila, resultadoBastidor.getComentario(), formatoDatosIzquierda);
			sheet.addCell(label);
			numFila++;
		}
	}
}
