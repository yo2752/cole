package org.gestoresmadrid.oegam2comun.trafico.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.InformeInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.inteve.view.bean.ResultInteveBean;
import org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans.ResultadoTramSolInfoBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.SolicitudInformeVehiculoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafSolInfoDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTrafFacturacionDto;

import escrituras.beans.ResultBean;

public interface ServicioTramiteTraficoSolInfo extends Serializable {

	public static final String NUM_EXPEDIENTE = "numExpediente";
	public static final String TRAMITE_DETALLE = "tramiteDetalle";

	/**
	 * Borra la solicitud de información para un vehiculo dentro de un trámite
	 * 
	 * @param numExpediente
	 * @param idVehiculo
	 * @return
	 */
	ResultBean borrarSolicitudVehiculo(BigDecimal numExpediente, long idVehiculo);

	/**
	 * Recupera el tramite trafico de solicitud de información con los informes
	 * @param numExpediente
	 * @return
	 */
	ResultBean getTramiteTraficoSolInfo(BigDecimal numExpediente);

	/**
	 * Guarda el tramite de solicitud de información
	 * 
	 * @param tramite
	 * @param solicitud
	 * @param idUsuario
	 * @return
	 */
	ResultBean guardarSolicitudInformacion(TramiteTrafSolInfoDto tramite, SolicitudInformeVehiculoDto solicitud, BigDecimal idUsuario, boolean admin);

	/**
	 * Guarda el tramite de solicitud de información
	 * 
	 * @param tramite
	 * @param idUsuario
	 * @return
	 */
	ResultBean guardarSolicitudesInformacion(TramiteTrafSolInfoDto tramiteDto, BigDecimal idUsuario, boolean admin);

	/**
	 * Reinicia todas las solicitudes de información del trámite
	 * 
	 * @param numExpediente
	 * @param idUsuario
	 * @return
	 */
	ResultBean reiniciarTramite(BigDecimal numExpediente, BigDecimal idUsuario);

	/**
	 * Comprueba si el trámite tiene algún fichero con los informes solicitados
	 * 
	 * @param numExpediente
	 * @return
	 */
	boolean existeFicheroInforme(BigDecimal numExpediente);

	/**
	 * Recupera el informe obtenido
	 * 
	 * @param numExpediente
	 * @return
	 */
	File getFicheroInforme(BigDecimal numExpediente);

	/**
	 * Solicita el informe AVPO directamente a Gest
	 * @param tramiteDto
	 * @param solicitudDto
	 * @param idUsuario
	 * @param numColegiado
	 * @param idContrato
	 * @return
	 */
	ResultBean obtenerInformeAVPO(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, String numColegiado, BigDecimal idContrato, boolean admin);

	/**
	 * Encola la petición para el proceso Inteve
	 * 
	 * @param tramiteDto
	 * @param solicitudDto
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 */
	ResultBean obtenerInformeInteve(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin);

	/**
	 * Encola la petición para el proceso Atem
	 * 
	 * @param tramiteDto
	 * @param solicitudDto
	 * @param idUsuario
	 * @param idContrato
	 * @return
	 */
	ResultBean obtenerInformeAtem(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin);

	SolicitudInformeVehiculoDto getTramiteTraficoSolInfoPorCodTasa(String codigoTasa);

	ResultBean obtenerInformeInteveNuevo(TramiteTrafSolInfoDto tramiteDto, SolicitudInformeVehiculoDto solicitudDto, BigDecimal idUsuario, BigDecimal idContrato, boolean admin);

	/**
	 * Devuelve true si en un plazo inferior a X horas, otro gestor ha pedido un informe para esa matrícula
	 * 
	 * @param matricula
	 * @param numColegiado
	 * @return
	 */
	boolean isSolicitudesOtrosColegiados(String matricula, String bastidor, String nive, String numColegiado);

	void actualizarTramiteConEvolucion(TramiteTrafSolInfoDto tramiteDto, EstadoTramiteTrafico estadoAnterior, BigDecimal idUsuario);

	ResultBean guardarFacturacion(TramiteTrafFacturacionDto datosFacturacion, BigDecimal idUsuario);

	TramiteTrafSolInfoVO getTramiteTraficoSolInfoVO(BigDecimal numExpediente, Boolean tramiteCompleto);

	ResultBean actualizarTramiteSolInfoVehiculo(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVehiculoVO);

	ResultBean actualizarTramiteVOConEvolucion(TramiteTrafSolInfoVO tramiteTrafSolInfoVO, 	EstadoTramiteTrafico estadoNuevo, BigDecimal idUsuario);

	TramiteTrafSolInfoDto getTramiteTraficoSolInfoDto(BigDecimal numExpediente);

	ResultadoTramSolInfoBean getTramSolInfoPantalla(BigDecimal numExpediente);

	ResultadoTramSolInfoBean guardarTramSolInfo(TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario);

	SolicitudInformeVehiculoDto getTramiteSolInfoVehiculo(Long idTramiteSolInfo, BigDecimal numExpediente);

	TramiteTrafSolInfoVehiculoVO getTramiteSolInfoVehiculoVO(Long idTramiteSolInfo, BigDecimal numExpediente);

	ResultadoTramSolInfoBean eliminarSolicitudes(String codSeleccionados, TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario, Boolean tienePermisoAdmin);

	ResultadoTramSolInfoBean generarXmlApp(TramiteTrafSolInfoDto tramiteTrafSolInfo, BigDecimal idUsuario);

	ResultInteveBean actualizarDescargaInformes(List<InformeInteveBean> listaIdSolInfoOK,
			List<InformeInteveBean> listaIdSolInfoError, BigDecimal numExpediente, BigDecimal idUsuario);

}