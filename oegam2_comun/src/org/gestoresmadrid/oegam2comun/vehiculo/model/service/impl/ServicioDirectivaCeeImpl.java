package org.gestoresmadrid.oegam2comun.vehiculo.model.service.impl;

import java.util.List;

import org.gestoresmadrid.core.vehiculo.model.dao.DirectivaCeeDao;
import org.gestoresmadrid.core.vehiculo.model.vo.DirectivaCeeVO;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioDirectivaCee;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.DirectivaCeeDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import trafico.beans.HomologacionBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDirectivaCeeImpl implements ServicioDirectivaCee {

	private static final long serialVersionUID = -828827476037122102L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDirectivaCeeImpl.class);

	@Autowired
	private DirectivaCeeDao directivaCeeDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public List<DirectivaCeeDto> listadoDirectivaCee(String criterioConstruccion) {
		try {
			if("-1".equals(criterioConstruccion)){
				criterioConstruccion = null;
			}
			List<DirectivaCeeVO> listadoDirectivaCee = directivaCeeDao.getListaDirectivasCee(criterioConstruccion);
			if (listadoDirectivaCee != null && !listadoDirectivaCee.isEmpty()) {
				return conversor.transform(listadoDirectivaCee, DirectivaCeeDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar las directivas de homologación", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public HomologacionBean getHomologacionBean(String idDirectivaCEE) {
		HomologacionBean homologacionBean = null;
		if(idDirectivaCEE != null && !idDirectivaCEE.isEmpty()){
			try {
				DirectivaCeeVO directivaCeeVO = directivaCeeDao.getDirectivaPorId(idDirectivaCEE);
				homologacionBean = new HomologacionBean();
				homologacionBean.setIdHomologacion(idDirectivaCEE);
				if(directivaCeeVO != null){
					homologacionBean.setDescripcion(directivaCeeVO.getDescripcion());
				}else{
					homologacionBean.setDescripcion("Descripcion por defecto " + idDirectivaCEE);
				}
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de obtener la homologacion EU, error: ", e);
			}
		}
		return homologacionBean;
	}

	
	public DirectivaCeeDao getDirectivaCeeDao() {
		return directivaCeeDao;
	}

	public void setDirectivaCeeDao(DirectivaCeeDao directivaCeeDao) {
		this.directivaCeeDao = directivaCeeDao;
	}
}