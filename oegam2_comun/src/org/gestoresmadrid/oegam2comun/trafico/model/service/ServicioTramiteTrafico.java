package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.model.beans.AliasQueryBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.presentacion.jpt.model.beans.ResumenJPTBean;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoSolInteveVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafCambioServicioVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafTranVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.oegam2comun.estadisticas.listados.view.beans.ListadoGeneralYPersonalizadoBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;
import org.gestoresmadrid.oegam2comun.trafico.estadisticas.ctit.diarias.bean.RespuestaEstadisticasTransmisionBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafTranDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.FormularioAutorizarTramitesDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.hibernate.HibernateException;
import org.hibernate.criterion.ProjectionList;
import org.viafirma.cliente.exception.InternalException;

import escrituras.beans.ResultBean;
import escrituras.beans.ResultadoAccionUsuarioBean;
import trafico.utiles.enumerados.TipoTransferencia;
import trafico.utiles.enumerados.TipoTransferenciaActual;
import utilidades.web.OegamExcepcion;

public interface ServicioTramiteTrafico extends Serializable {

	public static String TRAMITE_TRAFICO = "tramiteTrafico";
	public static String direccionEstadisticasTransmisiones = "estadisticas.transmision.mail.cc";
	public static String dirOcultaEstadisticasTransmisiones = "estadisticas.transmision.mail.bcc";
	public static String subjectEstadisticasTranmisiones = "subject.estadisticas.transmision";

	TramiteTraficoVO getTramite(BigDecimal numExpediente, boolean tramiteCompleto);
	
	TramiteTraficoSolInteveVO getTramiteInteve(BigDecimal numExpediente, String codigoTasa);

	TramiteTrafDto getTramiteDto(BigDecimal numExpediente, boolean tramiteCompleto);

	TramiteTrafBajaVO getTramiteBaja(BigDecimal numExpediente, boolean tramiteCompleto);

	List<TramiteTrafBajaVO> bajasExcel(String jefatura);

	List<TramiteTrafDuplicadoVO> duplicadosExcel(String jefatura);

	TramiteTrafTranVO getTramiteTransmision(BigDecimal numExpediente, boolean tramiteCompleto);

	TramiteTrafDuplicadoVO getTramiteDuplicado(BigDecimal numExpediente, boolean tramiteCompleto);

