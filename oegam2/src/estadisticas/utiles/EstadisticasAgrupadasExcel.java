package estadisticas.utiles;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import colas.servlet.ColasServletContextListener;
import estadisticas.acciones.EstadisticasAction;
import estadisticas.utiles.enumerados.ConvierteCodigoALiteral;

public class EstadisticasAgrupadasExcel {

	public static final String ESTADISTICASTRAMITES = "EstadisticasTramites";
	private static final ILoggerOegam log = LoggerOegam.getLogger(EstadisticasAgrupadasExcel.class);

	public WritableWorkbook createAgrupacionExcel(List<HashMap> listaAgrupacion, String ruta) throws Exception {

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
		final BigDecimal CODIGO_CARBURANTE = BigDecimal.valueOf(5.0);
		final BigDecimal TRAMITE_TRAFICO = BigDecimal.valueOf(10.0);
		final BigDecimal ESTADO = BigDecimal.valueOf(11.0);
		ColasServletContextListener colas = new ColasServletContextListener();
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(new File(ruta));
			sheet = copyWorkbook.createSheet(ESTADISTICASTRAMITES, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 11; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 11; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(
					fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD,
					false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(
					fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;
			try {
				label = new Label(0, 0, "Número de expediente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Numero de colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Provincia",formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Jefatura de tráfico", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Tipo de carburante", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Exento", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Servicio", formatoCabecera);
				sheet.addCell(label);
				label = new Label(7, 0, "Tipo de trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(8, 0, "Estado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(9, 0, "Marca del vehículo", formatoCabecera);
				sheet.addCell(label);
				label = new Label(10, 0, "Tipo de vehículo", formatoCabecera);
				sheet.addCell(label);
				label = new Label(11, 0, "Tipo de Creacion", formatoCabecera);
				sheet.addCell(label);

				int contador = 0;
				while (contador < listaAgrupacion.size()){
					Iterator it = listaAgrupacion.iterator();
					while (it.hasNext()){
						Object[] objecto = (Object[])it.next();

						if (objecto[0]!=null){
							label = new Label(0,contador+1,((Long)objecto[0]).toString(), formatoDatos);
						} else {
							label = new Label(0,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[1]!=null && !objecto[1].equals("")){
							label = new Label(1,contador+1,(String)objecto[1], formatoDatos);
						} else {
							label = new Label(1,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[2]!=null && !objecto[2].equals("")){
							label = new Label(2,contador+1, (String)objecto[2], formatoDatos);
						} else {
							label = new Label(2,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[3]!=null && !objecto[3].equals("")){
							label = new Label(3,contador+1, (String)objecto[3], formatoDatos);
						} else {
							label = new Label(3,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[4]!=null && !objecto[4].equals("")){
							label = new Label(4,contador+1, convierteCodigoALiteral.getLiteral((String)objecto[4], CODIGO_CARBURANTE), formatoDatos);
						} else {
							label = new Label(4,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[5]!=null && !objecto[5].equals("")){
							label = new Label(5,contador+1, (String)objecto[5], formatoDatos);
						} else {
							label = new Label(5,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[6]!=null && !objecto[6].equals("")){
							label = new Label(6,contador+1, (String)objecto[6], formatoDatos);
						} else {
							label = new Label(6,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[7]!=null && !objecto[7].equals("")){
							label = new Label(7,contador+1,convierteCodigoALiteral.getLiteral((String)objecto[7],TRAMITE_TRAFICO), formatoDatos);
						} else {
							label = new Label(7,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[8]!=null){
							label = new Label(8,contador+1,convierteCodigoALiteral.getLiteral(((BigDecimal)objecto[8]).toString(),ESTADO), formatoDatos);
						} else {
							label = new Label(8,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[9]!=null && !objecto[9].equals("")){
							label = new Label(9,contador+1,(String)objecto[9], formatoDatos);
						} else {
							label = new Label(9,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[10]!=null){
							label = new Label(10, contador+1, (String)objecto[10], formatoDatos);
						} else {
							label = new Label(10, contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[11] != null) {
							label = new Label(11, contador+1, (String)objecto[11], formatoDatos);
						} else {
							label = new Label(11, contador+1,"", formatoDatos);
						}
						sheet.addCell(label);
						contador ++;
					}
				}

			} catch (RowsExceededException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			} catch (WriteException e) {
				log.error("Error al obtener las estadisticas agrupadas para excel.", e);
			}

			copyWorkbook.write();

		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		}

		copyWorkbook.close();
		return copyWorkbook;
	}

	public WritableWorkbook createAgrupacionVehiculosExcel(List<HashMap> listaAgrupacion, String ruta) throws Exception {
		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
		ColasServletContextListener colas = new ColasServletContextListener();
		try {

			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(new File(ruta));
			sheet = copyWorkbook.createSheet(ESTADISTICASTRAMITES, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 11; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 11; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(
					fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont
					.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD,
					false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(
					fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;
			try {
				label = new Label(0, 0, "Numero Colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Tipo Tramite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Estado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Matricula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Bastidor", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Fecha Primera Matricula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Fecha Presentacion", formatoCabecera);
				sheet.addCell(label);

				int contador  = 0;
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

				for (contador = 0; contador < listaAgrupacion.size(); contador++) {
					Iterator it = listaAgrupacion.iterator();
					while (it.hasNext()) {
						Object[] objecto = (Object[])it.next();
						if (objecto[0] != null) {
							label = new Label(0, contador+1, (String) objecto[0], formatoDatos);
						} else {
							label = new Label(0, contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[1] != null) {
							label = new Label(1, contador+1, new EstadisticasAction().getConvertirResultados((String) objecto[1], "5") , formatoDatos);
						} else {
							label = new Label(1, contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[2] != null) {
							label = new Label(2, contador+1, new EstadisticasAction().getConvertirResultados((String) objecto[2].toString(), "4"), formatoDatos);
						} else {
							label = new Label(2, contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[3] !=null){
							label = new Label(3,contador+1, (String) objecto[3], formatoDatos);
						} else {
							label = new Label(3,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[4] != null) {
							label = new Label(4, contador+1, (String) objecto[4], formatoDatos);
						} else {
							label = new Label(4, contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[5] != null) {
							label = new Label(5, contador+1, format.format(objecto[5]), formatoDatos);
						} else {
							label = new Label(5,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[6] != null) {
							label = new Label(6, contador+1, format.format(objecto[6]), formatoDatos);
						} else {
							label = new Label(6,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);
						contador ++;
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error: ", e);
			} catch (WriteException e) {
				log.error("Error: ", e);
			}

			copyWorkbook.write();
		} catch (IOException e) {
			throw new Exception(e);
		} catch (Exception e) {
			throw new Exception(e);
		}

		copyWorkbook.close();
		return copyWorkbook;
	}
}