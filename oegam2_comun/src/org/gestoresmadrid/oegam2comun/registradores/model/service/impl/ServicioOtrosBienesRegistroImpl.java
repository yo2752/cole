package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.OtrosBienesDao;
import org.gestoresmadrid.core.registradores.model.vo.OtrosBienesRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOtrosBienesRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtrosBienesRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.validaciones.NIFValidator;

@Service
public class ServicioOtrosBienesRegistroImpl implements ServicioOtrosBienesRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private OtrosBienesDao otrosBienesRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getOtrosBienesRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		OtrosBienesRegistroDto otrosBienesRegistroDto = conversor.transform(otrosBienesRegistroDao.getOtrosBienesRegistro(identificador), OtrosBienesRegistroDto.class);

		if (null != otrosBienesRegistroDto) {
			result.setObj(otrosBienesRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener otros bienes");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarOtrosBienesRegistro(OtrosBienesRegistroDto otrosBienesRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarOtrosBienesRegistro(otrosBienesRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de otrosBienes entonces actualizamos, si no añadimos
		if (0 == otrosBienesRegistroDto.getIdOtrosBienes()) {
			otrosBienesRegistroDto.setIdPropiedad(idPropiedad);
		}

		OtrosBienesRegistroVO otrosBienesRegistroVO = otrosBienesRegistroDao.guardarOActualizar(conversor.transform(otrosBienesRegistroDto, OtrosBienesRegistroVO.class));

		if (null != otrosBienesRegistroVO) {
			result.setMensaje("Otros bienes guardado correctamente");
			result.setObj(otrosBienesRegistroVO.getIdOtrosBienes());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar otros bienes");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarOtrosBienesRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (otrosBienesRegistroDao.borrar(otrosBienesRegistroDao.getOtrosBienesRegistro(id))) {
			result.setMensaje("OtrosBienes eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando otros bienes");
		}
		return result;
	}

	@Override
	@Transactional
	public OtrosBienesRegistroDto getOtrosBienesPorPropiedad(String idPropiedad) {
		return conversor.transform(otrosBienesRegistroDao.getOtrosBienesPorPropiedad(new BigDecimal(idPropiedad)), OtrosBienesRegistroDto.class);
	}

	@Override
	public ResultRegistro validarOtrosBienesRegistro(OtrosBienesRegistroDto otrosBienesRegistroDto) {

		ResultRegistro result = new ResultRegistro();

		if ((StringUtils.isBlank(otrosBienesRegistroDto.getTipo()) || StringUtils.isBlank(otrosBienesRegistroDto.getMarca()) || StringUtils.isBlank(otrosBienesRegistroDto.getModelo()) || StringUtils
				.isBlank(otrosBienesRegistroDto.getNumSerie()))

				&& (StringUtils.isBlank(otrosBienesRegistroDto.getTipoRegAdm()) || StringUtils.isBlank(otrosBienesRegistroDto.getNumRegAdm()))

				&& (StringUtils.isBlank(otrosBienesRegistroDto.getCodProvincia()) || StringUtils.isBlank(otrosBienesRegistroDto.getCodMunicipio()) || StringUtils.isBlank(otrosBienesRegistroDto
						.getCodTipoVia()) || StringUtils.isBlank(otrosBienesRegistroDto.getNombreVia()) || StringUtils.isBlank(otrosBienesRegistroDto.getNumero())) && StringUtils.isBlank(
								otrosBienesRegistroDto.getLugarUbicacion()) && (StringUtils.isBlank(otrosBienesRegistroDto.getNombreSociedad()) || StringUtils.isBlank(otrosBienesRegistroDto
										.getCifSociedad()) || StringUtils.isBlank(otrosBienesRegistroDto.getCnae()) || StringUtils.isBlank(otrosBienesRegistroDto.getCodRegMercantil()) || StringUtils
												.isBlank(otrosBienesRegistroDto.getSeccionMercantil()) || StringUtils.isBlank(otrosBienesRegistroDto.getNumHoja()) || StringUtils.isBlank(
														otrosBienesRegistroDto.getNumHojaDup()) || StringUtils.isBlank(otrosBienesRegistroDto.getNumSubHoja()) || StringUtils.isBlank(
																otrosBienesRegistroDto.getTomoSociedad()) || StringUtils.isBlank(otrosBienesRegistroDto.getFolioSociedad()) || StringUtils.isBlank(
																		otrosBienesRegistroDto.getInsSociedad())) && StringUtils.isBlank(otrosBienesRegistroDto.getOtraDescripcion())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe rellenar completamente al menos uno de los bloques de otros bienes muebles registrales.");
		}

		if (!UtilesValidaciones.validarCodigoPostal(otrosBienesRegistroDto.getCodigoPostal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El código postal del domicilio no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getNumero())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número del domicilio no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getCodRegistroPropiedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro de la propiedad de los datos registrales no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getNumFinca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de finca de los datos registrales no tiene el formato correcto.");
		}

		if (NIFValidator.isValidCIF(otrosBienesRegistroDto.getCifSociedad().toUpperCase())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El CIF de la sociedad no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getCodRegMercantil())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El registro mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getSeccionMercantil())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La sección mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getNumHoja())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de hoja del registro mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getNumHojaDup())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de hoja duplicada del registro mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getTomoSociedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tomo inscrip. del registro mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getFolioSociedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El folio inscrip. del registro mercantil no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarNumero(otrosBienesRegistroDto.getInsSociedad())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El Número inscrip. del registro mercantil no tiene el formato correcto.");
		}

		return result;

	}

}
