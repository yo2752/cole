package trafico.modelo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBException;

import org.displaytag.properties.SortOrderEnum;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.enumerados.EstadoJustificante;
import org.gestoresmadrid.core.trafico.justificante.profesional.model.vo.JustificanteProfVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.model.service.ServicioJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.beans.ResultadoJustificanteProfesional;
import org.gestoresmadrid.oegam2comun.trafico.justificante.profesional.view.dto.JustificanteProfDto;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioJefaturaTrafico;
import org.gestoresmadrid.oegam2comun.trafico.tramitar.build.BuildJustifProfSega;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXParseException;

import colas.constantes.ConstantesProcesos;
import colas.modelo.ModeloSolicitud;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.Persona;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloBase;
import escrituras.modelo.ModeloColegiado;
import escrituras.utiles.enumerados.Decision;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.JefaturaTrafico;
import trafico.beans.JustificanteProfesional;
import trafico.beans.TramiteTraficoBean;
import trafico.beans.TramiteTraficoDuplicadoBean;
import trafico.beans.TramiteTraficoTransmisionBean;
import trafico.beans.VehiculoBean;
import trafico.beans.daos.JustificanteProfesionalCursor;
import trafico.beans.daos.pq_justificantes.BeanPQBUSCAR;
import trafico.beans.daos.pq_justificantes.BeanPQCREAR;
import trafico.beans.daos.pq_justificantes.BeanPQCREAR_VALIDACION;
import trafico.beans.daos.pq_justificantes.BeanPQINICIAR;
import trafico.beans.daos.pq_justificantes.BeanPQVALIDAR;
import trafico.beans.utiles.TransmisionTramiteTraficoBeanPQConversion;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.DocumentosJustificante;
import trafico.utiles.enumerados.MotivoJustificante;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.estructuras.ListaRegistros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.CrearJustificanteExcepcion;
import utilidades.web.excepciones.IniciarJustificanteExcepcion;
import utilidades.web.excepciones.ValidacionJustificantePorFechaExcepcion;
import utilidades.web.excepciones.ValidacionJustificanteRepetidoExcepcion;

