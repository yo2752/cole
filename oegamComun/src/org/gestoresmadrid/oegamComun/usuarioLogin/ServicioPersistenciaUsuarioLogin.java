package org.gestoresmadrid.oegamComun.usuarioLogin;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.gestoresmadrid.core.administracion.model.vo.UsuarioLoginVO;

import utilidades.estructuras.FechaFraccionada;

public interface ServicioPersistenciaUsuarioLogin extends Serializable {

	public List<UsuarioLoginVO> buscaUsuariosActivosLogin(String numColegiado, int frontal, Collection<Object> listIdUsuarios, String ordenBusqueda);

	public UsuarioLoginVO guardarOActualizarUsuarioLogin(UsuarioLoginVO usuarioLoginVO);

	public UsuarioLoginVO getUsuarioLoginVO(String idSesion);

	public List<Object[]> buscaIPMasRepetida(String numColegiado, FechaFraccionada fechaLogin);

	public List<Integer> getFrontalesActivos();

	public List<Object[]> getUsuariosFrontal();

	public List<Object[]> getUsuariosRepetidos();

	public List<Object[]> getUsuariosTotalesFrontales();

}
