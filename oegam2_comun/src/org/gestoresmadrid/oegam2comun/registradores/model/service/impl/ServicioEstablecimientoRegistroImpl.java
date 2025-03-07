package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.EstablecimientoDao;
import org.gestoresmadrid.core.registradores.model.vo.EstablecimientoRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioEstablecimientoRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.EstablecimientoRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioEstablecimientoRegistroImpl implements ServicioEstablecimientoRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private EstablecimientoDao establecimientoRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getEstablecimientoRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		EstablecimientoRegistroDto establecimientoRegistroDto = conversor.transform(establecimientoRegistroDao.getEstablecimientoRegistro(identificador), EstablecimientoRegistroDto.class);

		if (null != establecimientoRegistroDto) {
			result.setObj(establecimientoRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener establecimiento");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarEstablecimientoRegistro(EstablecimientoRegistroDto establecimientoRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarEstablecimientoRegistro(establecimientoRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de establecimiento entonces actualizamos, si no añadimos
		if (0 == establecimientoRegistroDto.getIdEstablecimiento()) {
			establecimientoRegistroDto.setIdPropiedad(idPropiedad);
		}

		EstablecimientoRegistroVO establecimientoRegistroVO = establecimientoRegistroDao.guardarOActualizar(conversor.transform(establecimientoRegistroDto, EstablecimientoRegistroVO.class));

		if (null != establecimientoRegistroVO) {
			result.setMensaje("Establecimiento guardado correctamente");
			result.setObj(establecimientoRegistroVO.getIdEstablecimiento());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar establecimiento");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarEstablecimientoRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (establecimientoRegistroDao.borrar(establecimientoRegistroDao.getEstablecimientoRegistro(id))) {
			result.setMensaje("Establecimiento eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando establecimiento");
		}
		return result;
	}

	@Override
	@Transactional
	public EstablecimientoRegistroDto getEstablecimientoPorPropiedad(String idPropiedad) {
		return conversor.transform(establecimientoRegistroDao.getEstablecimientoPorPropiedad(new BigDecimal(idPropiedad)), EstablecimientoRegistroDto.class);
	}

	@Override
	public ResultRegistro validarEstablecimientoRegistro(EstablecimientoRegistroDto establecimientoRegistroDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(establecimientoRegistroDto.getNombreEstablecimiento())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre del establecimiento es obligatorio.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getClaseEstablecimiento())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La clase del establecimiento es obligatoria.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getNumeroEstablecimiento())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del establecimiento es obligatorio.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getCodProvincia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La provincia del domicilio es obligatoria.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getCodMunicipio())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El municipio del domicilio es obligatorio.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getCodTipoVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tipo de vía del domicilio es obligatorio.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getNombreVia())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre de la vía del domicilio es obligatorio.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del domicilio es obligatorio.");
		} else if (!UtilesValidaciones.validarNumero(establecimientoRegistroDto.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del domicilio no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(establecimientoRegistroDto.getCodigoPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal del domicilio es obligatorio.");
		} else if (!UtilesValidaciones.validarCodigoPostal(establecimientoRegistroDto.getCodigoPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal del domicilio no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(establecimientoRegistroDto.getNumFinca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de finca de los datos registrales no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(establecimientoRegistroDto.getCodRegistroPropiedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro de la propiedad de los datos registrales no tiene el formato correcto.");
		}
		return result;
	}

}
