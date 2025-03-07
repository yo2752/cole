package org.gestoresmadrid.oegam2comun.atex5.model.service.impl;

import java.io.File;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.core.atex5.model.dao.ConsultaVehiculoAtex5Dao;
import org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5;
import org.gestoresmadrid.core.atex5.model.enumerados.TipoTramiteAtex5;
import org.gestoresmadrid.core.atex5.model.vo.ConsultaVehiculoAtex5VO;
import org.gestoresmadrid.core.evolucionAtex5.model.dao.EvolucionAtex5Dao;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.TipoActualizacion;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioImpresionAtex5;
import org.gestoresmadrid.oegam2comun.atex5.model.service.ServicioVehiculoAtex5;
import org.gestoresmadrid.oegam2comun.atex5.view.bean.ResultadoAtex5Bean;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.ConsultaVehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.view.dto.VehiculoAtex5Dto;
import org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo.Vehiculo;
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
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioVehiculoAtex5Impl implements ServicioVehiculoAtex5 {

	private static final long serialVersionUID = 2354487062743760787L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioVehiculoAtex5Impl.class);

	@Autowired
	ConsultaVehiculoAtex5Dao consultaVehiculoAtex5Dao;

	@Autowired
	ServicioEvolucionAtex5 servicioEvolucionAtex5;
	
	@Autowired
	 EvolucionAtex5Dao evolucionAtex5;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioCredito servicioCredito;

	@Autowired
	ServicioTasa servicioTasa;
	
	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	Conversor conversor;
	
	@Autowired
	BuildVehiculoAtex5 buildVehiculoAtex5;
	
	@Autowired
	ServicioImpresionAtex5 servicioImpresionAtex5;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional(readOnly = true)
	public ResultadoAtex5Bean getConsultaVehiculoAtex5(BigDecimal numExpediente) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorExpVO(numExpediente, true);
				if (consultaVehiculoAtex5VO != null) {
					ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto = conversor.transform(consultaVehiculoAtex5VO, ConsultaVehiculoAtex5Dto.class);
					if(EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())
							&& consultaVehiculoAtex5Dto.getRespuesta() != null && !consultaVehiculoAtex5Dto.getRespuesta().contains(ID_ATEX5_SIN_ANTECEDENTES)){
						resultado =  getDetalleVehiculoXml(consultaVehiculoAtex5Dto);
					}
					if(!resultado.getError()){
						resultado.setConsultaVehiculoAtex5Dto(consultaVehiculoAtex5Dto);
					}
				} else {
					resultado.setError(true);
					resultado.setMensaje("No existen datos en la base de datos para esa consulta del vehiculo atex5.");
				}
			} else {
				resultado.setError(true);
				resultado.setMensaje("Debe de indicar la consulta del vehiculo atex5 que desea obtener sus datos.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta del vehiculo atex5 con numero de expediente: " + numExpediente + " , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener la consulta del vehiculo atex5 con numero de expediente: " + numExpediente);
		}
		return resultado;
	}

	public ResultadoAtex5Bean getDetalleVehiculoXml(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		ResultadoAtex5Bean resultadoXml = new ResultadoAtex5Bean(Boolean.FALSE);
		resultadoXml = obtenerXml(consultaVehiculoAtex5Dto);
		if(!resultadoXml.getError()){
			Vehiculo vehiculoAtex5 = prepararVehiculoFile(resultadoXml.getFicheroDescarga());
			if(vehiculoAtex5 != null){
				VehiculoAtex5Dto vehiculoAtex5Dto = buildVehiculoAtex5.convertirVehiculoXmlToVehiculoAtex5(vehiculoAtex5);
				if(vehiculoAtex5Dto != null){
					consultaVehiculoAtex5Dto.setVehiculoAtex5(vehiculoAtex5Dto);
				} else {
					resultadoXml.setError(Boolean.TRUE);
					resultadoXml.setMensaje("No se ha podido obtener los datos del vehiculo de la consulta para poder mostrarlos por la pantalla.");
				}
			} else {
				resultadoXml.setError(Boolean.TRUE);
				resultadoXml.setMensaje("No se ha podido obtener los datos del vehiculo de la consulta para poder mostrarlos por la pantalla.");
			}
		}
		return resultadoXml;
	}
	
	@SuppressWarnings("unchecked")
	public Vehiculo prepararVehiculoFile(File fichero) {
		Vehiculo vehiculo = null;
		try {
			JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.atex5.xml.vehiculo");
			Unmarshaller unmarshaller = context.createUnmarshaller();
			byte[] xmlVehiculo = gestorDocumentos.transformFiletoByte(fichero);
			String xmlData = new String(xmlVehiculo);
			StringReader stringReaderXML = new StringReader(xmlData);
			JAXBElement<Vehiculo> unmarshal = (JAXBElement<Vehiculo>) unmarshaller.unmarshal(stringReaderXML);
			vehiculo = (Vehiculo) unmarshal.getValue();
		} catch (Exception e) {
			log.error("Error al generar el vehiculo con el xml", e);
		}
		return vehiculo;
	}

	@Override
	@Transactional(readOnly = true)
	public ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorExpVO(BigDecimal numExpediente, Boolean tramiteCompleto) {
		try {
			if (numExpediente != null) {
				return consultaVehiculoAtex5Dao.getConsultaVehiculoAtex5PorExpediente(numExpediente, tramiteCompleto);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas del vehiculo atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta del vehiculo atex5 con numExpediente: " + numExpediente + " , error: ", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ConsultaVehiculoAtex5VO getConsultaVehiculoAtex5PorIdConsulta(BigDecimal idConsultaVehiculoAtex5, Boolean tramiteCompleto) {
		try {
			if (idConsultaVehiculoAtex5 != null) {
				Boolean filtrarPorTasa = Boolean.FALSE;
				String tasasProperties = gestorPropiedades.valorPropertie("nuevo.atex5.tasas");
				if("SI".equalsIgnoreCase(tasasProperties)){
					filtrarPorTasa = Boolean.TRUE;
				}
				return consultaVehiculoAtex5Dao.getConsultaVehiculoAtex5PorId(idConsultaVehiculoAtex5.longValue(), tramiteCompleto,filtrarPorTasa);
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas del vehiculo atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta del vehiculo atex5 con idConsultaVehiculOAtex5: " + idConsultaVehiculoAtex5 + " , error: ", e);
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ConsultaVehiculoAtex5Dto getConsultaVehiculoAtex5PorIdConsultaDto(BigDecimal idConsultaVehiculoAtex5, Boolean tramiteCompleto) {
		try {
			if (idConsultaVehiculoAtex5 != null) {
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorIdConsulta(idConsultaVehiculoAtex5, tramiteCompleto);
				if(consultaVehiculoAtex5VO != null){
					return conversor.transform(consultaVehiculoAtex5VO, ConsultaVehiculoAtex5Dto.class);
				} else {
					log.error("No se han encontrado datos de la consultaVehiculoAtex5 para el id: " + idConsultaVehiculoAtex5);
				}
			} else {
				log.error("No se puede realizar una consulta a la BBDD con el id a null para las consultas del vehiculo atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la consulta del vehiculo atex5 con idConsultaVehiculOAtex5: " + idConsultaVehiculoAtex5 + " , error: ", e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean guardarConsultaVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			resultado = validarDatos(consultaVehiculoAtex5Dto);
			if (!resultado.getError()) {
				Date fecha = new Date();
				BigDecimal estadoAnt = null;
				TipoActualizacion tipoActualizacion = null;
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = conversor.transform(consultaVehiculoAtex5Dto, ConsultaVehiculoAtex5VO.class);
				rellenarDatosGuardadoInicial(consultaVehiculoAtex5Dto, consultaVehiculoAtex5VO, fecha);
				if (consultaVehiculoAtex5Dto.getEstado() != null && !consultaVehiculoAtex5Dto.getEstado().isEmpty()) {
					estadoAnt = new BigDecimal(consultaVehiculoAtex5Dto.getEstado());
				}
				if (consultaVehiculoAtex5Dto.getIdConsultaVehiculoAtex5() != null) {
					tipoActualizacion = TipoActualizacion.MOD;
				} else {
					tipoActualizacion = TipoActualizacion.CRE;
				}
				modificarDatos(consultaVehiculoAtex5VO);
				if(!resultado.getError()){
					consultaVehiculoAtex5Dao.guardarOActualizar(consultaVehiculoAtex5VO);
					resultado.setNumExpediente(consultaVehiculoAtex5VO.getNumExpediente());
					servicioEvolucionAtex5.guardarEvolucion(consultaVehiculoAtex5VO.getNumExpediente(), idUsuario.longValue(), fecha, estadoAnt, consultaVehiculoAtex5VO.getEstado(), tipoActualizacion
							.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar la consulta del vehiculo atex5 , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar la consulta del vehiculo atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean consultarVehiculoAtex5(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			resultado = validarDatos(consultaVehiculoAtex5Dto);
			if (!resultado.getError()) {
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = conversor.transform(consultaVehiculoAtex5Dto, ConsultaVehiculoAtex5VO.class);
				Date fecha = new Date();
				BigDecimal estadoAnt = consultaVehiculoAtex5VO.getEstado();
				consultaVehiculoAtex5VO.setEstado(new BigDecimal(EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum()));
				resultado = realizarConsulta(consultaVehiculoAtex5VO, idUsuario);
				if (!resultado.getError()) {
					resultado.setNumExpediente(consultaVehiculoAtex5VO.getNumExpediente());
					servicioEvolucionAtex5.guardarEvolucion(consultaVehiculoAtex5Dto.getNumExpediente(), idUsuario.longValue(), fecha, estadoAnt, consultaVehiculoAtex5VO.getEstado(),
							TipoActualizacion.MOD.getValorEnum());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar la consulta del vehiculo atex5 , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta del vehiculo atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de generar la cola para la consulta del vehiculo atex5 , error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de generar la consulta del vehiculo atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public void rellenarDatosGuardadoInicial(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto, ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO, Date fecha) throws Exception {
		if (consultaVehiculoAtex5VO.getFechaAlta() == null) {
			consultaVehiculoAtex5VO.setFechaAlta(fecha);
		}

		if (consultaVehiculoAtex5Dto.getNumExpediente() == null) {
			if (consultaVehiculoAtex5Dto.getNumColegiado() != null && !consultaVehiculoAtex5Dto.getNumColegiado().isEmpty()) {
				consultaVehiculoAtex5VO.setNumColegiado(consultaVehiculoAtex5Dto.getNumColegiado());
			} else {
				ContratoDto contratoDto = servicioContrato.getContratoDto(consultaVehiculoAtex5Dto.getContrato().getIdContrato());
				consultaVehiculoAtex5VO.setNumColegiado(contratoDto.getColegiadoDto().getNumColegiado());
			}
			consultaVehiculoAtex5VO.setNumExpediente(generarNumExpediente(consultaVehiculoAtex5VO.getNumColegiado()));
		}

		consultaVehiculoAtex5VO.setEstado(new BigDecimal(EstadoAtex5.Iniciado.getValorEnum()));
	}

	private BigDecimal generarNumExpediente(String numColegiado) throws Exception {
		return consultaVehiculoAtex5Dao.generarNumExpediente(numColegiado);
	}

	private ResultadoAtex5Bean validarDatos(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		ResultadoAtex5Bean result = new ResultadoAtex5Bean(Boolean.FALSE);
		if (consultaVehiculoAtex5Dto == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de rellenar los datos obligatorios para poder realizar una consulta de vehiculo atex5.");
		} else if (consultaVehiculoAtex5Dto.getContrato() == null || consultaVehiculoAtex5Dto.getContrato().getIdContrato() == null) {
			result.setError(Boolean.TRUE);
			result.setMensaje("Debe de seleccionar un contrato para poder realizar la consulta de vehiculo atex5.");
		} else {
			boolean rellenadoOK = false;
			if (consultaVehiculoAtex5Dto.getMatricula() != null && !consultaVehiculoAtex5Dto.getMatricula().isEmpty()) {
				rellenadoOK = true;
			} else if (consultaVehiculoAtex5Dto.getBastidor() != null && !consultaVehiculoAtex5Dto.getBastidor().isEmpty()) {
				rellenadoOK = true;
			}
			if (!rellenadoOK) {
				result.setError(Boolean.TRUE);
				result.setMensaje("Debe de seleccionar algún dato minimo para poder realizar la consulta de vehiculo atex5.");
			}
		}
		return result;
	}

	private ResultadoAtex5Bean realizarConsulta(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO, BigDecimal idUsuario) throws OegamExcepcion {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		modificarDatos(consultaVehiculoAtex5VO);
		consultaVehiculoAtex5Dao.guardarOActualizar(consultaVehiculoAtex5VO);
		resultado = tratarCobrarCreditos(consultaVehiculoAtex5VO, idUsuario);
		if (!resultado.getError()) {
			ResultBean resultBean = servicioCola.crearSolicitud(ProcesosEnum.CONSULTA_VEHICULO_ATEX5.getNombreEnum(), ConstantesProcesos.TIPO_SOLICITUD_PROCESO_CONSULTA_VEH_ATEX5, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD_NODO_2), TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum(), consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5().toString(), idUsuario, null, new BigDecimal(
					consultaVehiculoAtex5VO.getContrato().getIdContrato()));
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		}
		return resultado;
	}

	private void modificarDatos(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO) {
		if (consultaVehiculoAtex5VO.getMatricula() != null && !consultaVehiculoAtex5VO.getMatricula().isEmpty()) {
			consultaVehiculoAtex5VO.setMatricula(consultaVehiculoAtex5VO.getMatricula().toUpperCase());
		}
		if (consultaVehiculoAtex5VO.getBastidor() != null && !consultaVehiculoAtex5VO.getBastidor().isEmpty()) {
			consultaVehiculoAtex5VO.setBastidor(consultaVehiculoAtex5VO.getBastidor().toUpperCase());
		}
	}

	private ResultadoAtex5Bean tratarCobrarCreditos(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(COBRAR_CREDITOS))) {
				ResultBean resultBean = servicioCredito.validarCreditos(TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum(), new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()), BigDecimal.ONE);
				if (!resultBean.getError()) {
					resultBean = servicioCredito.descontarCreditos(TipoTramiteAtex5.Consulta_Vehiculo.getValorEnum(), new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()), BigDecimal.ONE,
							idUsuario, ConceptoCreditoFacturado.VTX5, consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5().toString());
				}
				if(resultBean.getError()){
					resultado.setError(Boolean.TRUE);
						if(resultBean.getListaMensajes().get(0)!=null){
							resultado.setMensaje(resultBean.getListaMensajes().get(0));
							
						}
					resultado.setListaErrores(resultBean.getListaMensajes());
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cobrar los creditos para realizar la consulta de vehiculo atex5, error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de cobrar los creditos.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultadoAtex5Bean obtenerXmlProceso(BigDecimal idConsultaVehiculoAtex5) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if(idConsultaVehiculoAtex5 != null){
				ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto = getConsultaVehiculoAtex5PorIdConsultaDto(idConsultaVehiculoAtex5, Boolean.FALSE);
				if(consultaVehiculoAtex5Dto != null){
					return obtenerXml(consultaVehiculoAtex5Dto);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se puede obtener el xml del vehiculoAtex5 porque no se encuentras datos para el id: " + idConsultaVehiculoAtex5);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se puede obtener el xml del vehiculoAtex5 porque el id de la consulta esta vacio.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el xml del vehiculoAtex5 para la consulta con id: "+idConsultaVehiculoAtex5 + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el xml del vehiculoAtex5 para la consulta con id: "+idConsultaVehiculoAtex5);
		}
		return resultado;
	}
	
	public ResultadoAtex5Bean obtenerXml(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5Dto) {
		ResultadoAtex5Bean resultDescargar = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (consultaVehiculoAtex5Dto != null) {
				if (!EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5Dto.getEstado())) {
					resultDescargar.setError(Boolean.TRUE);
					resultDescargar.setMensaje("La consulta no se encuentra en un estado valido para poder realizar la descarga.");
				} else {
					String nombreFichero = NOMBRE_CONSULTA_VEHICULO_XML + consultaVehiculoAtex5Dto.getNumExpediente();
					FileResultBean fichero = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ATEX5, ConstantesGestorFicheros.ATEX5_VEHICULO_XML, Utilidades
							.transformExpedienteFecha(consultaVehiculoAtex5Dto.getNumExpediente()), nombreFichero, ConstantesGestorFicheros.EXTENSION_XML);
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
			log.error("Ha sucedido un error a la hora de descargar el xml generado de la consulta de vehiculo atex5, error: ", e);
			resultDescargar.setError(Boolean.TRUE);
			resultDescargar.setMensaje("Ha sucedido un error a la hora de descargar el xml generado de la consulta de vehiculo atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de descargar el xml generado de la consulta de vehiculo atex5, error: ", e);
			resultDescargar.setError(Boolean.TRUE);
			resultDescargar.setMensaje("Ha sucedido un error a la hora de descargar el xml generado de la consulta de vehiculo atex5.");
		}
		return resultDescargar;
	}
	
	@Override
	@Transactional (readOnly=true)
	public ResultadoAtex5Bean descargarPdf(BigDecimal numExpediente) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = consultaVehiculoAtex5Dao.getConsultaVehiculoAtex5PorExpediente(numExpediente, true);
				if ( EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5VO.getEstado().toString())){
					FileResultBean ficheroPdf = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ATEX5, ConstantesGestorFicheros.ATEX5_VEHICULO_PDF, Utilidades
							.transformExpedienteFecha(numExpediente), numExpediente + ConstantesGestorFicheros.EXTENSION_PDF, null);
					if (ficheroPdf != null && ficheroPdf.getFile() != null) {
						resultado.setFicheroDescarga(ficheroPdf.getFile());
						resultado.setNombreFichero(numExpediente + ConstantesGestorFicheros.EXTENSION_PDF);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha encontrado ningún PDF para la consulta: " + numExpediente);
					}					
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El tramite " + numExpediente + " no se puede imprimir. Debe estar en estado Finalizado PDF." );
				}				
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos validos de la consulta: " + numExpediente + " para poder descargar la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf de la consulta de vehiculo atex5: " + numExpediente +", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf de la consulta de vehiculo atex5: " + numExpediente);
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public ResultadoAtex5Bean imprimirPdf(ConsultaVehiculoAtex5Dto consultaVehiculoAtex5, String xmlResultado) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (consultaVehiculoAtex5 != null) {
				resultado = servicioImpresionAtex5.imprimirPdfVehiculoAtex5(consultaVehiculoAtex5,xmlResultado);
				if(!resultado.getError()){
					resultado = guardarXml(consultaVehiculoAtex5.getNumExpediente(), ConstantesGestorFicheros.ATEX5_VEHICULO_XML,
						ServicioVehiculoAtex5.NOMBRE_CONSULTA_VEHICULO_XML, xmlResultado);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos validos de la consulta para poder realizar la impresión.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de la consulta de vehiculo atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de la consulta de vehiculo atex5.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void cambiarEstadoProceso(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, BigDecimal estadoNuevo, String respuesta) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if(idConsultaVehiculoAtex5 != null){
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorIdConsulta(idConsultaVehiculoAtex5, Boolean.FALSE);
				if(consultaVehiculoAtex5VO != null){
					BigDecimal estadoAnt = consultaVehiculoAtex5VO.getEstado();
					consultaVehiculoAtex5VO.setEstado(estadoNuevo);
					consultaVehiculoAtex5VO.setRespuesta(respuesta);
					consultaVehiculoAtex5Dao.actualizar(consultaVehiculoAtex5VO);
					servicioEvolucionAtex5.guardarEvolucion(consultaVehiculoAtex5VO.getNumExpediente(), idUsuario.longValue(), new Date(), estadoAnt, consultaVehiculoAtex5VO.getEstado(),
							TipoActualizacion.MOD.getValorEnum());
				} else {
					log.error("No se puede cambiar el estado porque no existen datos en la BBDD para la consulta de vehiculOAtex5 con id: " +idConsultaVehiculoAtex5);
					resultado.setError(Boolean.TRUE);
				}
			} else {
				log.error("No se puede cambiar el estado porque el id de la consulta de vehiculoAtex5 esta vacio.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de vehiculo atex5 con numero de expediente: " + idConsultaVehiculoAtex5 + ", error: ", e);
			resultado.setError(Boolean.TRUE);
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
	}
	
	@Override
	@Transactional
	public ResultadoAtex5Bean cambiarEstado(BigDecimal numExpediente, BigDecimal idUsuario, BigDecimal estadoNuevo, Boolean accionesAsociadas, String tipoTramite, ConceptoCreditoFacturado concepto) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (numExpediente != null) {
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorExpVO(numExpediente, false);
				if (consultaVehiculoAtex5VO != null) {
					BigDecimal estadoAnt = consultaVehiculoAtex5VO.getEstado();
					consultaVehiculoAtex5VO.setEstado(estadoNuevo);
					consultaVehiculoAtex5Dao.actualizar(consultaVehiculoAtex5VO);
					servicioEvolucionAtex5.guardarEvolucion(consultaVehiculoAtex5VO.getNumExpediente(), idUsuario.longValue(), new Date(), estadoAnt, consultaVehiculoAtex5VO.getEstado(),
							TipoActualizacion.MOD.getValorEnum());
					if (accionesAsociadas) {
						resultado = accionesCambiarEstadoSinValidacion(consultaVehiculoAtex5VO, estadoAnt, estadoNuevo, idUsuario, tipoTramite, concepto);
					}
					if (!resultado.getError()) {
						resultado.setMensaje("La consulta de vehiculo atex5 con número de expediente: " + numExpediente + ", se ha actualizado.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("La consulta de vehiculo atex5 con número de expediente: " + numExpediente + ", no se ha podido cambiar el estado porque no se encuentra disponible.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado de la consulta de vehiculo atex5 con numero de expediente: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado de la consulta de vehiculo atex5");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	private ResultadoAtex5Bean accionesCambiarEstadoSinValidacion(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO, BigDecimal estadoAnt, BigDecimal estadoNuevo, BigDecimal idUsuario, String tipoTramite, ConceptoCreditoFacturado concepto) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			if (EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(estadoAnt.toString())) {
				servicioCola.eliminar(new BigDecimal(consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5()),null);
				resultado = devolverCreditos(consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5(), idUsuario, new BigDecimal(consultaVehiculoAtex5VO.getIdContrato()), tipoTramite, concepto, 1);
			} 
			if (EstadoAtex5.Pdte_Respuesta_DGT.getValorEnum().equals(estadoNuevo.toString())) {
				return realizarConsulta(consultaVehiculoAtex5VO, idUsuario);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de vehiculo atex5 con num.Expediente: "
					+ consultaVehiculoAtex5VO.getNumExpediente() + ", error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de vehiculo atex5 con num.Expediente: "
					+ consultaVehiculoAtex5VO.getNumExpediente() + ".");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de vehiculo atex5 con num.Expediente: "
					+ consultaVehiculoAtex5VO.getNumExpediente() + ", error: ", e);
			resultado.setError(true);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar las acciones relacionadas con el cambio de estado de la consulta de vehiculo atex5 con num.Expediente: "
					+ consultaVehiculoAtex5VO.getNumExpediente() + ".");
		}
		return resultado;
	}

	private ResultadoAtex5Bean devolverCreditos(Long idConsultaVehiculoAtex5, BigDecimal idUsuario, BigDecimal idContrato, String tipoTramite, ConceptoCreditoFacturado concepto, int numeroSolcitudes) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		if ("SI".equalsIgnoreCase(gestorPropiedades.valorPropertie(COBRAR_CREDITOS))) {
			ResultBean resultBean = servicioCredito
					.devolverCreditos(tipoTramite, idContrato, numeroSolcitudes, idUsuario, concepto, idConsultaVehiculoAtex5.toString());
			if(resultBean.getError()){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultBean.getMensaje());
			}
		}
		return resultado;
	}

	public ResultadoAtex5Bean guardarXml(BigDecimal numExpediente, String subTipo, String nombre, String xmlRespuestaAtex5) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.ATEX5);
			fichero.setSubTipo(subTipo);
			fichero.setNombreDocumento(nombre + numExpediente);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
			fichero.setFecha(fecha);
			fichero.setFicheroByte(xmlRespuestaAtex5.getBytes());
			File file = gestorDocumentos.guardarFichero(fichero);
			if (file == null) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido guardar el xml con los datos de la consulta de vehiculo atex5.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el xml de la consulta de vehiculo atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml de la consulta de vehiculo atex5.");
		} catch (OegamExcepcion e) {
			log.error("Ha sucedido un error a la hora de guardar el xml de la consulta de vehiculo atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de guardar el xml de la consulta de vehiculo atex5.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean devolverCreditosWS(BigDecimal idConsultaVehiculoAtex5, BigDecimal idUsuario, BigDecimal idContrato, String tipoTramite, ConceptoCreditoFacturado concepto, int numeroSolicitudes) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try {
			ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorIdConsulta(idConsultaVehiculoAtex5, Boolean.FALSE);
			if (consultaVehiculoAtex5VO != null) {
				resultado = devolverCreditos(consultaVehiculoAtex5VO.getIdConsultaVehiculoAtex5(), idUsuario, idContrato, tipoTramite, concepto, numeroSolicitudes);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la consulta de vehiculos de atex5 para poder devolver el credito.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de devolver el credito de la consulta de vehiculo atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de devolver el credito de la consulta de vehiculo atex5.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultadoAtex5Bean asignarTasa(String[] sNumExpedientes,String codigoTasa, BigDecimal idUsuario,String tipoTramite) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try{
			Boolean tasaAsig = Boolean.FALSE;
			Date fecha = new Date();
			for (String numExpediente : sNumExpedientes) {
				BigDecimal numExped = new BigDecimal(numExpediente);
				fecha = new Date();
				resultado = actualizarConsultaVehiculoAtex5(codigoTasa,numExped,fecha,TipoActualizacion.AMT.getValorEnum(),idUsuario);
				if(!resultado.getError()){
					if(!tasaAsig){
						ResultBean respuestaTasaExpediente = servicioTasa.asignarMicroTasa(codigoTasa, numExpediente, idUsuario,fecha);
						if(respuestaTasaExpediente.getError()) {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje(respuestaTasaExpediente.getMensaje());
							break;
						} else {
							tasaAsig = Boolean.TRUE;
						}
					}
				} else {
					break;
				}
			}
		}catch(Exception e){
			log.error("Ha sucedido un error a la hora de recuperar el numero de expediente de la consulta de vehiculo atex5, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de recuperar el numero de expediente de la consulta de vehiculo atex5.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}else {
			log.info("La micro tasa ha sido asignada a todos los trámites" );
			resultado.setMensaje("La micro tasa ha sido asignada a todos los trámites" );
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoAtex5Bean desasignarTasa(String[] nNumExpediente, List<String> listaTasa,BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try{
			if(listaTasa != null && !listaTasa.isEmpty()){
				resultado = desasignarTasaConsultaVehiculoAtex5(nNumExpediente,idUsuario);
				if(!resultado.getError()){
					for(String codigoTasa : listaTasa){
						ResultBean resultTasa = servicioTasa.desasignarMicroTasa(codigoTasa, idUsuario);
						if(resultTasa.getError()){
							resultado.addError();
							resultado.setMensaje(resultTasa.getMensaje());
						} else {
							resultado.addOk();
							resultado.setMensaje("La tasa " + codigoTasa + " ha sido desasignada correctamente.");
						}
					}
				}
				
			}
		} catch(Exception e){
			log.error("Ha sucedido un error a la hora de desasignar las tasas, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de desasignar las tasas.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private ResultadoAtex5Bean desasignarTasaConsultaVehiculoAtex5(String[] nNumExpediente, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultadoVehAtex5 = new ResultadoAtex5Bean(Boolean.FALSE);
		for(String numExp: nNumExpediente){
			BigDecimal numExpediente = new BigDecimal(numExp);
			ConsultaVehiculoAtex5VO consultaVehiculoAtex5BBDD = getConsultaVehiculoAtex5PorExpVO(numExpediente, Boolean.FALSE);
			if(consultaVehiculoAtex5BBDD != null){
				consultaVehiculoAtex5BBDD.setCodigoTasa(null);
				consultaVehiculoAtex5Dao.actualizar(consultaVehiculoAtex5BBDD);
				servicioEvolucionAtex5.guardarEvolucion(numExpediente, idUsuario.longValue(), new Date(), consultaVehiculoAtex5BBDD.getEstado(), consultaVehiculoAtex5BBDD.getEstado(),  TipoActualizacion.DMT.getNombreEnum().toString());
			} else {
				resultadoVehAtex5.setError(Boolean.TRUE);
				resultadoVehAtex5.setMensaje("No se pueden desasignar las tasas seleccionadas porque no existen datos para la consulta con numero de expediente: " + numExp);
				break;
			}
		}
		return resultadoVehAtex5;
	}

	private ResultadoAtex5Bean actualizarConsultaVehiculoAtex5(String codigoTasa, BigDecimal numExped, Date fecha, String estadoNuevo, BigDecimal idUsuario) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try{
			if(numExped != null){
				ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorExpVO(numExped, false);
				if (consultaVehiculoAtex5VO != null) {
					consultaVehiculoAtex5VO.setCodigoTasa(codigoTasa);
					consultaVehiculoAtex5Dao.actualizar(consultaVehiculoAtex5VO);
					servicioEvolucionAtex5.guardarEvolucion(numExped, idUsuario.longValue(), fecha, consultaVehiculoAtex5VO.getEstado(), 
							consultaVehiculoAtex5VO.getEstado(),  TipoActualizacion.AMT.getNombreEnum().toString());
				}
			}
		}catch(Exception e){
			log.error("Error al guardar al actualizar la consulta de vehiculos Atex5");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar al actualizar la consulta de vehiculos Atex5");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoAtex5Bean validarTasasVehiculoAtex5(String[] sNumExpedientes) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		try{	
			String solicitudesMaximas = gestorPropiedades.valorPropertie("numero.maximo.tasas.atex5");
			if(solicitudesMaximas != null && solicitudesMaximas.isEmpty()){
				if(sNumExpedientes.length == Integer.valueOf(solicitudesMaximas)){
					for (String numExpediente : sNumExpedientes) {
						BigDecimal numExpedientes = new BigDecimal(numExpediente);
						ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorExpVO(numExpedientes, true);
						if (consultaVehiculoAtex5VO != null) {
			 				 if (!EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5VO.getEstado().toString())) {
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("La consulta no se encuentra en un estado valido para poder asignar una tasa");
							}else if (consultaVehiculoAtex5VO.getTasa() != null && consultaVehiculoAtex5VO.getTasa().getCodigoTasa() != null && !consultaVehiculoAtex5VO.getTasa().getCodigoTasa().isEmpty()){
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("Los trámites indicados ya tienes una tasa asignada.");
							}
					
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se han podido recuperar los expedientes seleccionados");
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha alcanzado el numero minimo de expedientes seleccionados para asignar la tasa");
				}
			}
		}catch (Exception e) {
				log.error("Error al recibir el numero total de peticiones atex5 para asignar una tasa");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Error al recibir el numero total de peticiones atex5 para asignar una tasa");
			}
		return resultado;
	}

	@Override
	@Transactional(readOnly = true)
	public ResultadoAtex5Bean validarDesasignarTasasVehiculoAtex5(String[] nNumExpediente) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		for(String numExpediente : nNumExpediente){
			BigDecimal numExpedientes = new BigDecimal(numExpediente);
			ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO = getConsultaVehiculoAtex5PorExpVO(numExpedientes, true);
			if (consultaVehiculoAtex5VO != null) {
				 if (!EstadoAtex5.Finalizado_PDF.getValorEnum().equals(consultaVehiculoAtex5VO.getEstado().toString())) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La consulta no se encuentra en un estado valido para poder desasignar una tasa");
				 }else if(consultaVehiculoAtex5VO.getTasa() != null 
						 && consultaVehiculoAtex5VO.getTasa().getCodigoTasa() != null 
						 && !consultaVehiculoAtex5VO.getTasa().getCodigoTasa().isEmpty()){
						if(resultado.getListaTasa() == null || resultado.getListaTasa().isEmpty()){
						 	resultado.setListaTasa(new ArrayList<String>());
						 	resultado.getListaTasa().add(consultaVehiculoAtex5VO.getTasa().getCodigoTasa());
						}else{
							Boolean existeTasa = Boolean.FALSE;
							for(String tasa : resultado.getListaTasa()){
								if(tasa.equals(consultaVehiculoAtex5VO.getTasa().getCodigoTasa())){
									existeTasa = Boolean.TRUE;
									break;
								}
							}
							if(!existeTasa){
								resultado.getListaTasa().add(consultaVehiculoAtex5VO.getTasa().getCodigoTasa());
							}
						}
				 }
			}
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<ConsultaVehiculoAtex5VO> getListaConsultasFinalizadas() {
			return consultaVehiculoAtex5Dao.getListaConsultaFinalizada();
	}
	
	@Override
	@Transactional
	public ResultadoAtex5Bean asignarTasasProceso(List<ConsultaVehiculoAtex5VO> listaVehiculoAtex5BBDD) {
		ResultadoAtex5Bean resultado = new ResultadoAtex5Bean(Boolean.FALSE);
		String idContrato = gestorPropiedades.valorPropertie("contrato.informatica");
		String idUsuario = gestorPropiedades.valorPropertie("usuario.informatica");
		try {
			ResultadoAtex5Bean resultObtTasa = servicioTasa.getTasaAtex5(new Long(idContrato));
			if(!resultObtTasa.getError()){
				Boolean asigTasa = Boolean.FALSE;
				String solicitudesMaximas = gestorPropiedades.valorPropertie("numero.maximo.tasas.atex5");
				int numConsultas = 0;
				for(ConsultaVehiculoAtex5VO consultaVehiculoAtex5VO : listaVehiculoAtex5BBDD){
					if(numConsultas < new Integer(solicitudesMaximas)){
						Date fecha = new Date();
						if(!asigTasa){
							servicioTasa.asignarMicroTasa(resultObtTasa.getCodigoTasa(), consultaVehiculoAtex5VO.getNumExpediente().toString(), new BigDecimal(idUsuario), fecha);
							asigTasa = Boolean.TRUE;
						}
						consultaVehiculoAtex5VO.setCodigoTasa(resultObtTasa.getCodigoTasa());
						consultaVehiculoAtex5Dao.actualizar(consultaVehiculoAtex5VO);
						servicioEvolucionAtex5.guardarEvolucion(consultaVehiculoAtex5VO.getNumExpediente(), new Long(idUsuario) , fecha, consultaVehiculoAtex5VO.getEstado(), 
								consultaVehiculoAtex5VO.getEstado(),  TipoActualizacion.AMT.getNombreEnum().toString());
					} else {
						break;
					}
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultObtTasa.getMensaje());
			}
		} catch (Exception e) {
			log.error("Error a la hora de asignar la tasa");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error a la hora de asignar la tasa");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

}
