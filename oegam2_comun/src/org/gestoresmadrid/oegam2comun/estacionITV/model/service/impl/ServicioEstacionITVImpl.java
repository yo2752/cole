package org.gestoresmadrid.oegam2comun.estacionITV.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.estacionITV.model.dao.EstacionITVDao;
import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.estacionITV.view.bean.EstacionITV;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import trafico.beans.EstacionITVBean;

@Service
@Transactional
public class ServicioEstacionITVImpl implements ServicioEstacionITV{
	
	@Autowired
	EstacionITVDao estacionITVDao;

	@Autowired
	private Conversor conversor;

	@Override
	public ResultBean altaEstacionITV(EstacionITV estacion) {
		ResultBean resultado = new ResultBean();
		EstacionITVVO estacionVO = conversor.transform(estacion, EstacionITVVO.class);
		
		List<EstacionITVVO> listadoPosibleEstacion = getEstacion(estacion.getId());
		
		if(listadoPosibleEstacion == null || listadoPosibleEstacion.isEmpty() ){
			estacionITVDao.guardar(estacionVO);
		}else{
			resultado.setError(true);
			resultado.setMensaje("El id de la estación ya esta en uso: "+listadoPosibleEstacion.get(0).getProvincia() + ", " + listadoPosibleEstacion.get(0).getMunicipio());
		}
				
		return resultado;
		
	}
	
	@Override
	public List<EstacionITVVO> getEstacion(String id){
		EstacionITVVO estacion = new EstacionITVVO();
		estacion.setId(id);
		return estacionITVDao.buscar(estacion);
	}
	
	@Override
	@Transactional
	public List<EstacionITVBean> getEstacionesItv(String idProvincia) {
		List<EstacionITVBean> estacionITVBean = new ArrayList<EstacionITVBean>();
		List<EstacionITVVO> estacionesITVVO = estacionITVDao.getEstacionesItv(idProvincia);
		if (estacionesITVVO != null && !estacionesITVVO.isEmpty()) {
			estacionITVBean = conversor.transform(estacionesITVVO, EstacionITVBean.class);

			for (EstacionITVBean estacion : estacionITVBean) {
				estacion.setMunicipio(estacion.getEstacionITV() + " - " + estacion.getMunicipio());
			}
		}
		return estacionITVBean;
	}

}
