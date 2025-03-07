package org.gestoresmadrid.oegamComun.direccion.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioSitesVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.direccion.model.vo.TipoViaVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaPK;
import org.gestoresmadrid.core.personas.model.vo.EvolucionPersonaVO;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaDireccionVO;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunDireccion;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioComunPersonaDireccion;
import org.gestoresmadrid.oegamComun.direccion.service.ServicioPersistenciaDireccion;
import org.gestoresmadrid.oegamComun.direccion.view.bean.ResultadoDireccionBean;
import org.gestoresmadrid.oegamComun.persona.service.ServicioComunPersona;
import org.gestoresmadrid.oegamComun.persona.view.bean.ResultadoPersonaBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioComunDireccionImpl implements ServicioComunDireccion {

	private static final long serialVersionUID = 3984731185565069954L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioComunDireccionImpl.class);

	@Autowired
	ServicioComunPersonaDireccion servicioComunPersonaDireccion;
	
	@Autowired
	ServicioPersistenciaDireccion servicioPersistenciaDireccion;

	@Autowired
	Conversor conversor;

	@Autowired
	Utiles utiles;
	
	@Autowired
	ServicioValDireccion servicioValDireccion;
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoPersonaBean tratarPersonaDireccion(DireccionVO direccion, String nif, String numColegiado, String tipoTramite, BigDecimal numExpediente,Long idUsuario, String conversion) {
		ResultadoPersonaBean resultado = new ResultadoPersonaBean(Boolean.FALSE);
		DireccionVO direccionNueva = new DireccionVO();
		try {
			ResultadoDireccionBean resultCompDir = servicioValDireccion.comprobarDireccionCorrecta(direccion); 
			if(!resultCompDir.getError()){
				PersonaDireccionVO personaDireccionVO = servicioComunPersonaDireccion.buscarDireccionExistente(direccion, numColegiado, nif, tipoTramite);
				if (personaDireccionVO != null) {
					direccionNueva = personaDireccionVO.getDireccion();
					if (conversion != null) {
						DireccionVO direccionAntigua = (DireccionVO) direccionNueva.clone();
						conversor.transform(direccion, direccionNueva, conversion);
						resultado.setGuardarDir(servicioValDireccion.esModificadaDireccion(direccionNueva, direccionAntigua));
					} else {
						resultado.setGuardarDir(servicioValDireccion.esModificadaDireccion(direccion, direccionNueva));
					}
					if(!resultado.getGuardarDir() && personaDireccionVO.getFechaFin() != null 
							&& ServicioComunPersona.TIPO_TRAMITE_MANTENIMIENTO.equals(tipoTramite)){
						ResultadoPersonaBean resultadpPerDir = servicioComunPersonaDireccion.tratarAsignarPrincipal(numColegiado, nif, direccionNueva.getIdDireccion());
						if(!resultadpPerDir.getError()){
							resultado.setPersonaDirPrincipalAnt(resultadpPerDir.getPersonaDirPrincipalAnt());
							resultado.setPersonaDirPrincipalNue(resultadpPerDir.getPersonaDirPrincipalNue());
						}
					}
					resultado.setDireccion(direccionNueva);
				} else {
					ResultadoPersonaBean resulPerDir = servicioComunPersonaDireccion.buscarPersonaDireccionVO(numColegiado, nif);
					if (resulPerDir != null && !resulPerDir.getError()) {
						personaDireccionVO = resulPerDir.getPersonaDireccion();
					}
					if (personaDireccionVO != null) {
						if (StringUtils.isNotBlank(direccion.getAsignarPrincipal()) 
								&& !DIRECCION_NO_ACTIVA.contains(direccion.getAsignarPrincipal())) {
							personaDireccionVO.setFechaFin(new Date());
							resultado.setPersonaDirPrincipalAnt(personaDireccionVO);
						}
					} else {
						direccion.setAsignarPrincipal("SI");
					}
					personaDireccionVO = new PersonaDireccionVO();
					personaDireccionVO.setFechaInicio(new Date());
					if (StringUtils.isBlank(direccion.getAsignarPrincipal()) || DIRECCION_NO_ACTIVA.contains(direccion.getAsignarPrincipal())) {
						personaDireccionVO.setFechaFin(new Date());
					}
					PersonaDireccionPK personaDireccionPK = new PersonaDireccionPK(numColegiado, nif, null);
					personaDireccionVO.setId(personaDireccionPK);
					resultado.setPersonaDirPrincipalNue(personaDireccionVO);
					resultado.setEvolucionPersona(rellenarEvolucionPersona(personaDireccionVO.getId().getNif(), numExpediente, tipoTramite, numColegiado, idUsuario));
					direccion.setIdDireccion(null);
					resultado.setGuardarDir(Boolean.TRUE);
					resultado.setDireccion(direccion);
				}
			} else {
				resultado.setMensaje("La dirección de la persona no es correcta por el siguiente motivo: " + resultCompDir.getMensaje());
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de tratar la direccion de la persona con NIF: " + nif + " error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar la direccion de la persona con NIF: " + nif);
		}
		return resultado;
	}
	
	private EvolucionPersonaVO rellenarEvolucionPersona(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, Long idUsuario) {
		EvolucionPersonaVO evolucion = new EvolucionPersonaVO();
		EvolucionPersonaPK id = new EvolucionPersonaPK();
		id.setNif(nif);
		id.setNumColegiado(numColegiado);
		evolucion.setId(id);
		evolucion.setNumExpediente(numExpediente);
		evolucion.setTipoTramite(tipoTramite);
		evolucion.setOtros("Nueva Dirección");
		evolucion.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario);
		evolucion.setUsuario(usuario);
		return evolucion;
	}

	@Override
	public ResultadoDireccionBean tratarDirVehiculo(DireccionVO direccion, String matricula, String conversion) {
		ResultadoDireccionBean resultado = new ResultadoDireccionBean(Boolean.FALSE);
		DireccionVO direccionNueva = new DireccionVO();
		try { // Comprobamos datos obligatorios
			resultado = servicioValDireccion.comprobarDireccionCorrecta(direccion); 
			if (!resultado.getError()) {
				if (direccion.getIdDireccion() != null) {
					direccionNueva = servicioPersistenciaDireccion.getDireccion(direccion.getIdDireccion());
					if (conversion != null) {
						DireccionVO direccionAntigua = (DireccionVO) direccionNueva.clone();
						conversor.transform(direccion, direccionNueva, conversion);
						resultado.setGuardarDir(servicioValDireccion.esModificadaDireccion(direccionNueva, direccionAntigua));
					} else {
						resultado.setGuardarDir(esModificada(direccion, direccionNueva));
					}
					resultado.setDireccion(direccionNueva);
				} else {
					resultado.setGuardarDir(Boolean.TRUE);
					resultado.setDireccion(direccion);
				}
			} else {
				resultado.setMensaje("La dirección del vehículo no es correcta por el siguiente motivo: " + resultado.getMensaje());
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la direccion del vehiculo, error: ", e);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la direccion del vehiculo");
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}
	
	@Override
	public DireccionVO guardarActualizarDireccion(DireccionVO direccion) {
		return servicioPersistenciaDireccion.guardarOActualizar(direccion);
	}
	
	@Override
	@Transactional
	public ResultadoPersonaBean guardarActualizarPersona(DireccionVO direccionVO, String nif, String numColegiado, String tipoTramite, String conversion) {
		ResultadoPersonaBean result = new ResultadoPersonaBean(Boolean.FALSE);
		DireccionVO direccionNueva = new DireccionVO();
		PersonaDireccionVO personaDireccionVO = new PersonaDireccionVO();
		boolean nuevaDireccion = false;
		try {
			ResultadoDireccionBean resultValDir = servicioValDireccion.comprobarDireccionCorrecta(direccionVO);
			if (!resultValDir.getError()) {
				personaDireccionVO = servicioComunPersonaDireccion.buscarDireccionExistente(direccionVO, numColegiado, nif, tipoTramite);
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
						servicioPersistenciaDireccion.guardarOActualizar(direccionNueva);
					}
					result.setDireccion(direccionNueva);
				} else {
					ResultadoPersonaBean resulPerDir = servicioComunPersonaDireccion.buscarPersonaDireccionVO(numColegiado, nif);
					if (resulPerDir != null && !resulPerDir.getError()) {
						personaDireccionVO = resulPerDir.getPersonaDireccion();
					}
					if (personaDireccionVO != null) {
						if (!DIRECCION_NO_ACTIVA.contains(direccionVO.getAsignarPrincipal())) {
							personaDireccionVO.setFechaFin(new Date());
							servicioComunPersonaDireccion.guardarActualizar(personaDireccionVO);
						}
					} else {
						direccionVO.setAsignarPrincipal("SI");
					}

					Long idDireccion = (Long) servicioPersistenciaDireccion.guardar(direccionVO);

					personaDireccionVO = new PersonaDireccionVO();
					personaDireccionVO.setFechaInicio(new Date());
					if (DIRECCION_NO_ACTIVA.contains(direccionVO.getAsignarPrincipal())) {
						personaDireccionVO.setFechaFin(new Date());
					}
					PersonaDireccionPK personaDireccionPK = new PersonaDireccionPK(numColegiado, nif, idDireccion);
					personaDireccionVO.setId(personaDireccionPK);
					servicioComunPersonaDireccion.guardarActualizar(personaDireccionVO);

					nuevaDireccion = true;
					result.setDireccion(direccionVO);
				}
			} else {
				result.setMensaje("Error al guardar la dirección de la persona. Dirección no correcta: " +resultValDir.getMensaje());
				result.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Error al guardar/Actualizar la direccion de un interviniente", e);
			result.setMensaje("Error al guardar la dirección de la persona");
			result.setError(true);
		}
		result.setNuevaDireccion(nuevaDireccion);
		return result;
	}

	private Boolean esModificada(DireccionVO direccionVO, DireccionVO direccionBBDD) {
		Boolean actualiza = false;
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
			servicioPersistenciaDireccion.evict(direccionVO);
		}
		return actualiza;
	}

	@Override
	public String obtenerNombreTipoVia(String idTipoVia) {
		try {
			TipoViaVO tipoVia = servicioPersistenciaDireccion.getTipoVia(idTipoVia);
			if (tipoVia != null) {
				return tipoVia.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre del tipo via", e);
		}
		return "";
	}

	@Override
	public String obtenerNombreProvincia(String idProvincia) {
		try {
			ProvinciaVO provincia = servicioPersistenciaDireccion.getProvincia(idProvincia);
			if (provincia != null) {
				return provincia.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la provincia", e);
		}
		return "";
	}

	@Override
	public String obtenerNombreProvinciaSitex(String idProvincia) {
		try {
			ProvinciaVO provincia = servicioPersistenciaDireccion.getProvincia(idProvincia);
			if (provincia != null) {
				return provincia.getDescripcionSitex();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre de la provincia Sitex", e);
		}
		return "";
	}

	@Override
	public String obtenerNombreMunicipio(String idMunicipio, String idProvincia) {
		try {
			MunicipioVO municipio = servicioPersistenciaDireccion.getMunicipio(idMunicipio, idProvincia);
			if (municipio != null) {
				return municipio.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre municipio", e);
		}
		return "";
	}

	@Override
	public String obtenerNombreMunicipioSitex(String idMunicipio, String idProvincia) {
		try {
			MunicipioSitesVO municipio = servicioPersistenciaDireccion.getMunicipioSites(idMunicipio, idProvincia);
			if (municipio != null) {
				return municipio.getNombre();
			}
		} catch (Exception e) {
			log.error("Error al recuperar el nombre municipio Sitex", e);
		}
		return "";
	}

}
