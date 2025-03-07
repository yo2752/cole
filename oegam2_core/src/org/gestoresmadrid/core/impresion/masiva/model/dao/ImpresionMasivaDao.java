package org.gestoresmadrid.core.impresion.masiva.model.dao;

import java.io.Serializable;

import org.gestoresmadrid.core.impresion.masiva.model.vo.ImpresionMasivaVO;
import org.gestoresmadrid.core.model.dao.GenericDao;

public interface ImpresionMasivaDao extends GenericDao<ImpresionMasivaVO>, Serializable {

	ImpresionMasivaVO getImpresionMasivaPorNombreFichero(String nombreFichero);
}
