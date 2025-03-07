package org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.evolucionDatosBancariosFavoritos.modelo.vo.EvolucionDatosBancariosFavoritosVO;
import org.gestoresmadrid.oegam2comun.evolucionDatosBancariosFavoritos.view.beans.EvolucionDatosBancariosFavoritosBean;

import escrituras.beans.ResultBean;

public interface ServicioEvolucionDatosBancariosFavoritos extends Serializable {

	public static String CAMPO_MOD_CONTRATO = "CONTRATO";
	public static String CAMPO_MOD_DESCRIPCION = "DESCRIPCION";

	List<EvolucionDatosBancariosFavoritosBean> convertirListaEnBeanPantalla(List<EvolucionDatosBancariosFavoritosVO> list);

	ResultBean guardarEvolucion(EvolucionDatosBancariosFavoritosVO evolucionDatosBancariosFavoritosVO);

}