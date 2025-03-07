package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.vehiculo.model.vo.TipoReduccionVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioTipoReduccion;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.Provincia;
import general.beans.RespuestaGenerica;
import trafico.beans.JustificanteProfesional;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarInterviniente;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import trafico.beans.daos.BeanPQTramiteTransmision;
import trafico.beans.daos.JustificanteProfesionalCursor;
import trafico.utiles.enumerados.ContratoFactura;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Component
public class TransmisionTramiteTraficoBeanPQConversion {

	private static final ILoggerOegam log = LoggerOegam.getLogger(TransmisionTramiteTraficoBeanPQConversion.class);
	private static final String P_CONSENTIMIENTO_CAMBIO = "P_CONSENTIMIENTO_CAMBIO";
	private static final String P_EXENTO_ITP = "P_EXENTO_ITP";
	private static final String P_FECHA_DEVENGO_ITP = "P_FECHA_DEVENGO_ITP";
	private static final String P_IMPR_PERMISO_CIRCU = "P_IMPR_PERMISO_CIRCU";
	private static final String P_CONTRATO_FACTURA = "P_CONTRATO_FACTURA";
	private static final String P_VALOR_REAL = "P_VALOR_REAL";
	private static final String P_TIPO_REDUCCION = "P_TIPO_REDUCCION";
	private static final String P_REDUCCION_PORCENTAJE = "P_REDUCCION_PORCENTAJE";
	private static final String P_REDUCCION_IMPORTE = "P_REDUCCION_IMPORTE";
	private static final String P_PROCEDENCIA_620 = "P_PROCEDENCIA_620";
	private static final String P_ES_SINIESTRO = "P_ES_SINIESTRO";
//	private static final String P_TIENE_CARGA_FINANCIERA = "P_TIENE_CARGA_FIANCIERA";
//	private static final String P_CAMBIO_SOCIETARIO = "P_CAMBIO_SOCIETARIO";
//	private static final String P_MODIFICACION_NO_CONSTANTE = "P_MODIFICACION_NO_CONSTANTE";
	private static final String P_ACREDITACION_PAGO = "P_ACREDITACION_PAGO";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String SI = "SI";
	private static final String NO = "NO";
	private static final String S = "S";
	private static final String N = "N";
	private static final String E = "E";
	private static final String CADENA_VACIA = "";

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	VehiculoBeanPQConversion vehiculoBeanPQConversion;

	@Autowired
	IntervinienteTraficoBeanPQConversion intervinienteTraficoBeanPQConversion;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Autowired
	private ServicioTipoReduccion servicioTipoReduccion;

