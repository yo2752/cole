package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.materiales.model.values.ConsumoMaterialValue;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.distintivo.view.bean.ResultadoDistintivoDgtBean;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoMatriculacionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.DatosCardMatwDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

public interface ServicioTramiteTraficoMatriculacion extends Serializable {

	public static String NUMEXPEDIENTE = "NUMEXPEDIENTE";
	public static String ESTADO_VALIDAR = "estadoValidar";
	public static String TITULAR_LIBERACION = "titularLiberacionDto";
	public static String NOMBRE_HOST_PROCESOS_3 = "nombreHostSolicitudProcesos3";
	public static final String antiguoDatosSensibles = "datosSensibles.antiguo";
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";
	public static final String NOMBRE_HOST_SOLICITUD2 = "nombreHostSolicitudProcesos2";
	public static final String DIRECCION_MAIL_GENERAR_PERMISO = "solicitud.generar.permiso.mail.bcc";
	public static final String SUBJECT_PERMISO = "subject.generar.permiso";
	public static final String SUBJECT_DISTINTIVO = "subject.generar.distintivo";
	public static final String SUBJECT_EITV = "subject.generar.eitv";

	public static final String CONTRATO_COLEGIO = "contrato.colegio";
	public static final String USUARIO_ADMIN = "usuario.admin";

	public static final String CODIGO_FUNCION_FICHA_TECNICA = "OT13M";
	public static final String CODIGO_APLICACION_TRAFICO = "OEGAM_TRAF";

	ResultBean guardarTramite(TramiteTrafMatrDto tramiteDto, IvtmMatriculacionDto ivtmMatriculacionDto, BigDecimal idUsuario, boolean permisoAdmin, boolean permisoLiberacion, boolean desdeValidar)
			throws OegamExcepcion;

	TramiteTrafMatrDto getTramiteMatriculacion(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto) throws OegamExcepcion;

	void guardarOActualizar(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion;

	boolean tramiteModificadoParaLiberacion(TramiteTrafMatrDto tramitePantalla) throws OegamExcepcion;

	ResultBean matriculado(TramiteTrafMatrDto tramiteDto, String matricula, BigDecimal idUsuario, boolean admin);

	ResultBean tramitacion(BigDecimal numExpediente, BigDecimal idUsuario, String aliasColegiado, String colegio, boolean permisoLiberacion, BigDecimal idContrato) throws Throwable;

	ResultBean validarTramite(TramiteTrafMatrDto tramiteDto) throws ParseException;

	DatosCardMatwDto datosCardMatw(BigDecimal numExpediente);

	TramiteTrafMatrVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente);

	void actualizarPegatina(BigDecimal numExpediente) throws OegamExcepcion;

	void actualizarRespuestaMatriculacion(BigDecimal numExpediente, String respuesta);

	void actualizarRespuesta576Matriculacion(BigDecimal numExpediente, String respuesta);

