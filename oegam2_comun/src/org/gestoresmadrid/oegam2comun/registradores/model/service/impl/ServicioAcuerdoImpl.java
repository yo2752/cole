package org.gestoresmadrid.oegam2comun.registradores.model.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.gestoresmadrid.core.registradores.model.dao.AcuerdoDao;
import org.gestoresmadrid.core.registradores.model.enumerados.Aceptacion;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoAcuerdo;
import org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro;
import org.gestoresmadrid.core.registradores.model.vo.AcuerdoVO;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioAcuerdo;
import org.gestoresmadrid.oegam2comun.registradores.model.service.ServicioSociedadCargo;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.AcuerdoDto;
import org.gestoresmadrid.oegam2comun.registradores.view.dto.TramiteRegistroDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import escrituras.beans.ResultBean;

@Service
public class ServicioAcuerdoImpl implements ServicioAcuerdo {

	private static final long serialVersionUID = 3984950450556351151L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioAcuerdoImpl.class);

	@Autowired
	private AcuerdoDao acuerdoDao;

	@Autowired
	private ServicioSociedadCargo servicioSociedadCargo;

	@Autowired
	private Conversor conversor;

	@Transactional
	@Override
	public AcuerdoVO getAcuerdo(Long idAcuerdo) {
		try {
			if (idAcuerdo != null) {
				return acuerdoDao.getAcuerdo(idAcuerdo);
			}
		} catch (Exception e) {
			log.error("Error al recuperar un acuerdo: " + idAcuerdo, e);
		}
		return null;
	}

	@Transactional
	@Override
	public AcuerdoDto getAcuerdoDto(Long idAcuerdo) {
		try {
			if (idAcuerdo != null) {
				AcuerdoVO acuerdo = acuerdoDao.getAcuerdo(idAcuerdo);
				return conversor.transform(acuerdo, AcuerdoDto.class);
			}
		} catch (Exception e) {
			log.error("Error al recuperar un acuerdo: " + idAcuerdo, e);
		}
		return null;
	}

	@Transactional
	@Override
	public List<AcuerdoDto> getAcuerdos(BigDecimal idTramiteRegistro) {
		List<AcuerdoDto> result = null;
		try {
			if (idTramiteRegistro != null) {
				List<AcuerdoVO> list = acuerdoDao.getAcuerdos(idTramiteRegistro);
				if (list != null && !list.isEmpty()) {
					result = conversor.transform(list, AcuerdoDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Error al recuperar los acuerdos: " + idTramiteRegistro, e);
		}
		return result;
	}

	
	@Transactional
	@Override
	public ResultBean guardarAcuerdo(AcuerdoDto acuerdoDto, TramiteRegistroDto tramiteRegistro, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		acuerdoDto.setCifSociedad(tramiteRegistro.getSociedad().getNif());
		acuerdoDto.setNumColegiado(tramiteRegistro.getNumColegiado());
		acuerdoDto.setCodigoCargo(acuerdoDto.getSociedadCargo().getCodigoCargo());
		acuerdoDto.setNifCargo(acuerdoDto.getSociedadCargo().getPersonaCargo().getNif());
		acuerdoDto.setIdReunion(tramiteRegistro.getReunion().getIdReunion());
		acuerdoDto.setIdTramiteRegistro(tramiteRegistro.getIdTramiteRegistro());

		calcularFecha(acuerdoDto, tramiteRegistro);

		if (TipoTramiteRegistro.MODELO_2.getValorEnum().equals(tramiteRegistro.getTipoTramite())) {
			List<AcuerdoVO> lista = acuerdoDao.getAcuerdos(tramiteRegistro.getIdTramiteRegistro());
			if (lista != null && !lista.isEmpty()) {
				result.setError(Boolean.TRUE);
				result.addMensajeALista("Ya existe un nombramiento para este trámite");
				return result;
			}
		}

		AcuerdoVO acuerdoVO = acuerdoDao.getAcuerdoPorSociedadCargo(tramiteRegistro.getNumColegiado(), tramiteRegistro.getSociedad().getNif(),
				acuerdoDto.getSociedadCargo().getPersonaCargo().getNif(), acuerdoDto.getSociedadCargo().getCodigoCargo(), tramiteRegistro.getIdTramiteRegistro(), acuerdoDto.getTipoAcuerdo());

		if (acuerdoVO == null) {
			result = servicioSociedadCargo.guardarSociedadCargo(acuerdoDto.getSociedadCargo(), tramiteRegistro.getIdTramiteRegistro(), tramiteRegistro.getNumColegiado(), tramiteRegistro.getSociedad()
					.getNif(), tramiteRegistro.getTipoTramite(), idUsuario);
			if (!result.getError()) {
				acuerdoVO = conversor.transform(acuerdoDto, AcuerdoVO.class);
				acuerdoDao.guardarOActualizar(acuerdoVO);
				result.addAttachment(ACUERDO, acuerdoDto);
			}
		}
		return result;
	}

	private void calcularFecha(AcuerdoDto acuerdoDto, TramiteRegistroDto tramiteRegistro) {
		if (TipoAcuerdo.Cese.getValorEnum().equalsIgnoreCase(acuerdoDto.getTipoAcuerdo())) {
			acuerdoDto.getSociedadCargo().setFechaFin(tramiteRegistro.getReunion().getFecha());
		} else {
			if (Aceptacion.EN_LA_MISMA_JUNTA.getValorEnum().equals(acuerdoDto.getAceptacion())) {
				acuerdoDto.getSociedadCargo().setFechaInicio(tramiteRegistro.getReunion().getFecha());
			}
		}
	}

	@Transactional
	@Override
	public void eliminarAcuerdo(Long idAcuerdo) {
		AcuerdoVO acuerdo = getAcuerdo(idAcuerdo);
		if (acuerdo != null) {
			acuerdoDao.borrar(acuerdo);
		}
	}
}
