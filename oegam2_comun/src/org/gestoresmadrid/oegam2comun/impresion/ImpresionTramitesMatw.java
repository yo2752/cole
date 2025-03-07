package org.gestoresmadrid.oegam2comun.impresion;

import static trafico.utiles.ConstantesPDF.VALOR_NO;
import static trafico.utiles.ConstantesPDF.VALOR_SI;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.NoSujeccionOExencion;
import org.gestoresmadrid.core.model.enumerados.ReduccionNoSujeccionOExencion05;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteMatriculacion;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TiposReduccion576;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.core.vehiculo.model.enumerados.ConceptoTutela;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.impresion.util.ImpresionGeneral;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields.FieldPosition;
import com.itextpdf.text.pdf.BarcodePDF417;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.enums.FileResultStatus;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import general.utiles.UtilesCadenaCaracteres;
import general.utiles.UtilesTrafico;
import net.sf.jasperreports.engine.JRException;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.constantes.ConstantesTrafico;
import utilidades.estructuras.Fecha;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ImpresionTramitesMatw extends ImpresionGeneral {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionTramitesMatw.class);

	private TramiteTrafMatrDto detalleMatriculacion;
	private ArrayList<TramiteTrafMatrDto> listaTramitesMat;

	private ArrayList<CampoPdfBean> camposFormateados;
	private Set<String> camposPlantilla;

	private String ruta;
	private String rutaPlantillas;

	private byte[] bytePdf;
	private PdfMaker pdf;

	private String mensaje;

	private static final String CABECERA = "expediente";

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	// Tipo impresos: PDF417, Borrador417 y PDFTelematico
	public ResultBean matriculacionGeneralMatw(String numExpediente, String tipoImpreso, TipoTramiteTrafico tipoTramiteTrafico) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<>();
		pdf = new PdfMaker();

		String nube = null;
		String nube2 = null;

		boolean error = false;
		mensaje = "";

		int[] vectPags = new int[ConstantesPDF._1];
		vectPags[0] = ConstantesPDF._1;
		// -------------------------------------------------------------------------------------------------

		obtenerDetalleTramiteMatriculacion(numExpediente, tipoImpreso);

		if (detalleMatriculacion != null) {
			ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
			ContratoVO contratoVO = servicioContrato.getContrato(detalleMatriculacion.getIdContrato());

			calcularRuta(tipoImpreso);

			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);

			procesarTipoTramite();
			procesarIvtmCemu();
			procesarExentoIvtm();

			procesarDatosPresentacion(tipoImpreso, contratoVO);

			if (procesarDatosInterviniente()) {
				mensaje = "Los datos del titular están vacíos";
			}

			procesarDatosArrendatario(tipoImpreso);
			procesarConductorHabitual();

			if (procesarRenting(tipoImpreso)) {
				error = true;
				mensaje = "Con renting hay que especificar 'arrendatario' o 'conductor habitual'.";
			}

			if (error || bytePdf == null) {
				error = true;
				mensaje += ". Puede que no esté completo el trámite impreso.";
			}

			textosExternos(tipoImpreso, vectPags, nube, nube2, error);
			procesarMatricula(mensaje);
			procesarFechaActual();

			bytePdf = insertarFirmaColegioProceso(vectPags, contratoVO);

			procesarCodigoItv();

			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error || bytePdf == null) {
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		return resultadoMetodo;
	}

	/**
	 * Método para imprimir el listado de bastidores, dependiendo de si viene o no el mismo número de bastidor.
	 * @param listaTramites
	 * @return
	 * @throws OegamExcepcion
	 */
	public ResultBean matriculacionListadoBastidoresMatriculacion(List<String> numExpedientes) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		byte[] byteFinal = null;

		listaTramitesMat = new ArrayList<>();

		pdf = new PdfMaker();

		// ---------------------------------------------------------------

		for (String numExpediente : numExpedientes) {
			obtenerDetalleTramiteMatriculacion(numExpediente, TipoImpreso.MatriculacionListadoBastidores.toString());
		}

		String nombreInforme = gestorPropiedades.valorPropertie("matw.plantillas.relacionMatriculas");

		calcularRuta(TipoImpreso.MatriculacionListadoBastidores.toString());

		// Generamos el XML para imprimir el pdf
		String xmlDatos = generaXMLBastidores(CABECERA);

		Map<String, Object> params = obtenerParametros();
		if (params == null) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Se ha producido un error al generar el PDF del listado de Bastidores ");
			return resultadoMetodo;
		}

		// Llamamos al informe para generar el informe
		ReportExporter re = new ReportExporter();

		try {
			byteFinal = re.generarInforme(ruta, nombreInforme, "pdf", xmlDatos, CABECERA, params, null);
		} catch (JRException | ParserConfigurationException e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Se ha producido un error al generar el PDF del listado de Bastidores: " + e.getMessage());
			return resultadoMetodo;
		}

		if (byteFinal != null) {
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byteFinal);
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "listadoBastidores.pdf");
			resultadoMetodo.setError(false);
		} else if (!resultadoMetodo.getError()) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Error al generar el PDF del listado de Bastidores");
		}
		return resultadoMetodo;
	}

	public ResultBean impresionMandatosMatriculacion(String numExpediente, String tipoImpreso) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		camposFormateados = new ArrayList<>();
		pdf = new PdfMaker();

		mensaje = "";

		boolean error = false;
		// ---------------------------------------------------------------

		obtenerDetalleTramiteMatriculacion(numExpediente, tipoImpreso);

		if (detalleMatriculacion != null) {
			calcularRuta(tipoImpreso);
			bytePdf = pdf.abrirPdf(ruta);
			camposPlantilla = pdf.getAllFields(bytePdf);
			camposFormateados.addAll(obtenerValoresMandatoMatwDuplicadoYSol(ConstantesPDF._11, detalleMatriculacion.getTitular(), detalleMatriculacion.getRepresentanteTitular(), camposPlantilla,
					detalleMatriculacion.getIdContrato(), detalleMatriculacion.getNumColegiado()));
			procesarAsuntos();
			bytePdf = pdf.setCampos(bytePdf, camposFormateados);

		} else {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		}

		if (error || bytePdf == null) {
			error = true;
			mensaje += ". Puede que no esté completo el trámite impreso.";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, bytePdf);
		if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoEspecifico.toString())) {
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "MandatoEspecifico.pdf");
		} else {
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "MandatoGenerico.pdf");
		}
		return resultadoMetodo;
	}

	private void procesarAsuntos() {
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "MATRICULACION: ", false, false, ConstantesPDF._11));
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
			if (detalleMatriculacion.getVehiculoDto() != null) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, detalleMatriculacion.getVehiculoDto().getBastidor(), false, false, ConstantesPDF._11));
			}
		}
	}

	private void calcularRuta(String tipoImpreso) throws OegamExcepcion {
		UtilResources util = new UtilResources();
		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			ruta = gestorPropiedades.valorPropertie("matw.plantillas");
		} else {
			ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		}

		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417Matw.toString()) || tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString())) {
			rutaPlantillas = ruta;
		}

		if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionBorradorPDF417Matw.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417Matw.toString()) || tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoGenerico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoGenerico.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionMandatoEspecifico.toString())) {
			ruta = util.getFilePath(ruta + TipoImpreso.MatriculacionMandatoEspecifico.getNombreEnum());
		}
	}

	private void obtenerDetalleTramiteMatriculacion(String numExpediente, String tipoImpreso) throws Throwable {
		detalleMatriculacion = new TramiteTrafMatrDto();
		ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion = ContextoSpring.getInstance().getBean(ServicioTramiteTraficoMatriculacion.class);
		detalleMatriculacion = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(numExpediente), true, true);
		if (tipoImpreso != null && tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			listaTramitesMat.add(detalleMatriculacion);
		}
	}

	private byte[] insertarFirmaColegioProceso(int[] vectPags, ContratoVO contratoVO) throws OegamExcepcion {
		String fechaDeHoy = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		ServicioPersona servicioPersona = ContextoSpring.getInstance().getBean(ServicioPersona.class);
		PersonaDto colegiado = servicioPersona.obtenerColegiadoCompleto(detalleMatriculacion.getNumColegiado(), new BigDecimal(contratoVO.getIdContrato()));

		// Insertamos Firma del Colegio:
		String firmaColegio = contratoVO.getCif()
				+ "#"
				+ (detalleMatriculacion.getVehiculoDto().getBastidor() != null && !detalleMatriculacion.getVehiculoDto().getBastidor().equals("") ? detalleMatriculacion.getVehiculoDto().getBastidor()
						: "") + "#" + detalleMatriculacion.getTitular().getPersona().getNif() + "#" + colegiado.getNif() + "#" + contratoVO.getColegio().getCif() + "#" + fechaDeHoy;
		// Ciframos la firma, convertimos a hexadecimal y restringimos a los
		// primeros _16 caracteres.
		firmaColegio = utiles.cifrarHMACSHA1(firmaColegio, "ClaveCifrado");
		firmaColegio = utiles.stringToHex(firmaColegio);
		firmaColegio = firmaColegio.substring(0, ConstantesPDF._16);
		bytePdf = insertarBarcodeFirma(ConstantesPDF.ID_FIRMA_COLEGIO, vectPags, firmaColegio);

		return bytePdf;
	}

	private byte[] insertarBarcodeFirma(String nombreCampo, int[] vectPags, String barcode) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, nombreCampo);
		FieldPosition posicion = posiciones.get(0);
		bytePdf = pdf.crearCodigoBarras128ConTexto(barcode, bytePdf, -ConstantesPDF._1, ConstantesPDF._12, -ConstantesPDF._1, true, posicion.position.getLeft(), posicion.position.getBottom());
		return bytePdf;
	}

	private void procesarTipoTramite() {
		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_TRAMITE)) {
			TipoTramiteMatriculacion tipoTramiteMatriculacion = TipoTramiteMatriculacion.convertir(detalleMatriculacion.getTipoTramiteMatr());
			String TIPO_TRAMITE_MAT = (null != tipoTramiteMatriculacion ? tipoTramiteMatriculacion.getNombreEnum() : "");
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TRAMITE, TIPO_TRAMITE_MAT, false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarIvtmCemu() {
		CampoPdfBean campoAux;
		if (camposPlantilla.contains(ConstantesTrafico.ID_IVTM_CEMU_MTW)) {
			String IVTM = "DOCUMENTO ESCANEADO EN LA PLATAFORMA";
			ServicioIvtmMatriculacion servicioIvtmMatriculacion = ContextoSpring.getInstance().getBean(ServicioIvtmMatriculacion.class);
			IvtmMatriculacionDto ivtmMatriculacion = servicioIvtmMatriculacion.getIvtmPorExpedienteDto(detalleMatriculacion.getNumExpediente());
			if (ivtmMatriculacion != null && ivtmMatriculacion.getNrc() != null) {
				IVTM = ivtmMatriculacion.getNrc();
			}
			if (IVTM.equals("DOCUMENTO ESCANEADO EN LA PLATAFORMA")) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_IVTM_CEMU_MTW, IVTM, false, false, ConstantesPDF._6);
			} else {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_IVTM_CEMU_MTW, IVTM, false, false, ConstantesPDF._10);
			}
			camposFormateados.add(campoAux);
		}
	}

	private void procesarExentoIvtm() {
		CampoPdfBean campoAux;
		if (camposPlantilla.contains(ConstantesTrafico.ID_EXENCION_IVTM_MTW)) {
			if (detalleMatriculacion.getJustificadoIvtm() != null && detalleMatriculacion.getJustificadoIvtm()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_EXENCION_IVTM_MTW, VALOR_SI, true, false, ConstantesPDF._8);
			} else {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_EXENCION_IVTM_MTW, VALOR_NO, true, false, ConstantesPDF._8);
			}
			camposFormateados.add(campoAux);
		}
	}

	private void procesarDatosPresentacion(String tipoImpreso, ContratoVO contratoVO) throws OegamExcepcion {
		camposFormateados.addAll(obtenerValoresCamposMatw(ConstantesPDF._8, camposPlantilla, detalleMatriculacion, contratoVO));
		bytePdf = pdf.setCampos(bytePdf, camposFormateados);
	}

	private boolean procesarDatosInterviniente() {
		if (detalleMatriculacion.getTitular() == null) {
			return true;
		} else {
			camposFormateados.addAll(obtenerValoresIntervinienteMatw(ConstantesPDF._8, detalleMatriculacion.getTitular(), 1, camposPlantilla));

			procesarDoiRepresentanteTitular();
			procesarTutela();
			procesarTipoTutela();
			procesarFechaInicioTutela();
			procesarDireccionVehiculo();
		}
		return false;
	}

	private void procesarDireccionVehiculo() {
		// Comprobamos direccion del vehiculo y si no metemos la del titular
		// Dirección del vehículo
		VehiculoDto vehiculo = detalleMatriculacion.getVehiculoDto();
		if (vehiculo != null && vehiculo.getDireccion() == null && detalleMatriculacion.getTitular().getDireccion() != null) {
			DireccionDto direccion = detalleMatriculacion.getTitular().getDireccion();
			vehiculo.setDireccion(direccion);
			camposFormateados.addAll(obtenerValoresDireccionMatw(ConstantesPDF._8, direccion, 4, camposPlantilla));
		}
	}

	private void procesarDoiRepresentanteTitular() {
		if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_TITULAR_MTW)) {
			String dniRepr = (null != detalleMatriculacion.getRepresentanteTitular() ? detalleMatriculacion.getRepresentanteTitular().getNifInterviniente() : "");
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_TITULAR_MTW, dniRepr, false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarTutela() {
		if (camposPlantilla.contains(ConstantesTrafico.ID_TUTELA_TITULAR_MTW)) {
			if (detalleMatriculacion.getRepresentanteTitular() != null) {
				ConceptoTutela conceptoRepre = ConceptoTutela.convertir(detalleMatriculacion.getRepresentanteTitular().getConceptoRepre());
				CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_TUTELA_TITULAR_MTW, (ConceptoTutela.Tutela.equals(conceptoRepre) ? "SI" : "NO"), false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
		}
	}

	private void procesarTipoTutela() {
		if (camposPlantilla.contains(ConstantesTrafico.ID_TIPO_TUTELA_TITULAR_MTW) && detalleMatriculacion.getRepresentanteTitular() != null) {
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_TIPO_TUTELA_TITULAR_MTW, (null != detalleMatriculacion.getRepresentanteTitular().getIdMotivoTutela() ? detalleMatriculacion
					.getRepresentanteTitular().getIdMotivoTutela() : ""), false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarFechaInicioTutela() {
		if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_INICIO_TUTELA_TITULAR_MTW) && detalleMatriculacion.getRepresentanteTitular() != null
				&& detalleMatriculacion.getRepresentanteTitular().getFechaInicio() != null
				&& (detalleMatriculacion.getRepresentanteTitular().getFechaInicio().getAnio() != null || detalleMatriculacion.getRepresentanteTitular().getFechaInicio().getMes() != null || detalleMatriculacion
						.getRepresentanteTitular().getFechaInicio().getDia() != null)) {
			Fecha fecha = detalleMatriculacion.getRepresentanteTitular().getFechaInicio();
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_INICIO_TUTELA_TITULAR_MTW, fecha.toString(), false, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarDatosArrendatario(String tipoImpreso) {
		if (detalleMatriculacion.getArrendatario() != null) {
			camposFormateados.addAll(obtenerValoresIntervinienteMatw(ConstantesPDF._8, detalleMatriculacion.getArrendatario(), 2, camposPlantilla));
			// DOI ARRENDATARIO
			if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_ARRENDATARIO)) {
				String dniRepr = (null != detalleMatriculacion.getRepresentanteArrendatario() ? detalleMatriculacion.getRepresentanteArrendatario().getNifInterviniente() : "");
				CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_ARRENDATARIO, dniRepr, false, false, ConstantesPDF._8);
				camposFormateados.add(campoAux);
			}
			if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
				bytePdf = pdf.setCampos(bytePdf, camposFormateados);
			}
		}
	}

	private void procesarConductorHabitual() {
		if (detalleMatriculacion.getConductorHabitual() != null) {
			camposFormateados.addAll(obtenerValoresIntervinienteMatw(ConstantesPDF._8, detalleMatriculacion.getConductorHabitual(), 3, camposPlantilla));
		}
	}

	private boolean procesarRenting(String tipoImpreso) {
		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417.toString())
				&& detalleMatriculacion.getRenting() != null && detalleMatriculacion.getRenting()) {
			boolean faltaArrendatario = false;
			boolean faltaConductorHabitual = false;
			if (detalleMatriculacion.getArrendatario() == null || detalleMatriculacion.getArrendatario().getPersona() == null
					|| detalleMatriculacion.getArrendatario().getPersona().getNif() == null) {
				faltaArrendatario = true;
			}
			if (detalleMatriculacion.getConductorHabitual() == null || detalleMatriculacion.getConductorHabitual().getPersona() == null
					|| detalleMatriculacion.getConductorHabitual().getPersona().getNif() == null) {
				faltaConductorHabitual = true;
			}
			if (faltaConductorHabitual && faltaArrendatario) {
				return true;
			}
		}
		return false;
	}

	private void procesarMatricula(String mensaje) {
		if (null != detalleMatriculacion.getVehiculoDto().getMatricula()) {
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_MATW, "MATRICULA: " + detalleMatriculacion.getVehiculoDto().getMatricula(), true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		} else {
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_MATW, mensaje, true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarFechaActual() {
		if (camposPlantilla.contains(ConstantesPDF.ID_FECHA_ACTUAL_MATW)) {
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_ACTUAL_MATW, utilesFecha.getFechaActual().toString(), false, false, ConstantesPDF._11);
			camposFormateados.add(campoAux);
		}
	}

	private void procesarCodigoItv() {
		ServicioEvolucionVehiculo servicioEvolucionVehiculo = ContextoSpring.getInstance().getBean(ServicioEvolucionVehiculo.class);
		String otrosSinCodig = servicioEvolucionVehiculo.obtenerSinCodig(detalleMatriculacion.getVehiculoDto().getIdVehiculo().longValue(), detalleMatriculacion.getNumExpediente());
		if (detalleMatriculacion.getVehiculoDto().getServicioTrafico().getIdServicio() != null && otrosSinCodig != null && !otrosSinCodig.isEmpty()) {
			String sinCodigTexto = "DIFERENCIAS EN CÓDIGO ITV: " + otrosSinCodig;
			sinCodigTexto = sinCodigTexto.substring(0, sinCodigTexto.lastIndexOf(","));
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesPDF.ID_SINCODIG, sinCodigTexto, false, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
	}

	private Map<String, Object> obtenerParametros() {
		Map<String, Object> params = new HashMap<>();

		DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(listaTramitesMat.get(0).getNumExpediente());

		String codCarpeta = docBaseCarpetaTramiteBean.getTipoCarpeta();
		String nombreCarpeta = DocumentoBaseTipoCarpetaEnum.convertirTexto(docBaseCarpetaTramiteBean.getTipoCarpeta());
		String gestor = listaTramitesMat.get(0).getNumColegiado();
		ColegiadoDto col = null;
		try {
			ServicioColegiado servicioColegiado = ContextoSpring.getInstance().getBean(ServicioColegiado.class);
			col = servicioColegiado.getColegiadoDto(gestor);
			gestor = col != null && col.getUsuario() != null ? gestor + " - " + col.getUsuario().getApellidosNombre() : gestor + "";
		} catch (Throwable t) {
			log.error("Error recuperando usuario", t);
			return null;
		}

		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoVO contratoVO = servicioContrato.getContrato(listaTramitesMat.get(0).getIdContrato());

		String gestoria = contratoVO.getRazonSocial();
		String nifGestoria = col.getUsuario().getNif(); // TODO: puede dar NullPointerException
		String totalExp = listaTramitesMat.size() + "";
		String totalEITV = getNumEITV();
		String totalAmbientales = getTotalAmbientales();
		String datosNubePuntos = getDatosNubePuntosListadoBastidores();
		java.awt.Image nubePuntos417;
		try {
			nubePuntos417 = getBarcode(datosNubePuntos);
		} catch (Exception e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			return null;
		}

		params.put("SUBREPORT_DIR", ruta);
		params.put("IMG_DIR", ruta);
		params.put("CODIGO_CARPETA", codCarpeta);
		params.put("NOMBRE_CARPETA", nombreCarpeta);
		params.put("COLOR_CARPETA", "");
		params.put("ESTADO_CARPETA", "");
		params.put("GESTOR", gestor);
		params.put("GESTORIA", gestoria);
		params.put("NIF_GESTORIA", nifGestoria);
		params.put("TOTAL_EXP", totalExp);
		params.put("TOTAL_EITV", totalEITV);
		params.put("TOTAL_AMBIENTALES", totalAmbientales);
		params.put("NUBE_PUNTOS", nubePuntos417);
		params.put("FECHA_PRESENTACION", listaTramitesMat.get(0).getFechaPresentacion());

		return params;
	}

	/**
	 * Dada la lista de tramites, obetenemos el xml que usamos para generar el informe
	 * @param listaTramites
	 * @return xml de datos
	 */
	private String generaXMLBastidores(String cabecera) {
		String xml = "";

		if (!listaTramitesMat.isEmpty()) {

			xml = "<?xml version='1.0' encoding='UTF-8' standalone='no' ?> <" + cabecera + "s>";

			for (int i = 0; i < listaTramitesMat.size(); i++) {

				TramiteTrafMatrDto tramite = listaTramitesMat.get(i);
				xml = xml + "<" + cabecera + ">";
				// numero expediente
				xml = xml + "<numexpediente>";
				if (tramite.getNumExpediente() != null) {
					xml = xml + tramite.getNumExpediente();
				}
				xml = xml + "</numexpediente>";
				// matricula
				xml = xml + "<matricula>";
				if (tramite.getVehiculoDto().getMatricula() != null) {
					xml = xml + tramite.getVehiculoDto().getMatricula();
				}
				xml = xml + "</matricula>";
				// bastidor
				xml = xml + "<bastidor>";
				if (tramite.getVehiculoDto().getBastidor() != null) {
					xml = xml + tramite.getVehiculoDto().getBastidor();
				}
				xml = xml + "</bastidor>";
				// titular
				xml = xml + "<titular>";
				xml = xml + "<nif>";
				if (tramite.getTitular().getPersona().getNif() != null) {
					xml = xml + tramite.getTitular().getPersona().getNif();
				}
				xml = xml + "</nif>";
				xml = xml + "<nombreapp>";
				if (tramite.getTitular().getPersona() != null) {
					String nomApp = "";
					if (tramite.getTitular().getPersona().getNombre() != null) {
						nomApp = tramite.getTitular().getPersona().getNombre();
					}

					if (tramite.getTitular().getPersona().getApellido1RazonSocial() != null) {
						nomApp += " " + tramite.getTitular().getPersona().getApellido1RazonSocial();
					}

					if (tramite.getTitular().getPersona().getApellido2() != null) {
						nomApp += " " + tramite.getTitular().getPersona().getApellido2();
					}
					xml = xml + nomApp;
				}
				xml = xml + "</nombreapp>";
				xml = xml + "</titular>";

				// tasa
				xml = xml + "<tasa>";
				if (tramite.getTasa() != null) {
					if (tramite.getTasa().getCodigoTasa() != null) {
						xml = xml + tramite.getTasa().getCodigoTasa();
					}
				}
				xml = xml + "</tasa>";

				// ficha electr
				xml = xml + "<fichaelectr>";
				if (tramite.getVehiculoDto().getNive() != null) {
					xml = xml + "SI";
				}
				xml = xml + "</fichaelectr>";

				// dist ambiental
				xml = xml + "<distambiental>";
				// Mantis 17104: David Sierra: Validacion vehiculos distintivo medioambiental
				if (tramite.getVehiculoDto() != null) {
					if (isValidoAmbiental(tramite.getVehiculoDto())) {
						xml = xml + "SI";
					}
				}
				// Fin mantis
				xml = xml + "</distambiental>";

				xml = xml + "</" + cabecera + ">";
			}
			xml = xml + "</" + cabecera + "s>";
		}
		return xml;
	}

	private java.awt.Image getBarcode(String datosNubePuntos) throws Exception {
		BarcodePDF417 barcode = new BarcodePDF417();
		barcode.setText(datosNubePuntos);
		barcode.setAspectRatio(.25f);
		return barcode.createAwtImage(Color.BLACK, Color.WHITE);
	}

	private String getNumEITV() {
		String eitv = "";
		int contador = 0;
		for (int i = 0; i < listaTramitesMat.size(); i++) {
			if (listaTramitesMat.get(i).getVehiculoDto().getNive() != null) {
				contador++;
			}
		}
		eitv = eitv + contador;
		return eitv;
	}

	private String getTotalAmbientales() {
		String electricos = "";
		int contador = 0;
		for (int i = 0; i < listaTramitesMat.size(); i++) {
			// Mantis David Sierra: Se cuentan los expedientes con distintivo medioambiental
			if (listaTramitesMat.get(i).getVehiculoDto() != null) {
				if (isValidoAmbiental(listaTramitesMat.get(i).getVehiculoDto())) {
					contador++;
				}
			}
		}
		electricos = electricos + contador;
		return electricos;
	}

	private String getDatosNubePuntosListadoBastidores() {
		String nube = "";
		if (!listaTramitesMat.isEmpty()) {
			for (int i = 0; i < listaTramitesMat.size(); i++) {
				TramiteTrafMatrDto tramite = listaTramitesMat.get(i);
				if (i > 0) {
					nube = nube + ";";
				}
				if (tramite.getVehiculoDto().getMatricula() != null) {
					nube = nube + tramite.getVehiculoDto().getMatricula();
				}
			}
		}
		return nube;
	}

	private void textosExternos(String tipoImpreso, int[] vectPags, String nube, String nube2, boolean error) {
		comprobacionTelematica();
		bytePdf = etiquetasPlantilla(tipoImpreso, ConstantesTrafico.ID_NUBE_PUNTOS, ConstantesTrafico.ID_NUBE_PUNTOS_2);
		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417.toString()) || tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica.toString())) {
			rutaAguaMatw(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA);
			nubePuntos(nube, nube2, vectPags);
		}
		generarTipoImpreso();
	}

	private byte[] etiquetasPlantilla(String tipoImpreso, String campoNube, String campoNube2) {
		if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, true, true, ConstantesPDF._26);
			bytePdf = pdf.setCampo(bytePdf, campoNube, "BORRADOR");
			if (camposPlantilla.contains(campoNube2)) {
				bytePdf = pdf.setCampo(bytePdf, campoNube2, "BORRADOR");
			}
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}
		return bytePdf;
	}

	private void nubePuntos(String nube, String nube2, int vectPags[]) {
		nube1(vectPags, nube);
		nube2(vectPags, nube2);
	}

	private void nube1(int vectPags[], String nube) {
		nube = obtenerNubeMatriculacionMatw(detalleMatriculacion, 0, ConstantesTrafico.POS_IN_NUBE2); // NUEVA NUBE DE MATW
		nube = UtilesCadenaCaracteres.sustituyeCaracteres_MATW(nube);
		bytePdf = insertarNubePuntosMatw(ConstantesTrafico.ID_NUBE_PUNTOS, vectPags, nube);
	}

	private void nube2(int vectPags[], String nube2) {
		if (detalleMatriculacion.getRenting()
				|| (detalleMatriculacion.getConductorHabitual() != null && detalleMatriculacion.getConductorHabitual().getPersona() != null && detalleMatriculacion.getConductorHabitual().getPersona()
						.getNif() != null)) {
			nube2 = obtenerNubeMatriculacionMatw(detalleMatriculacion, ConstantesTrafico.POS_IN_NUBE2, ConstantesTrafico.POS_FIN_NUBE2);
			nube2 = UtilesCadenaCaracteres.sustituyeCaracteres(nube2);
			bytePdf = insertarNubePuntosMatw(ConstantesTrafico.ID_NUBE_PUNTOS_2, vectPags, nube2);
		}
	}

	private byte[] insertarNubePuntosMatw(String tipoNube, int[] vectPags, String nube) {
		List<FieldPosition> posiciones = pdf.getFieldPosition(bytePdf, tipoNube);
		FieldPosition posicion = posiciones.get(0);
		Image img = pdf.crearNubePuntosMatw(nube);
		img = pdf.escalarImagen(img, posicion.position.getWidth(), posicion.position.getHeight());
		return pdf.insertarImagen(bytePdf, img, vectPags, posicion.position.getLeft(), posicion.position.getBottom());
	}

	private void rutaAguaMatw(String tipoImpreso, int[] vectPags, String rutaJpg) {
		boolean habBastDuplicado = false;

		VehiculoDto vehiculo = detalleMatriculacion.getVehiculoDto() != null ? detalleMatriculacion.getVehiculoDto() : null;
		if (vehiculo != null) {
			if (vehiculo.getBastidorMatriculado() != null && vehiculo.getBastidorMatriculado().equals(BigDecimal.valueOf(1)) && habBastDuplicado) {
				bytePdf = rutaAgua(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA_BASTIDOR_MATRICULADO);
			} else if (vehiculo.getCarburante() != null && vehiculo.getCarburante().equals("E")) {
				bytePdf = rutaAgua(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA_VEHICULO_ELECTRICO);
			}else if(comprobarDirectivaCeeFinSerie(vehiculo)){
				bytePdf = rutaAgua(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA_FIN_SERIE);
			} else {
				bytePdf = rutaAgua(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA);
			}
		} else {
			bytePdf = rutaAgua(tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA);
		}
	}

	private byte[] rutaAgua(String tipoImpreso, int[] vectPags, String rutaJpg) {
		UtilResources util = new UtilResources();
		String rutaMarcaAgua = util.getFilePath(rutaPlantillas + rutaJpg);
		Image img = pdf.cargarImagen(rutaMarcaAgua);
		bytePdf = pdf.insertarMarcaDeAgua(bytePdf, img, vectPags, ConstantesPDF._110, ConstantesPDF._250, ConstantesPDF._45);
		return bytePdf;
	}

	private void comprobacionTelematica() {
		if (!new BigDecimal(EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum()).equals(detalleMatriculacion.getEstado())
				&& !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum()).equals(detalleMatriculacion.getEstado())
				&& !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum()).equals(detalleMatriculacion.getEstado())
				&& !new BigDecimal(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getValorEnum()).equals(detalleMatriculacion.getEstado())) {

			String mensajeNoValidacion = detalleMatriculacion.getRespuesta();
			if (null != mensajeNoValidacion && !"".equals(mensajeNoValidacion)) {
				CampoPdfBean campoAux = new CampoPdfBean(ConstantesPDF.ID_ERRORES_MATE, "NO MATRICULABLE TELEMÁTICAMENTE: " + mensajeNoValidacion, true, false, ConstantesPDF._10);
				camposFormateados.add(campoAux);
			}
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._13);
		}
	}

	private void generarTipoImpreso() {
		// Decidirá que tipo de impreso es, esto se coloca en el impreso abajo a la derecha.
		String mensajeExencion = "";
		if (detalleMatriculacion.getIdReduccion05() != null && !detalleMatriculacion.getIdReduccion05().isEmpty() && !"-1".equals(detalleMatriculacion.getIdReduccion05())) {
			ReduccionNoSujeccionOExencion05 reduccionNoSujeccionOExencion05 = ReduccionNoSujeccionOExencion05.convertir(detalleMatriculacion.getIdReduccion05());
			mensajeExencion = "MODELO 05 - " + reduccionNoSujeccionOExencion05.getNombreEnum();
		} else if (detalleMatriculacion.getIdNoSujeccion06() != null && !detalleMatriculacion.getIdNoSujeccion06().isEmpty() && !"-1".equals(detalleMatriculacion.getIdNoSujeccion06())) {
			NoSujeccionOExencion noSujeccionOExencion = NoSujeccionOExencion.convertir(detalleMatriculacion.getIdNoSujeccion06());
			mensajeExencion = "MODELO 06 - " + noSujeccionOExencion.getNombreEnum();
		} else if (detalleMatriculacion.getReduccion576() != null && detalleMatriculacion.getReduccion576()) {
			mensajeExencion = "MODELO 576 - " + TiposReduccion576.FamiliaNumerosa.getNombreEnum();
		} else if (detalleMatriculacion.getIedtm() != null && !detalleMatriculacion.getIedtm().isEmpty() && !"-1".equals(detalleMatriculacion.getIedtm())) {
			mensajeExencion = "NO TIENE MODELO 576";
		} else {
			mensajeExencion = "MODELO 576";
		}

		if (!"".equals(mensajeExencion)) {
			CampoPdfBean campoAux = new CampoPdfBean(ConstantesPDF.ID_EXENCION, mensajeExencion, true, false, ConstantesPDF._10);
			camposFormateados.add(campoAux);
		}
	}

	// Mantis 17104. David Sierra: Validación de los vehículos para verificar si deben tener el distintivo medioambiental
	// Mantis 0025314. David Rico: Distinción de las 4 clasificaciones de distintivos medioambientales
	private boolean isValidoAmbiental(VehiculoDto vehiculo) {
		log.info("Inicio: isValidoAmbiental");

		boolean validacion = false;
		// El vehículo debe tener alguna de las siguientes categorías de homologacion M, N o L
		if (vehiculo.getIdDirectivaCee() != null && (vehiculo.getIdDirectivaCee().contains("M") ||
			vehiculo.getIdDirectivaCee().contains("N") ||
			vehiculo.getIdDirectivaCee().contains("L"))) {

			if (isDistintivoCero(vehiculo) || isDistintivoEco(vehiculo)){
				validacion = true;
			}
			/* Estos distintivos todavía no lo contempla la DGT
			else if(isDistintivoC(vehiculo) || isDistintivoB(vehiculo)){
				validacion = true;
			}
			*/
		}

		return validacion;
	}

	private boolean isDistintivoCero(VehiculoDto vehiculo) {
		log.info("Inicio: isDistintivoCero");

		boolean validacion = false;

		// Si Vehículo con tipo de matrícula ordinaria o ciclomotor.
		if ((vehiculo.getMatricula() != null)
				&& (("ordinaria".equals(UtilesTrafico.analizarMatriculaVehiculo(vehiculo.getMatricula()))
						|| "ciclomotor".equals(UtilesTrafico.analizarMatriculaVehiculo(vehiculo.getMatricula()))))) {
			// Si vehículos con carburante eléctrico o diesel o gasolina
			if (vehiculo.getCarburante() != null && ( Combustible.Electrico.getValorEnum().equals(vehiculo.getCarburante())
					|| Combustible.Diesel.getValorEnum().equals(vehiculo.getCarburante())
					|| Combustible.Gasolina.getValorEnum().equals(vehiculo.getCarburante()))) {
				if (vehiculo.getCategoriaElectrica() != null && ("BEV".equals(vehiculo.getCategoriaElectrica()) || "REEV".equals(vehiculo.getCategoriaElectrica()) || "FCEV".equals(vehiculo.getCategoriaElectrica()))) {
					// La categoría eléctrica informada debe ser BEV o REEV.
					validacion = true;
				} // Con categoría eléctrica PHEV la autonomía que debe tener el vehículo debe ser igual o superior a 40 KM
				else if ((vehiculo.getAutonomiaElectrica() != null) && ("PHEV".equals(vehiculo.getCategoriaElectrica())
						&& ((vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == 1)
						|| vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == 0))) {
					validacion = true;
				}else if (Combustible.Hidrogeno.getValorEnum().equals(vehiculo.getCarburante()))
					//Vehículo con pila de combustible (HIDROGENO). Para un futuro.
					validacion = true;
			}
		}
		return validacion;
	}

	private boolean isDistintivoEco(VehiculoDto vehiculo) {
		log.info("Inicio: isDistintivoEco");

		boolean validacion = false;

		if ((vehiculo.getMatricula() != null)
				&& ("ordinaria".equals(UtilesTrafico.analizarMatriculaVehiculo(vehiculo.getMatricula())))) {

			// Vehículo híbrido enchufable con autonomia inferior a 40 Km
			if (vehiculo.getCategoriaElectrica() != null) {
				if ("PHEV".equals(vehiculo.getCategoriaElectrica())
						&& vehiculo.getAutonomiaElectrica() != null && vehiculo.getAutonomiaElectrica().compareTo(new BigDecimal(40)) == -1) {
					validacion = true;
					// Vehículo híbrido no enchufable
				} else if ("HEV".equals(vehiculo.getCategoriaElectrica())) {
					validacion = true;
				}
			}
			// Vehículo propulsado por gas natural comprimido (GNC) o gas natural licuado (GNL) o gas licuado del petroleo (GLP)
			else if ("GNC".equals(vehiculo.getCarburante()) || "GNL".equals(vehiculo.getCarburante())
					|| "GLP".equals(vehiculo.getCarburante())) {
				validacion = true;
			}
		}
		return validacion; //&& isDistintivoC(vehiculo);
	}

	public ResultBean imprimirConsultaTarjetaEitvPorExpedientes(String[] numExpedientes, String tipo) {
		ResultBean result = new ResultBean();
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		String subtipo = ConstantesGestorFicheros.PDF_CONSULTA_EITV;
		String tipoFichero = ConstantesGestorFicheros.MATE;
		String extension = ConstantesGestorFicheros.EXTENSION_PDF;
		byte[] byte1 = null;
		try {
			url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + "zip" + System.currentTimeMillis();
			out = new FileOutputStream(url);
			zip = new ZipOutputStream(out);

			for (int i = 0; i < numExpedientes.length; i++) {
				Fecha fecha = null;
				if (numExpedientes[i].contains("_")) {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i].split("_")[1]);
				} else {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i]);
				}

				String nombreFichero = TipoImpreso.ConsultaEITV.toString() + "_" + numExpedientes[i];

				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipoFichero, subtipo, fecha, nombreFichero, extension);
				if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
					FileInputStream is = new FileInputStream(fileResultBean.getFile());
					nombreFichero = fileResultBean.getFile().getName();
					ZipEntry zipEntry = new ZipEntry(nombreFichero);
					zip.putNextEntry(zipEntry);
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (fileResultBean.getFile().lastModified() > 0) {
						zipEntry.setTime(fileResultBean.getFile().lastModified());
					}
				} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo " + tipo + " para el numero Expediente " + numExpedientes[i] + ". " + fileResultBean.getMessage());
					result.setListaMensajes(listaMensajes);
				} else {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo " + tipo + " para el numero Expediente " + numExpedientes[i]);
					result.setListaMensajes(listaMensajes);
				}
			}
			zip.close();
			File fichero = new File(url);
			result.addAttachment(ResultBean.NOMBRE_FICHERO, tipo + ConstantesGestorFicheros.EXTENSION_ZIP);

			byte1 = gestorDocumentos.transformFiletoByte(fichero);
			if (fichero.delete()) { //TODO: cambiar a java.nio.Files#delete
				log.info("Se ha eliminado correctamente el fichero");
			} else {
				log.info("No se ha eliminado el fichero");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File eliminarZip = new File(url);
			eliminarZip.delete(); //TODO: cambiar a java.nio.Files#delete
		}

		if (byte1 != null) {
			result.setError(false);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}
		return result;
	}

	public ResultBean imprimirConsultaTarjetaEitvPorExpediente(String numExp, String tipoImpresion) {
		ResultBean result = new ResultBean();
		byte[] byte1 = null;
		try {
			Fecha fecha;
			if (numExp.contains("_")) {
				fecha = Utilidades.transformExpedienteFecha(numExp.split("_")[1]);
			} else {
				fecha = Utilidades.transformExpedienteFecha(numExp);
			}
			String nombreFichero = TipoImpreso.ConsultaEITV.toString() + "_" + numExp;
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.PDF_CONSULTA_EITV, fecha, nombreFichero,
					ConstantesGestorFicheros.EXTENSION_PDF);

			if (fileResultBean.getFile() != null) {
				byte1 = gestorDocumentos.transformFiletoByte(fileResultBean.getFile());
				result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero + ConstantesGestorFicheros.EXTENSION_PDF);
			} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo " + tipoImpresion + " para el número Expediente " + numExp + ". " + fileResultBean.getMessage());
				result.setListaMensajes(listaMensajes);
			} else {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo " + tipoImpresion + " para el número Expediente " + numExp);
				result.setListaMensajes(listaMensajes);
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en pdf");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en pdf");
			result.setListaMensajes(listaMensajes);
		}

		if (byte1 != null) {
			result.setError(false);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}
		return result;
	}

	// Método que devuelve el zip con los XML eITV en caso de seleccionar más de uno
	public ResultBean imprimirXMLConsultaTarjetaEitvPorExpedientes(String[] numExpedientes, String tipo) {
		ResultBean result = new ResultBean();
		ZipOutputStream zip = null;
		FileOutputStream out = null;
		String url = null;
		String subtipo = ConstantesGestorFicheros.RESPUESTA_EITV;
		String tipoFichero = ConstantesGestorFicheros.MATE;
		String extension = ConstantesGestorFicheros.EXTENSION_XML;
		byte[] byte1 = null;
		try {
			url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + "zip" + System.currentTimeMillis();
			out = new FileOutputStream(url);
			zip = new ZipOutputStream(out);

			for (int i = 0; i < numExpedientes.length; i++) {
				Fecha fecha = null;
				if (numExpedientes[i].contains("_")) {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i].split("_")[1]);
				} else {
					fecha = Utilidades.transformExpedienteFecha(numExpedientes[i]);
				}
				// Mantis 22638 Cambio para que funcione la exportación de varios archivos: CONSULTA_EITV->CONSULTA_EITV_NUEVO
				String nombreFichero = ConstantesGestorFicheros.CONSULTA_EITV_NUEVO + numExpedientes[i];

				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(tipoFichero, subtipo, fecha, nombreFichero, extension);
				if (fileResultBean.getFile() != null && fileResultBean.getFile().exists()) {
					FileInputStream is = new FileInputStream(fileResultBean.getFile());
					nombreFichero = fileResultBean.getFile().getName();
					ZipEntry zipEntry = new ZipEntry(nombreFichero);
					zip.putNextEntry(zipEntry);
					byte[] buffer = new byte[2048];
					int byteCount;
					while (-1 != (byteCount = is.read(buffer))) {
						zip.write(buffer, 0, byteCount);
					}
					zip.closeEntry();
					is.close();
					if (fileResultBean.getFile().lastModified() > 0) {
						zipEntry.setTime(fileResultBean.getFile().lastModified());
					}
				} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo " + tipo + " para el número Expediente " + numExpedientes[i] + ". " + fileResultBean.getMessage());
					result.setListaMensajes(listaMensajes);
				} else {
					result.setError(true);
					List<String> listaMensajes = result.getListaMensajes();
					listaMensajes.add("No se ha podido recuperar el documento tipo " + tipo + " para el número Expediente " + numExpedientes[i]);
					result.setListaMensajes(listaMensajes);
				}
			}
			zip.close();
			File fichero = new File(url);
			result.addAttachment(ResultBean.NOMBRE_FICHERO, tipo + ConstantesGestorFicheros.EXTENSION_ZIP);

			byte1 = gestorDocumentos.transformFiletoByte(fichero);
			if (fichero.delete()) { //TODO: cambiar a java.nio.Files#delete
				log.info("Se ha eliminado correctamente el fichero");
			} else {
				log.info("No se ha eliminado el fichero");
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en zip");
			result.setListaMensajes(listaMensajes);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			File eliminarZip = new File(url);
			eliminarZip.delete(); //TODO: cambiar a java.nio.Files#delete
		}

		if (byte1 != null) {
			result.setError(false);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}
		return result;
	}

	public ResultBean imprimirXMLConsultaTarjetaEitvPorExpediente(String numExp, String tipoImpresion) {
		ResultBean result = new ResultBean();
		byte[] byte1 = null;
		try {
			Fecha fecha;
			if (numExp.contains("_")) {
				fecha = Utilidades.transformExpedienteFecha(numExp.split("_")[1]);
			} else {
				fecha = Utilidades.transformExpedienteFecha(numExp);
			}
			String nombreFichero = ConstantesGestorFicheros.CONSULTA_EITV_NUEVO + numExp;
			FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.MATE, ConstantesGestorFicheros.RESPUESTAEITV, fecha, nombreFichero,
					ConstantesGestorFicheros.EXTENSION_XML);

			if (fileResultBean.getFile() != null) {
				byte1 = gestorDocumentos.transformFiletoByte(fileResultBean.getFile());
				result.addAttachment(ResultBean.NOMBRE_FICHERO, nombreFichero + ConstantesGestorFicheros.EXTENSION_XML);
			} else if (FileResultStatus.ON_DEMAND_FILE.equals(fileResultBean.getStatus())) {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo " + tipoImpresion + " para el número Expediente " + numExp + ". " + fileResultBean.getMessage());
				result.setListaMensajes(listaMensajes);
			} else {
				result.setError(true);
				List<String> listaMensajes = result.getListaMensajes();
				listaMensajes.add("No se ha podido recuperar el documento tipo " + tipoImpresion + " para el número Expediente " + numExp);
				result.setListaMensajes(listaMensajes);
			}
		} catch (FileNotFoundException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en xml");
			result.setListaMensajes(listaMensajes);
		} catch (OegamExcepcion e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add(e.getMessage());
			result.setListaMensajes(listaMensajes);
		} catch (IOException e) {
			log.error(e);
			result.setError(true);
			List<String> listaMensajes = result.getListaMensajes();
			listaMensajes.add("Error al guardar los archivos en xml");
			result.setListaMensajes(listaMensajes);
		}

		if (byte1 != null) {
			result.setError(false);
			result.addAttachment(ResultBean.TIPO_PDF, byte1);
		} else {
			result.setError(true);
		}
		return result;
	}

	//Mantis 24989
	public boolean comprobarDirectivaCeeFinSerie(VehiculoDto vehiculo){
		String reglal8 = "L1e.*";
		String reglal9 = "L2e.*";
		String reglal10 = "L6e.*";
		String reglal3 = "L3e.*";
		String reglal4 = "L4e.*";
		String reglal5 = "L5e.*";
		String reglal7 = "L7e.*";
		String reglaR1 = "R1";
		String reglaR2 = "R2";
		String reglaR3 = "R3";
		String reglaR4 = "R4";
		String reglaS1 = "S1";
		String reglaS2 = "S2";
		String reglaS3 = "S3";
		String reglaS4 = "S4";
		String reglaM1 = "M1";
		String reglaN1 = "N1.*";
		String reglaN2 = "N2";
		String nivelEmisionesEuro6 = gestorPropiedades.valorPropertie("nivel.emisiones.euro6");
		if(vehiculo.getIdDirectivaCee() != null && !vehiculo.getIdDirectivaCee().isEmpty()){
			if(vehiculo.getIdDirectivaCee().matches(reglal3) ||
				vehiculo.getIdDirectivaCee().matches(reglal4) ||
				vehiculo.getIdDirectivaCee().matches(reglal5) ||
				vehiculo.getIdDirectivaCee().matches(reglal7) ||
				vehiculo.getIdDirectivaCee().matches(reglal8) ||
				vehiculo.getIdDirectivaCee().matches(reglal9) ||
				vehiculo.getIdDirectivaCee().matches(reglal10) ||
				vehiculo.getIdDirectivaCee().matches(reglaR1) ||
				vehiculo.getIdDirectivaCee().matches(reglaR2) ||
				vehiculo.getIdDirectivaCee().matches(reglaR3) ||
				vehiculo.getIdDirectivaCee().matches(reglaR4) ||
				vehiculo.getIdDirectivaCee().matches(reglaS1) ||
				vehiculo.getIdDirectivaCee().matches(reglaS2) ||
				vehiculo.getIdDirectivaCee().matches(reglaS3) ||
				vehiculo.getIdDirectivaCee().matches(reglaS4) ||
				vehiculo.getIdDirectivaCee().matches(reglaM1) ||
				vehiculo.getIdDirectivaCee().matches(reglaN1) ||
				vehiculo.getIdDirectivaCee().matches(reglaN2)){

				if(vehiculo.getNivelEmisiones() == null || vehiculo.getNivelEmisiones().isEmpty()){
					return true;
				} else if(vehiculo.getNivelEmisiones().contains("EURO0") ||
							vehiculo.getNivelEmisiones().contains("EURO1") ||
							vehiculo.getNivelEmisiones().contains("EURO2") ||
							vehiculo.getNivelEmisiones().contains("EURO3") ||
							vehiculo.getNivelEmisiones().contains("EURO 0") ||
							vehiculo.getNivelEmisiones().contains("EURO 1") ||
							vehiculo.getNivelEmisiones().contains("EURO 2") ||
							vehiculo.getNivelEmisiones().contains("EURO 3") ||
							vehiculo.getNivelEmisiones().contains("EURO I") ||
							vehiculo.getNivelEmisiones().contains("EURO II") ||
							vehiculo.getNivelEmisiones().contains("EURO III") ||
							vehiculo.getNivelEmisiones().contains("EUROI") ||
							vehiculo.getNivelEmisiones().contains("EUROII") ||
							vehiculo.getNivelEmisiones().contains("EUROIII") ||(
							"SI".equals(nivelEmisionesEuro6) && (vehiculo.getNivelEmisiones().contains("EURO 6B") ||
							vehiculo.getNivelEmisiones().contains("EURO 6C") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D-TEMP") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D-TEMP-EVAP") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D-TEMP-ISC") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D TEMP") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D TEMP EVAP") ||
							vehiculo.getNivelEmisiones().contains("EURO 6D TEMP ISC")))) {
					//Con estos matches buscamos que en caso de encontrar un EUROIV o EUROIV, devuelva false
					String regla1 = "EUROIV.*";
					String regla2 = "EURO IV.*";
					if(!vehiculo.getNivelEmisiones().matches(regla1) && !vehiculo.getNivelEmisiones().matches(regla2)){
						return true;
					}else{
						return false;
					}

					//23-02-2017 Eduardo Puerro. Se añade un filtro más para que a motocicletas
					//les salga también la marca de agua.
				}else if((vehiculo.getTipoVehiculo() != null && !vehiculo.getTipoVehiculo().isEmpty())){
					if ((vehiculo.getTipoVehiculo().equals("90") ||
							vehiculo.getTipoVehiculo().equals("91") ||
							vehiculo.getTipoVehiculo().equals("92") ||
							vehiculo.getTipoVehiculo().equals("54") ||
							vehiculo.getTipoVehiculo().equals("50") ||
							vehiculo.getTipoVehiculo().equals("51")) 
						&& !vehiculo.getNivelEmisiones().contains("EURO")){
							return true;
					}else if((vehiculo.getTipoVehiculo().equals("RH") ||
							vehiculo.getTipoVehiculo().equals("SH"))){
						return true;
					}
				}
			}
		}
		return false;
	}
}