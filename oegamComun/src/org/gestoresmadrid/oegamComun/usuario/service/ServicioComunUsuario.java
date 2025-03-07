package org.gestoresmadrid.oegamComun.usuario.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;

public interface ServicioComunUsuario extends Serializable{

	List<UsuarioVO> getUsuarioPorNif(String nif, BigDecimal estado);

	ResultadoBean deshabilitarUsuario(BigDecimal idUsuario, Date fecha, Boolean salirSesion) ;
	
	ResultadoBean habilitarUsuario(BigDecimal idUsuario, Boolean salirSesion);

	UsuarioVO getUsuario(Long idUsuario);

	void actualizarDatosConexionUsuario(Long idUsuario);

	List<UsuarioDto> getListaUsuariosColegio();

	UsuarioVO getUsuarioPorNifYColegiado(String nif, String numColegiado);

	ResultadoBean altaUsuarioDesdeOtro(UsuarioVO usuarioBBDD, String numColegiado);
}
