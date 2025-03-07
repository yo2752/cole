package exportarXML;

import general.utiles.enumerados.EstadoReconocimientoMedico;
import hibernate.entities.general.ReconocimientoMedico;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class ReconocimientosMedicosExcel {

	private static final String RESUMEN_RECONOCIMIENTOS = "ResumenReconocimientosMedicos";
	private static final int NUM_COLUMNAS = 10;
	private static final String FORMATO_TIMESTAMP = "dd/MM/yyyy HH:mm";
	private static final String FORMATO_FECHA = "dd/MM/yyyy";

	private static final ILoggerOegam log = LoggerOegam.getLogger(ReconocimientosMedicosExcel.class);

	public static WritableWorkbook createResumenExcel(List<ReconocimientoMedico> listaReconocimientos, String ruta) throws Exception {

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;
		try {
			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(new File(ruta));
			sheet = copyWorkbook.createSheet(RESUMEN_RECONOCIMIENTOS, 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < NUM_COLUMNAS; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < NUM_COLUMNAS; i++) {
				vistaCeldas.setAutosize(true);
				sheet.setColumnView(i, vistaCeldas);
			}

			// Generamos las cabeceras de la hoja Excel con el formato indicado

			// Formato de las celdas de cabecera
			WritableFont fuenteCabecera = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuenteCabecera.setColour(Colour.BLACK);
			WritableCellFormat formatoCabecera = new WritableCellFormat(fuenteCabecera);

			formatoCabecera.setBackground(Colour.PALE_BLUE);
			formatoCabecera.setAlignment(Alignment.LEFT);
			formatoCabecera.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Formato de las celdas de datos
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD,false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			SimpleDateFormat sdfTimestamp = new SimpleDateFormat(FORMATO_TIMESTAMP);
			SimpleDateFormat sdfFecha = new SimpleDateFormat(FORMATO_FECHA);
			Label label = null;
			try {
				label = new Label(0, 0, "NIF cliente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Nombre cliente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "Apellido cliente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Tipo renovación", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Caducidad carnet conducir", formatoCabecera);
				sheet.addCell(label);
				label = new Label(5, 0, "Teléfono cliente", formatoCabecera);
				sheet.addCell(label);
				label = new Label(6, 0, "Cif Clinica", formatoCabecera);
				sheet.addCell(label);
				label = new Label(7, 0, "Clínica", formatoCabecera);
				sheet.addCell(label);
				label = new Label(8, 0, "Fecha de alta",formatoCabecera);
				sheet.addCell(label);
				label = new Label(9, 0, "Fecha de la cita", formatoCabecera);
				sheet.addCell(label);
				label = new Label(10, 0, "Fecha real de la visita", formatoCabecera);
				sheet.addCell(label);
				label = new Label(11, 0, "Estado", formatoCabecera);
				sheet.addCell(label);

				for (int i=0; i < listaReconocimientos.size(); i++){
					ReconocimientoMedico recMedico = listaReconocimientos.get(i);

					label = new Label(0, i+1, recMedico.getPersona().getId().getNif(), formatoDatos);
					sheet.addCell(label);
					label = new Label(1, i+1, recMedico.getPersona().getNombre(), formatoDatos);
					sheet.addCell(label);
					label = new Label(2, i+1, recMedico.getPersona().getApellido1RazonSocial(), formatoDatos);
					sheet.addCell(label);
					label = new Label(3, i+1, recMedico.getTipoTramiteRenovacion()!=null?recMedico.getTipoTramiteRenovacion().getDescripcion():"", formatoDatos);
					sheet.addCell(label);
					label = new Label(4, i+1, recMedico.getPersona().getFechaCaducidadCarnet() != null ? sdfFecha.format(recMedico.getPersona().getFechaCaducidadCarnet()):"", formatoDatos);
					sheet.addCell(label);
					label = new Label(5, i+1, recMedico.getPersona().getTelefonos(), formatoDatos);
					sheet.addCell(label);
					label = new Label(6, i+1, recMedico.getClinica().getCif(), formatoDatos);
					sheet.addCell(label);
					label = new Label(7, i+1, recMedico.getClinica().getRazonSocial(), formatoDatos);
					sheet.addCell(label);
					label = new Label(8, i+1, recMedico.getFechaAlta()!=null?sdfFecha.format(recMedico.getFechaAlta()):"", formatoDatos);
					sheet.addCell(label);
					label = new Label(9, i+1, recMedico.getFechaReconocimiento()!=null?sdfTimestamp.format(recMedico.getFechaReconocimiento()):"", formatoDatos);
					sheet.addCell(label);
					label = new Label(10, i+1, recMedico.getFechaRealVisita()!=null?sdfTimestamp.format(recMedico.getFechaRealVisita()):"", formatoDatos);
					sheet.addCell(label);
					label = new Label(11, i+1, recMedico.getEstado()!=null?EstadoReconocimientoMedico.convertirTexto(recMedico.getEstado().toString()):"", formatoDatos);
					sheet.addCell(label);
				}
			} catch (RowsExceededException e) {
				log.error("Se ha producido un error al crear el excel de reconocimiento medico. ", e);
			} catch (WriteException e) {
				log.error("Se ha producido un error al crear el excel de reconocimiento medico. ", e);
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