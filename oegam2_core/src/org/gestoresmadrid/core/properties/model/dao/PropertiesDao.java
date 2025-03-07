package org.gestoresmadrid.core.properties.model.dao;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.properties.model.vo.PropertiesVO;

public interface PropertiesDao extends GenericDao<PropertiesVO>, Serializable{

	List<PropertiesVO> getListaPropertiesPorContexto(String contexto);

	List<PropertiesVO> getListaPropertiesPorContextoListeners(String contexto);

	List<PropertiesVO> getListaConsultaPropertie(String contextoProperties, String nombre, String valor);

	List<PropertiesVO> getListaRecargaPropertie(String contextoProperties, String nombre, String valor, String sort, String dir);

	PropertiesVO getPropertiePorId(Long id);
	PropertiesVO findByContextAndName(String context, String name);

	PropertiesVO getPropertiePorIdYEntorno(Long id, String entorno);

	PropertiesVO getPropertiePorNombreYEntorno(String nombre, String entorno);

}
