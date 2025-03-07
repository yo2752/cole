package org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.trafico.canjes.model.dao.CanjesDao;
import org.gestoresmadrid.core.trafico.canjes.model.vo.CanjesVO;
import org.gestoresmadrid.oegam2comun.canjes.view.dto.CanjesDto;
import org.gestoresmadrid.oegam2comun.trafico.canjes.model.service.ServicioPersistenciaCanjes;
import org.gestoresmadrid.oegam2comun.trafico.canjes.view.beans.ResultadoCanjesBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioPersistenciaCanjesImpl implements ServicioPersistenciaCanjes{

	private static final long serialVersionUID = -4342386021973690111L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersistenciaCanjesImpl.class);

	@Autowired
	Utiles utiles;
	
	@Autowired
	CanjesDao canjesDao;
	
	@Override
	@Transactional
	public ResultadoCanjesBean guardarCanje(CanjesDto canjesDto) {
		ResultadoCanjesBean resultado = new ResultadoCanjesBean(Boolean.FALSE);
		try {
			CanjesVO canjesVO = new CanjesVO();
			canjesVO.setDocIdCanje(canjesDto.getIdCanje());
			canjesVO.setNombreApell(canjesDto.getNombreapell());
			canjesVO.setFechaAlta(new Date());
			canjesVO.setDniNie(canjesDto.getDninie());
			canjesVO.setNumColegiado(canjesDto.getNumColegiado());
			canjesVO.setFechaExpedicion(canjesDto.getFechaExp());
			canjesVO.setFechaNacimiento(canjesDto.getFechaNac());
			canjesVO.setLugarExpedicion(canjesDto.getLugarExp());
			canjesVO.setNumCarnet(canjesDto.getNumCarnet());
			canjesVO.setCategorias(canjesDto.getCategorias());
			canjesVO.setPais(canjesDto.getPais());
			canjesVO.setImpreso("SI");
			canjesDao.guardarOActualizar(canjesVO);
		} catch (Exception e) {
			log.error("Error al guardar el canje." + e.getMessage(), e);
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el canje.");
		}
		return resultado;
	}
	@Override
	@Transactional
	public String generarIdCanje() throws Exception {
		return  canjesDao.generarIdCanje();
	}
	
	@Override
	@Transactional
	public ResultadoCanjesBean modificarImpresion(CanjesDto canjesDto) {
		ResultadoCanjesBean resultado = new ResultadoCanjesBean(Boolean.FALSE);
		try {
			CanjesVO canjesVO = canjesDao.buscarCanjePorId(canjesDto.getDninie(),canjesDto.getNumColegiado());
			if(canjesVO != null) {
				canjesDao.borrar(canjesVO);
			}
		}catch (Exception e) {
			log.error("Error al guardar el canje." + e.getMessage(), e);
			resultado.setError(true);
			resultado.setMensaje("Error al guardar el canje.");
		}
			
		return resultado;
	}
}
