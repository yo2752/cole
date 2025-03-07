package org.gestoresmadrid.oegamComun.usuarioLogin;

import java.io.Serializable;
import java.util.List;

import utilidades.estructuras.FechaFraccionada;

/**
 * Servicio destinado a UsuarioLoginVO
 */
public interface ServicioUsuarioLogin extends Serializable {

	public int cerrarSesionUsuariosOnline(String idSesionActual, String numColegiado, Integer frontal);

	public void cerrarSesionUsuarioOnline(String idSesion);

	public String buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin);

	public List<Object[]> getUsuariosFrontal();

	public List<Object[]> getUsuariosRepetidos();

	public List<Object[]> getUsuariosTotalesFrontales();

}
