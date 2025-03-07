package org.gestoresmadrid.oegam2comun.tasas.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafSolInfoVehiculoVO;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCambioServicioBean;
import org.gestoresmadrid.oegamComun.tasa.view.bean.ResultadoTasaBean;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;

import escrituras.beans.ResultBean;

public interface ServicioTasa extends Serializable {

	public static final String TIPO_TASA_4_1 = "4.1";
	public static final String TIPO_TASA_4_4 = "4.4";

	TasaDto getTasaCodigoTasa(String codigoTasa);

	TasaDto getTasaExpediente(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	TasaDto getTasaExpedienteDuplicados(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	ResultBean desasignarTasaExpediente(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	ResultBean desasignarTasaExpedienteDuplicados(String codigoTasaNuevo,String codigoTasaAntiguo, BigDecimal numExpediente, String tipoTasa);

	ResultBean desasignarTasa(TasaDto tasa);

	boolean sePuedeAsignarTasa(String codigoTasa, BigDecimal numExpediente);

	ResultBean asignarTasaExpediente(String codigoTasa, BigDecimal numExpediente);

	boolean comprobarTasa(String codigoTasa, BigDecimal numExpediente);

	ResultBean asignarTasa(String codigoTasa, BigDecimal numExpediente);

	TasaDto getTasaPegatinaCodigoTasa(String codigoTasa);

	String eliminarTasaPegatina(String codigoTasa);

	List<TasaDto> obtenerTasasContrato(Long idContrato, String tipoTasa);

	TasaDto getDetalleTasaCargada(String codigoTasa, Integer formatoTasa);

	List<TasaDto> getListaTasasPegatinasPorCodigos(List<String> asList);

	ResultBean desasignarTasaInforme(TramiteTrafSolInfoVehiculoVO tramiteTrafSolInfoVO);

	ResultBean asignarMicroTasa(String codigoTasa, String numExpedientes, BigDecimal idUsuario, Date fecha);

	ResultBean desasignarMicroTasa(String codigoTasa, BigDecimal idUsuario);

	TasaVO getTasaVO(String codigoTasa);

	ResultadoAtex5Bean getTasaAtex5(Long idContrato);

	ResultBean desasignarTasaDuplicar(String codigoTasa, BigDecimal idUsuario);

	ResultBean desasignarTasaInteve(String codigoTasa, BigDecimal numExpediente, String tipoTasa, BigDecimal idUsuario);

	ResultadoCambioServicioBean desasignarTasaExp(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	ResultadoCambioServicioBean asignarTasaExp(String codigoTasa, BigDecimal numExpediente, String tipoTasa);

	ResultBean cambiarFormato(String codigosTasaSeleccion, Integer formato, BigDecimal idUsuario);

	ResultBean bloquearTasa(String codigosTasaSeleccion, String motivo, BigDecimal idUsuario);

	ResultBean desbloquearTasa(String codigosTasaSeleccion, BigDecimal idUsuario);

	List<TasaDto> obtenerTodasTasasContrato(Long idContrato, String tipoTasa);

	ResultadoTasaBean cambiarAlmacen(String idCodigoTasaSeleccion, String almacenNuevo, BigDecimal idUsuario);

	List<TasaDto> obtenerTasasMatwContrato(Long idContratTramite, String tipoTasa);

	List<TasaDto> obtenerTasasCtitContrato(Long idContrato, String tipoTasa);

	List<TasaDto> obtenerTasasBajaContrato(Long idContrato, String tipoTasa);

	List<TasaDto> obtenerTasasDuplicadoContrato(Long idContrato, String tipoTasa);

	List<TasaDto> obtenerTasasPermIntContrato(Long idContrato, String tipoTasa);

	List<TasaDto> obtenerTasasInteveContrato(Long idContrato, String tipoTasa);

	List<TasaDto> getTasasLibres(Long idContrato, String tipoTasa);

	List<TasaDto> getTasasPorNumExpediente(BigDecimal numExpediente);

	ResultBean desasignarTasaXml(String codigoTasa, BigDecimal numExpediente, BigDecimal bigDecimal);

}