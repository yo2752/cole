package org.gestoresmadrid.oegam2comun.transporte.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.NotificacionTransporteDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;

import escrituras.beans.ResultBean;

public interface ServicioNotificacionesTransporte extends Serializable{
	
	public static final String NIF_EMPRESA = "nifEmpresa";
	public static final String LISTA_NOT_TRANS_DTO = "listaNotTransDto";

	ResultadoTransporteBean imprimirNotificaciones(List<NotificacionTransporteDto> listaNotificacionesTrans, ContratoDto contrato, Date fecha);

	ResultBean rechazarNotificaciones(Long idNotificacionTransporte, BigDecimal idUsuario);

	NotificacionTransporteVO getNotificacionTransporteVO(Long idNotificacionTransporte, Boolean notificacionCompleto);

	NotificacionTransporteDto getNotificacionTransporteDto(Long idNotificacionTrans, Boolean notificacionCompleta);

	ResultBean getListadoNotificacionesTransImprimir(String[] idsNotificacionesTrans, BigDecimal idContratoSession, Boolean esAdmin);

	ResultBean cambiarEstadoNotificacionesImpresion(List<NotificacionTransporteDto> listaNotificacionesDto,	BigDecimal idUsuario, Date fecha, String nombreFichero);

	List<String> getListaFicherosPdfPorIdTramite(String[] idsNotificaciones);

}