	TramiteTrafCambioServicioVO getTramiteCambioServicio(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean custodiar(TramiteTrafDto tramiteDto, String aliasColegiado) throws UnsupportedEncodingException, InternalException;

	ResultBean guardarActualizarTramiteFacturacion(TramiteTrafFacturacionDto tramiteFacturacionDto);

	ResultBean getTipoTramite(String[] arrayExpediente);

	ResultBean cambiarEstadoConEvolucion(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, boolean notificar, String textoNotificacion,
			BigDecimal idUsuario);

	ResultBean cambiarEstadoSinValidacion(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, BigDecimal idUsuario, boolean notificar,
			String textoNotificacion);

	ResultBean cambiarEstadoConEvolucionEstadosPermitidos(BigDecimal numExpediente, EstadoTramiteTrafico antiguoEstado, EstadoTramiteTrafico nuevoEstado, EstadoTramiteTrafico[] estadosPermitidos,
			boolean notificar, String textoNotificacion, BigDecimal idUsuario);

	ResultBean cambiarEstadoConEvolucionEstadosPermitidos(BigDecimal numExpediente, EstadoTramiteTrafico nuevoEstado, EstadoTramiteTrafico[] estadosPermitidos, boolean notificar,
			String textoNotificacion, BigDecimal idUsuario);

	String getRespuestaTramiteTrafico(BigDecimal numExpediente);

	/**
	 * Confirma la presentación en jefatura, en fecha actual, de los trámites asociados a ese docId
	 * @param docId docId del documento base que contiene la relación de expedientes
	 * @param idDoc identificador del documento base que contiene los trámites presentados
	 * @return número de trámites actualizados
	 * @throws HibernateException
	 */
	int presentarJPT(String idDoc);

	ResumenJPTBean getTramitesDocumentoBase(String idDoc);

	/**
	 * Obtiene los trámites que no se han presentado en JPT
	 * @return Los trámites que no se han presentado
	 * @throws HibernateException
	 */
	List<TramiteTraficoVO> obtenerNoPresentados();

	/**
	 * Obtiene los trámites que se presentaron en JPT
	 * @param fechaPresentado Fecha de presentación de esos trámites
	 * @param carpeta Carpeta/Tipo Trámite
	 * @return Los trámites que se han presentado
	 * @throws HibernateException
	 */
	List<TramiteTraficoVO> obtenerPresentados(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	int getCantidadTipoEstadistica(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	int getCantidadTipoEstadisticaNive(Date fechaPresentado, String jefaturaJpt) throws Exception;

	int getNumColegiadosDistintos(Date fechaPresentado, String carpeta, String jefaturaJpt) throws Exception;

	int getNumColegiadosDistintosNive(Date fechaPresentado, String jefaturaJpt) throws Exception;

	int getNumTramitePorVehiculo(BigDecimal numExpediente, Long idVehiculo);

	String getMismoTipoCreditoTramiteTransmision(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramite);

	List<Long> getListaTramitesMismoContratoPorExpedientes(BigDecimal[] numerosExpedientes);

	TipoTramiteTrafico recuperarTipoTramiteSiEsElMismo(BigDecimal[] numerosExpedientes);

	int recuperarTipoTramiteMatriculacionSiEsElMismo(BigDecimal[] numerosExpedientes);

	TipoTransferenciaActual getTipoTransferenciaActual(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramiteTrafico);

	TipoTransferencia getTipoTransferencia(BigDecimal[] numerosExpedientes, TipoTramiteTrafico tipoTramiteTrafico);

	String validarRelacionMatriculas(String[] numExpedientes);

	ResultBean validarPDF417(String[] numExps);

	ResultBean validarPDFPresentacionTelematica(String[] numExps);

	ResultBean comprobarEstadosTramites(BigDecimal[] numExp, EstadoTramiteTrafico[] estados);

	BigDecimal crearTramite(String numColegiado, String tipoTramite, BigDecimal idContrato, BigDecimal idUsuario, Date fechaAlta) throws Exception;

	BigDecimal generarNumExpediente(String numColegiado) throws Exception;

	BigDecimal generarNumExpedienteFacturacionTasa(String numColegiado) throws Exception;

	void guardarEvolucionTramiteClonado(TramiteTraficoVO tramiteTraficoVO);

	ResultBean cambiarEstadoSinEvolucion(BigDecimal numExpediente, String estado);

	TramiteTraficoVO actualizarRespuestaMateEitv(String respuesta, BigDecimal numExpediente);

	List<TramiteTraficoVO> recuperaTramitesBloqueantes();

	RespuestaEstadisticasTransmisionBean getListadoTransmisionesDiarios();

	void cambiarAPresentado(List<TramiteTraficoVO> listaTramitesTraficoVO) throws OegamExcepcion;

	void actualizarTramite(TramiteTraficoVO tramite);

	Boolean comprobarEstadosTramitesAnulados(BigDecimal[] numerosExpedientes);

	TramiteTrafFacturacionDto getTramiteFacturacion(BigDecimal numExpediente);

	boolean esImprimiblePdf(String numExpediente);

	String obtenerNumColegiadoByNumExpediente(BigDecimal numExpediente);

	ResultBean liberarDocBaseNive(BigDecimal numExpediente, BigDecimal idUsuario);

	Object[] listarTramitesFinalizadosPorTiempo(String valorEnum, List<BigDecimal> estadosOk, List<BigDecimal> estadosKo, List<String> tipoTransferencia, int segundos);

	void borrar(BigDecimal numExpediente);

	ResultBean cambiarEstadoFinalizadoError(BigDecimal numExpediente, BigDecimal idUsuario);

	TramiteTrafDto getTramiteDtoImpresion05(BigDecimal numExpediente, Boolean tramiteCompleto);

	List<TramiteTraficoDto> getListaExpedientesPantallaPorTasa(String codigoTasa);

	ResultBean desasignarTasaTramites(TramiteTraficoVO tramiteTraficoVO, String codigoTasa);

	List<TramiteTraficoVO> getListaExpedientesPorTasa(String codigoTasa);

	// List<TramiteTrafDto> getListaTramiteTrafico(String[] numsExpedientes, Boolean tramiteCompleto);

	List<TramiteTrafDto> getListaTramiteTrafico(String[] numsExpedientes, boolean tramiteCompleto);

	ResultBean desasignarDuplicarTramite(BigDecimal numExpediente, String tipoTramite, BigDecimal idUsuario, String desasignarTasaAlDuplicar);

	ResultBean cambiarEstadoConGestionCreditos(String numExpediente, String nuevoEstado, BigDecimal idUsuario);

	List<TramiteTraficoVO> getListaExpedientesPorListNumExp(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	List<TramiteTraficoVO> getListaTramitesPermisosPorDocId(Long idDocPermisos);

	List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocId(Long idDocFichasTecnicas);

	ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo);

	ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo);

	Integer getCountNumTramitesPermisos(Long idDocPermDistItv);

	Integer getCountNumTramitesFichas(Long idDocFichas);

	ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo,
			BigDecimal idUsuario, String docId, String ipConexion);

	ResultadoPermisoDistintivoItvBean actualizarTramitesImpresionFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo,
			BigDecimal idUsuario, String docId, String ipConexion);

