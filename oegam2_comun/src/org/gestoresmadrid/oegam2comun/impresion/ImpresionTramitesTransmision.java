package org.gestoresmadrid.oegam2comun.impresion;

import static trafico.utiles.ConstantesPDF.PERSONA_JURIDICA;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.datosSensibles.model.service.ServicioDatosSensibles;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioEvolucionTramite;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamDocBase.enumerados.DocumentoBaseTipoCarpetaEnum;
import org.gestoresmadrid.oegamDocBase.service.ServicioGestionDocBase;
import org.gestoresmadrid.oegamDocBase.view.bean.DocBaseCarpetaTramiteBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import com.itextpdf.text.pdf.BarcodePDF417;

import escrituras.beans.Direccion;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import hibernate.entities.general.Colegiado;
import net.sf.jasperreports.engine.JRException;
import oegam.constantes.ConstantesPQ;
import trafico.beans.IntervinienteTrafico;
import trafico.beans.TramiteTraficoBajaBean;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.jaxb.matriculacion.TipoServicio;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import trafico.utiles.UtilesConversiones;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.constantes.ConstantesTrafico;
import trafico.utiles.enumerados.TipoImpreso;
import trafico.utiles.enumerados.TipoProvinciaNoObligadoPDF;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.web.OegamExcepcion;

public class ImpresionTramitesTransmision extends ImpresionTramites {

	private static ILoggerOegam log = LoggerOegam.getLogger(ImpresionTramitesTransmision.class);

	private static final String CABECERA = "expediente";

	@Autowired
	ServicioDatosSensibles servicioDatosSensibles;

	@Autowired
	ServicioEvolucionTramite servicioEvolucionTramite;

	@Autowired
	GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired
	ServicioGestionDocBase servicioGestionDocBase;

	@Autowired
	UtilesConversiones utilesConversiones;
	
	@Autowired
	UtilesColegiado utilesColegiado;
	
	private UtilResources utilResources;

	// Tipo impresos: PDF417, Borrado417 y Presentacion Telematica
	public ResultBean impresionGeneralTransmision(String numExpediente, String numColegiado, String tipoImpreso, BigDecimal idContrato, TipoTramiteTrafico tipoTramite, BigDecimal idUsuario)
			throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		PdfMaker pdf = new PdfMaker();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		int[] vectPags = new int[ConstantesPDF._1];

		String nube = null;
		String nube2 = null;
		String mensaje = "";

		Boolean error = false;

		String nifArrendatario = null;
		String nifTransmitente = null;
		String nifAdquiriente = null;
		String nifPoseedor = null;

		ContratoVO contrato = utilesColegiado.getContratoDelColegiado(idContrato);
		// -------------------------------------------------------------------------------------------------

		calcularRuta(tipoImpreso, false);
		vectPags[0] = ConstantesPDF._1;

		// Obtenemos el detalle del trámite
		resultadoMetodo = obtenerDetalleTramite(tipoTramite.getValorEnum(), numExpediente, numColegiado, tipoImpreso);
		if (resultadoMetodo.getError()) {
			return resultadoMetodo;
		}

		// Abro la plantilla del PDF que corresponda
		UtilResources util = new UtilResources();
		String file = null;

