/**
 * 
 */
package org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gestoresmadrid.core.mensajeErrorServicio.model.dao.MensajeErrorServicioDao;
import org.gestoresmadrid.core.mensajeErrorServicio.model.vo.MensajeErrorServicioVO;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.model.service.ServicioMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.view.bean.ResultadoMensajeErrorServicio;
import org.gestoresmadrid.oegam2comun.mensajeErrorServicio.views.dto.MensajeErrorServicioDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
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
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

/**
 * @author ext_fjcl
 */
@Service
public class ServicioMensajeErrorServicioImpl implements ServicioMensajeErrorServicio {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioMensajeErrorServicioImpl.class);

	private static final String NOMBRE_FICHERO = "ErrorServicio";
	@Autowired
	MensajeErrorServicioDao mensajeErrorServicioDao;

	@Autowired
	Conversor conversor;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public List<MensajeErrorServicioDto> getListaMensajeErrorServicio(Date fecha) {
		log.info("ServicioMensajeErrorServicioImpl getListaMensajeErrorServicio");
		List<MensajeErrorServicioVO> listaMensajes = mensajeErrorServicioDao.getListaMensajeErrorServicio(fecha);
		if (listaMensajes != null) {
			log.info("ServicioMensajeErrorServicioImpl getListaMensajeErrorServicio existen mensajes");
			return conversor.transform(listaMensajes, MensajeErrorServicioDto.class);
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoMensajeErrorServicio obtenerExcel(Fecha fecha) {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		String nombreFichero = NOMBRE_FICHERO + "_" + fecha.getAnio() + fecha.getMes() + fecha.getDia();
		resultado.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_XLSX);
		try {
			FileResultBean result = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MENSAJE_ERROR_SERVICIO, null, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLSX);
			if (result.getStatus().equals(FileResultStatus.ERROR)) {
				resultado.setError(true);
				resultado.setMensaje("Error al obtener el fichero");
			} else if (result.getStatus().equals(FileResultStatus.FILE_NOT_FOUND)) {
				resultado.setMensaje("Fichero no encontrado");
				resultado.setError(true);
			} else {
				resultado.setFile(result.getFile());
			}
		} catch (OegamExcepcion e) {
			resultado.setError(true);
			resultado.setMensaje("Error: " + e.getMensajeError1());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoMensajeErrorServicio generarExcel(List<MensajeErrorServicioDto> lista) {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		try {
			if (lista != null && !lista.isEmpty()) {
				boolean cabecera = true;
				int fila = 0;
				int col = 0;
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFCellStyle formatoCeldaDate = workbook.createCellStyle();
				XSSFDataFormat format = workbook.createDataFormat();
				formatoCeldaDate.setDataFormat(format.getFormat("dd/mm/yyyy hh:mm"));
				XSSFCellStyle estiloNegrita = workbook.createCellStyle();
				XSSFFont negrita = workbook.createFont();
				negrita.setBold(true);
				negrita.setFontHeight(15);
				estiloNegrita.setFont(negrita);
				XSSFSheet sheet = workbook.createSheet("Errores servicio");
				XSSFRow row = sheet.createRow(fila++);
				for (MensajeErrorServicioDto elemento : lista) {
					if (cabecera) {
						cabecera = false;
						row.createCell(col).setCellValue("FECHA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("PROCESO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("ID ENVIO");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("COLA");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
						row.createCell(col).setCellValue("DESCRIPCION");
						row.getCell(col).setCellStyle(estiloNegrita);
						col++;
					}
					col = 0;
					row = sheet.createRow(fila++);
					row.createCell(col).setCellValue(elemento.getFecha());
					row.getCell(col).setCellStyle(formatoCeldaDate);
					col++;
					row.createCell(col++).setCellValue(elemento.getProceso());
					row.createCell(col++).setCellValue(elemento.getIdEnvio());
					row.createCell(col++).setCellValue(elemento.getCola());
					row.createCell(col++).setCellValue(elemento.getDescripcion());
				}
				for (int i = 0; i < 4; i++) {
					sheet.autoSizeColumn(i);
				}
				resultado.setFichero(workbook);
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje(e.getMessage());
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoMensajeErrorServicio guardar(MensajeErrorServicioDto mensaje) {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		try {
			mensajeErrorServicioDao.guardar(conversor.transform(mensaje, MensajeErrorServicioVO.class));
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el mensaje error servicio, error: ", e);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el mensaje error servicio");
			resultado.setError(true);
		}
		return resultado;
	}

	@Transactional
	public ResultadoMensajeErrorServicio borrar(List<MensajeErrorServicioDto> lista) {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		try {
			for (MensajeErrorServicioDto elemento : lista) {
				mensajeErrorServicioDao.borrar(conversor.transform(elemento, MensajeErrorServicioVO.class));
			}
		} catch (Exception e) {
			resultado.setError(true);
			resultado.setMensaje("Error al borrar los mensajes de error servicio");
		}
		return resultado;
	}

	public ResultadoMensajeErrorServicio guardarFichero(XSSFWorkbook workbook) {
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(false);
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);
			Fecha fecha = utilesFecha.getFechaActual();
			String nombreFichero = "ErrorServicio_" + utilesFecha.formatoFecha("yyyyMMdd", new Date());
			File file = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.MENSAJE_ERROR_SERVICIO, null, fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_XLSX, baos.toByteArray());
			resultado.setFile(file);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero:, Error: " + e.getMensajeError1());
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el fichero:");
		} catch (IOException e) {
			log.error("Error al guardar el fichero:, Error: " + e.getMessage());
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el fichero:");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoMensajeErrorServicio generarExcelListadoMensajesErrorServicio(FechaFraccionada fecha) {
		File archivo = null;
		ResultadoMensajeErrorServicio resultado = new ResultadoMensajeErrorServicio(Boolean.FALSE);

		String nombreFichero = NOMBRE_FICHERO + "_" + utilesFecha.getTimestampActual().toString();
		nombreFichero = nombreFichero.replace(':', '_');
		nombreFichero = nombreFichero.replace('-', '_');
		nombreFichero = nombreFichero.replace(' ', '_');
		nombreFichero = nombreFichero.replace('.', '_');

		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MENSAJE_ERROR_SERVICIO);
		fichero.setSubTipo(ConstantesGestorFicheros.EXCELS);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XLS);
		fichero.setNombreDocumento(nombreFichero);
		fichero.setFecha(utilesFecha.getFechaActual());
		fichero.setSobreescribir(true);
		try {
			archivo = gestorDocumentos.guardarFichero(fichero);
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el fichero excel con las estadísticas de Mensajes Error Servicio: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar el fichero excel con las estadísticas de Mensajes Error Servicio.");
		}

		// Obtenemos los objetos de la hoja de excel donde insertaremos los trámites.
		WritableWorkbook copyWorkbook = null;
		WritableSheet sheet;

		try {

			// Creamos la hoja y el fichero Excel
			copyWorkbook = Workbook.createWorkbook(archivo);
			sheet = copyWorkbook.createSheet("Errores servicio", 0);

			// Formato para las columnas que ajusten el tamaño al del texto
			CellView vistaCeldas = new CellView();
			vistaCeldas.setAutosize(true);

			for (int i = 0; i < 8; i++) {
				sheet.setColumnView(i, vistaCeldas);
			}

			WritableFont fuente = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.BOLD, false);
			fuente.setColour(Colour.BLACK);
			WritableCellFormat formato = new WritableCellFormat(fuente);

			vistaCeldas.setFormat(formato);
			for (int i = 0; i < 8; i++) {
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
			WritableFont fuenteDatos = new WritableFont(WritableFont.createFont("MS Sans Serif"), 10, WritableFont.NO_BOLD, false);
			fuenteDatos.setColour(Colour.BLACK);
			WritableCellFormat formatoDatos = new WritableCellFormat(fuenteDatos);

			formatoDatos.setAlignment(Alignment.LEFT);

			Label label = null;

			try {

				label = new Label(0, 0, "Fecha", formatoCabecera);
				sheet.addCell(label);
				label = new Label(1, 0, "Proceso", formatoCabecera);
				sheet.addCell(label);
				label = new Label(2, 0, "ID Envío", formatoCabecera);
				sheet.addCell(label);
				label = new Label(3, 0, "Cola", formatoCabecera);
				sheet.addCell(label);
				label = new Label(4, 0, "Descripción", formatoCabecera);
				sheet.addCell(label);

				Integer contador = 1;

				List<MensajeErrorServicioVO> lista = mensajeErrorServicioDao.getListaMensajeErrorServicio(fecha);

				for (MensajeErrorServicioVO element : lista) {
					// Columna Fecha
					if (element.getFecha() != null) {
						label = new Label(0, contador, utilesFecha.formatoFechaHorasMinutos(element.getFecha()), formatoDatos);
					} else {
						label = new Label(0, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Proceso
					if (StringUtils.isNotBlank(element.getProceso())) {
						label = new Label(1, contador, element.getProceso(), formatoDatos);
					} else {
						label = new Label(1, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna ID envío
					if (element.getIdEnvio() != null) {
						label = new Label(2, contador, element.getIdEnvio().toString(), formatoDatos);
					} else {
						label = new Label(2, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Cola
					if (StringUtils.isNotBlank(element.getCola())) {
						label = new Label(3, contador, element.getCola(), formatoDatos);
					} else {
						label = new Label(3, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					// Columna Descripción
					if (StringUtils.isNotBlank(element.getDescripcion())) {
						label = new Label(4, contador, element.getDescripcion(), formatoDatos);
					} else {
						label = new Label(4, contador, "", formatoDatos);
					}
					sheet.addCell(label);

					contador++;
				}

			} catch (RowsExceededException e) {
				log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
			}
			copyWorkbook.write();
		} catch (IOException | WriteException e) {
			log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
		} finally {
			if (copyWorkbook != null) {
				try {
					copyWorkbook.close();
				} catch (IOException | WriteException e) {
					log.error("Error al generar el fichero excel con las estadísticas generales y personalizadas: ,error: ", e);
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Error al generar el fichero excel con las estadísticas generales y personalizadas.");
				}
			}
		}

		resultado.setFile(archivo);
		resultado.setNombreFichero(nombreFichero);

		return resultado;

	}

}
