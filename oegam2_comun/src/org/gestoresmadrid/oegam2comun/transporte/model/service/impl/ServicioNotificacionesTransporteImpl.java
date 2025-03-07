package org.gestoresmadrid.oegam2comun.transporte.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.gestoresmadrid.core.transporte.model.dao.NotificacionTransporteDao;
import org.gestoresmadrid.core.transporte.model.enumeration.EstadosNotificacionesTransporte;
import org.gestoresmadrid.core.transporte.model.vo.NotificacionTransporteVO;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioMunicipio;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioImpresionTransporte;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioNotificacionesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.NotificacionTransporteDto;
import org.gestoresmadrid.oegamComun.colegiado.view.dto.ColegiadoDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.oegamComun.direccion.view.dto.MunicipioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import trafico.beans.jaxb.pruebaCertificado.DatosFirmados;
import trafico.beans.jaxb.pruebaCertificado.SolicitudPruebaCertificado;
import trafico.utiles.XMLPruebaCertificado;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioNotificacionesTransporteImpl implements ServicioNotificacionesTransporte {

	private static final long serialVersionUID = -4647739553123665795L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioNotificacionesTransporteImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioMunicipio servicioMunicipio;

	@Autowired
	Conversor conversor;

	@Autowired
	NotificacionTransporteDao notificacionTransporteDao;

	@Autowired
	ServicioImpresionTransporte servicioImpresionTransporte;

	private UtilesViafirma utilesViafirma;

	@Override
	@Transactional(readOnly=true)
	public List<String> getListaFicherosPdfPorIdTramite(String[] idsNotificaciones) {
		List<String> listaFicheros = new ArrayList<>();
		try {
			for (String idNotificacion : idsNotificaciones) {
				NotificacionTransporteDto notificacionTransporteDto = getNotificacionTransporteDto(Long.parseLong(idNotificacion), Boolean.FALSE);
				if (notificacionTransporteDto != null) {
					if (EstadosNotificacionesTransporte.Aceptada.getValorEnum()
							.equals(notificacionTransporteDto.getEstado())
							&& notificacionTransporteDto.getNombrePdf() != null
							&& !notificacionTransporteDto.getNombrePdf().isEmpty()) {
						listaFicheros.add(notificacionTransporteDto.getNombrePdf());
					}
				}
			}
			if (listaFicheros != null && !listaFicheros.isEmpty() && listaFicheros.size() > 1) {
				HashSet<String> hashSet = new HashSet<String>(listaFicheros);
				listaFicheros.clear();
				listaFicheros.addAll(hashSet);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista con los ficheros que se desean descargar, error: ",e);
		}
		return listaFicheros;
	}

	@Override
	public ResultadoTransporteBean imprimirNotificaciones(List<NotificacionTransporteDto> listaNotificacionesTrans, ContratoDto contrato, Date fecha) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		try {
			if (listaNotificacionesTrans != null && !listaNotificacionesTrans.isEmpty()) {
				if (pruebaCaducidadCert(contrato.getColegiadoDto())) {
					ResultBean resultImpresion = servicioImpresionTransporte.imprimirListadoNotificaciones(listaNotificacionesTrans,contrato, fecha);
					if (resultImpresion.getError()) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje(resultImpresion.getMensaje());
					} else {
						String nombreFichero = ConstantesGestorFicheros.NOMBRE_NOTIF_TRANSPORTE + "_" + contrato.getColegiadoDto().getNumColegiado() + "_" 
								+ new SimpleDateFormat("yyyyMMddHHmmss").format(fecha.getTime()); 
						File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.NOTIFICACIONES,
								new Fecha(new SimpleDateFormat("ddMMyyyy").format(fecha.getTime())), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF,(byte[]) resultImpresion.getAttachment("ficheroNotPdf"));
						if (fichero != null) {
							resultado.setNombreFichero(nombreFichero);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("Ha sucedido un error a la hora de guardar el pdf de las notificaciones.");
						}
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("El certificado del colegiado esta caducado por lo que no se podra firmar ninguna notificación");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de las notificaciones para imprimir.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de imprimir las notificaciones, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir las notificaciones.");
		}
		return resultado;
	}

	private Boolean pruebaCaducidadCert(ColegiadoDto colegiadoDto) {
		Boolean esOk = false;
		SolicitudPruebaCertificado solicitudPruebaCertificado = new SolicitudPruebaCertificado();
		trafico.beans.jaxb.pruebaCertificado.ObjectFactory objectFactory = new trafico.beans.jaxb.pruebaCertificado.ObjectFactory();
		solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
		DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
		datosFirmados.setAlias(colegiadoDto.getAlias());
		solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
		XMLPruebaCertificado xmlPruebaCertificado = new XMLPruebaCertificado();
		String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
		String idFirma = getUtilesViafirma().firmarPruebaCertificadoCaducidad(xml, colegiadoDto.getAlias());
		if (idFirma != null) {
			esOk = true;
		}
		return esOk;
	}

	@Override
	@Transactional
	public ResultBean cambiarEstadoNotificacionesImpresion(List<NotificacionTransporteDto> listaNotificacionesDto, BigDecimal idUsuario, Date fecha, String nombreFichero) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if(listaNotificacionesDto != null && !listaNotificacionesDto.isEmpty()) {
				for(NotificacionTransporteDto notificacionTransporteDto : listaNotificacionesDto) {
					NotificacionTransporteVO notificacionTransporteVO = conversor.transform(notificacionTransporteDto, NotificacionTransporteVO.class);
					notificacionTransporteVO.setEstado(EstadosNotificacionesTransporte.Aceptada.getValorEnum());
					notificacionTransporteVO.setIdUsuario(idUsuario.longValue());
					notificacionTransporteVO.setFechaModificacion(fecha);
					notificacionTransporteVO.setNombrePdf(nombreFichero);
					notificacionTransporteDao.guardarOActualizar(notificacionTransporteVO);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos de la lista de notificaciones para poder actualizar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de aceptar las notificaciones, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de aceptar las notificaciones.");
		}
		if (resultado.getError()) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional
	public ResultBean getListadoNotificacionesTransImprimir(String[] idsNotificacionesTrans, BigDecimal idContratoSession, Boolean esAdmin) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			List<NotificacionTransporteDto> listaNotificaciones = new ArrayList<>();
			for(String idNotificacionTrans : idsNotificacionesTrans){
				NotificacionTransporteDto notificacionTransporteDto = getNotificacionTransporteDto(Long.parseLong(idNotificacionTrans),Boolean.TRUE);
				ResultBean resultVal = validarNotificacionImprimir(notificacionTransporteDto, idContratoSession, esAdmin);
				if (!resultVal.getError()) {
					listaNotificaciones.add(notificacionTransporteDto);
				} else {
					resultBean.setError(Boolean.TRUE);
					resultBean.setMensaje(resultVal.getMensaje());
					break;
				}
			}
			if (!resultBean.getError()) {
				resultBean.addAttachment(LISTA_NOT_TRANS_DTO, listaNotificaciones);
			} 
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la lista de notificaciones, error: ",e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de obtener la lista de notificaciones.");
		}
		return resultBean;
	}

	private ResultBean validarNotificacionImprimir(NotificacionTransporteDto notificacionTransporteDto, BigDecimal idContratoSession, Boolean esAdmin) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		String mensajeError = "No se pueden imprimir las notificaciones seleccionadas porque ";
		if(notificacionTransporteDto == null){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeError + "para una de las notificaciones no existen resultado.");
		} else if(!EstadosNotificacionesTransporte.Por_Defecto.getValorEnum().equals(notificacionTransporteDto.getEstado())){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeError + "la notificación para la empresa con nif: " + notificacionTransporteDto.getNifEmpresa() + " no se puede generar porque no se encuentra en estado '" + EstadosNotificacionesTransporte.Por_Defecto.getNombreEnum() + "'.");
		} else if(!esAdmin && notificacionTransporteDto.getContrato().getIdContrato().compareTo(idContratoSession) != 0){
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensajeError + "la notificación para la empresa con nif: " + notificacionTransporteDto.getNifEmpresa() + " no se puede generar porque no es del mismo contrato.");
		}
		return resultado;
	}

	@Override
	@Transactional
	public NotificacionTransporteDto getNotificacionTransporteDto(Long idNotificacionTrans, Boolean notificacionCompleta) {
		NotificacionTransporteDto notificacionTransporteDto = null;
		if (idNotificacionTrans != null) {
			NotificacionTransporteVO notificacionTransporteVO = getNotificacionTransporteVO(idNotificacionTrans, notificacionCompleta);
			if (notificacionTransporteVO != null) {
				notificacionTransporteDto = conversor.transform(notificacionTransporteVO, NotificacionTransporteDto.class);
				if (notificacionCompleta) {
					MunicipioDto municipioDto = null;
					if (notificacionTransporteDto.getContrato() != null
							&& notificacionTransporteDto.getContrato().getIdMunicipio() != null
							&& !notificacionTransporteDto.getContrato().getIdMunicipio().isEmpty()) {
						municipioDto = conversor.transform(servicioMunicipio.getMunicipio(notificacionTransporteDto.getContrato().getIdMunicipio(), notificacionTransporteDto.getContrato().getIdProvincia()), MunicipioDto.class);
					}
					notificacionTransporteDto.setMunicipioContrato(municipioDto);
				}
			}
		}
		return notificacionTransporteDto;
	}

	@Override
	@Transactional
	public ResultBean rechazarNotificaciones(Long idNotificacionTransporte, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			if (idNotificacionTransporte != null) {
				NotificacionTransporteVO notificacionTransporteVO = getNotificacionTransporteVO(idNotificacionTransporte,Boolean.FALSE);
				if (notificacionTransporteVO != null) {
					resultado.addAttachment(NIF_EMPRESA, notificacionTransporteVO.getNifEmpresa());
					if(EstadosNotificacionesTransporte.Por_Defecto.getValorEnum().equals(notificacionTransporteVO.getEstado())) {
						notificacionTransporteVO.setEstado(EstadosNotificacionesTransporte.Rechazada.getValorEnum());
						notificacionTransporteVO.setFechaModificacion(new Date());
						notificacionTransporteVO.setIdUsuario(idUsuario.longValue());
						notificacionTransporteDao.actualizar(notificacionTransporteVO);
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("La notificación no se encuentra en estado '" + EstadosNotificacionesTransporte.Por_Defecto.getNombreEnum() + "'");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Para el identificador: " + idNotificacionTransporte + " no existe notificación para poder rechazarla.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe identificador de la notificación para poder rechazarla.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de rechazar la notificación con id: " + idNotificacionTransporte + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de rechazar la notificacion.");
		}
		if(resultado.getError()){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public NotificacionTransporteVO getNotificacionTransporteVO(Long idNotificacionTransporte, Boolean notificacionCompleto) {
		try {
			return notificacionTransporteDao.getNotificacionPorId(idNotificacionTransporte,notificacionCompleto);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la notificacion de BBDD, error: ",e);
		}
		return null;
	}

	public UtilesViafirma getUtilesViafirma() {
		if(utilesViafirma == null){
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}

}