		// Si es trade lleva otra plantilla (poseedor en vez de adquiriente)
		if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
			file = getPlantillaPDF417(util);
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
			file = util.getFilePath(getRuta() + TipoImpreso.TransmisionBorradorPDF417.getNombreEnum());
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			file = getPlantillaPDFPresentacionTelematica(util);
		}

		setByte1(pdf.abrirPdf(file));
		Set<String> camposPlantilla = pdf.getAllFields(getByte1());

		procesarCET(camposFormateados);

		if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
			procesarCema(camposFormateados);
			procesarTipoTasa(camposFormateados);
		}

		ResultBean retultTipoTrans = procesarTipoTransferencia(camposFormateados, tipoTramite.getValorEnum(), tipoImpreso, contrato, idUsuario, numColegiado);
		if (retultTipoTrans != null)
			return retultTipoTrans;

		if (!tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			procesarClaseTransferencia(camposFormateados, tipoTramite.getValorEnum(), tipoImpreso);
		}

		procesarModoAdjudicacion(camposFormateados);

		if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			procesarIdFactura(camposFormateados);
		} else {
			if (procesarNumFactura(camposFormateados)) {
				error = true;
				mensaje = "Limitaciones para Impresion PDF 417 de Transmisiones tipo 'FINALIZACION TRAS UNA NOTIFICACION'";
			}
		}

		if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			procesarTasaCambio(camposFormateados, camposPlantilla);
			procesarTasaInforme(camposFormateados, camposPlantilla);
			procesarResultadoTramitabilidad(camposFormateados, camposPlantilla, tipoImpreso);
			procesarResultadoCtit(camposFormateados, camposPlantilla, tipoImpreso);
			nifTransmitente = procesarNifTransmitiente();
			nifAdquiriente = procesarNifAdquiriente();
			nifPoseedor = procesarNifPoseedor();
			nifArrendatario = procesarNifArrendatario();
			procesarCompraVentaAdquirientes(camposFormateados, nifTransmitente, nifAdquiriente, nifPoseedor);
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
			procesarTelematico(camposFormateados, tipoTramite.getValorEnum());
		}

		setByte1(pdf.setCampos(getByte1(), camposFormateados));
		if (procesarDatosPresentacion(camposPlantilla, camposFormateados, pdf, getDetalleTransmision().getTramiteTraficoBean())) {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		} else {

			if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
				mensaje = procesarTIntervinientesPDFPresentacionTelematica(camposFormateados, camposPlantilla, pdf, nifTransmitente, nifAdquiriente, nifPoseedor, nifArrendatario);
				if (!mensaje.isEmpty()) {
					error = true;
				}
			} else {
				procesarDatosAdquiriente(camposFormateados, camposPlantilla, pdf);
				procesarRepresentanteAdquiriente(camposFormateados, camposPlantilla, pdf);
				procesarTransmitiente(camposFormateados, camposPlantilla, pdf);
				procesarRepresentanteTransmitiente(camposFormateados, camposPlantilla, pdf);
				procesarCompraVenta(camposFormateados, camposPlantilla);
			}

			if (!error && getByte1() != null) {
				if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
					nubePuntos(nube, nube2, pdf, vectPags, contrato);
				}
				setByte1(insertarFirmasColegiadoYColegio(getDetalleTransmision().getTramiteTraficoBean(), getDetalleTransmision().getAdquirienteBean(), getByte1(), vectPags, pdf, contrato));
				if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
					TramiteTraficoTransmisionBean tramiteTrafico = getDetalleTransmision();
					if(tramiteTrafico != null && tramiteTrafico.getResCheckCtit() != null && !tramiteTrafico.getResCheckCtit().isEmpty()){
						camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_ERROR_GEST, tramiteTrafico.getResCheckCtit(), false, false, ConstantesPDF._f9));
						setByte1(pdf.setCampos(getByte1(), camposFormateados));
					}
				}
			}

			if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString()) || tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
				setByte1(rutaAgua(pdf, tipoImpreso, vectPags, ConstantesPDF.RUTA_MARCA_AGUA, getRutaPlantillas(), getByte1()));
			}

			if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
				setByte1(etiquetasPlantilla(camposPlantilla, pdf, getByte1(), tipoImpreso, ConstantesPDF.ID_NUBE_PUNTOS, ConstantesPDF.ID_NUBE_PUNTOS_2));
			}
		}

		if (error == true || getByte1() == null) {
			error = true;
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);
		resultadoMetodo.addAttachment("pdf", getByte1());
		return resultadoMetodo;
	}

	private ResultBean impresionListadoBastidoresTransmisionOld(List<String> numExpedientes, String numColegiado, BigDecimal idContrato, String tipoTramite) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		PdfMaker pdf = new PdfMaker();

		setListaTramitesTrans(new ArrayList<TramiteTraficoTransmisionBean>());

		int numExpedientesEscritos = 0;
		// --------------------------------------------------------------------------------

		String tipoTransmision = "TRANSMISIÓN ";
		if (tipoTramite.equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum()))
			tipoTransmision += "ELECTRÓNICA";

		for (String numExpediente : numExpedientes) {
			resultadoMetodo = obtenerDetalleTramite(TipoTramiteTrafico.Transmision.getValorEnum(), numExpediente, numColegiado, TipoImpreso.MatriculacionListadoBastidores.toString());
			if (resultadoMetodo.getError()) {
				return resultadoMetodo;
			}
		}

		TramiteTraficoTransmisionBean tramite = getListaTramitesTrans().get(0);
		boolean mismoTitular = esMismoTitular(tramite);

		calcularRuta(TipoImpreso.MatriculacionListadoBastidores.toString(), mismoTitular);

		byte[] byteFinal = generarPDFListadoBastidores(tramite, pdf, mismoTitular, numExpedientesEscritos, tipoTransmision);

		if (byteFinal != null) {
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byteFinal);
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "listadoMatriculas.pdf");
			resultadoMetodo.setError(false);
		} else {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Error al generar el PDF del listado de Matrículas");
		}

		return resultadoMetodo;
	}

	// Este método a través de una property, imprimirá el listado de matrículas de transmisión del modo antiguo o nuevo.
	public ResultBean impresionListadoBastidoresTransmision(List<String> numExpedientes, String numColegiado, BigDecimal idContrato, String tipoTramite) throws OegamExcepcion, Throwable {

		ResultBean resultadoMetodo = new ResultBean();
		String habilitado = gestorPropiedades.valorPropertie("habilitar.impresion.nuevas.transmisiones");

		if (("SI").equals(habilitado)) {
			resultadoMetodo = impresionListadoBastidoresTransmisionNew(numExpedientes, numColegiado, idContrato, tipoTramite);
		} else {
			resultadoMetodo = impresionListadoBastidoresTransmisionOld(numExpedientes, numColegiado, idContrato, tipoTramite);
		}

		return resultadoMetodo;

	}

	private ResultBean impresionListadoBastidoresTransmisionNew(List<String> numExpedientes, String numColegiado, BigDecimal idContrato, String tipoTramite) throws OegamExcepcion, Throwable {
		ResultBean resultadoMetodo = new ResultBean();
		byte[] byteFinal = null;

		setListaTramitesTrans(new ArrayList<TramiteTraficoTransmisionBean>());

		// --------------------------------------------------------------------------------

		String nombreInforme = gestorPropiedades.valorPropertie("transmision.plantillas.relacionMatriculas");
		calcularRuta(TipoImpreso.MatriculacionListadoBastidores.toString());

		for (String numExpediente : numExpedientes) {
			resultadoMetodo = obtenerDetalleTramite(TipoTramiteTrafico.Transmision.getValorEnum(), numExpediente, numColegiado, TipoImpreso.MatriculacionListadoBastidores.toString());
			if (resultadoMetodo.getError()) {
				return resultadoMetodo;
			}
		}

		String xmlDatos = generaXMLTransimision(getListaTramitesTrans(), CABECERA);

		Map<String, Object> params = obtenerParametros(getListaTramitesTrans());
		if (params == null) {
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Se ha producido un error al generar el PDF del listado de Bastidores");
			return resultadoMetodo;
		}

		ReportExporter re = new ReportExporter();

		try {
			byteFinal = re.generarInforme(getRuta(), nombreInforme, "pdf", xmlDatos, CABECERA, params, null);
		} catch (JRException e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Se ha producido un error al generar el PDF del listado de Bastidores: " + e.getMessage());
			return resultadoMetodo;
		} catch (ParserConfigurationException e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			resultadoMetodo.setError(true);
			resultadoMetodo.setMensaje("Se ha producido un error al generar el PDF del listado de Bastidores: " + e.getMessage());
			return resultadoMetodo;
		}

		if (byteFinal != null) {
			resultadoMetodo.addAttachment(ResultBean.TIPO_PDF, byteFinal);
			resultadoMetodo.addAttachment(ResultBean.NOMBRE_FICHERO, "listadoBastidores.pdf");
			resultadoMetodo.setError(false);
		} else {
			if (!resultadoMetodo.getError()) {
				resultadoMetodo.setError(true);
				resultadoMetodo.setMensaje("Error al generar el PDF del listado de Bastidores");
			}
		}
		return resultadoMetodo;
	}

	public ResultBean impresionModelo430Transmision(String numExpediente, String numColegiado, BigDecimal idContrato, String tipoImpreso, TipoTramiteTrafico tipoTramite) throws OegamExcepcion,
			Throwable {
		ResultBean resultadoMetodo = new ResultBean();

		PdfMaker pdf = new PdfMaker();

		String mensaje = "";

		boolean error = false;
		// --------------------------------------------------------------------------------
		calcularRuta(tipoImpreso, false);

		ContratoVO contrato = utilesColegiado.getContratoDelColegiado(idContrato);

		// Obtenemos el detalle del trámite
		resultadoMetodo = obtenerDetalleTramite(tipoTramite.getValorEnum(), numExpediente, numColegiado, tipoImpreso);
		if (resultadoMetodo.getError()) {
			return resultadoMetodo;
		}

		// Abro la plantilla del PDF que corresponda
		UtilResources util = new UtilResources();
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		String file = util.getFilePath(getRuta());

		setByte1(pdf.abrirPdf(file));

		Set<String> camposPlantilla = pdf.getAllFields(getByte1());

		if (procesarDatosPresentacion(camposPlantilla, camposFormateados, pdf, getDetalleTransmision().getTramiteTraficoBean())) {
			error = true;
			mensaje = "Los datos del trámite están vacíos";
		} else {
			procesarBaseImponible(camposFormateados);
			procesarPorcentajeAdquisicion(camposFormateados);

			setByte1(pdf.setCampos(getByte1(), camposFormateados));

			if (getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo5) {
				mensaje = procesarPoseedor(camposFormateados, camposPlantilla, pdf, procesarNifPoseedor(), getDetalleTransmision().getTipoTransferencia());
				if (!mensaje.isEmpty()) {
					error = true;
				}
			} else {
				mensaje = procesarAdquiriente(camposFormateados, camposPlantilla, pdf, procesarNifAdquiriente(), getDetalleTransmision().getTipoTransferencia());
			}
		}

		procesarTransmitiente(camposFormateados, camposPlantilla, pdf);
		procesarRepresentanteAdquiriente(camposFormateados, camposPlantilla, pdf);
		procesarProvinciaPresentador(camposFormateados, camposPlantilla, pdf,contrato);
		
		
		
		if (error == true || getByte1() == null) {
			error = true;
			mensaje += ". Error al imprimir el PDF";
		}

		resultadoMetodo.setError(error);
		resultadoMetodo.setMensaje(mensaje);

		resultadoMetodo.addAttachment("pdf", getByte1());

		return resultadoMetodo;
	}

	private void calcularRuta(String tipoImpreso, boolean mismoTitular) throws OegamExcepcion {
		String ruta = "";
		String rutaPlantillas = "";

		ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);

		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			if (mismoTitular) {
				ruta += TipoImpreso.ListadoMatriculas1.getNombreEnum();
			} else {
				ruta += TipoImpreso.ListadoMatriculasTrans2.getNombreEnum();
			}
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionModelo430.toString())) {
			ruta += TipoImpreso.TransmisionModelo430.getNombreEnum();
		}

		if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString()) || tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			rutaPlantillas = ruta;
		}

		setRuta(ruta);
		setRutaPlantillas(rutaPlantillas);
	}

	private void procesarCET(ArrayList<CampoPdfBean> camposFormateados) {
		String cet = (null != getDetalleTransmision().getCetItp()) ? getDetalleTransmision().getCetItp() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CET, cet, false, false, ConstantesPDF._12));
	}

	private void procesarCema(ArrayList<CampoPdfBean> camposFormateados) {
		String cema = (null != getDetalleTransmision().getTramiteTraficoBean().getCema()) ? getDetalleTransmision().getTramiteTraficoBean().getCema() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CEMA, cema, false, false, ConstantesPDF._12));
	}

	private void procesarTipoTasa(ArrayList<CampoPdfBean> camposFormateados) {
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TASA, getDetalleTransmision().getTramiteTraficoBean().getTasa().getTipoTasa(), false, false, ConstantesPDF._9));
	}

	private ResultBean procesarTipoTransferencia(ArrayList<CampoPdfBean> camposFormateados, String tipoTramite, String tipoImpreso, ContratoVO contrato, BigDecimal idUsuario, String numColegiado)
			throws OegamExcepcion {
		String tipoTransferencia = (null != getDetalleTransmision().getTipoTransferencia()) ? getDetalleTransmision().getTipoTransferencia().getValorEnum() : "";

		if (!tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())) {
			return procesarTipoTranferenciaPDF417(camposFormateados, tipoTramite, tipoImpreso, tipoTransferencia, contrato, idUsuario, numColegiado);
		} else {
			return procesarTipoTranferenciaTelematico(camposFormateados, tipoTramite, tipoTransferencia);
		}
	}

	private ResultBean procesarTipoTranferenciaPDF417(ArrayList<CampoPdfBean> camposFormateados, String tipoTramite, String tipoImpreso, String tipoTransferencia, ContratoVO contrato,
			BigDecimal idUsuario, String numColegiado) throws OegamExcepcion {
		String transfer_type = "";
		// FIX: Como las transmisiones elctrónicas y no electrónicas pasan por el mismo método
		// se ponen los casos para cada tipo de tramite según el tipo de transmisión (T2 o T7)
		if (tipoTramite.equalsIgnoreCase(TipoTramiteTrafico.Transmision.getValorEnum())) {
			if (tipoTransferencia.equals(TipoTransferencia.tipo1.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_1;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo2.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_4;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo3.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_3;
			}
		} else if (tipoTramite.equalsIgnoreCase(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())) {
			if (tipoTransferencia.equals(TipoTransferencia.tipo1.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_1;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo2.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_2;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo3.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_3;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo4.toString())) {
				transfer_type = ConstantesPDF.ID_TIPO_TRANSMISION_4;
			} else if (tipoTransferencia.equals(TipoTransferencia.tipo5.toString())) {
				ImpresionTramitesBaja imprimirTramitesBajaP = new ImpresionTramitesBaja();
				TramiteTraficoBajaBean tramiteBajaCV = convertirEntregaCVaBaja(getDetalleTransmision());
				try {
					return imprimirTramitesBajaP.impresionGeneralBajaParaTransmisiones(tramiteBajaCV, tipoImpreso, contrato, idUsuario, numColegiado);
				} catch (Exception e) {
					log.error("Error al imprimir tramite de transmision pdf417 ", e);
				}
			}
		}

		if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, transfer_type.toUpperCase(), true, false, ConstantesPDF._8));
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_SOLICITUD, transfer_type, false, false, ConstantesPDF._9));
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, transfer_type.toUpperCase(), true, false, ConstantesPDF._12));
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_SOLICITUD, transfer_type, false, false, ConstantesPDF._7));
		}
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA_2, transfer_type, false, false, ConstantesPDF._9));

		return null;
	}

	private ResultBean procesarTipoTranferenciaTelematico(ArrayList<CampoPdfBean> camposFormateados, String tipoTramite, String tipoTransferencia) throws OegamExcepcion {
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSFERENCIA, tipoTransferencia, false, false, ConstantesPDF._12));
		// Dependiendo del tipo de transferencia, tendremos que rellenar el ID_TIPO_TRANSMISION
		if (tipoTransferencia.equals("1")) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_1, true, false, ConstantesPDF._10));
		} else if (tipoTransferencia.equals("2")) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_2, true, false, ConstantesPDF._10));
		} else if (tipoTransferencia.equals("3")) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_3, true, false, ConstantesPDF._10));
		} else if (tipoTransferencia.equals("4")) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_4, true, false, ConstantesPDF._10));
		} else if (tipoTransferencia.equals("5")) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_TIPO_TRANSMISION, ConstantesPDF.ID_TIPO_TRANSMISION_5, true, false, ConstantesPDF._10));
		}
		return null;
	}

	private TramiteTraficoBajaBean convertirEntregaCVaBaja(TramiteTraficoTransmisionBean detalleTransmision) {
		Direccion dirVehiculo;
		TramiteTraficoBajaBean tramiteTraficoBajaBean = new TramiteTraficoBajaBean();
		tramiteTraficoBajaBean.setAdquiriente(detalleTransmision.getPoseedorBean());
		tramiteTraficoBajaBean.setRepresentanteAdquiriente(detalleTransmision.getRepresentantePoseedorBean());
		tramiteTraficoBajaBean.setTramiteTrafico(detalleTransmision.getTramiteTraficoBean());
		tramiteTraficoBajaBean.setTitular(detalleTransmision.getTransmitenteBean());
		tramiteTraficoBajaBean.setRepresentanteTitular(detalleTransmision.getRepresentanteTransmitenteBean());
		tramiteTraficoBajaBean.setFechaNacimientoTitular(detalleTransmision.getTransmitenteBean().getPersona().getFechaNacimientoBean());
		tramiteTraficoBajaBean.setTasaDuplicado(detalleTransmision.getCodigoTasaInforme());
		tramiteTraficoBajaBean.setMotivoBaja(MotivoBaja.TempT.getValorEnum());
		tramiteTraficoBajaBean.setConsentimientoCambio(detalleTransmision.getConsentimientoCambio());

		// Incluir la direccion del vehiculo si se ha marcado el check de localización.
		if (null == detalleTransmision.getTipoTransferencia() || !detalleTransmision.getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo5.getValorEnum())) {// Antes era tipo 3
			dirVehiculo = detalleTransmision.getAdquirienteBean().getPersona().getDireccion();
		} else {
			dirVehiculo = detalleTransmision.getPoseedorBean().getPersona().getDireccion();
		}

		if ("true".equals(detalleTransmision.getConsentimientoCambio()) && detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean() != null) {
			dirVehiculo = detalleTransmision.getTramiteTraficoBean().getVehiculo().getDireccionBean();
		}

		tramiteTraficoBajaBean.getTramiteTrafico().getVehiculo().setDireccionBean(dirVehiculo);

		tramiteTraficoBajaBean.getTramiteTrafico().getVehiculo().setFechaMatriculacion(detalleTransmision.getTramiteTraficoBean().getVehiculo().getFechaMatriculacion());

		tramiteTraficoBajaBean.setCet(detalleTransmision.getCetItp());

		return tramiteTraficoBajaBean;
	}

	private void procesarClaseTransferencia(ArrayList<CampoPdfBean> camposFormateados, String tipoTramite, String tipoImpreso) throws OegamExcepcion {
		String claseTransferencia = (null != getDetalleTransmision().getTipoTransferencia()) ? getDetalleTransmision().getTipoTransferencia().getNombreEnum() : "";
		// Hacemos comprobación de la clase de transferencia. Sólo para las no telemáticas
		// Esto es debido a los nuevos tipos de CTIT, que al ser no telemáticos no estan los de FULL
		if (claseTransferencia != null && !"".equals(claseTransferencia)) {
			if (tipoTramite.equalsIgnoreCase(TipoTramiteTrafico.Transmision.getValorEnum())) {
				String cTrans = getDetalleTransmision().getTipoTransferencia().getValorEnum();
				if (cTrans.equalsIgnoreCase(TipoTransferencia.tipo1.getValorEnum())) {
					cTrans = TipoTransferencia.tipo1.getNombreEnum();
					claseTransferencia = cTrans;
				} else if (cTrans.equalsIgnoreCase(TipoTransferencia.tipo2.getValorEnum())) {
					cTrans = TipoTransferencia.tipo4.getNombreEnum();
					claseTransferencia = cTrans;
				} else if (cTrans.equalsIgnoreCase(TipoTransferencia.tipo3.getValorEnum())) {
					cTrans = TipoTransferencia.tipo3.getNombreEnum();
					claseTransferencia = cTrans;
				}
			}
		}
		if (tipoImpreso.equals(TipoImpreso.TransmisionPDF417.toString())) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CLASE_TRANSMISION, claseTransferencia, true, false, ConstantesPDF._6));
		} else if (tipoImpreso.equals(TipoImpreso.TransmisionBorradorPDF417.toString())) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CLASE_TRANSMISION, claseTransferencia, false, false, ConstantesPDF._9));
		}
	}

	private void procesarModoAdjudicacion(ArrayList<CampoPdfBean> camposFormateados) {
		String modoAdjudicacion = (null != getDetalleTransmision().getModoAdjudicacion()) ? getDetalleTransmision().getModoAdjudicacion().getNombreEnum() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MODO_ADJUDICACION, modoAdjudicacion, false, false, ConstantesPDF._9));
	}

	private boolean procesarNumFactura(ArrayList<CampoPdfBean> camposFormateados) {
		String numFactura = (null != getDetalleTransmision().getFactura()) ? getDetalleTransmision().getFactura() : "";
		String cet = (null != getDetalleTransmision().getCetItp()) ? getDetalleTransmision().getCetItp() : "";
		VehiculoBean vehiculo = getDetalleTransmision().getTramiteTraficoBean().getVehiculo();
		String jefatura  = getDetalleTransmision().getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial();

		/**
		 * No se va adejar imprimir el Pdf 417 en las transimisiones electrónicas si se cumplen todas las siguientes condiciones: 
		 * -Es una Finalización tras una notificación 
		 * -El CET viene vacío, 0 o 00000000 (quitar lo que hay del 620) 
		 * -No hay factura. 
		 * -Servicio del vehículo es distinto de B06
		 **/

		String jefaturas = gestorPropiedades.valorPropertie("jefatura.finalizacion.tras.notificacion");
		if("SI".equals("validaciones.finalizacionTrasNoti") || (StringUtils.isNotBlank(jefaturas) && (!"TODOS".equals(jefaturas) && !jefaturasTipoA(jefaturas, jefatura)))){
			if (getDetalleTransmision().getTipoTransferencia().getValorEnum().equals(TipoTransferencia.tipo2.toString())
					&& (vehiculo.getServicioTraficoBean().getIdServicio() == null || !vehiculo.getServicioTraficoBean().equals(TipoServicio.B_06)) && (numFactura == null || numFactura.isEmpty())
					&& (cet == null || cet.isEmpty() || "00000000".equals(cet))) {
				return true;
			} else{
				return false;
			}
		} else {
			return false;
		}
	}
	
	private boolean jefaturasTipoA(String jefaturas, String jefatura) {
		if (StringUtils.isNotBlank(jefatura)) {
			String[] listaJefaturas = jefaturas.split(",");
			if (listaJefaturas != null && listaJefaturas.length > 0) {
				for (String jef : listaJefaturas) {
					if (jef != null && jef.equals(jefatura)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private void procesarIdFactura(ArrayList<CampoPdfBean> camposFormateados) {
		String numFactura = (null != getDetalleTransmision().getFactura()) ? getDetalleTransmision().getFactura() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_FACTURA, numFactura, false, false, ConstantesPDF._12));
	}

	private void procesarTasaCambio(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla) {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_TASA_CAMBIO)) {
			if (getDetalleTransmision().getCodigoTasaCambioServicio() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TASA_CAMBIO, getDetalleTransmision().getCodigoTasaCambioServicio(), true, false, ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}
		}
	}

	private void procesarTasaInforme(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla) {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_TASA_INFORME)) {
			if (getDetalleTransmision().getCodigoTasaInforme() != null) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_TASA_INFORME, getDetalleTransmision().getCodigoTasaInforme(), true, false, ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}
		}
	}

	private void procesarResultadoTramitabilidad(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, String tipoImpreso) {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD)) {
			if (getDetalleTransmision().getResCheckCtit() != null) {
				if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())
						&& "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("modificar.pdf.transmision"))
						&& (EstadoTramiteTrafico.Finalizado_Telematicamente.equals(getDetalleTransmision().getTramiteTraficoBean().getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
								.equals(getDetalleTransmision().getTramiteTraficoBean().getEstado()))) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD, gestorPropiedades.valorPropertie("pdf.transmision.tramitable"), true, false, ConstantesPDF._12);
				} else {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_TRAMITABILIDAD, getDetalleTransmision().getResCheckCtit(), true, false, ConstantesPDF._12);
				}
				camposFormateados.add(campoAux);
			}
		}
	}

	private void procesarResultadoCtit(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, String tipoImpreso) throws OegamExcepcion {
		CampoPdfBean campoAux = null;
		if (camposPlantilla.contains(ConstantesTrafico.ID_RESULTADO_CTIT)) {
			if (getDetalleTransmision().getTramiteTraficoBean().getRespuesta() != null) {
				String mensajeRespuestaCTIT = "";
				String mensajeOk = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK;
				if (getDetalleTransmision().getTramiteTraficoBean().getRespuesta().equals(mensajeOk)) {
					mensajeRespuestaCTIT += mensajeOk;
					if (!"true".equals(getDetalleTransmision().getImpresionPermiso())) {
						String mensajeImpresion = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK_PERMISO_NO_SOLICITADO;
						mensajeRespuestaCTIT += ", " + mensajeImpresion;
					} else if ("EMBARGO O PRECINTO ANOTADO. SE REQUIERE INFORME.".equals(getDetalleTransmision().getResCheckCtit())) {
						String mensajeImpresion = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_OK_PRECINTO;
						mensajeRespuestaCTIT += ", " + mensajeImpresion;
					}
				} else {
					String mensajeError = ConstantesMensajes.MENSAJE_CTIT_RESULTADO_ERROR;
					mensajeRespuestaCTIT = mensajeError;
				}
				if (tipoImpreso.equals(TipoImpreso.TransmisionPDFPresentacionTelematica.toString())
						&& "SI".equalsIgnoreCase(gestorPropiedades.valorPropertie("modificar.pdf.transmision"))
						&& (EstadoTramiteTrafico.Finalizado_Telematicamente.equals(getDetalleTransmision().getTramiteTraficoBean().getEstado()) || EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso
								.equals(getDetalleTransmision().getTramiteTraficoBean().getEstado()))) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_CTIT, gestorPropiedades.valorPropertie("pdf.transmision.tramite.ok"), true, false, ConstantesPDF._12);
				} else {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_RESULTADO_CTIT, mensajeRespuestaCTIT, true, false, ConstantesPDF._12);
				}
				camposFormateados.add(campoAux);
			}
		}
	}

	private String procesarNifPoseedor() throws OegamExcepcion {
		String nifPoseedor = null;
		if (getDetalleTransmision().getPoseedorBean() != null && getDetalleTransmision().getPoseedorBean().getPersona() != null) {
			nifPoseedor = getDetalleTransmision().getPoseedorBean().getPersona().getNif();
		}
		return nifPoseedor;
	}

	private String procesarNifAdquiriente() throws OegamExcepcion {
		String nifAdquiriente = null;
		if (getDetalleTransmision().getAdquirienteBean() != null && getDetalleTransmision().getAdquirienteBean().getPersona() != null) {
			nifAdquiriente = getDetalleTransmision().getAdquirienteBean().getPersona().getNif();
		}
		return nifAdquiriente;
	}

	private String procesarNifTransmitiente() throws OegamExcepcion {
		String nifTransmitente = null;
		if (getDetalleTransmision().getTransmitenteBean() != null && getDetalleTransmision().getTransmitenteBean().getPersona() != null) {
			nifTransmitente = getDetalleTransmision().getTransmitenteBean().getPersona().getNif();
		}
		return nifTransmitente;
	}

	private String procesarNifArrendatario() throws OegamExcepcion {
		String nifArrendatario = null;
		if (getDetalleTransmision().getArrendatarioBean() != null && getDetalleTransmision().getArrendatarioBean().getPersona() != null) {
			nifArrendatario = getDetalleTransmision().getArrendatarioBean().getPersona().getNif();
		}
		return nifArrendatario;
	}

	private void procesarCompraVentaAdquirientes(ArrayList<CampoPdfBean> camposFormateados, String nifTransmitente, String nifAdquiriente, String nifPoseedor) throws OegamExcepcion {
		if (nifAdquiriente != null && nifPoseedor != null && nifAdquiriente.equals(nifPoseedor)) {
			camposFormateados.add(new CampoPdfBean(ConstantesTrafico.ID_COMPRAVENTA_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		}
		if (nifTransmitente != null && nifPoseedor != null && nifTransmitente.equals(nifPoseedor)) {
			camposFormateados.add(new CampoPdfBean(ConstantesTrafico.ID_COMPRAVENTA_TRANSMITENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		}
		// Si es 'trade', el adquiriente (poseedor) siempre es compraventa:
		if (getDetalleTransmision().getTipoTransferencia() != null && getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo5) {
			camposFormateados.add(new CampoPdfBean(ConstantesTrafico.ID_COMPRAVENTA_ADQUIRIENTE, ConstantesPDF.VALOR_SI, true, false, ConstantesPDF._6));
		}
	}

	private String procesarTIntervinientesPDFPresentacionTelematica(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf, String nifTransmitente,
			String nifAdquiriente, String nifPoseedor, String nifArrendatario) throws OegamExcepcion {
		String mensaje = "";
		TipoTransferencia tipoTransferenciaObj = getDetalleTransmision().getTipoTransferencia();

		mensaje = procesarTransmitientePDFPresentacionTelematica(camposFormateados, camposPlantilla, pdf, nifTransmitente);
		mensaje = procesarAdquiriente(camposFormateados, camposPlantilla, pdf, nifAdquiriente, tipoTransferenciaObj);
		mensaje = procesarPoseedor(camposFormateados, camposPlantilla, pdf, nifPoseedor, tipoTransferenciaObj);
		mensaje = procesarArrendatarioPDFPresentacionTelematica(camposFormateados, camposPlantilla, pdf, nifArrendatario, tipoTransferenciaObj);

		return mensaje;
	}

	private String procesarTransmitientePDFPresentacionTelematica(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf, String nifTransmitente) throws OegamExcepcion {
		String mensaje = "";
		CampoPdfBean campoAux = null;

		// Datos transmitente
		if (nifTransmitente == null) {
			mensaje = "Los datos del transmitente están vacíos";
		} else {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._12, getDetalleTransmision().getTransmitenteBean(), ConstantesPDF._TRANSMITENTE, camposPlantilla));
			if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_TRANSMITENTE)) {
				if (null != getDetalleTransmision().getRepresentanteTransmitenteBean() && null != getDetalleTransmision().getRepresentanteTransmitenteBean().getPersona()
						&& null != getDetalleTransmision().getRepresentanteTransmitenteBean().getPersona().getNif()) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_TRANSMITENTE, getDetalleTransmision().getRepresentanteTransmitenteBean().getPersona().getNif(), false, false,
							ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
			}
			if (null != getDetalleTransmision().getPrimerCotitularTransmitenteBean() && null != getDetalleTransmision().getPrimerCotitularTransmitenteBean().getPersona()
					&& null != getDetalleTransmision().getPrimerCotitularTransmitenteBean().getPersona().getNif()
					&& !"".equals(getDetalleTransmision().getPrimerCotitularTransmitenteBean().getPersona().getNif())) {
				Persona primerCotitular = getDetalleTransmision().getPrimerCotitularTransmitenteBean().getPersona();
				if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR)) {
					String nombreApellidos = (null != primerCotitular.getNombre() ? primerCotitular.getNombre() : "") + " "
							+ (null != primerCotitular.getApellido1RazonSocial() ? primerCotitular.getApellido1RazonSocial() : "") + " "
							+ (null != primerCotitular.getApellido2() ? primerCotitular.getApellido2() : "");
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_PRIMER_COTITULAR, nombreApellidos, false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_PRIMER_COTITULAR)) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_PRIMER_COTITULAR, primerCotitular.getNif(), false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_NACIMIENTO_PRIMER_COTITULAR)) {
					if (primerCotitular.getFechaNacimientoBean() != null && !primerCotitular.getFechaNacimientoBean().isfechaNula()) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_NACIMIENTO_PRIMER_COTITULAR, primerCotitular.getFechaNacimientoBean().toString(), false, false, ConstantesPDF._12);
						camposFormateados.add(campoAux);
					}
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_SEXO_PRIMER_COTITULAR)) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEXO_PRIMER_COTITULAR, primerCotitular.getSexo(), false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
			}
			if (null != getDetalleTransmision().getSegundoCotitularTransmitenteBean() && null != getDetalleTransmision().getSegundoCotitularTransmitenteBean().getPersona()
					&& null != getDetalleTransmision().getSegundoCotitularTransmitenteBean().getPersona().getNif()
					&& !"".equals(getDetalleTransmision().getSegundoCotitularTransmitenteBean().getPersona().getNif())) {
				Persona segundoCotitular = getDetalleTransmision().getSegundoCotitularTransmitenteBean().getPersona();
				if (camposPlantilla.contains(ConstantesTrafico.ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR)) {
					String nombreApellidos = (null != segundoCotitular.getNombre() ? segundoCotitular.getNombre() : "") + " "
							+ (null != segundoCotitular.getApellido1RazonSocial() ? segundoCotitular.getApellido1RazonSocial() : "") + " "
							+ (null != segundoCotitular.getApellido2() ? segundoCotitular.getApellido2() : "");
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_NOMBRE_APELLIDOS_SEGUNDO_COTITULAR, nombreApellidos, false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_SEGUNDO_COTITULAR)) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_SEGUNDO_COTITULAR, segundoCotitular.getNif(), false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR)) {
					if (segundoCotitular.getFechaNacimientoBean() != null && !segundoCotitular.getFechaNacimientoBean().isfechaNula()) {
						campoAux = new CampoPdfBean(ConstantesTrafico.ID_FECHA_NACIMIENTO_SEGUNDO_COTITULAR, segundoCotitular.getFechaNacimientoBean().toString(), false, false, ConstantesPDF._12);
						camposFormateados.add(campoAux);
					}
				}
				if (camposPlantilla.contains(ConstantesTrafico.ID_SEXO_SEGUNDO_COTITULAR)) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_SEXO_SEGUNDO_COTITULAR, segundoCotitular.getSexo(), false, false, ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
			}
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
		}
		return mensaje;
	}

	private String procesarAdquiriente(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf, String nifAdquiriente, TipoTransferencia tipoTransferenciaObj)
			throws OegamExcepcion {
		String mensaje = "";
		CampoPdfBean campoAux = null;

		if (nifAdquiriente == null && !(tipoTransferenciaObj == TipoTransferencia.tipo5)) {
			mensaje = "Los datos del adquiriente están vacíos";
		} else {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._12, getDetalleTransmision().getAdquirienteBean(), ConstantesPDF._ADQUIRIENTE, camposPlantilla));

			setByte1(pdf.setCampos(getByte1(), camposFormateados));

			if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_ADQUIRIENTE) && null != getDetalleTransmision().getRepresentanteAdquirienteBean()
					&& null != getDetalleTransmision().getRepresentanteAdquirienteBean().getPersona() && null != getDetalleTransmision().getRepresentanteAdquirienteBean().getPersona().getNif()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_ADQUIRIENTE, getDetalleTransmision().getRepresentanteAdquirienteBean().getPersona().getNif(), false, false,
						ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}
		}
		return mensaje;
	}

	private String procesarPoseedor(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf, String nifPoseedor, TipoTransferencia tipoTransferenciaObj)
			throws OegamExcepcion {
		String mensaje = "";
		CampoPdfBean campoAux = null;

		if (tipoTransferenciaObj == TipoTransferencia.tipo5) {
			if (nifPoseedor == null) {
				mensaje = "Los datos del poseedor están vacíos";
			} else {
				camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._12, getDetalleTransmision().getPoseedorBean(), ConstantesPDF._ADQUIRIENTE, camposPlantilla));

				setByte1(pdf.setCampos(getByte1(), camposFormateados));

				if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_ADQUIRIENTE) && null != getDetalleTransmision().getRepresentantePoseedorBean()
						&& null != getDetalleTransmision().getRepresentantePoseedorBean().getPersona() && null != getDetalleTransmision().getRepresentantePoseedorBean().getPersona().getNif()) {
					campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_ADQUIRIENTE, getDetalleTransmision().getRepresentantePoseedorBean().getPersona().getNif(), false, false,
							ConstantesPDF._12);
					camposFormateados.add(campoAux);
				}
			}
		}
		return mensaje;
	}

	private String procesarArrendatarioPDFPresentacionTelematica(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf, String nifArrendatario,
			TipoTransferencia tipoTransferenciaObj) throws OegamExcepcion {
		String mensaje = "";
		CampoPdfBean campoAux = null;

		if (nifArrendatario != null) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._12, getDetalleTransmision().getArrendatarioBean(), ConstantesPDF._ARRENDATARIO, camposPlantilla));

			setByte1(pdf.setCampos(getByte1(), camposFormateados));

			if (camposPlantilla.contains(ConstantesTrafico.ID_DNI_REPRESENTANTE_ARRENDADOR) && null != getDetalleTransmision().getRepresentanteArrendatarioBean()
					&& null != getDetalleTransmision().getRepresentanteArrendatarioBean().getPersona() && null != getDetalleTransmision().getRepresentanteArrendatarioBean().getPersona().getNif()) {
				campoAux = new CampoPdfBean(ConstantesTrafico.ID_DNI_REPRESENTANTE_ARRENDADOR, getDetalleTransmision().getRepresentanteArrendatarioBean().getPersona().getNif(), false, false,
						ConstantesPDF._12);
				camposFormateados.add(campoAux);
			}
		}
		return mensaje;
	}

	private void procesarTelematico(ArrayList<CampoPdfBean> camposFormateados, String tipoTramite) {
		String telematico = "";

		// FIX: Se pone que compruebe el tipo de transmision que és (T2: TRANS. NO ELECTRÓNICA o T7: TRANS. ELECTRÓNICA).
		// Si es de tipo electrónica, se comprueba si es telemática o no
		if (tipoTramite.equalsIgnoreCase(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())) {// es T7
			if ("TRAMITABLE".equals(getDetalleTransmision().getResCheckCtit())
					&& ("TRÁMITE OK".equals(getDetalleTransmision().getTramiteTraficoBean().getRespuesta()) || getDetalleTransmision().getTramiteTraficoBean().getRespuesta() == null || getDetalleTransmision()
							.getTramiteTraficoBean().getRespuesta().isEmpty())) {
				telematico = ConstantesTrafico.ID_EXP_TELEMATICO;
			} else {
				telematico = ConstantesTrafico.ID_EXP_NO_TELEMATICO;
			}
		}
		camposFormateados.add(new CampoPdfBean(ConstantesTrafico.ID_TELEMATICA, telematico, true, false, ConstantesPDF._8));
	}

	private boolean procesarDatosPresentacion(Set<String> camposPlantilla, ArrayList<CampoPdfBean> camposFormateados, PdfMaker pdf, TramiteTraficoBean tramiteTraficoBean) throws OegamExcepcion {
		if (tramiteTraficoBean == null) {
			return true;
		} else {
			camposFormateados = obtenerValoresCampos(ConstantesPDF._12, camposPlantilla, getDetalleTransmision().getTramiteTraficoBean());
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
			// Si se ha marcado el check de localización del vehiculo se envia la direccion del vehiculo
			if ("TRUE".equals(getDetalleTransmision().getConsentimientoCambio().toUpperCase())) {
				camposFormateados = obtenerValoresDireccionVehiculo(ConstantesPDF._12, camposPlantilla, getDetalleTransmision());
				setByte1(pdf.setCampos(getByte1(), camposFormateados));
			}
			
			if (camposPlantilla.contains(ConstantesTrafico.ID_MES_PRESENTACION)) {
				if (null != tramiteTraficoBean.getFechaPresentacion()
						&& tramiteTraficoBean.getFechaPresentacion().getMes() != null) {
					CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_MES_PRESENTACION,
							utilesFecha.convertirMesNumberToDesc(Integer.valueOf(tramiteTraficoBean.getFechaPresentacion().getMes())),
							false);
					camposFormateados.add(campoAux);
					setByte1(pdf.setCampos(getByte1(), camposFormateados));
				}
			}
			if (camposPlantilla.contains(ConstantesTrafico.ID_MES_CONTRATO430)) {
				if (null != tramiteTraficoBean.getFechaContrato()
						&& tramiteTraficoBean.getFechaContrato().getMes() != null) {
					CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_MES_CONTRATO430,
							utilesFecha.convertirMesNumberToDesc(Integer.valueOf(tramiteTraficoBean.getFechaContrato().getMes())),
							false);
					camposFormateados.add(campoAux);
					setByte1(pdf.setCampos(getByte1(), camposFormateados));
				}
			}
		}
		return false;
	}

	private void procesarDatosAdquiriente(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf) {
		if (getDetalleTransmision().getAdquirienteBean() != null) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getAdquirienteBean(), ConstantesPDF._ADQUIRIENTE, camposPlantilla));
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
		}
	}

	private void procesarRepresentanteAdquiriente(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf) {
		if (getDetalleTransmision().getRepresentanteAdquirienteBean() != null) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getRepresentanteAdquirienteBean(), ConstantesPDF._REPR_ADQUIRIENTE, camposPlantilla));
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
		}
	}

	private void procesarProvinciaPresentador(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf,ContratoVO contrato) {
		if (getDetalleTransmision().getPresentadorBean() != null) {
			if (camposPlantilla.contains(ConstantesTrafico.ID_PROVINCIA_PRESENTADOR)) {
				CampoPdfBean campoAux = new CampoPdfBean(ConstantesTrafico.ID_PROVINCIA_PRESENTADOR, contrato.getProvincia().getNombre(), false, false, ConstantesPDF._11);
				camposFormateados.add(campoAux);
				setByte1(pdf.setCampos(getByte1(), camposFormateados));
			}
		}
	}

	private void procesarTransmitiente(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf) {
		if (getDetalleTransmision().getTransmitenteBean() != null) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getTransmitenteBean(), ConstantesPDF._TRANSMITENTE, camposPlantilla));
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
		}
	}

	private void procesarRepresentanteTransmitiente(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla, PdfMaker pdf) {
		if (getDetalleTransmision().getRepresentanteTransmitenteBean() != null) {
			camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getRepresentanteTransmitenteBean(), ConstantesPDF._REPR_TRANSMITENTE, camposPlantilla));
			setByte1(pdf.setCampos(getByte1(), camposFormateados));
		}
	}

	private void procesarCompraVenta(ArrayList<CampoPdfBean> camposFormateados, Set<String> camposPlantilla) {
		if (null != getDetalleTransmision().getPoseedorBean() && getDetalleTransmision().getPoseedorBean().getPersona().getNif() != null
				&& !getDetalleTransmision().getPoseedorBean().getPersona().getNif().equals("")) {
			// Si es trade los datos del poseedor en la plantilla van en lo que en las demas es adquiriente.
			if (getDetalleTransmision().getTipoTransferencia() != null && getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo5) {
				camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getPoseedorBean(), ConstantesPDF._ADQUIRIENTE, camposPlantilla));
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", true, false, ConstantesPDF._15));
			} else {
				camposFormateados.addAll(obtenerValoresInterviniente(ConstantesPDF._9, getDetalleTransmision().getPoseedorBean(), ConstantesPDF._COMPRAVENTA, camposPlantilla));
			}
			String nifPoseedor = getDetalleTransmision().getPoseedorBean().getPersona().getNif();
			String nifAdquiriente = null;
			String nifTransmitente = null;

			if (getDetalleTransmision().getAdquirienteBean() != null && getDetalleTransmision().getAdquirienteBean().getPersona() != null) {
				nifAdquiriente = getDetalleTransmision().getAdquirienteBean().getPersona().getNif();
			}
			if (getDetalleTransmision().getTransmitenteBean() != null && getDetalleTransmision().getTransmitenteBean().getPersona() != null) {
				nifTransmitente = getDetalleTransmision().getTransmitenteBean().getPersona().getNif();
			}
			if (nifAdquiriente != null && nifPoseedor.equals(nifAdquiriente)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV, "SI", true, false, ConstantesPDF._15));
			}
			if (nifTransmitente != null && nifPoseedor.equals(nifTransmitente)) {
				camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_CV2, "SI", true, false, ConstantesPDF._15));
			}
		}
	}

	private byte[] generarPDFListadoBastidores(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, boolean mismoTitular, int numExpedientesEscritos, String tipoTransmision) {
		int numLinea = 0;
		int numPag = 0;
		float xPosInicial = ConstantesPDF._f73;
		float xPosFinal = ConstantesPDF._f537;
		float yPos = ConstantesPDF._f610;
		UtilResources util = new UtilResources();
		byte[] byteFinal = null;

		// -------------------------------------------------------------------------------------------------------------------

		String file = util.getFilePath(getRuta());
		int numExpetientesTotales = getListaTramitesTrans().size();
		int numPags = calcularNumPags(numExpetientesTotales);
		setByte1(pdf.abrirPdf(file));
		numPag++;
		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contratoDto = servicioContrato.getContratoDto(tramite.getTramiteTraficoBean().getIdContrato());
		
		//utilidades.web.Contrato contrato = utilesColegiado.getContratoDelColegiado(tramite.getTramiteTraficoBean().getIdContrato());

		procesarCabeceraListadoBastidores(tramite, pdf, contratoDto, numPags, numPag, numExpetientesTotales, tipoTransmision);
		if (mismoTitular) {
			procesarTitularListadoBastidores(tramite, pdf);
		}

		for (TramiteTraficoTransmisionBean tramiteLista : getListaTramitesTrans()) {
			if (numLinea > ConstantesPDF._24) {
				if (numExpedientesEscritos == ConstantesPDF._25) {
					byteFinal = getByte1();
				} else {
					byteFinal = pdf.concatenarPdf(byteFinal, getByte1());
				}

				numLinea = 0;
				yPos = ConstantesPDF._f612;
				setByte1(pdf.abrirPdf(file));
				numPag++;
				procesarCabeceraListadoBastidores(tramiteLista, pdf, contratoDto, numPags, numPag, numExpetientesTotales, tipoTransmision);
				if (mismoTitular) {
					procesarTitularListadoBastidores(tramiteLista, pdf);
				}
			}

			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._5);

			numExpedientesEscritos++;
			procesarBastidoresListadoBastidores(tramiteLista, pdf, numLinea, numExpedientesEscritos, mismoTitular);

			if (mismoTitular) {
				procesarIntervinientesMismoTitular(tramiteLista, pdf, numLinea);
				procesarRespuestaDGTListadoBastidores(tramiteLista, pdf, numLinea);

				pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
			} else {
				procesarIntervinientes(tramiteLista, pdf, numLinea);

				pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);
			}

			setByte1(pdf.dibujarLinea(getByte1(), ConstantesPDF._1, ConstantesPDF._0_5F, xPosInicial, yPos, xPosFinal, yPos));

			if (mismoTitular) {
				yPos = yPos - ConstantesPDF._18_35F;
			} else {
				yPos = yPos - ConstantesPDF._18_8F;
			}

			numLinea++;
		}

		if (numExpedientesEscritos <= ConstantesPDF._25) {
			byteFinal = getByte1();
		} else {
			byteFinal = pdf.concatenarPdf(byteFinal, getByte1());
		}

		return byteFinal;
	}

	private void procesarCabeceraListadoBastidores(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, ContratoDto contratoDto, int numPags, int numPag, int numExpetientesTotales, String tipoTransmision) {
		pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._10);

		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TOTAL_PAG, Integer.toString(numPags)));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_PAG, Integer.toString(numPag)));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TOTAL_EXPEDIENTES, Integer.toString(numExpetientesTotales)));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_CODIGO_COLEGIADO, tramite.getTramiteTraficoBean().getNumColegiado()));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_GESTORIA, contratoDto.getRazonSocial()));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TRAMITE, tipoTransmision));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NIF_GESTORIA, contratoDto.getCif()));
	}

	private void procesarTitularListadoBastidores(TramiteTraficoTransmisionBean tramite, PdfMaker pdf) {
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NIF, tramite.getTransmitenteBean().getPersona().getNif()));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TITULAR, (null != tramite.getTransmitenteBean().getPersona().getApellido1RazonSocial() ? tramite.getTransmitenteBean().getPersona()
				.getApellido1RazonSocial() : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getApellido2() ? " " + tramite.getTransmitenteBean().getPersona().getApellido2() : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getNombre() ? ", " + tramite.getTransmitenteBean().getPersona().getNombre() : "")));
	}

	private void procesarBastidoresListadoBastidores(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, int numLinea, int numExpedientesEscritos, boolean mismoTitular) {

		if (mismoTitular) {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._6);
		} else {
			pdf.establecerFuente(ConstantesPDF.HELVETICA, false, false, ConstantesPDF._8);
		}

		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NUM + "." + numLinea, Integer.toString(numExpedientesEscritos)));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NUM_EXPEDIENTE + "." + numLinea, tramite.getTramiteTraficoBean().getNumExpediente().toString()));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_FECHA + "." + numLinea, tramite.getTramiteTraficoBean().getFechaPresentacion().toString()));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_BASTIDOR + "." + numLinea, tramite.getTramiteTraficoBean().getVehiculo().getMatricula()));
	}

	private void procesarIntervinientesMismoTitular(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, int numLinea) {
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TIPO_TRANSFERENCIA + "." + numLinea, tramite.getTipoTransferencia().getNombreEnum()));

		String datosComp = (null != tramite.getPoseedorBean().getPersona().getApellido1RazonSocial() ? tramite.getPoseedorBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getPoseedorBean().getPersona().getNombre() ? ", " + tramite.getPoseedorBean().getPersona().getNombre() : "");
		if (datosComp.length() > 27) {
			datosComp = datosComp.substring(0, 24);
			datosComp += "..";
		}

		String datosAd = (null != tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial() ? tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getAdquirienteBean().getPersona().getNombre() ? ", " + tramite.getAdquirienteBean().getPersona().getNombre() : "");
		if (datosAd.length() >= 27) {
			datosAd = datosAd.substring(0, 24);
			datosAd += "..";
		}

		String datosTrans = (null != tramite.getTransmitenteBean().getPersona().getApellido1RazonSocial() ? tramite.getTransmitenteBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getNombre() ? ", " + tramite.getTransmitenteBean().getPersona().getNombre() : "");
		if (datosTrans.length() > 27) {
			datosTrans = datosTrans.substring(0, 24);
			datosTrans += "..";
		}

		if (tramite.getTipoTransferencia().getNombreEnum().equals(TipoTransferencia.tipo5.getNombreEnum())) {
			setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NIF_AD + "." + numLinea,
					(null != tramite.getPoseedorBean().getPersona().getNif() ? tramite.getPoseedorBean().getPersona().getNif() : "")));
			setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_ADQUI + "." + numLinea, datosComp));
		} else {
			setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NIF_AD + "." + numLinea, (null != tramite.getAdquirienteBean().getPersona().getNif() ? tramite.getAdquirienteBean().getPersona()
					.getNif() : "")));
			setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_ADQUI + "." + numLinea, datosAd));
		}
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TRANS + "." + numLinea, datosTrans));
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_NIF_TRANSMITENTE + "." + numLinea, (null != tramite.getTransmitenteBean().getPersona().getNif() ? tramite.getTransmitenteBean().getPersona()
				.getNif() : "")));
	}

	private void procesarIntervinientes(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, int numLinea) {
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_ADQUIRIENTE + "." + numLinea, (null != tramite.getAdquirienteBean().getPersona().getNif() ? "Adquiriente: "
				+ tramite.getAdquirienteBean().getPersona().getNif() + " - " : "")
				+ (null != tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial() ? tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getAdquirienteBean().getPersona().getApellido2() ? " " + tramite.getAdquirienteBean().getPersona().getApellido2() : "")
				+ (null != tramite.getAdquirienteBean().getPersona().getNombre() ? ", " + tramite.getAdquirienteBean().getPersona().getNombre() : "")));

		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_TRANSMITENTE + "." + numLinea, (null != tramite.getTransmitenteBean().getPersona().getNif() ? "Transmitente: "
				+ tramite.getTransmitenteBean().getPersona().getNif() + " - " : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getApellido1RazonSocial() ? tramite.getTransmitenteBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getApellido2() ? " " + tramite.getTransmitenteBean().getPersona().getApellido2() : "")
				+ (null != tramite.getTransmitenteBean().getPersona().getNombre() ? ", " + tramite.getTransmitenteBean().getPersona().getNombre() : "")));

		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_POSEEDOR + "." + numLinea, (null != tramite.getPoseedorBean().getPersona().getNif() ? "Poseedor: AA"
				+ tramite.getPoseedorBean().getPersona().getNif() + " - " : "")
				+ (null != tramite.getPoseedorBean().getPersona().getApellido1RazonSocial() ? tramite.getPoseedorBean().getPersona().getApellido1RazonSocial() : "")
				+ (null != tramite.getPoseedorBean().getPersona().getApellido2() ? " " + tramite.getPoseedorBean().getPersona().getApellido2() : "")
				+ (null != tramite.getPoseedorBean().getPersona().getNombre() ? ", " + tramite.getPoseedorBean().getPersona().getNombre() : "")));
	}

	private void procesarBaseImponible(ArrayList<CampoPdfBean> camposFormateados) {
		//Mantis 25415
		/*
		 * Según me comenta Juan Pablo, en el contrato de compra venta debe dejar de
		 * aparecer el campo de "baseimponible620" y en su lugar poner el contenido
		 * del nuevo campo llamado "valor_real".

		   Por lo demás, el funcionamiento seguirá siendo igual. El campo de base
		   imponible dentro de la pestaña 620 se rellenará y se guardará de la misma
		   manera y este nuevo campo sólo afectará al documento de contrato de
		   compraventa y a la exportación e importación de ficheros XML (añadiendo
		   a estos ficheros un campo más). 
		 */
		String baseImponible = (null != getDetalleTransmision().getValorReal()) ? getDetalleTransmision().getValorReal().toString().replace(".", ",") : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_BASE_IMPONIBLE, baseImponible, false, false, ConstantesPDF._9));
	}

	private void procesarPorcentajeAdquisicion(ArrayList<CampoPdfBean> camposFormateados) {
		String porcentajeAdquisicion = (null != getDetalleTransmision().getPorcentajeAdquisicion620()) ? getDetalleTransmision().getPorcentajeAdquisicion620().toString() : "";
		camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_PORCENTAJE_ADQUISICION, porcentajeAdquisicion, false, false, ConstantesPDF._9));
	}

	private void procesarRespuestaDGTListadoBastidores(TramiteTraficoTransmisionBean tramite, PdfMaker pdf, int numLinea) {
		String respuestaDgt;
		if (tramite.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente.getValorEnum())
				|| tramite.getTramiteTraficoBean().getEstado().getValorEnum().equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getValorEnum())) {
			respuestaDgt = Constantes.RESPUESTA_OK;
		} else {
			respuestaDgt = Constantes.RESPUESTA_ERROR;
		}
		setByte1(pdf.setCampo(getByte1(), ConstantesPDF.ID_DGT + "." + numLinea, respuestaDgt));
	}

	private boolean esMismoTitular(TramiteTraficoTransmisionBean tramite) {
		boolean mismoTitular = true;
		String dniTitular = null != tramite.getTransmitenteBean().getPersona().getNif() ? tramite.getTransmitenteBean().getPersona().getNif() : "";

		for (TramiteTraficoTransmisionBean linea : getListaTramitesTrans()) {
			if (null == linea.getTransmitenteBean().getPersona().getNif() || !linea.getTransmitenteBean().getPersona().getNif().equals(dniTitular)) {
				mismoTitular = false;
				break;
			}
		}
		return mismoTitular;
	}

	private String getPlantillaPDF417(UtilResources util) {
		String file = null;
		if (getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo5) {
			file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDF417Trade.getNombreEnum());
		} else {
			file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDF417.getNombreEnum());
		}
		return file;
	}

	private String getPlantillaPDFPresentacionTelematica(UtilResources util) {
		String file = null;
		if (getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo1 || getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo2
				|| getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo3 || getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo4) {

			// Se pone plantilla por defecto vacía para los que no son Trade
			file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematica.getNombreEnum());

			// Dependiendo del ITP, se debera coger una plantilla u otra.
			// Exento itp: SI - NoSujetoItp: NO
			if (!getDetalleTransmision().getExentoItp().equals("false") && getDetalleTransmision().getNoSujetoItp().equals("false")) {
				// Mayor 10 años: SI
				if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())) {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaExentoNoObligadoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaExentoNoObligado.getNombreEnum());
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaExentoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaExento.getNombreEnum());
					}
				}
			}
			// Exento itp: NO - NoSujetoItp: SI
			if (!getDetalleTransmision().getNoSujetoItp().equals("false") && getDetalleTransmision().getExentoItp().equals("false")) {
				// Mayor 10 años: SI
				if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())) {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoNoObligadoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoNoObligado.getNombreEnum());
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujetoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoSujeto.getNombreEnum());
					}
				}
			}
			// Mayor 10 años: SI - Exento itp: NO - NoSujetoItp: NO
			if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())
					&& getDetalleTransmision().getExentoItp().equals("false") && getDetalleTransmision().getNoSujetoItp().equals("false")) {
				// Autonomo: SI
				if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
					file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoObligadoConAutonomo.getNombreEnum());
					// Autonomo: NO
				} else {
					file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaNoObligado.getNombreEnum());
				}
			}
			// Autonomo: SI - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")
					&& !getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && getDetalleTransmision().getExentoItp().equals("false")
					&& getDetalleTransmision().getNoSujetoItp().equals("false")) {
				file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematicaConAutonomo.getNombreEnum());
			}
			// Autonomo: NO - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (!getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")
					&& !getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && getDetalleTransmision().getExentoItp().equals("false")
					&& getDetalleTransmision().getNoSujetoItp().equals("false")) {
				file = util.getFilePath(getRuta() + TipoImpreso.TransmisionPDFPresentacionTelematica.getNombreEnum());
			}
		} else if (getDetalleTransmision().getTipoTransferencia() == TipoTransferencia.tipo5) {
			// Dependiendo del ITP, se debera coger una plantilla u otra.
			// Por defecto se le pone que sea la plantilla sin marcas de agua. En caso de cumplir alguna condición se le cambia la plantilla (marcas de agua).
			file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematica.getNombreEnum());

			// Exento itp: SI - NoSujetoItp: NO
			if (!getDetalleTransmision().getExentoItp().equals("false") && getDetalleTransmision().getNoSujetoItp().equals("false")) {
				// Mayor 10 años: SI
				if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())) {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaExentoNoObligadoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaExentoNoObligado.getNombreEnum());
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaExentoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaExento.getNombreEnum());
					}
				}
			}
			// Exento itp: NO - NoSujetoItp: SI
			if (!getDetalleTransmision().getNoSujetoItp().equals("false") && getDetalleTransmision().getExentoItp().equals("false")) {
				// Mayor 10 años: SI
				if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())) {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoNoObligadoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoNoObligado.getNombreEnum());
					}
					// Mayor 10 años: NO
				} else {
					// Autonomo: SI
					if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoSujetoConAutonomo.getNombreEnum());
						// Autonomo: NO
					} else {
						file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoSujeto.getNombreEnum());
					}
				}
			}
			// Mayor 10 años: SI - Exento itp: NO - NoSujetoItp: NO
			if (getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && TipoProvinciaNoObligadoPDF.comprobarCCAA(getDetalleTransmision())
					&& getDetalleTransmision().getExentoItp().equals("false") && getDetalleTransmision().getNoSujetoItp().equals("false")) {
				// Autonomo: SI
				if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")) {
					file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoObligadoConAutonomo.getNombreEnum());
					// Autonomo: NO
				} else {
					file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaNoObligado.getNombreEnum());
				}
			}
			// Autonomo: SI - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")
					&& !getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && getDetalleTransmision().getExentoItp().equals("false")
					&& getDetalleTransmision().getNoSujetoItp().equals("false")) {
				file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematicaConAutonomo.getNombreEnum());
			}
			// Autonomo: NO - Mayor 10 años: NO - Exento itp: NO - NoSujetoItp: NO
			if (!getDetalleTransmision().getTransmitenteBean().getAutonomo().equals("true")
					&& !getModeloTransmision().vehiculoMayorDe10Anios(getDetalleTransmision().getTramiteTraficoBean().getVehiculo()) && getDetalleTransmision().getExentoItp().equals("false")
					&& getDetalleTransmision().getNoSujetoItp().equals("false")) {
				file = util.getFilePath(getRuta() + TipoImpreso.TransmisionTradePresentacionTelematica.getNombreEnum());
			}
		}
		return file;
	}
	
	public ResultBean imprimirTransmisionMandatos(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato, 
			BigDecimal idUsuario, String interviniente, String tipoImpreso)
			throws OegamExcepcion {
		TramiteTraficoTransmisionBean detalleTransmision = new TramiteTraficoTransmisionBean(true);
		ResultBean result = null;

		// Obtenemos el detalle del trámite
		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalleConDescripciones(numExpediente);
		result = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

		if (result.getError()) {
			return result;
		}

		if (detalleTransmision != null) {
			result = imprimirMandato(numExpediente, idUsuario, detalleTransmision, tipoImpreso, interviniente);
		} else {
			result = new ResultBean(true, "Los datos del trámite están vacíos. Puede que no esté completo el trámite impreso.");
		}
		return result;
	}

	private ResultBean imprimirMandato(BigDecimal numExpediente, BigDecimal idUsuario, TramiteTraficoTransmisionBean detalleTransmision, String tipoImpreso, String interviniente) {
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		ruta += tipoImpreso;
		ResultBean result = null;
		// -- Inicio comprobación de datos sensibles
		try {
			List<String> expedientesDatosSensibles = servicioDatosSensibles.existenDatosSensiblesPorExpedientes(new BigDecimal[] { numExpediente }, idUsuario);
			if (expedientesDatosSensibles != null && !expedientesDatosSensibles.isEmpty()) {
				for (String expedienteSensible : expedientesDatosSensibles) {
					result = new ResultBean(true);
					result.setMensaje("Se ha recibido un error técnico. No intenten presentar el tramite " + expedienteSensible + ". Les rogamos comuniquen con el Colegio.");
					log.error("El expediente " + expedienteSensible + " ha intentado obtener el mandado, y contiene datos sensibles");
				}
				return result;
			}
			// -- Fin de comprobación de datos sensibles

			IntervinienteTrafico tipoRepresenta = new IntervinienteTrafico();
			IntervinienteTrafico tipoRepresentanteRepresenta = new IntervinienteTrafico();

			if (interviniente.equals("Transmitente")) {
				tipoRepresenta = detalleTransmision.getTransmitenteBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteTransmitenteBean();
			} else if (interviniente.equals("Adquiriente")) {
				tipoRepresenta = detalleTransmision.getAdquirienteBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteAdquirienteBean();
			} else if (interviniente.equals("Compraventa")) {
				tipoRepresenta = detalleTransmision.getPoseedorBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentantePoseedorBean();
			}

			result = comprobarTipoRepresenta(tipoRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			result = comprobarTipoRepresentanteRepresenta(tipoRepresenta, tipoRepresentanteRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			byte[] byteLista = null;
			ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
			CampoPdfBean campoAux = new CampoPdfBean();

			String file = getUtilResources().getFilePath(ruta);
			PdfMaker pdfMaker = new PdfMaker();
			byteLista = pdfMaker.abrirPdf(file);

			Set<String> camposPlantilla = pdfMaker.getAllFields(byteLista);

			camposFormateados.addAll(obtenerValoresMandato(ConstantesPDF._11, tipoRepresenta, tipoRepresentanteRepresenta, detalleTransmision.getTramiteTraficoBean().getIdContrato(),camposPlantilla));

			byteLista = pdfMaker.setCampos(byteLista, camposFormateados);

			// Va sin acento por que no la muestra en la plantilla.
			if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_MOTIVO)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_MOTIVO, "TRANSFERENCIA: ", false, false, ConstantesPDF._11);
				camposFormateados.add(campoAux);
			}

			if (camposPlantilla.contains(ConstantesPDF.ID_ASUNTO1_EXPLICACION)) {
				String asunto_valor = "";
				if (detalleTransmision.getTramiteTraficoBean().getVehiculo().getBastidor() != null) {
					asunto_valor = "BAST: " + detalleTransmision.getTramiteTraficoBean().getVehiculo().getBastidor().toString();
				}
				if (detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula() != null) {
					asunto_valor = asunto_valor + "  MAT: " + detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula().toString();
				}
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ASUNTO1_EXPLICACION, asunto_valor, false, false, ConstantesPDF._11);
				camposFormateados.add(campoAux);
			}

			// Genero y formateo las nubes de puntos
			byteLista = pdfMaker.setCampos(byteLista, camposFormateados);
			if (byteLista == null) {
				result = new ResultBean(true);
				result.setMensaje("No se ha cargado la plantilla de Mandato.");
				return result;
			}
			result = new ResultBean(false);
			result.addAttachment("pdf", byteLista);
		} catch (OegamExcepcion e) {
			result = new ResultBean(true);
			result.setMensaje("Se ha producido un error a la hora de imprimir el tramite: " + numExpediente);
			log.error("se ha producido un error a la hora de imprimir el tramite: " + numExpediente + ", error: " + e.getMessage());
		} catch (Exception e) {
			result = new ResultBean(true);
			result.setMensaje("Se ha producido un error a la hora de imprimir el tramite: " + numExpediente);
			log.error("se ha producido un error a la hora de imprimir el tramite: " + numExpediente + ", error: " + e.getMessage());
		}
		return result;
	}

	
	public ResultBean imprimirDeclaracionJurada(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato,
			BigDecimal idUsuario, String interviniente, String tipoImpreso) {
		
		TramiteTraficoTransmisionBean detalleTransmision = new TramiteTraficoTransmisionBean(true);
		ResultBean result = null;

		// Obtenemos el detalle del trámite
		Map<String, Object> resultadoDetalle = getModeloTransmision().obtenerDetalleConDescripciones(numExpediente);
		result = (ResultBean) resultadoDetalle.get(ConstantesPQ.RESULTBEAN);
		detalleTransmision = (TramiteTraficoTransmisionBean) resultadoDetalle.get(ConstantesPQ.BEANPANTALLA);

		if (result.getError()) {
			return result;
		}

		if (detalleTransmision != null) {
			result = imprimirDeclaracion(numExpediente, idUsuario, detalleTransmision, tipoImpreso, interviniente);
		} else {
			result = new ResultBean(true, "Los datos del trámite están vacíos. Puede que no esté completo el trámite impreso.");
		}
		return result;
	
	}
	
	private ResultBean imprimirDeclaracion(BigDecimal numExpediente, BigDecimal idUsuario, TramiteTraficoTransmisionBean detalleTransmision, String tipoImpreso, String interviniente) {
		String ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		ruta += tipoImpreso;
		ResultBean result = null;
		// -- Inicio comprobación de datos sensibles
		try {
			IntervinienteTrafico tipoRepresenta = new IntervinienteTrafico();
			IntervinienteTrafico tipoRepresentanteRepresenta = new IntervinienteTrafico();

			if (interviniente.equals("Transmitente")) {
				tipoRepresenta = detalleTransmision.getTransmitenteBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteTransmitenteBean();
			} else if (interviniente.equals("Adquiriente")) {
				tipoRepresenta = detalleTransmision.getAdquirienteBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentanteAdquirienteBean();
			} else if (interviniente.equals("Compraventa")) {
				tipoRepresenta = detalleTransmision.getPoseedorBean();
				tipoRepresentanteRepresenta = detalleTransmision.getRepresentantePoseedorBean();
			}else if (interviniente.equals("Presentador")) {
				tipoRepresenta = detalleTransmision.getPresentadorBean();
			}
			
			
			
			result = comprobarTipoRepresenta(tipoRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			result = comprobarTipoRepresentanteRepresenta(tipoRepresenta, tipoRepresentanteRepresenta, interviniente);
			if (result != null) {
				return result;
			}

			byte[] byteLista = null;
			ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();

			String file = getUtilResources().getFilePath(ruta);
			PdfMaker pdfMaker = new PdfMaker();
			byteLista = pdfMaker.abrirPdf(file);

			Set<String> camposPlantilla = pdfMaker.getAllFields(byteLista);

			camposFormateados.addAll(obtenerValoresDeclaracionJurada(ConstantesPDF._11, tipoRepresenta, tipoRepresentanteRepresenta, detalleTransmision,camposPlantilla));

			byteLista = pdfMaker.setCampos(byteLista, camposFormateados);

			// Genero y formateo las nubes de puntos
			byteLista = pdfMaker.setCampos(byteLista, camposFormateados);
			if (byteLista == null) {
				result = new ResultBean(true);
				result.setMensaje("No se ha cargado la plantilla de la declaracion.");
				return result;
			}
			result = new ResultBean(false);
			result.addAttachment("pdf", byteLista);
			
		} catch (Exception e) {
			result = new ResultBean(true);
			result.setMensaje("Se ha producido un error a la hora de imprimir el tramite: " + numExpediente);
			log.error("se ha producido un error a la hora de imprimir el tramite: " + numExpediente + ", error: " ,e);
		}
		return result;
			
	}
	
	private Collection<? extends CampoPdfBean> obtenerValoresDeclaracionJurada(Integer tamCampos,IntervinienteTrafico titular,IntervinienteTrafico representante,
			TramiteTraficoTransmisionBean detalleTransmision, Set<String> camposPlantilla){
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		CampoPdfBean campoAux = null;
		
		Persona personaTitular = titular.getPersona();		
		String provincia = "";
				
		// Si es persona juridica se cogen los datos del representante del transmitiente y como entidad la razon social del transmitente.
		if (null != personaTitular && null != personaTitular.getSexo()
				&& personaTitular.getSexo().equals(PERSONA_JURIDICA)) {
			// Declara entidad
			if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {				
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ENTIDAD,
						personaTitular.getApellido1RazonSocial(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}	
			
			//Datos de la persona fisca
			Persona personaRepresentante = representante.getPersona();		
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
				String nombreApellidos = (null != personaRepresentante
						.getNombre() ? personaRepresentante.getNombre() : "")
						+ " "
						+ (null != personaRepresentante
								.getApellido1RazonSocial() ? personaRepresentante
								.getApellido1RazonSocial()
								: "")
						+ " "
						+ (null != personaRepresentante.getApellido2() ? personaRepresentante
								.getApellido2()
								: "");
				
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF,personaRepresentante.getNif(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}	
				
		} else { // Si la persona es fisica se cogen los datos del transmitente  y el campo entidad deberá ir vacío.
			// Declara entidad
			if (camposPlantilla.contains(ConstantesPDF.ID_ENTIDAD)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_ENTIDAD, "", false, false, tamCampos);
				camposFormateados.add(campoAux);
			}
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NOMBRE_GESTOR)) {
				String nombreApellidos = (null != personaTitular
						.getNombre() ? personaTitular.getNombre() : "")
						+ " "
						+ (null != personaTitular
								.getApellido1RazonSocial() ? personaTitular
								.getApellido1RazonSocial()
								: "")
						+ " "
						+ (null != personaTitular.getApellido2() ? personaTitular
								.getApellido2()
								: "");
				
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NOMBRE_GESTOR, nombreApellidos, false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}
			
			if (camposPlantilla.contains(ConstantesPDF.ID_NIF)) {
				campoAux = new CampoPdfBean(ConstantesPDF.ID_NIF,personaTitular.getNif(), false, false,
						tamCampos);
				camposFormateados.add(campoAux);
			}					
		}
		
		//La dirección se debe coger de los datos del transmitente tanto para personas juridicas como fisica 
		// porque en los datos del representante no viene la direccion
		String nombreVia = utilesConversiones.ajustarCamposIne(personaTitular.getDireccion().getNombreVia());			
		String municipio="";
		// Picar la dirección si tiene más de 13 caractéres
		if (nombreVia != null && !nombreVia.isEmpty()) {
			municipio =  (null !=personaTitular.getDireccion().getMunicipio().getNombre() ? ", " + personaTitular.getDireccion().getMunicipio().getNombre() : "");
		} 
		
		if (camposPlantilla.contains(ConstantesPDF.ID_CALLE)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CALLE, nombreVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		if (camposPlantilla.contains(ConstantesPDF.ID_MUNICIPIO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MUNICIPIO,
					municipio, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}					

		if (camposPlantilla.contains(ConstantesPDF.ID_PROVINCIA)) {
			provincia = personaTitular.getDireccion().getMunicipio().getProvincia()
					.getNombre();
			campoAux = new CampoPdfBean(ConstantesPDF.ID_PROVINCIA,provincia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}

		if (camposPlantilla.contains(ConstantesPDF.ID_NUMERO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_NUMERO,
					personaTitular.getDireccion().getNumero(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}			

		if (camposPlantilla.contains(ConstantesPDF.ID_CODIGO)) {
			campoAux = new CampoPdfBean(ConstantesPDF.ID_CODIGO,
					personaTitular.getDireccion().getCodPostal(), false, false,
					tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla
				.contains(ConstantesPDF.ID_DOMICILIO_ENTIDAD)) {
			campoAux = new CampoPdfBean(
					ConstantesPDF.ID_DOMICILIO_ENTIDAD,
					nombreVia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}		
		
		if (detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula() != null) {
			camposFormateados.add(new CampoPdfBean(ConstantesPDF.ID_MATRICULA,detalleTransmision.getTramiteTraficoBean().getVehiculo().getMatricula().toString(), false, false, ConstantesPDF._11));
		}
		
		//Firma
		// Obtener una fecha a partir de su representación
		DateTime dt = new DateTime();	
		String dia =  dt.toString("dd");
		String mes =  dt.toString("MMMM").toUpperCase();
		String anio =  dt.toString("yyyy");
		
		
		if (camposPlantilla.contains(ConstantesPDF.ID_LUGAR)) {					
			campoAux = new CampoPdfBean(ConstantesPDF.ID_LUGAR, provincia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_DIA)) {					
			campoAux = new CampoPdfBean(ConstantesPDF.ID_DIA, dia, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_MES)) {					
			campoAux = new CampoPdfBean(ConstantesPDF.ID_MES, mes, false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		if (camposPlantilla.contains(ConstantesPDF.ID_ANIO)) {					
			campoAux = new CampoPdfBean(ConstantesPDF.ID_ANIO,anio , false, false, tamCampos);
			camposFormateados.add(campoAux);
		}
		
		return camposFormateados;		
	}
	
	private ResultBean comprobarTipoRepresentanteRepresenta(IntervinienteTrafico tipoRepresenta, IntervinienteTrafico tipoRepresentanteRepresenta, String interviniente) {
		ResultBean resultado = null;
		if ("X".equalsIgnoreCase(tipoRepresenta.getPersona().getSexo())) {
			// El interviniente es persona jurídica. Verifica los datos mínimos de su representante:
			if (tipoRepresentanteRepresenta == null || tipoRepresentanteRepresenta.getPersona() == null || tipoRepresentanteRepresenta.getPersona().getNif() == null
					|| tipoRepresentanteRepresenta.getPersona().getNif().isEmpty() || tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial() == null
					|| tipoRepresentanteRepresenta.getPersona().getApellido1RazonSocial().isEmpty()){
				resultado = new ResultBean(true);
				resultado.setMensaje("No se han recuperado los datos mínimos requeridos del " + " representante del " + interviniente);
			}
		}
		return resultado;
	}

	private ResultBean comprobarTipoRepresenta(IntervinienteTrafico tipoRepresenta, String interviniente) {
		ResultBean resultado = null;
		if (tipoRepresenta == null || tipoRepresenta.getPersona() == null || tipoRepresenta.getPersona().getNif() == null || tipoRepresenta.getPersona().getNif().isEmpty()
				|| tipoRepresenta.getPersona().getApellido1RazonSocial() == null || tipoRepresenta.getPersona().getApellido1RazonSocial().isEmpty()){
			resultado = new ResultBean(true);
			resultado.setMensaje("No se han recuperado los datos mínimos requeridos del " + interviniente);
		}
		return resultado;
	}

	/**
	 * Dada la lista de tramites, obetenemos el xml que usamos para generar el informe
	 * @param listaTramites
	 * @return xml de datos
	 */
	private String generaXMLTransimision(ArrayList<TramiteTraficoTransmisionBean> listaTramites, String cabecera) {
		String xml = "";

		if (!listaTramites.isEmpty()) {

			xml = "<?xml version='1.0' encoding='UTF-8' standalone='no' ?> <" + cabecera + "s>";

			for (int i = 0; i < listaTramites.size(); i++) {

				TramiteTraficoTransmisionBean tramite = listaTramites.get(i);
				xml = xml + "<" + cabecera + ">";

				// numero expediente
				xml = xml + "<expediente>";
				if (tramite.getTramiteTraficoBean().getNumExpediente() != null) {
					xml = xml + tramite.getTramiteTraficoBean().getNumExpediente();
				}
				xml = xml + "</expediente>";

				// matricula
				xml = xml + "<matricula>";
				if (tramite.getTramiteTraficoBean().getVehiculo().getMatricula() != null) {
					xml = xml + tramite.getTramiteTraficoBean().getVehiculo().getMatricula();
				}
				xml = xml + "</matricula>";

				// tasa
				xml = xml + "<tasa>";
				if (tramite.getTramiteTraficoBean().getTasa() != null) {
					if (tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() != null) {
						xml = xml + tramite.getTramiteTraficoBean().getTasa().getCodigoTasa();
					}
				}
				xml = xml + "</tasa>";

				// nif
				xml = xml + "<nif>";
				if(!TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())){
					if (tramite.getAdquirienteBean() != null && tramite.getAdquirienteBean().getPersona() != null) {
						if (tramite.getAdquirienteBean().getPersona().getNif() != null) {
							xml = xml + tramite.getAdquirienteBean().getPersona().getNif();
						}
					}
				} else {
					if (tramite.getPoseedorBean() != null && tramite.getPoseedorBean().getPersona() != null) {
						if (tramite.getPoseedorBean().getPersona().getNif() != null) {
							xml = xml + tramite.getPoseedorBean().getPersona().getNif();
						}
					}
				}
				xml = xml + "</nif>";

				// titular
				xml = xml + "<titular>";
				String nomApp = "";
				if(!TipoTransferencia.tipo5.getValorEnum().equals(tramite.getTipoTransferencia().getValorEnum())){
					if (tramite.getAdquirienteBean() != null && tramite.getAdquirienteBean().getPersona() != null) {
						if (tramite.getAdquirienteBean().getPersona().getNombre() != null) {
							nomApp = tramite.getAdquirienteBean().getPersona().getNombre();
						}

						if (tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial() != null) {
							nomApp += " " + tramite.getAdquirienteBean().getPersona().getApellido1RazonSocial();
						}

						if (tramite.getAdquirienteBean().getPersona().getApellido2() != null) {
							nomApp += " " + tramite.getAdquirienteBean().getPersona().getApellido2();
						}
						xml = xml + nomApp;
					}
				} else {
					if (tramite.getPoseedorBean() != null && tramite.getPoseedorBean().getPersona() != null) {
						if (tramite.getPoseedorBean().getPersona().getNombre() != null) {
							nomApp = tramite.getPoseedorBean().getPersona().getNombre();
						}

						if (tramite.getPoseedorBean().getPersona().getApellido1RazonSocial() != null) {
							nomApp += " " + tramite.getPoseedorBean().getPersona().getApellido1RazonSocial();
						}

						if (tramite.getPoseedorBean().getPersona().getApellido2() != null) {
							nomApp += " " + tramite.getPoseedorBean().getPersona().getApellido2();
						}
						xml = xml + nomApp;
					}
				}
				xml = xml + "</titular>";

				// permiso
				xml = xml + "<permiso>";
				if (tramite.getImpresionPermiso() != null) {
					String permiso = "NO";
					if (("true").equals(tramite.getImpresionPermiso())) {
						permiso = "SI";
					}
					xml = xml + permiso;
				}
				xml = xml + "</permiso>";

				// factura
				xml = xml + "<factura>";
				String factura = "NO";
				if (tramite.getFactura() != null) {
					factura = "SI";
					xml = xml + factura;
				}
				xml = xml + "</factura>";

				// cambioservicio
				xml = xml + "<cambio_servicio>";
				if (tramite.getCambioServicio() != null) {
					String cambioSer = "NO";
					if (("true").equals(tramite.getCambioServicio())) {
						cambioSer = "SI";
					}
					xml = xml + cambioSer;
				}
				xml = xml + "</cambio_servicio>";

				xml = xml + "</" + cabecera + ">";
			}
			xml = xml + "</" + cabecera + "s>";
		}
		return xml;
	}

	private Map<String, Object> obtenerParametros(ArrayList<TramiteTraficoTransmisionBean> listaTramites) {
		Map<String, Object> params = new HashMap<String, Object>();
		
		DocBaseCarpetaTramiteBean docBaseCarpetaTramiteBean = servicioGestionDocBase.getDocBaseParaTramite(listaTramites.get(0).getTramiteTraficoBean().getNumExpediente());
		
		String codRelacion =  docBaseCarpetaTramiteBean.getTipoCarpeta();
		String carpeta = DocumentoBaseTipoCarpetaEnum.convertirTexto(docBaseCarpetaTramiteBean.getTipoCarpeta());
		
		String gestor = listaTramites.get(0).getTramiteTraficoBean().getNumColegiado();
		Colegiado col = null;
		try {
			col = utilesColegiado.getColegiado(listaTramites.get(0).getTramiteTraficoBean().getNumColegiado());
			gestor = col != null && col.getUsuario() != null ? gestor + " - " + col.getUsuario().getApellidosNombre() : gestor + "";
		} catch (Throwable t) {
			log.error("Error recuperando usuario", t);
			return null;
		}

		ServicioContrato servicioContrato = ContextoSpring.getInstance().getBean(ServicioContrato.class);
		ContratoDto contratoDto = servicioContrato.getContratoDto(listaTramites.get(0).getTramiteTraficoBean().getIdContrato());
		
		/*utilidades.web.Contrato contrato = utilesColegiado.getContratoDelColegiado(listaTramites.get(0).getTramiteTraficoBean().getIdContrato());

		String gestoria = contrato.get_razon_social();
		String nifGestoria = col.getUsuario().getNif();*/
		String totalExp = listaTramites.size() + "";

		// Contadores para totalCambioServicio, ExpedientesConPermiso, ExpedientesSinpermiso, TotalFacturas
		int camSer = 0;
		int expConPer = 0;
		int expSinPer = 0;
		int facturas = 0;
		for (TramiteTraficoTransmisionBean tramite : listaTramites) {
			if (("true").equals(tramite.getImpresionPermiso())) {
				expConPer++;
			} else {
				expSinPer++;
			}
			if (("true").equals(tramite.getCambioServicio())) {
				camSer++;
			}
			if (tramite.getFactura() != null) {
				facturas++;
			}
		}
		String datosNubePuntos = getDatosNubePuntosListadoBastidores(listaTramites);
		java.awt.Image nubePuntos417;
		try {
			nubePuntos417 = getBarcode(datosNubePuntos);
		} catch (Exception e) {
			log.error("Error al generar el PDF del listado de Bastidores.\n" + e);
			return null;
		}

		params.put("SUBREPORT_DIR", getRuta());
		params.put("IMG_DIR", getRuta());
		params.put("CODIGO_RELACION", codRelacion);
		params.put("CARPETA", carpeta);
		params.put("TIPO_CARPETA", "");
		params.put("ESTADO_CARPETA", "");
		params.put("GESTOR", gestor);
		params.put("GESTORIA", contratoDto.getRazonSocial());
		params.put("NIF_GESTORIA", contratoDto.getCif());
		params.put("TOTAL_FACTURAS", Integer.toString(facturas));
		params.put("TOTAL_EXPEDIENTES", totalExp);
		params.put("TOTAL_CAMBIO_SERVICIO", Integer.toString(camSer));
		params.put("EXPEDIENTES_CON_PERMISO", Integer.toString(expConPer));
		params.put("EXPEDIENTES_SIN_PERMISO", Integer.toString(expSinPer));
		params.put("NUBE_PUNTOS", nubePuntos417);
		params.put("FECHA", listaTramites.get(0).getTramiteTraficoBean().getFechaPresentacion());

		return params;
	}

	private void calcularRuta(String tipoImpreso) throws OegamExcepcion {
		String ruta = "";
		String rutaPlantillas = "";

		if (tipoImpreso.equals(TipoImpreso.MatriculacionListadoBastidores.toString())) {
			ruta = gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie("transmision.plantillas");
		} else {
			ruta = gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS);
		}

		if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417Matw.toString()) || tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString())) {
			rutaPlantillas = ruta;
		}

		if (tipoImpreso.equals(TipoImpreso.MatriculacionBorradorPDF417.toString())) {
			ruta += TipoImpreso.MatriculacionBorradorPDF417Matw.getNombreEnum();
		} else if (tipoImpreso.equals(TipoImpreso.MatriculacionPDF417Matw.toString()) || tipoImpreso.equals(TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.toString())) {
			ruta += TipoImpreso.MatriculacionPDFPresentacionTelematica_MATEW.getNombreEnum();
		}

		setRuta(ruta);
		setRutaPlantillas(rutaPlantillas);
	}

	private String getDatosNubePuntosListadoBastidores(ArrayList<TramiteTraficoTransmisionBean> listaTramites) {
		String nube = "";
		if (!listaTramites.isEmpty()) {
			for (int i = 0; i < listaTramites.size(); i++) {
				TramiteTraficoTransmisionBean tramite = listaTramites.get(i);
				if (i > 0) {
					nube = nube + ";";
				}
				if (tramite.getTramiteTraficoBean().getVehiculo().getMatricula() != null) {
					nube = nube + tramite.getTramiteTraficoBean().getVehiculo().getMatricula();
				}
			}
		}
		return nube;
	}

	private java.awt.Image getBarcode(String datosNubePuntos) throws Exception {
		BarcodePDF417 barcode = new BarcodePDF417();
		barcode.setText(datosNubePuntos);
		barcode.setAspectRatio(.25f);
		return barcode.createAwtImage(Color.BLACK, Color.WHITE);
	}

	public UtilResources getUtilResources() {
		if (utilResources == null) {
			utilResources = new UtilResources();
		}
		return utilResources;
	}

	public void setUtilResources(UtilResources utilResources) {
		this.utilResources = utilResources;
	}

	

	

}