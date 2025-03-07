package org.gestoresmadrid.oegamComun.usuarioLogin.impl;

import java.util.Collection;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.dao.UsuarioLoginDao;
import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.oegamComun.usuarioLogin.ServicioPersistenciaUsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.FechaFraccionada;

@Service
public class ServicioPersistenciaUsuarioLoginImpl implements ServicioPersistenciaUsuarioLogin {

	private static final long serialVersionUID = -1315611383906571917L;

	@Autowired
	private UsuarioLoginDao usuarioLoginDao;

	@Override
	@Transactional
	public UsuarioLoginVO guardarOActualizarUsuarioLogin(UsuarioLoginVO usuarioLoginVO) {
		return usuarioLoginDao.guardarOActualizar(usuarioLoginVO);
	}

	@Override
	@Transactional
	public UsuarioLoginVO getUsuarioLoginVO(String idSesion) {
		return usuarioLoginDao.getUsuarioLoginVO(idSesion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda) {
		return usuarioLoginDao.buscaUsuariosActivosLogin(numColegiado, frontal, listIdUsuarios, ordenBusqueda);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin) {
		return usuarioLoginDao.buscaIPMasRepetida(numColegiado, fechaLogin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> getFrontalesActivos() {
		return usuarioLoginDao.getFrontalesActivos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getUsuariosFrontal() {
		return usuarioLoginDao.getUsuariosFrontal();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getUsuariosRepetidos() {
		return usuarioLoginDao.getUsuariosRepetidos();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> getUsuariosTotalesFrontales() {
		return usuarioLoginDao.getUsuariosTotalesFrontales();
	}

}
