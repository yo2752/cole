package org.gestoresmadrid.oegamComun.usuarioLogin.impl;

import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.oegamComun.usuarioLogin.ServicioPersistenciaUsuarioLogin;
import org.gestoresmadrid.oegamComun.usuarioLogin.ServicioUsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import utilidades.estructuras.FechaFraccionada;

@Service
public class ServicioUsuarioLoginImpl implements ServicioUsuarioLogin {

	private static final long serialVersionUID = -6508460558085159875L;

	@Autowired
	private ServicioPersistenciaUsuarioLogin servicioPersistenciaUsuarioLogin;

	@Override
	public int cerrarSesionUsuariosOnline(String idSesionActual, String numColegiado, Integer frontal) {
		int cantidadSesionesCerradas = 0;

		if (frontal == null) {
			frontal = -1;
		}

		List<UsuarioLoginVO> listaUsuariosOnline = servicioPersistenciaUsuarioLogin.buscaUsuariosActivosLogin(numColegiado, frontal, null, null);

		if (listaUsuariosOnline != null && !listaUsuariosOnline.isEmpty()) {
			for (UsuarioLoginVO element : listaUsuariosOnline) {
				// no se cierra la sesion a los usuarios administradores que las sesiones hayan iniciado el día de hoy
				if (!idSesionActual.equalsIgnoreCase(element.getIdSesion())) {
					element.setFechaFin(new Date());
					servicioPersistenciaUsuarioLogin.guardarOActualizarUsuarioLogin(element);
					cantidadSesionesCerradas++;
				}
			}
		}
		return cantidadSesionesCerradas;
	}

	@Override
	public void cerrarSesionUsuarioOnline(String idSesion) {
		UsuarioLoginVO usuarioLoginVO = servicioPersistenciaUsuarioLogin.getUsuarioLoginVO(idSesion);
		// comprobar si ya se ha actualizado la fecha fin en BBDD
		if (usuarioLoginVO != null && usuarioLoginVO.getFechaFin() == null) {

			usuarioLoginVO.setFechaFin(new Date());
			servicioPersistenciaUsuarioLogin.guardarOActualizarUsuarioLogin(usuarioLoginVO);
		}
	}

	@Override
	public String buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin) {
		String textoIpMasRepetida = null;
		List<Object[]> ipMasRepetida = servicioPersistenciaUsuarioLogin.buscaIPMasRepetida(numColegiado, fechaLogin);
		if (ipMasRepetida != null && !ipMasRepetida.isEmpty()) {
			for (Object[] element : ipMasRepetida) {
				textoIpMasRepetida = " La Ip más repetida es la " + (String) element[1] + " con un total de " + (Integer) element[0] + " veces.";
			}
		}
		return textoIpMasRepetida;
	}

	@Override
	public List<Object[]> getUsuariosFrontal() {
		return servicioPersistenciaUsuarioLogin.getUsuariosFrontal();
	}

	@Override
	public List<Object[]> getUsuariosRepetidos() {
		return servicioPersistenciaUsuarioLogin.getUsuariosRepetidos();
	}

	@Override
	public List<Object[]> getUsuariosTotalesFrontales() {
		return servicioPersistenciaUsuarioLogin.getUsuariosTotalesFrontales();
	}

}
