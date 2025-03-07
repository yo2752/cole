package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.OtroImporteDao;
import org.gestoresmadrid.core.registradores.model.vo.OtroImporteVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioOtroImporte;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.OtroImporteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioOtroImporteImpl implements ServicioOtroImporte {

	private static final long serialVersionUID = 7305605957238024710L;

	@Autowired
	private OtroImporteDao otroImporteDao;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultRegistro getOtroImporte(String identificador) {
		ResultRegistro result = new ResultRegistro();

		OtroImporteDto otroImporteDto = conversor.transform(otroImporteDao.getOtroImporte(identificador), OtroImporteDto.class);

		if (null != otroImporteDto) {
			result.setObj(otroImporteDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener el Importe");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro guardarOActualizarOtroImporte(OtroImporteDto otroImporteDto, long idDatosFinancieros) {
		ResultRegistro result;

		result = validarOtroImporte(otroImporteDto);
		if (result.isError())
			return result;

		// Si tenemos el identificador de importe entonces actualizamos, si no añadimos
		if (0 != otroImporteDto.getIdOtroImporte()) {
			otroImporteDto.setFecModificacion(utilesFecha.getTimestampActual());
		} else {
			otroImporteDto.setIdDatosFinancieros(new BigDecimal(idDatosFinancieros));
			otroImporteDto.setFecCreacion(utilesFecha.getTimestampActual());
		}

		OtroImporteVO otroImporteVO = otroImporteDao.guardarOActualizar(conversor.transform(otroImporteDto, OtroImporteVO.class));

		if (null != otroImporteVO) {
			result.setMensaje("Importe actualizado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar el Importe");
		}

		return result;
	}

	@Override
	@Transactional
	public ResultRegistro borrarOtroImporte(String id) {
		ResultRegistro result = new ResultRegistro();
		if (otroImporteDao.borrar(otroImporteDao.getOtroImporte(id))) {
			result.setMensaje("Importe eliminado correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando Importe");
		}
		return result;
	}

	@Override
	public ResultRegistro validarOtroImporte(OtroImporteDto otroImporteDto) {
		ResultRegistro resultado = new ResultRegistro();

		if (StringUtils.isBlank(otroImporteDto.getTipoOtroImporte())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El tipo de importe del bloque otros importes es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(otroImporteDto.getImporte())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe del bloque otros importes es obligatorio.");
		} else if (!UtilesValidaciones.validarImporte(otroImporteDto.getImporte())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El importe del bloque otros importes debe ser de hasta diez dígitos parte entera y dos dígitos parte decimal, separados por un punto.");
		}

		if (null != otroImporteDto.getPorcentaje() && !UtilesValidaciones.validarPorcentaje(String.valueOf(otroImporteDto.getPorcentaje()))) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El porcentaje de otros importes no tiene el formato correcto.");
		}

		if (!UtilesValidaciones.validarCamposSiNo(otroImporteDto.getCondicionante())) {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("El campo condicionante de otros importes no tiene el formato correcto.");
		}

		return resultado;
	}

}
