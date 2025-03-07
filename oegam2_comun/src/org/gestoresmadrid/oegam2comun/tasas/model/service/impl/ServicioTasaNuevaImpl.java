package org.gestoresmadrid.oegam2comun.tasas.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.tasas.model.dao.EvolucionTasaDao;
import org.gestoresmadrid.core.tasas.model.dao.TasaDao;
import org.gestoresmadrid.core.tasas.model.vo.TasaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioEvolucionTasaNueva;
import org.gestoresmadrid.oegam2comun.tasas.model.service.ServicioTasaNueva;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.ResultadoConsultaTasasBean;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTrafico;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.tasa.view.dto.TasaDto;
import org.gestoresmadrid.oegamComun.trafico.view.dto.TramiteTraficoDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.ficheros.BorrarFicherosThread;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioTasaNuevaImpl implements ServicioTasaNueva {

	private static final long serialVersionUID = -3577452955635288032L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTasaNuevaImpl.class);
	
	private boolean ficheroOK = false;
	
	@Autowired
	TasaDao tasaDao;
	
	@Autowired
	EvolucionTasaDao evolucionTasa;
	
	@Autowired
	ServicioEvolucionTasaNueva servicioEvolucionTasa;
	
	@Autowired
	ServicioTramiteTrafico servicioTramiteTrafico;
	
	@Autowired
	Conversor conversor;
	
	@Autowired
	GestorDocumentos gestorDocumentos;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Override
	@Transactional
	public ResultadoConsultaTasasBean eliminaTasaBloque(String idstasaSeleccion, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		try{
			if(idstasaSeleccion != null){
				TasaVO tasaBBDD = getTasaVO(idstasaSeleccion);
				resultado = validarTasaBloqueEliminar(tasaBBDD, idContrato, esAdmin);
				if(!resultado.getError()){
					if(tasaBBDD != null){
						ResultBean resulEv = servicioEvolucionTasa.eliminarEvolucionTasa(idstasaSeleccion);
						if(!resulEv.getError()){
							tasaDao.borrar(tasaBBDD);
							resultado.setMensaje("La tasa " + idstasaSeleccion + " ha sido eliminada");
						}else{
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("La tasa " + idstasaSeleccion +  " no se ha podido eliminar");
						}
					}else{
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La tasa no existe");
					}
				}
			}else{
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe indicar la tasa a eliminar");
			}
		} catch (Exception e){
			log.error("Ha sucedido un error a la hora de eliminar la tasa, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar la tasa.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	
	private ResultadoConsultaTasasBean validarTasaBloqueEliminar(TasaVO tasaBBDD, BigDecimal idContrato, Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(tasaBBDD == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa no existe.");
		} else if(!esAdmin && tasaBBDD.getContrato() != null && !idContrato.toString().equals(tasaBBDD.getContrato().getIdContrato().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede eliminar la tasa " + tasaBBDD.getCodigoTasa() +  " porque no corresponde con el contrato.");
		}else if(tasaBBDD.getFechaAsignacion() != null){
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("La tasa " + tasaBBDD.getCodigoTasa() +  " está asignada y no se puede eliminar.");
		}
		return resultado;
	}
	
	@Override
	@Transactional
	public ResultadoConsultaTasasBean desasignarTasaBloque(String idsTasaSeleccion,BigDecimal numExpedientes, BigDecimal idUsuario, BigDecimal idContrato, Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		try{
			if(idsTasaSeleccion != null){
				TasaVO tasaBBDD = getTasaVO(idsTasaSeleccion);
				List<TramiteTraficoVO> listaTramites = servicioTramiteTrafico.getListaExpedientesPorTasa(idsTasaSeleccion);
				resultado = validarTasaBloqueDesasignar(tasaBBDD,listaTramites, idContrato, esAdmin);
				if(!resultado.getError()){
					if(listaTramites != null && !listaTramites.isEmpty()){
						for(TramiteTraficoVO traficoVO : listaTramites){
							ResultBean resultTramite = servicioTramiteTrafico.desasignarTasaTramites(traficoVO,tasaBBDD.getCodigoTasa());
							if(resultTramite.getError()){
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("La tasa para el expediente " +numExpedientes+ "no se pudo desasignar");
							}else{
								if(tasaBBDD.getNumExpediente() == null){
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje("No existe número de expediente para la tasa " + idsTasaSeleccion );
								}else{
									tasaBBDD.setNumExpediente(null);
									tasaBBDD.setFechaAsignacion(null);
									servicioEvolucionTasa.insertarEvolucionTasa(tasaBBDD, ServicioEvolucionTasaNuevaImpl.DESASIGNAR);
									tasaBBDD = (TasaVO) tasaDao.actualizar(tasaBBDD);
									resultado.setMensaje("La tasa " + idsTasaSeleccion + " ha sido eliminada");
								}
							}
						}
					}
				}else{
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Debe indicar la tasa a eliminar");
				}
			}
		} catch (Exception e){
			log.error("Ha sucedido un error a la hora de eliminar la tasa, error: ",e, numExpedientes.toString());
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de eliminar la tasa.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}
	
	private ResultadoConsultaTasasBean validarTasaBloqueDesasignar(TasaVO tasaBBDD, List<TramiteTraficoVO> listaTramites,BigDecimal idContrato,Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(tasaBBDD == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa no existe.");
		} else if(!esAdmin && tasaBBDD.getContrato() != null && !idContrato.toString().equals(tasaBBDD.getContrato().getIdContrato().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede desasignar la tasa " + tasaBBDD.getCodigoTasa() + " porque no corresponde con el contrato.");
/*		}else if (numExpedientes == null || numExpedientes.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Debe seleccionar a que expediente desea desasignar la tasa.");*/
		}else if (listaTramites == null || listaTramites.isEmpty()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa " + tasaBBDD.getCodigoTasa() + " no esta asignada a ningun expediente para poder desasignarla.");
		}else{ 
			String[] numExps = tasaBBDD.getNumExpediente().toString().split("-");
		//mirar el Iterator si hace su funcion y sino dejar como estaba	
			for(TramiteTraficoVO tramiteTrafico : listaTramites){
				for(String numExp : numExps){
					if(tramiteTrafico.getNumExpediente().compareTo(new BigDecimal(numExp)) == 0){
					Iterator<TramiteTraficoVO> iter = listaTramites.iterator();
						while(iter.hasNext()){
							tramiteTrafico = iter.next();
							if(TipoTramiteTrafico.Anuntis.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && !EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTrafico.getEstado())){
								resultado.setError(Boolean.TRUE);
								resultado.setMensaje("El tramite: " + numExp + " no es modificable, no se puede desasignar la tasa.");
								iter.remove();
								break;
							}else if(!TipoTramiteTrafico.Anuntis.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && !TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTrafico.getTipoTramite()) 
									&& !EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.No_Tramitable.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Tramitable.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum().equals(tramiteTrafico.getEstado())){
								resultado.setError(Boolean.TRUE);	
								resultado.setMensaje("El tramite: " + numExp + " no es modificable, no se puede desasignar la tasa.");
								iter.remove();
									break;
							}else if(TipoTramiteTrafico.Solicitud.getValorEnum().equals(tramiteTrafico.getTipoTramite()) && !EstadoTramiteTrafico.Iniciado.getValorEnum().equals(tramiteTrafico.getEstado()) &&
									!EstadoTramiteTrafico.No_Tramitable.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Tramitable_Incidencias.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Tramitable.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Validado_Telematicamente.getValorEnum().equals(tramiteTrafico.getEstado()) &&
									!EstadoTramiteTrafico.Validado_PDF.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Finalizado_Con_Error.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Tramitable_Jefatura.getValorEnum().equals(tramiteTrafico.getEstado()) && !EstadoTramiteTrafico.Finalizado_Excel_Incidencia.getValorEnum().equals(tramiteTrafico.getEstado()) 
									&& !EstadoTramiteTrafico.Finalizado_Parcialmente.getValorEnum().equals(tramiteTrafico.getEstado())){
									resultado.setError(Boolean.TRUE);
									resultado.setMensaje("El tramite: " + numExp + " no es modificable, no se puede desasignar la tasa.");
									iter.remove();
									break;
							}
						}
					}
				}
			}
		}
		return resultado;
	}
	

	@Override
	@Transactional
	public ResultadoConsultaTasasBean generarCertificadoTasa(String idstasaSeleccion, BigDecimal idUsuarioDeSesion) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		/*try { 
			if((listaChecksConsultaTasa == null || listaChecksConsultaTasa.isEmpty()) 
					&& (listaChecksConsultaTasaPegatinas == null || listaChecksConsultaTasaPegatinas.isEmpty())){
				resultado.setError(true);
				resultado.setMensajeError("Debe seleccionar alguna tasa para generar el certificado.");
			}else{
				List<CertificadoTasasBean> listaCertificados = new ArrayList<CertificadoTasasBean>();
				if(listaChecksConsultaTasa != null && !listaChecksConsultaTasa.isEmpty()){
					generarCertificadoTasas(listaChecksConsultaTasa,resultado, listaCertificados);
				}
				if(listaChecksConsultaTasaPegatinas != null && !listaChecksConsultaTasaPegatinas.isEmpty()){
					generarCertificaTasasPegatinas(listaChecksConsultaTasaPegatinas,resultado, listaCertificados);
				}
				if(listaCertificados.size() > 0){
					generarPdfCertificadosTasas(listaCertificados,resultado,idContrato);
				}
				
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el certificado de las tasas seleccionadas, error: ",e);
			resultado.setError(true);
			resultado.setMensajeError("Ha sucedido un error a la hora de generar el certificado de las tasas seleccionadas.");
		}*/
		return resultado;
	}
	

	
	/*private ResultBean listarTablaCompleta(String idSession) {
		ResultBean resultado = new ResultBean(); 
		
		ArrayList<String> lineasExport = new ArrayList<String>();
		if (lista!=null){						
			//Recorremos toda la lista de creditosFacturados
			for (TasaVO tasaVo : lista) {						
				String lineaExport = "";					
				lineaExport += null!=tasaVo.getNumExpediente()?tasaVo.getNumExpediente():"";
				lineaExport += ";";
				lineaExport += null!=tasaVo.getCodigoTasa()?tasaVo.getCodigoTasa():"";
				lineaExport += ";";
				lineaExport += null!=tasaVo.getFechaAsignacion()?tasaVo.getFechaAsignacion():"";
				lineaExport += ";";
					
				//Conjunto de tramites por cada creditoFactura
				for (CreditoFacturadoTramiteVO creditoFacturadoTramite : creditoFacturado.getCreditoFacturadoTramites()) {
					lineaExport+=null!=creditoFacturadoTramite.getIdTramite()?creditoFacturadoTramite.getIdTramite():"";	
					lineaExport += " | ";										
				} 
				//Quitamos el ultimo separador de cada registro de expedientes
				lineaExport = lineaExport.substring(0,lineaExport.length()-3);
				
				lineaExport += ";";
				lineasExport.add(lineaExport);
			}					
		}			
		resultado.addAttachment("contenidoFichero", lineasExport);			
	}
	catch (Exception e) {
		log.error("Ha sucedido un error a la hora de exportar la tabla, error: ",e);
		resultado.setError(Boolean.TRUE);
		resultado.setMensaje("Ha sucedido un error a la hora de exportar la tabla.");
	}
		
	
	return resultado;
		
	}*/


	@Override
	@Transactional
	public ResultadoConsultaTasasBean validarTasaExportacion(TasaDto tasaDto, BigDecimal idContrato, Boolean esAdmin) {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		if(tasaDto == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("La tasa no existe.");
		} else if(!esAdmin && tasaDto.getContrato() != null && !idContrato.toString().equals(tasaDto.getContrato().getIdContrato().toString())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No se puede exportar la tasa porque no corresponde con el contrato.");
		}else{
			resultado.setMensaje("La tasa " );
		}
		return resultado;
	}
	@Override
	@Transactional
	public ResultadoConsultaTasasBean exportarTasas(List<TasaDto> listaTasas) throws OegamExcepcion {
		ResultadoConsultaTasasBean resultado = new ResultadoConsultaTasasBean(Boolean.FALSE);
		try{
			try{
				String idSession = ServletActionContext.getRequest().getSession().getId();
				FicheroBean fichero = new FicheroBean();
				fichero.setTipoDocumento(ConstantesGestorFicheros.TASAS);
				fichero.setSubTipo(ConstantesGestorFicheros.EXPORTAR);
				fichero.setExtension(ConstantesGestorFicheros.EXTENSION_TXT);
				fichero.setNombreDocumento(ConstantesGestorFicheros.NOMBRE_FICHERO_TASA + idSession);
				fichero.setSobreescribir(true);
				fichero.setFecha(utilesFecha.getFechaActual());
				File file = gestorDocumentos.guardarFichero(fichero);
				if(file == null){
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha podido guardar el xml con los datos de la consulta de tasas");
				}
				// y a los 5 minutos, lo borraremos
				BorrarFicherosThread hiloBorrar = new BorrarFicherosThread(file.getAbsolutePath());
				hiloBorrar.start();
				log.debug("Se lanza el hilo para borrar el fichero creado, pasados 5 minutos");
				resultado.setMensaje("Tasas exportadas correctamente");
				ficheroOK = true;
			} catch (Exception e) {
				resultado.setMensaje("Error al crear el fichero");
			}
		} catch (Exception e){
			log.error("Ha sucedido un error a la hora de exportar la tasa, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de exportar la tasa.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
		
	}
	

	@Override
	@Transactional(readOnly=true)
	public ResultadoConsultaTasasBean getTasaDto(String idCodigoTasa) {
		ResultadoConsultaTasasBean resultado  = new ResultadoConsultaTasasBean(Boolean.FALSE);
		try {
			TasaVO tasaVO = getTasaVO(idCodigoTasa);
			if (tasaVO != null) {
				TasaDto tasaDto = conversor.transform(tasaVO, TasaDto.class);
				List<TramiteTraficoDto> listaExpedientesTasa = servicioTramiteTrafico.getListaExpedientesPantallaPorTasa(idCodigoTasa);
				if (listaExpedientesTasa != null) {
					tasaDto.setListaExpedientesAsignados(listaExpedientesTasa);
				}
				resultado.setTasaDto(tasaDto);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos para la tasa seleccionada.");
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa con codigo: " + idCodigoTasa, e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al obtener la tasa con codigo: " + idCodigoTasa);
		}
		return resultado;
	}


	@Override
	@Transactional(readOnly=true)
	public TasaVO getTasaVO(String idCodigoTasa) {
		try {
			TasaVO tasaVO = tasaDao.getTasa(idCodigoTasa, null, null);
			if (tasaVO != null) {
				return tasaVO;
			}
		} catch (Exception e) {
			log.error("Error al obtener la tasa con codigo: " + idCodigoTasa, e);
		}
		return null;
	}






}
