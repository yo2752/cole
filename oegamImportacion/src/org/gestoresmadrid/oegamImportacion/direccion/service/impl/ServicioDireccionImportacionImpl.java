package org.gestoresmadrid.oegamImportacion.direccion.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.direccion.model.dao.DireccionDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDgtMunicipioImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioLocalidadDgtImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioMunicipioImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioProvinciaImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioPuebloImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioTipoViaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaDireccionImportacion;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDireccionImportacionImpl implements ServicioDireccionImportacion {

	private static final long serialVersionUID = -4352554321135365119L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDireccionImportacionImpl.class);

	@Autowired
	DireccionDao direccionDao;

	@Autowired
	ServicioPersonaDireccionImportacion servicioPersonaDireccion;

	@Autowired
	ServicioMunicipioImportacion servicioMunicipio;

	@Autowired
	ServicioDgtMunicipioImportacion servicioDgtMunicipio;

	@Autowired
	ServicioLocalidadDgtImportacion servicioLocalidadDgt;

	@Autowired
	ServicioProvinciaImportacion servicioProvincia;

	@Autowired
	ServicioTipoViaImportacion servicioTipoVia;

	@Autowired
	ServicioPuebloImportacion servicioPueblo;

	@Autowired
	Conversion conversor;

	@Autowired
	Utiles utiles;

	@Override
	@Transactional
	public ResultadoBean guardarActualizarDirPersona(DireccionVO direccion, String nif, String numColegiado, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			PersonaDireccionVO personaDireccionVO = servicioPersonaDireccion.buscarDireccionExistente(direccion, numColegiado, nif, tipoTramite);
			if (personaDireccionVO != null) {
				DireccionVO direccionNueva = new DireccionVO();
				boolean actualiza = false;
				direccionNueva = personaDireccionVO.getDireccion();
				String conversion = getConversion(tipoTramite);
				if (conversion != null) {
					DireccionVO direccionAntigua = (DireccionVO) direccionNueva.clone();
					conversor.transform(direccion, direccionNueva, conversion);
					actualiza = esModificada(direccionNueva, direccionAntigua);
				} else {
					actualiza = esModificada(direccion, direccionNueva);
				}
				if (actualiza) {
					direccionDao.guardarOActualizar(direccionNueva);
				}
				resultado.setIdDirPersona(direccionNueva.getIdDireccion());
			} else {
				ResultadoBean resulPerDir = servicioPersonaDireccion.buscarPersonaDireccionVO(numColegiado, nif);
				if (resulPerDir != null && !resulPerDir.getError()) {
					personaDireccionVO = resulPerDir.getPersonaDir();
				}
				if (personaDireccionVO != null) {
					if (StringUtils.isNotBlank(direccion.getAsignarPrincipal()) && !DIRECCION_NO_ACTIVA.contains(direccion.getAsignarPrincipal())) {
						personaDireccionVO.setFechaFin(new Date());
						servicioPersonaDireccion.guardarActualizar(personaDireccionVO);
					}
				} else {
					direccion.setAsignarPrincipal("SI");
				}

				direccionDao.guardar(direccion);

				personaDireccionVO = new PersonaDireccionVO();
				personaDireccionVO.setFechaInicio(new Date());
				if (StringUtils.isBlank(direccion.getAsignarPrincipal()) || DIRECCION_NO_ACTIVA.contains(direccion.getAsignarPrincipal())) {
					personaDireccionVO.setFechaFin(new Date());
				}
				PersonaDireccionPK personaDireccionPK = new PersonaDireccionPK(numColegiado, nif, direccion.getIdDireccion());
				personaDireccionVO.setId(personaDireccionPK);
				servicioPersonaDireccion.guardarActualizar(personaDireccionVO);

				resultado.setEsNuevaDir(Boolean.TRUE);
				resultado.setIdDirPersona(direccion.getIdDireccion());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la direccion, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la direccion.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoValidacionImporBean buscarPersonaDireccion(String numColegiado, String nif) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			PersonaDireccionVO personaDirVO = servicioPersonaDireccion.buscarPersonaDireccionActivaVO(numColegiado, nif);
			if (personaDirVO != null) {
				resultado.setDireccion(personaDirVO.getDireccion());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la direccion de la persona con NIF: " + nif + " del colegiado: " + numColegiado + "error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la direccion de la persona con NIF: " + nif);
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean codigoProvinciaCorrecta(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = servicioProvincia.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar la provincia, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean codigoMunicipioCorrecto(String idProvincia, String idMunicipio) {
		try {
			MunicipioVO municipioVO = servicioMunicipio.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el municipio, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Transactional
	@Override
	public Boolean codigoMunicipioCorrectoDGT(String idProvincia, String municipio) {
		try {
			DgtMunicipioVO dgtMunicipioVO = servicioDgtMunicipio.getDgtMunicipio(idProvincia, municipio);
			if (dgtMunicipioVO != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public Boolean esTipoViaCorrecta(String idTipoVia) {
		try {
			TipoViaVO tipoViaVO = servicioTipoVia.getTipoVia(idTipoVia);
			if (tipoViaVO != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tipo de via, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional(readOnly = true)
	public TipoViaVO getTipoViaPorIdDgt(String idTipoViaDgt) {
		try {
			TipoViaVO tipoViaVO = servicioTipoVia.getTipoViaDgt(idTipoViaDgt);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tipo de via, error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public TipoViaVO getTipoViaPorNombre(String nombre) {
		try {
			TipoViaVO tipoViaVO = servicioTipoVia.getIdTipoViaPorDesc(nombre);
			if (tipoViaVO != null) {
				return tipoViaVO;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tipo de via, error: ", e);
		}
		return null;
	}

	@Override
	public Boolean esPuebloCorrecto(String idProvincia, String idMunicipio, String pueblo) {
		try {
			PuebloVO puebloVO = servicioPueblo.getPueblo(idProvincia, idMunicipio, pueblo);
			if (puebloVO != null) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el pueblo, error: ", e);
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean esPuebloCorrectoDgt(String codigoPostal, String pueblo) {
		try {
			List<LocalidadDgtVO> localidades = servicioLocalidadDgt.getLocalidadesPorCodigoPostal(codigoPostal, pueblo);
			if (localidades != null && localidades.size() == 1) {
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la localidad", e);
		}
		return Boolean.FALSE;
	}

	@Override
	@Transactional
	public ResultadoBean guardarActualizarDireccionVeh(DireccionVO direccionVO, String matricula, String tipoTramite) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		DireccionVO direccionNueva = new DireccionVO();
		boolean actualiza = true;
		try { // Comprobamos datos obligatorios
			if (direccionVO.getIdDireccion() != null) {
				direccionNueva = direccionDao.getDireccion(direccionVO.getIdDireccion());
				if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
					direccionVO.setPueblo(direccionNueva.getPueblo());
					direccionVO.setCodPostal(direccionNueva.getCodPostal());
				} else {
					direccionVO.setPuebloCorreos(direccionNueva.getPuebloCorreos());
					direccionVO.setCodPostalCorreos(direccionNueva.getCodPostalCorreos());
				}
				actualiza = esModificada(direccionVO, direccionNueva);
				if (actualiza) {
					direccionDao.guardarOActualizar(direccionNueva);
				}
				resultado.setIdDirVehiculo(direccionNueva.getIdDireccion());
			} else {
				direccionDao.guardar(direccionVO);
				resultado.setIdDirVehiculo(direccionVO.getIdDireccion());
			}
		} catch (Exception e) {
			log.error("Error al guardar la direccion del vehiculo, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar la direccion del vehiculo.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public boolean esModificada(DireccionVO direccionVO, DireccionVO direccionBBDD) {
		boolean actualiza = false;
		direccionVO.setIdDireccion(direccionBBDD.getIdDireccion());
		try {
			if (!utiles.sonIgualesCombo(direccionVO.getIdTipoVia(), direccionBBDD.getIdTipoVia())) {
				actualiza = true;
				direccionBBDD.setIdTipoVia(utiles.convertirCombo(direccionVO.getIdTipoVia()));
			}
			if (!utiles.sonIgualesString(direccionVO.getNumero(), direccionBBDD.getNumero())) {
				actualiza = true;
				direccionBBDD.setNumero(direccionVO.getNumero());
			}
			if (!utiles.sonIgualesString(direccionVO.getPueblo(), direccionBBDD.getPueblo())) {
				actualiza = true;
				direccionBBDD.setPueblo(direccionVO.getPueblo());
			}
			if (!utiles.sonIgualesString(direccionVO.getPuebloCorreos(), direccionBBDD.getPuebloCorreos())) {
				actualiza = true;
				direccionBBDD.setPuebloCorreos(direccionVO.getPuebloCorreos());
			}
			if (!utiles.sonIgualesString(direccionVO.getLetra(), direccionBBDD.getLetra())) {
				actualiza = true;
				direccionBBDD.setLetra(direccionVO.getLetra());
			}
			if (!utiles.sonIgualesString(direccionVO.getIdMunicipio(), direccionBBDD.getIdMunicipio())) {
				actualiza = true;
				direccionBBDD.setIdMunicipio(direccionVO.getIdMunicipio());
			}
			if (!utiles.sonIgualesString(direccionVO.getIdProvincia(), direccionBBDD.getIdProvincia())) {
				actualiza = true;
				direccionBBDD.setIdProvincia(direccionVO.getIdProvincia());
			}
			if (!utiles.sonIgualesString(direccionVO.getEscalera(), direccionBBDD.getEscalera())) {
				actualiza = true;
				direccionBBDD.setEscalera(direccionVO.getEscalera());
			}
			if (!utiles.sonIgualesString(direccionVO.getBloque(), direccionBBDD.getBloque())) {
				actualiza = true;
				direccionBBDD.setBloque(direccionVO.getBloque());
			}
			if (!utiles.sonIgualesString(direccionVO.getPlanta(), direccionBBDD.getPlanta())) {
				actualiza = true;
				direccionBBDD.setPlanta(direccionVO.getPlanta());
			}
			if (!utiles.sonIgualesString(direccionVO.getPuerta(), direccionBBDD.getPuerta())) {
				actualiza = true;
				direccionBBDD.setPuerta(direccionVO.getPuerta());
			}
			if (!utiles.sonIgualesObjetos(direccionVO.getKm(), direccionBBDD.getKm())) {
				actualiza = true;
				direccionBBDD.setKm(direccionVO.getKm());
			}
			if (!utiles.sonIgualesObjetos(direccionVO.getHm(), direccionBBDD.getHm())) {
				actualiza = true;
				direccionBBDD.setHm(direccionVO.getHm());
			}
			if (!utiles.sonIgualesString(direccionVO.getCodPostal(), direccionBBDD.getCodPostal())) {
				actualiza = true;
				direccionBBDD.setCodPostal(direccionVO.getCodPostal());
			}
			if (!utiles.sonIgualesString(direccionVO.getCodPostalCorreos(), direccionBBDD.getCodPostalCorreos())) {
				actualiza = true;
				direccionBBDD.setCodPostalCorreos(direccionVO.getCodPostalCorreos());
			}
			if (!utiles.sonIgualesString(direccionVO.getViaSineditar(), direccionBBDD.getViaSineditar())) {
				actualiza = true;
				direccionBBDD.setViaSineditar(direccionVO.getViaSineditar());
			}
			if (!utiles.sonIgualesString(direccionVO.getNombreVia(), direccionBBDD.getNombreVia())) {
				actualiza = true;
				direccionBBDD.setNombreVia(direccionVO.getNombreVia());
			}
			if (!utiles.sonIgualesString(direccionVO.getPais(), direccionBBDD.getPais())) {
				actualiza = true;
				direccionBBDD.setPais(direccionVO.getPais());
			}
			if (!utiles.sonIgualesString(direccionVO.getRegionExtranjera(), direccionBBDD.getRegionExtranjera())) {
				actualiza = true;
				direccionBBDD.setRegionExtranjera(direccionVO.getRegionExtranjera());
			}
		} catch (Exception e) {
			log.error("Error al comparar la direccion de pantalla con la direccion de la bbdd", e);
		} finally {
			direccionDao.evict(direccionVO);
		}
		return actualiza;
	}

	@Override
	@Transactional
	public String obtenerNombreMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre municipio", e);
		}
		return "";
	}

	@Transactional
	@Override
	public MunicipioVO getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = servicioMunicipio.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Transactional
	@Override
	public MunicipioVO getMunicipioPorNombre(String nombreMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = servicioMunicipio.getMunicipioPorNombre(nombreMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Transactional
	@Override
	public DgtMunicipioVO getMunicipioDGT(BigDecimal idDgtMunicipio, String idProvincia) {
		try {
			DgtMunicipioVO dgtMunicipioVO = servicioDgtMunicipio.getDgtMunicipioPorIdDgt(idProvincia, idDgtMunicipio);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio DGT", e);
		}
		return null;
	}

	@Transactional
	@Override
	public DgtMunicipioVO getMunicipioDGTPorNombre(String municipio, String idProvincia) {
		try {
			DgtMunicipioVO dgtMunicipioVO = servicioDgtMunicipio.getDgtMunicipio(idProvincia, municipio);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio DGT", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String obtenerNombreProvincia(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = servicioProvincia.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return provinciaVO.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la provincia", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerCodigoPostal(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return municipioVO.getCodigoPostal();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el codigo postal", e);
		}
		return "";
	}

	@Override
	@Transactional(readOnly = true)
	public String obtenerCodigoPostalDGT(BigDecimal idDgtMunicipio, String idProvincia) {
		try {
			DgtMunicipioVO dgtMunicipioVO = getMunicipioDGT(idDgtMunicipio, idProvincia);
			if (dgtMunicipioVO != null) {
				return dgtMunicipioVO.getCodigoPostal();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el codigo postal", e);
		}
		return "";
	}

	private String getConversion(String tipoTramite) {
		if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			return CONVERSION_DIRECCION_CORREOS;
		} else {
			return CONVERSION_DIRECCION_INE;
		}
	}
}
