package org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.direccion.model.vo.MunicipioVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.general.model.vo.UsuarioVO;
import org.gestoresmadrid.core.historicocreditos.model.dao.enumerados.ConceptoCreditoFacturado;
import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.core.trafico.model.dao.TramiteTraficoDao;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.dao.PlacasMatriculacionDAO;
import org.gestoresmadrid.core.trafico.solicitudesplacas.model.vo.SolicitudPlacaVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCredito;
import org.gestoresmadrid.oegam2comun.creditos.model.service.ServicioCreditoFacturado;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.model.service.ServicioNotificacion;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.constantes.ConstantesPlacasMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.EstadoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoPlacasMatriculasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.enumerados.TipoSolicitudPlacasEnum;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.model.service.PlacasMatriculacionService;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.EstadisticasPlacasBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.SolicitudPlacaBean;
import org.gestoresmadrid.oegam2comun.trafico.solicitudesplacas.view.beans.ValidacionPlacasBean;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegam2comun.view.dto.NotificacionDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xhtmlrenderer.pdf.ITextRenderer;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import escrituras.modelo.ModeloPersona;
import hibernate.entities.general.Usuario;
import hibernate.entities.personas.Persona;
import hibernate.entities.trafico.TramiteTrafico;
import hibernate.utiles.constantes.ConstantesHibernate;
import trafico.utiles.UtilesConversiones;
import utiles.correo.PropiedadesEmail;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;


/**
 * Implementación de la interfaz del modelo de solicitud de placas de matriculación
 * @author ALvaro Fernandez
 * @date 27/12/2018
 *
 */
@Service
@Transactional
public class PlacasMatriculacionServiceImpl implements PlacasMatriculacionService {
	
	
	@Autowired
	private PlacasMatriculacionDAO placasMatriculacionDAO;
	
	@Autowired
	private TramiteTraficoDao tramiteTraficoDao;

	@Autowired
	ServicioCredito servicioCredito;
	
	@Autowired
	ServicioMunicipio servicioMunicipio;
	
	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	ServicioCreditoFacturado servicioCreditoFacturado;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesConversiones utilesConversiones;
	
	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private Integer[] arrayTipoPlacas={1,2,3,4,5,6};
	
	final String sFormatoFechaIntervalo = "dd/MM/yyyy";
	
	@SuppressWarnings("rawtypes")
	ArrayList<List> listaSolicitudAgruVehiculo = null;
	StringBuffer resultadoHtmlAgruVehiculos = new StringBuffer();
	HashMap<Integer, Integer> mapaSolicitudAgruCreditos = null;
	HashMap<String, Integer> mapaSolicitudAgruNumColegiado = null; 
	HashMap<String, HashMap<Integer, Integer>> mapaAgrupaCreditoColegiado;
	
	@Autowired
	private ServicioCorreo servicioCorreo;
	
	@Autowired
	private ServicioContrato servicioContrato;
	
	@Override
	public String analizarMatricula(String matricula) {
		
		String trozo1;
		String trozo2;
		String trozo3;
		String tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_ORDINARIA;
		
		// Análisis matricula ordinaria
		if(matricula.length()==ConstantesPlacasMatriculacion.SIETE.intValue()){
			trozo1 = matricula.substring(0,4);
			trozo2 = matricula.substring(4,7);
			
			if(utilesConversiones.comprobarNumero(trozo1) && !utilesConversiones.comprobarNumero(trozo2)){
				tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_ORDINARIA;
				return tipoMatricula;
			}
		}
		
		if(matricula.length()==ConstantesPlacasMatriculacion.OCHO.intValue()){
			// Análisis tipo matrícula antigua
			
			// Análisis matrícula tractor y agrícolas
			trozo1 = matricula.substring(0,1);
			trozo2 = matricula.substring(1,5);
			trozo3 = matricula.substring(5,8);
			
			if(!utilesConversiones.comprobarNumero(trozo1) && trozo1.equals("E") && utilesConversiones.comprobarNumero(trozo2) && !utilesConversiones.comprobarNumero(trozo3)){
				tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_TRACTOR;
				return tipoMatricula;
			}
			
			// Análisis matrícula ciclomotores
			trozo1 = matricula.substring(0,2);
			trozo2 = matricula.substring(2,5);
			trozo3 = matricula.substring(5,8);
			
			if(!utilesConversiones.comprobarNumero(trozo1) && trozo1.substring(0,1).equals("C") && utilesConversiones.comprobarNumero(trozo2) && !utilesConversiones.comprobarNumero(trozo3)){
				tipoMatricula = ConstantesPlacasMatriculacion.TIPO_MATRICULA_CICLOMOTOR;
				return tipoMatricula;
			}
		}
		
		return tipoMatricula;
	}

