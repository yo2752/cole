package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.core.atex5.model.dao.ConsultaPersonaAtex5Dao;
import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaPersonaAtex5VO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.core.model.enumerados.TipoTasa;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioImpresionAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioPersonaAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaPersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.PersonaAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.persona.Persona;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.evolucionAtex5.model.service.ServicioEvolucionAtex5;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasa;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import es.trafico.servicios.vehiculos.consulta.atex.webservices.RespuestaAtex;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPersonaAtex5Impl implements ServicioPersonaAtex5 {

	private static final long serialVersionUID = -3574296497234447754L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPersonaAtex5Impl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioEvolucionAtex5 servicioEvolucionAtex5;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ConsultaPersonaAtex5Dao consultaPersonaAtex5Dao;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioTasa servicioTasa;
	
	@Autowired
	BuildPersonaAtex5 buildPersonaAtex5;
	
	@Autowired
	ServicioImpresionAtex5 servicioImpresionAtex5;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional(readOnly = true)
	public ResultadoAtex5Bean getConsultaPersonaAtex5(BigDecimal numExpediente) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = getConsultaPersonaAtex5PorExpVO(numExpediente, true);
				if (consultaPersonaAtex5VO != null) {
					ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto = conversor.transform(consultaPersonaAtex5VO, ConsultaPersonaAtex5Dto.class);
					if(EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())){
						resultado = getPersonaXml(consultaPersonaAtex5Dto);
					}
					if(!resultado.getError()){
						resultado.setConsultaPersonaAtex5Dto(consultaPersonaAtex5Dto);
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("No existen datos en la bade de datos para esa consulta de la persona atex5.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe de indicar la consulta de la persona atex5 que desea obtener sus datos.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de la persona atex5 con numero de expediente: " + numExpediente + " , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la consulta de la persona atex5 con numero de expediente: " + numExpediente);
		}
		return resultado;
	}

	public ResultadoAtex5Bean getPersonaXml(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		resultado = obtenerXml(consultaPersonaAtex5Dto);
		if(!resultado.getError()){
			Persona personaAtex5 = prepararPersonaFile(resultado.getFicheroDescarga());
			if(personaAtex5 != null){
				PersonaAtex5Dto personaAtex5Dto = buildPersonaAtex5.convertirPersonaXmlToPersonaAtex5Dto(personaAtex5); 
				if(personaAtex5Dto != null){
					consultaPersonaAtex5Dto.setPersonaAtex5(personaAtex5Dto);
				} else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha podido obtener los datos de la persona de la consulta para poder mostrarlos por la pantalla.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido obtener los datos de la persona de la consulta para poder mostrarlos por la pantalla.");
			}
		}
		return resultado;
	}
	
	@SuppressWarnings("unchecked")
	public Persona prepararPersonaFile(File fichero) {
		Persona persona = null;
		try {
			JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.atex5.xml.persona");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			byte[] xmlVehiculo = gestorDocumentos.transformFiletoByte(fichero);
			String xmlData = new String(xmlVehiculo);
			StringReader stringReaderXML = new StringReader(xmlData);
			JAXBElement<Persona> unmarshal = (JAXBElement<Persona>) unmarshaller.unmarshal(stringReaderXML);
			persona = (Persona) unmarshal.getValue();
		} catch (Exception e) {
			log.error("Error al generar la persona con el xml", e);
			persona = null;
		}
		return persona;
	}

	@Override
	@Transactional(readOnly = true)
	public ConsultaPersonaAtex5VO getConsultaPersonaAtex5PorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			if (numExpediente != null) {
				Boolean filtrarPorTasa = Boolean.FALSE;
				String tasasProperties = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
				if("SI".equalsIgnoreCase(tasasProperties)){
					filtrarPorTasa = Boolean.TRUE;
				}
				return consultaPersonaAtex5Dao.getConsultaPersonaAtex5PorExpediente(numExpediente, tramiteCompleto,filtrarPorTasa);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas de la persona atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de la persona atex5 con numExpediente: " + numExpediente + " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ConsultaPersonaAtex5VO getConsultaPersonaAtex5VO(Long idConsultaPersonaAtex5, Boolean tramiteCompleto) {
		try {
			if (idConsultaPersonaAtex5 != null) {
				Boolean filtrarPorTasa = Boolean.FALSE;
				String tasasProperties = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
				if("SI".equalsIgnoreCase(tasasProperties)){
					filtrarPorTasa = Boolean.TRUE;
				}
				return consultaPersonaAtex5Dao.getConsultaPersonaAtex5PorId(idConsultaPersonaAtex5, tramiteCompleto,filtrarPorTasa);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas de la persona atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta de la persona atex5 con id: " + idConsultaPersonaAtex5 + " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean guardarConsultaPersonaAtex5(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			resultado = validarDatos(consultaPersonaAtex5Dto);
			String tasasProperties = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
			if (!resultado.getError()) {
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = conversor.transform(consultaPersonaAtex5Dto, ConsultaPersonaAtex5VO.class);
				rellenarDatosGuardadoInicial(consultaPersonaAtex5Dto, consultaPersonaAtex5VO, fecha);
				if (consultaPersonaAtex5Dto.getEstado() != null && !consultaPersonaAtex5Dto.getEstado().isEmpty()) {
					estadoAnt = new BigDecimal(consultaPersonaAtex5Dto.getEstado());
				}
				if (consultaPersonaAtex5Dto.getIdConsultaPersonaAtex5() != null) {
					tipoActualizacion = TipoActualizacion.MOD;
				} else {
					tipoActualizacion = TipoActualizacion.CRE;
				}
				modificarDatos(consultaPersonaAtex5VO);
				consultaPersonaAtex5VO.setEstado(new BigDecimal(EstadoAtex5.Iniciado.getValorEnum()));
				if("SI".equalsIgnoreCase(tasasProperties)){
					if (consultaPersonaAtex5VO.getTasa() != null && consultaPersonaAtex5VO.getTasa().getCodigoTasa() != null && !consultaPersonaAtex5VO.getTasa().getCodigoTasa().isEmpty()) {
						ResultBean respuestaDesTasaExpediente = servicioTasa.desasignarTasaExpediente(consultaPersonaAtex5VO.getTasa().getCodigoTasa(), consultaPersonaAtex5VO.getNumExpediente(),
								TipoTasa.CuatroUno.getValorEnum());
						resultado.addListaErroresList(respuestaDesTasaExpediente.getListaMensajes());
					}
				}
				if(!resultado.getError()){
					consultaPersonaAtex5Dao.guardarOActualizar(consultaPersonaAtex5VO);
					resultado.setNumExpediente(consultaPersonaAtex5VO.getNumExpediente());
					servicioEvolucionAtex5.guardarEvolucion(consultaPersonaAtex5VO.getNumExpediente(), idUsuario.longValue(), fecha, estadoAnt, consultaPersonaAtex5VO.getEstado(), tipoActualizacion
							.getValorEnum());
				}
				if("SI".equalsIgnoreCase(tasasProperties)){
					if (consultaPersonaAtex5VO.getTasa() != null && consultaPersonaAtex5VO.getTasa().getCodigoTasa() != null && !consultaPersonaAtex5VO.getTasa().getCodigoTasa().isEmpty()) {
						ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(consultaPersonaAtex5VO.getTasa().getCodigoTasa(), consultaPersonaAtex5VO.getNumExpediente());
						if(respuestaTasaExpediente.getError()){
							resultado.addListaErroresList(respuestaTasaExpediente.getListaMensajes());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la consulta de la persona atex5 , error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la consulta de la persona atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private void modificarDatos(ConsultaPersonaAtex5VO consultaPersonaAtex5VO) {
		if (consultaPersonaAtex5VO.getNif() != null && !consultaPersonaAtex5VO.getNif().isEmpty()) {
			consultaPersonaAtex5VO.setNif(consultaPersonaAtex5VO.getNif().toUpperCase());
		}
		if (consultaPersonaAtex5VO.getNombre() != null && !consultaPersonaAtex5VO.getNombre().isEmpty()) {
			consultaPersonaAtex5VO.setNombre(consultaPersonaAtex5VO.getNombre().toUpperCase());
		}
		if (consultaPersonaAtex5VO.getApellido1() != null && !consultaPersonaAtex5VO.getApellido1().isEmpty()) {
			consultaPersonaAtex5VO.setApellido1(consultaPersonaAtex5VO.getApellido1().toUpperCase());
		}
		if (consultaPersonaAtex5VO.getApellido2() != null && !consultaPersonaAtex5VO.getApellido2().isEmpty()) {
			consultaPersonaAtex5VO.setApellido2(consultaPersonaAtex5VO.getApellido2().toUpperCase());
		}
		if (consultaPersonaAtex5VO.getRazonSocial() != null && !consultaPersonaAtex5VO.getRazonSocial().isEmpty()) {
			consultaPersonaAtex5VO.setRazonSocial(consultaPersonaAtex5VO.getRazonSocial().toUpperCase());
		}
	}

	public void rellenarDatosGuardadoInicial(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto, ConsultaPersonaAtex5VO consultaPersonaAtex5VO, Date fecha) throws Exception {
		if (consultaPersonaAtex5VO.getFechaAlta() == null) {
			consultaPersonaAtex5VO.setFechaAlta(fecha);
		}
		if (consultaPersonaAtex5Dto.getNumExpediente() == null) {
			if (consultaPersonaAtex5Dto.getNumColegiado() != null && !consultaPersonaAtex5Dto.getNumColegiado().isEmpty()) {
				consultaPersonaAtex5VO.setNumColegiado(consultaPersonaAtex5Dto.getNumColegiado());
			} else {
				ContratoDto contratoDto = servicioContrato.getContratoDto(consultaPersonaAtex5Dto.getContrato().getIdContrato());
				consultaPersonaAtex5VO.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			}
			consultaPersonaAtex5VO.setNumExpediente(generarNumExpediente(consultaPersonaAtex5VO.getNumColegiado()));
		}
	}

	private ResultadoAtex5Bean validarDatos(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		if (consultaPersonaAtex5Dto == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de rellenar los datos obligatorios para poder realizar una consulta de persona atex5.");
		} else if (consultaPersonaAtex5Dto.getContrato() == null || consultaPersonaAtex5Dto.getContrato().getIdContrato() == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe de seleccionar un contrato para poder realizar la consulta de persona atex5.");
		}  else {
			boolean rellenadoOK = false;
			if (consultaPersonaAtex5Dto.getNif() != null && !consultaPersonaAtex5Dto.getNif().isEmpty()) {
				rellenadoOK = true;
			} else if (consultaPersonaAtex5Dto.getRazonSocial() != null && !consultaPersonaAtex5Dto.getRazonSocial().isEmpty()) {
				rellenadoOK = true;
			} else if (consultaPersonaAtex5Dto.getApellido1() != null && !consultaPersonaAtex5Dto.getApellido1().isEmpty()) {
				rellenadoOK = true;
			} else if (consultaPersonaAtex5Dto.getApellido2() != null && !consultaPersonaAtex5Dto.getApellido2().isEmpty()) {
				rellenadoOK = true;
			} else if (consultaPersonaAtex5Dto.getNombre() != null && !consultaPersonaAtex5Dto.getNombre().isEmpty()) {
				rellenadoOK = true;
			} else if (!consultaPersonaAtex5Dto.getFechaNacimiento().isfechaNula()) {
				rellenadoOK = true;
			} else if (consultaPersonaAtex5Dto.getAnioNacimiento() != null && !consultaPersonaAtex5Dto.getAnioNacimiento().isEmpty()) {
				rellenadoOK = true;
			}
			if (!rellenadoOK) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe de rellenar algún dato minimo para poder realizar la consulta de persona atex5.");
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean consultarPersonaAtex5(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			resultado = validarDatos(consultaPersonaAtex5Dto);
			String tasasProperties = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
			if (!resultado.getError()) {
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = conversor.transform(consultaPersonaAtex5Dto, ConsultaPersonaAtex5VO.class);
				Date fecha = new Date();
				BigDecimal estadoAnt = consultaPersonaAtex5VO.getEstado();
				consultaPersonaAtex5VO.setEstado(new BigDecimal(EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum()));
				if("SI".equalsIgnoreCase(tasasProperties)){
					if (consultaPersonaAtex5VO.getTasa() != null && consultaPersonaAtex5VO.getTasa().getCodigoTasa() != null && !consultaPersonaAtex5VO.getTasa().getCodigoTasa().isEmpty()) {
						ResultBean respuestaDesTasaExpediente = servicioTasa.desasignarTasaExpediente(consultaPersonaAtex5VO.getTasa().getCodigoTasa(), consultaPersonaAtex5VO.getNumExpediente(),
								TipoTasa.CuatroUno.getValorEnum());
						resultado.addListaErroresList(respuestaDesTasaExpediente.getListaMensajes());
					}
				}
				consultaPersonaAtex5Dao.guardarOActualizar(consultaPersonaAtex5VO);
				resultado = realizarConsulta(consultaPersonaAtex5VO, idUsuario);
				if (!resultado.getError()) {
					resultado.setNumExpediente(consultaPersonaAtex5VO.getNumExpediente());
					servicioEvolucionAtex5.guardarEvolucion(consultaPersonaAtex5VO.getNumExpediente(), idUsuario.longValue(), fecha, estadoAnt, consultaPersonaAtex5VO.getEstado(),
							TipoActualizacion.MOD.getValorEnum());
					if("SI".equalsIgnoreCase(tasasProperties)){
						if (consultaPersonaAtex5VO.getTasa() != null && consultaPersonaAtex5VO.getTasa().getCodigoTasa() != null && !consultaPersonaAtex5VO.getTasa().getCodigoTasa().isEmpty()) {
							ResultBean respuestaTasaExpediente = servicioTasa.asignarTasa(consultaPersonaAtex5VO.getTasa().getCodigoTasa(), consultaPersonaAtex5VO.getNumExpediente());
							resultado.addListaErroresList(respuestaTasaExpediente.getListaMensajes());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta de la persona atex5 , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de la persona atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar la cola para la consulta de la persona atex5 , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta de la persona atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoAtex5Bean realizarConsulta(ConsultaPersonaAtex5VO consultaPersonaAtex5VO, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		resultado = tratarCobrarCreditos(consultaPersonaAtex5VO, idUsuario);
		if (!resultado.getError()) {
			ResultBean resultCola = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_PERSONA_ATEX5.getNombreEnum(), ConstantesProcesos.TIPO_SOLICITUD_PROCESO_CONSULTA_PERS_ATEX5, gestorPropiedades.valorPropertie(ServicioPersonaAtex5.NOMBRE_HOST_SOLICITUD_NODO_2), TipoTramiteAtex5.Consulta_Persona.getValorEnum(), consultaPersonaAtex5VO.getNumExpediente().toString(), idUsuario, null,
					new BigDecimal(consultaPersonaAtex5VO.getContrato().getIdContrato()));
			if (resultCola.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultCola.getMensaje());
			}
		}
		return resultado;
	}

	private ResultadoAtex5Bean tratarCobrarCreditos(ConsultaPersonaAtex5VO consultaPersonaAtex5VO, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioPersonaAtex5.cobrarCreditos))) {
				ResultBean resultBean = servicioCredito.validarCreditos(TipoTramiteAtex5.Consulta_Persona.getValorEnum(), new BigDecimal(consultaPersonaAtex5VO.getIdContrato()), BigDecimal.ONE);
				if (!resultBean.getError()) {
					// Descontar creditos
					resultBean = servicioCredito.descontarCreditos(TipoTramiteAtex5.Consulta_Persona.getValorEnum(), new BigDecimal(consultaPersonaAtex5VO.getIdContrato()), BigDecimal.ONE, idUsuario,
							ConceptoCreditoFacturado.PTX5, consultaPersonaAtex5VO.getIdConsultaPersonaAtex5().toString());
					if(resultBean.getError()){
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultBean.getMensaje());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los creditos para realizar la consulta de persona atex5, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de cobrar los creditos.");
		}
		return resultado;
	}

	private BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return consultaPersonaAtex5Dao.generarNumExpediente(numColegiado);
	}

	@Override
	public ResultadoAtex5Bean guardarXmlConsultaPersonaAtex5(BigDecimal numExpediente, RespuestaAtex respuestaAtex) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (respuestaAtex != null && respuestaAtex.getRespuesta() != null && !respuestaAtex.getRespuesta().isEmpty()) {
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.ATEX5);
				fichero.setSubTipo(ConstantesGestorFicheros.ATEX5_PERSONA_XML);
				fichero.setNombreDocumento(ServicioPersonaAtex5.NOMBRE_XML + numExpediente);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
				Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
				fichero.setFecha(fecha);
				fichero.setFicheroByte(respuestaAtex.getRespuesta().getBytes());
				File file = gestorDocumentos.guardarFichero(fichero);
				if (file == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha podido guardar el xml con los datos de la consulta de persona atex5.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La respuesta de la consulta de persona atex5 no contiene ningun xml que guardar.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el xml de la consulta de persona atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml de la consulta de persona atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el xml de la consulta de persona atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml de la consulta de persona atex5.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = getConsultaPersonaAtex5PorExpVO(numExpediente, false);
				if (consultaPersonaAtex5VO != null) {
					if (EstadoAtex5.Anulado.getValorEnum().equals(consultaPersonaAtex5VO.getEstado().toString())) {
						resultado.setError(Boolean.TRUE);
						resultado
								.setMensaje("La consulta de persona atex5 con número de expediente: " + numExpediente + " se encuentra en estado anulada y no se podran realizar acciones sobre ella.");
						return resultado;
					}
					BigDecimal estadoAnt = consultaPersonaAtex5VO.getEstado();
					consultaPersonaAtex5VO.setEstado(estadoNuevo);
					consultaPersonaAtex5Dao.actualizar(consultaPersonaAtex5VO);
					servicioEvolucionAtex5.guardarEvolucion(consultaPersonaAtex5VO.getNumExpediente(), idUsuario.longValue(), new Date(), estadoAnt, consultaPersonaAtex5VO.getEstado(),
							TipoActualizacion.MOD.getValorEnum());
					if (accionesAsociadas) {
						resultado = accionesCambiarEstadoSinValidacion(consultaPersonaAtex5VO, estadoAnt, estadoNuevo, idUsuario, Boolean.FALSE);
					}
					if (!resultado.getError()) {
						resultado.setMensaje("La consulta de persona atex5 con número de expediente: " + numExpediente + ", se ha actualizado.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La consulta de persona atex5 con número de expediente: " + numExpediente + ", no se ha podido cambiar el estado porque no se encuentra disponible.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de persona atex5 con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la consulta de persona atex5");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ResultadoAtex5Bean accionesCambiarEstadoSinValidacion(ConsultaPersonaAtex5VO consultaPersonaAtex5VO, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario, Boolean esMasiva) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(estadoAnt.toString())) {
				if (!esMasiva) {
					servicioCola.eliminar(consultaPersonaAtex5VO.getNumExpediente(),null);
				}
				resultado = devolverCreditos(consultaPersonaAtex5VO.getIdConsultaPersonaAtex5(), idUsuario, new BigDecimal(consultaPersonaAtex5VO.getIdContrato()));
			} else if (EstadoAtex5.Anulado.getValorEnum().equals(estadoAnt.toString())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La consulta de persona atex5 con número de expediente: " + consultaPersonaAtex5VO.getNumExpediente()
						+ " se encuentra en estado anulada y no se podran realizar acciones sobre ella.");
			}
			if (!esMasiva) {
				if (EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(estadoNuevo.toString())) {
					return realizarConsulta(consultaPersonaAtex5VO, idUsuario);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de persona atex5 con num.Expediente: "
					+ consultaPersonaAtex5VO.getNumExpediente() + ", error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de persona atex5 con num.Expediente: "
					+ consultaPersonaAtex5VO.getNumExpediente() + ".");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de persona atex5 con num.Expediente: "
					+ consultaPersonaAtex5VO.getNumExpediente() + ", error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de persona atex5 con num.Expediente: "
					+ consultaPersonaAtex5VO.getNumExpediente() + ".");
		}
		return resultado;
	}

	private ResultadoAtex5Bean devolverCreditos(Long idConsultaPersonaAtex5, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(ServicioPersonaAtex5.cobrarCreditos))) {
			ResultBean resultBean = servicioCredito.devolverCreditos(TipoTramiteAtex5.Consulta_Persona.getValorEnum(), 
					idContrato, 1, idUsuario, ConceptoCreditoFacturado.DPX5, idConsultaPersonaAtex5.toString());
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean devolverCreditosWS(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal idContrato) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			ConsultaPersonaAtex5VO consultaPersonaAtex5VO = getConsultaPersonaAtex5PorExpVO(numExpediente, false);
			if (consultaPersonaAtex5VO != null) {
				resultado = devolverCreditos(consultaPersonaAtex5VO.getIdConsultaPersonaAtex5(), idUsuario, idContrato);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la consulta de personas de atex5 para poder devolver el credito.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver el credito de la consulta de persona atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de devolver el credito de la consulta de persona atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public ResultadoAtex5Bean obtenerXml(ConsultaPersonaAtex5Dto consultaPersonaAtex5Dto) {
		ResultadoAtex5Bean resultDescargar = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (consultaPersonaAtex5Dto != null) {
				if (!EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5Dto.getEstado())) {
					resultDescargar.setError(Boolean.TRUE);
					resultDescargar.setMensaje("La consulta no se encuentra en un estado valido para poder realizar la descarga.");
				} else {
					String nombreFichero = ServicioPersonaAtex5.NOMBRE_XML + consultaPersonaAtex5Dto.getNumExpediente();
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ATEX5, ConstantesGestorFicheros.ATEX5_PERSONA_XML, Utilidades
							.transformExpedienteFecha(consultaPersonaAtex5Dto.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_XML);
					if (fichero != null && fichero.getFile() != null) {
						resultDescargar.setNombreFichero(nombreFichero + ConstantesGestorFicheros.EXTENSION_XML);
						resultDescargar.setFicheroDescarga(fichero.getFile());
					} else {
						resultDescargar.setError(Boolean.TRUE);
						resultDescargar.setMensaje("El xml de la consulta no se encuentra para poder imprimirlo.");
					}
				}
			} else {
				resultDescargar.setError(Boolean.TRUE);
				resultDescargar.setMensaje("No existen datos validos de la consulta para poder realizar la descarga.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de descargar el xml generado de la consulta de persona atex5, error: ", e);
			resultDescargar.setError(Boolean.TRUE);
			resultDescargar.setMensaje("Ha sucedido un error a la hora de descargar el xml generado de la consulta de persona atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de descargar el xml generado de la consulta de persona atex5, error: ", e);
			resultDescargar.setError(Boolean.TRUE);
			resultDescargar.setMensaje("Ha sucedido un error a la hora de descargar el xml generado de la consulta de persona atex5.");
		}
		return resultDescargar;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoAtex5Bean imprimirPdf(BigDecimal numExpediente) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaPersonaAtex5VO consultaPersonaAtex5VO = getConsultaPersonaAtex5PorExpVO(numExpediente, Boolean.TRUE);
				if (consultaPersonaAtex5VO != null) {
					if (!EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaPersonaAtex5VO.getEstado().toString())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La consulta no se encuentra en un estado valido para poder realizar la impresión.");
					} else {
						String nombreFichero = NOMBRE_XML + consultaPersonaAtex5VO.getNumExpediente();
						FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ATEX5, ConstantesGestorFicheros.ATEX5_PERSONA_XML, Utilidades
								.transformExpedienteFecha(consultaPersonaAtex5VO.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_XML);
						if (fichero != null && fichero.getFile() != null) {
							resultado = servicioImpresionAtex5.imprimirPdfPersonaAtex5(consultaPersonaAtex5VO,fichero.getFile());
							if(!resultado.getError()){
								FileResultBean ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ATEX5, ConstantesGestorFicheros.ATEX5_PERSONA_PDF, Utilidades
										.transformExpedienteFecha(consultaPersonaAtex5VO.getNumExpediente()), resultado.getNombreFichero(), null);
								if (ficheroPdf != null && ficheroPdf.getFile() != null) {
									resultado.setFicheroDescarga(ficheroPdf.getFile());
								} else {
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje("El pdf generado no se ha podido descargar.");
								}
							}
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("El xml de la consulta no se encuentra para poder imprimirlo.");
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos de la consulta para poder realizar la impresión.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos validos de la consulta para poder realizar la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la consulta de persona atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la consulta de persona atex5.");
		}
		return resultado;
	}

}
