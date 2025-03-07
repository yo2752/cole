package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadIntelectualDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIntelectualRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedadIntelectualRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadIntelectualRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPropiedadIntelectualRegistroImpl implements ServicioPropiedadIntelectualRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private PropiedadIntelectualDao propiedadIntelectualRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getPropiedadIntelectualRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		PropiedadIntelectualRegistroDto propiedadIntelectualRegistroDto = conversor.transform(propiedadIntelectualRegistroDao.getPropiedadIntelectualRegistro(identificador),
				PropiedadIntelectualRegistroDto.class);

		if (null != propiedadIntelectualRegistroDto) {
			result.setObj(propiedadIntelectualRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener propiedad intelectual");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarPropiedadIntelectualRegistro(PropiedadIntelectualRegistroDto propiedadIntelectualRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarPropiedadIntelectualRegistro(propiedadIntelectualRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de propiedadIntelectual entonces actualizamos, si no añadimos
		if (0 == propiedadIntelectualRegistroDto.getIdPropiedadIntelectual()) {
			propiedadIntelectualRegistroDto.setIdPropiedad(idPropiedad);
		}

		PropiedadIntelectualRegistroVO propiedadIntelectualRegistroVO = propiedadIntelectualRegistroDao.guardarOActualizar(conversor.transform(propiedadIntelectualRegistroDto,
				PropiedadIntelectualRegistroVO.class));

		if (null != propiedadIntelectualRegistroVO) {
			result.setMensaje("Propiedad intelectual guardada correctamente");
			result.setObj(propiedadIntelectualRegistroVO.getIdPropiedadIntelectual());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar propiedad intelectual");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarPropiedadIntelectualRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (propiedadIntelectualRegistroDao.borrar(propiedadIntelectualRegistroDao.getPropiedadIntelectualRegistro(id))) {
			result.setMensaje("Propiedad intelectual eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando propiedad intelectual");
		}
		return result;
	}

	@Override
	@Transactional
	public PropiedadIntelectualRegistroDto getPropiedadIntelectualPorPropiedad(String idPropiedad) {
		return conversor.transform(propiedadIntelectualRegistroDao.getPropiedadIntelectualPorPropiedad(new BigDecimal(idPropiedad)), PropiedadIntelectualRegistroDto.class);
	}

	@Override
	public ResultRegistro validarPropiedadIntelectualRegistro(PropiedadIntelectualRegistroDto propiedadIntelectualRegistroDto) {

		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(propiedadIntelectualRegistroDto.getNumRegAdm())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número registro administrativo de la propiedad intelectual es obligatorio.");
		}

		if (StringUtils.isBlank(propiedadIntelectualRegistroDto.getDescripcion())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La descripción de la propiedad intelectual es obligatoria.");
		}

		return result;
	}

}
