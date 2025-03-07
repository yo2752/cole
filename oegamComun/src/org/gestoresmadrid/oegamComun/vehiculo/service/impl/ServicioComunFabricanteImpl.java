package org.gestoresmadrid.oegamComun.vehiculo.service.impl;

import org.gestoresmadrid.core.vehiculo.model.dao.FabricanteDao;
import org.gestoresmadrid.core.vehiculo.model.vo.FabricanteVO;
import org.gestoresmadrid.oegamComun.vehiculo.service.ServicioComunFabricante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComunFabricanteImpl implements ServicioComunFabricante{

	private static final long serialVersionUID = 716972602267373405L;

	@Autowired	
	FabricanteDao fabricanteDao;
	
	@Override
	@Transactional(readOnly=true)
	public FabricanteVO getFabricante(String fabricante) {
		return fabricanteDao.getFabricante(fabricante);
	}
}
