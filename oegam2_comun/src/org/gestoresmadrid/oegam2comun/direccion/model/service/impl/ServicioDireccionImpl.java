package org.gestoresmadrid.oegam2comun.direccion.model.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.direccion.model.dao.DireccionDao;
import org.gestoresmadrid.core.direccion.model.vo.DgtMunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.LocalidadDgtVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.PuebloVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.direccion.model.vo.ViaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDgtMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioLocalidadDgt;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioProvincia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioPueblo;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioTipoVia;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioVia;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtMunicipioDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.LocalidadDgtDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.trafico.view.beans.ResultadoCtitBean;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.gestoresmadrid.oegamComun.direccion.view.dto.ProvinciaDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioDireccionImpl implements ServicioDireccion {

	private static final long serialVersionUID = 2966675591576079906L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioDireccionImpl.class);

	@Autowired
	private DireccionDao direccionDao;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioMunicipio servicioMunicipio;

	@Autowired
	private ServicioProvincia servicioProvincia;

	@Autowired
	private ServicioTipoVia servicioTipoVia;

	@Autowired
	private ServicioDgtMunicipio servicioDgtMunicipio;

	@Autowired
	private ServicioPueblo servicioPueblo;

	@Autowired
	private ServicioVia servicioVia;

	@Autowired
	private ServicioLocalidadDgt servicioLocalidadDgt;

	@Autowired
	private Conversor conversor;

	@Autowired
	Utiles utiles;

	/**
	 * Devuelve un VO. Solo será utilizado por los servicios
	 */
	@Transactional
	@Override
	public DireccionDto getDireccionDto(Long idDireccion) {
		try {
			DireccionVO direccionVO = getDireccionVO(idDireccion);
			if (direccionVO != null) {
				return conversor.transform(direccionVO, DireccionDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la direccion", e);
		}
		return null;
	}

	/**
	 * Devuelve un VO. Solo será utilizado por los servicios
	 */
	@Transactional
	@Override
	public DireccionVO getDireccionVO(Long idDireccion) {
		try {
			DireccionVO direccionVO = direccionDao.getDireccion(idDireccion);
			if (direccionVO != null) {
				return direccionVO;
			}
		} catch (Exception e) {
			log.error("Error al recuperar la direccion", e);
		}
		return null;
	}

	@Transactional
	@Override
	public ResultBean guardar(DireccionVO direccion) {
		ResultBean result = new ResultBean();
		try {
			Long idDireccion = (Long) direccionDao.guardar(direccion);
			direccion.setIdDireccion(idDireccion);
			result.addAttachment(DIRECCION, direccion);
		} catch (Exception e) {
			log.error("Error al guardar la direccion", e);
		}
		return result;
	}

	@Transactional
	@Override
	public ResultBean guardarActualizar(DireccionDto direccionDto) {
		ResultBean result = new ResultBean();
		try {
			DireccionVO direccion = conversor.transform(direccionDto, DireccionVO.class);
			direccionDao.guardarOActualizar(direccion);

			List<DireccionVO> lDireccion = direccionDao.buscar(direccion);

			if (lDireccion != null && !lDireccion.isEmpty()) {
				direccionDto = conversor.transform(lDireccion.get(0), DireccionDto.class);
			}
			result.addAttachment(DIRECCION, direccionDto);
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion", e);
		}
		return result;
	}

	/**
	 * Devuelve un VO. Solo será utilizado por los servicios. Guarda la dirección de un vehículo
	 */
	@Override
	@Transactional
	public ResultBean guardarActualizarVehiculo(DireccionVO direccionVO, String matricula, String conversion) {
		ResultBean result = new ResultBean();
		DireccionVO direccionNueva = new DireccionVO();
		boolean actualiza = true;
		try { // Comprobamos datos obligatorios
			if (comprobarDireccionCorrecta(direccionVO)) {
				if (direccionVO.getIdDireccion() != null) {
					direccionNueva = direccionDao.getDireccion(direccionVO.getIdDireccion());
					if (conversion != null) {
						DireccionVO direccionAntigua = (DireccionVO) direccionNueva.clone();
						conversor.transform(direccionVO, direccionNueva, conversion);
						actualiza = esModificada(direccionNueva, direccionAntigua);
					} else {
						actualiza = esModificada(direccionVO, direccionNueva);
					}

					if (actualiza) {
						direccionDao.guardarOActualizar(direccionNueva);
					}
					result.addAttachment(DIRECCION, direccionNueva);
				} else {
					direccionDao.guardar(direccionVO);
					result.addAttachment(DIRECCION, direccionVO);
				}
			} else {
				result.setMensaje("La dirección del vehículo no es correcta");
				result.setError(true);
			}
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion de un vehículo", e);
		}
		return result;
	}

	/**
	 * Devuelve un VO. Solo será utilizado por los servicios. Guarda la dirección de un interviniente
	 */
	@Override
	@Transactional
	public ResultBean guardarActualizarPersona(DireccionVO direccionVO, String nif, String numColegiado, String tipoTramite, String conversion) {
		ResultBean result = new ResultBean();
		DireccionVO direccionNueva = new DireccionVO();
		PersonaDireccionVO personaDireccionVO = new PersonaDireccionVO();
		boolean nuevaDireccion = false;
		try {
			// Comprobamos datos obligatorios
			if (comprobarDireccionCorrecta(direccionVO)) {
				personaDireccionVO = servicioPersonaDireccion.buscarDireccionExistente(direccionVO, numColegiado, nif, tipoTramite);
				if (personaDireccionVO != null) {
					boolean actualiza = false;
					direccionNueva = personaDireccionVO.getDireccion();
					if (conversion != null) {
						DireccionVO direccionAntigua = (DireccionVO) direccionNueva.clone();
						conversor.transform(direccionVO, direccionNueva, conversion);
						actualiza = esModificada(direccionNueva, direccionAntigua);
					} else {
						actualiza = esModificada(direccionVO, direccionNueva);
					}
					if (actualiza) {
						direccionDao.guardarOActualizar(direccionNueva);
					} else {
						if (personaDireccionVO.getFechaFin() != null && ServicioPersona.TIPO_TRAMITE_MANTENIMIENTO.equals(tipoTramite)) {
							servicioPersonaDireccion.asignarPrincipal(numColegiado, nif, direccionNueva.getIdDireccion());
						}
					}
					result.addAttachment(DIRECCION, direccionNueva);
				} else {
					ResultBean resulPerDir = servicioPersonaDireccion.buscarPersonaDireccionVO(numColegiado, nif);
					if (resulPerDir != null && !resulPerDir.getError()) {
						personaDireccionVO = (PersonaDireccionVO) resulPerDir.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
					}
					if (personaDireccionVO != null) {
						if (StringUtils.isNotBlank(direccionVO.getAsignarPrincipal()) && !DIRECCION_NO_ACTIVA.contains(direccionVO.getAsignarPrincipal())) {
							personaDireccionVO.setFechaFin(new Date());
							servicioPersonaDireccion.guardarActualizar(personaDireccionVO);
						}
					} else {
						direccionVO.setAsignarPrincipal(ConstantesProcesos.SI);
					}

					Long idDireccion = (Long) direccionDao.guardar(direccionVO);

					personaDireccionVO = new PersonaDireccionVO();
					personaDireccionVO.setFechaInicio(new Date());
					if (StringUtils.isBlank(direccionVO.getAsignarPrincipal()) || DIRECCION_NO_ACTIVA.contains(direccionVO.getAsignarPrincipal())) {
						personaDireccionVO.setFechaFin(new Date());
					}
					PersonaDireccionPK personaDireccionPK = new PersonaDireccionPK(numColegiado, nif, idDireccion);
					personaDireccionVO.setId(personaDireccionPK);
					servicioPersonaDireccion.guardarActualizar(personaDireccionVO);

					nuevaDireccion = true;
					result.addAttachment(DIRECCION, direccionVO);
				}
			} else {
				result.setError(true);
				result.setMensaje("Error al guardar la dirección de la persona. Dirección no correcta");
			}
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion de un interviniente", e);
			result.setMensaje("Error al guardar la dirección de la persona");
			result.setError(true);
		}

		result.addAttachment(NUEVA_DIRECCION, nuevaDireccion);
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarActualizarBien(DireccionVO direccionVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			if (comprobarDireccionCorrectaBien(direccionVO)) {
				direccionDao.guardarOActualizar(direccionVO);
				resultado.addAttachment(ServicioDireccion.DIRECCION, direccionVO);
			} else {
				resultado.setError(true);
				resultado.setMensaje("Error al guardar la dirección del bien. Dirección no correcta.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la direccion del bien, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la dirección del bien.");
		}
		return resultado;
	}

	private boolean comprobarDireccionCorrecta(DireccionVO direccion) {
		if (utiles.convertirCombo(direccion.getIdMunicipio()) == null) {
			return false;
		} else if (utiles.convertirCombo(direccion.getIdProvincia()) == null) {
			return false;
		} else if (utiles.convertirCombo(direccion.getIdTipoVia()) == null) {
			return false;
		} else if (utiles.convertirNulltoString(direccion.getNombreVia()) == null) {
			return false;
		} else if (utiles.convertirNulltoString(direccion.getNumero()) == null) {
			return false;
		}

		String viaSinEditar = getViaSinEditar(direccion.getNombreVia());
		direccion.setViaSineditar(viaSinEditar);

		return true;
	}

	private boolean comprobarDireccionCorrectaBien(DireccionVO direccion) {
		if (utiles.convertirCombo(direccion.getIdMunicipio()) == null) {
			return false;
		} else if (utiles.convertirCombo(direccion.getIdProvincia()) == null) {
			return false;
		}
		if (direccion.getNombreVia() != null && !direccion.getNombreVia().isEmpty()) {
			String viaSinEditar = getViaSinEditar(direccion.getNombreVia());
			direccion.setViaSineditar(viaSinEditar);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
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
			log.error("Error al comparar la direccion de pantalla con la direccion de la BBDD", e);
		} finally {
			direccionDao.evict(direccionVO);
		}
		return actualiza;
	}
	
	@Override
	public ResultadoCtitBean validarDireccion(DireccionVO direccion, String mensaje) {
		ResultadoCtitBean resultado = new ResultadoCtitBean(Boolean.FALSE);
		Boolean errorDir = Boolean.FALSE;
		if (StringUtils.isBlank(direccion.getIdProvincia())) {
			errorDir = Boolean.TRUE;
			if (errorDir) {
				mensaje += ", Provincia";
			} else {
				mensaje += "Provincia";
			}
		}
		if (StringUtils.isBlank(direccion.getIdMunicipio())) {
			errorDir = Boolean.TRUE;
			if (errorDir) {
				mensaje += ", Municipio";
			} else {
				mensaje += "Municipio";
			}
		}
		if (StringUtils.isBlank(direccion.getCodPostal())) {
			errorDir = Boolean.TRUE;
			if (errorDir) {
				mensaje += ", Codigo Postal";
			} else {
				mensaje += "Codigo Postal";
			}
		}
		if (StringUtils.isBlank(direccion.getIdTipoVia())) {
			errorDir = Boolean.TRUE;
			if (errorDir) {
				mensaje += ", Tipo Via";
			} else {
				mensaje += "Tipo Via";
			}
		}
		if (StringUtils.isBlank(direccion.getNombreVia())) {
			errorDir = Boolean.TRUE;
			if (errorDir) {
				mensaje += ", Nombre Via";
			} else {
				mensaje += "Nombre Via";
			}
		}
		if (errorDir) {
			resultado.setMensajeError(mensaje);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private String getViaSinEditar(String nombreVia) {
		nombreVia = nombreVia.toUpperCase();

		nombreVia = nombreVia.replace("(", " ");
		nombreVia = nombreVia.replace(")", " ");
		nombreVia = nombreVia.replace("-", " ");
		nombreVia = nombreVia.replace("/", " ");
		nombreVia = nombreVia.replace("_", " ");
		nombreVia = nombreVia.replace(",", " ");
		nombreVia = nombreVia.replace(" DE ", "");
		nombreVia = nombreVia.replace(" LA ", "");
		nombreVia = nombreVia.replace(" LAS ", "");
		nombreVia = nombreVia.replace(" LOS ", "");
		nombreVia = nombreVia.replace(" EL ", "");
		nombreVia = nombreVia.replace(" DEL ", "");
		nombreVia = nombreVia.replace(" UN ", "");
		nombreVia = nombreVia.replace(" UNOS ", "");
		nombreVia = nombreVia.replace(" ", "");
		nombreVia = nombreVia.replace("ª", "");
		nombreVia = nombreVia.replace("º", "");
		nombreVia = nombreVia.replace("\\", "");
		nombreVia = nombreVia.replace(";", "");
		nombreVia = nombreVia.replace(".", "");
		nombreVia = nombreVia.replace(":", "");
		nombreVia = nombreVia.replace("Á", "A");
		nombreVia = nombreVia.replace("É", "A");
		nombreVia = nombreVia.replace("Í", "A");
		nombreVia = nombreVia.replace("Ó", "A");
		nombreVia = nombreVia.replace("Ú", "A");

		return nombreVia;
	}

	@Transactional
	@Override
	public MunicipioDto getMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipioVO = servicioMunicipio.getMunicipio(idMunicipio, idProvincia);
			if (municipioVO != null) {
				return conversor.transform(municipioVO, MunicipioDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String obtenerNombreMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioDto municipioDto = getMunicipio(idMunicipio, idProvincia);
			if (municipioDto != null) {
				return municipioDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre municipio", e);
		}
		return "";
	}

	@Override
	@Transactional
	public String obtenerCodigoPostal(String idMunicipio, String idProvincia) {
		try {
			MunicipioDto municipioDto = getMunicipio(idMunicipio, idProvincia);
			if (municipioDto != null) {
				return municipioDto.getCodigoPostal();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el codigo postal", e);
		}
		return "";
	}

	@Override
	@Transactional
	public String obtenerCodigoPostalSiUnico(String localidad, String municipio) {
		try {
			List<LocalidadDgtVO> lista = servicioLocalidadDgt.getLocalidades(localidad, municipio);
			if (lista != null) {
				if (lista.size() == 1) {
					lista.get(0).getCodigoPostal();
				} else {
					return "";
				}
			}
			return "";
		} catch (Exception e) {
			log.error("Error al recuperar el codigo postal", e);
		}
		return "";
	}

	@Transactional
	@Override
	public ProvinciaDto getProvincia(String idProvincia) {
		try {
			ProvinciaVO provinciaVO = servicioProvincia.getProvincia(idProvincia);
			if (provinciaVO != null) {
				return conversor.transform(provinciaVO, ProvinciaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la provincia", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String obtenerNombreProvincia(String idProvincia) {
		try {
			ProvinciaDto provinciaDto = getProvincia(idProvincia);
			if (provinciaDto != null) {
				return provinciaDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la provincia", e);
		}
		return "";
	}

	@Transactional
	@Override
	public TipoViaDto getTipoVia(String idTipoVia) {
		try {
			TipoViaVO tipoViaVO = servicioTipoVia.getTipoVia(idTipoVia);
			if (tipoViaVO != null) {
				return conversor.transform(tipoViaVO, TipoViaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo via", e);
		}
		return null;
	}

	@Transactional
	@Override
	public TipoViaDto getTipoViaDgt(String idTipoViaDgt) {
		try {
			TipoViaVO tipoViaVO = servicioTipoVia.getTipoViaDgt(idTipoViaDgt);
			if (tipoViaVO != null) {
				return conversor.transform(tipoViaVO, TipoViaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el tipo via", e);
		}
		return null;
	}

	@Override
	@Transactional
	public String obtenerNombreTipoVia(String idTipoVia) {
		try {
			TipoViaDto tipoViaDto = getTipoVia(idTipoVia);
			if (tipoViaDto != null) {
				return tipoViaDto.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre del tipo via", e);
		}
		return "";
	}

	@Transactional
	@Override
	public DgtMunicipioDto getDgtMunicipio(String idProvincia, String municipio) {
		try {
			DgtMunicipioVO dgtMunicipioVO = servicioDgtMunicipio.getDgtMunicipio(idProvincia, municipio);
			if (dgtMunicipioVO != null) {
				return conversor.transform(dgtMunicipioVO, DgtMunicipioDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el municipio de la DGT", e);
		}
		return null;
	}

	@Transactional
	@Override
	public PuebloDto getPueblo(String idProvincia, String idMunicipio, String pueblo) {
		try {
			PuebloVO puebloVO = servicioPueblo.getPueblo(idProvincia, idMunicipio, pueblo);
			if (puebloVO != null) {
				return conversor.transform(puebloVO, PuebloDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el pueblo", e);
		}
		return null;
	}

	@Transactional
	@Override
	public LocalidadDgtDto getLocalidadDgt(String codigoPostal, String pueblo) {
		try {
			List<LocalidadDgtVO> localidades = servicioLocalidadDgt.getLocalidadesPorCodigoPostal(codigoPostal, pueblo);
			if (localidades != null && localidades.size() == 1) {
				return conversor.transform(localidades.get(0), LocalidadDgtDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la localidad", e);
		}
		return null;
	}

	@Transactional
	@Override
	public ViaDto getVia(String idProvincia, String idMunicipio, String idTipoVia, String via) {
		try {
			ViaVO viaVO = servicioVia.getVia(idProvincia, idMunicipio, idTipoVia, via);
			if (viaVO != null) {
				return conversor.transform(viaVO, ViaDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar la vía", e);
		}
		return null;
	}

	@Override
	@Transactional
	public void eliminar(Long idDireccion) {
		try {
			DireccionVO direccion = getDireccionVO(idDireccion);
			if (direccion != null) {
				direccionDao.borrar(direccion);
			}
		} catch (Exception e) {
			log.error("Error al eliminar una dirección", e);
		}
	}
	
	@Override
	@Transactional
	public ResultBean actualizar(DireccionVO direccionVO) {
		ResultBean result = new ResultBean();
		try {
			direccionDao.actualizar(direccionVO);
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion", e);
		}
		return result;
	}

	public DireccionDao getDireccionDao() {
		return direccionDao;
	}

	public void setDireccionDao(DireccionDao direccionDao) {
		this.direccionDao = direccionDao;
	}

	public ServicioPersonaDireccion getServicioPersonaDireccion() {
		return servicioPersonaDireccion;
	}

	public void setServicioPersonaDireccion(ServicioPersonaDireccion servicioPersonaDireccion) {
		this.servicioPersonaDireccion = servicioPersonaDireccion;
	}

	public ServicioMunicipio getServicioMunicipio() {
		return servicioMunicipio;
	}

	public void setServicioMunicipio(ServicioMunicipio servicioMunicipio) {
		this.servicioMunicipio = servicioMunicipio;
	}

	public ServicioProvincia getServicioProvincia() {
		return servicioProvincia;
	}

	public void setServicioProvincia(ServicioProvincia servicioProvincia) {
		this.servicioProvincia = servicioProvincia;
	}

	public ServicioTipoVia getServicioTipoVia() {
		return servicioTipoVia;
	}

	public void setServicioTipoVia(ServicioTipoVia servicioTipoVia) {
		this.servicioTipoVia = servicioTipoVia;
	}

	public ServicioDgtMunicipio getServicioDgtMunicipio() {
		return servicioDgtMunicipio;
	}

	public void setServicioDgtMunicipio(ServicioDgtMunicipio servicioDgtMunicipio) {
		this.servicioDgtMunicipio = servicioDgtMunicipio;
	}

	public ServicioPueblo getServicioPueblo() {
		return servicioPueblo;
	}

	public void setServicioPueblo(ServicioPueblo servicioPueblo) {
		this.servicioPueblo = servicioPueblo;
	}

	public ServicioVia getServicioVia() {
		return servicioVia;
	}

	public void setServicioVia(ServicioVia servicioVia) {
		this.servicioVia = servicioVia;
	}
}