package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.PropiedadIndustrialDao;
import org.gestoresmadrid.core.registradores.model.vo.PropiedadIndustrialRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPropiedadIndustrialRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PropiedadIndustrialRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPropiedadIndustrialRegistroImpl implements ServicioPropiedadIndustrialRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private PropiedadIndustrialDao propiedadIndustrialRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getPropiedadIndustrialRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		PropiedadIndustrialRegistroDto propiedadIndustrialRegistroDto = conversor.transform(propiedadIndustrialRegistroDao.getPropiedadIndustrialRegistro(identificador),
				PropiedadIndustrialRegistroDto.class);

		if (null != propiedadIndustrialRegistroDto) {
			result.setObj(propiedadIndustrialRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener propiedad industrial");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarPropiedadIndustrialRegistro(PropiedadIndustrialRegistroDto propiedadIndustrialRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarPropiedadIndustrialRegistro(propiedadIndustrialRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de propiedadIndustrial entonces actualizamos, si no añadimos
		if (0 == propiedadIndustrialRegistroDto.getIdPropiedadIndustrial()) {
			propiedadIndustrialRegistroDto.setIdPropiedad(idPropiedad);
		}

		PropiedadIndustrialRegistroVO propiedadIndustrialRegistroVO = propiedadIndustrialRegistroDao.guardarOActualizar(conversor.transform(propiedadIndustrialRegistroDto,
				PropiedadIndustrialRegistroVO.class));

		if (null != propiedadIndustrialRegistroVO) {
			result.setMensaje("Propiedad industrial guardada correctamente");
			result.setObj(propiedadIndustrialRegistroVO.getIdPropiedadIndustrial());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar propiedad industrial");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarPropiedadIndustrialRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (propiedadIndustrialRegistroDao.borrar(propiedadIndustrialRegistroDao.getPropiedadIndustrialRegistro(id))) {
			result.setMensaje("Propiedad industrial eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando propiedad industrial");
		}
		return result;
	}

	@Override
	@Transactional
	public PropiedadIndustrialRegistroDto getPropiedadIndustrialPorPropiedad(String idPropiedad) {
		return conversor.transform(propiedadIndustrialRegistroDao.getPropiedadIndustrialPorPropiedad(new BigDecimal(idPropiedad)), PropiedadIndustrialRegistroDto.class);
	}

	@Override
	public ResultRegistro validarPropiedadIndustrialRegistro(PropiedadIndustrialRegistroDto propiedadIndustrialRegistroDto) {

		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(propiedadIndustrialRegistroDto.getNombreMarca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre de la marca de la propiedad industrial es obligatorio.");
		}

		if (StringUtils.isBlank(propiedadIndustrialRegistroDto.getNumRegAdm())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número registro administrativo de la propiedad industrial es obligatorio.");
		}

		if (StringUtils.isBlank(propiedadIndustrialRegistroDto.getDescripcion())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La descripción de la propiedad industrial es obligatoria.");
		}

		return result;
	}

}
