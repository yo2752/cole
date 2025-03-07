package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.PactoVO;
import org.gestoresmadrid.core.registradores.model.dao.PactoDao;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioPacto;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.PactoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioPactoImpl implements ServicioPacto {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private PactoDao pactoDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getPacto(String identificador) {
		ResultRegistro result = new ResultRegistro();

		PactoDto pactoDto = conversor.transform(pactoDao.getPacto(identificador), PactoDto.class);

		if (null != pactoDto) {
			result.setObj(pactoDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener el acuerdo");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarPacto(PactoDto pactoDto, BigDecimal idTramiteRegistro) {
		ResultRegistro result;

		result = validarPacto(pactoDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de clausula entonces actualizamos, si no añadimos
		if (0 != pactoDto.getIdPacto()) {
			pactoDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			pactoDto.setIdTramiteRegistro(idTramiteRegistro);
			pactoDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		PactoVO pactoVO = pactoDao.guardarOActualizar(conversor.transform(pactoDto, PactoVO.class));

		if (null != pactoVO) {
			result.setMensaje("Acuerdo actualizado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar el acuerdo");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarPacto(String id) {
		ResultRegistro result = new ResultRegistro();
		if (pactoDao.borrar(pactoDao.getPacto(id))) {
			result.setMensaje("Acuerdo eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando acuerdo");
		}
		return result;
	}

	@Override
	public ResultRegistro validarPacto(PactoDto pactoDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(pactoDto.getTipoPacto())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tipo de pacto es obligatorio.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(pactoDto.getPactado())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El campo Ha sido pactado no tiene el formato correcto.");
		}

		return result;
	}

}
