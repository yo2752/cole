package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.EvolucionMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.EstadoMaterial;
import org.gestoresmadrid.core.trafico.materiales.model.vo.EvolucionMaterialVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.MaterialVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaEvolucionMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGuardarEvolucionMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioGuardarEvolucionMaterialImpl implements ServicioGuardarEvolucionMaterial {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8036441106460483782L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioGuardarEvolucionMaterialImpl.class);


	@Resource  EvolucionMaterialDao   evolucionMaterialDao;
	
	@Autowired ServicioConsultaEvolucionMateriales servicioConsultaEvolucionMateriales;
	
	@Override
	@Transactional
	public ResultBean guardarEvolucionMaterial(MaterialVO materialVO, EstadoMaterial estado) {
		ResultBean result = new ResultBean(Boolean.FALSE);

		try {
			EvolucionMaterialVO evolucionMaterialVO = servicioConsultaEvolucionMateriales.getEvolucionMaterialFromMaterial(materialVO, estado);
			@SuppressWarnings("unused")
			Long evolucionMaterialId = (Long) evolucionMaterialDao.guardar(evolucionMaterialVO);
			log.info("Evolucion Guardada correctamente");
		} catch (Exception ex) {
			result.setError(Boolean.TRUE);
			log.error("Evolucion no Guardada " + ex.getMessage());
		}
		
		return result;	
	}

}
