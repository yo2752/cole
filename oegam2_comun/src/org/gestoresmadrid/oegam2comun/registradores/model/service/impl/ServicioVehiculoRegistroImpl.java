package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.VehiculoRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.VehiculoRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioVehiculoRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.VehiculoRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioVehiculoRegistroImpl implements ServicioVehiculoRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private VehiculoRegistroDao vehiculoRegistroDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getVehiculoRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		VehiculoRegistroDto vehiculoRegistroDto = conversor.transform(vehiculoRegistroDao.getVehiculoRegistro(identificador), VehiculoRegistroDto.class);

		if (null != vehiculoRegistroDto) {
			result.setObj(vehiculoRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener vehículo");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarVehiculoRegistro(VehiculoRegistroDto vehiculoRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarVehiculo(vehiculoRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de vehiculo entonces actualizamos, si no añadimos
		if (0 != vehiculoRegistroDto.getIdVehiculo()) {
			vehiculoRegistroDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			vehiculoRegistroDto.setIdPropiedad(idPropiedad);
			vehiculoRegistroDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		VehiculoRegistroVO vehiculoRegistroVO = vehiculoRegistroDao.guardarOActualizar(conversor.transform(vehiculoRegistroDto, VehiculoRegistroVO.class));

		if (null != vehiculoRegistroVO) {
			result.setMensaje("Vehículo guardado correctamente");
			result.setObj(vehiculoRegistroVO.getIdVehiculo());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar vehículo");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarVehiculoRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (vehiculoRegistroDao.borrar(vehiculoRegistroDao.getVehiculoRegistro(id))) {
			result.setMensaje("Vehículo eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando vehículo");
		}
		return result;
	}

	@Override
	@Transactional
	public VehiculoRegistroDto getVehiculoPorPropiedad(String idPropiedad) {
		return conversor.transform(vehiculoRegistroDao.getVehiculoPorPropiedad(new BigDecimal(idPropiedad)), VehiculoRegistroDto.class);
	}

	@Override
	public ResultRegistro validarVehiculo(VehiculoRegistroDto vehiculo) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(vehiculo.getMatricula())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La matrícula es obligatoria.");
		} else if (!UtilesValidaciones.validarMatricula(vehiculo.getMatricula())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La matrícula no tiene el formato correcto.");
		}

		if (StringUtils.isBlank(vehiculo.getBastidor())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El bastidor es obligatorio.");
		} else if (!UtilesValidaciones.validarBastidor(vehiculo.getBastidor())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El bastidor no tiene el formato correcto.");
		}

		return result;
	}

}
