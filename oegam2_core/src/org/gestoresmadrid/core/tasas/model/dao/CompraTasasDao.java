package org.gestoresmadrid.core.tasas.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.tasas.model.vo.CompraTasaVO;

public interface CompraTasasDao extends GenericDao<CompraTasaVO>, Serializable {

	List<CompraTasaVO> getListaCompraTasa(Integer estado, Date fechaCompraDesde, Date fechaCompraHasta, Date fechaAltaDesde, Date fechaAltaHasta);

	CompraTasaVO guardarCompraTasa(CompraTasaVO compraTasa);

	void eliminarCompraTasa(CompraTasaVO compraTasa);

	CompraTasaVO get(Long idCompra, Long idContrato, boolean withDesglose);
}
