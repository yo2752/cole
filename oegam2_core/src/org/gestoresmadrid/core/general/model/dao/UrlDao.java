package org.gestoresmadrid.core.general.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UrlVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface UrlDao extends GenericDao<UrlVO>, Serializable {

	public List<UrlVO> list(UrlVO filter, String... initialized);
	public List<String> listPatronUrlsSecured();
	public List<String> listPatronUrlsAplicacion(String codigoAplicacion);
	public List<String> listPatronUrlsContrato(BigDecimal idContrato, BigDecimal idUsuario);

}