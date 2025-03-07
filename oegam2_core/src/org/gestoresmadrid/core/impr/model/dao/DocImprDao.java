package org.gestoresmadrid.core.impr.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.core.model.dao.GenericDao;


public interface DocImprDao  extends GenericDao<DocImprVO>, Serializable{

	DocImprVO getDocImprPorId(Long idDocImpr, Boolean completo);

	int numeroElementosConsultaTramite(Long id, Long docId, String matricula, String tipoImpr, String tipoTramite,
			String numExpediente, String estado, Date fechaAltaInicio, Date fechaAltaFin, Date fechaImpresionInicio, Date fechaImpresionFin,
			String jefatura, Long idContrato, String carpeta) throws Exception;

	List<Object> buscarConsultaTramite(Long id, Long docId, String matricula, String tipoImpr, String tipoTramite,
			String numExpediente, String estado, Date fechaAltaInicio, Date fechaAltaFin, Date fechaImpresionInicio, Date fechaImpresionFin,
			String jefatura, Long idContrato, String carpeta, int firstResult, int maxResults, String dir, String sort) throws Exception;

}