	/*
	 * Método general para convertir un trámite de transmision bean en un beanPQ
	 */
	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return BeanPQTramiteTransmision
	 * 
	 * @ Los atributos P_INFORMACION son siempre devueltos con 'null'
	 */
	public BeanPQTramiteTraficoGuardarTransmision convertirBeanToPQ(TramiteTraficoTransmisionBean tramite) {
		// Controlo que tramite no venga a null; si fuera null, tendría nullPointer Exception al hacer tramite.getX().
		if (tramite == null)
			return null;

		BeanPQTramiteTraficoGuardarTransmision tramitePQ = new BeanPQTramiteTraficoGuardarTransmision();

		// Valores propios del trámite general
		try {
			if (tramite.getTramiteTraficoBean() != null) {
				tramitePQ.setP_ID_USUARIO(null != tramite.getTramiteTraficoBean().getIdUsuario() ? tramite.getTramiteTraficoBean().getIdUsuario() : utilesColegiado.getIdUsuarioSessionBigDecimal());
				tramitePQ.setP_NUM_EXPEDIENTE(null == tramite.getTramiteTraficoBean().getNumExpediente() ? null : tramite.getTramiteTraficoBean().getNumExpediente());
				tramitePQ.setP_ID_CONTRATO(null != tramite.getTramiteTraficoBean().getIdContrato() ? tramite.getTramiteTraficoBean().getIdContrato() : utilesColegiado.getIdContratoSessionBigDecimal());
				tramitePQ.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
				tramitePQ.setP_NUM_COLEGIADO(null == tramite.getTramiteTraficoBean().getNumColegiado() || tramite.getTramiteTraficoBean().getNumColegiado().equals(CADENA_VACIA) ? utilesColegiado.getNumColegiadoSession() : CADENA_VACIA+tramite.getTramiteTraficoBean().getNumColegiado());

				tramitePQ.setP_CODIGO_TASA(tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() != null
						&& !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().equals(CADENA_VACIA)
						&& !tramite.getTramiteTraficoBean().getTasa().getCodigoTasa().equals("-1")
						? tramite.getTramiteTraficoBean().getTasa().getCodigoTasa() : null);

				tramitePQ.setP_TIPO_TASA(tramite.getTramiteTraficoBean().getTasa().getTipoTasa() != null && !tramite.getTramiteTraficoBean().getTasa().getTipoTasa().equals(CADENA_VACIA) && !tramite.getTramiteTraficoBean().getTasa().getTipoTasa().equals("-1") ? tramite.getTramiteTraficoBean().getTasa().getTipoTasa() : null);
				tramitePQ.setP_ESTADO(null);
				if (tramite.getTramiteTraficoBean().getEstado() != null && !tramite.getTramiteTraficoBean().getEstado().getValorEnum().equals("-1")){
					tramitePQ.setP_ESTADO(new BigDecimal (tramite.getTramiteTraficoBean().getEstado().getValorEnum()));
				}
				tramitePQ.setP_REF_PROPIA(tramite.getTramiteTraficoBean().getReferenciaPropia() != null && !tramite.getTramiteTraficoBean().getReferenciaPropia().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getReferenciaPropia() : null);
				tramitePQ.setP_ANOTACIONES(tramite.getTramiteTraficoBean().getAnotaciones() != null && !tramite.getTramiteTraficoBean().getAnotaciones().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getAnotaciones() : null);
				tramitePQ.setP_FECHA_ALTA(null);
				tramitePQ.setP_FECHA_PRESENTACION(null);
				if (tramite.getTramiteTraficoBean().getFechaPresentacion() != null) {
					tramitePQ.setP_FECHA_PRESENTACION(null == tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp() ? null : tramite.getTramiteTraficoBean().getFechaPresentacion().getTimestamp());
				}
				tramitePQ.setP_FECHA_ULT_MODIF(null);
				tramitePQ.setP_FECHA_IMPRESION(null);
				tramitePQ.setP_RENTING(tramite.getTramiteTraficoBean().getRenting() != null && !tramite.getTramiteTraficoBean().getRenting().equals(CADENA_VACIA) ? checkSiNo(tramite.getTramiteTraficoBean().getRenting(), TRUE) : null);
				tramitePQ.setP_IEDTM(tramite.getTramiteTraficoBean().getIedtm() != null && !tramite.getTramiteTraficoBean().getIedtm().equals(CADENA_VACIA) ? (tramite.getTramiteTraficoBean().getIedtm().equals(E) ? E : CADENA_VACIA) : null);
				tramitePQ.setP_MODELO_IEDTM(tramite.getTramiteTraficoBean().getModeloIedtm() != null && !tramite.getTramiteTraficoBean().getModeloIedtm().equals(CADENA_VACIA) && !tramite.getTramiteTraficoBean().getModeloIedtm().equals("-1")?tramite.getTramiteTraficoBean().getModeloIedtm() : null);
				tramitePQ.setP_FECHA_IEDTM(null);
				if (tramite.getTramiteTraficoBean().getFechaIedtm() != null) {
					tramitePQ.setP_FECHA_IEDTM(null == tramite.getTramiteTraficoBean().getFechaIedtm().getTimestamp() ? null : tramite.getTramiteTraficoBean().getFechaIedtm().getTimestamp());
				}
				tramitePQ.setP_N_REG_IEDTM(tramite.getTramiteTraficoBean().getNumRegIedtm()!=null && !tramite.getTramiteTraficoBean().getNumRegIedtm().getValorEnum().equals("-1") ? tramite.getTramiteTraficoBean().getNumRegIedtm().getValorEnum() : null);
				tramitePQ.setP_FINANCIERA_IEDTM(tramite.getTramiteTraficoBean().getFinancieraIedtm() != null && !tramite.getTramiteTraficoBean().getFinancieraIedtm().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getFinancieraIedtm() : null);
				tramitePQ.setP_EXENTO_IEDTM(tramite.getTramiteTraficoBean().getExentoIedtm()!=null && !tramite.getTramiteTraficoBean().getExentoIedtm().equals(CADENA_VACIA) ? checkSiNo(tramite.getTramiteTraficoBean().getExentoIedtm(), TRUE) : null);
				tramitePQ.setP_NO_SUJECION_IEDTM(tramite.getTramiteTraficoBean().getNoSujetoIedtm()!=null && !tramite.getTramiteTraficoBean().getNoSujetoIedtm().equals(CADENA_VACIA) ? checkSiNo(tramite.getTramiteTraficoBean().getNoSujetoIedtm(), TRUE) : null);
				tramitePQ.setP_RESPUESTA(tramite.getTramiteTraficoBean().getRespuesta()!=null && !tramite.getTramiteTraficoBean().getRespuesta().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getRespuesta() : null);
				tramitePQ.setP_RES_CHECK_CTIT(tramite.getResCheckCtit()!=null && !tramite.getResCheckCtit().equals(CADENA_VACIA)?tramite.getResCheckCtit():null);
				tramitePQ.setP_EXENTO_CEM(tramite.getTramiteTraficoBean().getExentoCem()!=null && !tramite.getTramiteTraficoBean().getExentoCem().equals(CADENA_VACIA) ? checkSiNo(tramite.getTramiteTraficoBean().getExentoCem(), TRUE) : null);
				tramitePQ.setP_TIPO_TRAMITE(tramite.getTramiteTraficoBean().getTipoTramite()!=null ? tramite.getTramiteTraficoBean().getTipoTramite().getValorEnum() : null);
				//Mantis 25415
				tramitePQ.setP_VALOR_REAL(tramite.getValorReal());
				//DVV
				if (tramite.getTramiteTraficoBean() != null && tramite.getTramiteTraficoBean().getVehiculo() != null) {
					tramitePQ.setP_ES_SINIESTRO(tramite.getTramiteTraficoBean().getVehiculo().getEsSiniestro() != null && tramite.getTramiteTraficoBean().getVehiculo().getEsSiniestro() ? new BigDecimal(1) : new BigDecimal(0));
//					tramitePQ.setP_TIENE_CARGA_FINANCIERA(tramite.getTramiteTraficoBean().getVehiculo().getTieneCargaFinanciera() != null && tramite.getTramiteTraficoBean().getVehiculo().getTieneCargaFinanciera() ? new BigDecimal(1) : new BigDecimal(0));
				}
			}else{
				tramitePQ.setP_ID_USUARIO(null);
				tramitePQ.setP_NUM_EXPEDIENTE(null);
				tramitePQ.setP_ID_CONTRATO(null);
				tramitePQ.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
				tramitePQ.setP_NUM_COLEGIADO(utilesColegiado.getNumColegiadoSession());
				tramitePQ.setP_CODIGO_TASA(null);
				tramitePQ.setP_TIPO_TASA(null);
				tramitePQ.setP_ESTADO(null);
				tramitePQ.setP_REF_PROPIA(null);
				tramitePQ.setP_ANOTACIONES(null);
				tramitePQ.setP_FECHA_ALTA(null);
				tramitePQ.setP_FECHA_PRESENTACION(null);
				tramitePQ.setP_FECHA_ULT_MODIF(null);
				tramitePQ.setP_FECHA_IMPRESION(null);
				tramitePQ.setP_RENTING(null);
				tramitePQ.setP_IEDTM(null);
				tramitePQ.setP_MODELO_IEDTM(null);
				tramitePQ.setP_FECHA_IEDTM(null);
				tramitePQ.setP_N_REG_IEDTM(null);
				tramitePQ.setP_FINANCIERA_IEDTM(null);
				tramitePQ.setP_EXENTO_IEDTM(null);
				tramitePQ.setP_NO_SUJECION_IEDTM(null);
				tramitePQ.setP_RESPUESTA(null);
				tramitePQ.setP_RES_CHECK_CTIT(null);
			}
		} catch (ParseException e) {
			log.error(e.getMessage());
		}

		tramitePQ.setP_JEFATURA_PROVINCIAL(null);
		if (tramite.getTramiteTraficoBean().getJefaturaTrafico() != null) {
			tramitePQ.setP_JEFATURA_PROVINCIAL(
					tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial() != null
							&& !tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial().equals(CADENA_VACIA)
							&& !tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial().equals(
									"-1") ? tramite.getTramiteTraficoBean().getJefaturaTrafico().getJefaturaProvincial()
											: null);
		}

		//@author: Carlos García
		//Incidencia: 0001632
		tramitePQ.setP_ID_PROVINCIA_CET(tramite.getProvinciaCet()!=null ? tramite.getProvinciaCet().getIdProvincia() : null);

		tramitePQ.setP_ELECTRONICA(null!=tramite.getElectronica() ? tramite.getElectronica() : N);
		tramitePQ.setP_CEM(tramite.getTramiteTraficoBean().getCemIedtm()!=null && !tramite.getTramiteTraficoBean().getCemIedtm().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getCemIedtm() : null);

		// DRC@24-01-2013 Incidencia Mantis:3437
		tramitePQ.setP_ID_PROVINCIA_CEM(tramite.getProvinciaCem() != null ? tramite.getProvinciaCem().getIdProvincia() : null);

		tramitePQ.setP_CEMA(tramite.getTramiteTraficoBean().getCema() != null && !tramite.getTramiteTraficoBean().getCema().equals(CADENA_VACIA) ? tramite.getTramiteTraficoBean().getCema() : null);
		// Valores propios de la Baja
		tramitePQ.setP_MODO_ADJUDICACION(tramite.getModoAdjudicacion() != null ? tramite.getModoAdjudicacion().getValorEnum() : null);
		tramitePQ.setP_TIPO_TRANSFERENCIA(tramite.getTipoTransferencia() != null ? tramite.getTipoTransferencia().getValorEnum() : null);
		if (tramite.getTramiteTraficoBean().getVehiculo() != null) {
			//Si hay datos de domicilio de vehículo es que es distinto al del titular...

			//Incidencia 3038: Además debe aceptar la responsabilidad del cambio del domicilio.
			//String cambioLocalizacion = SI.equals(tramite.getConsentimientoCambio())?SI:NO;

			tramitePQ.setP_CAMBIO_DOMICILIO(tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean()!=null
					&& tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal()!=null
					&& SI.equals(tramite.getConsentimientoCambio()) && !tramite.getTramiteTraficoBean().getVehiculo().getDireccionBean().getCodPostal().equals(CADENA_VACIA) ? SI : null);

			tramitePQ.setP_ID_VEHICULO(null == tramite.getTramiteTraficoBean().getVehiculo().getIdVehiculo()?null:tramite.getTramiteTraficoBean().getVehiculo().getIdVehiculo());
			tramitePQ.setP_ACEPTACION_RESPONS_ITV(
					tramite.getTramiteTraficoBean().getVehiculo().getConsentimiento() != null
							&& !tramite.getTramiteTraficoBean().getVehiculo().getConsentimiento().equals(CADENA_VACIA)
									? checkSiNo(tramite.getTramiteTraficoBean().getVehiculo().getConsentimiento(), TRUE)
									: null);
		} else {
			tramitePQ.setP_CAMBIO_DOMICILIO(null);
			tramitePQ.setP_ID_VEHICULO(null);
			tramitePQ.setP_ACEPTACION_RESPONS_ITV(null);
		}

		tramitePQ.setP_CAMBIO_SERVICIO(tramite.getCambioServicio()!=null && !tramite.getCambioServicio().equals(CADENA_VACIA) ? checkSiNo(tramite.getCambioServicio(), TRUE) : null);
		tramitePQ.setP_ESTADO_620(null);
		if (tramite.getEstado620() != null) {
			tramitePQ.setP_ESTADO_620(new BigDecimal (tramite.getEstado620().getValorEnum()));
		}
		tramitePQ.setP_MODELO_ITP(tramite.getModeloItp() != null && !tramite.getModeloItp().equals(CADENA_VACIA) && !tramite.getModeloItp().equals("-1")?tramite.getModeloItp() : null);
		if (tramite.getOficinaLiquidadora620() != null) {
			tramitePQ.setP_OFICINA_LIQUIDADORA(tramite.getOficinaLiquidadora620().getOficinaLiquidadora()!=null && !tramite.getOficinaLiquidadora620().getOficinaLiquidadora().equals(CADENA_VACIA)
					&& !tramite.getOficinaLiquidadora620().getOficinaLiquidadora().equals("-1") && !tramite.getOficinaLiquidadora620().getOficinaLiquidadora().equals("undefined") ? tramite.getOficinaLiquidadora620().getOficinaLiquidadora() : null);
			tramitePQ.setP_ID_PROVINCIA(tramite.getOficinaLiquidadora620().getProvincia()!=null ? tramite.getOficinaLiquidadora620().getProvincia().getIdProvincia() : null);
		} else {
			tramitePQ.setP_OFICINA_LIQUIDADORA(null);
			tramitePQ.setP_ID_PROVINCIA(null);
		}
		tramitePQ.setP_FUNDAMENTO_EXENCION(tramite.getFundamentoExencion620()!=null && !tramite.getFundamentoExencion620().equals(CADENA_VACIA) && !tramite.getFundamentoExencion620().equals("-1") ? tramite.getFundamentoExencion620() : null);
		//JRG - Mantis: 6681
		tramitePQ.setP_EXENTO_ITP(tramite.getExentoItp()!=null && tramite.getExentoItp().equals(TRUE) ? SI : NO);
		//
		tramitePQ.setP_SUBASTA(tramite.getSubasta()!=null && tramite.getSubasta().equals(TRUE) ? S : N);
		tramitePQ.setP_TIPO_MOTOR(tramite.getTipoMotor() != null ? tramite.getTipoMotor().getIdTipoMotor() : null);
		tramitePQ.setP_PROCEDENCIA_620(tramite.getProcedencia());
		tramitePQ.setP_TIPO_REDUCCION(tramite.getReduccionCodigo() != null ? tramite.getReduccionCodigo().getTipoReduccion() : null);
		tramitePQ.setP_REDUCCION_PORCENTAJE(tramite.getReduccionCodigo() != null ? tramite.getReduccionCodigo().getPorcentajeReduccion() : null);
		tramitePQ.setP_NO_SUJETO_ITP(tramite.getNoSujetoItp()!=null && tramite.getNoSujetoItp().equals(TRUE) ? SI : (tramite.getModeloItp()!=null ? NO : null));
		tramitePQ.setP_P_REDUCCION_ANUAL(tramite.getPorcentajeReduccionAnual620());
		tramitePQ.setP_VALOR_DECLARADO(tramite.getValorDeclarado620());
		tramitePQ.setP_P_ADQUISICION(tramite.getPorcentajeAdquisicion620());
		tramitePQ.setP_BASE_IMPONIBLE(tramite.getBaseImponible620());
		tramitePQ.setP_TIPO_GRAVAMEN(tramite.getTipoGravamen620());
		tramitePQ.setP_CUOTA_TRIBUTARIA(tramite.getCuotaTributaria620());
		tramitePQ.setP_CODIGO_TASA_INF(tramite.getCodigoTasaInforme()!=null && !tramite.getCodigoTasaInforme().equals(CADENA_VACIA) && !tramite.getCodigoTasaInforme().equals("-1") ? tramite.getCodigoTasaInforme() : null);
		tramitePQ.setP_CODIGO_TASA_CAMSER(tramite.getCodigoTasaCambioServicio()!=null && !tramite.getCodigoTasaCambioServicio().equals(CADENA_VACIA) && !tramite.getCodigoTasaCambioServicio().equals("-1") ? tramite.getCodigoTasaCambioServicio() : null);
		tramitePQ.setP_IMPR_PERMISO_CIRCU(tramite.getImpresionPermiso()!=null && !tramite.getImpresionPermiso().equals(CADENA_VACIA) ? (tramite.getImpresionPermiso().equals(TRUE) || tramite.getImpresionPermiso().equals(SI) ? SI : NO) : null);
		tramitePQ.setP_SIMULTANEA(tramite.getSimultanea()!=null ? tramite.getSimultanea() : null);
		tramitePQ.setP_CONSENTIMIENTO(tramite.getConsentimiento()!=null && !tramite.getConsentimiento().equals(CADENA_VACIA) ? checkSiNo(tramite.getConsentimiento(), TRUE) : null);
		tramitePQ.setP_CONSENTIMIENTO_CAMBIO(tramite.getConsentimientoCambio()!=null && !tramite.getConsentimientoCambio().equals(CADENA_VACIA) ? checkSiNo(tramite.getConsentimientoCambio(), TRUE) : null);
		tramitePQ.setP_CONTRATO_FACTURA(tramite.getContratoFactura()!=null && !tramite.getContratoFactura().equals(CADENA_VACIA) ? (tramite.getContratoFactura().equals(TRUE) ? ContratoFactura.Contrato.toString() : (tramite.getFactura() != null && !tramite.getFactura().equals(CADENA_VACIA) ? ContratoFactura.Factura.toString() : null)) : null);
		tramitePQ.setP_FACTURA(tramite.getFactura()!=null && !tramite.getFactura().equals(CADENA_VACIA) ? tramite.getFactura() : null);
		tramitePQ.setP_ACTA_SUBASTA(tramite.getActaSubasta()!=null && !tramite.getActaSubasta().equals(CADENA_VACIA) ? checkSiNo(tramite.getActaSubasta(), TRUE) : null);
		tramitePQ.setP_SENTENCIA_JUDICIAL(tramite.getSentenciaJudicial()!=null && !tramite.getSentenciaJudicial().equals(CADENA_VACIA) ? checkSiNo(tramite.getSentenciaJudicial(), TRUE) : null);
		tramitePQ.setP_ACREDITA_HERENCIA_DONACION(tramite.getAcreditaHerenciaDonacion()!=null ? tramite.getAcreditaHerenciaDonacion().getValorEnum() : null);
		tramitePQ.setP_CET_ITP(tramite.getCetItp()!=null && !tramite.getCetItp().equals(CADENA_VACIA) ? tramite.getCetItp() : null);
		tramitePQ.setP_NUM_AUTO_ITP(tramite.getNumAutoItp() != null && !tramite.getNumAutoItp().equals(CADENA_VACIA) ? tramite.getNumAutoItp() : null);
		tramitePQ.setP_MODELO_ISD(tramite.getModeloIsd() != null && !tramite.getModeloIsd().equals(CADENA_VACIA) && !tramite.getModeloIsd().equals("-1") ? tramite.getModeloIsd() : null);
		tramitePQ.setP_NUM_AUTO_ISD(tramite.getNumAutoIsd() != null && !tramite.getNumAutoIsd().equals(CADENA_VACIA) ? tramite.getNumAutoIsd() : null);
		tramitePQ.setP_EXENCION_ISD(tramite.getExentoIsd() != null && tramite.getExentoIsd().equals(TRUE) ? SI : (tramite.getModeloIsd() != null ? NO : null));
		tramitePQ.setP_NO_SUJETO_ISD(tramite.getNoSujetoIsd() != null && tramite.getNoSujetoIsd().equals(TRUE) ? SI : (tramite.getModeloIsd() != null ? NO : null));
		tramitePQ.setP_DUA(tramite.getDua() != null && !tramite.getDua().equals(CADENA_VACIA) ? checkSiNo(tramite.getDua(), TRUE) : null);
		tramitePQ.setP_ALTA_IVTM(tramite.getAltaIvtm() != null && !tramite.getAltaIvtm().equals(CADENA_VACIA) ? checkSiNo(tramite.getAltaIvtm(), TRUE) : null);
		//DVV
//		tramitePQ.setP_CAMBIO_SOCIETARIO(tramite.getCambioSocietario() != null && tramite.getCambioSocietario() ? new BigDecimal(1) : new BigDecimal(0));
//		tramitePQ.setP_MODIFICACION_NO_CONSTANTE(tramite.getModificacionNoConstante() != null && tramite.getModificacionNoConstante() ? new BigDecimal(1) : new BigDecimal(0));
		tramitePQ.setP_ACREDITACION_PAGO(tramite.getAcreditacionPago()!=null && tramite.getAcreditacionPago().equals("SI") ? SI : NO);
		try {
			tramitePQ.setP_FECHA_DEVENGO_ITP(null);
			if (tramite.getTramiteTraficoBean().getFechaIedtm() != null){
				tramitePQ.setP_FECHA_DEVENGO_ITP(null == tramite.getFechaDevengoItp620().getTimestamp() ? null : tramite.getFechaDevengoItp620().getTimestamp());
			}
		} catch(ParseException e) {
			// Mantis 12982. David Sierra
			log.error("La fecha introducida no tiene un formato válido");
			// Fin Mantis 12982
		}

		tramitePQ.setP_FUNDAMENTO_NO_SUJETO_ITP(tramite.getFundamentoNoSujeccion620() != null && !tramite.getFundamentoNoSujeccion620().equals(CADENA_VACIA) ? tramite.getFundamentoNoSujeccion620() : null);
		tramitePQ.setP_ID_REDUCCION_05(null != tramite.getIdReduccion05() ? tramite.getIdReduccion05().getValorEnum() : null);
		tramitePQ.setP_ID_NO_SUJECCION_06(null != tramite.getIdNoSujeccion06() ? tramite.getIdNoSujeccion06().getValorEnum() : null);
		tramitePQ.setP_INFORMACION(null);
		tramitePQ.setP_NUM_TITULARES(tramite.getNumTitulares());

		/* SCL. Fecha de factura y fecha de contrato. */
		try {
			tramitePQ.setP_FECHA_FACTURA(tramite != null && tramite.getFechaFactura() != null ? tramite.getFechaFactura().getTimestamp() : null);
			tramitePQ.setP_FECHA_CONTRATO(tramite != null && tramite.getFechaContrato() != null ? tramite.getFechaContrato().getTimestamp() : null);
		} catch (ParseException e) {
			if (e.getMessage() != null)
				log.error("Error al insertar fecha de factura o fecha de contrato: " + e.getMessage());
		}
		/* Fin fecha de factura y fecha de contrato */

		//DRC@15-02-2013 Incidencia Mantis: 2708
		tramitePQ.setP_ID_TIPO_CREACION(tramite.getIdTipoCreacion());

		tramitePQ.setP_CHECK_VALOR_MANUAL_620(tramite.getCheck620ValorManual());
		return tramitePQ;
	}

