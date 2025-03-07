package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcInfoEdificioAltaDao;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioAltaVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDatosPortalAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcInfoEdificioAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPlantaAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDatosPortalAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcInfoEdificioAltaDto;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcSupNoComputablePlantaDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcInfoEdificioAltaImpl implements ServicioLcInfoEdificioAlta {

	private static final long serialVersionUID = 7305605957238024710L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcInfoEdificioAltaImpl.class);

	@Autowired
	LcInfoEdificioAltaDao infoEdificioAltaDao;

	@Autowired
	ServicioLcDireccion servicioLcDireccion;

	@Autowired
	ServicioLcDatosPortalAlta servicioLcDatosPortalAlta;

	@Autowired
	Conversor conversor;

	@Transactional
	@Override
	public ResultadoLicenciasBean getDatosInfoEdificioAlta(long identificador) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		LcInfoEdificioAltaDto infoEdificioAltaDto = conversor.transform(infoEdificioAltaDao.getInfoEdificioAlta(identificador), LcInfoEdificioAltaDto.class);
		if (infoEdificioAltaDto != null) {
			result.setObj(infoEdificioAltaDto);
		} else {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la información del Edificio Alta");
		}
		return result;
	}

	@Override
	@Transactional
	public List<LcInfoEdificioAltaDto> getInfoEdificiosAlta(long id) {
		List<LcInfoEdificioAltaDto> resultado = conversor.transform(infoEdificioAltaDao.getInfoEdificiosAlta(id), LcInfoEdificioAltaDto.class);
		if (resultado != null && !resultado.isEmpty()) {
			Collections.sort(resultado, new ComparadorInfoEdificiosAlta());
		}
		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarInfoEdificioAlta(LcInfoEdificioAltaDto infoEdificioAltaDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			resultado = servicioLcDireccion.guardarOActualizarDireccion(infoEdificioAltaDto.getLcDirEdificacionAlta());
			if (resultado.getError()) {
				return resultado;
			} else {
				infoEdificioAltaDto.setIdLcDirEdificacionAlta((Long) resultado.getObj());
			}

			LcInfoEdificioAltaVO infoEdificioAltaVO = infoEdificioAltaDao.guardarOActualizar(conversor.transform(infoEdificioAltaDto, LcInfoEdificioAltaVO.class));

			if (infoEdificioAltaVO != null) {
				resultado.setMensaje("Información del Edificio Alta actualizada correctamente");
				resultado.setObj(infoEdificioAltaVO.getIdInfoEdificioAlta());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al actualizar información del Edificio Alta");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar Información del Edificio Alta, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar Información del Edificio Alta.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return resultado;
	}

	@Transactional
	@Override
	public ResultadoLicenciasBean borrarInfoEdificioAlta(long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);
		LcInfoEdificioAltaVO infoEdificioAltaVO = infoEdificioAltaDao.getInfoEdificioAlta(id);
		if (infoEdificioAltaVO == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Error al obtener la información del Edificio Alta");
			return result;
		}

		LcInfoEdificioAltaDto infoEdificioAltaDto = conversor.transform(infoEdificioAltaVO, LcInfoEdificioAltaDto.class);

		// Borramos los portales (si existen)
		if (null != infoEdificioAltaDto.getLcDatosPortalesAlta() && !infoEdificioAltaDto.getLcDatosPortalesAlta().isEmpty()) {
			int numportales = infoEdificioAltaDto.getLcDatosPortalesAlta().size();
			for (int i = 0; i < numportales; i++) {
				servicioLcDatosPortalAlta.borrarDatosPortalAlta(infoEdificioAltaDto.getLcDatosPortalesAlta().get(i).getIdDatosPortalAlta());
			}
		}

		// Borramos el edificio
		if (infoEdificioAltaDao.borrar(infoEdificioAltaVO)) {
			result.addMensaje("Información del Edificio Alta eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando información del Edificio Alta");
		}

		// Borramos su dirección
		servicioLcDireccion.borrarDireccion(infoEdificioAltaDto.getLcDirEdificacionAlta().getIdDireccion());
		return result;
	}

	private class ComparadorInfoEdificiosAlta implements Comparator<LcInfoEdificioAltaDto> {
		@Override
		public int compare(LcInfoEdificioAltaDto o1, LcInfoEdificioAltaDto o2) {
			return ((Long) o1.getIdInfoEdificioAlta()).compareTo(o2.getIdInfoEdificioAlta());
		}
	}

	@Override
	public void validarInfoEdificacionAlta(LcInfoEdificioAltaDto alta, ResultadoLicenciasBean resultado) {
		if (alta.getLcDirEdificacionAlta() != null) {
			servicioLcDireccion.validarDireccion(alta.getLcDirEdificacionAlta(), resultado, "Edificación Alta de uno de los edificios", false, false);
			if (resultado != null && !resultado.getError()) {
				if (alta.getLcDatosPortalesAlta() != null && !alta.getLcDatosPortalesAlta().isEmpty()) {
					for (LcDatosPortalAltaDto portal : alta.getLcDatosPortalesAlta()) {
						if (portal.getUnidadesSbRasante() == null || portal.getSuperficieComputableSbRasante() == null || portal.getSuperficieConstruidaSbRasante() == null) {
							resultado.setError(Boolean.TRUE);
							resultado.addValidacion("Toda la información del sobre rasante de uno de los portales en Edificación Alta debe ir rellena.");
						} else {
							if (portal.getLcDatosPlantasAlta() != null && !portal.getLcDatosPlantasAlta().isEmpty()) {
								for (LcDatosPlantaAltaDto planta : portal.getLcDatosPlantasAlta()) {
									validarPlanta(planta, resultado);
									if (resultado != null && resultado.getError()) {
										resultado.addValidacion("Toda la información de una de las plantas en Edificación Alta debe ir rellena.");
									} else {
										if (planta.getLcSupNoComputablesPlanta() != null && !planta.getLcSupNoComputablesPlanta().isEmpty()) {
											for (LcSupNoComputablePlantaDto supPlanta : planta.getLcSupNoComputablesPlanta()) {
												if (supPlanta.getTamanio() == null || supPlanta.getTipo() == null) {
													resultado.setError(Boolean.TRUE);
													resultado.addValidacion("Toda la información de una de las superficies no computables en Edificación Alta debe ir rellena.");
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			resultado.setError(Boolean.TRUE);
			resultado.addValidacion("La dirección de uno de los edificios en Edificación Alta debe estar rellena.");
		}
	}

	private void validarPlanta(LcDatosPlantaAltaDto planta, ResultadoLicenciasBean resultado) {
		if (StringUtils.isBlank(planta.getTipoPlanta())) {
			resultado.setError(Boolean.TRUE);
		}
		if (StringUtils.isBlank(planta.getNumPlanta())) {
			resultado.setError(Boolean.TRUE);
		}
		if (planta.getAlturaLibre() == null) {
			resultado.setError(Boolean.TRUE);
		}
		if (planta.getAlturaPiso() == null) {
			resultado.setError(Boolean.TRUE);
		}
		if (StringUtils.isBlank(planta.getUsoPlanta())) {
			resultado.setError(Boolean.TRUE);
		}
		if (planta.getNumUnidades() == null) {
			resultado.setError(Boolean.TRUE);
		}
		if (planta.getSupComputable() == null) {
			resultado.setError(Boolean.TRUE);
		}
		if (planta.getSupConstruida() == null) {
			resultado.setError(Boolean.TRUE);
		}
	}
}
