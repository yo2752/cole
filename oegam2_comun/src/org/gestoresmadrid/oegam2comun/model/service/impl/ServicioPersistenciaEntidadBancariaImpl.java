package org.gestoresmadrid.oegam2comun.model.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.general.model.dao.EntidadBancariaDao;
import org.gestoresmadrid.core.general.model.vo.EntidadBancariaVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPersistenciaEntidadBancaria;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaEntidadBancariaImpl implements ServicioPersistenciaEntidadBancaria, Serializable {

	private static final long serialVersionUID = 802977541135157715L;

	@Autowired
	private EntidadBancariaDao entidadBancariaDao;

	@Override
	@Transactional(readOnly = true)
	public List<DatoMaestroBean> listAll() {
		List<DatoMaestroBean> result = new ArrayList<>();
		try {
			List<EntidadBancariaVO> listVos = entidadBancariaDao.buscar(null);
			for (EntidadBancariaVO entidad : listVos) {
				entidadBancariaDao.evict(entidad);
				result.add(new DatoMaestroBean(entidad.getCodigoEntidad(), entidad.getDescripcion()));
			}
		} catch (HibernateException e) {
		}
		return result;
	}

	public EntidadBancariaDao getEntidadBancariaDao() {
		return entidadBancariaDao;
	}

	public void setEntidadBancariaDao(EntidadBancariaDao entidadBancariaDao) {
		this.entidadBancariaDao = entidadBancariaDao;
	}

}