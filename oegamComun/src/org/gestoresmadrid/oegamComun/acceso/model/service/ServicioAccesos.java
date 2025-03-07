package org.gestoresmadrid.oegamComun.acceso.model.service;

import java.io.Serializable;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioFuncionSinAccesoVO;
import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;

public interface ServicioAccesos extends Serializable {

	ResultadoAccesoBean getUsuariosActivo(String nif);

	ResultadoAccesoBean getContratosColegiado(String numColegiado);

	ResultadoAccesoBean actualizarDatosConexionUsuario(Long idUsuario);

	ResultadoAccesoBean obtenerFavoritosContrato(Long idContrato);

	ResultadoAccesoBean obtenerListaMenuContrato(Long idUsuario, Long idContrato);

	ResultadoAccesoBean generarUserAuthorization(List<String> listaUrlsAcceso);
	
	public List<UsuarioFuncionSinAccesoVO> obtenerListadoFuncionSinAcceso(Long idContrato, Long idUsuario);

}
