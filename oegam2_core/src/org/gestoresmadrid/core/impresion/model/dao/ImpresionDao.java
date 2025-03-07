package org.gestoresmadrid.core.impresion.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.impresion.model.vo.ImpresionVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImpresionDao extends GenericDao<ImpresionVO>, Serializable {

	ImpresionVO getImpresion(Long idImpresion);

	ImpresionVO getImpresionPorNombreDocumento(String nombreDocumento);

	ImpresionVO getImpresionPorNombreDocumentoConEstados(String nombreDocumento, List<String> estados);

	int numeroElementos(String nombreDocumento, String tipoDocumento, String estado, Long idContrato, Date fechaDesde, Date fechaHasta, String tipoTramite, BigDecimal numExpediente) throws Exception;

	List<ImpresionVO> buscar(String nombreDocumento, String tipoDocumento, String estado, Long idContrato, Date fechaDesde, Date fechaHasta, String tipoTramite, BigDecimal numExpediente,
			int firstResult, int maxResult) throws Exception;
}
