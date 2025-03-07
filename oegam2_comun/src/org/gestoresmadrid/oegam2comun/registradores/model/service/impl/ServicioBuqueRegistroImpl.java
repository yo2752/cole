package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.BuqueDao;
import org.gestoresmadrid.core.registradores.model.vo.BuqueRegistroVO;
import org.gestoresmadrid.core.registradores.model.vo.MotorBuqueVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioBuqueRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMotorBuque;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.BuqueRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class ServicioBuqueRegistroImpl implements ServicioBuqueRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private BuqueDao buqueRegistroDao;

	@Autowired
	private ServicioMotorBuque servicioMotorBuque;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getBuqueRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		BuqueRegistroDto buqueRegistroDto = conversor.transform(buqueRegistroDao.getBuqueRegistro(identificador), BuqueRegistroDto.class);

		if (null != buqueRegistroDto) {
			result.setObj(buqueRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener buque");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarBuqueRegistro(BuqueRegistroDto buqueRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarBuqueRegistro(buqueRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de buque entonces actualizamos, si no añadimos
		if (0 == buqueRegistroDto.getIdBuque()) {
			buqueRegistroDto.setIdPropiedad(idPropiedad);
		}

		BuqueRegistroVO buqueRegistroVO = buqueRegistroDao.guardarOActualizar(conversor.transform(buqueRegistroDto, BuqueRegistroVO.class));

		if (null != buqueRegistroDto.getMotor() && null != buqueRegistroDto.getMotor().getTipo()) {
			result = servicioMotorBuque.guardarOActualizarMotorBuque(buqueRegistroDto.getMotor(), buqueRegistroVO.getIdBuque());
			if (result.isError()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return result;
			}
		}

		if (null != buqueRegistroVO) {
			result.setMensaje("Buque guardado correctamente");
			result.setObj(buqueRegistroVO.getIdBuque());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar buque");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarBuqueRegistro(String id) {
		ResultRegistro result = new ResultRegistro();

		// Obtenermos el buque
		BuqueRegistroVO buqueRegistro = buqueRegistroDao.getBuqueRegistro(id);
		if (null == buqueRegistro) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al recuperar el buque");
			return result;
		}

		// Borramos los motores si los tuviera
		if (null != buqueRegistro.getMotorBuques() && !buqueRegistro.getMotorBuques().isEmpty()) {
			for (MotorBuqueVO elemento : buqueRegistro.getMotorBuques()) {
				result = servicioMotorBuque.borrarMotorBuque(String.valueOf(elemento.getIdMotor()));
				if (result.isError()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}
			}
		}

		if (buqueRegistroDao.borrar(buqueRegistroDao.getBuqueRegistro(id))) {
			result.setMensaje("Buque eliminado correctamente");
		} else {
			result.setMensaje("Error eliminando buque");
			result.setError(Boolean.TRUE);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return result;
	}

	@Override
	@Transactional
	public BuqueRegistroDto getBuquePorPropiedad(String idPropiedad) {
		return conversor.transform(buqueRegistroDao.getBuquePorPropiedad(new BigDecimal(idPropiedad)), BuqueRegistroDto.class);
	}

	@Override
	public ResultRegistro validarBuqueRegistro(BuqueRegistroDto buqueRegistroDto) {

		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(buqueRegistroDto.getNombreBuque())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El nombre del buque es obligatorio.");
		}

		if (!UtilesValidaciones.validarNumero(buqueRegistroDto.getPabellon())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El pabellón del buque no tiene el formato correcto.");
		}

		if ((StringUtils.isNotBlank(buqueRegistroDto.getCapitaniaMaritima()) || StringUtils.isNotBlank(buqueRegistroDto.getProvinciaMaritima()) || StringUtils.isNotBlank(buqueRegistroDto
				.getDistritoMaritimo()) || StringUtils.isNotBlank(buqueRegistroDto.getNumLista()) || StringUtils.isNotBlank(buqueRegistroDto.getFolioInscripcion()) || StringUtils.isNotBlank(
						buqueRegistroDto.getAnioInscripcion())) && StringUtils.isNotBlank(buqueRegistroDto.getMatriculaFluvial())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe rellenar el bloque Matrícula marítima o el bloque Matrícula fluvial, pero no ambos.");
		}

		else if (StringUtils.isBlank(buqueRegistroDto.getMatriculaFluvial())) {

			if (StringUtils.isNotBlank(buqueRegistroDto.getCapitaniaMaritima()) && StringUtils.isNotBlank(buqueRegistroDto.getProvinciaMaritima()) || StringUtils.isBlank(buqueRegistroDto
					.getCapitaniaMaritima()) && StringUtils.isBlank(buqueRegistroDto.getProvinciaMaritima())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("Dentro del bloque Matrícula buque debe rellenar capitanía marítima o provincia marítima, pero no ambas.");
			}

			if (!UtilesValidaciones.validarNumero(buqueRegistroDto.getCapitaniaMaritima())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("La capitanía marítima de la matrícula del buque no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(buqueRegistroDto.getDistritoMaritimo())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El distrito marítimo de la matrícula del buque es obligatorio.");
			}

			if (StringUtils.isBlank(buqueRegistroDto.getNumLista())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El número lista de la matrícula del buque es obligatorio.");
			}

			if (StringUtils.isBlank(buqueRegistroDto.getFolioInscripcion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio de inscripción de la matrícula del buque es obligatorio.");
			} else if (!UtilesValidaciones.validarNumero(buqueRegistroDto.getFolioInscripcion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El folio de inscripción de la matrícula del buque no tiene el formato correcto.");
			}

			if (StringUtils.isBlank(buqueRegistroDto.getAnioInscripcion())) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El año de inscripción de la matrícula del buque es obligatorio.");
			} else if (StringUtils.isNotBlank(buqueRegistroDto.getAnioInscripcion()) && (!UtilesValidaciones.validarNumero(buqueRegistroDto.getAnioInscripcion()) || buqueRegistroDto
					.getAnioInscripcion().length() != 4)) {
				result.setError(Boolean.TRUE);
				result.addValidacion("El año de inscripción de la matrícula del buque no tiene el formato correcto.");
			}

		}
		// Dimensiones
		if (!UtilesValidaciones.validarLongitud(buqueRegistroDto.getEslora())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La eslora debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarLongitud(buqueRegistroDto.getManga())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La manga debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarLongitud(buqueRegistroDto.getPuntal())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La dimensión puntual debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}
		if (!UtilesValidaciones.validarLongitud(buqueRegistroDto.getCaladoMaximo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El calado máximo debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		// Tonelaje
		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getBruto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje bruto debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getRegistradoBruto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje bruto registrado debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getNeto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje neto debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getRegistradoNeto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje neto registrado debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getDesplazamiento())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje de desplazamiento debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getPesoMuerto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje de peso muerto debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		if (!UtilesValidaciones.validarPeso(buqueRegistroDto.getCargaMaxima())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tonelaje de carga máxima debe ser de hasta diez dígitos parte entera y tres dígitos parte decimal, separados por un punto.");
		}

		// Casco
		if (StringUtils.isNotBlank(buqueRegistroDto.getAnioConstruccion()) && (!UtilesValidaciones.validarNumero(buqueRegistroDto.getAnioConstruccion()) || buqueRegistroDto.getAnioConstruccion()
				.length() != 4)) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El año de construcción del casco no tiene el formato correcto.");
		}

		return result;
	}

}
