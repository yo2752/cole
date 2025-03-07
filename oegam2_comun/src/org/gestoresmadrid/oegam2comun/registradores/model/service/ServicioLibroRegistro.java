package org.gestoresmadrid.oegam2comun.registradores.model.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.vo.LibroRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.LibroRegistroDto;

public interface ServicioLibroRegistro extends Serializable{

	public ResultRegistro getLibroRegistro(String id);
	public ResultRegistro borrarLibroRegistro(String id);
	public ResultRegistro guardarOActualizarLibroRegistro(LibroRegistroVO libroRegistroVO, BigDecimal idTramiteRegistro);
	public List<LibroRegistroDto> getLibrosRegistro(BigDecimal idTramiteRegistro);

}
