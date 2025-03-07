package org.gestoresmadrid.oegam2comun.transporte.model.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.core.transporte.model.dao.PoderTransporteDao;
import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.direccion.model.service.ServicioDireccion;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioImpresionTransporte;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioPoderesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioPoderesTransporteImpl implements ServicioPoderesTransporte {

	private static final long serialVersionUID = 7313001996980224431L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioPoderesTransporteImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioContrato servicioContrato;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	PoderTransporteDao poderTransporteDao;

	@Autowired
	ServicioDireccion servicioDireccion;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioImpresionTransporte servicioImpresionTransporte;

	@Override
	@Transactional(readOnly=true)
	public ResultadoTransporteBean getPoderTransportePantalla(PoderTransporteDto poderTransporte) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		try {
			if (poderTransporte != null && poderTransporte.getIdPoderTransporte() != null) {
				PoderTransporteDto poderTransporteDto = getPoderTransporteDto(poderTransporte.getIdPoderTransporte());
				if (poderTransporteDto != null) {
					resultado.setPoderTransporte(poderTransporteDto);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existen datos del poder para obtener su detalle.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del poder para obtener su detalle.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el poder del transporte, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de obtener el poder del transporte.");
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly=true)
	public PoderTransporteDto getPoderTransporteDto(Long idPoderTransporte) {
		try {
			if(idPoderTransporte != null){
				PoderTransporteVO poderTransporteVO = getPoderTransporteVO(idPoderTransporte);
				if(poderTransporteVO != null){
					return conversor.transform(poderTransporteVO, PoderTransporteDto.class);
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el poder del transporte, error: ",e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public PoderTransporteVO getPoderTransporteVO(Long idPoderTransporte) {
		try {
			if (idPoderTransporte != null) {
				return poderTransporteDao.getPoderPorId(idPoderTransporte);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el poder del transporte, error: ",e);
		}
		return null;
	}

	@Override
	@Transactional
	public ResultadoTransporteBean generarPoderTransporte(PoderTransporteDto poderTransporte, BigDecimal idContrato, BigDecimal idUsuario) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		String nombreFichero = null;
		Fecha fecha = null;
		try {
			ResultBean resultValidar = validarPoder(poderTransporte);
			if(!resultValidar.getError()){
				fecha = utilesFecha.getFechaActual();
				rellenarDatosDto(poderTransporte ,idContrato,idUsuario,fecha);
				PoderTransporteVO poderTransporteVO = conversor.transform(poderTransporte, PoderTransporteVO.class);
				poderTransporteDao.guardar(poderTransporteVO);
				poderTransporte.setIdPoderTransporte(poderTransporteVO.getIdPoderTransporte());
				ResultBean resultImpresion = generarPdfPoderTransporte(poderTransporte);
				if (!resultImpresion.getError()) {
					nombreFichero = ConstantesGestorFicheros.NOMBRE_PODERES_TRANSPORTE + "_" + poderTransporte.getIdPoderTransporte(); 
					File fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.PODERES,
							fecha, nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF,(byte[]) resultImpresion.getAttachment("ficheroPoderTransPdf"));
					if (fichero == null) {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("Ha sucedido un error a la hora de custodiar el pdf generado, por favor vuelva a intentarlo de nuevo.");
					} else {
						resultado.setPoderTransporte(poderTransporte);
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje(resultImpresion.getMensaje());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultValidar.getMensaje());
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de generar el poder de representación de transportes, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el poder.");
		}
		if (resultado.getError()) {
			if (nombreFichero != null && !nombreFichero.isEmpty()) {
				try {
					gestorDocumentos.borraFicheroSiExiste(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.PODERES,
							fecha, nombreFichero);
				} catch (OegamExcepcion e) {
					log.error("Ha sucedido un error a la hora de borrar el pdf de poder de representación de transportes, error: ",e);
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return resultado;
	}

	public void rellenarDatosDto(PoderTransporteDto poderTransporte, BigDecimal idContrato, BigDecimal idUsuario, Fecha fecha) throws ParseException {
		poderTransporte.setIdUsuarioAlta(idUsuario.longValue());
		poderTransporte.setFechaAlta(fecha.getDate());
		poderTransporte.setContrato(servicioContrato.getContratoDto(idContrato));
		poderTransporte.setNombreProvincia(servicioDireccion.obtenerNombreProvincia(poderTransporte.getIdProvincia()));
		if(poderTransporte.getIdMunicipio() != null && !poderTransporte.getIdMunicipio().isEmpty()){
			poderTransporte.setNombreMunicipio(servicioDireccion.obtenerNombreMunicipio(poderTransporte.getIdMunicipio(), poderTransporte.getIdProvincia()));
		}
		poderTransporte.setDescTipoVia(servicioDireccion.obtenerNombreTipoVia(poderTransporte.getIdTipoVia()));
	}

	@Override
	public ResultadoTransporteBean descargarPdf(PoderTransporteDto poderTransporte) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		try {
			if(poderTransporte != null && poderTransporte.getIdPoderTransporte() != null && poderTransporte.getFechaAlta() != null){
				String nombreFichero = ConstantesGestorFicheros.NOMBRE_PODERES_TRANSPORTE + "_" + poderTransporte.getIdPoderTransporte(); 
				FileResultBean fileResultBean = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TRANSPORTE, ConstantesGestorFicheros.PODERES,
							new Fecha(poderTransporte.getFechaAlta()), nombreFichero, ConstantesGestorFicheros.EXTENSION_PDF);
				if(fileResultBean.getFile() != null){
					resultado.setFichero(fileResultBean.getFile());
					resultado.setNombreFichero("Poder_Transporte.pdf");
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe pdf de representación.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Faltan datos obligatorios para poder descaragr el pdf.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de descargar el pdf, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de descargar el pdf.");
		}
		return resultado;
	}

	private ResultBean generarPdfPoderTransporte(PoderTransporteDto poderTransporte) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		try {
			resultBean = servicioImpresionTransporte.impresionPoder(poderTransporte);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el pdf, error: ", e);
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Ha sucedido un error a la hora de generar el pdf.");
		}
		return resultBean;
	}

	private ResultBean validarPoder(PoderTransporteDto poderTransporte) {
		ResultBean resultBean = new ResultBean(Boolean.FALSE);
		if (poderTransporte == null) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de rellenar los datos del poder para poder generarlo.");
		} else if (poderTransporte.getNifPoderdante() == null || poderTransporte.getNifPoderdante().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el nif del poderdante.");
		} else if (poderTransporte.getNombrePoderdante() == null || poderTransporte.getNombrePoderdante().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el nombre del poderdante.");
		} else if (poderTransporte.getApellido1Poderdante() == null || poderTransporte.getApellido1Poderdante().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el primer apellido del poderdante.");
		} else if (poderTransporte.getNifEmpresa() == null || poderTransporte.getNifEmpresa().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el NIF de la empresa.");
		} else if (poderTransporte.getNombreEmpresa() == null || poderTransporte.getNombreEmpresa().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el nombre de la empresa.");
		} else if (poderTransporte.getIdProvincia() == null || poderTransporte.getIdProvincia().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar la provincia de la empresa.");
		} else if (poderTransporte.getIdMunicipio() == null || poderTransporte.getIdMunicipio().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el municipio de la empresa.");
		} else if (poderTransporte.getNombreVia() == null || poderTransporte.getNombreVia().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el nombre de la via de la empresa.");
		} else if (poderTransporte.getIdTipoVia() == null || poderTransporte.getIdTipoVia().isEmpty()) {
			resultBean.setError(Boolean.TRUE);
			resultBean.setMensaje("Debe de indicar el tipo de via de la empresa.");
		}
		return resultBean;
	}

}