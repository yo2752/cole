package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.tasas.model.dao.TasaProcedureDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.oegam2comun.tasas.model.dto.RespuestaTasas;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioPersistenciaTasa;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPersistenciaTasaImpl implements ServicioPersistenciaTasa {

	@Autowired
	private TasaProcedureDao tasaProcedureDao;
	
	@Override
	@Transactional
	public RespuestaTasas guardarTasa(Tasa tasa) {
		RespuestaTasas respuesta = new RespuestaTasas();
		TasaVO tasaVO = new TasaVO();
		tasaVO.setCodigoTasa(tasa.getCodigoTasa());
		tasaVO.setContrato(new ContratoVO());
		tasaVO.getContrato().setIdContrato(tasa.getIdContrato().longValue());
		tasaVO.setFechaAlta(tasa.getFechaAlta());
		tasaVO.setPrecio(tasa.getPrecio());
		tasaVO.setTipoTasa(tasa.getTipoTasa());
		tasaVO.setUsuario(new UsuarioVO());
		tasaVO.getUsuario().setIdUsuario(tasa.getIdUsuario().longValue());
		tasaVO.setImportadoIcogam(new BigDecimal(tasa.getImportadoIcogam()));
		String mensaje = tasaProcedureDao.guardar(tasaVO);
		if (mensaje!=null && !mensaje.isEmpty()) {
			respuesta.setError(true);
			respuesta.setMensajeError(mensaje);
		}
		return respuesta;
	}

	public TasaProcedureDao getTasaProcedureDao() {
		return tasaProcedureDao;
	}

	public void setTasaProcedureDao(TasaProcedureDao tasaProcedureDao) {
		this.tasaProcedureDao = tasaProcedureDao;
	}

	
}
