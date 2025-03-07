package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.gestoresmadrid.core.registradores.model.dao.MinutaRegistroDao;
import org.gestoresmadrid.core.registradores.model.vo.MinutaRegistroVO;
import org.gestoresmadrid.oegam2comun.registradores.clases.ResultRegistro;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioMinutaRegistro;
import org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesValidaciones;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.MinutaRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioMinutaRegistroImpl implements ServicioMinutaRegistro {

	private static final long serialVersionUID = -1605563582795681666L;

	private static Logger log = Logger.getLogger(ServicioMinutaRegistroImpl.class);

	@Autowired
	private MinutaRegistroDao minutaRegistroDao;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public ResultRegistro getMinutaRegistro(String identificador) {
		ResultRegistro result = new ResultRegistro();

		MinutaRegistroDto minutaRegistroDto = conversor.transform(minutaRegistroDao.getMinutaRegistro(identificador), MinutaRegistroDto.class);

		if (null != minutaRegistroDto) {
			result.setObj(minutaRegistroDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener los datos de la minuta");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro guardarOActualizarMinutaRegistro(MinutaRegistroDto minutaRegistroDto, BigDecimal idTramiteRegistro) {
		ResultRegistro result;

		result = validarMinutaRegistro(minutaRegistroDto);
		if (result.isError())
			return result;

		// Si no tenemos el identificador de minuta entonces añadimos
		if (0 == minutaRegistroDto.getIdMinuta()) {
			minutaRegistroDto.setIdTramiteRegistro(idTramiteRegistro);
		}

		if (null != minutaRegistroDto.getFechaMinutaRegistro()) {
			try {
				minutaRegistroDto.setFechaMinuta(minutaRegistroDto.getFechaMinutaRegistro().getDate());
			} catch (ParseException e) {
				log.error(e.getMessage());
			}
		}

		MinutaRegistroVO minutaRegistroVO = minutaRegistroDao.guardarOActualizar(conversor.transform(minutaRegistroDto, MinutaRegistroVO.class));

		if (null != minutaRegistroVO) {
			result.setMensaje("Datos de la minuta actualizados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al actualizar los datos de la minuta");
		}

		return result;
	}

	@Transactional
	@Override
	public ResultRegistro borrarMinutaRegistro(String id) {
		ResultRegistro result = new ResultRegistro();
		if (minutaRegistroDao.borrar(minutaRegistroDao.getMinutaRegistro(id))) {
			result.setMensaje("Datos de la minuta eliminados correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error eliminando los datos de la minuta");
		}
		return result;
	}

	@Override
	public ResultRegistro validarMinutaRegistro(MinutaRegistroDto minutaRegistroDto) {
		ResultRegistro result = new ResultRegistro();

		if (StringUtils.isBlank(minutaRegistroDto.getAceptada())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("Debe indicar si acepta o no la minuta.");
		}

		if (StringUtils.isBlank(minutaRegistroDto.getNumeroMinuta())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("El número de minuta es obligatorio.");
		}

		if (!UtilesValidaciones.validarObligatoriedad(minutaRegistroDto.getFechaMinutaRegistro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de la minuta es obligatoria.");
		} else if (!UtilesValidaciones.validarFecha(minutaRegistroDto.getFechaMinutaRegistro())) {
			result.setError(Boolean.TRUE);
			result.addValidacion("La fecha de la minuta no tiene el formato correcto.");
		}

		return result;
	}

	@Override
	@Transactional
	public List<MinutaRegistroDto> getMinutasPorTramite(BigDecimal idTramiteRegistro) {
		return conversor.transform(minutaRegistroDao.getMinutasRegistroPorTramite(idTramiteRegistro), MinutaRegistroDto.class);
	}

}
