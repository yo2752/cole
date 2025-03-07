package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.MaquinariaDao;
import org.gestoresmadrid.core.registradores.model.vo.MaquinariaRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMaquinariaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MaquinariaRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioMaquinariaRegistroImpl implements ServicioMaquinariaRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private MaquinariaDao maquinariaRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getMaquinariaRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		MaquinariaRegistroDto maquinariaRegistroDto = conversor.transform(maquinariaRegistroDao.getMaquinariaRegistro(identificador), MaquinariaRegistroDto.class);

		if (null != maquinariaRegistroDto) {
			result.setObj(maquinariaRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener maquinaria");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarMaquinariaRegistro(MaquinariaRegistroDto maquinariaRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarMaquinariaRegistro(maquinariaRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de maquinaria entonces actualizamos, si no añadimos
		if (0 == maquinariaRegistroDto.getIdMaquinaria()) {
			maquinariaRegistroDto.setIdPropiedad(idPropiedad);
		}

		MaquinariaRegistroVO maquinariaRegistroVO = maquinariaRegistroDao.guardarOActualizar(conversor.transform(maquinariaRegistroDto, MaquinariaRegistroVO.class));

		if (null != maquinariaRegistroVO) {
			result.setMensaje("Maquinaria guardada correctamente");
			result.setObj(maquinariaRegistroVO.getIdMaquinaria());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar maquinaria");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarMaquinariaRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (maquinariaRegistroDao.borrar(maquinariaRegistroDao.getMaquinariaRegistro(id))) {
			result.setMensaje("Maquinaria eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando maquinaria");
		}
		return result;
	}

	@Override
	@Transactional
	public MaquinariaRegistroDto getMaquinariaPorPropiedad(String idPropiedad) {
		return conversor.transform(maquinariaRegistroDao.getMaquinariaPorPropiedad(new BigDecimal(idPropiedad)), MaquinariaRegistroDto.class);
	}

	@Override
	public ResultRegistro validarMaquinariaRegistro(MaquinariaRegistroDto maquinariaRegistroDto) {
		ResultRegistro result = new ResultRegistro();

		if ((StringUtils.isBlank(maquinariaRegistroDto.getTipo()) || StringUtils.isBlank(maquinariaRegistroDto.getNumSerie())) && (StringUtils.isBlank(maquinariaRegistroDto.getCodProvincia())
				|| StringUtils.isBlank(maquinariaRegistroDto.getCodMunicipio()) || StringUtils.isBlank(maquinariaRegistroDto.getCodTipoVia()) || StringUtils.isBlank(maquinariaRegistroDto
						.getNombreVia()) || StringUtils.isBlank(maquinariaRegistroDto.getNumero())) && StringUtils.isBlank(maquinariaRegistroDto.getLugarUbicacion())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe rellenar al menos el bloque maquinaria o el bloque domicilio de la maquinaria.");
		}

		if (!UtilesValidaciones.validarCodigoPostal(maquinariaRegistroDto.getCodigoPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal del domicilio no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(maquinariaRegistroDto.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del domicilio no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(maquinariaRegistroDto.getCodRegistroPropiedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro de la propiedad de los datos registrales no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(maquinariaRegistroDto.getNumFinca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de finca de los datos registrales no tiene el formato correcto.");
		}
		return result;
	}

}
