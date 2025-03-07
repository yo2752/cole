package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.registradores.model.dao.InmuebleDao;
import org.gestoresmadrid.core.registradores.model.vo.InmuebleVO;
import org.gestoresmadrid.oegam2comun.bien.model.service.ServicioBien;
import org.gestoresmadrid.oegam2comun.bien.view.dto.BienDto;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccionCam;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioInmueble;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.InmuebleDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioInmuebleImpl implements ServicioInmueble {

	private static final long serialVersionUID = 2191898157618183764L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioInmuebleImpl.class);

	@Autowired
	private InmuebleDao inmuebleDao;

	@Autowired
	private ServicioDireccionCam servicioDireccionCam;

	@Autowired
	private ServicioBien servicioBien;

	@Autowired
	private Conversor conversor;

	@Override
	@Transactional
	public InmuebleVO getInmueble(Long idInmueble) {
		try {
			if (idInmueble != null) {
				return inmuebleDao.getInmueble(idInmueble);
			}
		} catch (Exception e) {
			log.error("Error al recuperar el inmueble: " + idInmueble, e);
		}
		return null;
	}

	@Override
	@Transactional
	public InmuebleDto getInmuebleDto(Long idInmueble) {
		InmuebleDto inmuebleDto = null;
		try {
			InmuebleVO vo = inmuebleDao.getInmueble(idInmueble);
			inmuebleDto = conversor.transform(vo, InmuebleDto.class);
			if (inmuebleDto.getBien().getDireccion() != null && inmuebleDto.getBien().getDireccion().getIdProvincia() != null && !inmuebleDto.getBien().getDireccion().getIdProvincia().isEmpty()) {
				inmuebleDto.getBien().getDireccion().setNombreProvincia(servicioDireccionCam.obtenerNombreProvincia(inmuebleDto.getBien().getDireccion().getIdProvincia()));
				if (inmuebleDto.getBien().getDireccion().getIdMunicipio() != null && !inmuebleDto.getBien().getDireccion().getIdMunicipio().isEmpty()) {
					inmuebleDto.getBien().getDireccion().setNombreMunicipio(servicioDireccionCam.obtenerNombreMunicipio(inmuebleDto.getBien().getDireccion().getIdProvincia(), inmuebleDto.getBien()
							.getDireccion().getIdMunicipio()));
				}
			}

		} catch (Exception e) {
			log.error("Error al recuperar el inmueble: " + idInmueble, e);
		}
		return inmuebleDto;
	}

	@Override
	@Transactional
	public List<InmuebleDto> getInmuebles(Long idBien) {
		List<InmuebleDto> result = new ArrayList<InmuebleDto>();
		try {
			if (idBien != null) {
				List<InmuebleVO> lista = inmuebleDao.getInmuebles(idBien);
				if (lista != null && !lista.isEmpty()) {
					for (InmuebleVO inmuebleVO : lista) {
						InmuebleDto inmuebleDto = conversor.transform(inmuebleVO, InmuebleDto.class);
						if (inmuebleDto.getBien() != null && inmuebleDto.getBien().getDireccion() != null && inmuebleDto.getBien().getDireccion().getIdProvincia() != null && !inmuebleDto.getBien()
								.getDireccion().getIdProvincia().isEmpty()) {
							inmuebleDto.getBien().getDireccion().setNombreProvincia(servicioDireccionCam.obtenerNombreProvincia(inmuebleDto.getBien().getDireccion().getIdProvincia()));
							if (inmuebleDto.getBien().getDireccion().getIdMunicipio() != null && !inmuebleDto.getBien().getDireccion().getIdMunicipio().isEmpty()) {
								inmuebleDto.getBien().getDireccion().setNombreMunicipio(servicioDireccionCam.obtenerNombreMunicipio(inmuebleDto.getBien().getDireccion().getIdProvincia(), inmuebleDto
										.getBien().getDireccion().getIdMunicipio()));
							}
						}
						result.add(inmuebleDto);
					}
				}
			}
			return result;
		} catch (Exception e) {
			log.error("Error al recuperar los inmuebles: " + idBien, e);
		}
		return result;
	}

	@Override
	@Transactional
	public List<InmuebleDto> getInmuebles(BigDecimal idTramiteRegistro) {
		List<InmuebleDto> result = new ArrayList<InmuebleDto>();
		try {
			if (idTramiteRegistro != null) {
				List<InmuebleVO> lista = inmuebleDao.getInmuebles(idTramiteRegistro);
				if (lista != null && !lista.isEmpty()) {
					for (InmuebleVO inmuebleVO : lista) {
						InmuebleDto inmuebleDto = conversor.transform(inmuebleVO, InmuebleDto.class);
						if (inmuebleDto.getBien() != null && inmuebleDto.getBien().getDireccion() != null && inmuebleDto.getBien().getDireccion().getIdProvincia() != null && !inmuebleDto.getBien()
								.getDireccion().getIdProvincia().isEmpty()) {
							inmuebleDto.getBien().getDireccion().setNombreProvincia(servicioDireccionCam.obtenerNombreProvincia(inmuebleDto.getBien().getDireccion().getIdProvincia()));
							if (inmuebleDto.getBien().getDireccion().getIdMunicipio() != null && !inmuebleDto.getBien().getDireccion().getIdMunicipio().isEmpty()) {
								inmuebleDto.getBien().getDireccion().setNombreMunicipio(servicioDireccionCam.obtenerNombreMunicipio(inmuebleDto.getBien().getDireccion().getIdProvincia(), inmuebleDto
										.getBien().getDireccion().getIdMunicipio()));
							}
						}
						result.add(inmuebleDto);
					}
				}
			}
			return result;
		} catch (Exception e) {
			log.error("Error al recuperar los inmuebles: " + idTramiteRegistro, e);
		}
		return result;
	}

	@Override
	@Transactional
	public ResultBean guardarInmueble(InmuebleDto inmueble, BigDecimal idTramiteRegistro) {
		ResultBean result = new ResultBean();
		boolean existeIdufir = Boolean.FALSE;
		try {

			result = validarInmueble(inmueble);
			if (result.getError()) {
				return result;
			}

			// Buscamos por IDUFIR a ver si el bien ya está dado de alta. En ese caso recuperamos el bien
			if (inmueble.getBien().getIdBien() == null && inmueble.getBien() != null && inmueble.getBien().getIdufir() != null) {
				result = servicioBien.getBienPorIdufir(inmueble.getBien().getIdufir());
				if (result != null && !result.getError() && result.getAttachment("bienDto") != null) {
					inmueble.setBien((BienDto) result.getAttachment("bienDto"));
					inmueble.setIdBien(inmueble.getBien().getIdBien());
					existeIdufir = Boolean.TRUE;
				}
			}

			if (!existeIdufir) {
				result = servicioBien.guardar(inmueble.getBien());
				if (result != null && result.getError()) {
					result.addMensajeALista("Error al guardar el bien.");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return result;
				}

				if (result != null && null != result.getAttachment("idBien")) {
					inmueble.setIdBien((Long) result.getAttachment("idBien"));
				}
			}

			inmueble.setIdTramiteRegistro(idTramiteRegistro);
			inmueble.getBien().setIdBien(inmueble.getBien().getIdBien());
			InmuebleVO inmuebleVO = conversor.transform(inmueble, InmuebleVO.class);
			inmuebleDao.guardarOActualizar(inmuebleVO);
		} catch (Exception e) {
			result.setError(Boolean.TRUE);
			result.setMensaje(e.getMessage());
			log.error("Error al guardar el inmueble: " + idTramiteRegistro, e.getMessage());
		}
		return result;
	}

	@Override
	@Transactional
	public void eliminarInmueble(Long idInmueble) {
		InmuebleVO inmueble = getInmueble(idInmueble);
		if (inmueble != null) {
			inmuebleDao.borrar(inmueble);
		}
	}

	@Override
	@Transactional
	public void eliminarInmueblesPorExpediente(BigDecimal idTramiteRegistro) {
		if (idTramiteRegistro != null) {
			List<InmuebleVO> lista = inmuebleDao.getInmuebles(idTramiteRegistro);
			if (lista != null && !lista.isEmpty()) {
				for (InmuebleVO inmuebleVO : lista) {
					eliminarInmueble(inmuebleVO.getIdInmueble());
				}
			}
		}
	}

	private ResultBean validarInmueble(InmuebleDto inmueble) {
		ResultBean result = new ResultBean();

		if (inmueble.getBien() != null) {

			if (StringUtils.isBlank(inmueble.getBien().getDireccion().getIdProvincia()) || "-1".equals(inmueble.getBien().getDireccion().getIdProvincia())) {
				result.addMensajeALista("La provincia del inmueble es obligatoria.");
				result.setError(Boolean.TRUE);
			}

			if (StringUtils.isBlank(inmueble.getBien().getDireccion().getIdMunicipio()) || "-1".equals(inmueble.getBien().getDireccion().getIdMunicipio())) {
				result.addMensajeALista("El municipio del inmueble es obligatorio.");
				result.setError(Boolean.TRUE);
			}

			if (null == inmueble.getBien().getSeccion()) {
				result.addMensajeALista("La sección registral del inmueble es obligatoria.");
				result.setError(Boolean.TRUE);
			}

			if (null == inmueble.getBien().getNumeroFinca()) {
				result.addMensajeALista("El número de finca del inmueble es obligatorio.");
				result.setError(Boolean.TRUE);
			}

			if (null == inmueble.getBien().getIdufir()) {
				result.addMensajeALista("Debe rellenar el campo 'IDUFIR/CRU' del inmueble con un número de 14 dígitos.");
				result.setError(Boolean.TRUE);
			} else if (14 != inmueble.getBien().getIdufir().toString().length()) {
				result.addMensajeALista("El 'IDUFIR/CRU' del inmueble no tiene el formato correcto.");
				result.setError(Boolean.TRUE);
			}
		}

		return result;
	}
}
