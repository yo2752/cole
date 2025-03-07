package org.gestoresmadrid.oegamComun.general.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.core.general.model.dao.impl.IpNoPermitidasDao;
import org.gestoresmadrid.core.general.model.vo.IpNoPermitidasVO;
import org.gestoresmadrid.oegamComun.general.service.ServicioComunIpNoValida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ServicioComunIpNoValidaImpl implements ServicioComunIpNoValida{
	
	private static final long serialVersionUID = 4913519814161527140L;
	
	@Autowired
	private IpNoPermitidasDao ipNoPermitidasDao;
	
	@Override
	public Map<String, String> obtenerIP() {
		List<IpNoPermitidasVO> tempIpPerm = ipNoPermitidasDao.getListadoIpNoPermitidas();
		if(tempIpPerm != null){
			Map<String, String> map = new HashMap<String, String>();
			for (int i = 0 ;i< tempIpPerm.size(); i++){ 
				map.put(tempIpPerm.get(i).getIp(), tempIpPerm.get(i).getMail());
			}
			
			if (map != null) {
				return map;
			}
		}
		return null;
	}
}
