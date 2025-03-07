package org.gestoresmadrid.oegamComun.impr.service;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionDocImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;

import utilidades.web.OegamExcepcion;

public interface ServicioGestionarDocImpr extends Serializable{

	List<GestionDocImprFilterBean> convertirListaEnBeanPantalla(List<DocImprVO> list);

	ResultadoDocImprBean impresionManual(String codSeleccionados, Long idUsuario, String jefatura);

	ResultadoDocImprBean descargaManual(String codSeleccionados, Boolean esEntornoAm) throws OegamExcepcion;

	void borrarFichero(File fichero);

	ResultadoDocImprBean cambiarEstado(String codSeleccionados, String estadoNuevo, Long idUsuario);

	ResultadoDocImprBean reactivar(String codSeleccionados, Long idUsuario, Boolean esEntornoAm);

	ResultadoDocImprBean imprimir(String codSeleccionados, Long idUsuario, String jefatura);


}
