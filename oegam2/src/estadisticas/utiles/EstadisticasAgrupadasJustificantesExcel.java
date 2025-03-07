package estadisticas.utiles;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import colas.servlet.ColasServletContextListener;
import estadisticas.utiles.enumerados.ConvierteCodigoALiteral;
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

public class EstadisticasAgrupadasJustificantesExcel {

	public static final String ESTADISTICASJUSTIFICANTES = "EstadisticasJustificantes";
	private static final String MS_SANS_SERIF = "MS Sans Serif";
	private static final ILoggerOegam log = LoggerOegam.getLogger(EstadisticasAgrupadasJustificantesExcel.class);

	@Autowired
	UtilesFecha utilesFecha;

	public EstadisticasAgrupadasJustificantesExcel() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public WritableWorkbook createAgrupacionExcel(List<HashMap> listaAgrupacion, String ruta) throws Exception {
		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
		final BigDecimal TRAMITE_TRAFICO = BigDecimal.TEN;
		final BigDecimal ESTADO = BigDecimal.valueOf(11.0);
		ColasServletContextListener colas = new ColasServletContextListener();
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(new File(ruta));
			sheet = copyWorkbook.createSheet(ESTADISTICASJUSTIFICANTES, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 8; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont
					.createFont(MS_SANS_SERIF), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 8; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont
					.createFont(MS_SANS_SERIF), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(
					fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont
					.createFont(MS_SANS_SERIF), 10, WritableFont.NO_BOLD,
					false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(
					fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;
			try {
				label = new Label(0, 0, "Número de Colegiado", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Matricula", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Numero de justificante",formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Número de expediente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Tipo de Trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Estado del Trámite", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Fecha Inicio", formatoCabecera);
				sheet.addCell(label);
				label = new Label(7, 0, "Fecha Fin", formatoCabecera);
				sheet.addCell(label);
				label = new Label(8, 0, "Días Validez", formatoCabecera);
				sheet.addCell(label);

				int contador=0;
				while (contador < listaAgrupacion.size()) {
					Iterator it = listaAgrupacion.iterator();
					while (it.hasNext()){
						Object[] objecto = (Object[])it.next();

						if (objecto[0] != null && !objecto[0].equals("")) {
							label = new Label(0,contador+1, (String)objecto[0], formatoDatos);
						} else {
							label = new Label(0,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[1] != null && !objecto[1].equals("")) {
							label = new Label(1,contador+1, (String)objecto[1], formatoDatos);
						} else {
							label = new Label(1,contador+1, "-", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[2] != null && !objecto[2].equals("")) {
							label = new Label(2,contador+1, (String)objecto[2].toString(), formatoDatos);
						} else {
							label = new Label(2,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[3] != null && !objecto[3].equals("")) {
							label = new Label(3,contador+1,((Long)objecto[3]).toString(), formatoDatos);
						} else {
							label = new Label(3,contador+1,"", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[4]!=null && !objecto[4].equals("")){
							label = new Label(4,contador+1,convierteCodigoALiteral.getLiteral((String)objecto[4], TRAMITE_TRAFICO), formatoDatos);
						} else {
							label = new Label(4,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[5]!=null && !objecto[5].equals("")){
							label = new Label(5,contador+1, convierteCodigoALiteral.getLiteral(((BigDecimal)objecto[5]).toString(), ESTADO), formatoDatos);
						} else {
							label = new Label(5,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[6] != null && !objecto[6].equals("")) {
							label = new Label(6,contador+1, utilesFecha.formatoFecha((Date)objecto[6]), formatoDatos);
						} else {
							label = new Label(6,contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[7] != null && !objecto[7].equals("")) {
							label = new Label(7, contador+1, utilesFecha.formatoFecha((Date)objecto[7]), formatoDatos);
						} else {
							label = new Label(7, contador+1, "", formatoDatos);
						}
						sheet.addCell(label);

						if (objecto[8] != null && !objecto[8].equals("")) {
							label = new Label(8, contador + 1, ((BigDecimal) objecto[8]).toString(), formatoDatos);
						} else {
							label = new Label(8, contador + 1, "", formatoDatos);
						}
						sheet.addCell(label);

						contador ++;
					}
				}
			} catch (RowsExceededException e) {
				log.error("Error al obtener las estadisticas agrupadas justificantes para excel.", e);
			} catch (WriteException e) {
				log.error("Error al obtener las estadisticas agrupadas justificantes para excel.", e);
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