package trafico.modelo.ivtm.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmWS;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//TODO MPC. Cambio IVTM. Clase nueva.
import hibernate.entities.trafico.IvtmConsultaMatriculacion;
import trafico.dao.implHibernate.ivtm.IvtmConsMatriculacionDaoImplHibernate;
import trafico.dao.ivtm.IvtmConsMatriculacionDaoInterface;
import trafico.generarPeticiones.ivtm.GenerarPeticionIVTM;
import trafico.modelo.ivtm.IVTMConsModeloMatriculacionInterface;
import trafico.utiles.constantes.ConstantesIVTM;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.CrearSolicitudExcepcion;
import utilidades.web.OegamExcepcion;

public class IVTMConsModeloMatriculacionImpl extends IVTMModeloImpl implements IVTMConsModeloMatriculacionInterface {

	@Autowired
	private ServicioCola servicioCola;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;

	private static final ILoggerOegam log = LoggerOegam.getLogger(IVTMConsModeloMatriculacionImpl.class);
	private IvtmConsMatriculacionDaoInterface ivtmDao;

	public IVTMConsModeloMatriculacionImpl() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		ivtmDao = new IvtmConsMatriculacionDaoImplHibernate(new IvtmConsultaMatriculacion());
	}

	@Override
	public boolean solicitarConsulta(IvtmConsultaMatriculacionDto ivtmConsultaDto){
		log.debug("Ha entrado método  solicitar servicio consulta");
		IvtmConsultaMatriculacion ivtmConsulta;
		try {
			ivtmConsulta = convertirIvtmConsultaDTOToIvtmConsulta(ivtmConsultaDto);
			//TODO MPC. Cambio IVTM. Descomentar esto y el catch cuando se habilite
//			new ModeloSolicitud().crearSolicitud(ivtmConsulta.getIdPeticion(),
//					utilesColegiado.getIdUsuarioDeSesion(),
//					TipoTramiteTrafico.Consulta_IVTM,
//					ConstantesProcesos.PROCESO_CONSULTA_IVTM,
//					ConstantesIVTM.TIPO_CONSULTA_IVTM_WS);

			servicioCola.crearSolicitud(ProcesosEnum.IVTM_CONSULTA.getNombreEnum(),
										ConstantesIVTM.TIPO_CONSULTA_IVTM_WS.toString(),
										 gestorPropiedades.valorPropertie(ServicioIvtmWS.NOMBRE_HOST_SOLICITUD),
										TipoTramiteTrafico.Consulta_IVTM.getValorEnum(),
										ivtmConsulta.getIdPeticion().toString(),
										utilesColegiado.getIdUsuarioSessionBigDecimal(),
										"",
										utilesColegiado.getIdContratoSessionBigDecimal());
			return true;
		} catch (CrearSolicitudExcepcion e) {
			log.error("Un error ha ocurrido al crear una Solicitud para encolar la peteción");
			log.error(e.getMessage());
			return false;
		} catch (Exception e1) {
			log.error("Un error ha ocurrido en el método solicitarConsulta ");
			log.error(e1.getMessage());
			return false;
		} catch (OegamExcepcion e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<String> validarDatos(IvtmConsultaMatriculacionDto ivtmConsultaDto) {
		List<String> errores = new ArrayList<>();
		log.debug("Ha entrado método validar Datos consulta");
		return errores;
	}

	@Override
	public BigDecimal guardarDatosConsulta(
			IvtmConsultaMatriculacionDto ivtmConsultaDto) throws Exception {
		log.debug("Ha entrado guardar Datos consulta");
		IvtmConsMatriculacionDaoInterface cd = new IvtmConsMatriculacionDaoImplHibernate(
				new IvtmConsultaMatriculacion());
		IvtmConsultaMatriculacion ivtmConsulta = convertirIvtmConsultaDTOToIvtmConsulta(ivtmConsultaDto);
		return cd.guardarConsulta(ivtmConsulta);
	}

	/**
	 * 
	 * @param eeffConsultaDTO
	 * @return
	 */
	private IvtmConsultaMatriculacion convertirIvtmConsultaDTOToIvtmConsulta(
			IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto)
			throws Exception {
		IvtmConsultaMatriculacion ivtmConsultaMatriculacion = new IvtmConsultaMatriculacion();
		ivtmConsultaMatriculacion.setIdPeticion(ivtmConsultaMatriculacionDto
				.getIdPeticion());
		// Fecha
		if (ivtmConsultaMatriculacionDto.getFechaReq() != null) {
			ivtmConsultaMatriculacion
					.setFechaReq(ivtmConsultaMatriculacionDto.getFechaReq());
		} else {
			ivtmConsultaMatriculacion.setFechaReq(null);
		}
		ivtmConsultaMatriculacion.setNumColegiado(ivtmConsultaMatriculacionDto
				.getNumColegiado());

		ivtmConsultaMatriculacion.setMatricula(ivtmConsultaMatriculacionDto
				.getMatricula());
		ivtmConsultaMatriculacion.setMensaje(ivtmConsultaMatriculacionDto
				.getMensaje());

		return ivtmConsultaMatriculacion;
	}

	/**
	 * 
	 * @param eeffConsulta
	 * @return
	 */
	private IvtmConsultaMatriculacionDto convertirIvtmConsultaToIvtmConsultaDTO(
			IvtmConsultaMatriculacion ivtmConsulta) throws Exception {
		IvtmConsultaMatriculacionDto ivtmConsultadto = new IvtmConsultaMatriculacionDto();
		ivtmConsultadto.setIdPeticion(ivtmConsulta.getIdPeticion());

		if (ivtmConsulta.getFechaReq() != null) {
			ivtmConsultadto.setFechaReq(ivtmConsulta.getFechaReq());
		}
		ivtmConsultadto.setNumColegiado(ivtmConsulta.getNumColegiado());
		ivtmConsultadto.setMatricula(ivtmConsulta.getMatricula());
		ivtmConsultadto.setMensaje(ivtmConsulta.getMensaje());

		return ivtmConsultadto;
	}

	@Override
	public es.map.scsp.esquemas.v2.peticion.consultaivtm.Peticion damePeticionConsulta(
			IvtmConsultaMatriculacionDto ivtmConsultaMatriculacionDto) {
		return new GenerarPeticionIVTM()
				.damePeticionConsulta(ivtmConsultaMatriculacionDto);
	}

	@Override
	public IvtmConsultaMatriculacionDto buscarIvtmConsultaIdPeticion(
			BigDecimal idPeticion) {
		IvtmConsultaMatriculacion ivtmCons = new IvtmConsultaMatriculacion();
		IvtmConsMatriculacionDaoInterface cd = new IvtmConsMatriculacionDaoImplHibernate(
				ivtmCons);
		ivtmCons.setIdPeticion(idPeticion);
		List<IvtmConsultaMatriculacion> lista = cd.buscar(ivtmCons);
		IvtmConsultaMatriculacion ivtmConsultaMatriculacion = null;
		if (lista != null && !lista.isEmpty()) {
			ivtmConsultaMatriculacion = lista.get(0);
		}
		IvtmConsultaMatriculacionDto ivtmDto = null;
		try {
			ivtmDto = convertirIvtmConsultaToIvtmConsultaDTO(ivtmConsultaMatriculacion);
		} catch (Exception e) {
			log.error("Un error ha ocurrido al convertir Objeto DAO a DTO");
			log.error(e.getMessage());
		}
		return ivtmDto;
	}

	@Override
	public IvtmConsultaMatriculacionDto actualizar(
			IvtmConsultaMatriculacionDto ivtmDto) throws OegamExcepcion {
		IvtmConsultaMatriculacion ivtm;
		try {
			ivtm = convertirIvtmConsultaDTOToIvtmConsulta(ivtmDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al parsear de fecha a date");
		}
		try {
			ivtm = (IvtmConsultaMatriculacion) ivtmDao.actualizar(ivtm);
		} catch (Exception e) {
			log.error(e);
			throw new OegamExcepcion("Error: al guardar ivtm");
		}
		IvtmConsultaMatriculacionDto resultado = new IvtmConsultaMatriculacionDto();
		try {
			resultado = convertirIvtmConsultaToIvtmConsultaDTO(ivtm);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al parsear de fecha a date");
		}
		return resultado;
	}

}