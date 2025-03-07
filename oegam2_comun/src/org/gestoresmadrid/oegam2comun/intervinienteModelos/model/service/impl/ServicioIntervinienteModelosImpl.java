package org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.intervinienteModelos.model.dao.IntervinienteModelosDao;
import org.gestoresmadrid.core.intervinienteModelos.model.vo.IntervinienteModelosVO;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.modelo600_601.model.vo.Modelo600_601VO;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.remesa.model.vo.RemesaVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.model.service.ServicioIntervinienteModelos;
import org.gestoresmadrid.oegam2comun.intervinienteModelos.view.dto.IntervinienteModelosDto;
import org.gestoresmadrid.oegam2comun.modelo600_601.view.dto.Modelo600_601Dto;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioConcepto;
import org.gestoresmadrid.oegam2comun.participacion.model.service.ServicioParticipacion;
import org.gestoresmadrid.oegam2comun.participacion.view.dto.ParticipacionDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioEvolucionPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.personas.view.dto.EvolucionPersonaDto;
import org.gestoresmadrid.oegam2comun.remesa.view.dto.RemesaDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIntervinienteModelosImpl implements ServicioIntervinienteModelos{

	private static final long serialVersionUID = -4406278985469429470L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIntervinienteModelosImpl.class);

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioEvolucionPersona servicioEvolucionPersona;

	@Autowired
	private ServicioParticipacion servicioParticipacion;

	@Autowired
	private Conversor conversor;

	@Autowired
	private IntervinienteModelosDao intervinienteModelosDao;

	@Autowired
	private ServicioConcepto servicioConcepto;

	@Autowired
	Utiles utiles;

	@Override
	public ResultBean getIntervinientePorNifYContrato(String nif, BigDecimal idContrato) {
		ResultBean resultado = null;
		try {
			ContratoDto contratoDto = servicioContrato.getContratoDto(idContrato);
			if(contratoDto != null && contratoDto.getColegiadoDto() != null && contratoDto.getColegiadoDto().getNumColegiado() != null && nif != null && !nif.isEmpty()){
					IntervinienteModelosDto intervinienteModelosDto = getIntervinientePorNifYNumColegiado(nif, contratoDto.getColegiadoDto().getNumColegiado());
					resultado = new ResultBean(false);
					resultado.addAttachment("intervinienteDto", intervinienteModelosDto);
			}else{
				resultado = new ResultBean(true, "No existen datos suficientes para poder realizar la búsqueda de los intervinientes.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el interviniente por el NIF: " + nif + ", y para el contrato: " + idContrato + ",error: " ,e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente");
		}
		return resultado;
	}

	@Override
	@Transactional
	public IntervinienteModelosDto getIntervinientePorNifYNumColegiado(String nif, String numColegiado){
		IntervinienteModelosDto intervinienteModelosDto = null;
		try{
			IntervinienteModelosVO interModelosVO = intervinienteModelosDao.getIntervinientePorNifYNumColegiado(nif,numColegiado);
			if(interModelosVO != null && interModelosVO.getPersona() != null){
				intervinienteModelosDto = conversor.transform(interModelosVO, IntervinienteModelosDto.class);
				ResultBean resultado = servicioPersonaDireccion.buscarPersonaDireccionDto(numColegiado, nif);
				if(!resultado.getError()){
					PersonaDireccionDto personaDireccionDto = (PersonaDireccionDto) resultado.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
					intervinienteModelosDto.getPersona().setDireccionDto(personaDireccionDto.getDireccion());
				}
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de obtener el interviniente por nif y numColegiado, error: ",e);
		}
		return intervinienteModelosDto;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean convertirIntervinientesVoToDto(RemesaDto remesaDto, RemesaVO remesaVO) {
		ResultBean resultado = null;
		try {
			List<IntervinienteModelosDto> listaSujPasivo = null;
			List<IntervinienteModelosDto> listaTransmitente = null;
			IntervinienteModelosDto presentador = null;
			if(remesaVO.getListaIntervinientes() != null && !remesaVO.getListaIntervinientes().isEmpty()){
				for(IntervinienteModelosVO interModelosVO : remesaVO.getListaIntervinientes()){
					if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						if(listaSujPasivo == null){
							listaSujPasivo = new ArrayList<IntervinienteModelosDto>();
						}
						listaSujPasivo.add(conversor.transform(interModelosVO, IntervinienteModelosDto.class));
					}else if(TipoInterviniente.Transmitente.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						if(listaTransmitente == null){
							listaTransmitente = new ArrayList<IntervinienteModelosDto>();
						}
						listaTransmitente.add(conversor.transform(interModelosVO, IntervinienteModelosDto.class));
					}else if(TipoInterviniente.Presentador.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						presentador = conversor.transform(interModelosVO, IntervinienteModelosDto.class);
					}
				}
				if(listaSujPasivo != null && !listaSujPasivo.isEmpty()){
					remesaDto.setListaSujetosPasivos(listaSujPasivo);
				}
				if(listaTransmitente != null && !listaTransmitente.isEmpty()){
					remesaDto.setListaTransmitentes(listaTransmitente);
				}
				remesaDto.setPresentador(presentador);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de convertir los intervinientes");
		}

		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean convertirIntervinientesModeloVoToDto(Modelo600_601VO modeloVO, Modelo600_601Dto modeloDto) {
		ResultBean resultado = null;
		try {
			if(modeloVO.getListaIntervinientes() != null && !modeloVO.getListaIntervinientes().isEmpty()){
				for(IntervinienteModelosVO interModelosVO : modeloVO.getListaIntervinientes()){
					if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						modeloDto.setSujetoPasivo(conversor.transform(interModelosVO, IntervinienteModelosDto.class));
					}else if(TipoInterviniente.Transmitente.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						modeloDto.setTransmitente(conversor.transform(interModelosVO, IntervinienteModelosDto.class));
					}else if(TipoInterviniente.Presentador.getValorEnum().equals(interModelosVO.getTipoInterviniente())){
						modeloDto.setPresentador(conversor.transform(interModelosVO, IntervinienteModelosDto.class));
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de convertir los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de convertir los intervinientes");
		}
		return resultado;
	}

	@Override
	public ResultBean guardarIntervinientesRemesa(IntervinienteModelosVO intervinienteModVO, RemesaVO remesaVO, String tipoTramiteModelo, UsuarioDto usuario, boolean esConceptoBienes,BigDecimal porcentaje) {
		ResultBean resultado = new ResultBean(false);
		boolean direccionNueva = false;
		try {
			ResultBean resultValidar = validarDatosMinimos(intervinienteModVO);
			if(!resultValidar.getError()){
				if(intervinienteModVO.getPersona().getId().getNumColegiado() == null || intervinienteModVO.getPersona().getId().getNumColegiado().isEmpty()){
					intervinienteModVO.getPersona().getId().setNumColegiado(usuario.getNumColegiado());
				}

				if(intervinienteModVO.getNumColegiado() == null || intervinienteModVO.getNumColegiado().isEmpty()){
					intervinienteModVO.setNumColegiado(usuario.getNumColegiado());
				}
				//guardamos la persona antes que el interviniente
				ResultBean resultPersona = servicioPersona.guardarActualizar(intervinienteModVO.getPersona(), remesaVO.getNumExpediente(), tipoTramiteModelo, usuario, ServicioIntervinienteModelos.CONVERSION_PERSONA_REMESA);
				if(!resultPersona.getError()){
					ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(intervinienteModVO.getDireccion(), intervinienteModVO.getPersona().getId().getNif(),
							intervinienteModVO.getPersona().getId().getNumColegiado(), tipoTramiteModelo, ServicioDireccion.CONVERSION_DIRECCION_INE);
					if(resultDireccion.getError()){
						resultado.addMensajeALista(resultDireccion.getMensaje());
						intervinienteModVO.setIdDireccion(null);
					}else {
						if(intervinienteModVO.getDireccion() != null){
							DireccionVO direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
							direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
							if (direccion != null) {
								intervinienteModVO.setIdDireccion(direccion.getIdDireccion());
							}
							guardarEvolucionPersona(intervinienteModVO.getPersona().getId().getNif(), remesaVO.getNumExpediente(), tipoTramiteModelo,
									intervinienteModVO.getPersona().getId().getNumColegiado(), usuario, direccionNueva);
						}
					}
					intervinienteModVO.setIdRemesa(remesaVO.getIdRemesa());
					intervinienteModelosDao.guardarOActualizar(intervinienteModVO);
					if(!esConceptoBienes && !TipoInterviniente.Presentador.getValorEnum().equals(intervinienteModVO.getTipoInterviniente())){
						resultado = servicioParticipacion.guardarCoefParticipacionInterviniente(intervinienteModVO.getIdInterviniente(),remesaVO.getIdRemesa(),porcentaje);
					}
				}
			}else{
				resultado.setError(true);
				resultado.setListaMensajes(resultValidar.getListaMensajes());;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los intervinientes");
		}
		return resultado;
	}

	private ResultBean validarDatos(IntervinienteModelosVO intervinienteModelos,String mensajeError) {
		ResultBean resultado = new ResultBean(false);
		if(intervinienteModelos.getPersona().getId().getNif() == null || intervinienteModelos.getPersona().getId().getNif().isEmpty()){
			resultado.setError(true);
			resultado.addMensajeALista(mensajeError + " debe rellenar un NIF/CIF.");
		}

		if(intervinienteModelos.getPersona().getTipoPersona() != null && !intervinienteModelos.getPersona().getTipoPersona().isEmpty()){
			if(TipoPersona.Juridica.getValorEnum().equals(intervinienteModelos.getPersona().getTipoPersona())){
				if(intervinienteModelos.getPersona().getApellido1RazonSocial() == null || intervinienteModelos.getPersona().getApellido1RazonSocial().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista(mensajeError + " debe rellenar la razón social.");
				}
			}else if(TipoPersona.Fisica.getValorEnum().equals(intervinienteModelos.getPersona().getTipoPersona())){
				if(intervinienteModelos.getPersona().getApellido1RazonSocial() == null || intervinienteModelos.getPersona().getApellido1RazonSocial().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista(mensajeError + " debe rellenar el 1er apellido.");
				}
				// Hay personas que no tienen segundo apellido
//					if(intervinienteModelos.getPersona().getApellido2() == null || intervinienteModelos.getPersona().getApellido2().isEmpty()){
//						resultado.setError(true);
//						resultado.addMensajeALista(mensajeError + " debe rellenar el 2do Apellido.");
//					}
				if(intervinienteModelos.getPersona().getNombre() == null || intervinienteModelos.getPersona().getNombre().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista(mensajeError + " debe rellenar el nombre.");
				}
			}
		}

		if(intervinienteModelos.getDireccion() == null){
			resultado.setError(true);
			resultado.addMensajeALista(mensajeError + " debe rellenar los datos obligatorios de la dirección.");
		}else{
			if(intervinienteModelos.getDireccion().getIdProvincia() == null || intervinienteModelos.getDireccion().getIdProvincia().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe seleccionar una provincia para la dirección.");
			}

			if(intervinienteModelos.getDireccion().getIdMunicipio() == null || intervinienteModelos.getDireccion().getIdMunicipio().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe seleccionar un municipio para la dirección.");
			}

			if(intervinienteModelos.getDireccion().getIdTipoVia() == null || intervinienteModelos.getDireccion().getIdTipoVia().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe seleccionar un tipo de vía para la dirección.");
			}

			if(intervinienteModelos.getDireccion().getNombreVia() == null || intervinienteModelos.getDireccion().getNombreVia().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe rellenar el nombre de la vía.");
			}

			if(intervinienteModelos.getDireccion().getNumero() == null || intervinienteModelos.getDireccion().getNumero().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe rellenar el numero de la vía.");
			}

			if(intervinienteModelos.getDireccion().getCodPostal() == null || intervinienteModelos.getDireccion().getCodPostal().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista(mensajeError + " debe rellenar el codigo postal de la dirección.");
			}
		}
		return resultado;
	}

	private void guardarEvolucionPersona(String nif, BigDecimal numExpediente, String tipoTramite, String numColegiado, UsuarioDto usuario, boolean direccionNueva) {
		if (direccionNueva) {
			EvolucionPersonaDto evolucionDto = new EvolucionPersonaDto();
			evolucionDto.setNumColegiado(numColegiado);
			evolucionDto.setNumExpediente(numExpediente);
			evolucionDto.setTipoTramite(tipoTramite);
			evolucionDto.setNif(nif);
			evolucionDto.setOtros("Nueva Dirección");
			evolucionDto.setTipoActuacion(TipoActualizacion.MOD.getNombreEnum());
			evolucionDto.setUsuario(usuario);

			servicioEvolucionPersona.guardarEvolucion(evolucionDto);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getIntervinientePorNifYIdRemesaYTipoInterviniente(String nif, Long idRemesa, String tipoInterviniente) {
		ResultBean resultado = null;
		try {
			if(nif != null && !nif.isEmpty() && idRemesa != null && tipoInterviniente != null && !tipoInterviniente.isEmpty()){
				IntervinienteModelosVO intervinienteModelosVO = intervinienteModelosDao.getIntervinientePorNifYIdRemesaYTipoInterviniente(nif,idRemesa,tipoInterviniente);
				if(intervinienteModelosVO != null){
					resultado = new ResultBean(false);
					resultado.addAttachment("intervinienteDto", conversor.transform(intervinienteModelosVO, IntervinienteModelosDto.class));
				}
			}else{
				resultado = new ResultBean(true, "Faltan datos obligatorios para poder realizar la consulta del interviniente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el interviniente por la remesa y el NIF, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente por la remesa y el NIF");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getIntervinientePorNifYIdModeloYTipoInterviniente(String nif, Long idModelo, String tipoInterviniente) {
		ResultBean resultado = null;
		try {
			if(nif != null && !nif.isEmpty() && idModelo != null && tipoInterviniente != null && !tipoInterviniente.isEmpty()){
				IntervinienteModelosVO intervinienteModelosVO = intervinienteModelosDao.getIntervinientePorNifYIdModeloYTipoInterviniente(nif,idModelo,tipoInterviniente);
				if(intervinienteModelosVO != null){
					resultado = new ResultBean(false);
					resultado.addAttachment("intervinienteDto", conversor.transform(intervinienteModelosVO, IntervinienteModelosDto.class));
				}
			}else{
				resultado = new ResultBean(true, "Faltan datos obligatorios para poder realizar la consulta del interviniente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el interviniente por el modelo y el NIF, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente por el modelo y el NIF");
		}
		return resultado;
	}

	@Override
	public ResultBean getIntervinientesPorIdRemesa(Long idRemesa) {
		ResultBean resultado = null;
		try {
			if(idRemesa != null){
				List<IntervinienteModelosVO> lista = intervinienteModelosDao.getIntervinientesPorRemesa(idRemesa);
				if(lista != null && !lista.isEmpty()){
					resultado = new ResultBean(false);
					resultado.addAttachment("listaIntervinientesVO", lista);
				}
			}else{
				resultado = new ResultBean(true, "Falta el id de la remesa para poder realizar la consulta de los intervinientes");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los interviniente por la remesa, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener los interviniente por la remesa");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getIntervinientesPorId(Long idInterviniente) {
		ResultBean resultado = null;
		try {
			if(idInterviniente != null){
				IntervinienteModelosVO intervinienteModelosVO = intervinienteModelosDao.getIntervinientePorId(idInterviniente);
				if(intervinienteModelosVO != null){
					resultado = new ResultBean(false);
					resultado.addAttachment("intervMDto", conversor.transform(intervinienteModelosVO, IntervinienteModelosDto.class));
				}else{
					resultado = new ResultBean(true, "No existen datos para ese interviniente");
				}
			}else{
				resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el interviniente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean getIntervinientesRemesaPorId(Long idInterviniente) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(idInterviniente != null){
				ResultBean resultInterv = getIntervinientesPorId(idInterviniente);
				if(!resultInterv.getError()){
					IntervinienteModelosDto intervModDto = (IntervinienteModelosDto) resultInterv.getAttachment("intervMDto");
					if(!servicioConcepto.esConceptoBienes(intervModDto.getRemesa().getConcepto())){
						//obtener porcentaje participacion al ser concepto sin bienes
						List<ParticipacionDto> listaParticipacion = servicioParticipacion.getListaParticipacionIntervinientesRemesa(idInterviniente,intervModDto.getRemesa().getIdRemesa());
						if(listaParticipacion != null && !listaParticipacion.isEmpty()){
							if(listaParticipacion.size()<=2){
								for(ParticipacionDto participacionDto : listaParticipacion){
									if(!TipoInterviniente.Presentador.getValorEnum().equals(participacionDto.getIntervinienteModelos().getTipoInterviniente())){
										intervModDto.setPorcentaje(participacionDto.getPorcentaje());
										break;
									}
								}
								resultado.addAttachment("intervMDto", intervModDto);
								return resultado;
							}
						}
					}
					return resultInterv;
				}
			}else{
				resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el interviniente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el interviniente.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarIntervientesModelos(Set<IntervinienteModelosVO> listaIntervinientes) {
		ResultBean resultado = new ResultBean(false);
		try {
			if(listaIntervinientes != null && !listaIntervinientes.isEmpty()){
				for(IntervinienteModelosVO intervinienteModelosVO : listaIntervinientes){
					intervinienteModelosDao.borrar(intervinienteModelosVO);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar los intervinientes del modelo, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de eliminar los intervinientes del modelo.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean eliminarInterviniente(Long idInterviniente) {
		ResultBean resultado = new ResultBean(false);
		try {
			IntervinienteModelosVO intervinienteModelosVO = intervinienteModelosDao.getIntervinientePorId(idInterviniente);
			if(intervinienteModelosVO != null) {
				intervinienteModelosDao.borrar(intervinienteModelosVO);
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("No existen datos para ese interviniente");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de eliminar el interviniente de la remesa, error: ",e);
			resultado = new ResultBean(true,"Ha sucedido un error a la hora de eliminar el interviniente de la remesa");
		}
		return resultado;
	}

	@Override
	public ResultBean validarGenerarModelosIntervinientes(RemesaVO remesaVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			Boolean existeSujPasivo = false;
			Boolean existeTransmitente = false;
			String mensajeError = null;
			if(remesaVO.getListaIntervinientes() != null && !remesaVO.getListaIntervinientes().isEmpty()){
				for(IntervinienteModelosVO intervinienteModelosVO : remesaVO.getListaIntervinientes()){
					if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
						existeSujPasivo = true;
					}else if(TipoInterviniente.Transmitente.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
						existeTransmitente = true;
					}
					mensajeError = "Para el " + TipoInterviniente.convertirTexto(intervinienteModelosVO.getTipoInterviniente()) + " con NIF: " + intervinienteModelosVO.getPersona().getId().getNif() + " se";
					ResultBean resultValidarInterv = validarDatos(intervinienteModelosVO,mensajeError);
					if(resultValidarInterv.getError()){
						resultado.setError(true);
						for(String mensaje : resultValidarInterv.getListaMensajes()){
							resultado.addMensajeALista(mensaje);
						}
					}
				}
				if(!existeSujPasivo){
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar los datos del sujeto pasivo.");
				}
				if(!existeTransmitente && !resultado.getError()){
					resultado.setError(false);
					resultado.addMensajeALista("No existen datos del transmitente.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe de seleccionar algún interviniente para la remesa.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de validar los intervinientes.");
		}
		return resultado;
	}

	@Override
	public ResultBean validarIntervinientesModelos(Modelo600_601VO modeloVO) {
		ResultBean resultado = new ResultBean(false);
		try {
			Boolean existeSujPasivo = false;
			Boolean existeTransmitente = false;
			String mensajeError = null;
			if(modeloVO.getListaIntervinientes() != null && !modeloVO.getListaIntervinientes().isEmpty()){
				for(IntervinienteModelosVO intervinienteModelosVO : modeloVO.getListaIntervinientes()){
					if(TipoInterviniente.SujetoPasivo.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
						existeSujPasivo = true;
					}else if(TipoInterviniente.Transmitente.getValorEnum().equals(intervinienteModelosVO.getTipoInterviniente())){
						existeTransmitente = true;
					}
					mensajeError = "Para el " + TipoInterviniente.convertirTexto(intervinienteModelosVO.getTipoInterviniente()) + " se";
					ResultBean resultValidarInterv = validarDatos(intervinienteModelosVO,mensajeError);
					if(resultValidarInterv.getError()){
						resultado.setError(true);
						for(String mensaje : resultValidarInterv.getListaMensajes()){
							resultado.addMensajeALista(mensaje);
						}
					}
				}
				if(!existeSujPasivo){
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar los datos del sujeto pasivo.");
				}
				if(!existeTransmitente && !resultado.getError()){
					resultado.setError(false);
					resultado.addMensajeALista("No existen datos del transmitente.");
				}
			}else{
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar algún interviniente.");
			}
			
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar los intervinientes, error: ",e);
			resultado.setError(true);
			resultado.addMensajeALista("Ha sucedido un error a la hora de validar los intervinientes.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean actualizarInterv(IntervinienteModelosVO intervinienteModelosVO) {
		ResultBean resultado = null;
		try {
			if(intervinienteModelosVO != null){
				intervinienteModelosDao.guardarOActualizar(intervinienteModelosVO);
			}else{
				resultado = new ResultBean(true, "No existen datos del interviniente para actualizar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de actualizar el interviniente, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de actualizar el interviniente.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarIntervinientesModelo600_601(IntervinienteModelosVO intervinienteModelosVO, Long idModelo) {
		ResultBean resultado = null;
		try {
			intervinienteModelosVO.setIdModelo(idModelo);
			intervinienteModelosVO.setNif(intervinienteModelosVO.getNif().toUpperCase());
			intervinienteModelosDao.guardarOActualizar(intervinienteModelosVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los interviniente de los modelos, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los interviniente de los modelos.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean guardarIntervinientesModelo(IntervinienteModelosVO intervinienteModelosVO, Modelo600_601VO modeloVO, String tipoTramiteModelo, UsuarioDto usuario) {
		ResultBean resultado = new ResultBean(false);
		boolean direccionNueva = false;
		try {
			ResultBean resultValidar = validarDatosMinimos(intervinienteModelosVO);
			if(!resultValidar.getError()){
				if(intervinienteModelosVO.getPersona().getId().getNumColegiado() == null || intervinienteModelosVO.getPersona().getId().getNumColegiado().isEmpty()){
					intervinienteModelosVO.getPersona().getId().setNumColegiado(usuario.getNumColegiado());
				}
				if(intervinienteModelosVO.getNumColegiado() == null || intervinienteModelosVO.getNumColegiado().isEmpty()){
					intervinienteModelosVO.setNumColegiado(usuario.getNumColegiado());
				}
				//guardamos la persona antes que el interviniente
				ResultBean resultPersona = servicioPersona.guardarActualizar(intervinienteModelosVO.getPersona(), modeloVO.getNumExpediente(), tipoTramiteModelo, usuario, null);
				if(!resultPersona.getError()){
					if(intervinienteModelosVO.getDireccion() != null){
						ResultBean resultDireccion = servicioDireccion.guardarActualizarPersona(intervinienteModelosVO.getDireccion(), intervinienteModelosVO.getPersona().getId().getNif(),
								intervinienteModelosVO.getPersona().getId().getNumColegiado(), tipoTramiteModelo, ServicioDireccion.CONVERSION_DIRECCION_INE);
						if(resultDireccion.getError()){
							resultado.addMensajeALista(resultDireccion.getMensaje());
							intervinienteModelosVO.setIdDireccion(null);
						}else {
							DireccionVO direccion = (DireccionVO) resultDireccion.getAttachment(ServicioDireccion.DIRECCION);
							direccionNueva = (Boolean) resultDireccion.getAttachment(ServicioDireccion.NUEVA_DIRECCION);
							if (direccion != null) {
								intervinienteModelosVO.setIdDireccion(direccion.getIdDireccion());
							}
							guardarEvolucionPersona(intervinienteModelosVO.getPersona().getId().getNif(), modeloVO.getNumExpediente(), tipoTramiteModelo,
									intervinienteModelosVO.getPersona().getId().getNumColegiado(), usuario, direccionNueva);
						}
					}
					intervinienteModelosVO.setIdModelo(modeloVO.getIdModelo());
					intervinienteModelosVO.setNif(intervinienteModelosVO.getNif().toUpperCase());
					intervinienteModelosDao.guardarOActualizar(intervinienteModelosVO);
				} else {
					resultado.setError(false);
					resultado.setListaMensajes(resultPersona.getListaMensajes());
				}
			}else{
				resultado.setError(false);
				resultado.setListaMensajes(resultValidar.getListaMensajes());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar los intervinientes, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de guardar los intervinientes");
		}
		return resultado;
	}

	private ResultBean validarDatosMinimos(IntervinienteModelosVO intervinienteModelosVO) {
		ResultBean resultado = new ResultBean(false);
		resultado.addMensajeALista("El " + TipoInterviniente.convertirTexto(intervinienteModelosVO.getTipoInterviniente()) + " contiene los siguientes errores : ");
		if(intervinienteModelosVO.getPersona() != null){
			if(intervinienteModelosVO.getPersona().getId().getNif() == null || intervinienteModelosVO.getPersona().getId().getNif().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar un NIF/CIF.");
			}
			
			if(intervinienteModelosVO.getPersona().getApellido1RazonSocial() == null || intervinienteModelosVO.getPersona().getApellido1RazonSocial().isEmpty()){
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el Primer Apellido/Razón Social.");
			}
			if(intervinienteModelosVO.getPersona().getTipoPersona() != null && TipoPersona.Fisica.getValorEnum().equals(intervinienteModelosVO.getPersona().getTipoPersona())){
				if(intervinienteModelosVO.getPersona().getNombre() == null || intervinienteModelosVO.getPersona().getNombre().isEmpty()){
					resultado.setError(true);
					resultado.addMensajeALista("Debe rellenar el nombre.");
				}
			}
		}
		if(intervinienteModelosVO.getDireccion() != null){
			if (utiles.convertirCombo(intervinienteModelosVO.getDireccion().getIdMunicipio()) == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el municipio.");
			} else if (utiles.convertirCombo(intervinienteModelosVO.getDireccion().getIdProvincia()) == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar la provincia.");
			} else if (utiles.convertirCombo(intervinienteModelosVO.getDireccion().getIdTipoVia()) == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el tipo de vía.");
			} else if (utiles.convertirNulltoString(intervinienteModelosVO.getDireccion().getNombreVia()) == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el nombre de la vía.");
			} else if (utiles.convertirNulltoString(intervinienteModelosVO.getDireccion().getNumero()) == null) {
				resultado.setError(true);
				resultado.addMensajeALista("Debe rellenar el número de la vía.");
			}
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public List<IntervinienteModelosDto> getListaIntervinientesPorModelo(Long idModelo) {
		try {
			List<IntervinienteModelosVO> lista = intervinienteModelosDao.getListaIntervinientesPorModelo(idModelo);
			if(lista != null && !lista.isEmpty()){
				return conversor.transform(lista, IntervinienteModelosDto.class);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los intervinientes del modelo, error: ",e);
		}
		return Collections.emptyList();
	}
	@Override
	@Transactional(readOnly=true)
	public List<IntervinienteModelosVO> getListaIntervinientesVOPorModelo(Long idModelo) {
		try {
			List<IntervinienteModelosVO> lista = intervinienteModelosDao.getListaIntervinientesPorModelo(idModelo);
			if(lista != null && !lista.isEmpty()){
				return lista;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los intervinientes del modelo, error: ",e);
		}
		return Collections.emptyList();
	}

	@Transactional
	@Override
	public void evict(IntervinienteModelosVO intervinienteModelosVO) {
		intervinienteModelosDao.evict(intervinienteModelosVO);
	}
}