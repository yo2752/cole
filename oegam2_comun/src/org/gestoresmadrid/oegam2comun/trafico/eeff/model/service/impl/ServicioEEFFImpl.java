package org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.impl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffConsultaVO;
import org.gestoresmadrid.core.trafico.eeff.model.vo.EeffLiberacionVO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFConsulta;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFLiberacion;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.beans.DetalleConsultaEEFFBean;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffConsultaDTO;
import org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import trafico.beans.jaxb.matw.FORMATOGA.MATRICULACION;
import trafico.modelo.ModeloCreditosTrafico;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEEFFImpl implements ServicioEEFF {

	@Autowired
	private ServicioEEFFConsulta servicioConsulta;
	
	@Autowired
	private ServicioEEFFLiberacion servicioLiberacion;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	ModeloCreditosTrafico modeloCreditosTrafico = new ModeloCreditosTrafico();
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioEEFFImpl.class);
	

	@Override
	public List<ResultBean> consultarMasivoDesdeLiberacion(String[] numExpedientes) {
		List<ResultBean> listaResultados = new ArrayList<ResultBean>();
		if (numExpedientes == null){
			listaResultados.add(new ResultBean(true, "No existen los números de expedientes"));
			return listaResultados;
		}
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_CONSULTA);
		if (cobraCreditos==null){
			ResultBean rs = new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
			listaResultados.add(rs);
			return listaResultados;
		} else if (cobraCreditos.equals("true")){
			ResultBean rs =servicioConsulta.comprobarCreditos(numExpedientes.length);
			if (rs.getError()){
				listaResultados.add(rs);
				return listaResultados;
			}
		}
		for (String expediente : numExpedientes){
			EeffLiberacionDTO liberacion = servicioLiberacion.recuperarDatos(new BigDecimal(expediente));
			if (liberacion == null){
				listaResultados.add(new ResultBean(true, "No se encuentra el trámite "+ expediente+" o los datos de liberación"));
			} else {
				EeffConsultaDTO consulta = devolverConsultaDesdeLiberacion(liberacion);
				listaResultados.add(servicioConsulta.consultar(consulta));
			}
		}
	  return listaResultados;
	}

	private EeffConsultaDTO devolverConsultaDesdeLiberacion(EeffLiberacionDTO liberacion) {
		EeffConsultaDTO consulta = new EeffConsultaDTO();
		consulta.setFirCif(liberacion.getFirCif());
		consulta.setFirMarca(liberacion.getFirMarca());
		consulta.setNifRepresentado(liberacion.getNifRepresentado());
		consulta.setNombreRepresentado(liberacion.getNombreRepresentado());
		consulta.setNumColegiado(liberacion.getNumColegiado());
		consulta.setNumExpedienteTramite(liberacion.getNumExpediente());
		consulta.setTarjetaBastidor(liberacion.getTarjetaBastidor());
		consulta.setTarjetaNive(liberacion.getTarjetaNive());
		return consulta;
	}

	@Override
	public EeffLiberacionDTO recuperarDatosLiberacion(BigDecimal numExpediente) {
		EeffLiberacionDTO dto = servicioLiberacion.recuperarDatos(numExpediente);
		if (dto==null){
			return null;
		}
		EeffConsultaVO eeffConsultaVO = servicioConsulta.obtenerRespuestaDesdeExpedienteLiberacion(numExpediente);
		
		if(eeffConsultaVO != null){
			if(eeffConsultaVO.getRespuesta() != null && eeffConsultaVO.getRespuesta().substring(0, 4).equals("EEFF")){
				dto.setRespuestaConsulta("ERROR");
			}else{
				dto.setRespuestaConsulta(eeffConsultaVO.getRespuesta());
			}
			
			dto.setNumExpedienteConsulta(eeffConsultaVO.getNumExpediente().toString());
		}
		
		return dto;
	}

	@Override
	public EeffLiberacionDTO actualizarDatosLiberacion(EeffLiberacionDTO eeffDTO) {
		return servicioLiberacion.actualizarDatosDTO(eeffDTO);
	}

	@Override
	public ResultBean guardarDatosImportadosLiberacion(MATRICULACION faMa, BigDecimal numExp) {
		return servicioLiberacion.guardarDatosImportados(faMa, numExp);
	}

	@Override
	public boolean guardarDatosLiberacion(org.gestoresmadrid.oegam2comun.trafico.eeff.view.dto.EeffLiberacionDTO eeffDTO, BigDecimal numExpediente, String numColegiado) {
		return servicioLiberacion.guardarDatos(eeffDTO, numExpediente, numColegiado);
	}

	@Override
	public ResultBean liberar(BigDecimal numExpediente, BigDecimal idUsuario) {
		if (numExpediente==null){
			return new ResultBean(true, "No se encuentra el número de expediente");
		}
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_LIBERAR);
		if (cobraCreditos==null){
			return new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
		} else if (cobraCreditos.equals("true")){
			ResultBean rs =servicioLiberacion.comprobarCreditos(1);
			if (rs.getError()){
				return rs;
			}
		}
		try {
			return servicioLiberacion.liberar(numExpediente,idUsuario);
		} catch (OegamExcepcion e) {
			return new ResultBean(true, e.getMensajeError1());
		}
	}

	@Override
	public ResultBean validarEeffLibMatwFORMATOGA(MATRICULACION faMa) {
		return servicioLiberacion.validarEeffLibMatwFORMATOGA(faMa);
	}

	@Override
	public boolean duplicarLiberacion(BigDecimal numExpedientePrevio, BigDecimal numExpedienteNuevo) {
		return servicioLiberacion.duplicar(numExpedientePrevio, numExpedienteNuevo);

	}

	@Override
	public boolean esRealizadoLiberacion(BigDecimal numExpediente) {
		return servicioLiberacion.esRealizado(numExpediente);
	}

	@Override
	public List<ResultBean> liberarMasivo(String[] numsExpedientes, BigDecimal idUsuario) {
		List<ResultBean> resultado = new ArrayList<ResultBean>();
		if (numsExpedientes == null){
			resultado.add(new ResultBean(true, "No existen los números de expedientes"));
			return resultado;
		}
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_LIBERAR);
		if (cobraCreditos==null){
			ResultBean rs = new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
			resultado.add(rs);
			return resultado;
		} else if (cobraCreditos.equals("true")){
			ResultBean rs =servicioLiberacion.comprobarCreditos(numsExpedientes.length);
			if (rs.getError()){
				resultado.add(rs);
				return resultado;
			}
		}
		for (int i=0; i<numsExpedientes.length;i++){
			try {
				resultado.add(servicioLiberacion.liberar(new BigDecimal(numsExpedientes[i]),idUsuario));
			} catch (OegamExcepcion e) {
				resultado.add(new ResultBean(true, "No se ha podido realizar la solicitud para el proceso. Inténtelo más tarde"));
			}
		}
		return resultado;
	}

	@Override
	public EeffConsultaDTO recuperarDatosConsulta(BigDecimal numExpediente) {
		return servicioConsulta.recuperarDatos(numExpediente);
	}

	@Override
	public List<String> validarDatosConsulta(EeffConsultaDTO eeffDTO) {
		return servicioConsulta.validarDatos(eeffDTO);
	}

	@Override
	public boolean actualizarDatosConsulta(EeffConsultaDTO eeffDTO) {
		return servicioConsulta.actualizarDatos(eeffDTO);
	}

	@Override
	public boolean solicitarConsulta(EeffConsultaDTO eeffDTO) {
		return servicioConsulta.solicitarConsulta(eeffDTO);
	}

	@Override
	public ResultBean consultarDatos(EeffConsultaDTO eeffConsulta) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_CONSULTA);
		if (cobraCreditos==null){
			return new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
		} else if (cobraCreditos.equals("true")){
			ResultBean rs = servicioConsulta.comprobarCreditos(1);
			if (rs.getError()){
				return rs;
			}
		}
		return servicioConsulta.consultar(eeffConsulta);
	}

	@Override
	public StringBuffer getFicheroXmlLiberar(ColaBean solicitud) {
		return servicioLiberacion.getFicheroXml(solicitud);
	}

	@Override
	public EeffLiberacionVO liberacionProcesoRealizada(EeffLiberacionVO eeffLiberacion, String estadoBastidor, String numRegistroEntrada, String numRegistroSalida) {
		return servicioLiberacion.liberacionProceso(eeffLiberacion, estadoBastidor, numRegistroEntrada, numRegistroSalida);
	}

	@Override
	public StringBuffer getFicheroXmlConsultar(ColaBean solicitud) {
		return servicioConsulta.getFicheroXml(solicitud);
	}


	@Override
	public ResultBean liberarProceso(ColaBean solictud) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_LIBERAR);
		if (cobraCreditos==null){
			return new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
		} else if (cobraCreditos.equals("true")){
			ResultBean rs = servicioLiberacion.comprobarCreditosProceso(solictud.getIdUsuario().toString(), 1);
			if (rs.getError()){
				return rs;
			}
		}
		return servicioLiberacion.liberaProceso(solictud);
		
	}

	@Override
	public ResultBean procesoConsultar(ColaBean solicitud) {
		String cobraCreditos = gestorPropiedades.valorPropertie(ConstantesEEFF.EEFF_COBRAR_CREDITOS_CONSULTA);
		if (cobraCreditos==null){
			return new ResultBean(true, "No se puede comprobar si tiene créditos suficientes");
		} else if (cobraCreditos.equals("true")){
			ResultBean rs = servicioConsulta.comprobarCreditosProceso(solicitud.getIdUsuario().toString(), 1);
			if (rs.getError()){
				return rs;
			}
		}
		return servicioConsulta.consultaProceso(solicitud);
	}

	@Override
	public boolean actualizarSolicitudSinXml(BigDecimal idTramite) {
		EeffLiberacionVO eeffLiberacionVO = servicioLiberacion.recuperarDatosVO(idTramite);
		if(eeffLiberacionVO != null){
			eeffLiberacionVO.setRespuesta("La Solicitud " + idTramite + " no contiene xml de envio.");
			eeffLiberacionVO.setRealizado(false);
			return servicioLiberacion.actualizarDatos(eeffLiberacionVO);
		}else{
			EeffConsultaVO eeffConsultaVO = servicioConsulta.recuperarDatosVO(idTramite);
			if(eeffConsultaVO != null){
				eeffConsultaVO.setRealizado(false);
				eeffConsultaVO.setRespuesta("La Solicitud " + idTramite + " no contiene xml de envio.");
				return servicioConsulta.actualizarDatosVO(eeffConsultaVO);
			}
		}
		return true;
	}

	@Override
	@Transactional
	public DetalleConsultaEEFFBean getDetalleConsultaEEFF(String numExpediente) {
		try{
			return servicioConsulta.getDetalleConsulta(numExpediente);
		} catch (FileNotFoundException e) {
			log.error("Error al obtener el fichero de respuesta de la consultaEEFF: " + e);
			return null;
		} catch (UnsupportedEncodingException e) {
			log.error("Error al realizar el encoding del fichero xml: " + e);
			return null;
		} catch (JAXBException e) {
			log.error("Error realizar el UnMarshaller del fichero de respuesta de la consulta EEFF: " + e);
			return null;
		}
		
	}
		
	public ServicioEEFFConsulta getServicioConsulta() {
		return servicioConsulta;
	}

	public void setServicioConsulta(ServicioEEFFConsulta servicioConsulta) {
		this.servicioConsulta = servicioConsulta;
	}

	public ServicioEEFFLiberacion getServicioLiberacion() {
		return servicioLiberacion;
	}

	public void setServicioLiberacion(ServicioEEFFLiberacion servicioLiberacion) {
		this.servicioLiberacion = servicioLiberacion;
	}

}
