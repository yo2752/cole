package org.gestoresmadrid.core.historicocreditos.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoTramiteVO;
import org.gestoresmadrid.core.historicocreditos.model.vo.CreditoFacturadoVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

import utilidades.estructuras.FechaFraccionada;

public interface CreditoFacturadoDao extends GenericDao<CreditoFacturadoVO>, Serializable {

	CreditoFacturadoVO getCreditoFacturadoVO(Long id, String... initialized);

	String getTipoCredito(String tipoTramite);

	List<CreditoFacturadoVO> buscarCreditoFacturadoTramite(CreditoFacturadoTramiteVO creditoFacturadoTramite);

	List<CreditoFacturadoVO> getListaExportarHistroricoFacturado(ConceptoCreditoFacturado concepto, Long idContrato,
			String tipoCredito, String tramite, FechaFraccionada fecha );

	List<CreditoFacturadoVO> getListaCreditoFacturadoPorTramite(String tipTramite);

}
