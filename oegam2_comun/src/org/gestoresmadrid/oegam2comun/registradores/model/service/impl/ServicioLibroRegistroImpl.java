package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.LibroRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.LibroRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioLibroRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.LibroRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioLibroRegistroImpl implements ServicioLibroRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private LibroRegistroDao libroRegistroDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public ResultRegistro getLibroRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		LibroRegistroDto libroRegistroDto = conversor.transform(libroRegistroDao.getLibroRegistro(identificador), LibroRegistroDto.class);

		if (null != libroRegistroDto) {
			result.setObj(libroRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos del libro");
		}

		return result;
	}

	@Transactional
	@Override
	public List<LibroRegistroDto> getLibrosRegistro(BigDecimal idTramiteRegistro) {
		return conversor.transform(libroRegistroDao.getLibrosRegistro(idTramiteRegistro), LibroRegistroDto.class);
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarLibroRegistro(LibroRegistroVO libroRegistroVO, BigDecimal idTramiteRegistro) {
		ResultRegistro result;

		result = validarLibroRegistro(libroRegistroVO);
		if (result.isError())
			return result;

		if (0 == libroRegistroVO.getIdLibro()) {
			libroRegistroVO.setIdTramiteRegistro(idTramiteRegistro);
		}

		libroRegistroVO = libroRegistroDao.guardarOActualizar(libroRegistroVO);

		if (null != libroRegistroVO) {
			result.setMensaje("Datos del libro actualizados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos del libro");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarLibroRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (libroRegistroDao.borrar(libroRegistroDao.getLibroRegistro(id))) {
			result.setMensaje("Datos del libro eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos del libro");
		}
		return result;
	}

	public ResultRegistro validarLibroRegistro(LibroRegistroVO libroRegistroVO) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(libroRegistroVO.getNombreLibro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre del libro es obligatorio.");
		}

		if (StringUtils.isBlank(libroRegistroVO.getNombreFichero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre del fichero es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(libroRegistroVO.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del libro es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(libroRegistroVO.getFecApertura())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de apertura del libro es obligatoria.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(libroRegistroVO.getFecCierre())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de cierre del libro es obligatoria.");
		}

		return result;
	}

}