	ResultBean liberarTramiteMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario);

	ResultBean crearSolcitudMatriculacionConCambioEstado(String numExpediente, BigDecimal idUsuario, BigDecimal idContrato, EstadoTramiteTrafico estadoNuevo, EstadoTramiteTrafico estadoAnt);

	TramiteTrafMatrVO getTramiteMatriculacionVO(BigDecimal numExpediente, boolean permisoLiberacion, boolean tramiteCompleto);

	ResultBean actualizarEstadoFinalizadoConError(BigDecimal numExpediente);

	List<TramiteTrafMatrVO> getListaTramitesMatriculacion(BigDecimal[] listaNumsExpedientes, Boolean tramiteCompleto);

	ResultadoMatriculacionBean obtenerFicheroPresentacion576(String numExpediente);

	ContratoDto getContratoTramite(String numExpediente);

	ResultadoPermisoDistintivoItvBean generarDistintivo(List<TramiteTrafMatrVO> listaTramites, BigDecimal idUsuario, Long idDocPermDstvEitv, Date fecha, Long idContrato, TipoDistintivo tipoDistintivo,
			String docId);

	ResultadoDistintivoDgtBean actualizarTramiteMatwDstv(TramiteTrafMatrVO tramiteTraficoMatr, Boolean tieneDist, String tipoDistintivo,
			BigDecimal idUsuario, String ipConexion);

	void guardarOActualizar(TramiteTrafMatrVO tramiteTrafMatrVO) throws OegamExcepcion;

	List<ContratoDto> getListaIdsContratosConDistintivos();

	List<ContratoDto> getListaIdsContratosSinDstvFinalizados(Date fechaPresentacion);

	ResultadoMatriculacionBean obtenerJustificanteIVTM(String numExpediente);

	ResultadoMatriculacionBean subirJustificanteIVTM(File fichero, String numExpediente);

	List<TramiteTrafMatrVO> getListaTramitesPendientesImprimirDstvConFechaAnterior(Date fechaPresentacion);

	ResultadoPermisoDistintivoItvBean comprobarSuperTelematicoMate(List<IntervinienteTraficoVO> listaIntervinientes, BigDecimal numExpediente, Long idContrato);

	boolean esSupertelematico(BigDecimal numExpediente, TramiteTrafMatrVO tramiteTrafMatrVO, Boolean esListadoBastidores);

	ResultadoPermisoDistintivoItvBean actualizarEstadoImpresionDocDistintivos(Long idDocDstv, EstadoPermisoDistintivoItv impresionOk, BigDecimal idUsuario,
			String docId, String ipConexion);

	List<TramiteTrafMatrVO> getListaTramitesDistintivosPorDocId(Long idDocPermDistItv);

	Integer getCountNumTramitesDstv(Long idDocDist);

	ResultadoPermisoDistintivoItvBean actualizarEstadoImpresionDistintivos(Set<TramiteTrafMatrVO> listaTramitesDistintivo, EstadoPermisoDistintivoItv impresionOk);

	ResultadoPermisoDistintivoItvBean tieneTramitesValidosIMPRPorContrato(ContratoDto contratoDto, Date fecha, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> getListaTramitesSTPorFechaContrato(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> getListaTramitesContratoDistintivos(Long idContrato);

	ResultadoDistintivoDgtBean indicarDocIdTramites(List<BigDecimal> listaTramites, Long idDocPermDstvEitv, BigDecimal idUsuario, Date fecha, String docId, String ipConexion);

	List<ConsumoMaterialValue> getListaConsumoDstvJefaturaPorDia(String jefatura, Date fecha);

	ResultadoDistintivoDgtBean existeTramiteDstvDuplicado(String matricula, Long idContrato);

	List<TramiteTrafMatrVO> getTramitePorMatriculaContrato(String matricula, Long idContrato);

	List<TramiteTrafMatrVO> getListaTramitesPorMatricula(String matricula);

	// Estadísticas MATE

	Integer obtenerTotalesTramitesMATE(boolean esTelematico, String delegacion, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	List<Object[]> obtenerTramitesMATETelJefatura(String delegacion, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	List<Object[]> obtenerTramitesMATETelTipoVehiculo(String provincia, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	List<Object[]> obtenerTramitesMATETelCarburante(String provincia, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	Integer obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	Integer obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	List<Object[]> obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(String provincia, String jefatura, Fecha fechaDesde, Fecha fechaHasta) throws ParseException;

	ResultadoPermisoDistintivoItvBean tieneTramitesFinalizadosTelematicamentePorContrato(ContratoDto contrato, Date fecha, String tipoTramiteSolicitud);

	List<TramiteTrafMatrVO> getListaTramitesFinalizadosTelematicamenteSinImpr(Long idContrato, Date fechaPresentacion, String tipoTramiteSolicitud);

	ResultadoPermisoDistintivoItvBean listaTramitesFinalizadosTelmPorContrato(ContratoDto contratoDto, Date fecha, String tipoSolicitud, Boolean esMoto);

	ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosDistintivos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo,
			BigDecimal idUsuario, String docId, String ipConexion);

	void actualizarEstadoImpresionDistintivo(BigDecimal numExpediente, String estadoAnt, String estadoNuevo, BigDecimal idUsuario, Date fecha,
			String docId, String ipConexion);

	boolean esTransporteBasura(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esTransporteDinero(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esVelMaxAutorizada(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esVehUnidoMaquina(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esEmpresaASC(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esMinistCCEntLocal(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esAutoescuela(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esCompraventaVeh(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esVivienda(TramiteTrafMatrVO trafMatrVO, ResultBean respuesta);

	boolean esUnaFichaA(String tipoTarjetaITV);

	void actualizarCampoAutorizacion(TramiteTraficoVO tramiteTrafico);

}