	/*
	 * Método general para convertir un trámite de transmision bean en un beanPQ
	 */
	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return BeanPQTramiteTransmision
	 * 
	 * @ Los atributos P_INFORMACION son siempre devueltos con 'null'
	 */
	public BeanPQTramiteTransmision convertirTramiteTransmisionToPQ(TramiteTraficoTransmisionBean beanF) {

		BeanPQTramiteTransmision beanPQ = new BeanPQTramiteTransmision();

		beanPQ.setBeanGuardarVehiculo(vehiculoBeanPQConversion.convertirBeanToPQ(beanF.getTramiteTraficoBean().getVehiculo(),beanF.getTramiteTraficoBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));

		beanPQ.setBeanGuardarTransmision(convertirBeanToPQ(beanF));

		beanPQ.setBeanGuardarAdquiriente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getAdquirienteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarAdquiriente().setP_TIPO_INTERVINIENTE(TipoInterviniente.Adquiriente.getValorEnum());

		beanPQ.setBeanGuardarConductorHabitual(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getConductorHabitualBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarConductorHabitual().setP_TIPO_INTERVINIENTE(TipoInterviniente.ConductorHabitual.getValorEnum());

		beanPQ.setBeanGuardarRepresentanteAdquiriente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteAdquirienteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteAdquiriente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());

		beanPQ.setBeanGuardarTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.TransmitenteTrafico.getValorEnum());

