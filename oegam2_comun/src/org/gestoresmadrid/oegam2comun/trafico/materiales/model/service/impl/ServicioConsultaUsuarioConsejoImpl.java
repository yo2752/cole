package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import javax.annotation.Resource;

import org.gestoresmadrid.core.trafico.materiales.model.dao.UsuarioColegioDao;
import org.gestoresmadrid.core.trafico.materiales.model.vo.AutorVO;
import org.gestoresmadrid.core.trafico.materiales.model.vo.UsuarioColegioVO;
import org.gestoresmadrid.core.trafico.model.dao.JefaturaTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.JefaturaTraficoVO;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaUsuarioConsejo;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.beans.rest.UsuarioInfoRest;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioConsultaUsuarioConsejoImpl implements ServicioConsultaUsuarioConsejo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3176443121903249077L;
	
	@Resource JefaturaTraficoDao jefaturaTraficoDao;
	@Resource UsuarioColegioDao  usuarioColegioDao; 
	
	@Autowired Conversor conversor;

	@Override
	@Transactional(readOnly=true)
	public JefaturaTraficoVO obtenerJefaturaProvincial(String jefatura) {
		return jefaturaTraficoDao.getJefatura(jefatura);
	}
	
	@Override
	public UsuarioColegioVO getDtoFromInfoRest(UsuarioInfoRest usuarioInfoRest) {
		UsuarioColegioVO usuarioColegioVO = conversor.transform(usuarioInfoRest, UsuarioColegioVO.class);
		return usuarioColegioVO;
	}

	@Override
	@Transactional(readOnly=true)
	public UsuarioColegioVO obtenerUsuarioColegio() {
		// TODO Auto-generated method stub
		
		return usuarioColegioDao.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public AutorVO obtenerAutorFromUsuarioColegio() {
		// TODO Auto-generated method stub
		UsuarioColegioVO usuarioColegioVO = obtenerUsuarioColegio();
		
		AutorVO autorVO = new AutorVO();
		autorVO.setAutorId(usuarioColegioVO.getUsuarioId());
		autorVO.setNombre(usuarioColegioVO.getNombre());
		autorVO.setEmail(usuarioColegioVO.getEmail());
		autorVO.setRol(usuarioColegioVO.getRol());
		return autorVO;
	}


}
