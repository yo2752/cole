package org.gestoresmadrid.oegamComun.acceso.model.service;

import java.io.Serializable;

import org.gestoresmadrid.oegamComun.acceso.model.bean.ResultadoAccesoBean;
import org.gestoresmadrid.oegamComun.acceso.model.bean.UsuarioAccesoBean;

public interface ServicioGestionAcceso extends Serializable{

	ResultadoAccesoBean guardarDatosAccesoSession(UsuarioAccesoBean usuarioAcceso);

	ResultadoAccesoBean gestionFavoritos(UsuarioAccesoBean usuarioAcceso);

	ResultadoAccesoBean montarMenuRepository(UsuarioAccesoBean usuarioAcceso);

	ResultadoAccesoBean gestionAcceso(String nif, Long idContrao);

	ResultadoAccesoBean gestionarCambioContratoUsuario(Long idUsuario, Long idContrato);

}
