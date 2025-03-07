package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.AeronaveDao;
import org.gestoresmadrid.core.registradores.model.vo.AeronaveRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAeronaveRegistro;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AeronaveRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioAeronaveRegistroImpl implements ServicioAeronaveRegistro {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private AeronaveDao aeronaveRegistroDao;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public ResultRegistro getAeronaveRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		AeronaveRegistroDto aeronaveRegistroDto = conversor.transform(aeronaveRegistroDao.getAeronaveRegistro(identificador), AeronaveRegistroDto.class);

		if (null != aeronaveRegistroDto) {
			result.setObj(aeronaveRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener aeronave");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarAeronaveRegistro(AeronaveRegistroDto aeronaveRegistroDto, BigDecimal idPropiedad) {
		ResultRegistro result;

		result = validarAeronaveRegistro(aeronaveRegistroDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de aeronave entonces actualizamos, si no añadimos
		if (0 == aeronaveRegistroDto.getIdAeronave()) {
			aeronaveRegistroDto.setIdPropiedad(idPropiedad);
		}

		AeronaveRegistroVO aeronaveRegistroVO = aeronaveRegistroDao.guardarOActualizar(conversor.transform(aeronaveRegistroDto, AeronaveRegistroVO.class));

		if (null != aeronaveRegistroVO) {
			result.setMensaje("Aeronave guardada correctamente");
			result.setObj(aeronaveRegistroVO.getIdAeronave());
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al guardar aeronave");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarAeronaveRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (aeronaveRegistroDao.borrar(aeronaveRegistroDao.getAeronaveRegistro(id))) {
			result.setMensaje("Aeronave eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando aeronave");
		}
		return result;
	}

	@Override
	@Transactional
	public AeronaveRegistroDto getAeronavePorPropiedad(String idPropiedad) {
		return conversor.transform(aeronaveRegistroDao.getAeronavePorPropiedad(new BigDecimal(idPropiedad)), AeronaveRegistroDto.class);
	}

	@Override
	public ResultRegistro validarAeronaveRegistro(AeronaveRegistroDto aeronaveRegistroDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(aeronaveRegistroDto.getTipo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tipo de la aeronave es obligatorio.");
		}

		if (StringUtils.isBlank(aeronaveRegistroDto.getMarca())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La marca de la aeronave es obligatoria.");
		}

		if (StringUtils.isBlank(aeronaveRegistroDto.getModelo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El modelo de la aeronave es obligatorio.");
		}

		if (StringUtils.isBlank(aeronaveRegistroDto.getMatricula())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La matrícula de la aeronave es obligatoria.");
		}

		if (StringUtils.isBlank(aeronaveRegistroDto.getNumSerie())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La serie de la aeronave es obligatoria.");
		}

		return result;
	}

}
