package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.FicheroInfo;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCTITDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;

import escrituras.beans.ResultBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.daos.BeanPQTramiteTraficoGuardarTransmision;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

public interface ServicioTramiteTraficoTransmision extends Serializable {

	public static final String NOMBRE_HOST_SOLICITUD3 = "nombreHostSolicitudProcesos3";
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";
	public static String ESTADO_VALIDAR = "estadoValidar";

	TramiteTrafTranDto getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean validarTramite(TramiteTrafTranDto tramite, Long idUsuario);

	ResultBean guardarTramite(TramiteTrafTranDto tramiteDto, BigDecimal idUsuario, boolean desdeValidar, boolean desdeJustificante, boolean admin);

	void guardarOActualizar(TramiteTrafTranVO tramiteTrafTranVO) throws OegamExcepcion;

	boolean sitexComprobarCTITPrevio(String matricula);

	ResultBean comprobarTramitabilidadTramiteTransmision(TramiteTraficoTransmisionBean tramite, BigDecimal idUsuarioSession, BigDecimal idContrato);

	ResultBean comprobarCheckCTIT(TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario);

	DatosCTITDto datosCTIT(BigDecimal numExpediente);

	TramiteTrafTranDto getTramiteTransmisionPorTasaCamser(String codigoTasa, Boolean tramiteCompleto);

	ResultBean generarJustificante(BigDecimal numExpediente, BigDecimal idUsuario);

	// Mantis 25415
	void actualizarValorRealTransmision(BigDecimal numExpediente, BigDecimal valorReal);

	//DVV
//	void actualizarCambioSocietario(BigDecimal numExpediente, BigDecimal cambioSocietario);
//	void actualizarModificacionNoConstante(BigDecimal numExpediente, BigDecimal modificacionNoConstante);
//	void actualizarEsSiniestro(BigDecimal numExpediente, BigDecimal esSiniestro);
	TramiteTrafTranDto actualizarAcreditacionPago(BigDecimal numExpediente);

	ResultBean comprobarTramitabilidadTramiteTransmisionSega(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean, BigDecimal idUsuario, BigDecimal idContrato);

	ResultadoCtitBean transmisionTelematica(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean, String nPasos, BigDecimal idUsuario, BigDecimal idContrato);

	void actualizarTramiteTransmision(TramiteTrafTranDto tramiteTrafTranDto);

	ResultBean generarJustitificanteProf(TramiteTrafTranDto tramiteTrafTranDto, BigDecimal idUsuario, String motivoJustificantes, String documentoJustificantes, String diasValidezJustificantes);

	ResultadoPermisoDistintivoItvBean comprobarCheckImpresionPermiso(BigDecimal numExpediente);

	// Estadisticas CTIT

	List<Object[]> obtenerTotalesTramitesCTIT(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, String idProvincia) throws ParseException;

	List<Object[]> obtenerDetalleTramitesCTIT(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, String idProvincia) throws ParseException;

	List<BigDecimal> obtenerListadoNumerosExpedienteCTIT(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	List<BigDecimal> obtenerListadoNumerosExpedienteCTITAnotacionesGest(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<Object[]> obtenerDetalleTramitesCTITCarpetaFusion(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaFusion(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<Object[]> obtenerDetalleTramitesCTITCarpetaI(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaI(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITCarpetaB(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<Object[]> obtenerDetalleTramitesCTITVehiculosAgricolas(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITVehiculosAgricolas(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerDetalleTramitesCTITJudicialSubasta(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITSinCETNiFactura(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITEmbargoPrecinto(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<Object[]> obtenerDetalleTramitesCTITErrorJefatura(Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITErrorJefatura(Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles) throws ParseException;

	List<BigDecimal> obtenerListaExpedientesCTITEvolucion(boolean esTelematico, Fecha fechaDesde, Fecha fechaHasta, List<BigDecimal> listaPosibles, List<EstadoTramiteTrafico> transicionEstados)
			throws ParseException;

	List<Object[]> obtenerListaTramitesGestorSobreExp(List<BigDecimal> listaPosibles);

	String getTipoTransferencia(BigDecimal numExpediente);

	ResultBean cambiarEstadoTramite(TramiteTraficoTransmisionBean tramite);

	String obtenerTipoTransferenciaTramite(BigDecimal numExpediente);

	void actualizarDatos620(BigDecimal numExpediente, BeanPQTramiteTraficoGuardarTransmision beanGuardarTransmision);

	ArrayList<FicheroInfo> recuperarDocumentos(BigDecimal numExpediente);

	void actualizarFicheroSubido(BigDecimal numExpediente);

	TramiteTrafTranVO getTramite(BigDecimal numExpediente, Boolean tramiteCompleto);

	void actualizarTramiteCtit(TramiteTrafTranVO trafTranVOBBDD, TramiteTrafTranDto tramite, Long estado, BigDecimal idUsuario);

	Boolean comprobarNuevosTramites(TramiteTrafTranDto trafTranDto);

	boolean esTransporteBasura(TramiteTrafTranVO trafTranTrafVO);

	boolean esTransporteDinero(TramiteTrafTranVO trafTranTrafVO);

	boolean esVelMaxAutorizada(TramiteTrafTranVO trafTranTrafVO);

	boolean esVehUnidoMaquina(TramiteTrafTranVO trafTranTrafVO);

	boolean esEmpresaASC(TramiteTrafTranVO trafTranTrafVO);

	boolean esMinistCCEntLocal(TramiteTrafTranVO trafTranTrafVO);

	boolean esAutoescuela(TramiteTrafTranVO trafTranTrafVO);

	boolean esCompraventaVeh(TramiteTrafTranVO trafTranTrafVO);

	boolean esVivienda(TramiteTrafTranVO trafTranTrafVO);

}