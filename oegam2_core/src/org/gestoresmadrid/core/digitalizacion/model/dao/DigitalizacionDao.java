package org.gestoresmadrid.core.digitalizacion.model.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;


public interface DigitalizacionDao extends GenericDao<TramiteTraficoVO>,Serializable {

	List<TramiteTraficoVO> getListaDigitalizacion(String idMacroExpediente, Date fechaPresentacion,String tipoDocumento, String tipoTramite);

}
