package org.gestoresmadrid.oegam2comun.clonarTramites.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafMatrVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.clonarTramites.model.service.ServicioClonarTramitesMatriculacion;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramiteMatriculacionDto;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ClonarTramitesDto;
import org.gestoresmadrid.oegam2comun.clonarTramites.view.dto.ResultClonarTramites;
import org.gestoresmadrid.oegam2comun.intervinienteTrafico.model.service.ServicioIntervinienteTrafico;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.IvtmMatriculacionDto;
import org.gestoresmadrid.oegam2comun.model.service.ServicioUsuario;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioEvolucionVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.EvolucionVehiculoDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.web.OegamExcepcion;
import utilidades.web.OegamExcepcion.EnumError;

@Service
public class ServicioClonarTramitesMatriculacionImpl implements ServicioClonarTramitesMatriculacion {

	private static final long serialVersionUID = -6182017958820666853L;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioIntervinienteTrafico servicioIntervinienteTrafico;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioIvtmMatriculacion servicioIvtm;

	@Autowired
	private ServicioUsuario servicioUsuario;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private Conversor conversor;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;

	@Override
	public Boolean comprobarCheckClonacion(ClonarTramitesDto clonarTramitesDto) {
		Boolean result = true;
		ResultClonarTramites resultClonarTramites = clonarTramitesDto.getResult();
		if (resultClonarTramites.getMatriculacion().isPestaniaTitular() && resultClonarTramites.getMatriculacion().isCheckTitular()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaVehiculo() && resultClonarTramites.getMatriculacion().isCheckVehiculo()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaConductorHabitual() && resultClonarTramites.getMatriculacion().isCheckConductorHabitual()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaLiberarEEFF() && resultClonarTramites.getMatriculacion().isCheckLiberarEEFF()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaRenting() && resultClonarTramites.getMatriculacion().isCheckRenting()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaModelo576() && resultClonarTramites.getMatriculacion().isCheckModelo576()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isPestaniaPagoPresentacion() && resultClonarTramites.getMatriculacion().isCheckPagoPresentacion()) {
			result = false;
		} else if (resultClonarTramites.getMatriculacion().isCheckRefPropia()) {
			result = false;
		}

		return result;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false, rollbackFor = OegamExcepcion.class)
	public ResultBean clonar(ClonarTramitesDto clonarTramitesDto, BigDecimal idUsuario) throws OegamExcepcion {
		ResultBean result = null;
		ClonarTramiteMatriculacionDto clonarTramiteMatriculacionDto = clonarTramitesDto.getResult().getMatriculacion();
		try {
			TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(clonarTramitesDto.getNumExpediente()), utilesColegiado
					.tienePermisoLiberarEEFF(), true);
			// tramiteTrafMatrDto.setNumColegiado(numColegiado.toString());
			UsuarioDto usuario = servicioUsuario.getUsuarioDto(idUsuario);
			if (!clonarTramiteMatriculacionDto.isCheckRefPropia()) {
				tramiteTrafMatrDto.setRefPropia(null);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaTitular()) {
				tramiteTrafMatrDto = noClonarTitular(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarTramiteMatriculacionDto.isCheckTitular() ? clonarTitular(tramiteTrafMatrDto) : noClonarTitular(tramiteTrafMatrDto);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaVehiculo()) {
				tramiteTrafMatrDto = noClonarVehiculo(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarVehiculo(clonarTramiteMatriculacionDto, tramiteTrafMatrDto);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaConductorHabitual()) {
				tramiteTrafMatrDto = noClonarConductorHabitual(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarTramiteMatriculacionDto.isCheckConductorHabitual() ? clonarConductorHabitual(tramiteTrafMatrDto) : noClonarConductorHabitual(tramiteTrafMatrDto);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaLiberarEEFF()) {
				tramiteTrafMatrDto = noClonarLiberacionEEFF(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarTramiteMatriculacionDto.isCheckLiberarEEFF() ? clonarLiberacionEEFF(tramiteTrafMatrDto) : noClonarLiberacionEEFF(tramiteTrafMatrDto);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaModelo576()) {
				tramiteTrafMatrDto = noClonarModelo576(tramiteTrafMatrDto);
			} else {
				if (clonarTramiteMatriculacionDto.isCheckModelo576()) {
					tramiteTrafMatrDto = clonarModelo576(tramiteTrafMatrDto);
				} else {
					tramiteTrafMatrDto = noClonarModelo576(tramiteTrafMatrDto);
				}
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaPagoPresentacion()) {
				tramiteTrafMatrDto = noClonarPagoPresentacion(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarTramiteMatriculacionDto.isCheckPagoPresentacion() ? clonarPagoPresentacion(tramiteTrafMatrDto) : noClonarPagoPresentacion(tramiteTrafMatrDto);
			}
			if (!clonarTramiteMatriculacionDto.isPestaniaRenting()) {
				tramiteTrafMatrDto = noClonarRenting(tramiteTrafMatrDto);
			} else {
				tramiteTrafMatrDto = clonarTramiteMatriculacionDto.isCheckRenting() ? clonarRenting(tramiteTrafMatrDto) : noClonarRenting(tramiteTrafMatrDto);
			}
			if (tramiteTrafMatrDto.getTasa() != null) {
				tramiteTrafMatrDto.setTasa(null);
			}
			if (tramiteTrafMatrDto.getNumExpediente() != null) {
				tramiteTrafMatrDto.setNumExpediente(null);
			}
			List<String> listaResultados = new ArrayList<String>();
			long numCopias = clonarTramitesDto.getResult().getNumCopias();
			for (int i = 0; i < numCopias; i++) {
				tramiteTrafMatrDto.setNumExpediente(servicioTramiteTrafico.generarNumExpediente(tramiteTrafMatrDto.getNumColegiado()));
				guardarClonado(tramiteTrafMatrDto, clonarTramiteMatriculacionDto, usuario);
				guardarEvolucionTramiteClonado(tramiteTrafMatrDto);
				listaResultados.add("Trámite: " + tramiteTrafMatrDto.getNumExpediente() + " se ha generado correctamente.");
			}
			result = new ResultBean();
			result.setError(false);
			result.setListaMensajes(listaResultados);
		} catch (OegamExcepcion oe) {
			if (EnumError.error_00002.equals(oe.getCodigoError())) {
				throw new OegamExcepcion(EnumError.error_00002, oe.getMensajeError1());
			} else {
				throw new OegamExcepcion(EnumError.error_00001, oe.getMessage());
			}
		} catch (Exception e) {
			throw new OegamExcepcion(EnumError.error_00001, e.getMessage());
		}
		return result;
	}

	private void guardarEvolucionTramiteClonado(TramiteTrafMatrDto tramiteTrafMatrDto) {
		TramiteTrafMatrVO tramiteTrafMatrVO = conversor.transform(tramiteTrafMatrDto, TramiteTrafMatrVO.class);
		servicioTramiteTrafico.guardarEvolucionTramiteClonado(tramiteTrafMatrVO);
	}

	private void guardarClonado(TramiteTrafMatrDto tramiteTrafMatrDto, ClonarTramiteMatriculacionDto clonarTramiteMatriculacionDto, UsuarioDto usuario) throws OegamExcepcion {
		if (clonarTramiteMatriculacionDto.isPestaniaVehiculo()) {
			tramiteTrafMatrDto.setVehiculoDto(guardarClonadoVehiculo(tramiteTrafMatrDto, usuario));
		}
		tramiteTrafMatrDto.setFechaAlta(utilesFecha.getFechaActual());
		tramiteTrafMatrDto.setFechaUltModif(utilesFecha.getFechaActual());
		tramiteTrafMatrDto.setFechaPresentacion(null);

		servicioTramiteTraficoMatriculacion.guardarOActualizar(tramiteTrafMatrDto);

		if (clonarTramiteMatriculacionDto.isPestaniaPagoPresentacion()) {
			guardarClonadoIvtm(tramiteTrafMatrDto);
		}

		if (clonarTramiteMatriculacionDto.isPestaniaLiberarEEFF()) {
			guardarClonadoEEFFLiberacio(tramiteTrafMatrDto);
		}

		if (clonarTramiteMatriculacionDto.isPestaniaTitular()) {
			guardarClonadoTitular(tramiteTrafMatrDto);
		}
		if (clonarTramiteMatriculacionDto.isPestaniaConductorHabitual()) {
			guardarClonadoConductorHabitual(tramiteTrafMatrDto);
		}

		if (clonarTramiteMatriculacionDto.isPestaniaRenting()) {
			guardarClonadoRenting(tramiteTrafMatrDto);
		}
	}

	private void guardarClonadoIvtm(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getIvtmMatriculacionDto() != null) {
			IvtmMatriculacionDto ivtmMatriculacionDto = tramiteTrafMatrDto.getIvtmMatriculacionDto();
			ivtmMatriculacionDto.setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			servicioIvtm.guardarIvtm(ivtmMatriculacionDto);
		}
	}

	private void guardarClonadoEEFFLiberacio(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getLiberacionEEFF() != null) {
			servicioEEFF.guardarDatosLiberacion(tramiteTrafMatrDto, tramiteTrafMatrDto.getNumExpediente());
		}
	}

	private void guardarClonadoRenting(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getArrendatario() != null) {
			IntervinienteTraficoVO arrendatarioVO = conversor.transform(tramiteTrafMatrDto.getArrendatario(), IntervinienteTraficoVO.class);
			arrendatarioVO.getId().setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			arrendatarioVO.getId().setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			if (tramiteTrafMatrDto.getArrendatario().getDireccion() != null && tramiteTrafMatrDto.getArrendatario().getDireccion().getIdDireccion() != null) {
				arrendatarioVO.setIdDireccion(tramiteTrafMatrDto.getArrendatario().getDireccion().getIdDireccion().longValue());
			}
			servicioIntervinienteTrafico.guardarActualizar(arrendatarioVO, null);
		}
		if (tramiteTrafMatrDto.getRepresentanteArrendatario() != null) {
			IntervinienteTraficoVO repreArrendatarioVO = conversor.transform(tramiteTrafMatrDto.getRepresentanteArrendatario(), IntervinienteTraficoVO.class);
			repreArrendatarioVO.getId().setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			repreArrendatarioVO.getId().setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			if (tramiteTrafMatrDto.getRepresentanteArrendatario().getDireccion() != null && tramiteTrafMatrDto.getRepresentanteArrendatario().getDireccion().getIdDireccion() != null) {
				repreArrendatarioVO.setIdDireccion(tramiteTrafMatrDto.getRepresentanteArrendatario().getDireccion().getIdDireccion().longValue());
			}
			servicioIntervinienteTrafico.guardarActualizar(repreArrendatarioVO, null);
		}
	}

	private void guardarClonadoConductorHabitual(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getConductorHabitual() != null) {
			IntervinienteTraficoVO intervConductorHabVO = conversor.transform(tramiteTrafMatrDto.getConductorHabitual(), IntervinienteTraficoVO.class);
			intervConductorHabVO.getId().setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			intervConductorHabVO.getId().setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			if (tramiteTrafMatrDto.getConductorHabitual().getDireccion() != null && tramiteTrafMatrDto.getConductorHabitual().getDireccion().getIdDireccion() != null) {
				intervConductorHabVO.setIdDireccion(tramiteTrafMatrDto.getConductorHabitual().getDireccion().getIdDireccion().longValue());
			}
			servicioIntervinienteTrafico.guardarActualizar(intervConductorHabVO, null);
		}
	}

