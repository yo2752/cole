package org.gestoresmadrid.oegam2comun.licenciasCam.model.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.licencias.model.dao.LcDireccionDao;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcPersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcPersonaDireccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicBDCRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoLicenciasBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.ResultadoValidacionDireccionBean;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.dto.LcDireccionDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLcDireccionImpl implements ServicioLcDireccion {

	private static final long serialVersionUID = 7305605957238024710L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLcDireccionImpl.class);

	@Autowired
	ServicioLcPersonaDireccion servicioLcPersonaDireccion;

	@Autowired
	ServicioLicBDCRestWS servicioLicBDCRestWS;

	@Autowired
	LcDireccionDao direccionDao;

	@Autowired
	Conversor conversor;

	@Autowired
	Utiles utiles;

	@Transactional
	@Override
	public ResultadoLicenciasBean guardarOActualizarDireccion(LcDireccionDto direccionDto) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			LcDireccionVO direccionVO = conversor.transform(direccionDto, LcDireccionVO.class);
			cambiarMayusculas(direccionVO);
			direccionVO = direccionDao.guardarOActualizar(direccionVO);
			if (direccionVO != null) {
				resultado.setMensaje("Datos de la dirección actualizados correctamente");
				resultado.setObj(direccionVO.getIdDireccion());
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.addMensaje("Error al actualizar los datos de la dirección");
			}

		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la dirección, error: ", e);
			resultado.setError(true);
			resultado.addMensaje("Ha sucedido un error a la hora de guardar la dirección.");
		}

		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarActualizarPersona(LcDireccionVO direccion, Long idPersona, String nif, String numColegiado) {
		ResultBean result = new ResultBean();
		try {
			cambiarMayusculas(direccion);
			LcPersonaDireccionVO lcPersonaDireccionVO = servicioLcPersonaDireccion.buscarDireccionExistente(direccion, numColegiado, nif);
			if (lcPersonaDireccionVO != null) {
				boolean actualiza = false;
				LcDireccionVO direccionNueva = lcPersonaDireccionVO.getLcDireccion();
				actualiza = esModificada(direccion, direccionNueva);
				if (actualiza) {
					direccionDao.guardarOActualizar(direccionNueva);
				}
				result.addAttachment(DIRECCION, direccionNueva);
			} else {
				lcPersonaDireccionVO = servicioLcPersonaDireccion.buscarPersonaDireccion(numColegiado, nif);
				if (lcPersonaDireccionVO != null) {
					lcPersonaDireccionVO.setFechaFin(new Date());
					servicioLcPersonaDireccion.guardarActualizar(lcPersonaDireccionVO);
				}

				Long idDireccionCompleta = (Long) direccionDao.guardar(direccion);

				lcPersonaDireccionVO = new LcPersonaDireccionVO();
				lcPersonaDireccionVO.setFechaInicio(new Date());
				lcPersonaDireccionVO.setNumColegiado(numColegiado);
				lcPersonaDireccionVO.setIdPersona(idPersona);
				lcPersonaDireccionVO.setNif(nif);
				lcPersonaDireccionVO.setIdDireccion(idDireccionCompleta);
				servicioLcPersonaDireccion.guardarActualizar(lcPersonaDireccionVO);
				result.addAttachment(DIRECCION, direccion);
			}
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion de un interviniente de licencias", e);
			result.setMensaje("Error al guardar la dirección de la persona de licencias");
			result.setError(true);
		}
		return result;
	}

	@Override
	public void validarDireccion(LcDireccionDto direccion, ResultadoLicenciasBean resultado, String textoValidacion, boolean esInterviniente, boolean validacionRest) {
		boolean errorValidacion = false;
		if (direccion != null) {
			if (esInterviniente) {
				if (StringUtils.isBlank(direccion.getPais())) {
					errorValidacion = true;
				}
				if (StringUtils.isBlank(direccion.getProvincia())) {
					errorValidacion = true;
				}
				if (StringUtils.isBlank(direccion.getMunicipio())) {
					errorValidacion = true;
				}
				if (StringUtils.isBlank(direccion.getPoblacion())) {
					errorValidacion = true;
				}
				if (StringUtils.isBlank(direccion.getCodigoPostal())) {
					errorValidacion = true;
				}
			}
			if (StringUtils.isBlank(direccion.getTipoVia())) {
				errorValidacion = true;
			}
			if (StringUtils.isBlank(direccion.getNombreVia())) {
				errorValidacion = true;
			}
			if (StringUtils.isBlank(direccion.getTipoNumeracion())) {
				errorValidacion = true;
			}
			if (direccion.getNumCalle() == null) {
				errorValidacion = true;
			}
			if (errorValidacion) {
				resultado.addValidacion("La dirección del " + textoValidacion + " le faltan datos obligatorios. ");
				resultado.setError(Boolean.TRUE);
			} else {
				if (validacionRest) {
					validarDireccionRest(direccion, textoValidacion);
				}
			}
		}
	}

	private ResultadoLicenciasBean validarDireccionRest(LcDireccionDto direccion, String textyoValidacion) {
		ResultadoLicenciasBean resultado = new ResultadoLicenciasBean(Boolean.FALSE);
		try {
			ResultadoValidacionDireccionBean resultRest = servicioLicBDCRestWS.validarDireccionCompletaRest(direccion);
			if (resultRest != null && !resultRest.getError()) {
				if (resultRest.getErrorPais() != null && resultRest.getErrorPais()) {
					resultado.setError(Boolean.TRUE);
				} else if (resultRest.getErrorProvincia() != null && resultRest.getErrorProvincia()) {
					resultado.setError(Boolean.TRUE);
				} else if (resultRest.getErrorPoblacion() != null && resultRest.getErrorPoblacion()) {
					resultado.setError(Boolean.TRUE);
				} else if (resultRest.getErrorVial() != null && resultRest.getErrorVial()) {
					resultado.setError(Boolean.TRUE);
				} else if (resultRest.getErrorNumero() != null && resultRest.getErrorNumero()) {
					resultado.setError(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
			}
			if (resultado != null && resultado.getError()) {
				resultado.addMensaje("La dirección del " + textyoValidacion + " no es una dirección válida para la CAM. Pulse la opción 'Validar Dirección' para comprobar los errores.");
			}
		} catch (Exception e) {
			log.error("Error al validar la dirección con el servicio Rest", e);
		}
		return resultado;
	}

	@Override
	public ResultadoLicenciasBean borrarDireccion(Long id) {
		ResultadoLicenciasBean result = new ResultadoLicenciasBean(Boolean.FALSE);

		if (direccionDao.borrar(direccionDao.getDireccion(id))) {
			result.addMensaje("La dirección eliminada correctamente");
		} else {
			result.setError(Boolean.TRUE);
			result.addMensaje("Error eliminando la dirección.");
		}
		return result;
	}

	private void cambiarMayusculas(LcDireccionVO direccion) {
		if (StringUtils.isNotBlank(direccion.getMunicipio())) {
			direccion.setMunicipio(direccion.getMunicipio().toUpperCase());
		}

		if (StringUtils.isNotBlank(direccion.getPoblacion())) {
			direccion.setPoblacion(direccion.getPoblacion().toUpperCase());
		}

		if (StringUtils.isNotBlank(direccion.getNombreVia())) {
			direccion.setNombreVia(direccion.getNombreVia().toUpperCase());
		}
	}

	private boolean esModificada(LcDireccionVO direccion, LcDireccionVO direccionBBDD) {
		boolean actualiza = false;
		direccion.setIdDireccion(direccionBBDD.getIdDireccion());
		try {
			if (!utiles.sonIgualesString(direccion.getPais(), direccionBBDD.getPais())) {
				actualiza = true;
				direccionBBDD.setPais(direccion.getPais());
			}
			if (!utiles.sonIgualesString(direccion.getMunicipio(), direccionBBDD.getMunicipio())) {
				actualiza = true;
				direccionBBDD.setMunicipio(direccion.getMunicipio());
			}
			if (!utiles.sonIgualesString(direccion.getProvincia(), direccionBBDD.getProvincia())) {
				actualiza = true;
				direccionBBDD.setProvincia(direccion.getProvincia());
			}
			if (!utiles.sonIgualesString(direccion.getPoblacion(), direccionBBDD.getPoblacion())) {
				actualiza = true;
				direccionBBDD.setPoblacion(direccion.getPoblacion());
			}
			if (!utiles.sonIgualesString(direccion.getCodigoPostal(), direccionBBDD.getCodigoPostal())) {
				actualiza = true;
				direccionBBDD.setCodigoPostal(direccion.getCodigoPostal());
			}

			if (!utiles.sonIgualesString(direccion.getTipoVia(), direccionBBDD.getTipoVia())) {
				actualiza = true;
				direccionBBDD.setTipoVia(direccion.getTipoVia());
			}
			if (!utiles.sonIgualesString(direccion.getNombreVia(), direccionBBDD.getNombreVia())) {
				actualiza = true;
				direccionBBDD.setNombreVia(direccion.getNombreVia());
			}
			if (!utiles.sonIgualesString(direccion.getTipoNumeracion(), direccionBBDD.getTipoNumeracion())) {
				actualiza = true;
				direccionBBDD.setTipoNumeracion(direccion.getTipoNumeracion());
			}
			if (!utiles.sonIgualesBigDecimal(direccion.getNumCalle(), direccionBBDD.getNumCalle())) {
				actualiza = true;
				direccionBBDD.setNumCalle(direccion.getNumCalle());
			}
			if (!utiles.sonIgualesString(direccion.getCalificador(), direccionBBDD.getCalificador())) {
				actualiza = true;
				direccionBBDD.setCalificador(direccion.getCalificador());
			}
			if (!utiles.sonIgualesString(direccion.getPuerta(), direccionBBDD.getPuerta())) {
				actualiza = true;
				direccionBBDD.setPuerta(direccion.getPuerta());
			}
			if (!utiles.sonIgualesString(direccion.getPlanta(), direccionBBDD.getPlanta())) {
				actualiza = true;
				direccionBBDD.setPlanta(direccion.getPlanta());
			}
			if (!utiles.sonIgualesString(direccion.getEscalera(), direccionBBDD.getEscalera())) {
				actualiza = true;
				direccionBBDD.setEscalera(direccion.getEscalera());
			}
			if (!utiles.sonIgualesString(direccion.getClaseDireccion(), direccionBBDD.getClaseDireccion())) {
				actualiza = true;
				direccionBBDD.setClaseDireccion(direccion.getClaseDireccion());
			}
			if (!utiles.sonIgualesBigDecimal(direccion.getCodDireccion(), direccionBBDD.getCodDireccion())) {
				actualiza = true;
				direccionBBDD.setCodDireccion(direccion.getCodDireccion());
			}
			if (!utiles.sonIgualesString(direccion.getAmbito(), direccionBBDD.getAmbito())) {
				actualiza = true;
				direccionBBDD.setAmbito(direccion.getAmbito());
			}
			if (!utiles.sonIgualesString(direccion.getCoordenadaX(), direccionBBDD.getCoordenadaX())) {
				actualiza = true;
				direccionBBDD.setCoordenadaX(direccion.getCoordenadaX());
			}
			if (!utiles.sonIgualesString(direccion.getCoordenadaY(), direccionBBDD.getCoordenadaY())) {
				actualiza = true;
				direccionBBDD.setCoordenadaY(direccion.getCoordenadaY());
			}
			if (!utiles.sonIgualesString(direccion.getDistrito(), direccionBBDD.getDistrito())) {
				actualiza = true;
				direccionBBDD.setDistrito(direccion.getDistrito());
			}
			if (!utiles.sonIgualesString(direccion.getReferenciaCatastral(), direccionBBDD.getReferenciaCatastral())) {
				actualiza = true;
				direccionBBDD.setReferenciaCatastral(direccion.getReferenciaCatastral());
			}
			if (!utiles.sonIgualesString(direccion.getEmplNoNormalizado(), direccionBBDD.getEmplNoNormalizado())) {
				actualiza = true;
				direccionBBDD.setEmplNoNormalizado(direccion.getEmplNoNormalizado());
			}
			if (!utiles.sonIgualesString(direccion.getLocal(), direccionBBDD.getLocal())) {
				actualiza = true;
				direccionBBDD.setLocal(direccion.getLocal());
			}
			if (!utiles.sonIgualesString(direccion.getCodLocal(), direccionBBDD.getCodLocal())) {
				actualiza = true;
				direccionBBDD.setCodLocal(direccion.getCodLocal());
			}
		} catch (Exception e) {
			log.error("Error al comparar la direccion de pantalla con la direccion de la bbdd", e);
		} finally {
			direccionDao.evict(direccion);
		}
		return actualiza;
	}
}