		beanPQ.setBeanGuardarRepresentanteTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTransmitente.getValorEnum());

		beanPQ.setBeanGuardarPrimerCotitularTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getPrimerCotitularTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarPrimerCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());

		beanPQ.setBeanGuardarRepresentantePrimerCotitularTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentantePrimerCotitularTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentantePrimerCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentantePrimerCotitularTransmision.getValorEnum());

		beanPQ.setBeanGuardarSegundoCotitularTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getSegundoCotitularTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarSegundoCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());

		beanPQ.setBeanGuardarRepresentanteSegundoCotitularTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteSegundoCotitularTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteSegundoCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteSegundoCotitularTransmision.getValorEnum());

		beanPQ.setBeanGuardarPresentador(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getPresentadorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarPresentador().setP_TIPO_INTERVINIENTE(TipoInterviniente.PresentadorTrafico.getValorEnum());

		beanPQ.setBeanGuardarPoseedor(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getPoseedorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarPoseedor().setP_TIPO_INTERVINIENTE(TipoInterviniente.Compraventa.getValorEnum());

		beanPQ.setBeanGuardarRepresentantePoseedor(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentantePoseedorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentantePoseedor().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteCompraventa.getValorEnum());

		beanPQ.setBeanGuardarArrendatario(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getArrendatarioBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarArrendatario().setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendatario.getValorEnum());

		beanPQ.setBeanGuardarRepresentanteArrendatario(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteArrendatarioBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteArrendatario().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum());

		return beanPQ;
	}

	/*
	 * Método general para convertir un trámite de transmisión bean actual, sin CTIT, en un beanPQ
	 */
	/**
	 * @author antonio.miguez
	 * @param TramiteTraficoTransmisionBean
	 * @return BeanPQTramiteTransmision
	 * 
	 * @ Los atributos P_INFORMACION son siempre devueltos con 'null'
	 */
	public BeanPQTramiteTransmision convertiraTramiteTransmisionActualToPQ(TramiteTraficoTransmisionBean beanF) {
		BeanPQTramiteTransmision beanPQ = new BeanPQTramiteTransmision();

		beanPQ.setBeanGuardarVehiculo(vehiculoBeanPQConversion.convertirBeanToPQ(beanF.getTramiteTraficoBean().getVehiculo(),beanF.getTramiteTraficoBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.setBeanGuardarTransmision(convertirBeanToPQ(beanF));
		beanPQ.setBeanGuardarAdquiriente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getAdquirienteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarAdquiriente().setP_TIPO_INTERVINIENTE(TipoInterviniente.Adquiriente.getValorEnum());
		beanPQ.setBeanGuardarConductorHabitual(new BeanPQTramiteTraficoGuardarInterviniente());
		beanPQ.getBeanGuardarConductorHabitual().setP_TIPO_INTERVINIENTE(TipoInterviniente.ConductorHabitual.getValorEnum());
		beanPQ.setBeanGuardarRepresentanteAdquiriente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteAdquirienteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteAdquiriente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteAdquiriente.getValorEnum());
		beanPQ.setBeanGuardarTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.TransmitenteTrafico.getValorEnum());
		beanPQ.setBeanGuardarRepresentanteTransmitente(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentanteTransmitenteBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentanteTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteTransmitente.getValorEnum());
		beanPQ.setBeanGuardarPrimerCotitularTransmitente(new BeanPQTramiteTraficoGuardarInterviniente());
		beanPQ.getBeanGuardarPrimerCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());
		beanPQ.setBeanGuardarSegundoCotitularTransmitente(new BeanPQTramiteTraficoGuardarInterviniente());
		beanPQ.getBeanGuardarSegundoCotitularTransmitente().setP_TIPO_INTERVINIENTE(TipoInterviniente.CotitularTransmision.getValorEnum());
		beanPQ.setBeanGuardarPresentador(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getPresentadorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarPresentador().setP_TIPO_INTERVINIENTE(TipoInterviniente.PresentadorTrafico.getValorEnum());
		beanPQ.setBeanGuardarPoseedor(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getPoseedorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarPoseedor().setP_TIPO_INTERVINIENTE(TipoInterviniente.Compraventa.getValorEnum());
		beanPQ.setBeanGuardarRepresentantePoseedor(intervinienteTraficoBeanPQConversion.convertirBeanToPQ(beanF.getRepresentantePoseedorBean(),
				utilesColegiado.getIdUsuarioSessionBigDecimal(), utilesColegiado.getIdContratoSessionBigDecimal(), utilesColegiado.getNumColegiadoSession()));
		beanPQ.getBeanGuardarRepresentantePoseedor().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteCompraventa.getValorEnum());
		beanPQ.setBeanGuardarArrendatario(new BeanPQTramiteTraficoGuardarInterviniente());
		beanPQ.getBeanGuardarArrendatario().setP_TIPO_INTERVINIENTE(TipoInterviniente.Arrendatario.getValorEnum());
		beanPQ.setBeanGuardarRepresentanteArrendatario(new BeanPQTramiteTraficoGuardarInterviniente());
		beanPQ.getBeanGuardarRepresentanteArrendatario().setP_TIPO_INTERVINIENTE(TipoInterviniente.RepresentanteArrendatario.getValorEnum());
		return beanPQ;
	}

	public TramiteTraficoTransmisionBean convertirPQToBean(RespuestaGenerica resultadoPQ) {
		TramiteTraficoTransmisionBean tramite = new TramiteTraficoTransmisionBean(true);

		// Campos genéricos del Trámite Tráfico
		tramite.getTramiteTraficoBean().setNumExpediente((BigDecimal)resultadoPQ.getParametro("P_NUM_EXPEDIENTE"));
		tramite.getTramiteTraficoBean().setIdContrato((BigDecimal)resultadoPQ.getParametro("P_ID_CONTRATO"));
		tramite.getTramiteTraficoBean().setNumColegiado((String)resultadoPQ.getParametro("P_NUM_COLEGIADO"));
		tramite.getTramiteTraficoBean().getVehiculo().setIdVehiculo((BigDecimal)resultadoPQ.getParametro("P_ID_VEHICULO"));
		tramite.getTramiteTraficoBean().getTasa().setCodigoTasa((String)resultadoPQ.getParametro("P_CODIGO_TASA"));
		tramite.getTramiteTraficoBean().getTasa().setTipoTasa((String)resultadoPQ.getParametro("P_TIPO_TASA"));
		tramite.getTramiteTraficoBean().setTipoTramite(null != resultadoPQ.getParametro("P_TIPO_TRAMITE") ? (String)resultadoPQ.getParametro("P_TIPO_TRAMITE") : null);

		if (resultadoPQ.getParametro("P_ESTADO") != null) {
			BigDecimal estado = (BigDecimal)resultadoPQ.getParametro("P_ESTADO");
			tramite.getTramiteTraficoBean().setEstado(estado.toString());
		}
		tramite.getTramiteTraficoBean().setReferenciaPropia((String)resultadoPQ.getParametro("P_REF_PROPIA"));

		if (resultadoPQ.getParametro("P_FECHA_ALTA") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ALTA");
			tramite.getTramiteTraficoBean().setFechaCreacion((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_PRESENTACION") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_PRESENTACION");
			tramite.getTramiteTraficoBean().setFechaPresentacion((utilesFecha.getFechaFracionada(fecha)));
		}
		/*Se recuperan los nuevos campos FECHA_FACTURA y FECHA_CONTRATO*/
		if (resultadoPQ.getParametro("P_FECHA_FACTURA") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_FACTURA");
			tramite.getTramiteTraficoBean().setFechaFactura((utilesFecha.getFechaFracionada(fecha)));
			tramite.setFechaFactura((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_CONTRATO") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_CONTRATO");
			tramite.getTramiteTraficoBean().setFechaContrato((utilesFecha.getFechaFracionada(fecha)));
			tramite.setFechaContrato((utilesFecha.getFechaFracionada(fecha)));
		}
		/*Recuperados*/

		if (resultadoPQ.getParametro("P_FECHA_ULT_MODIF") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_ULT_MODIF");
			tramite.getTramiteTraficoBean().setFechaUltModif((utilesFecha.getFechaFracionada(fecha)));
		}

		if (resultadoPQ.getParametro("P_FECHA_IMPRESION") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IMPRESION");
			tramite.getTramiteTraficoBean().setFechaImpresion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.getTramiteTraficoBean().getJefaturaTrafico().setJefaturaProvincial((String)resultadoPQ.getParametro("P_JEFATURA_PROVINCIAL"));
		tramite.getTramiteTraficoBean().setAnotaciones((String)resultadoPQ.getParametro("P_ANOTACIONES"));
		tramite.getTramiteTraficoBean().setRenting(resultadoPQ.getParametro("P_RENTING") != null && resultadoPQ.getParametro("P_RENTING").equals(SI) ? TRUE : FALSE);
		//Cambio de domicilio no hace falta en este caso
		tramite.getTramiteTraficoBean().setIedtm(resultadoPQ.getParametro("P_IEDTM") != null && resultadoPQ.getParametro("P_IEDTM").equals(E) ? E : CADENA_VACIA);
		tramite.getTramiteTraficoBean().setModeloIedtm((String)resultadoPQ.getParametro("P_MODELO_IEDTM"));

		if (resultadoPQ.getParametro("P_FECHA_IEDTM") != null) {
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro("P_FECHA_IEDTM");
			tramite.getTramiteTraficoBean().setFechaIedtm((utilesFecha.getFechaFracionada(fecha)));
		}
		tramite.getTramiteTraficoBean().setNumRegIedtm((String)resultadoPQ.getParametro("P_N_REG_IEDTM"));
		tramite.getTramiteTraficoBean().setFinancieraIedtm((String)resultadoPQ.getParametro("P_FINANCIERA_IEDTM"));
		tramite.getTramiteTraficoBean().setExentoIedtm(resultadoPQ.getParametro("P_EXENTO_IEDTM") != null && resultadoPQ.getParametro("P_EXENTO_IEDTM").equals(SI) ? TRUE : FALSE);
		tramite.getTramiteTraficoBean().setNoSujetoIedtm(resultadoPQ.getParametro("P_NO_SUJECION_IEDTM") != null && resultadoPQ.getParametro("P_NO_SUJECION_IEDTM").equals(SI) ? TRUE : FALSE);
		tramite.getTramiteTraficoBean().setCemIedtm((String)resultadoPQ.getParametro("P_CEM"));
		tramite.getTramiteTraficoBean().setCema((String)resultadoPQ.getParametro("P_CEMA"));
		tramite.getTramiteTraficoBean().setRespuesta((String)resultadoPQ.getParametro("P_RESPUESTA"));
		tramite.setResCheckCtit((String)resultadoPQ.getParametro("P_RES_CHECK_CTIT"));

		//Valores propios del trámite transmisión
		tramite.setModoAdjudicacion((String)resultadoPQ.getParametro("P_MODO_ADJUDICACION"));
		tramite.setTipoTransferencia((String)resultadoPQ.getParametro("P_TIPO_TRANSFERENCIA"));
		tramite.setTipoTransferenciaActual((String)resultadoPQ.getParametro("P_TIPO_TRANSFERENCIA"));
		tramite.getTramiteTraficoBean().getVehiculo().setConsentimiento(resultadoPQ.getParametro("P_ACEPTACION_RESPONS_ITV")!=null && resultadoPQ.getParametro("P_ACEPTACION_RESPONS_ITV").equals(SI) ? TRUE : FALSE);

		// Incidencia 3038: Localización vehículo
		tramite.setConsentimientoCambio((String)resultadoPQ.getParametro(P_CONSENTIMIENTO_CAMBIO));

		// Fin incidencia 3038
		tramite.setCambioServicio(resultadoPQ.getParametro("P_CAMBIO_SERVICIO") != null && resultadoPQ.getParametro("P_CAMBIO_SERVICIO").equals(SI) ? TRUE : FALSE);
		if (resultadoPQ.getParametro("P_ESTADO_620") != null) {
			BigDecimal estado620 = (BigDecimal)resultadoPQ.getParametro("P_ESTADO_620");
			tramite.setEstado620(estado620.toString());
		}

		//@author: Carlos García
		//Incidencia: 0001632
		tramite.getProvinciaCet().setIdProvincia((String)resultadoPQ.getParametro("P_ID_PROVINCIA_CET"));

		// DRC@24-01-2013 Incidencia: 3437
		tramite.getProvinciaCem().setIdProvincia((String)resultadoPQ.getParametro("P_ID_PROVINCIA_CEM"));

		tramite.setSubasta(resultadoPQ.getParametro("P_SUBASTA") != null && resultadoPQ.getParametro("P_SUBASTA").toString().equalsIgnoreCase(S) ? TRUE : FALSE);
		if (resultadoPQ.getParametro("P_TIPO_MOTOR") != null) {
			tramite.getTipoMotor().setIdTipoMotor(resultadoPQ.getParametro("P_TIPO_MOTOR").toString());
		}

		tramite.setModeloItp((String)resultadoPQ.getParametro("P_MODELO_ITP"));
		tramite.getOficinaLiquidadora620().getProvincia().setIdProvincia((String)resultadoPQ.getParametro("P_ID_PROVINCIA"));
		tramite.getOficinaLiquidadora620().setOficinaLiquidadora((String)resultadoPQ.getParametro("P_OFICINA_LIQUIDADORA"));
		tramite.setFundamentoExencion620((String)resultadoPQ.getParametro("P_FUNDAMENTO_EXENCION"));
		tramite.setFundamentoNoSujeccion620((String)resultadoPQ.getParametro("P_FUNDAMENTO_NO_SUJETO_ITP"));
		tramite.setExento620(resultadoPQ.getParametro(P_EXENTO_ITP) != null && resultadoPQ.getParametro(P_EXENTO_ITP).toString().equalsIgnoreCase(SI) ? TRUE : FALSE);

		tramite.setExentoItp(resultadoPQ.getParametro(P_EXENTO_ITP) != null && resultadoPQ.getParametro(P_EXENTO_ITP).equals(SI) ? TRUE : FALSE);

		tramite.setNoSujetoItp(resultadoPQ.getParametro("P_NO_SUJETO_ITP") != null && resultadoPQ.getParametro("P_NO_SUJETO_ITP").equals(SI) ? TRUE : FALSE);
		if (resultadoPQ.getParametro(P_FECHA_DEVENGO_ITP) != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro(P_FECHA_DEVENGO_ITP);
			tramite.setFechaDevengoItp620(utilesFecha.getFechaFracionada(fecha));
		}
		tramite.setPorcentajeReduccionAnual620((BigDecimal)resultadoPQ.getParametro("P_P_REDUCCION_ANUAL"));
		tramite.setValorDeclarado620((BigDecimal)resultadoPQ.getParametro("P_VALOR_DECLARADO"));
		tramite.setPorcentajeAdquisicion620((BigDecimal)resultadoPQ.getParametro("P_P_ADQUISICION"));
		tramite.setBaseImponible620((BigDecimal)resultadoPQ.getParametro("P_BASE_IMPONIBLE"));
		tramite.setTipoGravamen620((BigDecimal)resultadoPQ.getParametro("P_TIPO_GRAVAMEN"));
		tramite.setCuotaTributaria620((BigDecimal)resultadoPQ.getParametro("P_CUOTA_TRIBUTARIA"));
		tramite.setCodigoTasaInforme((String)resultadoPQ.getParametro("P_CODIGO_TASA_INF"));
		tramite.setCodigoTasaCambioServicio((String)resultadoPQ.getParametro("P_CODIGO_TASA_CAMSER"));
		//Controlamos si viene de TipoTramite "T2" y lo ponemos a true porque siempre lleva
		tramite.setImpresionPermiso(resultadoPQ.getParametro(P_IMPR_PERMISO_CIRCU) != null && resultadoPQ.getParametro(P_IMPR_PERMISO_CIRCU).equals(NO) ? FALSE:
			resultadoPQ.getParametro(P_IMPR_PERMISO_CIRCU) != null && resultadoPQ.getParametro(P_IMPR_PERMISO_CIRCU).equals(SI) ? TRUE : null);

		tramite.setConsentimiento(resultadoPQ.getParametro("P_CONSENTIMIENTO")!=null && resultadoPQ.getParametro("P_CONSENTIMIENTO").equals(SI) ? TRUE : FALSE);
		tramite.setConsentimientoCambio(resultadoPQ.getParametro(P_CONSENTIMIENTO_CAMBIO)!=null && resultadoPQ.getParametro(P_CONSENTIMIENTO_CAMBIO).equals(SI) ? TRUE : FALSE);
		/**
		 * Incidencia 1913. Para los XMLs importados con Si en COMPRA_VENTA, no se marcaba el checkbox porque solo se
		 * compraba el valor CO. Ahora añadimos el SI
		 */
		tramite.setContratoFactura((resultadoPQ.getParametro(P_CONTRATO_FACTURA)!=null && ((resultadoPQ.getParametro(P_CONTRATO_FACTURA).equals("CO")) || (resultadoPQ.getParametro(P_CONTRATO_FACTURA).equals(SI)))) ? TRUE : FALSE);
		tramite.setFactura((String)resultadoPQ.getParametro("P_FACTURA"));
		tramite.setActaSubasta(resultadoPQ.getParametro("P_ACTA_SUBASTA") != null && resultadoPQ.getParametro("P_ACTA_SUBASTA").equals(SI) ? TRUE : FALSE);
		tramite.setSentenciaJudicial(resultadoPQ.getParametro("P_SENTENCIA_JUDICIAL")!=null && resultadoPQ.getParametro("P_SENTENCIA_JUDICIAL").equals(SI) ? TRUE : FALSE);
		tramite.setAcreditaHerenciaDonacion((String)resultadoPQ.getParametro("P_ACREDITA_HERENCIA_DONACION"));
		tramite.setCetItp((String)resultadoPQ.getParametro("P_CET_ITP"));
		tramite.setNumAutoItp((String)resultadoPQ.getParametro("P_NUM_AUTO_ITP"));
		tramite.setModeloIsd((String)resultadoPQ.getParametro("P_MODELO_ISD"));
		tramite.setNumAutoIsd((String)resultadoPQ.getParametro("P_NUM_AUTO_ISD"));
		tramite.setExentoIsd((String)resultadoPQ.getParametro("P_EXENCION_ISD") != null && resultadoPQ.getParametro("P_EXENCION_ISD").equals(SI) ? TRUE : FALSE);
		tramite.setNoSujetoIsd((String)resultadoPQ.getParametro("P_NO_SUJETO_ISD") != null && resultadoPQ.getParametro("P_NO_SUJETO_ISD").equals(SI) ? TRUE : FALSE);
		tramite.setDua((String)resultadoPQ.getParametro("P_DUA") != null && resultadoPQ.getParametro("P_DUA").equals(SI) ? TRUE : FALSE);
		tramite.setAltaIvtm((String)resultadoPQ.getParametro("P_ALTA_IVTM") != null && resultadoPQ.getParametro("P_ALTA_IVTM").equals(SI) ? TRUE : FALSE);
		if (resultadoPQ.getParametro(P_FECHA_DEVENGO_ITP) != null){
			Timestamp fecha = (Timestamp)resultadoPQ.getParametro(P_FECHA_DEVENGO_ITP);
			tramite.setFechaDevengoItp620((utilesFecha.getFechaFracionada(fecha)));
		}
		tramite.getTramiteTraficoBean().setExentoCem(resultadoPQ.getParametro("P_EXENTO_CEM") != null && resultadoPQ.getParametro("P_EXENTO_CEM").equals(SI) ? TRUE : FALSE);

		//Nuevos Reducción 05 y No Sujección 06
		tramite.setIdReduccion05((String)resultadoPQ.getParametro("P_ID_REDUCCION_05"));
		tramite.setIdNoSujeccion06((String)resultadoPQ.getParametro("P_ID_NO_SUJECCION_06"));

		// DRC@24-09-2012 Incidencia: 2358
		tramite.getTramiteTraficoBean().getVehiculo().setTara((String)resultadoPQ.getParametro("P_TARA"));
		tramite.getTramiteTraficoBean().getVehiculo().setPesoMma((String)resultadoPQ.getParametro("P_PESOMMA"));
		tramite.getTramiteTraficoBean().getVehiculo().setPlazas((BigDecimal)resultadoPQ.getParametro("P_PLAZAS"));
		tramite.getTramiteTraficoBean().getVehiculo().setProvinciaPrimeraMatricula((Provincia)resultadoPQ.getParametro("P_ID_PROVINCIA_PRIMERA_MATRICULA"));
		//DVV
		tramite.getTramiteTraficoBean().getVehiculo().setEsSiniestro(resultadoPQ.getParametro(P_ES_SINIESTRO) != null && (resultadoPQ.getParametro(P_ES_SINIESTRO).equals(new BigDecimal(1)) || resultadoPQ.getParametro(P_ES_SINIESTRO).equals(true)));
//		tramite.getTramiteTraficoBean().getVehiculo().setTieneCargaFinanciera(resultadoPQ.getParametro(P_TIENE_CARGA_FINANCIERA) != null && (resultadoPQ.getParametro(P_TIENE_CARGA_FINANCIERA).equals(new BigDecimal(1)) || resultadoPQ.getParametro(P_TIENE_CARGA_FINANCIERA).equals(true)));
//		tramite.setCambioSocietario(resultadoPQ.getParametro(P_CAMBIO_SOCIETARIO) != null && (resultadoPQ.getParametro(P_CAMBIO_SOCIETARIO).equals(new BigDecimal(1)) || resultadoPQ.getParametro(P_CAMBIO_SOCIETARIO).equals(true)));
//		tramite.setModificacionNoConstante(resultadoPQ.getParametro(P_MODIFICACION_NO_CONSTANTE) != null && (resultadoPQ.getParametro(P_MODIFICACION_NO_CONSTANTE).equals(new BigDecimal(1)) || resultadoPQ.getParametro(P_MODIFICACION_NO_CONSTANTE).equals(true)));

		tramite.setAcreditacionPago(resultadoPQ.getParametro(P_ACREDITACION_PAGO) != null && resultadoPQ.getParametro(P_ACREDITACION_PAGO).equals("SI") ? "SI" : "NO");

		tramite.setNumTitulares((BigDecimal)resultadoPQ.getParametro("P_NUM_TITULARES"));

		//JRG: 0003728: Mejora para indicar cuándo es una transferencia simultánea
		tramite.setSimultanea((BigDecimal)resultadoPQ.getParametro("P_SIMULTANEA"));
		tramite.setIdTipoCreacion((BigDecimal) resultadoPQ.getParametro("P_ID_TIPO_CREACION"));

		//Mantis 25415 P_VALOR_REAL
		if (resultadoPQ.getParametro(P_VALOR_REAL) != null && !resultadoPQ.getParametro(P_VALOR_REAL).equals(CADENA_VACIA)) {
			tramite.setValorReal(((BigDecimal) resultadoPQ.getParametro(P_VALOR_REAL)).setScale(2));
		} else {
			BigDecimal vR = BigDecimal.valueOf(0.00);
			tramite.setValorReal(vR.setScale(2));
		}

		if (resultadoPQ.getParametro("P_CHECK_VALOR_MANUAL_620") != null) {
			tramite.setCheck620ValorManual(resultadoPQ.getParametro("P_CHECK_VALOR_MANUAL_620").toString());
		}

		//Cambio Modelo 620
		if (resultadoPQ.getParametro(P_TIPO_REDUCCION) != null) {
			tramite.getReduccionCodigo().setTipoReduccion(resultadoPQ.getParametro(P_TIPO_REDUCCION).toString());
		}

		BigDecimal reduccionPorcentaje = null;
		if (resultadoPQ.getParametro(P_REDUCCION_PORCENTAJE) != null && !resultadoPQ.getParametro(P_REDUCCION_PORCENTAJE).equals(CADENA_VACIA)) {
			tramite.setReduccionPorcentaje(((BigDecimal) resultadoPQ.getParametro(P_REDUCCION_PORCENTAJE)).setScale(2));
			reduccionPorcentaje = ((BigDecimal) resultadoPQ.getParametro(P_REDUCCION_PORCENTAJE)).setScale(2);
		} else {
			if (resultadoPQ.getParametro(P_TIPO_REDUCCION) != null) {
				String tipoReduccion = resultadoPQ.getParametro(P_TIPO_REDUCCION).toString();
				TipoReduccionVO tipoReduccionVO = null;
				if (StringUtils.isNotBlank(tipoReduccion)) {
					tipoReduccionVO = servicioTipoReduccion.getTipoReduccion(tipoReduccion);
				}
				if (tipoReduccionVO != null) {
					reduccionPorcentaje = tipoReduccionVO.getPorcentajeReduccion();
				} else {
					BigDecimal vR = BigDecimal.valueOf(0.00);
					tramite.setReduccionPorcentaje(vR.setScale(2));
					reduccionPorcentaje = vR.setScale(2);
				}
			} else {
				BigDecimal vR = BigDecimal.valueOf(0.00);
				tramite.setReduccionPorcentaje(vR.setScale(2));
				reduccionPorcentaje = vR.setScale(2);
			}
		}
		tramite.getReduccionCodigo().setPorcentajeReduccion(reduccionPorcentaje);

		if (resultadoPQ.getParametro(P_REDUCCION_IMPORTE) != null && !resultadoPQ.getParametro(P_REDUCCION_IMPORTE).equals(CADENA_VACIA)) {
			tramite.setReduccionImporte(((BigDecimal) resultadoPQ.getParametro(P_REDUCCION_IMPORTE)).setScale(2));
		} else {
			BigDecimal vR = BigDecimal.valueOf(0.00);
			tramite.setReduccionImporte(vR.setScale(2));
		}

		if (resultadoPQ.getParametro(P_PROCEDENCIA_620) != null) {
			tramite.setProcedencia(resultadoPQ.getParametro(P_PROCEDENCIA_620).toString());
		}

		return tramite;
	}

	public JustificanteProfesional justificanteProfesionalConvertirBeanPQ (JustificanteProfesionalCursor justificanteProfesionalCursor) {
		//Controlo que el justificanteProfesionalCursor no venga a null; si fuera null, tendría nullPointer Exception al hacer justificanteProfesional.getX().
		if (justificanteProfesionalCursor==null) return null;

		JustificanteProfesional justificanteProfesional = new JustificanteProfesional();

		justificanteProfesional.setDias_validez(justificanteProfesionalCursor.getDias_validez());
		justificanteProfesional.setDocumentos(justificanteProfesionalCursor.getDocumentos());
		justificanteProfesional.setFecha_fin(utilesFecha.getFechaFracionada(justificanteProfesionalCursor.getFecha_fin()));
		justificanteProfesional.setFecha_inicio(utilesFecha.getFechaFracionada(justificanteProfesionalCursor.getFecha_inicio()));
		justificanteProfesional.setId_justificante(justificanteProfesionalCursor.getId_justificante());
		justificanteProfesional.setNum_expediente(justificanteProfesionalCursor.getNum_expediente());
		justificanteProfesional.setTipoTramite(justificanteProfesionalCursor.getTipo_tramite());
		justificanteProfesional.setMatricula(justificanteProfesionalCursor.getMatricula());
		justificanteProfesional.setEstado(justificanteProfesionalCursor.getEstado().toString());

		return justificanteProfesional;
	}

	private String checkSiNo(String campo, String valor) {
		return campo.equals(valor) ? SI : NO;
	}

}