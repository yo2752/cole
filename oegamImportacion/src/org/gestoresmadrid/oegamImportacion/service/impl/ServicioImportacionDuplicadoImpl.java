package org.gestoresmadrid.oegamImportacion.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.DireccionVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.direccion.model.vo.ProvinciaVO;
import org.gestoresmadrid.core.enumerados.TipoCreacion;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoPK;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.MotivoDuplicado;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.personas.model.enumerados.SexoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.SubtipoPersona;
import org.gestoresmadrid.core.personas.model.enumerados.TipoPersona;
import org.gestoresmadrid.core.personas.model.vo.PersonaPK;
import org.gestoresmadrid.core.personas.model.vo.PersonaVO;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafDuplicadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegamConversiones.conversion.Conversion;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoValidacionImporBean;
import org.gestoresmadrid.oegamImportacion.bean.ResumenImportacionBean;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioDireccionImportacion;
import org.gestoresmadrid.oegamImportacion.direccion.service.ServicioProvinciaImportacion;
import org.gestoresmadrid.oegamImportacion.persona.service.ServicioPersonaImportacion;
import org.gestoresmadrid.oegamImportacion.schemas.utiles.ConversionFormatoImpGADuplicado;
import org.gestoresmadrid.oegamImportacion.service.ServicioImportacionDuplicado;
import org.gestoresmadrid.oegamImportacion.service.ServicioValidacionesImportacion;
import org.gestoresmadrid.oegamImportacion.tasa.service.ServicioTasaImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIntervinienteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioTramiteTraficoImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesAnagramaImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesConversionesImportacion;
import org.gestoresmadrid.oegamImportacion.utiles.UtilidadesNIFValidatorImportacion;
import org.gestoresmadrid.oegamImportacion.vehiculo.service.ServicioVehiculoImportacion;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jxl.Sheet;
import jxl.Workbook;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioImportacionDuplicadoImpl implements ServicioImportacionDuplicado {

	private static final long serialVersionUID = -4665408707968795418L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioImportacionDuplicadoImpl.class);

	@Autowired
	ConversionFormatoImpGADuplicado conversionFormatoGADuplicado;

	@Autowired
	ServicioValidacionesImportacion servicioValidaciones;

	@Autowired
	ServicioTramiteTraficoImportacion servicioTramiteTrafico;

	@Autowired
	ServicioVehiculoImportacion servicioVehiculo;
	
	@Autowired
	ServicioTasaImportacion servicioTasa;

	@Autowired
	ServicioIntervinienteTraficoImportacion servicioIntervinienteTrafico;
	
	@Autowired
	Conversion conversion;

	@Autowired
	UtilesFecha utilesFecha;
	
	@Autowired 
	ServicioPersonaImportacion servicioPersona;
	
	@Autowired
	UtilidadesConversionesImportacion utilidadesConversiones;
	
	@Autowired
	ServicioDireccionImportacion servicioDireccion;
	
	@Autowired
	ServicioProvinciaImportacion servicioProvincia;
	
	@Autowired
	UtilidadesNIFValidatorImportacion utilidadesNIFValidator;
	
	@Autowired
	UtilidadesAnagramaImportacion utilidadesAnagrama;
	
	private List<ResumenImportacionBean> resumen = new ArrayList<ResumenImportacionBean>();

	@Override
	public ResultadoImportacionBean convertirFicheroXmlImportacion(File fichero, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			log.error("INICIO CONVERTIR GA Duplicado del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
			resultado = conversionFormatoGADuplicado.convertirFicheroFormatoGA(fichero, contratoBBDD, tienePermisoAdmin);
			log.error("FIN CONVERTIR GA Duplicado del colegiado " + contratoBBDD.getColegiado().getNumColegiado());
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGADuplicado.convertirFormatoGAToVO(resultado.getFormatoOegam2Duplicado(), contratoBBDD, tienePermisoAdmin, tipoCreacion, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de duplicado.");
		}
		return resultado;
	}
	
	@Override
	public ResultadoImportacionBean convertirFicheroImportacion(File fichero, ContratoVO contrato, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			resultado = conversionFormatoGADuplicado.convertirFicheroFormatoGA(fichero, contrato, tienePermisoAdmin);
			if (resultado != null && !resultado.getError()) {
				String tipoCreacion = null;
				if (esSiga) {
					tipoCreacion = TipoCreacion.SIGA.getValorEnum();
				} else {
					tipoCreacion = TipoCreacion.XML.getValorEnum();
				}
				resultado = conversionFormatoGADuplicado.convertirFormatoGAToVO(resultado.getFormatoOegam2Duplicado(), contrato, tienePermisoAdmin, tipoCreacion, idUsuario);
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de duplicado, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de tratar el fichero de importacion de los tramites de duplicado.");
		}
		return resultado;
	}

	@Override
	public ResultadoValidacionImporBean validarTramiteImportacion(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicado(tramiteTrafDuplicadoVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteDuplicado(tramiteTrafDuplicadoVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteTrafDuplicadoVO.getVehiculo() != null) {
						ResultadoValidacionImporBean resultValVehiculo = servicioValidaciones.validarVehiculo(tramiteTrafDuplicadoVO.getVehiculo(), TipoTramiteTrafico.Duplicado.getValorEnum());
						if (resultValVehiculo.getErrorVehiculo() != null && resultValVehiculo.getErrorVehiculo()) {
							tramiteTrafDuplicadoVO.setVehiculo(null);
						} else if (resultValVehiculo.getErrorDireccion() != null && resultValVehiculo.getErrorDireccion()) {
							tramiteTrafDuplicadoVO.getVehiculo().setDireccion(null);
						}
						gestionarResultado(resultado, resultValVehiculo);
					}
					if (tramiteTrafDuplicadoVO.getIntervinienteTraficos() != null && !tramiteTrafDuplicadoVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafDuplicadoVO.getIntervinienteTraficos()) {
							ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.Duplicado.getValorEnum());
							if (resultValIntrv.getDireccion() != null) {
								intervinienteTraficoVO.setDireccion(resultValIntrv.getDireccion());
							}
							gestionarResultado(resultado, resultValIntrv);
						}
					}
				} else {
					return resultValTram;
				}
			} else {
				return resultValTram;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite.");
		}

		return resultado;
	}
	
	@Override
	public ResultadoValidacionImporBean validarTramiteImportacionExcel(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		try {
			ResultadoValidacionImporBean resultValTram = validarPosibleDuplicadoExcel(tramiteTrafDuplicadoVO);
			if (!resultValTram.getError()) {
				resultValTram = servicioValidaciones.validarTramiteDuplicado(tramiteTrafDuplicadoVO);
				if (!resultValTram.getError()) {
					gestionarResultado(resultado, resultValTram);
					if (tramiteTrafDuplicadoVO.getVehiculo() != null) {
						ResultadoValidacionImporBean resultValVehiculo = servicioValidaciones.validarVehiculo(tramiteTrafDuplicadoVO.getVehiculo(), TipoTramiteTrafico.Duplicado.getValorEnum());
						if (resultValVehiculo.getErrorVehiculo() != null && resultValVehiculo.getErrorVehiculo()) {
							tramiteTrafDuplicadoVO.setVehiculo(null);
						} else if (resultValVehiculo.getErrorDireccion() != null && resultValVehiculo.getErrorDireccion()) {
							tramiteTrafDuplicadoVO.getVehiculo().setDireccion(null);
						}
						gestionarResultado(resultado, resultValVehiculo);
					}
					if (tramiteTrafDuplicadoVO.getIntervinienteTraficos() != null && !tramiteTrafDuplicadoVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervinienteTraficoVO : tramiteTrafDuplicadoVO.getIntervinienteTraficos()) {
							ResultadoValidacionImporBean resultValIntrv = servicioValidaciones.validarInterviniente(intervinienteTraficoVO, TipoTramiteTrafico.Duplicado.getValorEnum());
							if (resultValIntrv.getDireccion() != null) {
								intervinienteTraficoVO.setDireccion(resultValIntrv.getDireccion());
							}
							gestionarResultado(resultado, resultValIntrv);
						}
					}
				} else {
					return resultValTram;
				}
			} else {
				return resultValTram;
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de validar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de validar el tramite.");
		}

		return resultado;
	}

	@Override
	public ResultadoImportacionBean guardarImportacion(TramiteTrafDuplicadoVO tramiteDuplicadoVO, Long idUsuario) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		try {
			String mensajeErrorVeh = "";
			String mensajeErrorInterv = "";
			String mensajeErrorTram = "";
			String mensajeErrorTasa = "";
			ResultadoBean resultCrearTram = new ResultadoBean(Boolean.FALSE);

			// Por si falla al crear el numero de expediente
			for (int i = 1; i <= 10; i++) {
				try {
					resultCrearTram = servicioTramiteTrafico.crearTramite(tramiteDuplicadoVO.getNumColegiado(), tramiteDuplicadoVO.getTipoTramite(), tramiteDuplicadoVO.getContrato().getIdContrato(),
							idUsuario, tramiteDuplicadoVO.getFechaAlta(), tramiteDuplicadoVO.getJefaturaTrafico(), tramiteDuplicadoVO.getIdTipoCreacion());
				} catch (Exception e) {
					resultCrearTram.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de crear el tramite.");
					if (i == 10) {
						log.error("Ha sucedido un error a la hora de crear el tramite, error: ", e);
					}
				}
				if (resultCrearTram != null && !resultCrearTram.getError()) {
					break;
				}
			}

			if (!resultado.getError()) {
				tramiteDuplicadoVO.setNumExpediente(resultCrearTram.getNumExpediente());
				resultado.setNumExpediente(resultCrearTram.getNumExpediente());
				if (tramiteDuplicadoVO.getVehiculo() != null) {
					ResultadoBean resultGVeh = servicioVehiculo.guardarVehiculo(tramiteDuplicadoVO.getVehiculo(), tramiteDuplicadoVO.getNumExpediente(), tramiteDuplicadoVO.getContrato(), idUsuario,
							tramiteDuplicadoVO.getTipoTramite(), false);
					mensajeErrorVeh = gestionarResultado(resultado, resultGVeh, "El vehiculo se ha guardado con las siguientes incidencias: ");
					if (!resultGVeh.getError()) {
						tramiteDuplicadoVO.getVehiculo().setIdVehiculo(resultGVeh.getIdVehiculo());
					}
					if (tramiteDuplicadoVO.getIntervinienteTraficos() != null && !tramiteDuplicadoVO.getIntervinienteTraficos().isEmpty()) {
						for (IntervinienteTraficoVO intervTrafVO : tramiteDuplicadoVO.getIntervinienteTraficos()) {
							try {
								ResultadoBean resultGInterv = servicioIntervinienteTrafico.guardarInterviniente(intervTrafVO, tramiteDuplicadoVO.getNumExpediente(), tramiteDuplicadoVO.getContrato(),
										idUsuario, tramiteDuplicadoVO.getTipoTramite());
								if (resultGInterv.getError()) {
									mensajeErrorInterv += resultGInterv.getMensaje();
								}
							} catch (Exception e) {
								log.error("Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + " del trámite: "
										+ tramiteDuplicadoVO.getNumExpediente() + ", error: ", e);
								mensajeErrorInterv += "Ha sucedido un error a la hora de guardar el " + TipoInterviniente.convertirTexto(intervTrafVO.getId().getTipoInterviniente()) + ". ";
							}
						}
					}
					if (tramiteDuplicadoVO.getTasa() != null) {
						mensajeErrorTasa = servicioTasa.asignarTasa(tramiteDuplicadoVO.getTasa(), tramiteDuplicadoVO.getNumExpediente(), tramiteDuplicadoVO.getUsuario().getIdUsuario());
					}
					ResultadoBean resultGTram = servicioTramiteTrafico.guardarTramiteDuplicado(tramiteDuplicadoVO, idUsuario);
					if (resultGTram.getError()) {
						mensajeErrorTram = resultGTram.getMensaje();
					}
				}
				resultado.setMensaje(tratarMensajes(mensajeErrorTram, mensajeErrorInterv, mensajeErrorVeh, mensajeErrorTasa));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCrearTram.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el tramite, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el tramite.");
		}
		return resultado;
	}

	private String gestionarResultado(ResultadoImportacionBean resultado, ResultadoBean resultTratar, String mensajeInicial) {
		if (resultTratar.getError()) {
			return resultTratar.getMensaje();
		} else if (resultTratar.getListaMensaje() != null && !resultTratar.getListaMensaje().isEmpty()) {
			for (String mensaje : resultTratar.getListaMensaje()) {
				mensajeInicial += mensaje;
			}
			return mensajeInicial;
		}
		return "";
	}

	private String tratarMensajes(String mensajeErrorTram, String mensajeErrorInterv, String mensajeErrorVeh, String mensajeErrorTasa) {
		String mensaje = "";
		if (mensajeErrorTram != null && !mensajeErrorTram.isEmpty()) {
			mensaje = mensajeErrorTram;
		}
		if (mensajeErrorInterv != null && !mensajeErrorInterv.isEmpty()) {
			mensaje += "Existen errores a la hora de guardar los intervienientes: " + mensajeErrorInterv;
		}
		if (mensajeErrorVeh != null && !mensajeErrorVeh.isEmpty()) {
			mensaje += mensajeErrorVeh;
		}
		if (mensajeErrorTasa != null && !mensajeErrorTasa.isEmpty()) {
			mensaje += mensajeErrorTasa;
		}
		return mensaje;
	}

	private void gestionarResultado(ResultadoValidacionImporBean resultado, ResultadoValidacionImporBean resultValidaciones) {
		if (resultValidaciones.getListaMensajesAvisos() != null && !resultValidaciones.getListaMensajesAvisos().isEmpty()) {
			for (String mensajeVal : resultValidaciones.getListaMensajesAvisos()) {
				resultado.addListaMensajeAvisos(mensajeVal);
			}
		}
		if (resultValidaciones.getListaMensajeError() != null && !resultValidaciones.getListaMensajeError().isEmpty()) {
			for (String mensajeError : resultValidaciones.getListaMensajeError()) {
				resultado.addListaMensajeError(mensajeError);
			}
			resultado.setError(Boolean.TRUE);
		}
	}

	private ResultadoValidacionImporBean validarPosibleDuplicado(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String bastidor = "";
		String nif = "";
		if (tramiteTrafDuplicadoVO.getVehiculo() != null) {
			bastidor = tramiteTrafDuplicadoVO.getVehiculo().getBastidor();
		}

		if (tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList() != null && !tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
					nif = interviente.getId().getNif();
					break;
				}
			}
		}

		if (StringUtils.isNotBlank(bastidor) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicado(bastidor, nif, tramiteTrafDuplicadoVO.getTipoTramite(), tramiteTrafDuplicadoVO.getNumColegiado());
		}
		return resultado;
	}
	
	private ResultadoValidacionImporBean validarPosibleDuplicadoExcel(TramiteTrafDuplicadoVO tramiteTrafDuplicadoVO) {
		ResultadoValidacionImporBean resultado = new ResultadoValidacionImporBean(Boolean.FALSE);
		String matricula = "";
		String nif = "";
		if (tramiteTrafDuplicadoVO.getVehiculo() != null) {
			matricula = tramiteTrafDuplicadoVO.getVehiculo().getMatricula();
		}

		if (tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList() != null && !tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList().isEmpty()) {
			for (IntervinienteTraficoVO interviente : tramiteTrafDuplicadoVO.getIntervinienteTraficosAsList()) {
				if (TipoInterviniente.Titular.getValorEnum().equals(interviente.getId().getTipoInterviniente())) {
					nif = interviente.getId().getNif();
					break;
				}
			}
		}

		if (StringUtils.isNotBlank(matricula) && StringUtils.isNotBlank(nif)) {
			resultado = servicioValidaciones.validarPosibleDuplicadoExcel(matricula, nif, tramiteTrafDuplicadoVO.getTipoTramite(), tramiteTrafDuplicadoVO.getNumColegiado());
		}
		return resultado;
	}

	@SuppressWarnings("unused")
	@Override
	public ResultadoImportacionBean obtenerDatosFichero(File fichero, ContratoVO contratoBBDD, Boolean tienePermisoAdmin, Long idUsuario, Boolean esSiga) {
		ResultadoImportacionBean resultado = new ResultadoImportacionBean(Boolean.FALSE);
		String jefatura = null;
		int dupl_index = 0;
		int otros_index = 1;
		int total_index = 2;
		try {
			Workbook archivoExcel = Workbook.getWorkbook(fichero);
			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) {
				Sheet hoja = archivoExcel.getSheet(sheetNo);
				int numFilas = hoja.getRows();
				resumen.add(dupl_index, new ResumenImportacionBean("Duplicados"));
				int resolucion;
				int posicion = 0;
				for (int fila = 1; fila < numFilas; fila++) { 
					if(StringUtils.isNotBlank(hoja.getCell(27, fila).getContents())) {
						try {
							TramiteTrafDuplicadoVO tramiteDuplicado = convertirTramiteFormatoExcelDuplicadoToVO(hoja, fila,
									contratoBBDD, TipoCreacion.EXCEL.getValorEnum(), idUsuario);
							
							List<IntervinienteTraficoVO> listaIntervinientes = new ArrayList<IntervinienteTraficoVO>();
							if(StringUtils.isNotBlank(hoja.getCell(27, fila).getContents())) {
								tramiteDuplicado.setVehiculo(convertirVehiculoExceltoVO(hoja, fila, contratoBBDD));
							}
							
							if(StringUtils.isNotBlank(hoja.getCell(5, fila).getContents())) {
								listaIntervinientes.add(convertirTitularExceltoVO(hoja, fila, contratoBBDD));
							}
							if(StringUtils.isNotBlank(hoja.getCell(9, fila).getContents())) {
								listaIntervinientes.add(convertirCotitularExceltoVO(hoja, fila, contratoBBDD));
							}
							tramiteDuplicado.setIntervinienteTraficos(listaIntervinientes);
							resultado.addListaTramiteDuplicado(tramiteDuplicado, posicion);
							posicion++;
						} catch (NumberFormatException e) {
							log.error(e.toString());
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha ocurrido un error a la hora de rellenar el excel: ");
							resumen = actualizaResumen(resumen, dupl_index, total_index, false);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del fichero, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener los datos del fichero.");
		}
		return resultado;
	}

	private IntervinienteTraficoVO convertirCotitularExceltoVO(Sheet hoja, int fila, ContratoVO contratoBBDD) {
		IntervinienteTraficoVO interviniente = new IntervinienteTraficoVO();
		IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
		idInterviniente.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		idInterviniente.setNif(hoja.getCell(9, fila).getContents().toUpperCase());
		idInterviniente.setTipoInterviniente(TipoInterviniente.CotitularTransmision.getValorEnum());
		interviniente.setId(idInterviniente);

		PersonaVO persona = new PersonaVO();
		PersonaPK idPersona = new PersonaPK();
		idPersona.setNif(interviniente.getId().getNif());
		idPersona.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		persona.setId(idPersona);

		if (StringUtils.isNotBlank(hoja.getCell(11, fila).getContents())) {
			persona.setApellido1RazonSocial(hoja.getCell(11, fila).getContents());
		} else {
			PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
			if (personaBBDD != null) {
				persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
			}
		}

		persona.setApellido2(hoja.getCell(12, fila).getContents());
		persona.setNombre(hoja.getCell(10, fila).getContents());
		setDatosPersona(persona);
		persona.setEstado(new Long(1));
		interviniente.setPersona(persona);
		return interviniente;
	}

	private IntervinienteTraficoVO convertirTitularExceltoVO(Sheet hoja, int fila, ContratoVO contratoBBDD) {
		IntervinienteTraficoVO interviniente = new IntervinienteTraficoVO();
		IntervinienteTraficoPK idInterviniente = new IntervinienteTraficoPK();
		idInterviniente.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		idInterviniente.setNif(hoja.getCell(5, fila).getContents().toUpperCase());
		idInterviniente.setTipoInterviniente(TipoInterviniente.Titular.getValorEnum());
		interviniente.setId(idInterviniente);

		PersonaVO persona = new PersonaVO();
		PersonaPK idPersona = new PersonaPK();
		idPersona.setNif(interviniente.getId().getNif());
		idPersona.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		persona.setId(idPersona);

		if (StringUtils.isNotBlank(hoja.getCell(7, fila).getContents())) {
			persona.setApellido1RazonSocial(hoja.getCell(7, fila).getContents());
		} else {
			PersonaVO personaBBDD = servicioPersona.getPersonaVO(persona.getId().getNif(), persona.getId().getNumColegiado());
			if (personaBBDD != null) {
				persona.setApellido1RazonSocial(personaBBDD.getApellido1RazonSocial());
			}
		}

		persona.setApellido2(hoja.getCell(8, fila).getContents());
		persona.setNombre(hoja.getCell(6, fila).getContents());
		setDatosPersona(persona);
		persona.setEstado(new Long(1));
		interviniente.setPersona(persona);
		
		if (StringUtils.isNotBlank(hoja.getCell(26, fila).getContents())) {
			DireccionVO direccion = new DireccionVO();
			ProvinciaVO provincia = servicioProvincia.getProvinciaPorNombre(hoja.getCell(26, fila).getContents());
			String idProvincia = provincia.getIdProvincia();
			if (idProvincia != null && !idProvincia.isEmpty()) {
				direccion.setIdProvincia(idProvincia);
			} else {
				direccion.setIdProvincia(hoja.getCell(26, fila).getContents());
			}
			MunicipioVO municipio = servicioDireccion.getMunicipioPorNombre(hoja.getCell(25, fila).getContents(), direccion.getIdProvincia());
				if (municipio != null) {
					direccion.setIdMunicipio(municipio.getId().getIdMunicipio());
				} else {
					direccion.setIdMunicipio(hoja.getCell(25, fila).getContents());
				}

				direccion.setNombreVia(hoja.getCell(14, fila).getContents());
				direccion.setViaSineditar(utilidadesConversiones.getViaSinEditar(direccion.getNombreVia()));
				direccion.setIdTipoVia(hoja.getCell(13, fila).getContents());
				direccion.setNumero(hoja.getCell(15, fila).getContents());
				direccion.setCodPostal(hoja.getCell(24, fila).getContents());
				if (hoja.getCell(23, fila).getContents() != null) {
					direccion.setPueblo(hoja.getCell(23, fila).getContents().toUpperCase());
				}
				direccion.setEscalera(hoja.getCell(18, fila).getContents());
				direccion.setBloque(hoja.getCell(16, fila).getContents());
				direccion.setPlanta(hoja.getCell(19, fila).getContents());
				direccion.setPuerta(hoja.getCell(20, fila).getContents());

				if (StringUtils.isNotBlank(hoja.getCell(21, fila).getContents())) {
					try {
						direccion.setKm(new BigDecimal(hoja.getCell(21, fila).getContents()));
					} catch (Exception e) {
						direccion.setKm(null);
					}
				}

				if (StringUtils.isNotBlank(hoja.getCell(22, fila).getContents())) {
					try {
						direccion.setHm(new BigDecimal(hoja.getCell(22, fila).getContents()));
					} catch (Exception e) {
						direccion.setHm(null);
					}
				}
				interviniente.setDireccion(direccion);
			}
		return interviniente;
	}

	private PersonaVO setDatosPersona(PersonaVO persona) {
		int tipo = utilidadesNIFValidator.isValidDniNieCif(persona.getId().getNif().toUpperCase());
		if (tipo >= 0) {
			if (tipo == utilidadesNIFValidator.FISICA) {
				String anagrama = utilidadesAnagrama.obtenerAnagramaFiscal(persona.getApellido1RazonSocial(), persona.getId().getNif());
				if (anagrama != null && !anagrama.isEmpty()) {
					persona.setAnagrama(anagrama);
				}
				persona.setTipoPersona(TipoPersona.Fisica.getValorEnum());
				persona.setSubtipo(null);
			} else {
				persona.setSexo(SexoPersona.Juridica.getNombreEnum());
				persona.setApellido2(null);
				persona.setAnagrama(null);
				persona.setFechaNacimiento(null);
				persona.setTipoPersona(TipoPersona.Juridica.getValorEnum());
				if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PUBLICA) {
					persona.setSubtipo(SubtipoPersona.Publica.getNombreEnum());
				} else if ((persona.getSubtipo() == null || persona.getSubtipo().isEmpty()) && tipo == utilidadesNIFValidator.JURIDICA_PRIVADA) {
					persona.setSubtipo(SubtipoPersona.Privada.getNombreEnum());
				}
			}
		}
		return persona;
	}

	private VehiculoVO convertirVehiculoExceltoVO(Sheet hoja, int fila, ContratoVO contratoBBDD) {
		VehiculoVO vehiculo = new VehiculoVO();
		vehiculo.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		vehiculo.setMatricula(hoja.getCell(27, fila).getContents());
		if(StringUtils.isNotBlank(hoja.getCell(29, fila).getContents())) {
			vehiculo.setFechaItv(utilesFecha.formatoFecha("dd/MM/yy", hoja.getCell(29, fila).getContents()));
		}
		if(StringUtils.isNotBlank(hoja.getCell(28, fila).getContents())) {
			vehiculo.setFechaMatriculacion(utilesFecha.formatoFecha("dd/MM/yy", hoja.getCell(28, fila).getContents()));
		}
		return vehiculo;
	}

	private TramiteTrafDuplicadoVO convertirTramiteFormatoExcelDuplicadoToVO(Sheet hoja, int fila, ContratoVO contratoBBDD, String tipoCreacion, Long idUsuario) {
		TramiteTrafDuplicadoVO tramite = new TramiteTrafDuplicadoVO();
		tramite.setFechaAlta(utilesFecha.getFechaActualDesfaseBBDD());
		tramite.setFechaUltModif(tramite.getFechaAlta());
		tramite.setNumExpediente(null);
		tramite.setContrato(contratoBBDD);
		tramite.setNumColegiado(contratoBBDD.getColegiado().getNumColegiado());
		tramite.setJefaturaTrafico(contratoBBDD.getJefaturaTrafico());
		tramite.setTipoTramite(TipoTramiteTrafico.Duplicado.getValorEnum());
		tramite.setIdTipoCreacion(new BigDecimal(tipoCreacion));
		tramite.setEstado(new BigDecimal(EstadoTramiteTrafico.Iniciado.getValorEnum()));
		UsuarioVO usuario = new UsuarioVO();
		usuario.setIdUsuario(idUsuario.longValue());
		tramite.setUsuario(usuario);

		if(StringUtils.isNotBlank(hoja.getCell(2, fila).getContents())) {
			tramite.setMotivoDuplicado(MotivoDuplicado.obtenerValorEnum(hoja.getCell(2, fila).getContents()));
		}
		if(StringUtils.isNotBlank(hoja.getCell(1, fila).getContents())) {
			tramite.setRefPropia(hoja.getCell(1, fila).getContents());
		}
		if(StringUtils.isNotBlank(hoja.getCell(30, fila).getContents())) {
			tramite.setImprPermisoCirculacion(hoja.getCell(30, fila).getContents());
		} else {
			tramite.setImprPermisoCirculacion("NO");
		}
		if (StringUtils.isNotBlank(hoja.getCell(31, fila).getContents())) {
			tramite.setImportacion(hoja.getCell(31, fila).getContents());
		} else {
			tramite.setImportacion("NO");
		}
		if(StringUtils.isNotBlank(hoja.getCell(32, fila).getContents())) {
			tramite.setTasaImportacion(hoja.getCell(32, fila).getContents());
		}
		if (StringUtils.isNotBlank(hoja.getCell(33, fila).getContents())) {
			tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yy",hoja.getCell(33, fila).getContents()));
		} else {
			tramite.setFechaPresentacion(utilesFecha.formatoFecha("dd/MM/yy",new Date().toString()));
		}
		if(StringUtils.isNotBlank(hoja.getCell(3, fila).getContents())) {
			TasaVO tasa = new TasaVO();
			tasa.setCodigoTasa(hoja.getCell(3, fila).getContents());
			tramite.setTasa(tasa);
		}
		return tramite;
	}

	private List<ResumenImportacionBean> actualizaResumen(List<ResumenImportacionBean> resumen, int tipoTramiteIndex, int total, Boolean correcto) {
		if (correcto) {
			resumen.get(tipoTramiteIndex).addCorrecto();
			if (total != 0 && tipoTramiteIndex != total)
				resumen.get(total).addCorrecto();
		} else {
			resumen.get(tipoTramiteIndex).addIncorrecto();
			if (total != 0 && tipoTramiteIndex != total)
				resumen.get(total).addIncorrecto();
		}
		return resumen;
	}
}
