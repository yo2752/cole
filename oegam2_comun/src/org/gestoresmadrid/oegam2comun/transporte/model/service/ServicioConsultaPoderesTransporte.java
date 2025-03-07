package org.gestoresmadrid.oegam2comun.transporte.model.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.PoderesTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;

public interface ServicioConsultaPoderesTransporte extends Serializable{

	public static String NOMBRE_ZIP = "PODERES";
	
	List<PoderesTransporteBean> convertirListaEnBeanPantalla(List<PoderTransporteVO> lista);

	ResultadoTransporteBean descargarPoderesBloque(String idsPoderesTransporte);

	void borrarZip(File ficheroZip);

}
