package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;

public interface ServicioPersistenciaGestionImpr extends Serializable {

	ImprVO getImprVO(Long id);

	void actualiarEstado(Long id, String estadoNuevo, Long idUsuario);

	void modificarCarpeta(Long id, String carpetaNueva, Long idUsuario);

	List<ImprVO> getListaImprPorIds(List<Long> convertirArrayStringToListLong);

	void actualizarGeneracionImpr(Long idImpr, Long idUsuario);

	List<ImprVO> getListaExpedientesPorListNumExp(Long[] ids);

	ResultadoDocImprBean generarDocImprNocturno(List<Long> listaIdsImpr, Long docId, Long idUsuario, String tipoSolicitud, Boolean esEntornoAm, Long idContrato);

	void desasignarDocumento(Long id, Long idUsuario);

}
