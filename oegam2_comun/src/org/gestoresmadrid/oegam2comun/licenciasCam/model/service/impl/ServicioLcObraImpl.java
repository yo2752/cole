package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcObraDao;
import org.gestoresmadrid.core.licencias.model.vo.LcObraVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcObra;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcObraDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcParteAutonomaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcObraImpl implements ServicioLcObra {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcObraImpl.class);

	@Autowired
	LcObraDao obraDao;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarObra(LcObraDto obraDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = validarObraGuardado(obraDto);
			if (resultado.getError()) {
				return resultado;
			}

			// Añadimos el nuevo Tipo de obra si es indicado
			if (StringUtils.isNotBlank(obraDto.getTipoObraPantalla())) {
				StringBuffer tipo = new StringBuffer(obraDto.getTipoObra());
				if (tipo.length() > 0) {
					tipo.append(";");
				}
				tipo.append(obraDto.getTipoObraPantalla());
				obraDto.setTipoObra(tipo.toString());
			}

			LcObraVO obraVO = obraDao.guardarOActualizar(conversor.transform(obraDto, LcObraVO.class));
			if (null != obraVO) {
				resultado.setMensaje("Datos de la obra actualizados correctamente");
				resultado.setObj(obraVO.getIdDatosObra());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensaje("Error al actualizar los datos de la obra");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los datos de la obra, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje("Ha sucedido un error a la hora de guardar los datos de la obra.");
		}
		return resultado;
	}

	private ResultadoLicenciasBean validarObraGuardado(LcObraDto obraDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		if (StringUtils.isBlank(obraDto.getTipoObra()) && StringUtils.isBlank(obraDto.getTipoObraPantalla())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El tipo de obra del bloque Datos Obra no puede ir vacío.<br/><br/>");
		} else if (StringUtils.isNotBlank(obraDto.getTipoObra()) && StringUtils.isNotBlank(obraDto.getTipoObraPantalla()) && obraDto.getTipoObra().contains(obraDto.getTipoObraPantalla())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" Tipo de obra del bloque Datos Obra ya indicada.<br/><br/>");
		}
		if (StringUtils.isBlank(obraDto.getDescripcionObra())) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La descripción del bloque Datos Obra no puede ir vacía.<br/><br/>");
		}
		if (obraDto.getPresupuestoTotal() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" El presupuesto total del bloque Datos Obra no puede ir vacío.<br/><br/>");
		}
		if (obraDto.getSuperficieAfectada() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.addMensaje(" La superficie afectada del bloque Datos Obra no puede ir vacía.<br/><br/>");
		}
		return resultado;
	}

	@Override
	public void validarDatosObra(LcObraDto obra, ResultadoLicenciasBean resultado) {
		if (obra != null) {
			if (obra.getTipoObra() == null || obra.getTipoObra().isEmpty()) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("Debe añadir un Tipo de Obra");
			}
			if (StringUtils.isBlank(obra.getDescripcionObra())) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Descripción de la Obra es obligatorio.");
			}
			if (obra.getPresupuestoTotal() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Presupuesto Total de la Obra es obligatorio.");
			}
			if (obra.getSuperficieAfectada() == null) {
				resultado.setError(Boolean.TRUE);
				resultado.addValidacion("El campo Superficie Afectada de la Obra es obligatorio.");
			}
			if (obra.getPartesAutonomas() != null && !obra.getPartesAutonomas().isEmpty()) {
				for (LcParteAutonomaDto parteAutonoma : obra.getPartesAutonomas()) {
					if (parteAutonoma.getNumero() == null || StringUtils.isBlank(parteAutonoma.getDescripcion())) {
						resultado.setError(Boolean.TRUE);
						resultado.addValidacion("Toda la información de una Parte Autónoma de los datos de Obra debe ir rellena.");
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("Debe rellenar los datos de la obra.");
		}
	}
}
