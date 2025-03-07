package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.MotorBuqueDao;
import org.gestoresmadrid.core.registradores.model.vo.MotorBuqueVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMotorBuque;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MotorBuqueDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioMotorBuqueImpl implements ServicioMotorBuque {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private MotorBuqueDao motorBuqueDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public ResultRegistro getMotorBuque(String identificador) {
		ResultRegistro result = new ResultRegistro();

		MotorBuqueDto motorBuqueDto = conversor.transform(motorBuqueDao.getMotorBuque(identificador), MotorBuqueDto.class);

		if (null != motorBuqueDto) {
			result.setObj(motorBuqueDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener motor");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarMotorBuque(MotorBuqueDto motorBuqueDto, long idBuque) {

		ResultRegistro result;

		result = validarMotorBuque(motorBuqueDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador del buque entonces actualizamos, si no añadimos
		if (0 == motorBuqueDto.getIdMotor()) {
			motorBuqueDto.setIdBuque(idBuque);
		}

		MotorBuqueVO motorBuqueVO = motorBuqueDao.guardarOActualizar(conversor.transform(motorBuqueDto, MotorBuqueVO.class));

		if (null != motorBuqueVO) {
			result.setMensaje("Motor actualizado correctamente");
			motorBuqueDto.setIdMotor(motorBuqueVO.getIdMotor());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar motor");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarMotorBuque(String id) {
		ResultRegistro result = new ResultRegistro();
		if (motorBuqueDao.borrar(motorBuqueDao.getMotorBuque(id))) {
			result.setMensaje("Motor eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando motor");
		}
		return result;
	}

	@Override
	@Transactional
	public List<MotorBuqueDto> getMotoresPorBuque(String idBuque) {
		return conversor.transform(motorBuqueDao.getMotoresPorBuque(Long.parseLong(idBuque)), MotorBuqueDto.class);
	}

	@Override
	public ResultRegistro validarMotorBuque(MotorBuqueDto motorBuqueDto) {

		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(motorBuqueDto.getTipo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tipo de motor es obligatorio.");
		}

		if (StringUtils.isBlank(motorBuqueDto.getMarca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La marca del motor es obligatoria.");
		}

		if (StringUtils.isBlank(motorBuqueDto.getModelo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El modelo del motor es obligatorio.");
		}

		if (StringUtils.isNotBlank(motorBuqueDto.getAnioConstruccion()) && (!UtilesValidaciones.validarNumero(motorBuqueDto.getAnioConstruccion()) || motorBuqueDto.getAnioConstruccion()
				.length() != 4)) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El año de construcción del motor no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarPotencia(motorBuqueDto.getPotenciaKw())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La potencia KW del motor debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPotencia(motorBuqueDto.getPotenciaCv())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La potencia CV del motor debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		return result;

	}

}