	// void actualizarTramiteTransmisionBean(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean);

	List<Long> getListaIdsContratosFinalizadosTelematicamente(Date fechaPresentacion);

	ResultadoPermisoDistintivoItvBean tieneTramitesFinalizadosCtit(ContratoDto contratoDto, Date fechaPresentacion);

	List<TramiteTraficoVO> getListaTramitesPorNifTipoIntervienteYFecha(String cifEmpresa, BigDecimal idContrato, String tipoInterviniente, Date fechaPresentacion, String tipoTramite);

	List<TramiteTraficoVO> getlistaTramitesCtitFinalizadosPorContratoYFecha(Long idContrato, Date fechaPresentacion);

	List<TramiteTraficoVO> getListaTramitesPermisosPorDocIdErroneos(Long docId);

	List<TramiteTraficoVO> getListaTramitesFichasTecnicasPorDocIdErroneos(Long docId);

	List<TramiteTraficoVO> getListaTramitesKOPendientesPorJefatura(String jefatura);

	ResultadoPermisoDistintivoItvBean getTramitesFinalizadosTelematicamentePorContrato(Long idContrato, Date fecha);

	List<TramiteTrafCambioServicioVO> cambioServicioExcel(String jefatura);

	ResultBean guardarOActualizarCS(TramiteTrafDto tramiteTraficoDto);

	List<TramiteTraficoVO> getListaTramitesTraficoVO(BigDecimal[] numExpedientes, Boolean tramiteCompleto);

	ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosPermisos(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo,
			BigDecimal idUsuario, String docId, String ipConexion);

	ResultadoPermisoDistintivoItvBean actualizarTramitesAnuladosFichas(Long idDocPermDistItv, EstadoPermisoDistintivoItv estadoNuevo,
			BigDecimal idUsuario, String docId, String ipConexion);

	void evictTramiteTransmision(TramiteTrafTranVO tramiteTrafTranVO);

	void evictTramiteBaja(TramiteTrafBajaVO tramiteBaja);

	List<TramiteTraficoVO> getListaExpedientesGenDocBase(BigDecimal[] numExpedientes);

