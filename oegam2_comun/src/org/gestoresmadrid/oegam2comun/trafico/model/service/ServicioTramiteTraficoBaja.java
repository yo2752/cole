package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;

import escrituras.beans.ResultBean;

public interface ServicioTramiteTraficoBaja extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";
	public static String ESTADO_VALIDAR = "estadoValidar";
	public static String MOTIVO_BAJA = "motivoBaja";
	public static String SOLICITUD_TRAMITE_CHECK_BTV = "solicitudTramiteCheckBtv";
	public static String SOLICITUD_TRAMITE_TRAMITAR_BTV = "solicitudRegistroEntradaBtv";
	public static String XML = "xmlValido";
	public static String NOMBRE_XML_CHECK_BTV = "nombreXmlCheckBTV";
	public static String NOMBRE_XML_TRAMITAR_BTV = "nombreXmlBTV";
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public static final String NOMBRE_HOST_SOLICITUD_NODO3 = "nombreHostSolicitudProcesos3";
	public static final String NOMBRE_HOST_SOLICITUD_PROCESOS2 = "nombreHostSolicitudProcesos2";
	public static final String NOMBRE_HOST_SOLICITUD_PROCESOS3 = "nombreHostSolicitudProcesos3";

	public static final String TRANSITO_EXPORTACION_CTIT_REQUIEREN_UN_CTIT_PREVIO = "Bajas de exportación y tránsito posteriores a CTIT, requieren de un trámite de transmisión electrónica previo.";

	ResultBean guardarTramite(TramiteTrafBajaDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar, boolean admin, Boolean tienePermisosBtv);

	ResultBean validarTramite(BigDecimal numExpediente, Boolean tienePermisoBTV);

	TramiteTrafBajaDto getTramiteBaja(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean pendientesEnvioExcel(TramiteTrafBajaDto tamiteDto, BigDecimal idUsuario);

	boolean vehiculoMayorDe15Anios(VehiculoDto vehiculo);

	ResultBean comprobarConsultaBTV(TramiteTrafBajaDto tramiteTraficoBaja, BigDecimal idUsuario);

	ResultBean tramitarBajaTelematicamente(BigDecimal numExpediente, BigDecimal idUsuarioDeSesion, String colegio);

	ResultBean esMotivoCorrectoBajasTelematicas(String[] numsExpedientes);

	ResultBean cambiarEstadoTramitesImprimir(String[] numsExpedientes, BigDecimal idUsuario);

	List<TramiteTrafBajaDto> bajasExcel(String jefatura);

	ResultBean guardarRespuestaBTV(BigDecimal numExpediente, String respuesta, Boolean isCheck, EstadoTramiteTrafico estado);

	TramiteTrafBajaVO getTramiteBajaVO(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean comprobarExpedientesImpresionMasiva(String[] numsExpedientes, String tipoImpreso);

	void cambiarEstadoTramite(BigDecimal numExpediente,	EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt,  boolean notificar, BigDecimal idUsuario, String respuesta, boolean esCheckBtv, boolean esBtv);

	void tratarDevolverCreditos(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato, ConceptoCreditoFacturado conceptoCreditoFacturado);

	ResultBean autorizarImpresionInformeBtv(BigDecimal numExpediente, Boolean tienePermisoAdmin);

	ResultBean estanAutorizadosExpImpresionInformeBTV(String[] numsExpedientes);

	List<TramiteTrafBajaDto> getListaTramitesFinalizadosIncidenciaBtv(String jefatura, Date fecha);

	void tratarDevolverCreditosAM(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato,ConceptoCreditoFacturado conceptoCreditoFacturado);
}