	@Override
	public TipoPlacasMatriculasEnum getTipoPlacaPorDefecto(String matricula) {
		
		TipoPlacasMatriculasEnum tipoPlaca = null;
		
		String tipoMatricula = analizarMatricula(matricula);
		
		if(tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_ORDINARIA)){
			tipoPlaca = TipoPlacasMatriculasEnum.Turismo_Ordinaria_Larga;
		} else if(tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_TRACTOR)){
			tipoPlaca = TipoPlacasMatriculasEnum.Tractor;
		} else if(tipoMatricula.equals(ConstantesPlacasMatriculacion.TIPO_MATRICULA_CICLOMOTOR)){
			tipoPlaca = TipoPlacasMatriculasEnum.Ciclomotor;
		} else {
			tipoPlaca = TipoPlacasMatriculasEnum.Turismo_Ordinaria_Larga;
		}
		
		return tipoPlaca;
	}
	
	@Override
	public ValidacionPlacasBean validarSolicitud(SolicitudPlacaBean solicitudBean,
			EstadoSolicitudPlacasEnum estadoPrevio, 
			int contador) {
		
		ValidacionPlacasBean validarSolicitud = new ValidacionPlacasBean();		
		validarSolicitud.setError(false);
		
		validarSolicitud.setNumColegiado(solicitudBean.getNumColegiado());
		validarSolicitud.setFechaSolicitud(solicitudBean.getFechaSolicitud());
		validarSolicitud.setMatricula(solicitudBean.getMatricula());
		
		
		ArrayList<String> mensajes = new ArrayList<String>();
		
		if(solicitudBean.getEstado()!=estadoPrevio){
			if(solicitudBean.getEstado().equals(EstadoSolicitudPlacasEnum.Confirmado) && !estadoPrevio.equals(EstadoSolicitudPlacasEnum.Iniciado)){
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado iniciado para poder pasar a estado confirmada.");
				log.error("La solicitud debe encontrarse en estado iniciado para poder pasar a estado confirmada.");
			} else if(solicitudBean.getEstado().equals(EstadoSolicitudPlacasEnum.Tramitado) && !estadoPrevio.equals(EstadoSolicitudPlacasEnum.Confirmado)){
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado confirmado para poder pasar a estado tramitado.");
				log.error("La solicitud debe encontrarse en estado confirmado para poder pasar a estado tramitado.");
			} else if(solicitudBean.getEstado().equals(EstadoSolicitudPlacasEnum.Finalizado) && !estadoPrevio.equals(EstadoSolicitudPlacasEnum.Tramitado)){
				validarSolicitud.setError(true);
				mensajes.add("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
				log.error("La solicitud debe encontrarse en estado tramitado para poder pasar a estado finalizado.");
			}
		}
		
		validarSolicitud.setMensajes(mensajes);
		
		return validarSolicitud;
	}
	
	@Override 
	public List<ValidacionPlacasBean> validarSolicitudes(List<SolicitudPlacaBean> listaSolicitudes, EstadoSolicitudPlacasEnum estadoPrevio) {
		
		ArrayList<ValidacionPlacasBean> resultadoValidaciones = new ArrayList<ValidacionPlacasBean>();
		int contador = 0;
		
		for (SolicitudPlacaBean spBean : listaSolicitudes) {
			ValidacionPlacasBean resultadoValidacion = validarSolicitud(spBean, estadoPrevio, contador);
			resultadoValidaciones.add(resultadoValidacion);
			contador++;
		}
		
		return resultadoValidaciones;
	}
	
	@Override
	public SolicitudPlacaVO prepararSolicitud(SolicitudPlacaBean solicitudBean) {
		
		SolicitudPlacaVO solicitud = new SolicitudPlacaVO();
		
		String fechaSolicitud = null;
		fechaSolicitud = solicitudBean.getFechaSolicitud();
		
		// Rellenamos el contrato a través de su id
		ContratoVO contrato = null;
		if(solicitudBean.getIdContrato()!=null){
			contrato = new ContratoVO();
			contrato.setIdContrato(solicitudBean.getIdContrato().longValue());
		}
		
		
		solicitud.setContrato(contrato!=null ? contrato : null);
		solicitud.setIdContrato(solicitudBean.getIdContrato());
		solicitud.setNumColegiado(solicitudBean.getNumColegiado());
		solicitud.setNifTitular(solicitudBean.getNif());
		solicitud.setMatricula(solicitudBean.getMatricula());
		solicitud.setBastidor(solicitudBean.getBastidor());
		solicitud.setTipoVehiculo(solicitudBean.getTipoVehiculo());
		
		solicitud.setIdSolicitud(solicitudBean.getIdSolicitud());
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setIdUsuario(solicitudBean.getUsuario().getIdUsuario());
		solicitud.setUsuario(usuarioVO);
		solicitud.setTipoDelantera(solicitudBean.getTipoPlacaDelantera()!=null ? Integer.valueOf(solicitudBean.getTipoPlacaDelantera().getValorEnum()) : null);
		solicitud.setTipoTrasera(solicitudBean.getTipoPlacaTrasera()!=null ? Integer.valueOf(solicitudBean.getTipoPlacaTrasera().getValorEnum()) : null);
		solicitud.setTipoAdicional(solicitudBean.getTipoPlacaAdicional()!=null ? Integer.valueOf(solicitudBean.getTipoPlacaAdicional().getValorEnum()) : null);
		try {
			solicitud.setFechaSolicitud(fechaSolicitud!=null ? new Fecha(fechaSolicitud).getDate() : null);
		} catch (ParseException e) {
			log.error("Error al obtener la fecha de solicitud de placas de matriculación: " + e.getMessage());
		}
		solicitud.setEstado(Integer.valueOf(solicitudBean.getEstado()!=null ? solicitudBean.getEstado().getValorEnum() : EstadoSolicitudPlacasEnum.Iniciado.getValorEnum()));
		solicitud.setDuplicada(solicitudBean.isDuplicada() == true ? 1 : 0);
		
		TramiteTraficoVO tramiteTraficoVO = servicioTramiteTrafico.getTramite(new BigDecimal(solicitudBean.getNumExpediente()), Boolean.TRUE);
		if (tramiteTraficoVO != null) {
			solicitud.setTramiteTrafico(tramiteTraficoVO);
			solicitud.setNumExpediente(solicitud.getTramiteTrafico().getNumExpediente());
		}
		
		return solicitud;
		
	}
	
	@Override
	public ArrayList<SolicitudPlacaVO> prepararSolicitudes(List<SolicitudPlacaBean> listaSolicitudes) {
		
		ArrayList<SolicitudPlacaVO> solicitudes = new ArrayList<SolicitudPlacaVO>();
		
		for (SolicitudPlacaBean spBean : listaSolicitudes) {		
			SolicitudPlacaVO solicitud = prepararSolicitud(spBean);
			solicitudes.add(solicitud);
		}
		
		return solicitudes;
		
	}
	 
	@Override
	public List<ValidacionPlacasBean> realizarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean) {
		
		//se mete el expediente si lo tiene
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<ValidacionPlacasBean>();
		for(SolicitudPlacaBean solicitudPlacaBean : lSolicitudPlacaBean)
		{	
			TramiteTrafico tramiteTrafico=null;
			try
			{
			 tramiteTrafico=tramiteTraficoDao.getTramiteTrafico(solicitudPlacaBean.getNumColegiado(),solicitudPlacaBean.getMatricula(),TipoTramiteTrafico.Matriculacion.getValorEnum());
			}
		 catch (Throwable e){
			log.error("Error hay más de un expediente de matriculación para la matricula "+solicitudPlacaBean.getMatricula()+".");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			resultadoGuardar.setMatricula(solicitudPlacaBean.getMatricula());
			ArrayList<String> mensajes = new ArrayList<String>();
			resultadoGuardar.setError(true);
			mensajes.add("Error hay más de un expediente de matriculación para la matricula "+solicitudPlacaBean.getMatricula()+".");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		} 
			
			if(tramiteTrafico!=null)
			{
				solicitudPlacaBean.setNumExpediente(tramiteTrafico.getNumExpediente());			
		    }
		}
		
		ArrayList<ValidacionPlacasBean> validar = new ArrayList<ValidacionPlacasBean>();
		
		// Validamos las distintas solicitudes de placas
	
		validar = (ArrayList<ValidacionPlacasBean>)validarSolicitudes(lSolicitudPlacaBean, EstadoSolicitudPlacasEnum.Iniciado);
		
		for (ValidacionPlacasBean resultadoValidar : validar) {
			if(resultadoValidar.isError()){
				return validar;
			}
		}
		
		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(lSolicitudPlacaBean);
		
		// Realizamos el guardado de las solicitudes
		
		try{
			guardar =(ArrayList<ValidacionPlacasBean>) guardarSolicitudes(solicitudes, false,false);
			return guardar;
		} catch (Throwable e){
			log.error("Error guardando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<String>();
			resultadoGuardar.setError(true);
			mensajes.add("Error guardando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		} 
		
	}
	
	@Override
	public List<ValidacionPlacasBean> confirmarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean) {
		
		ArrayList<ValidacionPlacasBean> validar = new ArrayList<ValidacionPlacasBean>();
		
		for(SolicitudPlacaBean solicitudPlacaBean : lSolicitudPlacaBean)
		{		
		TramiteTrafico tramiteTrafico=tramiteTraficoDao.getTramiteTrafico(solicitudPlacaBean.getNumColegiado(),solicitudPlacaBean.getMatricula(),"T1");
		if(tramiteTrafico!=null)
		 {
			solicitudPlacaBean.setNumExpediente(tramiteTrafico.getNumExpediente());			
	     }
		}
		
		// Validamos las distintas solicitudes de placas
		validar = (ArrayList<ValidacionPlacasBean>)validarSolicitudes(lSolicitudPlacaBean, EstadoSolicitudPlacasEnum.Iniciado);
		
		for (ValidacionPlacasBean resultadoValidar : validar) {
			if(resultadoValidar.isError()){
				return validar;
			}
		}
		
		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(lSolicitudPlacaBean);
		
		// Realizamos el guardado de las solicitudes
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<ValidacionPlacasBean>();
		try{
			guardar = (ArrayList<ValidacionPlacasBean>)guardarSolicitudes(solicitudes, true,false);
			return guardar;
		} catch (Throwable e){
			log.error("Error confirmando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<String>();
			resultadoGuardar.setError(true);
			mensajes.add("Error confirmando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		}
		
	}
	
	@Override
	public List<ValidacionPlacasBean> finalizarSolicitud(List<SolicitudPlacaBean> lSolicitudPlacaBean) {
		
		ArrayList<ValidacionPlacasBean> validar = new ArrayList<ValidacionPlacasBean>();
		
		// Validamos las distintas solicitudes de placas
		
		validar = (ArrayList<ValidacionPlacasBean>)validarSolicitudes(lSolicitudPlacaBean, EstadoSolicitudPlacasEnum.Tramitado);
		
		for (ValidacionPlacasBean resultadoValidar : validar) {
			if(resultadoValidar.isError()){
				return validar;
			}
		}
		
		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(lSolicitudPlacaBean);
		
		// Realizamos el guardado de las solicitudes
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<ValidacionPlacasBean>();
		try{
			guardar = (ArrayList<ValidacionPlacasBean>)guardarSolicitudes(solicitudes, false,false);
			return guardar;
		} catch (Throwable e){
			log.error("Error finalizando las solicitudes de placas de matriculación.");
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			ArrayList<String> mensajes = new ArrayList<String>();
			resultadoGuardar.setError(true);
			mensajes.add("Error finalizando las solicitudes de placas de matriculación.");
			resultadoGuardar.setMensajes(mensajes);
			guardar.add(resultadoGuardar);
			return guardar;
		}
		
	}

	@Override
	public SolicitudPlacaVO guardarSolicitud(SolicitudPlacaVO solicitud, boolean descontarCreditos) {
		
		solicitud.setIdUsuario(solicitud.getUsuario().getIdUsuario());
		
		solicitud = placasMatriculacionDAO.guardarOActualizar(solicitud);
			
		if(solicitud != null){
			
			if(descontarCreditos){
				boolean descontar = modificarCreditosSolicitudPlacas(solicitud, true);
				
				if(!descontar){
					solicitud.setEstado(ConstantesPlacasMatriculacion.UNO);
					guardarSolicitud(solicitud, false);
				}
				
			}
		}
		
		return solicitud;
		
	}
	
	
	
	
	public boolean guardarSolicitudEstado(SolicitudPlacaVO solicitud, boolean descontarCreditos) {
		
		boolean resultadoSolicitud = false;

		SolicitudPlacaVO solictudAnterior=placasMatriculacionDAO.getSolicitud(solicitud.getIdSolicitud(), (String []) null);				
		solictudAnterior.setEstado(solicitud.getEstado());
		  		
			if(descontarCreditos){
				boolean descontar = modificarCreditosSolicitudPlacas(solicitud, true);
				
				if(!descontar){

					solictudAnterior.setEstado(ConstantesPlacasMatriculacion.UNO);

				}
				
			}
		
		return resultadoSolicitud;
		
	}
	
	
	

	//@Override
	public List<ValidacionPlacasBean> guardarSolicitudes(List<SolicitudPlacaVO> solicitudes, boolean descontarCreditos,boolean soloEstado) {
		
		ArrayList<ValidacionPlacasBean> guardar = new ArrayList<ValidacionPlacasBean>();
		
		int indice = 0;
		for (SolicitudPlacaVO solicitudPlaca : solicitudes) {
			boolean resultadoSolicitud = false;
			
			if(soloEstado)
			{
			 resultadoSolicitud = guardarSolicitudEstado(solicitudPlaca, descontarCreditos);
			}
			else
			{
				solicitudPlaca = guardarSolicitud(solicitudPlaca, descontarCreditos);
				resultadoSolicitud = solicitudPlaca != null;
			}
			
			ValidacionPlacasBean resultadoGuardar = new ValidacionPlacasBean();
			resultadoGuardar.setError(false);
			ArrayList<String> mensajes = new ArrayList<String>();
			String fechaSolicitud = solicitudPlaca!=null
					&& solicitudPlaca.getFechaSolicitud()!=null ? solicitudPlaca.getFechaSolicitud().toString() : null;
			
			
			resultadoGuardar.setIdSolicitud(solicitudPlaca.getIdSolicitud());
			resultadoGuardar.setNumColegiado(solicitudPlaca.getNumColegiado());
			resultadoGuardar.setFechaSolicitud(fechaSolicitud);
			resultadoGuardar.setMatricula(solicitudPlaca.getMatricula());
			resultadoGuardar.setNoElemento(indice);
			
			if(!resultadoSolicitud){
				
				if(descontarCreditos){
					mensajes.add("Compruebe que dispone de créditos suficientes para efectuar la operación.");
					resultadoGuardar.setError(true);
					resultadoGuardar.setMensajes(mensajes);
				}
				
				
			}
			
			guardar.add(resultadoGuardar);
			
			indice++;
		}
		
		return guardar;
		
	}
	
	@Override
	public boolean modificarCreditosSolicitudPlacas(SolicitudPlacaVO solicitudPlacaVO, boolean incremental){
		
		boolean descontar = false;
		ArrayList<TipoSolicitudPlacasEnum> listaCobros = new ArrayList<TipoSolicitudPlacasEnum>();
		ArrayList<TipoSolicitudPlacasEnum> listaDevoluciones = new ArrayList<TipoSolicitudPlacasEnum>();
		Integer numeroCreditos;
		/* TODO: Esto se tiene que probar bien. De momento se coloca 1 crédito fijo
		* ya que el PQ se encargará de sumarlo o descontarlo en función de que el tipo
		* de crédito sea incremental o decremental en base de datos
		*/
		if(incremental){
			numeroCreditos = Integer.valueOf(1);
		} else {
			numeroCreditos = Integer.valueOf(1);
		}
		
		TipoSolicitudPlacasEnum tipoSolicitudPlaca;
			
		if(solicitudPlacaVO.getTipoDelantera()!=null){
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitudPlacaVO.getTipoDelantera())));
			listaCobros.add(tipoSolicitudPlaca);
		}
		
		if(solicitudPlacaVO.getTipoTrasera()!=null){
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitudPlacaVO.getTipoTrasera())));
			listaCobros.add(tipoSolicitudPlaca);
		}
	
		if(solicitudPlacaVO.getTipoAdicional()!=null){
			tipoSolicitudPlaca = getTipoSolicitudPlaca(TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitudPlacaVO.getTipoAdicional())));	
			listaCobros.add(tipoSolicitudPlaca);
		}
		
		for (TipoSolicitudPlacasEnum tipoSolicitud : listaCobros) {
			
			descontar = descuentoCreditos(solicitudPlacaVO, numeroCreditos, tipoSolicitud);
			if(descontar){
				listaDevoluciones.add(tipoSolicitud);
			} else {
				if(listaDevoluciones.size() > 0){
					devolverCreditos(solicitudPlacaVO, new BigDecimal(numeroCreditos).negate().intValue(), listaDevoluciones);
					log.error("SOLICITUD_PLACAS: Error al realizar el cargo de créditos para alguna de las placas de la solicitud: " + solicitudPlacaVO.getIdSolicitud());
					log.error("SOLICITUD_PLACAS: Se ha procedido a la devolución de los créditos cargados en la solicitud: " + solicitudPlacaVO.getIdSolicitud() + " debido a errores en el cargo de créditos para alguna de las placas.");
					
					
					try{
						//ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
						
						if (servicioCreditoFacturado != null ){
							servicioCreditoFacturado.eliminarGasto(solicitudPlacaVO);														
						}
					}catch(Exception e){
						log.error("Se ha producido un error al guardar historico de creditos para el tramite: " + solicitudPlacaVO.getIdSolicitud(), e);
					}
					
					
				}
				return descontar;
			}
		}
		
		return descontar;
		
	}
	
	public void devolverCreditos(SolicitudPlacaVO solicitudPlacaVO, Integer numeroCreditos, List<TipoSolicitudPlacasEnum> listaDevoluciones){
		
		for (TipoSolicitudPlacasEnum tipoSolicitudPlaca : listaDevoluciones) {
			descuentoCreditos(solicitudPlacaVO, numeroCreditos, tipoSolicitudPlaca);
		}
		
	}
	
	public boolean descuentoCreditos(SolicitudPlacaVO solicitudPlacaVO, Integer numeroCreditos, TipoSolicitudPlacasEnum tipoSolicitudPlaca){
		
	
		boolean descontar = false;
		
		String idContrato = solicitudPlacaVO!=null
				&& solicitudPlacaVO.getContrato()!=null
				&& solicitudPlacaVO.getContrato()!=null ? String.valueOf(solicitudPlacaVO.getContrato().getIdContrato()) : null;
		BigDecimal idUsuario = solicitudPlacaVO!=null && solicitudPlacaVO.getUsuario()!=null ? new BigDecimal(solicitudPlacaVO.getUsuario().getIdUsuario()) : null;
		if(idContrato!=null && idUsuario!=null){
			
			ResultBean  res=servicioCredito.descontarCreditos(tipoSolicitudPlaca.getValorEnum(), new BigDecimal(idContrato), new BigDecimal(numeroCreditos), 
					idUsuario, ConceptoCreditoFacturado.SPL,(String []) null);
				
					
			
			if(res.getError()){
				log.error("ERROR DESCONTAR CREDITOS");
				log.error("CONTRATO: " + idContrato);
				log.error("ID_USUARIO: " + String.valueOf(idUsuario));
			} else {
				
				
				
				try{
					//ServicioCreditoFacturado servicioCreditoFacturado = (ServicioCreditoFacturado) ContextoSpring.getInstance().getBean(Constantes.SERVICIO_HISTORICO_CREDITO);
					if (servicioCreditoFacturado != null ){
						servicioCreditoFacturado.anotarGasto(new Integer(1), ConceptoCreditoFacturado.SPL, utilesColegiado.getIdContratoSession(), tipoSolicitudPlaca.getValorEnum(),(String []) null);
					}
				}catch(Exception e){
					log.error("Se ha producido un error al guardar historico de creditos para el tramite: " + solicitudPlacaVO.getIdSolicitud(), e);
				}
				
				
				descontar = true;
			}
		}
		
		return descontar;
		
	}
	
	public TipoSolicitudPlacasEnum getTipoSolicitudPlaca(TipoPlacasMatriculasEnum tipoPlaca){
		
		TipoSolicitudPlacasEnum tipoSolicitud;
		
		switch(tipoPlaca){
			case Turismo_Ordinaria_Larga:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ordinaria_Larga;
				break;
			case Turismo_Ordinaria_Corta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ordinaria_Corta_Alfa_Romeo;
				break;
			case Motocicleta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Motocicleta;
				break;
			case Moto_Corta:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Motocicleta_Trial;
				break;
			case Tractor:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Vehiculo_Especial;
				break;
			case Ciclomotor:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_Ciclomotor;
				break;
			case TaxiVTC:
				tipoSolicitud = TipoSolicitudPlacasEnum.Solicitud_Placa_TaxiVTC;
				break;
			default:
				tipoSolicitud = null;
				break;
		}
		
		return tipoSolicitud;
		
	}
	
	@Override
	public SolicitudPlacaVO getSolicitud(Integer idSolicitud, String... initialized) {
		
		SolicitudPlacaVO solicitud = null;
		
		solicitud = placasMatriculacionDAO.getSolicitud(idSolicitud, initialized);
		
		return solicitud;
		
	}

	@Override
	public List<SolicitudPlacaVO> getSolicitudes() {
		
		return getSolicitudes(null, null, null);
		
	}

	@Override
	public List<SolicitudPlacaVO> getSolicitudes(Long numExpediente) {
		
		return getSolicitudes(numExpediente, null, null);
		
	}

	@Override
	public List<SolicitudPlacaVO> getSolicitudes(Integer idUsuario) {
		
		return getSolicitudes(null, idUsuario, null);
		
	}

	@Override
	public List<SolicitudPlacaVO> getSolicitudes(EstadoSolicitudPlacasEnum estado) {
		
		return getSolicitudes(null, null, estado);
		
	}

	@Override
	public List<SolicitudPlacaVO> getSolicitudes(Long numExpediente, Integer idUsuario, EstadoSolicitudPlacasEnum estado) {
		
		return null;
		
	}
	
	@Override
	public SolicitudPlacaBean getSolicitudPantalla(Integer idSolicitud){
		
		SolicitudPlacaBean spBean = null;
		
		// Preparo el array de entidades que necesitaré inicializar
		Integer numEntidades = new Integer(1);
		String[] entidades = (String[]) Array.newInstance(String.class, numEntidades);
		entidades[0] = ConstantesHibernate.USUARIO_PROPERTY;
		
		// Obtengo la solicitud guardada en BBDD
		SolicitudPlacaVO solicitud = getSolicitud(idSolicitud, entidades);
		
		// Convierto la solicitud obtenida de BBDD a un bean de pantalla
		if(solicitud!=null){
			spBean = convertSolicitudPantalla(solicitud);
		}
		
		return spBean;
		
	}
	
	@Override
	public List<SolicitudPlacaBean> getSolicitudesPantalla(Integer[] idSolicitudes) {
		return null;
	}

	@Override
	public SolicitudPlacaBean convertSolicitudPantalla(SolicitudPlacaVO solicitud) {
		
	
		SolicitudPlacaBean spBean = new SolicitudPlacaBean();
		
		String tipoPlacaDelantera = solicitud.getTipoDelantera()!=null ? TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoDelantera())).getValorEnum() : null;
		String tipoPlacaTrasera = solicitud.getTipoTrasera()!=null ? TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoTrasera())).getValorEnum() : null;
		String tipoPlacaAdicional = solicitud.getTipoAdicional()!=null ? TipoPlacasMatriculasEnum.convertir(String.valueOf(solicitud.getTipoAdicional())).getValorEnum() : null;
		String fechaSolicitud = solicitud.getFechaSolicitud()!=null ? String.valueOf(utilesFecha.formatoFecha("dd/MM/yyyy", solicitud.getFechaSolicitud())) : null;
		String estado = solicitud.getEstado()!=null ? String.valueOf(EstadoSolicitudPlacasEnum.convertir(String.valueOf(solicitud.getEstado()))) : null;
		String nombreUsuario = solicitud.getUsuario()!=null && solicitud.getUsuario().getApellidosNombre()!=null ? solicitud.getUsuario().getApellidosNombre(): null;
		
		// Relleno el bean de pantalla desde la solicitud de BBDD
		spBean.setIdSolicitud(solicitud.getIdSolicitud());
		
		
		spBean.setNumExpediente(solicitud.getTramiteTrafico()!=null ? solicitud.getTramiteTrafico().getNumExpediente().longValue() : null);
		
		spBean.setUsuario(null);
		spBean.setIdContrato(solicitud.getContrato()!=null && solicitud.getContrato().getIdContrato()!=null ? solicitud.getContrato().getIdContrato().intValue() : null);
		spBean.setNombreUsuario(nombreUsuario);
		
		spBean.setMatricula(solicitud.getMatricula());
		spBean.setBastidor(solicitud.getBastidor());
		spBean.setTipoVehiculo(solicitud.getTipoVehiculo());
		spBean.setNif(solicitud.getNifTitular());
		
		spBean.setTipoPlacaDelantera(tipoPlacaDelantera);
		spBean.setTipoPlacaTrasera(tipoPlacaTrasera);
		spBean.setTipoPlacaAdicional(tipoPlacaAdicional);
		spBean.setFechaSolicitud(fechaSolicitud);
		spBean.setEstado(estado);
		spBean.setTitular(solicitud.getTitular()!=null
				&& solicitud.getTitular().getId()!=null
				&& solicitud.getTitular().getId().getNif()!=null ? ModeloPersona.obtenerDetallePersonaCompleto(solicitud.getTitular().getId().getNif(), solicitud.getTitular().getId().getNumColegiado()) : null);
		spBean.setDuplicada(solicitud.getDuplicada()!=null && solicitud.getDuplicada() == 1 ? true : false);
		
		return spBean;
		
	}

	public SolicitudPlacaBean convertSolicitudPantallaBusqueda(SolicitudPlacaVO solicitudPlacaVO) {
		
		SolicitudPlacaBean spBean = new SolicitudPlacaBean();
		
		String fechaSolicitud = solicitudPlacaVO.getFechaSolicitud()!=null ? String.valueOf(utilesFecha.formatoFecha("dd/MM/yyyy", solicitudPlacaVO.getFechaSolicitud())) : null;
		String estado = solicitudPlacaVO.getEstado()!=null ? String.valueOf(EstadoSolicitudPlacasEnum.convertir(String.valueOf(solicitudPlacaVO.getEstado()))) : null;
		String nombreUsuario = solicitudPlacaVO.getUsuario().getApellidosNombre();
		
		// Relleno el bean de pantalla desde la solicitud de BBDD
		spBean.setIdSolicitud(solicitudPlacaVO.getIdSolicitud());
		spBean.setEstado(estado);
		spBean.setNombreUsuario(nombreUsuario);
		spBean.setFechaSolicitud(fechaSolicitud);
		spBean.setMatricula(solicitudPlacaVO.getMatricula());
		spBean.setBastidor(solicitudPlacaVO.getBastidor());
		spBean.setTipoVehiculo(solicitudPlacaVO.getTipoVehiculo());
		
		return spBean;
		
	}

	@Override
	public List<SolicitudPlacaBean> convertSolicitudesPantalla(List<SolicitudPlacaVO> solicitudes) {
		
		ArrayList<SolicitudPlacaBean> spBeans = new ArrayList<SolicitudPlacaBean>();
		
		for (SolicitudPlacaVO solicitudPlacaVO : solicitudes) {			
			SolicitudPlacaBean spBean = convertSolicitudPantallaBusqueda(solicitudPlacaVO);
			spBeans.add(spBean);
			
		}
		
		return spBeans;
		
	}

	@Override
	public List<SolicitudPlacaBean> buscar(Date fechaInicio, Date fechaFin, String numColegiado, Long numExpediente, String matricula, String tipoMatricula) {
		
		List<SolicitudPlacaBean> listaSolicitudes = new ArrayList<SolicitudPlacaBean>();
		
		ArrayList<SolicitudPlacaVO> solicitudes =  placasMatriculacionDAO.buscar(fechaInicio, fechaFin, numColegiado, numExpediente, matricula, tipoMatricula);
		
		if(solicitudes!=null){
			listaSolicitudes = convertSolicitudesPantalla(solicitudes);
		}
		
		return listaSolicitudes;
	}

	@Override
	public boolean cambiarEstadoSolicitud(List<SolicitudPlacaBean> listaSolicitudes, String estado) {
		
		boolean cambioEstado = false;
		
		// Preparamos los datos para ser guardados por el DAO
		ArrayList<SolicitudPlacaVO> solicitudes = prepararSolicitudes(listaSolicitudes);
		
		// Realizamos el guardado de las solicitudes
		try{
			guardarSolicitudes(solicitudes, false,true);
			cambioEstado = true;
		} catch (Throwable e){
			log.error("Error guardando las solicitudes de placas de matriculación.");
			cambioEstado = false;
		}
		
		return cambioEstado;
	}

	@Override
	public Integer getIdSolicitudPorClaveUnica(SolicitudPlacaBean spBean) {
		
		SolicitudPlacaVO solicitud = prepararSolicitud(spBean);
		
		Integer idSolicitud  = placasMatriculacionDAO.getIdSolicitudPorClaveUnica(solicitud);;
		
		
		if(solicitud.getEstado().equals(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum())){
			return idSolicitud;
		} else {
			return BigDecimal.ZERO.intValue();
		}
	}
	
	@Override
	public SolicitudPlacaBean getSolicitudPorClaveUnica(SolicitudPlacaBean solicitudPlacaBean) {
		
		
		SolicitudPlacaVO solicitudPlacaVO = prepararSolicitud(solicitudPlacaBean);
		
		SolicitudPlacaVO solicitudGuardada = placasMatriculacionDAO.getSolicitudPorClaveUnica(solicitudPlacaVO);
		
		
		SolicitudPlacaBean solicitudRetorno = null;

		if(solicitudGuardada!=null){
			solicitudRetorno = convertSolicitudPantalla(solicitudGuardada);
			return solicitudRetorno;
		} else {
			return solicitudRetorno;
		}
	}
	
	private void crearNotificacion(SolicitudPlacaBean spBean){
		ServicioNotificacion servicioNotificacion = ContextoSpring.getInstance().getBean(ServicioNotificacion.class);
		NotificacionDto notifDto = new NotificacionDto();
		notifDto.setTipoTramite(TipoTramiteTrafico.Solicitud_Placas.getValorEnum());
		notifDto.setIdTramite(BigDecimal.valueOf(spBean.getIdSolicitud()));
		notifDto.setEstadoAnt(new BigDecimal(EstadoSolicitudPlacasEnum.Tramitado.getValorEnum()));
		notifDto.setEstadoNue(new BigDecimal(EstadoSolicitudPlacasEnum.Finalizado.getValorEnum()));
		notifDto.setDescripcion(ConstantesPlacasMatriculacion.NOTIFICACION_FINALIZADO);
		notifDto.setIdUsuario(spBean.getUsuario().getIdUsuario());
		servicioNotificacion.crearNotificacion(notifDto);
	}

	
	@Override
	public boolean notificar(SolicitudPlacaBean solicitudPlacaBean, boolean notificarOegam){
		
		boolean enviado;
		
		// Asumimos que si se crea notificación en Oegam se trata de un correo al usuario, y si no, al colegio
		if(notificarOegam){
			enviado = sendMailUsuario(solicitudPlacaBean);
		} else {
			enviado = sendMailColegio(solicitudPlacaBean);
		}
		
		if(enviado && notificarOegam){
			crearNotificacion(solicitudPlacaBean);
		}
		
		return enviado;
		
	}
	
	@Override
	public HashMap<Long, Boolean> notificarVarias(List<SolicitudPlacaBean> listaSolicitudes, boolean notificarOegam) {
		
		HashMap<Long, Boolean> notificaciones = new HashMap<Long, Boolean>();
		
		for (SolicitudPlacaBean solicitudPlacaBean : listaSolicitudes) {
			
			boolean enviado = notificar(solicitudPlacaBean, notificarOegam);
			
			
		    notificaciones.put(solicitudPlacaBean.getNumExpediente(), Boolean.valueOf(enviado));
			
			
		}
		
		return notificaciones;
	}
	
	public PropiedadesEmail configurarCorreo(String propiedadRecipient, String propiedadBcc, String propiedadSubject, String propiedadBody){
		
		PropiedadesEmail propiedades = new PropiedadesEmail();
		
		propiedades.setPara(propiedadRecipient);
		propiedades.setCopia(propiedadBcc);
		propiedades.setAsunto(propiedadSubject);
		propiedades.setCuerpo(propiedadBody);
		
		return propiedades;
		
	}
	
	public boolean sendMailUsuario(SolicitudPlacaBean spBean){
		
		boolean correcto = false;
		
		String subject;
		String recipient;
		String Bcc;
		
		String correoHabilitado = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_HABILITADO)!=null ? gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_HABILITADO) : null;
		
		// El properties debe habilitar el envio de correo directo al colegiado, en caso contrario, debe asumirse que no estamos en producción y no se envia correo al colegiado
		String userMail;
		if(correoHabilitado!=null && !"".equals(correoHabilitado) && correoHabilitado.equals("SI")){
			userMail = spBean.getUsuario().getCorreoElectronico();
		} else {
			userMail = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_RECIPIENT);
		}
		
		
	
		String matricula = spBean.getMatricula()!=null ? spBean.getMatricula() : "";
		String delantera = spBean.getTipoPlacaDelantera()!=null ? spBean.getTipoPlacaDelantera().getNombreEnum() : "No Solicitada";
		String trasera = spBean.getTipoPlacaTrasera()!=null ? spBean.getTipoPlacaTrasera().getNombreEnum() : "No Solicitada";
		String adicional = spBean.getTipoPlacaAdicional()!=null ? spBean.getTipoPlacaAdicional().getNombreEnum() : "No solicitada";
		String duplicada = spBean.isDuplicada() == true ? "Si" : "No";
		
		String textoAniadir =		"<p>Su solicitud de placas para la matrícula " + matricula + " ha finalizado satisfactoriamente.</p>"
									+ "<p><u>Detalle de su solicitud:</u></p>"
									+ "<ul>"
									+ "<li><strong>Matrícula</strong>: " + matricula + "</li>"
									+ "<li><strong>Placa delantera</strong>: " + delantera + "</li>"
									+ "<li><strong>Placa trasera</strong>: " + trasera + "</li>"
									+ "<li><strong>Placa adicional</strong>: " + adicional + "</li>"
									+ "<li><strong>Placa duplicada</strong>: " + duplicada + "</li>"
									+ "</ul>";
		subject =					gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_SUBJECT);
		recipient =					userMail;
		Bcc =						gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.NOTIFICATION_MAIL_BCC);
		
		PropiedadesEmail propiedadesEmail = configurarCorreo(recipient, Bcc, subject, textoAniadir);
		
		correcto = sendMail(spBean, propiedadesEmail);
		if (correcto){
			StringBuffer sb = new StringBuffer();
			sb.append("Se ha mandado un correo referente a solicitud de placas.");
			sb.append(" El colegiado que solicitó la placa es: ");
			sb.append(spBean.getNumColegiado());
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo han sido: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido enviar el correo de placas. ");
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo eran: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		}
		return correcto;
		
	}
	
	public boolean sendMailColegio(SolicitudPlacaBean spBean){
		
		boolean correcto = false;
		
		String subject;
		String recipient;
		String Bcc;
		
		String jefaturaProv = utilesColegiado.getIdJefaturaSesion();
		hibernate.entities.general.JefaturaTrafico jefaturaTramite =  getJefaturaFromId(jefaturaProv);
		String correoJefatura = null;
		
		if(jefaturaProv!=null){
			if(jefaturaProv.equals("M")){
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_MADRID);
			} else if(jefaturaProv.equals("M1")){
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ALCORCON);
			} else {
				correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ESTANDAR);
			}
		} else {
			correoJefatura = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_JEFATURA_ESTANDAR);
		}
		
		String userMail = gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_COLEGIO_RECIPIENT);
		
	
		subject =					ConstantesPlacasMatriculacion.ORDER_MAIL_SUBJECT;
		recipient =					userMail;
		Bcc =						gestorPropiedades.valorPropertie(ConstantesPlacasMatriculacion.ORDER_MAIL_COLEGIO_BCC);
		
		if(correoJefatura!=null){
			if(Bcc!=null && !"".equals(Bcc)) {
				Bcc = Bcc.concat("," + correoJefatura);
			} else {
				Bcc = correoJefatura;
			}
		}
		
		String numColegiado = String.valueOf(spBean.getNumColegiado());
		ContratoVO contrato = servicioContrato.getContrato(new BigDecimal(spBean.getIdContrato().intValue()));
		MunicipioVO municipioVO = servicioMunicipio.getMunicipio(contrato.getIdMunicipio(), contrato.getProvincia().getIdProvincia());
		String municipio = municipioVO.getNombre();
		
		String bastidor = spBean.getBastidor();
		
		ColegiadoVO colegiado = contrato.getColegiado();
		String apellidosNombre = colegiado.getUsuario().getApellidosNombre();
		String dniColegiado = colegiado.getUsuario().getNif();
		
		String domicilio = contrato.getIdTipoVia() + " " + contrato.getVia() + ", " + contrato.getNumero()
				+ "<br />" + contrato.getCodPostal() + " " + municipio
				+ " ( " + contrato.getProvincia().getNombre() + " )";
		
		String titular = null;
		Persona persona = placasMatriculacionDAO.getPersonaFromNif(spBean.getNif(), spBean.getNumColegiado());
		if (persona != null) {
			titular = persona.getApellido1RazonSocial()
					+ " " + (persona.getApellido2()!=null ? persona.getApellido2() : " ")
					+ " " + (persona.getNombre()!=null ? persona.getNombre() : " ");
		}
		
		String fechaSolicitud = spBean.getFechaSolicitud()!=null ? spBean.getFechaSolicitud() : "";
		
		String matricula = spBean.getMatricula()!=null ? spBean.getMatricula() : "";
		String delantera = spBean.getTipoPlacaDelantera()!=null ? spBean.getTipoPlacaDelantera().getNombreEnum() : "No Solicitada";
		String trasera = spBean.getTipoPlacaTrasera()!=null ? spBean.getTipoPlacaTrasera().getNombreEnum() : "No Solicitada";
		String adicional = spBean.getTipoPlacaAdicional()!=null ? spBean.getTipoPlacaAdicional().getNombreEnum() : "No solicitada";
		String jefatura = jefaturaTramite!=null && jefaturaTramite.getDescripcion()!=null ? jefaturaTramite.getDescripcion() : "Sin jefatura asociada";
		String duplicada = spBean.isDuplicada() == true ? "Si" : "No";  
		String textoAniadir =		"<p>Se ha confirmado una nueva solicitud de placa para la matrícula: <strong>" + matricula + "</strong></p>"
									+ "<h2><u>Detalle de la solicitud:</u></h2>"
									+ "<h3>Datos de la solicitud</h3>"
									+ "<ul>"
									+ "<li><strong>Fecha de la solicitud</strong>: " + fechaSolicitud + "</li>"
									+ "<li><strong>Placa delantera</strong>: " + delantera + "</li>"
									+ "<li><strong>Placa trasera</strong>: " + trasera + "</li>"
									+ "<li><strong>Placa adicional</strong>: " + adicional + "</li>"
									+ "<li><strong>Placa duplicada</strong>: " + duplicada + "</li>"
									+ "</ul>"
									+ "<h3>Datos del vehículo</h3>"
									+ "<ul>"
									+ "<li><strong>Titular</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(titular)) + "</li>"
									+ "<li><strong>Matrícula</strong>: " + matricula + "</li>"
									+ "<li><strong>Bastidor</strong>: " + bastidor + "</li>"
									+ "</ul>"
									+ "<h3>Datos del colegiado</h3>"
									+ "<ul>"
									+ "<li><strong>Jefatura Provincial</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(jefatura)) + "</li>"
									+ "<li><strong>Número de Colegiado</strong>: " + numColegiado + "</li>"
									+ "<li><strong>Presentador</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(apellidosNombre)) + " (" + dniColegiado + ")</li>"
									+ "<li><strong>Domicilio</strong>: " + WordUtils.capitalizeFully(StringUtils.lowerCase(domicilio)) + "</li>"
									+ "</ul>";
		
		PropiedadesEmail propiedadesEmail = configurarCorreo(recipient, Bcc, subject, textoAniadir);
		
		correcto = sendMail(spBean, propiedadesEmail);
		
		if (correcto){
			StringBuffer sb = new StringBuffer();
			sb.append("Se ha mandado un correo referente a solicitud de placas.");
			sb.append(" El colegiado que solicitó la placa es: ");
			sb.append(spBean.getUsuario().getNumColegiado());
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo han sido: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("No se ha podido enviar el correo de placas. ");
			sb.append(" La matricula es: ");
			sb.append(spBean.getMatricula());
			sb.append(" Los destinatarios del correo eran: ");
			sb.append(recipient);
			sb.append(", ");
			sb.append(Bcc);
			log.error(sb);
		}
		return correcto;
		
	}
	
	@Override
	public boolean sendMail(SolicitudPlacaBean spBean, PropiedadesEmail propiedadesEmail){
		
		boolean correcto;		
		
		ResultBean resultBean = null;
		try {
			resultBean = servicioCorreo.enviarCorreo(propiedadesEmail.getCuerpo(), 
					                                      propiedadesEmail.getTextoPlano(), 
					                                      propiedadesEmail.getRemitente(), 
					                                      propiedadesEmail.getAsunto(), 
					                                      propiedadesEmail.getPara(), 
					                                      propiedadesEmail.getCopia(), 
					                                      null, 
					                                      null,  
					                                      (FicheroBean []) null);
			if(resultBean==null || resultBean.getError())
				throw new OegamExcepcion("Error en la notificacion de error servicio");
						
		} catch (OegamExcepcion | IOException e) {
			e.printStackTrace();
		}
		
		if(resultBean!=null){
			if(resultBean.getError()){
				correcto = false;
			} else {
				correcto = true;
			}
		} else {
			correcto = false;
		}
		
		return correcto;
		
	}
	
	private void generaTablaSinAgrupacion(EstadisticasPlacasBean estadisticasPlacasBean){
		List<SolicitudPlacaVO> lSolicitudPlacaVO =placasMatriculacionDAO.generarEstadisticasSinAgrupacion(estadisticasPlacasBean.getFecha().getFechaInicio(),
				estadisticasPlacasBean.getFecha().getFechaFin());
		
		
		
		if (lSolicitudPlacaVO!=null){
			resultadoHtmlAgruVehiculos.append("<HTML>");
			resultadoHtmlAgruVehiculos.append("<HEAD> <TITLE> <span style=\"font-size:14pt;font-family:Tunga;margin-left:20px;\"> "
					+ "<th> ESTADÍSTICAS DE PLACAS DE MATRICULACIÓN  <br/>"
					+ "Período del "
					+ utilesFecha.formatoFecha(sFormatoFechaIntervalo, estadisticasPlacasBean.getFecha().getFechaInicio()) 
					+ " hasta " 
					+ utilesFecha.formatoFecha(sFormatoFechaIntervalo, estadisticasPlacasBean.getFecha().getFechaFin())
					+ "</th></span></TITLE></HEAD>");
				resultadoHtmlAgruVehiculos.append("<BODY>");
				resultadoHtmlAgruVehiculos.append("<br/> <th>GENERAL PLACAS DE MATRICULA:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> "
						+ "<th>Matrícula</th> "
						+ "<th>Col.</th> "
						+ "<th>Placa delantera</th> "
						+ "<th>Placa trasera</th> "
						+ "<th>Placa adicional</th> "
						+ "<th>Fecha</th></tr>");
				
			//List<SolicitudPlaca> lSolicitudPlaca=conversor.transform(lSolicitudPlacaVO, SolicitudPlaca.class);
			
			for (SolicitudPlacaVO solicitudPlacaVO : lSolicitudPlacaVO){	
				resultadoHtmlAgruVehiculos.append("<tr>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getMatricula());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getNumColegiado());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getTipoDelantera()!=null ? TipoPlacasMatriculasEnum.convertir(solicitudPlacaVO.getTipoDelantera()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getTipoTrasera()!=null ? TipoPlacasMatriculasEnum.convertir(solicitudPlacaVO.getTipoTrasera()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getTipoAdicional()!=null ? TipoPlacasMatriculasEnum.convertir(solicitudPlacaVO.getTipoAdicional()) : "");
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("<td>");
				resultadoHtmlAgruVehiculos.append(solicitudPlacaVO.getFechaSolicitud());
				resultadoHtmlAgruVehiculos.append("</td>");
				resultadoHtmlAgruVehiculos.append("</tr>");
			}
			resultadoHtmlAgruVehiculos.append("</table>");			
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void generaTablaAgrupadaPorVehiculo(EstadisticasPlacasBean estadisticasPlacasBean){
		if (estadisticasPlacasBean.isAgrTipoVehiculo()==true){
			listaSolicitudAgruVehiculo = placasMatriculacionDAO.generarEstadisticasAgrupadoPorVehiculo(estadisticasPlacasBean.getFecha().getFechaInicio(),
					estadisticasPlacasBean.getFecha().getFechaFin()
					);
			
			if(!listaSolicitudAgruVehiculo.isEmpty()){
				resultadoHtmlAgruVehiculos.append("<br/> <th>SOLICITUDES DE PLACAS SEGÚN EL TIPO DE VEHÍCULO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> "
						+ "<th>Tipo de vehiculo</th>");
				resultadoHtmlAgruVehiculos.append("<th>Total de solicitudes de placas</th></tr>");
				for (List listaVehiculos : listaSolicitudAgruVehiculo){
					if (listaVehiculos.get(1)!=null && listaVehiculos.get(0)!=null){
						resultadoHtmlAgruVehiculos.append("<tr>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(listaVehiculos.get(0));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(listaVehiculos.get(1));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("</tr>");
					}
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void generaTablaAgrupadaCreditosColegiado(EstadisticasPlacasBean estadisticasPlacasBean){
		if (estadisticasPlacasBean.isAgrTipoCredito()==true){
			mapaAgrupaCreditoColegiado = placasMatriculacionDAO.generarEstadisticasAgrupadoPorCreditosColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(),
					estadisticasPlacasBean.getFecha().getFechaFin()
					);
			mapaSolicitudAgruNumColegiado = placasMatriculacionDAO.generarEstadisticasAgrupadoPorNumColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(),
					estadisticasPlacasBean.getFecha().getFechaFin());
			if (!mapaAgrupaCreditoColegiado.isEmpty()){
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR TIPO DE CRÉDITO Y NÚMERO DE COLEGIADO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> "
						+ "<th>Número de colegiado</th> " 
						+ "<th>Turismo Ordinaria Larga<br/> CTP1</th>"
						+ "<th>Turismo Ordinaria Corta<br/> CTP2</th>"
						+ "<th>Motocicleta<br/> CTP3</th>"
						+ "<th>Moto Corta<br/> CTP4</th>"
						+ "<th>Tractor<br/> CTP5</th>"
						+ "<th>Ciclomotor<br/> CTP6</th>"
						+ "<th>Total de placas</th></tr>"
						);
				
				Iterator entries = mapaAgrupaCreditoColegiado.entrySet().iterator();
				while (entries.hasNext()) {
					  Entry thisEntry = (Entry) entries.next();
					  String colegiado = (String)thisEntry.getKey();
					  HashMap<Integer, Integer> mapaTipoPlacaDeColegiado = (HashMap<Integer, Integer>)thisEntry.getValue();
					  resultadoHtmlAgruVehiculos.append("<tr>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(colegiado);
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[0])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[0]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[1])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[1]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[2])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[2]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[3])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[3]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[4])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[4]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[5])!=null?mapaTipoPlacaDeColegiado.get(arrayTipoPlacas[5]):"0");
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(mapaSolicitudAgruNumColegiado.get(colegiado));
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("</tr>");
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}
	
	private void generaTablaAgrupadaCreditos(EstadisticasPlacasBean estadisticasPlacasBean){
		if (estadisticasPlacasBean.isAgrTipoCredito()==true){
			mapaSolicitudAgruCreditos = placasMatriculacionDAO.generarEstadisticasAgrupadoPorCreditos(estadisticasPlacasBean.getFecha().getFechaInicio(),
					estadisticasPlacasBean.getFecha().getFechaFin());
			if (!mapaSolicitudAgruCreditos.isEmpty()){
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR TIPO DE CRÉDITO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> "
						+ "<th>Tipo de matricula</th> ");
				resultadoHtmlAgruVehiculos.append("<th>Total de créditos consumidos</th></tr>");
				for (Integer tipoPlaca : arrayTipoPlacas){
					if (mapaSolicitudAgruCreditos.containsKey(tipoPlaca)){
						resultadoHtmlAgruVehiculos.append("<tr>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(TipoPlacasMatriculasEnum.convertir(tipoPlaca));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("<td>");
						resultadoHtmlAgruVehiculos.append(mapaSolicitudAgruCreditos.get(tipoPlaca));
						resultadoHtmlAgruVehiculos.append("</td>");
						resultadoHtmlAgruVehiculos.append("</tr>");
					}
				}
				resultadoHtmlAgruVehiculos.append("</table>");
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void generaTablaAgrupadaPorColegiado(EstadisticasPlacasBean estadisticasPlacasBean){
		if (estadisticasPlacasBean.isNumColegiado()==true){
			mapaSolicitudAgruNumColegiado = placasMatriculacionDAO.generarEstadisticasAgrupadoPorNumColegiado(estadisticasPlacasBean.getFecha().getFechaInicio(),
					estadisticasPlacasBean.getFecha().getFechaFin());
			if (mapaSolicitudAgruNumColegiado!=null){
				mapaSolicitudAgruNumColegiado.keySet();
				resultadoHtmlAgruVehiculos.append("<br/> <th>AGRUPACIÓN POR NÚMERO DE COLEGIADO:</th>");
				resultadoHtmlAgruVehiculos.append("<table border='1' width='100%'> <tr> "
						+ "<th>Número de colegiado</th> ");
				resultadoHtmlAgruVehiculos.append("<th>Total de placas solicitadas</th></tr>");
				Iterator entries = mapaSolicitudAgruNumColegiado.entrySet().iterator();
				while (entries.hasNext()) {
					  Entry thisEntry = (Entry) entries.next();
					  String colegiado = (String)thisEntry.getKey();
					  Integer total = (Integer)thisEntry.getValue();
					  
					  resultadoHtmlAgruVehiculos.append("<tr>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(colegiado);
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("<td>");
					  resultadoHtmlAgruVehiculos.append(total);
					  resultadoHtmlAgruVehiculos.append("</td>");
					  resultadoHtmlAgruVehiculos.append("</tr>");
				}
				
			}
			resultadoHtmlAgruVehiculos.append("</table>");
		}
	}

	/*
	 * Método que guardará con formato html en un StringBuffer una serie de tablas en función
	 * de los checks que haya marcado el usuario. La tabla sin agrupación es una tabla con
	 * información general que siempre se creará. El resto de tablas se evaluará dentro de
	 * los métodos generaX si tiene que crearse o no.
	 * Despúes convertirá el html a pdf para que sea descargado.
	 */
	public byte[] generarEstadisticas(EstadisticasPlacasBean estadisticasPlacasBean){
		
		resultadoHtmlAgruVehiculos.setLength(0);//hace un clear del StringBuffer
		generaTablaSinAgrupacion(estadisticasPlacasBean);
		generaTablaAgrupadaPorVehiculo(estadisticasPlacasBean);
		generaTablaAgrupadaCreditosColegiado(estadisticasPlacasBean);
		generaTablaAgrupadaCreditos(estadisticasPlacasBean);
		generaTablaAgrupadaPorColegiado(estadisticasPlacasBean);
		
		resultadoHtmlAgruVehiculos.append("</BODY></HTML>");
		return generarPDFTemporal(resultadoHtmlAgruVehiculos);
		
	}
	
	private byte[] generarPDFTemporal(StringBuffer resultadoHtmlAgruVehiculos){
		OutputStream outputStream = null;
		byte[] result = null;
		try{
			
			ITextRenderer iTextRenderer = new ITextRenderer();

			/** 
             * Setting the document as the url value passed.
             * This means that the iText renderer 
             * has to parse this html document to generate the pdf.
             */
		
            iTextRenderer.setDocumentFromString(resultadoHtmlAgruVehiculos.toString());
            iTextRenderer.layout();
 
            /** The generated pdf will be written 
                to the invoice.pdf file. */
            outputStream = new ByteArrayOutputStream();
            /** Creating the pdf and 
                writing it in invoice.pdf file. */
            iTextRenderer.createPDF(outputStream);
           
            result = ((ByteArrayOutputStream)outputStream).toByteArray();
		}catch(Exception e ){
			log.error(e.getMessage());
		} finally {
			if (outputStream!=null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
		 return result;
		
	}
	
	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	private SolicitudPlacaBean getSolicitudPorMatricula(SolicitudPlacaBean solicitudPlacaBean){
		SolicitudPlacaBean solicitudPlacaBeanRetorno=null;
		solicitudPlacaBean.setFechaSolicitud(utilesFecha.getFechaHoy());
		SolicitudPlacaVO solicitudPlacaVO=prepararSolicitud(solicitudPlacaBean);
		
				
		solicitudPlacaVO.setNumColegiado(solicitudPlacaBean.getNumColegiado());
		
		SolicitudPlacaVO solicitud = placasMatriculacionDAO.getSolicitudPorClaveUnica(solicitudPlacaVO);
		
			
		
		if (solicitud!=null){
			solicitudPlacaBean = convertSolicitudPantalla(solicitud);
			solicitudPlacaBeanRetorno= solicitudPlacaBean;
		}
		
		return solicitudPlacaBeanRetorno;
	}
	
 
	public Usuario getUsuario(Integer idUsuario){
		Usuario usuario = placasMatriculacionDAO.getUsuario(idUsuario);
		return usuario;
	}
	
	@Override 
	public List<SolicitudPlacaBean> crearNuevasSolicitudes(List<SolicitudPlacaBean> listaSolicitudesp, List<TramiteTraficoVO> tramites, Usuario usuario, Integer idContrato) throws OegamExcepcion {
		List<SolicitudPlacaBean> listaSolicitudesExistente = new ArrayList<SolicitudPlacaBean>();  
		// Si vienen vehículos partimos de consulta de trámites
		SolicitudPlacaBean solicitudPlacaBean = null;
			
		for (TramiteTraficoVO tramite : tramites){
			solicitudPlacaBean = new SolicitudPlacaBean(); 
		
			solicitudPlacaBean.setNumExpediente(tramite.getNumExpediente().longValue());
			
			VehiculoVO vehiculo=tramite.getVehiculo();			
			
			solicitudPlacaBean.setMatricula(vehiculo.getMatricula());
			solicitudPlacaBean.setBastidor(vehiculo.getBastidor());
			solicitudPlacaBean.setTipoVehiculo(vehiculo.getTipvehi());
			
			
			Set<IntervinienteTraficoVO> intervinientes = tramite.getIntervinienteTraficos();
			for (IntervinienteTraficoVO interviniente : intervinientes){
				if (interviniente.getTipoIntervinienteVO().getTipoInterviniente().equals(TipoInterviniente.Titular.getValorEnum())){
					solicitudPlacaBean.setNif(interviniente.getId().getNif());
					
				}
			}
			listaSolicitudesp.add(solicitudPlacaBean);
			
		}
		
		
		for (SolicitudPlacaBean solicitudPlacaBean1 : listaSolicitudesp) {
			
			SolicitudPlacaBean spBeanAux = new SolicitudPlacaBean(); 
			
			//Comprobar si la matricula existe o es nueva solicitud
			solicitudPlacaBean1.setUsuario(usuario);
			spBeanAux=getSolicitudPorMatricula(solicitudPlacaBean1);
			
			if(spBeanAux==null){
				TipoPlacasMatriculasEnum tipoPlaca = getTipoPlacaPorDefecto(solicitudPlacaBean1.getMatricula());
				spBeanAux = new SolicitudPlacaBean(); 
				spBeanAux.setEstado(EstadoSolicitudPlacasEnum.Iniciado.getValorEnum());
				spBeanAux.setFechaSolicitud(utilesFecha.getFechaHoy());
				spBeanAux.setIdContrato(idContrato);
				spBeanAux.setUsuario(usuario);
				if(usuario!=null)
				{
				spBeanAux.setNombreUsuario(usuario.getApellidosNombre());
				}
				spBeanAux.setTipoPlacaDelantera(tipoPlaca.getValorEnum());
				if (solicitudPlacaBean1.isIndividual()){
					spBeanAux.setTipoPlacaTrasera(null);
				}else{
					spBeanAux.setTipoPlacaTrasera(tipoPlaca.getValorEnum());
				}
				spBeanAux.setIndividual(solicitudPlacaBean1.isIndividual());
				spBeanAux.setNumExpediente(solicitudPlacaBean1.getNumExpediente());
				spBeanAux.setMatricula(solicitudPlacaBean1.getMatricula());
				spBeanAux.setBastidor(solicitudPlacaBean1.getBastidor());
				spBeanAux.setTipoVehiculo(solicitudPlacaBean1.getTipoVehiculo());
				spBeanAux.setNif(solicitudPlacaBean1.getNif());
				
			}
			listaSolicitudesExistente.add(spBeanAux);
		}
		
		return listaSolicitudesExistente;
	}
	
	private hibernate.entities.general.JefaturaTrafico getJefaturaFromId(String jefaturaProv){
		return placasMatriculacionDAO.getJefaturaFromId(jefaturaProv);
	}

	@Override
	public boolean borrarSolicitud(SolicitudPlacaVO solicitud){
		solicitud.setEstado(0);
		
		boolean resultado = placasMatriculacionDAO.borrarSolicitud(solicitud);
		
		
		
		return resultado;
	}
	
	@Override
	public VehiculoVO getVehiculoPlacasPorNumExpediente(Long numExpediente) {
		return null;
	}

	
	
}
