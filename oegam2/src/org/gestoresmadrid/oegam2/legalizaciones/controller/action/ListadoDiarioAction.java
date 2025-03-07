package org.gestoresmadrid.oegam2.legalizaciones.controller.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.legalizacion.constantes.ConstantesLegalizacion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamLegalizaciones.service.ServicioLegalizacionesCita;
import org.gestoresmadrid.oegamLegalizaciones.view.dto.LegalizacionCitaDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import general.acciones.ActionBase;
import net.sf.jasperreports.engine.JRException;
import utilidades.estructuras.Fecha;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

/**
 * @author ext_dgr
 */
public class ListadoDiarioAction extends ActionBase {

	private static final long serialVersionUID = -8518866369020619052L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ListadoDiarioAction.class);

	private Fecha fechaListado;

	private File fileUpload;
	private String fileUploadContentType;
	private String fileUploadFileName;
	private String TipoPermisos;
	private String idPeticion;
	private LegalizacionCitaDto legDto;

	private String numColegiado;

	// DESCARGAR FICHEROS
	private InputStream inputStream;
	private String fileName;
	private String fileSize;
	private Map<String, Object> session;
	private Fecha fechaEntregaDocumentacion;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioLegalizacionesCita servicioLegalizaciones;

	@Autowired
	private UtilesFecha utilesFecha;

	@Autowired
	UtilesColegiado utilesColegiado;

	@Autowired
	private Utiles utiles;

	public String buscar() {
		try {
			List<LegalizacionCitaDto> listLegal = null;
			String xml = "";
			byte[] byteFinal = null;

			Fecha fechaInicialDatos = new Fecha();
			fechaInicialDatos.setDia("01");
			fechaInicialDatos.setMes("01");
			fechaInicialDatos.setAnio("2011");
			if (fechaListado.isfechaNula() || utilesFecha.compararFechaMayor(fechaInicialDatos, fechaListado) == 1 || utilesFecha.compararFechaMayor(fechaInicialDatos, fechaListado) == -1
					|| !utilesFecha.esFechaLaborableConsiderandoFestivos(fechaListado)) {
				addActionError("Debe indicar una fecha laborable válida");
				return SUCCESS;
			} else if (StringUtils.isNotBlank(numColegiado)) {
				String cadena = String.valueOf(numColegiado);
				if (!cadena.matches("\\d*")) {
					addActionError("Debe indicar un número de colegiado válido");
					return SUCCESS;
				}
			}

			// Ruta donde está el informe.
			String ruta = gestorPropiedades.valorPropertie(ConstantesLegalizacion.DIR_PLANTILLAS);
			String nombreInforme = "";

			// Formateo del colegiado
			if (StringUtils.isNotBlank(numColegiado) && numColegiado.length() < 4) {
				numColegiado = "0" + numColegiado;
			}

			// rellenamos el mapa de los parametros del informe
			Map<String, Object> params = new HashMap<>();
			if (utilesColegiado.tienePermisoAdmin() && (StringUtils.isBlank(numColegiado))) {

				listLegal = servicioLegalizaciones.getListadoDiario("", fechaListado);
				xml = servicioLegalizaciones.transformToXML(listLegal);
				nombreInforme = gestorPropiedades.valorPropertie(ConstantesLegalizacion.PLANTILLA_COLEGIO);

				params.put("FECHA_LEGALIZACION", fechaListado.getFecha());

			} else {
				listLegal = servicioLegalizaciones.getListadoDiario(numColegiado, fechaListado);
				xml = servicioLegalizaciones.transformToXML(listLegal);
				nombreInforme = gestorPropiedades.valorPropertie(ConstantesLegalizacion.PLANTILLA_COLEGIADO);

				Fecha fecha = utilesFecha.getPrimerLaborableAnterior(fechaListado);

				params.put("COLEGIADO", numColegiado);
				params.put("FECHA_LEGALIZACION", fecha.getFecha());
			}

			params.put("IMG_DIR", ruta);
			params.put("SUBREPORT_DIR", ruta);
			// Llamamos al informe para generar el informe
			ReportExporter re = new ReportExporter();

			try {
				byteFinal = re.generarInforme(ruta, nombreInforme, "pdf", xml, "Legalizacion", params, null);
				ByteArrayInputStream stream = new ByteArrayInputStream(byteFinal);
				setInputStream(stream);
				setFileName("Informe_" + fechaListado.toString() + ConstantesGestorFicheros.EXTENSION_PDF);
			} catch (JRException e) {
				log.error(e);
			} catch (ParserConfigurationException e) {
				log.error(e);
			}

		} catch (Throwable e) {
			log.error(e);
		}
		return "descargarPDF";
	}

	public String inicio() throws Exception {
		if (utiles.esUsuarioOegam3(utilesColegiado.getIdUsuarioSessionBigDecimal().toString())) {
			return "redireccionOegam3";
		} else {
			if (!utilesColegiado.tienePermisoAdmin() && utilesColegiado.tienePermisoMinisterio()) {
				addActionError("Listado diario no disponible para usuarios de ministerio");
				return ERROR;
			}

			if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoMinisterio()) {
				numColegiado = utilesColegiado.getNumColegiadoSession();
			}

			return SUCCESS;
		}
	}

	public LegalizacionCitaDto getLegDto() {
		return legDto;
	}

	public void setLegDto(LegalizacionCitaDto legDto) {
		this.legDto = legDto;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getTipoPermisos() {
		return TipoPermisos;
	}

	public void setTipoPermisos(String tipoPermisos) {
		TipoPermisos = tipoPermisos;
	}

	public String getIdPeticion() {
		return idPeticion;
	}

	public void setIdPeticion(String idPeticion) {
		this.idPeticion = idPeticion;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Fecha getFechaEntregaDocumentacion() {
		return fechaEntregaDocumentacion;
	}

	public void setFechaEntregaDocumentacion(Fecha fechaEntregaDocumentacion) {
		this.fechaEntregaDocumentacion = fechaEntregaDocumentacion;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Fecha getFechaListado() {
		return fechaListado;
	}

	public void setFechaListado(Fecha fechaListado) {
		this.fechaListado = fechaListado;
	}

	public String getNumColegiado() {
		return numColegiado;
	}

	public void setNumColegiado(String numColegiado) {
		this.numColegiado = numColegiado;
	}

}