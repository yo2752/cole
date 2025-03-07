package org.gestoresmadrid.oegamInteve.service;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.inteve.model.vo.TramiteTraficoInteveVO;
import org.gestoresmadrid.oegamInteve.view.bean.ConsultaTramiteInteveBean;
import org.gestoresmadrid.oegamInteve.view.bean.ResultadoTramiteInteveBean;

public interface ServicioConsultaTramiteInteve extends Serializable{

	List<ConsultaTramiteInteveBean> convertirListaEnBeanPantallaConsulta(List<TramiteTraficoInteveVO> listaBBDD);

	ResultadoTramiteInteveBean cambiarEstado(String numsExpedientes, String estadoNuevo, BigDecimal idUsuario, Boolean tienePermisosAdmin);

	ResultadoTramiteInteveBean validarTramites(String numsExpedientes, Long idUsuario);

	ResultadoTramiteInteveBean solicitarTramites(String numsExpedientes, Long idUsuario);

	ResultadoTramiteInteveBean reiniciarEstados(String numsExpedientes, Long idUsuario);

	ResultadoTramiteInteveBean descargarSolicitudes(String ids);

	void borrarZip(File fichero);

	ResultadoTramiteInteveBean eliminarTramites(String ids, long idUsuario);

}
