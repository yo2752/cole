package org.gestoresmadrid.core.impr.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImprDao extends GenericDao<ImprVO>, Serializable{

	int numeroElementosConsultaTramite(Long id, String matricula, String bastidor, String nive, String tipoImpr,
			String tipoTramite, String numExpediente, String estadoSolicitud, String estadoImpresion, Date fechaInicio,
			Date fechaFin, String jefatura, Long docId, Long idContrato, String carpeta, String estadoImpr) throws Exception;

	List<Object> buscarConsultaTramite(Long id, String matricula, String bastidor, String nive, String tipoImpr,
			String tipoTramite, String numExpediente, String estadoSolicitud, String estadoImpresion, Date fechaInicio,
			Date fechaFin, String jefatura, Long docId, Long idContrato, String carpeta, String estadoImpr,
			int firstResult, int maxResults, String dir, String sort) throws Exception;

	ImprVO getImpr(Long id);

	List<ImprVO> getListaImprPorIds(List<Long> listaIds);

	List<ImprVO> getListaImprPorIds(Long[] ids);
}
