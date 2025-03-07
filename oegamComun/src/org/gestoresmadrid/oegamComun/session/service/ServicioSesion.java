package org.gestoresmadrid.oegamComun.session.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioSesion {

	List<Integer> getFrontalesActivos();

	List<Object[]> getUsuariosFrontal();

	List<Object[]> getUsuariosRepetidos();
	
	List<Object[]> getUsuariosTotalesFrontales();

	void cerrarSesion(String idSesion, BigDecimal idUsuario, boolean cambioContrato);

	List<UsuarioLoginVO> buscaUsuariosActivosLogin(List<Object> listIdUsuarios);

	List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin);

	List<UsuarioLoginVO> buscaUsuarioLogin(String numColegiado, int frontal, FechaFraccionada fechaLogin);

	List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda);

	void insert(String idSesion, String numColegiado, String ipAddr, String ipFrontal, BigDecimal id_user, String apellidosNombre);

	boolean updateTablaUsuariosSinSession(String ipFrontal);

}