	private VehiculoDto guardarClonadoVehiculo(TramiteTrafMatrDto tramiteTrafMatrDto, UsuarioDto usuario) throws OegamExcepcion {
		VehiculoVO vehiculoVo = null;
		if (tramiteTrafMatrDto.getVehiculoDto() != null) {
			vehiculoVo = conversor.transform(tramiteTrafMatrDto.getVehiculoDto(), VehiculoVO.class);

			VehiculoVO vehiculoPreverVO = null;
			if (tramiteTrafMatrDto.getVehiculoDto().getIdVehiculoPrever() != null && tramiteTrafMatrDto.getVehiculoPrever() != null) {
				vehiculoPreverVO = conversor.transform(tramiteTrafMatrDto.getVehiculoPrever(), VehiculoVO.class);
				vehiculoPreverVO = servicioVehiculo.crearVehiculoNuevoConEvolucion(vehiculoPreverVO, new EvolucionVehiculoDto(), tramiteTrafMatrDto.getNumExpediente(), TipoTramiteTrafico.Matriculacion
						.getValorEnum(), usuario, ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_CRE);
			}

			if (vehiculoPreverVO != null) {
				vehiculoVo.setIdVehiculoPrever(new BigDecimal(vehiculoPreverVO.getIdVehiculo()));
			}

			// conversor especifico matriculacion
			vehiculoVo = servicioVehiculo.crearVehiculoNuevoConEvolucion(vehiculoVo, new EvolucionVehiculoDto(), tramiteTrafMatrDto.getNumExpediente(), TipoTramiteTrafico.Matriculacion.getValorEnum(),
					usuario, ServicioEvolucionVehiculo.TIPO_ACTUALIZACION_CRE);
		}
		return conversor.transform(vehiculoVo, VehiculoDto.class);
	}

