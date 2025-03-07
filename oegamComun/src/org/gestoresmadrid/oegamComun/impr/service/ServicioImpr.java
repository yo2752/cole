package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoImprBean;

public interface ServicioImpr extends Serializable {

	ResultadoImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario);

	ResultadoImprBean modificarCarpeta(Long id, String carpetaNueva, Long idUsuario);

	ResultadoImprBean dividirImprSolicitudesPantalla(String codSeleccionados);

	void solicitarImprEncarpetadosPantalla(Long idUsuario, ResultadoImprBean resultado);

	List<ImprVO> getListaExpedientesPorListNumExp(Long[] ids);

	ResultadoImprBean desasignarDocumento(Long id, Long idUsuario);

}
