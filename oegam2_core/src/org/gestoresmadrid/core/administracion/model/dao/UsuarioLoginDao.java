package org.gestoresmadrid.core.administracion.model.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;
import org.gestoresmadrid.core.model.beans.DatoMaestroBean;
import org.gestoresmadrid.core.model.dao.GenericDao;

import utilidades.estructuras.FechaFraccionada;

public interface UsuarioLoginDao extends GenericDao<UsuarioLoginVO> {

	public List<Integer> getFrontalesActivos();

	public List<Object[]> getUsuariosFrontal();

	public List<Object[]> getUsuariosRepetidos();
	
	public List<Object[]> getUsuariosTotalesFrontales();

	UsuarioLoginVO getUsuarioLoginVO(String idSesion);

	List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin);

	List<UsuarioLoginVO> buscaUsuarioLogin(String numColegiado, int frontal, FechaFraccionada fechaLogin);

	List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda);

	void insert(String idSesion, String numColegiado, String ipAddr, String ipFrontal, BigDecimal id_user, String apellidosNombre, String servidoNtp);

	public List<DatoMaestroBean> getUsuariosPorFrontal();

	public List<String> getUsuariosRepetidosPlataforma();

	List<UsuarioLoginVO> getSesionesAbiertas(BigDecimal idUsuario);
}