public class ModeloJustificanteProfesional extends ModeloBase {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloJustificanteProfesional.class);
	public static final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	private ModeloSolicitud modeloSolicitud;
	private ModeloCreditosTrafico modeloCreditosTrafico;
	private ModeloColegiado modeloColegiado;
	private ModeloTrafico modeloTrafico;
	private Connection connection;

	@Autowired
	BuildJustifProfSega buildJustifProfSega;

	@Autowired
	ServicioJefaturaTrafico servicioJefaturaTrafico;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private ServicioJustificanteProfesional servicioJustificanteProfesional;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	TransmisionTramiteTraficoBeanPQConversion transmisionTramiteTraficoBeanPQConversion;

	@Autowired
	Utiles utiles;

	@Autowired
	private UtilesColegiado utilesColegiado;

	/**
	 * 
	 * @param tramiteTraficoTransmisionBean
	 * @param idContrato
	 * @param diasValidez
	 * @param motivo
	 * @param alias
	 * @throws Throwable
	 * @throws Exception
	 */
	public void validarYGenerarJustificanteTransmisionActual(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean,
			String aliasColegiado,BigDecimal idUsuario, BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado,
			MotivoJustificante motivo, DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador) throws Throwable {

		validarGenerarIssueProfessionalProof(TipoTramiteTrafico.Transmision, aliasColegiado, tramiteTraficoTransmisionBean.getTramiteTraficoBean(),
				tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona(), idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo,
				documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	/**
	 * 
	 * @param tramiteTraficoTransmisionBean
	 * @param idContrato
	 * @param diasValidez
	 * @param motivo
	 * @param alias
	 * @throws Throwable
	 */
	public void generarJustificanteTransmisionActual(TramiteTraficoTransmisionBean tramiteTraficoTransmisionBean,
			String aliasColegiado,BigDecimal idUsuario, BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado,
			MotivoJustificante motivo, DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador) throws Throwable {

		generarIssueProfesionalProof(TipoTramiteTrafico.Transmision,aliasColegiado,tramiteTraficoTransmisionBean.getTramiteTraficoBean(),
				tramiteTraficoTransmisionBean.getAdquirienteBean().getPersona(), idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo,
				documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	/**
	 * 
	 * @param tramiteTransmision
	 * @param aliasColegiado
	 * @param idContrato
	 * @param diasValidez
	 * @param motivo
	 * @throws Throwable
	 * @throws Exception
	 */
	public void validarYGenerarJustificanteTransmisionElectronica(
			TramiteTraficoTransmisionBean tramiteTransmision,String aliasColegiado,BigDecimal idUsuario, BigDecimal idContrato,
			Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo, DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador)
	throws Throwable{

		validarGenerarIssueProfessionalProof(TipoTramiteTrafico.TransmisionElectronica,aliasColegiado,tramiteTransmision.getTramiteTraficoBean(),
				tramiteTransmision.getAdquirienteBean().getPersona(), idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo, documentos,
				idFuerzasArmadas, isUsuarioAdministrador);
	}

	/**
	 * 
	 * @param tramiteTransmision
	 * @param aliasColegiado
	 * @param idContrato
	 * @param diasValidez
	 * @param motivo
	 * @throws Throwable
	 */
	public void generarJustificanteTransmisionElectronica(
			TramiteTraficoTransmisionBean tramiteTransmision,String aliasColegiado,BigDecimal idUsuario, BigDecimal idContrato, Integer diasValidez,
			String colegiadoContrato, String numColegiado, MotivoJustificante motivo, DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador)
				throws Throwable{

		generarIssueProfesionalProof(TipoTramiteTrafico.TransmisionElectronica,aliasColegiado,tramiteTransmision.getTramiteTraficoBean(),
				tramiteTransmision.getAdquirienteBean().getPersona(), idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado,
				motivo, documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	/**
	 * 
	 * @param transmisionelectronica
	 * @param traficoTramiteTransmisionBean
	 * @param aliasColegiado
	 * @param idContrato
	 * @param diasValidez
	 * @param motivo
	 * @return
	 * @throws Throwable
	 * @throws Exception
	 * @throws UnsupportedEncodingException
	 * @throws JAXBException
	 * @throws JAXBException
	 */
	private void validarGenerarIssueProfessionalProof(TipoTramiteTrafico tipoTramite,
			String aliasColegiado, TramiteTraficoBean tramite, Persona titular, BigDecimal idUsuario,
			BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo,
			DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador) throws Throwable{

		if (!tramite.getEstado().equals(EstadoTramiteTrafico.Finalizado_Telematicamente) || isUsuarioAdministrador){
			validarIssueProf(tramite, titular, motivo, documentos, diasValidez);

			generarIssueProfesionalProof(tipoTramite, aliasColegiado, tramite,
					titular, idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo, documentos, idFuerzasArmadas, isUsuarioAdministrador);
		} else {
			throw new OegamExcepcion(
					"No se puede realizar justificantes para un tramite finalizado telematicamente: " + tramite.getNumExpediente()
						+" Por favor contacte con el colegio para que sea autorizado.");
		}
	}

	public void generarIssueProfesionalProofForzado (TipoTramiteTrafico tipoTramite, String aliasColegiado,TramiteTraficoBean tramite,
			Persona titular, BigDecimal idUsuario,BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo,
			DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador) throws Throwable {

		generarIssueProfesionalProof(tipoTramite, aliasColegiado, tramite,
				titular, idUsuario, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo, documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	private void generarIssueProfesionalProof(TipoTramiteTrafico tipoTramite, String aliasColegiado,TramiteTraficoBean tramite,
			Persona titular, BigDecimal idUsuario,BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo,
			DocumentosJustificante documentos, boolean idFuerzasArmadas, boolean isUsuarioAdministrador) throws Throwable {

		ModeloCreditosTrafico modeloCreditosTrafico = getModeloCreditosTrafico();
		String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
		//Entra cuando es un usuario colegiado.
		if (!isUsuarioAdministrador){
			try{
				validarJustificante(idContrato, idUsuario, tramite.getNumExpediente());
			}catch(ValidacionJustificanteRepetidoExcepcion e){
				if("SI".equals(Constantes.PERMITE_SER_FORZADO)){
					if("SI".equals(generarJustifSega)){
						generarPeticionXml(
								aliasColegiado, tramite, titular, tipoTramite,diasValidez, utilesColegiado.getColegioDelContrato(),
								idContrato,utilesColegiado.getNumColegiadoSession(), motivo, documentos, idFuerzasArmadas);
					}

					iniciar(new BigDecimal(diasValidez), documentos.getNombreEnum(), utilesFecha.getFechaActual(), motivo, tramite.getNumExpediente(), idUsuario);
				}
				throw e;
			}catch(ValidacionJustificantePorFechaExcepcion e){
				if("SI".equals(generarJustifSega)){
					//Meter validacion para que antes de iniciar compruebe que no existe otro JP en estado 5.
					generarPeticionXml(
							aliasColegiado, tramite, titular, tipoTramite,diasValidez, utilesColegiado.getColegioDelContrato(),
							idContrato,utilesColegiado.getNumColegiadoSession(), motivo, documentos, idFuerzasArmadas);
				}

				iniciar(new BigDecimal(diasValidez), documentos.getNombreEnum(), utilesFecha.getFechaActual(), motivo, tramite.getNumExpediente(), idUsuario);
				throw e;
			}

			if("SI".equals(generarJustifSega)){
				//Meter validacion para que antes de iniciar compruebe que no existe otro JP en estado 5.
				generarPeticionXml(
						aliasColegiado, tramite, titular, tipoTramite,diasValidez, utilesColegiado.getColegioDelContrato(),
						idContrato,utilesColegiado.getNumColegiadoSession(), motivo, documentos, idFuerzasArmadas);
			}

			iniciar(new BigDecimal(diasValidez), documentos.getNombreEnum(), utilesFecha.getFechaActual(), motivo, tramite.getNumExpediente(), idUsuario);
			//Meter validacion para que antes de iniciar compruebe que no existe otro JP en estado 5.
			modeloCreditosTrafico.descontarCreditosExcep(idContrato, utiles.convertirIntegerABigDecimal(1), "T9", idUsuario);
			try{
				ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
				if (servicioCreditoFacturado != null ){
					servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.SJP, idContrato.longValue(), "T9", tramite.getNumExpediente().toString());
				}
			}catch(Exception eg){
				log.error("No se pudo trazar la devolucion de creditos");
			}
			crearSolicitudJustificantesPro(tramite.getNumExpediente(), idUsuario, idContrato, tipoTramite);

		//Entra cuando es usuario que puede forzar
		}else{
			if("SI".equals(generarJustifSega)){
				generarPeticionXml(
						aliasColegiado, tramite, titular, tipoTramite,diasValidez, colegiadoContrato,
						idContrato,numColegiado, motivo, documentos, idFuerzasArmadas);
			}

			modeloCreditosTrafico.descontarCreditosExcep(idContrato, utiles.convertirIntegerABigDecimal(1), "T9", idUsuario);
			try{
				ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
				if (servicioCreditoFacturado != null ){
					servicioCreditoFacturado.anotarGasto(1, ConceptoCreditoFacturado.SJP, idContrato.longValue(), "T9", tramite.getNumExpediente().toString());
				}
			}catch(Exception eg){
				log.error("No se pudo trazar la devolucion de creditos");
			}

			crearSolicitudJustificantesPro(tramite.getNumExpediente(), idUsuario, idContrato, tipoTramite);

			servicioJustificanteProfesional.cambiarEstadoConEvolucionSega(tramite.getNumExpediente(), EstadoJustificante.Pendiente_autorizacion_colegio, EstadoJustificante.Iniciado, utilesColegiado.getIdUsuarioSessionBigDecimal());
		}
	}

	/**
	 * Nos dice si se premite generar otra peticion de justificante, mirando si ya
	 * hay un justificante que haya generado solicitud(iniciado)
	 * 
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 * @throws SQLException
	 */
	public boolean hayJustificanteIniciado(BigDecimal numExpediente) throws OegamExcepcion {
		log.info("hayJustificanteIniciado: " + numExpediente);

		JustificanteProfDto justificante = servicioJustificanteProfesional.getJustificanteProfesionalPorNumExpediente(
				numExpediente, new BigDecimal(EstadoJustificante.Iniciado.getValorEnum()));

		return (justificante != null);
	}

	/**
	 * Nos dice si se permite generar otra petición de justificante, mirando si ya hay un justificante que haya generado solicitud(pendiente autorización)
	 * @param numExpediente
	 * @return
	 * @throws OegamExcepcion
	 */
	public boolean hayJustificantePendienteAutorizacion(BigDecimal numExpediente) throws OegamExcepcion {
		log.info("hayJustificantePendienteAutorizacion: " + numExpediente);

		JustificanteProfDto justificante = servicioJustificanteProfesional.getJustificanteProfesionalPorNumExpediente(
				numExpediente, new BigDecimal(EstadoJustificante.Pendiente_autorizacion_colegio.getValorEnum()));

		return (justificante != null);
	}

	// INICIO MANTIS 0011927: impresión de justificantes profesionales
	/**
	 * Nos dice si hay algún justificante del expediente recibido están en el estado recibido.
	 * 
	 * @param numExpediente número de expediente.
	 * @param estadoJustificantes estado de los justificantes.
	 * 
	 * @return Boolean - verdadero si hay algún justificante del expediente recibido están en el estado recibido.
	 * @throws OegamExcepcion OegamExcepcion.
	 */
	public Boolean hayJustificantesEnEstado(String numExpediente, EstadoJustificante estadoJustificantes) {
		log.info("hayJustificantesEnEstado: " + numExpediente + ", " + estadoJustificantes.getValorEnum());

		JustificanteProfDto justificante = servicioJustificanteProfesional.getJustificanteProfesionalPorNumExpediente(
				new BigDecimal(numExpediente), new BigDecimal(estadoJustificantes.getValorEnum()));

		return (justificante != null);
	}
	// FIN MANTIS 0011927

	private void validarIssueProf(TramiteTraficoBean tramite, Persona titular, MotivoJustificante motivoJustificante, DocumentosJustificante documentosJustificante, Integer diasValidez)
	throws OegamExcepcion, ValidacionJustificanteRepetidoExcepcion, SQLException {

		if(hayJustificanteIniciado(tramite.getNumExpediente()))
			throw new OegamExcepcion(
			"Existe un Justificante Profesional en proceso para el tramite: "+ tramite.getNumExpediente() + " por favor espere a la respuesta.");

		if(hayJustificantePendienteAutorizacion(tramite.getNumExpediente()))
			throw new OegamExcepcion(
			"Ya existe un Justificante Profesional generado para el trámite: " + tramite.getNumExpediente()
				+" que se encuentra en estado pendiente autorización del colegio. Por favor contacte con el colegio para que sea autorizado.");

		validarElementosObligatoriosIssue(tramite.getVehiculo(), tramite.getJefaturaTrafico(), titular, motivoJustificante, documentosJustificante, diasValidez);
	}

	private void validarJustificante(BigDecimal idContrato, BigDecimal idUsuario, BigDecimal numExpediente)
	throws OegamExcepcion {

		BeanPQVALIDAR beanPQvalidar = new BeanPQVALIDAR();
		beanPQvalidar.setP_ID_CONTRATO_SESSION(idContrato);
		beanPQvalidar.setP_ID_USUARIO(idUsuario);
		beanPQvalidar.setP_NUM_EXPEDIENTE(numExpediente);
		beanPQvalidar.execute();

		log.info(ConstantesPQ.LOG_P_CODE + beanPQvalidar.getP_CODE());
		log.info(ConstantesPQ.LOG_P_INFORMACION + beanPQvalidar.getP_INFORMACION());
		log.info(ConstantesPQ.LOG_P_SQLERRM + beanPQvalidar.getP_SQLERRM());
		log.info("P_TIPO_VALIDO: " + beanPQvalidar.getP_TIPO_VALIDO());
		log.info("P_VALIDO: " + beanPQvalidar.getP_VALIDO());

		if(!beanPQvalidar.getP_CODE().equals(ConstantesPQ.pCodeOk)) {
			log.error(ConstantesPQ.LOG_P_SQLERRM + beanPQvalidar.getP_SQLERRM());
			throw new OegamExcepcion(beanPQvalidar.getP_SQLERRM());
		}
		if (!beanPQvalidar.getP_VALIDO().equals(new BigDecimal(1))) {
			if (beanPQvalidar.getP_TIPO_VALIDO().equals(new BigDecimal(1))) {
				throw new ValidacionJustificanteRepetidoExcepcion(
						"El Colegio le recuerda que solo se deberá de solicitar repetidos JP para la tramitación de un expediente de transferencia");
			}

			if (beanPQvalidar.getP_TIPO_VALIDO().equals(new BigDecimal(2))) {
				throw new ValidacionJustificantePorFechaExcepcion("");
			}
		}
	}

	private void generarPeticionXml(String aliasColegiado,
			TramiteTraficoBean tramite, Persona titular, TipoTramiteTrafico tipoTramite,
			Integer diasValidez, String colegioContrato, BigDecimal idContrato, String numColegiado, MotivoJustificante motivo,
			DocumentosJustificante documentos, boolean idFuerzasArmadas) throws Throwable {

		construirPeticionXml(aliasColegiado, tramite, titular, tipoTramite, diasValidez,
				colegioContrato, idContrato, numColegiado, motivo, documentos, idFuerzasArmadas);
	}

	private void validarElementosObligatoriosIssue(
			VehiculoBean vehiculo, JefaturaTrafico jefaturaTrafico, Persona titular, MotivoJustificante motivoJustificante, DocumentosJustificante documentosJustificante, Integer diasValidez) throws OegamExcepcion {

		ArrayList<String> obligatorioVehiculoArray = new ArrayList<>();
		ArrayList<String> obligatorioTitularArray = new ArrayList<>();
		boolean lanzarError = false;
		String mensaje = "Faltan datos obligatorios: ";

		if (vehiculo!=null){
			if (vehiculo.getMarcaBean().getCodigoMarca()==null||
					"".equals(vehiculo.getMarcaBean().getCodigoMarca())||
					new BigDecimal(-1).equals(vehiculo.getMarcaBean().getCodigoMarca()))
				obligatorioVehiculoArray.add("Marca"); 	

			if (vehiculo.getMatricula()==null||
					"".equals(vehiculo.getMatricula()))
				obligatorioVehiculoArray.add("Matricula");

			if (vehiculo.getBastidor()==null||
					"".equals(vehiculo.getBastidor()))
				obligatorioVehiculoArray.add("Bastidor");

			if (jefaturaTrafico==null)
				obligatorioVehiculoArray.add("Jefatura");

			if (jefaturaTrafico!=null && (jefaturaTrafico.getJefaturaProvincial()==null || "".equals(jefaturaTrafico.getJefaturaProvincial())))
				obligatorioVehiculoArray.add("Jefatura");

			if (vehiculo.getModelo()==null||
					"".equals(vehiculo.getModelo()))
				obligatorioVehiculoArray.add("Modelo");

			if (vehiculo.getTipoVehiculoBean().getTipoVehiculo()==null||
					"-1".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo())||
					"".equals(vehiculo.getTipoVehiculoBean().getTipoVehiculo()))
				obligatorioVehiculoArray.add("Tipo de vehículo");

			if (!obligatorioVehiculoArray.isEmpty()){
				lanzarError = true;
				mensaje += " Faltan datos del vehículo: " + listarObligatorios(obligatorioVehiculoArray) + ".";
			}
		}else{
			lanzarError = true;
			mensaje += " El vehículo es obligatorio.";
		}

		if (titular!=null){
			if (titular.getNif()==null ||
					"".equals(titular.getNif()))
				obligatorioTitularArray.add("NIF");

			if(titular.getDireccion() == null){
				obligatorioTitularArray.add("Dirección");
			}

			if (titular.getDireccion().getNombreVia()==null||
					"".equals(titular.getDireccion().getNombreVia()))
				obligatorioTitularArray.add("Nombre de vía");

			if (titular.getApellido1RazonSocial()==null||
					"".equals(titular.getApellido1RazonSocial()))
				obligatorioTitularArray.add("Nombre/Apellido o Razón Social");

			if (titular.getDireccion().getNumero()==null||
					"".equals(titular.getDireccion().getNumero()))
				obligatorioTitularArray.add("Número");

			if (titular.getDireccion().getMunicipio() == null 
					|| titular.getDireccion().getMunicipio().getIdMunicipio() == null
					|| titular.getDireccion().getMunicipio().getIdMunicipio().isEmpty()){
				obligatorioTitularArray.add("Municipio");
			}
			if (titular.getDireccion().getMunicipio() == null
					|| titular.getDireccion().getMunicipio().getProvincia() == null
					|| titular.getDireccion().getMunicipio().getProvincia().getIdProvincia() == null
					|| titular.getDireccion().getMunicipio().getProvincia().getIdProvincia().isEmpty()){
				obligatorioTitularArray.add("Provincia");
			}

			if (obligatorioTitularArray.size()>0){
				lanzarError = true;
				mensaje += " Faltan datos del titular: "+ listarObligatorios(obligatorioTitularArray) + ".";
			}
		} else{
			lanzarError = true;
			mensaje += " El titular es obligatorio.";
		}

		if(motivoJustificante == null){
			lanzarError = true;
			mensaje += " El motivo del justificante es obligatorio.";
		}

		if(documentosJustificante == null){
			lanzarError = true;
			mensaje += " El documento del justificante es obligatorio.";
		}

		if(diasValidez == null){
			lanzarError = true;
			mensaje += " Los días de validez es obligatorio.";
		} else if(diasValidez <= 0){
			lanzarError = true;
			mensaje += " Los días de validez tiene que ser mayor que cero.";
		}

		if(lanzarError)
			throw new OegamExcepcion(mensaje);
	}

	private String listarObligatorios(ArrayList<String> obligatArray) {
		StringBuffer strBuff = new StringBuffer();
		if (!obligatArray.isEmpty())
			strBuff.append(obligatArray.get(0));
		for (int i = 1; i < obligatArray.size(); i++) {
			strBuff.append(",");
			strBuff.append(obligatArray.get(i));
		}

		obligatArray.clear();

		return strBuff.toString();
	}

	/**
	 * 
	 * @param numExpediente
	 * @param usuario
	 * @param idContrato 
	 * @param tipoTramite
	 * @return
	 * @throws CrearSolicitudExcepcion
	 */
	public void crearSolicitudJustificantesPro(BigDecimal numExpediente,BigDecimal usuario,BigDecimal idContrato, TipoTramiteTrafico tipoTramite)
	throws CrearSolicitudExcepcion {
		String generarJustifSega = gestorPropiedades.valorPropertie("nuevas.url.sega.justProfesional");
		if("SI".equals(generarJustifSega)){
			try {
				ResultBean resultado = servicioCola.crearSolicitud(ProcesosEnum.JUSTIFICANTES_SEGA.getNombreEnum(), ConstantesProcesos.PROCESO_ISSUEPROFESSIONALPROOF + numExpediente, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD),
						tipoTramite.getValorEnum(), numExpediente.toString(), usuario, null, idContrato);
				if(resultado.getError()){
					throw new CrearSolicitudExcepcion(resultado.getMensaje());
				}
			} catch (OegamExcepcion e) {
				log.error("Ha ocurrido un error a la hora de encolar el Justificante, error:" ,e, numExpediente.toString());
				throw new CrearSolicitudExcepcion(e.getMensajeError1());
			}
		}else{
			try {
				servicioCola.crearSolicitud( ConstantesProcesos.PROCESO_JUSTIFICANTE_PROF, null, gestorPropiedades.valorPropertie(NOMBRE_HOST_SOLICITUD), tipoTramite.getValorEnum(), numExpediente.toString(), usuario, null, idContrato);
			} catch (OegamExcepcion e) {
				log.error("Ha ocurrido un error a la hora de encolar el Justificante, error:" ,e, numExpediente.toString());
				throw new CrearSolicitudExcepcion(e.getMensajeError1());
			}
		}
	}

	/**
	 * 
	 * @param beanPQBUSCAR
	 * @return
	 * @throws OegamExcepcion
	 */
	public List<Object> buscar(BeanPQBUSCAR beanPQBUSCAR) throws OegamExcepcion{
		List<Object> lista = beanPQBUSCAR.execute(JustificanteProfesionalCursor.class);

		//Recuperamos información de respuesta
		BigDecimal pCodeTramite = beanPQBUSCAR.getP_CODE();
		log.debug(ConstantesPQ.LOG_P_CODE + pCodeTramite);
		log.debug(ConstantesPQ.LOG_P_SQLERRM + beanPQBUSCAR.getP_SQLERRM());
		log.debug(ConstantesPQ.LOG_P_CUENTA + beanPQBUSCAR.getCUENTA());

		if ((!ConstantesPQ.pCodeOk.equals(beanPQBUSCAR.getP_CODE()))) {
			throw new OegamExcepcion("error al buscar justificantes:" + beanPQBUSCAR.getP_SQLERRM());
		}
		return lista;
	}

	/**
	 * @param pagina
	 * @param numeroElementosPagina
	 * @param orden
	 * @param columnaOrden
	 */
	@Override
	public ListaRegistros listarTabla(Integer pagina, Integer numeroElementosPagina, SortOrderEnum orden, String columnaOrden) {

		ListaRegistros listaRegistros = new ListaRegistros();
		BeanPQBUSCAR beanPqBuscar = new BeanPQBUSCAR();
		//Datos de paginación, que pasamos por defecto
		beanPqBuscar.setPAGINA(new BigDecimal(pagina));
		beanPqBuscar.setNUM_REG(new BigDecimal(numeroElementosPagina));
		beanPqBuscar.setCOLUMNA_ORDEN(columnaOrden);
		beanPqBuscar.setORDEN(orden.toString().toUpperCase());

		//Datos de búsqueda del formulario.

		if((BigDecimal)getParametrosBusqueda().get(ConstantesSession.ID_JUSTIFICANTE)!= null) {
			beanPqBuscar.setP_ID_JUSTIFICANTE((BigDecimal)getParametrosBusqueda().get(ConstantesSession.ID_JUSTIFICANTE));
		} else{
			beanPqBuscar.setP_ID_JUSTIFICANTE(null);
		}

		if((BigDecimal)getParametrosBusqueda().get(ConstantesSession.NUM_EXPEDIENTE)!= null) {
			beanPqBuscar.setP_NUM_EXPEDIENTE((BigDecimal)getParametrosBusqueda().get(ConstantesSession.NUM_EXPEDIENTE));
		} else {
			beanPqBuscar.setP_NUM_EXPEDIENTE(null);
		}

		if((String)getParametrosBusqueda().get(ConstantesSession.MATRICULA_CONSULTA)!= null) {
			// Mantis 7815 (jose.rumbo): convertir a mayúsculas la matrícula para realizar la búsqueda de justificantes
			String matricula = (String)getParametrosBusqueda().get(ConstantesSession.MATRICULA_CONSULTA);
			beanPqBuscar.setP_MATRICULA(matricula.toUpperCase());
		} else {
			beanPqBuscar.setP_MATRICULA(null);
		}

		FechaFraccionada fechaJustificante = (FechaFraccionada)getParametrosBusqueda().get(ConstantesSession.FECHA_JUSTIFICANTE);
		if (fechaJustificante!=null){
			if(!fechaJustificante.isfechaInicioNula()){
				beanPqBuscar.setP_FECHA_INICIO(utilesFecha.getTimestamp(fechaJustificante.getFechaInicio()));
			}
			if(!fechaJustificante.isfechaFinNula()){
				beanPqBuscar.setP_FECHA_FIN((utilesFecha.getTimestamp(fechaJustificante.getFechaFin())));
			}
		}

		beanPqBuscar.setP_ID_CONTRATO_SESSION(utilesColegiado.getIdContratoSessionBigDecimal());
		beanPqBuscar.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());

		List<Object> lista = new ArrayList<>();
		try {
			lista = buscar(beanPqBuscar);
		} catch (OegamExcepcion e) {
			log.error(e);
		}

		JustificanteProfesional linea;
		List<Object> listaBeanVista = new ArrayList<>();
		for (Object object : lista) {
			linea = new JustificanteProfesional();

			JustificanteProfesionalCursor deCursor = (JustificanteProfesionalCursor) object;
			linea = transmisionTramiteTraficoBeanPQConversion.justificanteProfesionalConvertirBeanPQ(deCursor);
			listaBeanVista.add(linea);
		}

		listaRegistros.setTamano(beanPqBuscar.getCUENTA().intValue());
		listaRegistros.setLista(listaBeanVista);
		return listaRegistros;
	}

	/**Actualiza una fila de la tabla justificantes añadiendo el estado a ok para que se pueda imprimir.
	 * 
	 * @param justificanteProf
	 * @throws ParseException
	 */
	public void crear(JustificanteProfesional justificanteProf, BigDecimal idUsuario) throws ParseException,CrearJustificanteExcepcion{

		BeanPQCREAR beanPQCREAR = new BeanPQCREAR();
		beanPQCREAR.setP_DIAS_VALIDEZ(justificanteProf.getDias_validez());
		beanPQCREAR.setP_DOCUMENTOS(justificanteProf.getDocumentos());

		beanPQCREAR.setP_FECHA_INICIO(justificanteProf.getFecha_inicio().getTimestamp());
		beanPQCREAR.setP_ID_JUSTIFICANTE(justificanteProf.getId_justificante());
		beanPQCREAR.setP_NUM_EXPEDIENTE(justificanteProf.getNum_expediente());
		beanPQCREAR.setP_ID_USUARIO(idUsuario);

		log.info("P_DIAS_VALIDEZ: " + beanPQCREAR.getP_DIAS_VALIDEZ());
		log.info("P_DOCUMENTOS: " + beanPQCREAR.getP_DOCUMENTOS());
		log.info("P_FECHA_FIN: " + beanPQCREAR.getP_FECHA_FIN());
		log.info("P_FECHA_INICIO: " + beanPQCREAR.getP_FECHA_INICIO());
		log.info("P_ID_JUSTIFICANTE: " + beanPQCREAR.getP_ID_JUSTIFICANTE());
		log.info("P_NUM_EXPEDIENTE: " + beanPQCREAR.getP_NUM_EXPEDIENTE());
		log.info("P_ID_USUARIO: " + beanPQCREAR.getP_ID_USUARIO());

		beanPQCREAR.execute();
		log.info(ConstantesPQ.LOG_P_CODE + beanPQCREAR.getP_CODE());
		if (!beanPQCREAR.getP_CODE().equals(new BigDecimal(0))) {
			log.error(ConstantesPQ.LOG_P_SQLERRM + beanPQCREAR.getP_SQLERRM());
			throw new CrearJustificanteExcepcion(beanPQCREAR.getP_SQLERRM());
		}
	}

	public void crear_validacion(JustificanteProfesional justificanteProf) throws ParseException,CrearJustificanteExcepcion{

		log.info("Se va a crear el justificante de la validación");
		BeanPQCREAR_VALIDACION beanPQCREARVALIDACION = new BeanPQCREAR_VALIDACION();

		beanPQCREARVALIDACION.setP_CODIGO_VERIFICACION(justificanteProf.getCodigoVerificacion());
		beanPQCREARVALIDACION.setP_VERIFICADO(justificanteProf.getVerificado()? Decision.Si.getNombreBD():Decision.No.getNombreBD());

		beanPQCREARVALIDACION.execute();
		log.info(ConstantesPQ.LOG_P_CODE + beanPQCREARVALIDACION.getP_CODE());
		if (!beanPQCREARVALIDACION.getP_CODE().equals(new BigDecimal(0))) {
			log.error(ConstantesPQ.LOG_P_SQLERRM + beanPQCREARVALIDACION.getP_SQLERRM());
			throw new CrearJustificanteExcepcion(beanPQCREARVALIDACION.getP_SQLERRM());
		}
	}

	public void validarYGenerarJustificanteTramiteDuplicados(TramiteTraficoDuplicadoBean tramiteDuplicados, String aliasColegiado,BigDecimal idUsuarioDeSesion,
			BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo, DocumentosJustificante documentos,
			boolean idFuerzasArmadas, boolean isUsuarioAdministrador) 
				throws Throwable{

		validarGenerarIssueProfessionalProof(TipoTramiteTrafico.Duplicado, aliasColegiado, tramiteDuplicados.getTramiteTrafico(),
				tramiteDuplicados.getTitular().getPersona(), idUsuarioDeSesion, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo, documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	public void validarYGenerarJustificanteTramiteBajas(TramiteTraficoDuplicadoBean tramiteDuplicados, String aliasColegiado,BigDecimal idUsuarioDeSesion,
			BigDecimal idContrato, Integer diasValidez, String colegiadoContrato, String numColegiado, MotivoJustificante motivo, DocumentosJustificante documentos,
			boolean idFuerzasArmadas, boolean isUsuarioAdministrador) 
				throws Throwable{
		validarGenerarIssueProfessionalProof(TipoTramiteTrafico.Baja, aliasColegiado, tramiteDuplicados.getTramiteTrafico(),
				tramiteDuplicados.getTitular().getPersona(), idUsuarioDeSesion, idContrato, diasValidez, colegiadoContrato, numColegiado, motivo, documentos, idFuerzasArmadas, isUsuarioAdministrador);
	}

	public void iniciar(BigDecimal diasValidez, String documentos, Fecha fecha_inicio, MotivoJustificante motivo, BigDecimal num_expediente, BigDecimal idUsuario) throws IniciarJustificanteExcepcion{

		BeanPQINICIAR beanPQIniciar = new BeanPQINICIAR();
		beanPQIniciar.setP_DIAS_VALIDEZ(diasValidez);
		beanPQIniciar.setP_DOCUMENTOS(documentos);
		beanPQIniciar.setP_FECHA_FIN(utilesFecha.sumaDias(fecha_inicio,diasValidez.toString()));
		beanPQIniciar.setP_ID_USUARIO(idUsuario);
		beanPQIniciar.setP_MOTIVO(motivo.getValorEnum());

		try {
			beanPQIniciar.setP_FECHA_INICIO(fecha_inicio.getTimestamp());
		} catch (ParseException e) {
			log.error(e);
			throw new IniciarJustificanteExcepcion("Hubo un problema con la fecha de inicio.");	}
		beanPQIniciar.setP_NUM_EXPEDIENTE(num_expediente);

		log.info("P_DIAS_VALIDEZ: " + beanPQIniciar.getP_DIAS_VALIDEZ());
		log.info("P_DOCUMENTOS: " + beanPQIniciar.getP_DOCUMENTOS());
		log.info("P_FECHA_FIN: " + beanPQIniciar.getP_FECHA_FIN());
		log.info("P_FECHA_INICIO: " + beanPQIniciar.getP_FECHA_INICIO());
		log.info("P_NUM_EXPEDIENTE: " + beanPQIniciar.getP_NUM_EXPEDIENTE());
		log.info("P_ID_USUARIO: " + beanPQIniciar.getP_ID_USUARIO());
		log.info("P_MOTIVO: " + beanPQIniciar.getP_MOTIVO());

		beanPQIniciar.execute();
		log.info(ConstantesPQ.LOG_P_CODE + beanPQIniciar.getP_CODE());
		if (!beanPQIniciar.getP_CODE().equals(new BigDecimal(0))) {
			log.error(ConstantesPQ.LOG_P_SQLERRM + beanPQIniciar.getP_SQLERRM());
			throw new IniciarJustificanteExcepcion(beanPQIniciar.getP_SQLERRM());
		}
	}

	public JustificanteProfVO buscarPorCodigoVerificacion(String codigoVerificacion) throws OegamExcepcion{
		return servicioJustificanteProfesional.getJustificanteProfesionalPorCodigoVerificacion(codigoVerificacion);
	}

	public void crearSolicitudVerificar(String codigoSeguroVerificacion, BigDecimal idUsuario) throws OegamExcepcion{
		getModeloSolicitud().crearSolicitud(null, idUsuario, null, ConstantesProcesos.PROCESO_VERIFYPROFESSIONALPROOF, codigoSeguroVerificacion);
	}

	private void construirPeticionXml(String aliasColegiado, TramiteTraficoBean tramite, Persona titular, TipoTramiteTrafico tipoTramite,
		Integer diasValidez, String colegioContrato, BigDecimal idContrato, String numColegiado, MotivoJustificante motivo, DocumentosJustificante documentos,
		boolean idFuerzasArmadas) throws Throwable {

		ResultadoJustificanteProfesional resultado = buildJustifProfSega.generarXmlJustiProfesional(tramite,titular,tipoTramite,diasValidez,idContrato,motivo,documentos,idFuerzasArmadas);
		if(resultado.isError()){
			throw new Throwable(resultado.getMensajeError());
		}
	}

	/**
	 * Muestra en claro el mensaje de parseSAXParseException
	 * Al momento de creacion de este metodo, solo formatea el error: cvc-maxLength-valid
	 */
	public String parseSAXParseException(SAXParseException spe) {
		if (spe == null) {
			return null;
		} else {
			if (spe.getMessage() != null && !spe.getMessage().isEmpty()) {
				String ERROR_LENGTH = "^(cvc-maxLength-valid: Value ')(.+)(' with length = ')(.+)(' is not facet-valid with respect to maxLength ')(.+)(' for type ')(.+)";
				Pattern pattern = Pattern.compile(ERROR_LENGTH);
				Matcher matcher = pattern.matcher(spe.getMessage());
				if (matcher.find()) {
					return new StringBuffer("La lóngitud de \"")
							.append(matcher.group(2))
							.append("\" excede el máximo permitido (")
							.append(matcher.group(6)).append(")").toString();
				}
			}
			return spe.getMessage();
		}
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloSolicitud getModeloSolicitud() {
		if (modeloSolicitud == null) {
			modeloSolicitud = new ModeloSolicitud();
		}
		return modeloSolicitud;
	}

	public void setModeloSolicitud(ModeloSolicitud modeloSolicitud) {
		this.modeloSolicitud = modeloSolicitud;
	}

	public ModeloCreditosTrafico getModeloCreditosTrafico() {
		if (modeloCreditosTrafico == null) {
			modeloCreditosTrafico = new ModeloCreditosTrafico();
		}
		return modeloCreditosTrafico;
	}

	public void setModeloCreditosTrafico(ModeloCreditosTrafico modeloCreditosTrafico) {
		this.modeloCreditosTrafico = modeloCreditosTrafico;
	}

	public ModeloColegiado getModeloColegiado() {
		if (modeloColegiado == null) {
			modeloColegiado = new ModeloColegiado();
		}
		return modeloColegiado;
	}

	public void setModeloColegiado(ModeloColegiado modeloColegiado) {
		this.modeloColegiado = modeloColegiado;
	}

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}