	TramiteTraficoVO primeraMatricula(Date fechaActual) throws Exception;

	TramiteTraficoVO ultimaMatricula(Date fechaActual) throws Exception;

	TramiteTraficoDto getTramiteTraficoDto(BigDecimal numExpediente, boolean tramiteCompleto);

	ResultBean cambiarEstadoRevisado(BigDecimal numExpediente, Long idUsuario);

	ResultBean asignarTasaLibre(BigDecimal numExpediente, Long idUsuario);

	String getNifFacturacionPorNumExp(String numeroExpediente);

	String getTipoTramite(BigDecimal numExpediente);

	MarcaDgtVO recuperarMarcaConFabricantes(String codigoMarca);

	ResultBean getExcelResumenNRE06PorFecha(Date fechaInicio, Date fechaFin) throws Exception;

	ResultBean actualizarFechaPresentacion(BigDecimal bigDecimal, Date fechaPresentacion);

	ResultBean generarExcelListadoVehiculosEstadisticas(Object filter);

	ResultBean validarAgrupacionListadoGeneralYPersonalizado(ListadoGeneralYPersonalizadoBean bean);

	int numeroElementosListadosEstadisticas(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, String agrupacion);

	int numeroElementosListadosEstadisticasConListaProyeccion(Object filter, String[] fetchModeJoinList, List<AliasQueryBean> entitiesJoin, ProjectionList listaProyecciones, String agrupacion);

	ResultBean generarExcelListadoGeneralYPersonalizadoEstadisticas(Object filter);

	ResultBean modificarReferenciaPropia(BigDecimal numExpediente, BigDecimal idUsuario, String nuevaReferenciaPropia);

	ResultBean autorizarCertificacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto) throws NoSuchAlgorithmException, IOException;

	ResultBean denegarAutorizacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario);

	ResultBean cambiarEstado(BigDecimal numExpediente, BigDecimal estadoNuevo, Long idUsuario);

	ResultBean guardarTramiteCertificacion(String numExpediente, String hash, String version);

	ResultBean firmarAutorizacion(String numExpediente, BigDecimal idContrato);

	ResultBean enviarMailDenegarColegiado(TramiteTrafTranDto trafTranDto, TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, String tipoTramite);

	ResultBean enviarMailAutorizarColegiado(TramiteTrafTranDto trafTranDto, TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, String tipoTramite);

	ResultBean autorizacionInicialCertificacion(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, String opcionSeleccionada) throws NoSuchAlgorithmException, IOException;

	ResultadoAccionUsuarioBean borrarDatos(String codSeleccionados, String datoBorrar, BigDecimal idUsuario);

	void guardarActionBorrarDatos(String numExpediente, String accion, ResultadoAccionUsuarioBean resultado, String ipAccesso);

	ResultadoBean gestionDesasignarTasaXMLExpediente(TasaVO tasaEnviadaEnXML, Long idContrato, String tipoTasa, Long idUsuario);

	ResultadoAccionUsuarioBean actualizarPueblo(String codSeleccionados, String puebloNuevo, String interviniente, BigDecimal idUsuario);

	ResultBean aceptarAutorizacionTransmision(TramiteTrafTranDto trafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion;

	ResultBean aceptarAutorizacionMatw(FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, TramiteTrafMatrDto trafMatrDto, BigDecimal idUsuario);

	ResultBean guardardadoValorAdicional(TramiteTrafTranDto tramiteTrafTranDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion;

	ResultBean cambiarEstadoAutorizacion(TramiteTrafTranDto trafTranDto,TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario);

	ResultBean guardardadoValorAdicionalMatw(TramiteTrafMatrDto trafMatrDto, FormularioAutorizarTramitesDto formularioAutorizarTramitesDto, BigDecimal idUsuario);

	ResultadoAccionUsuarioBean actualizarDatos(String codSeleccionados, String datoActualizar, String datoNuevo, BigDecimal idUsuario);
}