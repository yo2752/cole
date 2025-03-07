package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.ComisionDao;
import org.gestoresmadrid.core.registradores.model.vo.ComisionVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioComision;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.ComisionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioComisionImpl implements ServicioComision {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private ComisionDao comisionDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getComision(String identificador) {
		ResultRegistro result = new ResultRegistro();

		ComisionDto comisionDto = conversor.transform(comisionDao.getComision(identificador), ComisionDto.class);

		if (null != comisionDto) {
			result.setObj(comisionDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la comisión");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarComision(String id) {
		ResultRegistro result = new ResultRegistro();
		if (comisionDao.borrar(comisionDao.getComision(id))) {
			result.setMensaje("Comisión eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando comisión");
		}
		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarComision(ComisionDto comisionDto, long idDatosFinancieros) {
		ResultRegistro result;

		result = validarComision(comisionDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de comisión entonces actualizamos, si no añadimos
		if (0 != comisionDto.getIdComision()) {
			comisionDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			comisionDto.setIdDatosFinancieros(new BigDecimal(idDatosFinancieros));
			comisionDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		ComisionVO comisionVO = comisionDao.guardarOActualizar(conversor.transform(comisionDto, ComisionVO.class));

		if (null != comisionVO) {
			result.setMensaje("Comisión actualizada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar la comisión");
		}

		return result;
	}

	@Override
	public ResultRegistro validarComision(ComisionDto comisionDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(comisionDto.getTipoComision())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El tipo de comisión es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(comisionDto.getPorcentaje())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El porcentaje de la comisión es obligatorio.");
		} else if (!UtilesValidaciones.validarPorcentaje(String.valueOf(comisionDto.getPorcentaje()))) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El porcentaje de la comisión no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(comisionDto.getImporteMinimo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El importe mínimo de la comisión es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(comisionDto.getImporteMinimo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El importe mínimo de la comisión no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(comisionDto.getImporteMaximo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El importe máximo de la comisión es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(comisionDto.getImporteMaximo())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El importe máximo de la comisión no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(comisionDto.getFinanciado())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El campo financiado de la comisión no tiene el formato correcto.");
		}

		return result;
	}

}
