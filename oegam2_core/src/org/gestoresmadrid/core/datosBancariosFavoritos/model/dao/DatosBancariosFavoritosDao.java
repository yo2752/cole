package org.gestoresmadrid.core.datosBancariosFavoritos.model.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.datosBancariosFavoritos.model.vo.DatosBancariosFavoritosVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface DatosBancariosFavoritosDao extends GenericDao<DatosBancariosFavoritosVO>, Serializable{

	List<DatosBancariosFavoritosVO> getListaDatosBancariosPorIdContrato(Long idContrato, BigDecimal formaPago, BigDecimal estado);

	DatosBancariosFavoritosVO getDatoBancarioFavoritoPorId(Long idDatosBancarios);

	String getDatoBancario(Long idDatoBancario);

}
