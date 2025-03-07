package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.GestionImprFilterBean;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;

public interface ServicioGestionarImpr extends Serializable{

	List<GestionImprFilterBean> convertirListaEnBeanPantalla(List<ImprVO> list);

	ResultadoImprBean cambiarEstadoImpr(String codSeleccionados, String estadoNuevo, Long idUsuario);

	ResultadoImprBean modificarCarpetaImpr(String codSeleccionados, String carpetaNueva, Long idUsuario);

	ResultadoImprBean solicitarImpr(String codSeleccionados, Long idUsuario);

	ResultadoImprBean generarKo(String codSeleccionados, Long idUsuario);

	ResultadoImprBean desasignarDocumento(String codSeleccionados, Long idUsuario);

}