	private void guardarClonadoTitular(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getTitular() != null) {
			IntervinienteTraficoVO titularVO = conversor.transform(tramiteTrafMatrDto.getTitular(), IntervinienteTraficoVO.class);
			titularVO.getId().setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			titularVO.getId().setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			if (tramiteTrafMatrDto.getTitular().getDireccion() != null && tramiteTrafMatrDto.getTitular().getDireccion().getIdDireccion() != null) {
				titularVO.setIdDireccion(tramiteTrafMatrDto.getTitular().getDireccion().getIdDireccion().longValue());
			}
			servicioIntervinienteTrafico.guardarActualizar(titularVO, null);
		}
		if (tramiteTrafMatrDto.getRepresentanteTitular() != null) {
			IntervinienteTraficoVO repreTitularVO = conversor.transform(tramiteTrafMatrDto.getRepresentanteTitular(), IntervinienteTraficoVO.class);
			repreTitularVO.getId().setNumColegiado(tramiteTrafMatrDto.getNumColegiado());
			repreTitularVO.getId().setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
			if (tramiteTrafMatrDto.getRepresentanteTitular().getDireccion() != null && tramiteTrafMatrDto.getRepresentanteTitular().getDireccion().getIdDireccion() != null) {
				repreTitularVO.setIdDireccion(tramiteTrafMatrDto.getRepresentanteTitular().getDireccion().getIdDireccion().longValue());
			}
			servicioIntervinienteTrafico.guardarActualizar(repreTitularVO, null);
		}
	}

	private TramiteTrafMatrDto clonarRenting(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getArrendatario() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Debe de existir algún dato del Renting para poder clonar.");
		} else {
			tramiteTrafMatrDto.getArrendatario().setNumExpediente(null);
		}

		if (tramiteTrafMatrDto.getRepresentanteArrendatario() != null) {
			tramiteTrafMatrDto.getRepresentanteArrendatario().setNumExpediente(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarRenting(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getArrendatario() != null) {
			tramiteTrafMatrDto.setArrendatario(null);
		}

		if (tramiteTrafMatrDto.getRepresentanteArrendatario() != null) {
			tramiteTrafMatrDto.setRepresentanteArrendatario(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarPagoPresentacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getCem() != null) {
			tramiteTrafMatrDto.setCem(null);
		}
		if (tramiteTrafMatrDto.getExentoCem() != null) {
			tramiteTrafMatrDto.setExentoCem(null);
		}
		if (tramiteTrafMatrDto.getCema() != null) {
			tramiteTrafMatrDto.setCema(null);
		}
		if (tramiteTrafMatrDto.getJustificadoIvtm() != null) {
			tramiteTrafMatrDto.setJustificadoIvtm(null);
		}
		if (tramiteTrafMatrDto.getIvtmMatriculacionDto() != null) {
			tramiteTrafMatrDto.setIvtmMatriculacionDto(null);
		}
		if (tramiteTrafMatrDto.getJefaturaTraficoDto() != null) {
			tramiteTrafMatrDto.setJefaturaTraficoDto(null);
		}
		if (tramiteTrafMatrDto.getFechaPresentacion() != null) {
			tramiteTrafMatrDto.setFechaPresentacion(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarPagoPresentacion(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getCem() == null && tramiteTrafMatrDto.getExentoCem() == null && tramiteTrafMatrDto.getCema() == null && tramiteTrafMatrDto.getJustificadoIvtm() == null
				&& tramiteTrafMatrDto.getIvtmMatriculacionDto() == null && tramiteTrafMatrDto.getJefaturaTraficoDto() == null && tramiteTrafMatrDto.getFechaPresentacion() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Debe de existir algún dato del Pago o Presentación para poder clonar.");
		}
		if (tramiteTrafMatrDto.getIvtmMatriculacionDto().getNumExpediente() != null) {
			tramiteTrafMatrDto.getIvtmMatriculacionDto().setNumExpediente(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarModelo576(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getBaseImponible576() != null) {
			tramiteTrafMatrDto.setBaseImponible576(null);
		}
		if (tramiteTrafMatrDto.getReduccion576() != null) {
			tramiteTrafMatrDto.setReduccion576(null);
		}
		if (tramiteTrafMatrDto.getBaseImpoReducida576() != null) {
			tramiteTrafMatrDto.setBaseImpoReducida576(null);
		}
		if (tramiteTrafMatrDto.getTipoGravamen576() != null) {
			tramiteTrafMatrDto.setTipoGravamen576(null);
		}
		if (tramiteTrafMatrDto.getCuota576() != null) {
			tramiteTrafMatrDto.setCuota576(null);
		}
		if (tramiteTrafMatrDto.getDeduccionLineal576() != null) {
			tramiteTrafMatrDto.setDeduccionLineal576(null);
		}
		if (tramiteTrafMatrDto.getCuotaIngresar576() != null) {
			tramiteTrafMatrDto.setCuotaIngresar576(null);
		}
		if (tramiteTrafMatrDto.getDeducir576() != null) {
			tramiteTrafMatrDto.setDeducir576(null);
		}
		if (tramiteTrafMatrDto.getLiquidacion576() != null) {
			tramiteTrafMatrDto.setLiquidacion576(null);
		}
		if (tramiteTrafMatrDto.getNumDeclaracionComp576() != null) {
			tramiteTrafMatrDto.setNumDeclaracionComp576(null);
		}
		if (tramiteTrafMatrDto.getImporte576() != null) {
			tramiteTrafMatrDto.setImporte576(null);
		}
		if (tramiteTrafMatrDto.getEjercicio576() != null) {
			tramiteTrafMatrDto.setEjercicio576(null);
		}
		if (tramiteTrafMatrDto.getCausaHechoImpon576() != null) {
			tramiteTrafMatrDto.setCausaHechoImpon576(null);
		}
		if (tramiteTrafMatrDto.getObservaciones576() != null) {
			tramiteTrafMatrDto.setObservaciones576(null);
		}
		if (tramiteTrafMatrDto.getIdReduccion05() != null) {
			tramiteTrafMatrDto.setIdReduccion05(null);
		}
		if (tramiteTrafMatrDto.getIdNoSujeccion06() != null) {
			tramiteTrafMatrDto.setIdNoSujeccion06(null);
		}
		if (tramiteTrafMatrDto.getIedtm() != null) {
			tramiteTrafMatrDto.setIedtm(null);
		}
		if (tramiteTrafMatrDto.getFechaIedtm() != null) {
			tramiteTrafMatrDto.setFechaIedtm(null);
		}
		if (tramiteTrafMatrDto.getRegIedtm() != null) {
			tramiteTrafMatrDto.setRegIedtm(null);
		}
		if (tramiteTrafMatrDto.getFinancieraIedtm() != null) {
			tramiteTrafMatrDto.setFinancieraIedtm(null);
		}
		if (tramiteTrafMatrDto.getExento576() != null) {
			tramiteTrafMatrDto.setExento576(null);
		}
		if (tramiteTrafMatrDto.getNrc576() != null) {
			tramiteTrafMatrDto.setNrc576(null);
		}
		if (tramiteTrafMatrDto.getFechaPago576() != null) {
			tramiteTrafMatrDto.setFechaPago576(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarModelo576(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getBaseImponible576() == null && tramiteTrafMatrDto.getReduccion576() == null && tramiteTrafMatrDto.getBaseImpoReducida576() == null && tramiteTrafMatrDto
				.getTipoGravamen576() == null && tramiteTrafMatrDto.getCuota576() == null && tramiteTrafMatrDto.getDeduccionLineal576() == null && tramiteTrafMatrDto.getCuotaIngresar576() == null
				&& tramiteTrafMatrDto.getDeducir576() == null && tramiteTrafMatrDto.getLiquidacion576() == null && tramiteTrafMatrDto.getNumDeclaracionComp576() == null && tramiteTrafMatrDto
						.getImporte576() == null && tramiteTrafMatrDto.getEjercicio576() == null && tramiteTrafMatrDto.getCausaHechoImpon576() == null && tramiteTrafMatrDto
								.getObservaciones576() == null && tramiteTrafMatrDto.getIdReduccion05() == null && tramiteTrafMatrDto.getIdNoSujeccion06() == null && tramiteTrafMatrDto
										.getIedtm() == null && tramiteTrafMatrDto.getFechaIedtm() == null && tramiteTrafMatrDto.getRegIedtm() == null && tramiteTrafMatrDto.getFinancieraIedtm() == null
				&& tramiteTrafMatrDto.getExento576() == null && tramiteTrafMatrDto.getNrc576() == null && tramiteTrafMatrDto.getFechaPago576() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Debe de existir algún dato del Modelo 576 para poder clonar.");
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarLiberacionEEFF(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getLiberacionEEFF() != null) {
			tramiteTrafMatrDto.setLiberacionEEFF(null);
		}

		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarLiberacionEEFF(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getLiberacionEEFF() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Debe de existir algún dato de las Entidades Financieras para poder clonar.");
		}

		if (tramiteTrafMatrDto.getLiberacionEEFF().getNumExpediente() != null) {
			tramiteTrafMatrDto.getLiberacionEEFF().setNumExpediente(null);
		}

		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarConductorHabitual(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getConductorHabitual() != null) {
			tramiteTrafMatrDto.setConductorHabitual(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarConductorHabitual(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getConductorHabitual() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Debe de existir algún dato del conductor habitual para poder clonar.");
		}
		if (tramiteTrafMatrDto.getConductorHabitual().getNumExpediente() != null) {
			tramiteTrafMatrDto.getConductorHabitual().setNumExpediente(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarVehiculo(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getVehiculoDto() != null) {
			tramiteTrafMatrDto.setVehiculoDto(null);
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarVehiculo(ClonarTramiteMatriculacionDto clonarTramiteMatriculacionDto, TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getVehiculoDto() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Deben existir datos del vehiculo para poder clonar.");
		} else {
			if (tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null) {
				tramiteTrafMatrDto.getVehiculoDto().setBastidor(null);
			}
			if (tramiteTrafMatrDto.getVehiculoDto().getIdVehiculo() != null) {
				tramiteTrafMatrDto.getVehiculoDto().setIdVehiculo(null);
			}
		}
		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto noClonarTitular(TramiteTrafMatrDto tramiteTrafMatrDto) {
		if (tramiteTrafMatrDto.getTitular() != null) {
			tramiteTrafMatrDto.setTitular(null);
		}

		if (tramiteTrafMatrDto.getRepresentanteTitular() != null) {
			tramiteTrafMatrDto.setRepresentanteTitular(null);
		}

		return tramiteTrafMatrDto;
	}

	private TramiteTrafMatrDto clonarTitular(TramiteTrafMatrDto tramiteTrafMatrDto) throws OegamExcepcion {
		if (tramiteTrafMatrDto.getTitular() == null) {
			throw new OegamExcepcion(EnumError.error_00002, "Deben de existir algun datos del Titular para poder clonar.");
		} else {
			tramiteTrafMatrDto.getTitular().setNumExpediente(null);
		}

		if (tramiteTrafMatrDto.getRepresentanteTitular() != null) {
			tramiteTrafMatrDto.getRepresentanteTitular().setNumExpediente(null);
		}

		return tramiteTrafMatrDto;
	}

	@Override
	@Transactional
	public ClonarTramitesDto getPestaniasClonar(ClonarTramitesDto clonarTramitesDto) throws OegamExcepcion {
		TramiteTrafMatrDto tramiteTrafMatrDto = servicioTramiteTraficoMatriculacion.getTramiteMatriculacion(new BigDecimal(clonarTramitesDto.getNumExpediente()), utilesColegiado
				.tienePermisoLiberarEEFF(), true);
		ClonarTramiteMatriculacionDto clonarTramiteMatriculacionDto = new ClonarTramiteMatriculacionDto();
		ResultClonarTramites result = new ResultClonarTramites();
		if (tramiteTrafMatrDto.getTitular() != null || tramiteTrafMatrDto.getRepresentanteTitular() != null) {
			clonarTramiteMatriculacionDto.setPestaniaTitular(true);
		}
		if (tramiteTrafMatrDto.getRenting() != null && tramiteTrafMatrDto.getRenting()) {
			clonarTramiteMatriculacionDto.setPestaniaRenting(true);
		}
		if (tramiteTrafMatrDto.getConductorHabitual() != null && tramiteTrafMatrDto.getConductorHabitual().getPersona() != null && ((tramiteTrafMatrDto.getConductorHabitual().getPersona()
				.getNif() != null) || (tramiteTrafMatrDto.getConductorHabitual().getPersona().getNif() != ""))) {
			clonarTramiteMatriculacionDto.setPestaniaConductorHabitual(true);
		}
		if (tramiteTrafMatrDto.getVehiculoDto() != null && tramiteTrafMatrDto.getVehiculoDto().getCodItv() != null) {
			clonarTramiteMatriculacionDto.setPestaniaVehiculo(true);
		}
		if (tramiteTrafMatrDto.getLiberacionEEFF() != null) {
			clonarTramiteMatriculacionDto.setPestaniaLiberarEEFF(true);
		}
		if (existeDatosModelo576(tramiteTrafMatrDto)) {
			clonarTramiteMatriculacionDto.setPestaniaModelo576(true);
		}
		if (existeDatosPagoPresentacion(tramiteTrafMatrDto)) {
			clonarTramiteMatriculacionDto.setPestaniaPagoPresentacion(true);
		}
		result.setMatriculacion(clonarTramiteMatriculacionDto);
		clonarTramitesDto.setResult(result);
		return clonarTramitesDto;
	}

	private boolean existeDatosPagoPresentacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		boolean existe = false;
		if (tramiteTrafMatrDto.getCem() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getExentoCem() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getCema() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getJustificadoIvtm() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getIvtmMatriculacionDto() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getJefaturaTraficoDto() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getFechaPresentacion() != null) {
			existe = true;
		}
		return existe;
	}

	private boolean existeDatosModelo576(TramiteTrafMatrDto tramiteTrafMatrDto) {
		boolean existe = false;
		if (tramiteTrafMatrDto.getBaseImponible576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getReduccion576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getBaseImpoReducida576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getTipoGravamen576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getCuota576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getDeduccionLineal576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getCuotaIngresar576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getDeducir576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getLiquidacion576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getNumDeclaracionComp576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getImporte576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getEjercicio576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getCausaHechoImpon576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getObservaciones576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getIdReduccion05() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getIdNoSujeccion06() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getIedtm() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getFechaIedtm() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getRegIedtm() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getFinancieraIedtm() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getExento576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getNrc576() != null) {
			existe = true;
		} else if (tramiteTrafMatrDto.getFechaPago576() != null) {
			existe = true;
		}
		return existe;
	}

	public ServicioTramiteTrafico getServicioTramiteTrafico() {
		return servicioTramiteTrafico;
	}

	public void setServicioTramiteTrafico(ServicioTramiteTrafico servicioTramiteTrafico) {
		this.servicioTramiteTrafico = servicioTramiteTrafico;
	}

	public ServicioTramiteTraficoMatriculacion getServicioTramiteTraficoMatriculacion() {
		return servicioTramiteTraficoMatriculacion;
	}

	public void setServicioTramiteTraficoMatriculacion(ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion) {
		this.servicioTramiteTraficoMatriculacion = servicioTramiteTraficoMatriculacion;
	}

	public ServicioIntervinienteTrafico getServicioIntervinienteTrafico() {
		return servicioIntervinienteTrafico;
	}

	public void setServicioIntervinienteTrafico(ServicioIntervinienteTrafico servicioIntervinienteTrafico) {
		this.servicioIntervinienteTrafico = servicioIntervinienteTrafico;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public ServicioIvtmMatriculacion getServicioIvtm() {
		return servicioIvtm;
	}

	public void setServicioIvtm(ServicioIvtmMatriculacion servicioIvtm) {
		this.servicioIvtm = servicioIvtm;
	}

	public ServicioUsuario getServicioUsuario() {
		return servicioUsuario;
	}

	public void setServicioUsuario(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}
}