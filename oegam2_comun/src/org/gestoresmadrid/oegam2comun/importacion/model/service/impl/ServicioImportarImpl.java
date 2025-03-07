package org.gestoresmadrid.oegam2comun.importacion.model.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.enumerados.TipoDocumentoDuplicado;
import org.gestoresmadrid.core.estacionITV.model.vo.EstacionITVVO;
import org.gestoresmadrid.core.model.enumerados.MotivoBaja;
import org.gestoresmadrid.core.model.enumerados.MotivoDuplicado;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContratoColegiado;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.DgtMunicipioDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.LocalidadDgtDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.PuebloDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.TipoViaDto;
import org.gestoresmadrid.oegam2comun.direccion.view.dto.ViaDto;
import org.gestoresmadrid.oegam2comun.estacionITV.model.service.ServicioEstacionITV;
import org.gestoresmadrid.oegam2comun.importacion.model.service.ServicioImportar;
import org.gestoresmadrid.oegam2comun.paises.view.dto.PaisDto;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersona;
import org.gestoresmadrid.oegam2comun.personas.model.service.ServicioPersonaDireccion;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoBaja;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans.ResultadoImportacionInteve;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioCaracteristicasElectricas;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaDgtDto;
import org.gestoresmadrid.oegam2comun.vehiculo.view.dto.MarcaFabricanteDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.DireccionDto;
import org.gestoresmadrid.oegamComun.interviniente.view.dto.IntervinienteTraficoDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDireccionDto;
import org.gestoresmadrid.oegamComun.persona.view.dto.PersonaDto;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.JefaturaTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.ServicioTraficoDto;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import escrituras.beans.ResultBean;
import trafico.beans.ResumenImportacion;
import trafico.utiles.constantes.ConstantesMensajes;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioImportarImpl implements ServicioImportar {

	private static final long serialVersionUID = 7244370627618772272L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportarImpl.class);

	@Autowired
	private ServicioTramiteTraficoBaja servicioTraficoBaja;

	@Autowired
	private ServicioTramiteTraficoDuplicado servicioTramiteTraficoDuplicado;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioDireccion servicioDireccion;

	@Autowired
	private ServicioVehiculo servicioVehiculo;
	
	@Autowired
	private ServicioEstacionITV servicioEstacionITV;

	@Autowired
	private ServicioPersonaDireccion servicioPersonaDireccion;

	@Autowired
	private ServicioPersona servicioPersona;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioContratoColegiado servicioContratoColegiado;

	@Autowired
	private ServicioCaracteristicasElectricas servicioCaracteristicasElectricas;

	@Autowired
	private ServicioTramiteTrafico servicioTramiteTrafico;

	@Autowired
	private Conversor conversor;

	@Autowired
	private ServicioTasa servicioTasa;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultBean importarBaja(TramiteTrafBajaDto tramiteTrafBajaDto, BigDecimal idUsuario, Boolean tienePermisoBtv, Boolean esSiga) {
		ResultBean result = new ResultBean();
		BigDecimal numExpediente = null;
		String informacion = "";
		try {
			if (tramiteTrafBajaDto.getVehiculoDto() != null) {
				if (servicioContratoColegiado.existeContratoColegiado(tramiteTrafBajaDto.getIdContrato().longValue(), tramiteTrafBajaDto.getNumColegiado())) {
					ResultBean resultVehiculo = procesarVehiculo(tramiteTrafBajaDto.getVehiculoDto(), false, TipoTramiteTrafico.Baja.getValorEnum());
					result.addListaMensajes(resultVehiculo.getListaMensajes());
					if (!resultVehiculo.getError()) {
						if (tramiteTrafBajaDto.getMotivoBaja() != null && !"".equals(tramiteTrafBajaDto.getMotivoBaja())) {
							ResultBean resultIntervinientes = new ResultBean();
							if (tramiteTrafBajaDto.getTitular() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafBajaDto.getTitular(), TipoTramiteTrafico.Baja.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafBajaDto.getRepresentanteTitular() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafBajaDto.getRepresentanteTitular(), TipoTramiteTrafico.Baja.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafBajaDto.getAdquiriente() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafBajaDto.getAdquiriente(), TipoTramiteTrafico.Baja.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafBajaDto.getRepresentanteAdquiriente() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafBajaDto.getRepresentanteAdquiriente(), TipoTramiteTrafico.Baja.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}

							ResultBean resultJefatura = procesarJefatura(tramiteTrafBajaDto.getJefaturaTraficoDto(), tramiteTrafBajaDto.getIdContrato());
							if (resultJefatura.getAttachment("JEFATURA") != null) {
								if (tramiteTrafBajaDto.getJefaturaTraficoDto() == null) {
									tramiteTrafBajaDto.setJefaturaTraficoDto(new JefaturaTraficoDto());
								}
								tramiteTrafBajaDto.getJefaturaTraficoDto().setJefaturaProvincial((String) (resultJefatura.getAttachment("JEFATURA")));
							}

							result.addListaMensajes(resultJefatura.getListaMensajes());

							// Procesar Pais Baja (Validación del pais importado)
							
							if (tienePermisoBtv || esSiga) {
								ResultBean resultPaisBaja = procesarPaisBajaTelematica(tramiteTrafBajaDto.getMotivoBaja(), tramiteTrafBajaDto.getPais());
								result.addListaMensajes(resultPaisBaja.getListaMensajes());
							}

							if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
								for (String mensaje : result.getListaMensajes()) {
									informacion = informacion + mensaje + " ";
								}
								result.setListaMensajes(null);
							}

							tramiteTrafBajaDto.setTipoTramite(TipoTramiteTrafico.Baja.getValorEnum());
							tramiteTrafBajaDto.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
							rellenarVehiculoDesdeBBDD(tramiteTrafBajaDto.getVehiculoDto(), tramiteTrafBajaDto.getNumColegiado());
							numExpediente = servicioTramiteTrafico.crearTramite(tramiteTrafBajaDto.getNumColegiado(), tramiteTrafBajaDto.getTipoTramite(), tramiteTrafBajaDto.getIdContrato(),
									idUsuario, tramiteTrafBajaDto.getFechaAlta().getFechaHora());
							if (numExpediente != null) {
								tramiteTrafBajaDto.setNumExpediente(numExpediente);
								if (esSiga) {
									result = servicioTraficoBaja.guardarTramite(tramiteTrafBajaDto, idUsuario, false, false, esSiga);
								} else {
									result = servicioTraficoBaja.guardarTramite(tramiteTrafBajaDto, idUsuario, false, false, tienePermisoBtv);
								}

								if (result != null && !result.getError()) {
									if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
										if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
											for (String mensaje : result.getListaMensajes()) {
												informacion = informacion + mensaje + " ";
											}
											result.setListaMensajes(new ArrayList<String>());
										}
									}
									if (informacion.isEmpty()) {
										result.setMensaje("Trámite (" + numExpediente + ") guardado.");
									} else {
										result.setMensaje("Trámite (" + numExpediente + ") guardado. Información: " + informacion);
									}
								}
							} else {
								result.setError(true);
								result.addMensajeALista("Ha sucedido un error a la hora de crear el tramite.");
							}
						}
					}
				} else {
					result.setError(true);
					result.addMensajeALista("El contrato no pertenece al colegiado, no se puede importar");
				}
			} else {
				result.setError(true);
				result.addMensajeALista("No existe vehículo");
			}
		} catch (Exception e) {
			log.error("Error a la hora de importar el trámite", e);
			result.setError(true);
			result.setMensaje("Error a la hora de importar el trámite");
		}
		if (result != null && result.getError() && numExpediente != null) {
			try {
				servicioTramiteTrafico.borrar(numExpediente);
			} catch (Exception e) {
				log.error("Error a la hora de borrar el trámite: " + numExpediente, e, numExpediente.toString());
				result.setError(true);
				result.setMensaje("Ha sucedido un error en la importación del trámite");
			}
		}
		return result;
	}

	private void rellenarVehiculoDesdeBBDD(VehiculoDto vehiculoDto, String numColegiado) {
		VehiculoVO vehiculoBBDD;
		if (StringUtils.isNotBlank(vehiculoDto.getMatricula())) {
			vehiculoBBDD = servicioVehiculo.getVehiculoVO(null, vehiculoDto.getNumColegiado(), vehiculoDto.getMatricula(), null, null, EstadoVehiculo.Activo);
		} else if (StringUtils.isNotBlank(vehiculoDto.getNive())) {
			vehiculoBBDD = servicioVehiculo.getVehiculoVO(null, vehiculoDto.getNumColegiado(), null, null, vehiculoDto.getNive(), EstadoVehiculo.Activo);
		} else if (StringUtils.isNotBlank(vehiculoDto.getBastidor())) {
			vehiculoBBDD = servicioVehiculo.getVehiculoVO(null, vehiculoDto.getNumColegiado(), null, vehiculoDto.getBastidor(), null, EstadoVehiculo.Activo);
		} else {
			return;
		}
		if (null != vehiculoBBDD) {
			if (null == vehiculoDto.getFechaMatriculacion()) {
				vehiculoDto.setFechaMatriculacion(new Fecha(vehiculoBBDD.getFechaMatriculacion()));
			}
			if (StringUtils.isBlank(vehiculoDto.getTipoVehiculo())) {
				vehiculoDto.setTipoVehiculo(vehiculoBBDD.getTipoVehiculo());
			}
			if (null == vehiculoDto.getServicioTrafico() || StringUtils.isBlank(vehiculoDto.getServicioTrafico().getIdServicio())) {
				if (null == vehiculoDto.getServicioTrafico()) {
					vehiculoDto.setServicioTrafico(new ServicioTraficoDto());
				}
				vehiculoDto.getServicioTrafico().setIdServicio(vehiculoBBDD.getIdServicio());
			}
			if (StringUtils.isBlank(vehiculoDto.getPesoMma())) {
				vehiculoDto.setPesoMma(vehiculoBBDD.getPesoMma());
			}
			if (null == vehiculoDto.getDireccion()) {
				vehiculoDto.setDireccion(conversor.transform(vehiculoBBDD.getDireccion(), DireccionDto.class));
			}
		}
	}

	@Override
	public ResultBean importarDuplicado(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto, BigDecimal idUsuario) {
		ResultBean result = new ResultBean();
		BigDecimal numExpediente = null;
		String informacion = "";
		try {
			if (tramiteTrafDuplicadoDto.getVehiculoDto() != null) {
				if (servicioContratoColegiado.existeContratoColegiado(tramiteTrafDuplicadoDto.getIdContrato().longValue(), tramiteTrafDuplicadoDto.getNumColegiado())) {
					ResultBean resultVehiculo = procesarVehiculo(tramiteTrafDuplicadoDto.getVehiculoDto(), false, TipoTramiteTrafico.Duplicado.getValorEnum());
					result.addListaMensajes(resultVehiculo.getListaMensajes());
					if (!resultVehiculo.getError()) {
						ResultBean resultIntervinientes = new ResultBean();
						if (tramiteTrafDuplicadoDto.getTitular() != null) {
							resultIntervinientes = procesarInterviniente(tramiteTrafDuplicadoDto.getTitular(), TipoTramiteTrafico.Duplicado.getValorEnum());
							result.addListaMensajes(resultIntervinientes.getListaMensajes());
						}
						if (tramiteTrafDuplicadoDto.getRepresentanteTitular() != null) {
							resultIntervinientes = procesarInterviniente(tramiteTrafDuplicadoDto.getRepresentanteTitular(), TipoTramiteTrafico.Duplicado.getValorEnum());
							result.addListaMensajes(resultIntervinientes.getListaMensajes());
						}
						if (tramiteTrafDuplicadoDto.getCotitular() != null) {
							resultIntervinientes = procesarInterviniente(tramiteTrafDuplicadoDto.getCotitular(), TipoTramiteTrafico.Duplicado.getValorEnum());
							result.addListaMensajes(resultIntervinientes.getListaMensajes());
						}

						ResultBean resultJefatura = procesarJefatura(tramiteTrafDuplicadoDto.getJefaturaTraficoDto(), tramiteTrafDuplicadoDto.getIdContrato());
						if (resultJefatura.getAttachment("JEFATURA") != null) {
							if (tramiteTrafDuplicadoDto.getJefaturaTraficoDto() == null) {
								tramiteTrafDuplicadoDto.setJefaturaTraficoDto(new JefaturaTraficoDto());
							}
							tramiteTrafDuplicadoDto.getJefaturaTraficoDto().setJefaturaProvincial((String) (resultJefatura.getAttachment("JEFATURA")));
						}

						result.addListaMensajes(resultJefatura.getListaMensajes());
						
						ResultBean resultTipoDocumento = procesarTipoDocumento(tramiteTrafDuplicadoDto);
						result.addListaMensajes(resultTipoDocumento.getListaMensajes());
						
						if (tramiteTrafDuplicadoDto.getTasaPermiso() != null || tramiteTrafDuplicadoDto.getTasaFichaTecnica() != null) {
							ResultBean resultTasa = procesarTasa(tramiteTrafDuplicadoDto);
							result.addListaMensajes(resultTasa.getListaMensajes());
						}
						
						if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
							for (String mensaje : result.getListaMensajes()) {
								informacion = informacion + mensaje + " ";
							}
						}

						tramiteTrafDuplicadoDto.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
						tramiteTrafDuplicadoDto.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
						numExpediente = servicioTramiteTrafico.crearTramite(tramiteTrafDuplicadoDto.getNumColegiado(), tramiteTrafDuplicadoDto.getTipoTramite(), tramiteTrafDuplicadoDto
								.getIdContrato(), idUsuario, tramiteTrafDuplicadoDto.getFechaAlta().getFechaHora());
						if (numExpediente != null) {
							tramiteTrafDuplicadoDto.setNumExpediente(numExpediente);
							result = servicioTramiteTraficoDuplicado.guardarTramite(tramiteTrafDuplicadoDto, idUsuario, false, false, false);

							if (result != null && !result.getError()) {
								if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
									if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
										for (String mensaje : result.getListaMensajes()) {
											informacion = informacion + mensaje + " ";
										}
										result.setListaMensajes(new ArrayList<String>());
									}
								}
								if (informacion.isEmpty()) {
									result.setMensaje("Trámite (" + numExpediente + ") guardado.");
								} else {
									result.setMensaje("Trámite (" + numExpediente + ") guardado. Información: " + informacion);
								}
							}
						} else {
							result.setError(true);
							result.addMensajeALista("Ha sucedido un error a la hora de crear el trámite.");
						}
					}
				} else {
					result.setError(true);
					result.addMensajeALista("El contrato no pertenece al colegiado, no se puede importar");
				}
			} else {
				result.setError(true);
				result.addMensajeALista("No existe vehículo");
			}
		} catch (Exception e) {
			log.error("Error a la hora de importar el trámite", e);
			result.setError(true);
			result.setMensaje("Error a la hora de importar el trámite");
		}
		if (result != null && result.getError() && numExpediente != null) {
			try {
				servicioTramiteTrafico.borrar(numExpediente);
			} catch (Exception e) {
				log.error("Error a la hora de borrar el trámite: " + numExpediente, e, numExpediente.toString());
				result.setError(true);
				result.setMensaje("Ha sucedido un error en la importación del trámite");
			}
		}
		return result;
	}

	@Override
	public ResultBean importarMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario, Boolean tienePermisoLiberarEEFF, Boolean esAdmin) throws OegamExcepcion {
		ResultBean result = new ResultBean();
		BigDecimal numExpediente = null;
		String informacion = "";
		try {
			if (servicioContratoColegiado.existeContratoColegiado(tramiteTrafMatrDto.getIdContrato().longValue(), tramiteTrafMatrDto.getNumColegiado())) {
				result = validacionesMatriculacion(tramiteTrafMatrDto);
				if (result != null && !result.getError()) {
					result = mensajesInformacionMatriculacion(tramiteTrafMatrDto);
					if (tramiteTrafMatrDto.getVehiculoDto() != null) {
						ResultBean resultVehiculo = procesarVehiculo(tramiteTrafMatrDto.getVehiculoDto(), true, TipoTramiteTrafico.Matriculacion.getValorEnum());
						result.addListaMensajes(resultVehiculo.getListaMensajes());
						if (!resultVehiculo.getError()) {
							ResultBean resultIntervinientes = new ResultBean();
							if (tramiteTrafMatrDto.getTitular() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafMatrDto.getTitular(), TipoTramiteTrafico.Matriculacion.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafMatrDto.getRepresentanteTitular() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafMatrDto.getRepresentanteTitular(), TipoTramiteTrafico.Matriculacion.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafMatrDto.getRenting() != null && tramiteTrafMatrDto.getRenting() && tramiteTrafMatrDto.getArrendatario() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafMatrDto.getArrendatario(), TipoTramiteTrafico.Matriculacion.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafMatrDto.getRenting() != null && tramiteTrafMatrDto.getRenting() && tramiteTrafMatrDto.getRepresentanteArrendatario() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafMatrDto.getRepresentanteArrendatario(), TipoTramiteTrafico.Matriculacion.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}
							if (tramiteTrafMatrDto.getConductorHabitual() != null) {
								resultIntervinientes = procesarInterviniente(tramiteTrafMatrDto.getConductorHabitual(), TipoTramiteTrafico.Matriculacion.getValorEnum());
								result.addListaMensajes(resultIntervinientes.getListaMensajes());
							}

							ResultBean resultJefatura = procesarJefatura(tramiteTrafMatrDto.getJefaturaTraficoDto(), tramiteTrafMatrDto.getIdContrato());
							if (resultJefatura.getAttachment("JEFATURA") != null) {
								if (tramiteTrafMatrDto.getJefaturaTraficoDto() == null) {
									tramiteTrafMatrDto.setJefaturaTraficoDto(new JefaturaTraficoDto());
								}
								tramiteTrafMatrDto.getJefaturaTraficoDto().setJefaturaProvincial((String) (resultJefatura.getAttachment("JEFATURA")));
							}

							result.addListaMensajes(resultJefatura.getListaMensajes());

							if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
								for (String mensaje : result.getListaMensajes()) {
									informacion = informacion + mensaje + " ";
								}
								result.setListaMensajes(null);
							}

							tramiteTrafMatrDto.setTipoTramite(TipoTramiteTrafico.Matriculacion.getValorEnum());
							tramiteTrafMatrDto.setFechaAlta(utilesFecha.getFechaHoraActualLEG());
							numExpediente = servicioTramiteTrafico.crearTramite(tramiteTrafMatrDto.getNumColegiado(), tramiteTrafMatrDto.getTipoTramite(), tramiteTrafMatrDto.getIdContrato(),
									idUsuario, tramiteTrafMatrDto.getFechaAlta().getFechaHora());
							if (numExpediente != null) {
								tramiteTrafMatrDto.setNumExpediente(numExpediente);
								result = servicioTramiteTraficoMatriculacion.guardarTramite(tramiteTrafMatrDto, tramiteTrafMatrDto.getIvtmMatriculacionDto(), idUsuario, esAdmin,
										tienePermisoLiberarEEFF, false);

								if (result != null && !result.getError()) {
									if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
										if (result.getListaMensajes() != null && result.getListaMensajes().size() > 0) {
											for (String mensaje : result.getListaMensajes()) {
												informacion = informacion + mensaje + " ";
											}
											result.setListaMensajes(new ArrayList<String>());
										}
									}
									if (informacion.isEmpty()) {
										result.setMensaje("Trámite (" + numExpediente + ") guardado.");
									} else {
										result.setMensaje("Trámite (" + numExpediente + ") guardado. Información: " + informacion);
									}
								}
							} else {
								result.setError(true);
								result.addMensajeALista("Ha sucedido un error a la hora de crear el tramite.");
							}
						}
					} else {
						result.setError(true);
						result.addMensajeALista("No existe vehículo");
					}
				}
			} else {
				result.setError(true);
				result.addMensajeALista("El contrato no pertenece al colegiado, no se puede importar");
			}
		} catch (Exception e) {
			log.error("Error a la hora de importar el trámite", e);
			result.setError(true);
			result.setMensaje("Error a la hora de importar el trámite");
		}
		if (result != null && result.getError() && numExpediente != null) {
			try {
				servicioTramiteTrafico.borrar(numExpediente);
			} catch (Exception e) {
				log.error("Error a la hora de borrar el trámite: " + numExpediente, e, numExpediente.toString());
				result.setError(true);
				result.setMensaje("Ha sucedido un error en la importación del trámite");
			}
		}
		return result;
	}

	private ResultBean validacionesMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		ResultBean result = new ResultBean();
		if (tramiteTrafMatrDto.getCem() != null && !tramiteTrafMatrDto.getCem().isEmpty() && tramiteTrafMatrDto.getExentoCem()) {
			result.setError(true);
			result.setMensaje("El trámite no se ha guardado. No se puede indicar CEM y Exento_CEM a la vez. Por favor modifique uno.");
		}
		return result;
	}

	private ResultBean mensajesInformacionMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto) {
		ResultBean result = new ResultBean();
		if (tramiteTrafMatrDto.getVehiculoDto().getBastidor() != null && !tramiteTrafMatrDto.getVehiculoDto().getBastidor().isEmpty() && tramiteTrafMatrDto.getVehiculoDto().getNumColegiado() != null
				&& !tramiteTrafMatrDto.getVehiculoDto().getNumColegiado().isEmpty()) {
			if (servicioVehiculo.isBastidorDuplicado(tramiteTrafMatrDto.getVehiculoDto().getBastidor(), tramiteTrafMatrDto.getVehiculoDto().getNumColegiado())) {
				result.addMensajeALista("-Vehículo: El bastidor " + tramiteTrafMatrDto.getVehiculoDto().getBastidor() + " está duplicado para su número de colegiado. ");
				log.error("El " + tramiteTrafMatrDto.getVehiculoDto().getNumColegiado() + " ha dado de alta el siguiente bastidor duplicado " + tramiteTrafMatrDto.getVehiculoDto().getBastidor());
			}
		}
		if (tramiteTrafMatrDto.getVehiculoDto().getMotivoItv() != null && !tramiteTrafMatrDto.getVehiculoDto().getMotivoItv().isEmpty() && tramiteTrafMatrDto.getVehiculoDto().getFechaItv() != null) {
			if ("E".equals(tramiteTrafMatrDto.getVehiculoDto().getMotivoItv())) {
				tramiteTrafMatrDto.getVehiculoDto().setFechaItv(null);
				result.addMensajeALista("-Vehiculo: " + ConstantesMensajes.MENSAJE_TIPO_INSPECCION_ITV_AVISOFECHA);
			}
		}
		if (tramiteTrafMatrDto.getVehiculoDto().getModelo() != null && !tramiteTrafMatrDto.getVehiculoDto().getModelo().isEmpty() && tramiteTrafMatrDto.getVehiculoDto().getModelo().length() > 21) {
			tramiteTrafMatrDto.getVehiculoDto().setModelo(tramiteTrafMatrDto.getVehiculoDto().getModelo().substring(0, 21));
			result.addMensajeALista("-Vehiculo: El modelo se ha acortado a 22 caracteres, si desea modificarlo puede hacerlo desde el propio trámite. ");
		}
		return result;
	}

	private ResultBean procesarVehiculo(VehiculoDto vehiculo, Boolean versionMatw, String tipoTramite) {
		ResultBean result = new ResultBean();

		if (StringUtils.isNotBlank(vehiculo.getMatricula()) || (StringUtils.isNotBlank(vehiculo.getBastidor()) && vehiculo.getBastidor().length() == 17) || (StringUtils.isNotBlank(vehiculo
				.getBastidor()) && vehiculo.getBastidor().length() >= 3 && "true".equals(vehiculo.getFichaTecnicaRD750().toString()))) {

			if (vehiculo.getDireccion() != null) {
				String mensaje = procesarDireccion(vehiculo.getDireccion(), tipoTramite);
				if (mensaje != null && !mensaje.isEmpty()) {
					result.addMensajeALista("La dirección del vehículo: " + mensaje + ". ");
				}
			}

			if (vehiculo.getMarcaSinEditar() != null) {
				MarcaDgtDto marcaDgt = servicioVehiculo.getMarcaDgt(null, vehiculo.getMarcaSinEditar(), versionMatw);
				if (marcaDgt == null) {
					result.addMensajeALista("- La marca no esta reconocida por DGT. ");
				} else {
					vehiculo.setCodigoMarca(marcaDgt.getCodigoMarca().toString());
				}
			}

			if (versionMatw) {

				if (StringUtils.isNotBlank(vehiculo.getEstacionItv())) {
					List<EstacionITVVO> listadoPosibleEstacion = servicioEstacionITV
							.getEstacion(vehiculo.getEstacionItv());
					if (listadoPosibleEstacion == null || listadoPosibleEstacion.isEmpty()) {
						vehiculo.setEstacionItv(null);
						result.addMensajeALista("- La estación ITV indicada no es válida. ");
					}
				}
				result = validarMultifasicos(vehiculo);
				if (result != null && !result.getError()) {
					result = validarElectricos(vehiculo);
				}
			}

		} else {
			result.setError(true);
			result.addMensajeALista("- El vehículo no tiene matrícula ni número de bastidor válido, no se puede guardar. ");
		}

		return result;
	}

	private ResultBean validarMultifasicos(VehiculoDto vehiculo) {
		ResultBean result = new ResultBean();
		if (vehiculo.getCodigoMarcaBase() != null && !vehiculo.getCodigoMarcaBase().isEmpty()) {
			MarcaDgtDto marca = servicioVehiculo.getMarcaDgt(vehiculo.getCodigoMarcaBase(), null, true);
			if (marca == null) {
				result.setError(true);
				result.addMensajeALista("La Marca Base no existe. ");
			}
		}

		if (StringUtils.isBlank(vehiculo.getNive())) {
			if ((vehiculo.getCodigoMarcaBase() == null || "".equals(vehiculo.getCodigoMarcaBase())) && vehiculo.getFabricanteBase() != null) {
				result.setError(true);
				result.addMensajeALista("Debe informar la Marca Base si el Fabricante Base esta relleno. ");
			} else if ((vehiculo.getFabricanteBase() == null || "".equals(vehiculo.getFabricanteBase())) && vehiculo.getCodigoMarcaBase() != null) {
				result.setError(true);
				result.addMensajeALista("Debe informar el Fabricante Base si la Marca Base esta rellena. ");
			}
	
			if ((vehiculo.getCodigoMarcaBase() == null || vehiculo.getCodigoMarcaBase().isEmpty()) && !(vehiculo.getFabricanteBase() == null || vehiculo.getFabricanteBase().isEmpty())) {
				result.setError(true);
				result.addMensajeALista("La Marca Base y el Fabricante Base debe informarse en conjunto. ");
			}
		}
		
		if (vehiculo.getFabricanteBase() != null && !vehiculo.getFabricanteBase().isEmpty() && !"ND".equals(vehiculo.getFabricanteBase()) && vehiculo.getCodigoMarcaBase() != null && !vehiculo
				.getCodigoMarcaBase().isEmpty()) {
			MarcaFabricanteDto marcaFabricante = servicioVehiculo.obtenerMarcaFabricante(vehiculo.getFabricanteBase(), vehiculo.getCodigoMarcaBase());
			if (marcaFabricante == null) {
				result.setError(true);
				result.addMensajeALista("El fabricante base no corresponde con la marca base. Si desconoce el fabricante ponga ND. ");
			}
		}
		if (vehiculo.getTipoTarjetaITV() != null && !"A".equals(vehiculo.getTipoTarjetaITV().substring(0, 1)) && !"D".equals(vehiculo.getTipoTarjetaITV().substring(0, 1))) {
			if (vehiculo.getCodigoMarcaBase() != null && !vehiculo.getCodigoMarcaBase().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("La Marca Base no permitida para este tipo de tarjeta. ");
			}
			if (vehiculo.getFabricanteBase() != null && !vehiculo.getFabricanteBase().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("El Fabricante Base no permitido para este tipo de tarjeta. ");
			}
			if (vehiculo.getTipoBase() != null && !vehiculo.getTipoBase().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("El Tipo Base no permitido para este tipo de tarjeta. ");
			}
			if (vehiculo.getVersionBase() != null && !vehiculo.getVersionBase().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("La Version Base no permitida para este tipo de tarjeta");
			}
			if (vehiculo.getNumHomologacionBase() != null && !vehiculo.getNumHomologacionBase().isEmpty()) {
				result.setError(true);
				result.addMensajeALista("El Numero de Homologacion Base no permitida para este tipo de tarjeta. ");
			}
			if (vehiculo.getMomBase() != null) {
				result.setError(true);
				result.addMensajeALista("El MOM Base no permitido para este tipo de tarjeta. ");
			}
		}
		return result;
	}

	private ResultBean validarElectricos(VehiculoDto vehiculo) {
		ResultBean result = new ResultBean();

		if (vehiculo.getConsumo() == null && "E".equals(vehiculo.getCarburante()) && ("M1".equals(vehiculo.getIdDirectivaCee()) || "N1".equals(vehiculo.getIdDirectivaCee()))) {
			result.setError(true);
			result.addMensajeALista("Consumo KW/H. ");
		}

		if (vehiculo.getConsumo() != null && vehiculo.getConsumo().intValue() > 0 && !"E".equals(vehiculo.getCarburante())) {
			result.setError(true);
			result.addMensajeALista("Informe Consumo KW/H solo para vehículos eléctricos. ");
		}

		if (vehiculo.getCategoriaElectrica() != null) {
			if ("E".equals(vehiculo.getCarburante()) || "G".equals(vehiculo.getCarburante()) || "D".equals(vehiculo.getCarburante()) || "G/E".equals(vehiculo.getCarburante())) {
				if (!"BEV".equals(vehiculo.getCategoriaElectrica()) && !"PHEV".equals(vehiculo.getCategoriaElectrica()) && !"REEV".equals(vehiculo.getCategoriaElectrica()) &&
						!"HEV".equals(vehiculo.getCategoriaElectrica()) && !"FCEV".equals(vehiculo.getCategoriaElectrica())) {
					result.setError(true);
					result.addMensajeALista("Informe Consumo KW/H solo para vehículos eléctricos. ");
				}
			} else {
				result.setError(true);
				result.addMensajeALista("Categoría eléctrica solo para vehículos eléctricos. ");
			}
		} else {
			if ("E".equals(vehiculo.getCarburante()) && ("M".equals(vehiculo.getIdDirectivaCee().substring(0, 1)) || "N".equals(vehiculo.getIdDirectivaCee().substring(0, 1)))) {
				result.setError(true);
				result.addMensajeALista("Categoría eléctrica obligatoria con combustible eléctrico. ");
			}
			if (vehiculo.getAutonomiaElectrica() != null) {
				result.setError(true);
				result.addMensajeALista("Autonomía eléctrica solo permitida cuando hay categoría eléctrica. ");
			}
		}

		if (vehiculo.getAutonomiaElectrica() != null) {
			if (!"E".equals(vehiculo.getCarburante())) {
				result.setError(true);
				result.addMensajeALista("Autonomía eléctrica solo para vehículos eléctricos. ");
			}
			if (vehiculo.getAutonomiaElectrica().intValue() == 0) {
				result.setError(true);
				result.addMensajeALista("Autonomía eléctrica no puede ser 0. Si fuese 0, debe dejarse en blanco. ");
			}
		}

		if (vehiculo.getCarburante() != null && "E".equals(vehiculo.getCarburante())) {
			int numCoincidentes = servicioCaracteristicasElectricas.numeroCoincidentes(vehiculo.getCodigoMarca(), vehiculo.getModelo(), vehiculo.getTipoItv(), vehiculo.getVersion(), vehiculo
					.getVariante(), vehiculo.getBastidor(), vehiculo.getCategoriaElectrica(), vehiculo.getConsumo(), vehiculo.getAutonomiaElectrica());
			if (numCoincidentes == 0) {
				result.addMensajeALista("Las características del vehículo eléctrico no coinciden con ningún vehículo según circular del 04/07/2014. ");
			}

		}

		return result;
	}

	private ResultBean procesarInterviniente(IntervinienteTraficoDto intervinienteTrafico, String tipoTramite) {
		ResultBean result = new ResultBean();
		if (intervinienteTrafico.getPersona() != null) {
			if (intervinienteTrafico.getNifInterviniente() != null && !intervinienteTrafico.getNifInterviniente().isEmpty()) {
				if (!comprobarDireccionCorrecta(intervinienteTrafico.getDireccion(), tipoTramite) && (TipoInterviniente.Titular.getValorEnum().equals(intervinienteTrafico.getTipoInterviniente())
						|| TipoInterviniente.Adquiriente.getValorEnum().equals(intervinienteTrafico.getTipoInterviniente()) || TipoInterviniente.CotitularTransmision.getValorEnum().equals(
								intervinienteTrafico.getTipoInterviniente()))) {

					ResultBean resultPersonaDireccion = servicioPersonaDireccion.buscarPersonaDireccionDto(intervinienteTrafico.getNumColegiado(), intervinienteTrafico.getNifInterviniente());
					if (!resultPersonaDireccion.getError()) {
						PersonaDireccionDto personaDireccion = (PersonaDireccionDto) resultPersonaDireccion.getAttachment(ServicioPersonaDireccion.PERSONADIRECCION);
						if (personaDireccion != null && personaDireccion.getDireccion() != null) {
							intervinienteTrafico.setDireccion(personaDireccion.getDireccion());
						}
					}
					result.addMensajeALista("- " + TipoInterviniente.convertirTexto(intervinienteTrafico.getTipoInterviniente())
							+ ": sin dirección o dirección inválida, se asocia la dirección principal si la tiene. ");
				} else {
					String mensaje = procesarDireccion(intervinienteTrafico.getDireccion(), tipoTramite);
					if (mensaje != null && !mensaje.isEmpty()) {
						result.addMensajeALista("- " + TipoInterviniente.convertirTexto(intervinienteTrafico.getTipoInterviniente()) + ": " + mensaje);
					}
				}
				ResultBean resultPersona = procesarPersona(intervinienteTrafico.getPersona());
				result.addListaMensajes(resultPersona.getListaMensajes());
			}
		}
		return result;
	}

	private ResultBean procesarJefatura(JefaturaTraficoDto jefaturaTrafico, BigDecimal idContrato) {
		ResultBean result = new ResultBean();
		ContratoDto contrato = servicioContrato.getContratoDto(idContrato);
		if (contrato != null && contrato.getJefaturaTraficoDto() != null) {
			if (jefaturaTrafico == null || jefaturaTrafico.getJefaturaProvincial() == null || "".equals(jefaturaTrafico.getJefaturaProvincial())) {
				result.addAttachment("JEFATURA", contrato.getJefaturaTraficoDto().getJefaturaProvincial());
			} else if (!contrato.getJefaturaTraficoDto().getJefaturaProvincial().equals(jefaturaTrafico.getJefaturaProvincial())) {
				result.addMensajeALista("- La jefatura del trámite es diferente a la jefatura del contrato. ");
			}
		}
		return result;
	}
	
	private ResultBean procesarTasa(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto) {
		ResultBean result = new ResultBean();
		boolean tasaCorrecta;
		String tasaPermiso = tramiteTrafDuplicadoDto.getTasaPermiso();
		String tasaFichaTecnica = tramiteTrafDuplicadoDto.getTasaFichaTecnica();
		if (StringUtils.isNotBlank(tasaFichaTecnica)) {
			if (TipoDocumentoDuplicado.TARJETA_ITV.getValorEnum().equals(tramiteTrafDuplicadoDto.getTipoDocumento())
					|| TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum()
							.equals(tramiteTrafDuplicadoDto.getTipoDocumento())) {
				tasaCorrecta = servicioTasa.comprobarTasa(tasaFichaTecnica, null);
				if (tasaCorrecta) {
					TasaDto tasa = new TasaDto();
					tasa.setCodigoTasa(tasaFichaTecnica);
					tasa.setTipoTasa(TipoTasa.CuatroUno.getValorEnum());
					tramiteTrafDuplicadoDto.setTasa(tasa);
				} else {
					result.addMensajeALista("- La Tasa Ficha Técnica:" + tasaFichaTecnica
							+ " no se guardará al estar aplicada o no existir.");
					tramiteTrafDuplicadoDto.setTasaFichaTecnica(null);
				}
			} else {
				result.addMensajeALista("- La Tasa Ficha Técnica:" + tasaFichaTecnica
						+ " no se aplicará porque no se ha solicitado su documento.");
				tramiteTrafDuplicadoDto.setTasaFichaTecnica(null);
			}
		}
		if (StringUtils.isNotBlank(tasaPermiso)) {
			if (TipoDocumentoDuplicado.PERMISO_CIRCULACION.getValorEnum()
					.equals(tramiteTrafDuplicadoDto.getTipoDocumento())
					|| TipoDocumentoDuplicado.PERMISO_CIRCULACION_ITV.getValorEnum()
							.equals(tramiteTrafDuplicadoDto.getTipoDocumento())) {

				tasaCorrecta = servicioTasa.comprobarTasa(tasaPermiso, null);
				if (tasaCorrecta) {
					TasaDto tasa = new TasaDto();
					tasa.setCodigoTasa(tasaPermiso);
					tasa.setTipoTasa(TipoTasa.CuatroCuatro.getValorEnum());
					tramiteTrafDuplicadoDto.setTasa(tasa);
				} else {
					result.addMensajeALista(
							"- La Tasa Permiso:" + tasaPermiso + " no se guardará al estar aplicada o no existir.");
					tramiteTrafDuplicadoDto.setTasaPermiso(null);
				}
			} else {
				result.addMensajeALista("- La Tasa Permiso:" + tasaPermiso
						+ " no se aplicará porque no se ha solicitado su documento.");
				tramiteTrafDuplicadoDto.setTasaPermiso(null);
			}
		}
		return result;
	}
	
	private ResultBean procesarTipoDocumento(TramiteTrafDuplicadoDto tramiteTrafDuplicadoDto) {
		ResultBean result = new ResultBean();

		String mensajeError = "- No se ha guardado el Tipo de Documento a solicitar a DGT porque el motivo del duplicado no es "
				+ MotivoDuplicado.Deter.getNombreEnum() + ", " + MotivoDuplicado.Extrv.getNombreEnum() + " o "
				+ MotivoDuplicado.Sustr.getNombreEnum() + ".";

		if ((MotivoDuplicado.CambD.getValorEnum().equals(tramiteTrafDuplicadoDto.getMotivoDuplicado())
				|| MotivoDuplicado.CambDCond.getValorEnum().equals(tramiteTrafDuplicadoDto.getMotivoDuplicado()))) {

			tramiteTrafDuplicadoDto.setTipoDocumento(null);
			result.addMensajeALista(mensajeError);
		}
		return result;
	}


	private ResultBean procesarPersona(PersonaDto persona) {
		ResultBean result = new ResultBean();
		PersonaDto personaBBDD = null;

		if (persona.getFechaCaducidadNif() == null) {
			personaBBDD = servicioPersona.getPersona(persona.getNif(), persona.getNumColegiado());
			if (personaBBDD != null) {
				persona.setFechaCaducidadNif(personaBBDD.getFechaCaducidadNif());
			}
		}

		if (persona.getFechaCaducidadAlternativo() == null) {
			if (personaBBDD == null) {
				personaBBDD = servicioPersona.getPersona(persona.getNif(), persona.getNumColegiado());
			}
			if (personaBBDD != null) {
				persona.setFechaCaducidadAlternativo(personaBBDD.getFechaCaducidadAlternativo());
			}
		}

		if (persona.getTipoDocumentoAlternativo() == null || persona.getTipoDocumentoAlternativo().isEmpty()) {
			if (personaBBDD == null) {
				personaBBDD = servicioPersona.getPersona(persona.getNif(), persona.getNumColegiado());
			}
			if (personaBBDD != null) {
				persona.setTipoDocumentoAlternativo(personaBBDD.getTipoDocumentoAlternativo());
			}
		}

		if (persona.getApellido1RazonSocial() == null || persona.getApellido1RazonSocial().isEmpty()) {
			if (personaBBDD == null) {
				personaBBDD = servicioPersona.getPersona(persona.getNif(), persona.getNumColegiado());
			}
			if (personaBBDD != null) {
				persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
			}
		}

		return result;
	}

	private String procesarDireccion(DireccionDto direccion, String tipoTramite) {
		String mensaje = "";

		if (direccion != null && direccion.getIdProvincia() != null && !direccion.getIdProvincia().isEmpty()) {
			if (direccion.getIdTipoDgt() != null && !direccion.getIdTipoDgt().isEmpty() && (direccion.getIdTipoVia() == null || direccion.getIdTipoVia().isEmpty())) {
				TipoViaDto tipoVia = servicioDireccion.getTipoViaDgt(direccion.getIdTipoDgt());
				if (tipoVia == null) {
					mensaje += "- No existe el tipo de vía. ";
					direccion.setIdTipoVia(".");
				} else {
					direccion.setIdTipoVia(tipoVia.getIdTipoVia());
				}
			}

			if (direccion.getMunicipio() != null && (direccion.getIdMunicipio() == null || direccion.getIdMunicipio().isEmpty())) {
				DgtMunicipioDto dgtMunicipio = servicioDireccion.getDgtMunicipio(direccion.getIdProvincia(), direccion.getMunicipio());
				if (dgtMunicipio == null) {
					mensaje += "- El municipio es obligatorio o no es válido para la DGT. ";
				} else {
					direccion.setIdMunicipio(dgtMunicipio.getCodigoIne());
				}
			}

			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
				if (direccion.getCodPostalCorreos() == null || direccion.getCodPostalCorreos().isEmpty()) {
					if (direccion.getPuebloLit() != null && direccion.getIdMunicipio() != null) {
						String codigoPostal = servicioDireccion.obtenerCodigoPostalSiUnico(direccion.getPuebloLit(), direccion.getMunicipio());
						if (codigoPostal == null || codigoPostal.isEmpty()) {
							mensaje += "- El código postal es obligatorio. ";
						} else {
							direccion.setCodPostalCorreos(codigoPostal);
							mensaje += "- Para este pueblo el código postal que le correspone es: " + codigoPostal + ". ";
						}
					} else {
						mensaje += "- El código postal es obligatorio. ";
					}
				}
				if (direccion.getPuebloLit() != null) {
					LocalidadDgtDto localidadDgtDto = servicioDireccion.getLocalidadDgt(direccion.getCodPostalCorreos(), direccion.getPuebloLit());
					if (localidadDgtDto == null) {
						mensaje += " - Pueblo. El pueblo no está admitido, no se guardará.";
					} else {
						direccion.setPuebloCorreos(localidadDgtDto.getLocalidad());
					}
				} else {
					mensaje += "- El pueblo es obligatorio.";
				}
			} else {
				if (direccion.getCodPostal() == null || direccion.getCodPostal().isEmpty()) {
					if (direccion.getIdMunicipio() != null && !direccion.getIdMunicipio().isEmpty()) {
						String codigoPostal = servicioDireccion.obtenerCodigoPostal(direccion.getIdMunicipio(), direccion.getIdProvincia());
						if (codigoPostal == null || codigoPostal.isEmpty()) {
							mensaje += "- El código postal es obligatorio. ";
						} else {
							direccion.setCodPostal(codigoPostal);
							mensaje += "- Para este pueblo el código postal que le correspone es: " + codigoPostal + ". ";
						}
					} else {
						mensaje += "- El código postal es obligatorio. ";
					}
				}
				if (direccion.getPuebloLit() != null) {
					PuebloDto pueblo = servicioDireccion.getPueblo(direccion.getIdProvincia(), direccion.getIdMunicipio(), direccion.getPuebloLit());
					if (pueblo == null) {
						mensaje += "- El pueblo no es del callejero INE. ";
					} else {
						direccion.setPueblo(pueblo.getPueblo());
					}
				} else {
					mensaje += "- El pueblo es obligatorio.";
				}
			}

			if (direccion.getVia() != null) {
				ViaDto via = null;
				if (".".equals(direccion.getIdTipoVia())) {
					via = servicioDireccion.getVia(direccion.getIdProvincia(), direccion.getIdMunicipio(), null, direccion.getVia());
				} else {
					via = servicioDireccion.getVia(direccion.getIdProvincia(), direccion.getIdMunicipio(), direccion.getIdTipoVia(), direccion.getVia());
				}
				if (via == null) {
					mensaje += "- La vía no es del callejero INE, no se podrá procesar telemáticamente. ";
					direccion.setNombreVia(direccion.getVia().toUpperCase());
				} else {
					direccion.setNombreVia(via.getVia().toUpperCase());
				}
			}
		}

		return mensaje;
	}

	private boolean comprobarDireccionCorrecta(DireccionDto direccion, String tipoTramite) {
		if (direccion == null) {
			return false;
		} else if (direccion.getIdProvincia() == null || direccion.getIdProvincia().isEmpty()) {
			return false;
		} else if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			if (direccion.getVia() == null || direccion.getVia().isEmpty()) {
				return false;
			} else if (direccion.getIdTipoDgt() == null || direccion.getIdTipoDgt().isEmpty()) {
				return false;
			} else if (direccion.getMunicipio() == null || direccion.getMunicipio().isEmpty()) {
				return false;
			}
		} else if (!TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tipoTramite)) {
			if ((direccion.getNombreVia() == null || direccion.getNombreVia().isEmpty()) && (direccion.getVia() == null || direccion.getVia().isEmpty())) {
				return false;
			} else if ((direccion.getIdTipoVia() == null || direccion.getIdTipoVia().isEmpty()) && (direccion.getIdTipoDgt() == null || direccion.getIdTipoDgt().isEmpty())) {
				return false;
			} else if ((direccion.getIdMunicipio() == null || direccion.getIdMunicipio().isEmpty()) && (direccion.getMunicipio() == null || direccion.getMunicipio().isEmpty())) {
				return false;
			}
		}
		return true;
	}

	private ResultBean procesarPaisBajaTelematica(String motivoBaja, PaisDto pais) {
		ResultBean result = new ResultBean();

		if ((motivoBaja != null && !"".equals(motivoBaja))) {
			if (motivoBaja.equals(MotivoBaja.DefE.getValorEnum())) {
				if (pais.getIdPais() != null && pais.getTipoPais().equals(BigDecimal.ONE)) {
					result.setError(true);
					result.addMensajeALista("El país para Baja por Exportación no es correcto.");
				}
			}else if (motivoBaja.equals(MotivoBaja.DefTC.getValorEnum())) {
				if (pais == null || pais != null && pais.getIdPais() != null && pais.getTipoPais().equals(BigDecimal.ZERO)) {
					result.setError(true);
					result.addMensajeALista("El país para Baja por Tránsito comunitario no es correcto.");
				}
			}
		} else {
			result.setError(true);
			result.addMensajeALista("La baja no tiene motivo seleccionado, no se puede guardar");
		}
		return result;
	}

	private boolean comprobar(ResultadoImportacionInteve resultado) {
		boolean salida = false;
		for (ResumenImportacion resumen : resultado.getResumen()) {
			if (resumen.getGuardadosMal() > 0) {
				salida = true;
				resultado.setError(Boolean.TRUE);
			}
		}
		return salida;
	}

	public String validarTasa(String codTasa, int contLineas, BigDecimal idContrato) {
		String mensaje = null;
		if (codTasa == null || codTasa.isEmpty()) {
			mensaje = "La línea " + contLineas + " del fichero no contiene una tasa para la solicitud.";
		} else if (codTasa.length() > 12) {
			mensaje = "La línea " + contLineas + " del fichero no contiene una tasa con un formato válido para la solicitud.";
		} else {
			TasaDto tasaDto = servicioTasa.getTasaCodigoTasa(codTasa);
			if (tasaDto != null) {
				if (tasaDto.getFechaAsignacion() != null) {
					mensaje = "La línea " + contLineas + " del fichero contiene una tasa que ya está asignada a otros trámites.";
				} else if (idContrato.compareTo(tasaDto.getContrato().getIdContrato()) != 0) {
					mensaje = "La línea " + contLineas + " del fichero contiene una tasa que no está asociada al contrato.";
				} else if (!TipoTasa.CuatroUno.getValorEnum().equals(tasaDto.getTipoTasa())) {
					mensaje = "La línea " + contLineas + " del fichero no contiene una tasa del tipo correspondiente.";
				}
			} else {
				mensaje = "La línea " + contLineas + " del fichero no contiene una tasa válida para la solicitud.";
			}
		}
		return mensaje;
	}

}