package org.gestoresmadrid.oegam2comun.permisoDistintivoItv.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.GestionPermisosFichasDgtBean;
import org.gestoresmadrid.oegam2comun.permisoDistintivoItv.view.bean.ResultadoPermisoDistintivoItvBean;

public interface ServicioGestionPermisosFichasDgt extends Serializable{

	List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaConsultaPermisos(List<TramiteTraficoVO> listaVo);

	ResultadoPermisoDistintivoItvBean solicitarPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean imprimirPermisos(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean revertirPermisos(String numExpedientes, BigDecimal idUsuario, Boolean tienePermisosAdmin, String ipConexion);

	List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaGestionFichas(List<TramiteTraficoVO> listaVo);

	ResultadoPermisoDistintivoItvBean solicitarFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean imprimirFichas(String numExpedientes, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean revertirFichas(String numExpedientes, BigDecimal idUsuario, Boolean tienePermisosAdmin, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocKOPermisos(String codSeleccionados, BigDecimal idUsuario, String ipConexion);

	List<GestionPermisosFichasDgtBean> convertirListaEnBeanPantallaMasivo(List<TramiteTraficoVO> lista);

	ResultadoPermisoDistintivoItvBean cambiarEstadoPermisos(String codSeleccionados, BigDecimal estadoNuevo,BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean generarDocKOFichas(String codSeleccionados, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean cambiarEstadoFichas(String codSeleccionados, BigDecimal estadoNuevo, BigDecimal idUsuario, String ipConexion);

	ResultadoPermisoDistintivoItvBean desasignarPermisos(String codSeleccionados, BigDecimal idUsuarioDeSesion, String ipConexion);

	ResultadoPermisoDistintivoItvBean desasignarFichas(String codSeleccionados, BigDecimal idUsuario, String ipConexion);

}
