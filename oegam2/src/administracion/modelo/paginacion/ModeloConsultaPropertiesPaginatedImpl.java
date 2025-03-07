package administracion.modelo.paginacion;

import javax.annotation.Resource;

import org.gestoresmadrid.core.model.dao.GenericDao;
import org.gestoresmadrid.core.pagination.model.component.impl.AbstractModelPagination;
import org.gestoresmadrid.core.properties.model.dao.PropertiesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "modeloConsultaPropertiesPaginated")
@Transactional(readOnly = true)
public class ModeloConsultaPropertiesPaginatedImpl extends AbstractModelPagination {

	@Resource
	private PropertiesDao propertiesDao;

	@Override
	protected GenericDao<?> getDao() {
		return propertiesDao;
	}

	public PropertiesDao getPropertiesDao() {
		return propertiesDao;
	}

	public void setPropertiesDao(PropertiesDao propertiesDao) {
		this.propertiesDao = propertiesDao;
	}

}
