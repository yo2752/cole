package org.gestoresmadrid.oegam2comun.transporte.model.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.NotificacionTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;

public interface ServicioConsultaNotificacionesTransporte extends Serializable{

	ResultadoTransporteBean rechazarNotificacionesBloque(String codSeleccionados, BigDecimal idUsuario);

	ResultadoTransporteBean imprimirNotificacionesBloque(String codSeleccionados, BigDecimal idUsuario, BigDecimal idContratoSession, Boolean esAdmin);

	List<NotificacionTransporteBean> convertirListaEnBeanPantalla(List<NotificacionTransporteVO> list);

	ResultadoTransporteBean descargarPdf(String nombreFichero);

	ResultadoTransporteBean descargarNotificacionesBloque(String idsNotificacionesTransporte);

	void borrarZip(File ficheroZip);

}
