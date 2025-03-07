package org.gestoresmadrid.oegamComun.impr.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;

public interface ServicioPersistenciaDocImpr extends Serializable{

	void borrarDoc(Long docId);

	Long guardar(DocImprVO docImpr);

	void guardarDocId(DocImprVO docImpr, Long idUsuario);

	DocImprVO getDocImprPorId(Long id, Boolean completo);

	ResultadoDocImprBean impresionManual(Long id, List<Long> listaIds, Long idUsuario, String jefatura);

	ResultadoDocImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario, List<Long> obtenerListaIds);

	ResultadoDocImprBean reactivar(Long id, Long idUsuario, Boolean esEntornoAm);

	ResultadoDocImprBean imprimir(Long id, List<Long> listaIds, Long idUsuario, String jefatura);


}
