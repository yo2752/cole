package org.gestoresmadrid.oegamComun.session.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.gestoresmadrid.core.administracion.model.dao.UsuarioLoginDao;
import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.core.model.exceptions.TransactionalException;
import org.gestoresmadrid.oegamComun.session.service.ServicioSesion;
import org.gestoresmadrid.oegamComun.usuario.service.ServicioComunUsuario;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.estructuras.FechaFraccionada;

@Service
public class ServicioSesionImpl implements ServicioSesion {

	@Autowired
	private UsuarioLoginDao usuarioLoginDao;

	@Autowired
	private ServicioComunUsuario servicioComunUsuario;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
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

	@Override
	@Transactional
	public void cerrarSesion(String idSesion, BigDecimal idUsuario, boolean cambioContrato) {
		UsuarioLoginVO usuarioLoginVO = usuarioLoginDao.getUsuarioLoginVO(idSesion);
		// comprobar si ya se ha actualizado la fecha fin en BBDD
		if (usuarioLoginVO != null && usuarioLoginVO.getFechaFin() == null) {
			if (cambioContrato && idUsuario != null) {//&& !usuarioLoginVO.getIdUsuario().equals(idUsuario)) {
				// El usuario con el que ingreso en el sistema es distinto del actual debido a un cambio de contrato, se vuelve al usuario anterior
				servicioComunUsuario.deshabilitarUsuario(idUsuario,null,false);
				servicioComunUsuario.habilitarUsuario(usuarioLoginVO.getIdUsuario(),true);
			}
			usuarioLoginVO.setFechaFin(new Date());
			usuarioLoginDao.actualizar(usuarioLoginVO);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin) {
		return usuarioLoginDao.buscaIPMasRepetida(numColegiado, fechaLogin); 
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioLoginVO> buscaUsuarioLogin(String numColegiado, int frontal, FechaFraccionada fechaLogin) {
		return usuarioLoginDao.buscaUsuarioLogin(numColegiado, frontal, fechaLogin);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioLoginVO> buscaUsuariosActivosLogin(List<Object> listIdUsuarios) {
		return usuarioLoginDao.buscaUsuariosActivosLogin(null, -1, listIdUsuarios, "fechaLogin");
	}

	@Override
	@Transactional(readOnly = true)
	public List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda) {
		return usuarioLoginDao.buscaUsuariosActivosLogin(numColegiado, frontal, listIdUsuarios, ordenBusqueda);
	}

	@Override
	@Transactional
	public void insert(String idSesion, String numColegiado, String ipAddr, String ipFrontal, BigDecimal id_user, String apellidosNombre) {
		UsuarioLoginVO usuarioLoginVO = usuarioLoginDao.getUsuarioLoginVO(idSesion);
		if (usuarioLoginVO == null) {
			usuarioLoginDao.insert(idSesion, numColegiado, ipAddr, ipFrontal, id_user, apellidosNombre, gestorPropiedades.valorPropertie("estadisticas.servidor.ntp"));
		}
	}

	/**
	 * JMC Este metodo es para actualizar la fecha de fin en Un Frontal arrancar
	 * el tomcat de todos aquellos que se quedaron activos al pararlo
	 */
	@Override
	@Transactional
	public boolean updateTablaUsuariosSinSession(String ipFrontal) {
		boolean result = false;
		if (ipFrontal != null && !ipFrontal.isEmpty()) {
			Integer numFrontal = null;
			StringTokenizer st = new StringTokenizer(ipFrontal, ".");
			int contador = 1;
			while (st.hasMoreElements()) {
				if (contador == 3) {
					String frontal = (String) st.nextElement();
					if ("6".equals(frontal)) {
						numFrontal = Integer.parseInt("16");
					} else {
						numFrontal = Integer.parseInt(frontal);
					}
				} else {
					contador++;
					st.nextElement();
				}
			}
			String[] namedParemeters = { "fechaActual", "numFrontal" };
			Object[] namedValues = { new Date(), numFrontal };
			try {
				result = usuarioLoginDao.executeNamedQuery(UsuarioLoginVO.FINALIZAR_SESIONES, namedParemeters, namedValues) > 0;
			} catch (HibernateException e) {
				throw new TransactionalException(e);
			}
		}
		return result;
	}

}
