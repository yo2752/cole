package org.gestoresmadrid.oegam2comun.actualizacionMF.model.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.gestoresmadrid.core.actualizacionMF.model.dao.ActualizacionMFDao;
import org.gestoresmadrid.core.actualizacionMF.model.enumerados.ActualizacionMFEnum;
import org.gestoresmadrid.core.actualizacionMF.model.vo.ActualizacionMFVO;
import org.gestoresmadrid.oegam2comun.actualizacionMF.beans.ActualizacionMFRequest;
import org.gestoresmadrid.oegam2comun.actualizacionMF.beans.ActualizacionMFResponse;
import org.gestoresmadrid.oegam2comun.actualizacionMF.beans.ClienteRestActualizacionMF;
import org.gestoresmadrid.oegam2comun.actualizacionMF.model.service.ServicioActualizacionMF;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.google.common.io.Files;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioActualizacionMFImpl implements ServicioActualizacionMF {

	@Autowired
	ActualizacionMFDao actualizacionDAO;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	@Transactional
	public ResultBean encolarActualizacion(File ficheroSubido, BigDecimal idContrato,BigDecimal idUsuario ) {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		File fichero = null;
		Date fechaActual = new Date();
		DateFormat formateador = new SimpleDateFormat("ddMMyyyyHms");
		try {
			fichero = gestorDocumentos.guardarFichero(ConstantesGestorFicheros.TIPO_ACTUALIZACIONMF, ConstantesGestorFicheros.SUBTIPO_ACTUALIZACIONMF_SUBIDO, new Fecha(new Date()), formateador.format(fechaActual), ConstantesGestorFicheros.EXTENSION_XLSX, Files.toByteArray(ficheroSubido));
			if (fichero != null) {
				ActualizacionMFVO registro = new ActualizacionMFVO();
				registro.setFechaAlta(new Date());
				registro.setFicheroSubido(fichero.getName());
				registro.setEstado(ActualizacionMFEnum.Importado.getValorEnum());
				actualizacionDAO.guardar(registro);
			}
		} catch (OegamExcepcion | IOException e) {
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Se ha producido un error a la hora de guardar el fichero");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return salida;
	}

	@Override
	@Transactional
	public ResultBean realizarActualizacion(BigDecimal idActualizacion, BigDecimal idUsuario) {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		ClienteRestActualizacionMF cliente = new ClienteRestActualizacionMF(gestorPropiedades.valorPropertie("entorno"));
		ActualizacionMFResponse respuesta = null;
		ActualizacionMFVO registro = null;
		FileResultBean fichero = null;

		registro = actualizacionDAO.buscarActualizacion(idActualizacion.longValue());
		try {
			if (registro != null) {
				fichero = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.TIPO_ACTUALIZACIONMF, ConstantesGestorFicheros.SUBTIPO_ACTUALIZACIONMF_SUBIDO, new Fecha(registro.getFechaAlta()), registro.getFicheroSubido());

				if (fichero.getFiles() != null) {
					respuesta = cliente.procesarMF(new ActualizacionMFRequest(Files.toByteArray(fichero.getFiles().get(0)), idActualizacion.longValue()));
				}

				if (respuesta == null) {
					salida.setError(Boolean.TRUE);
					salida.setMensaje("eRROR");
				}else if (respuesta.isError()) {
					salida.setError(Boolean.TRUE);
					salida.setMensaje(respuesta.getMensajeError());
				}else{
					registro.setEstado(ActualizacionMFEnum.Activado.getValorEnum());
					actualizacionDAO.guardarOActualizar(registro);
					salida.setMensaje(respuesta.getMensajeError());
				}
			} else {
				salida.setError(Boolean.TRUE);
				salida.setMensaje("No se ha encontrado ningun fichero");
			}

		} catch (OegamExcepcion | IOException e) {
			salida.setError(Boolean.TRUE);
			salida.setMensaje("Se ha producido un error a la hora de guardar el fichero");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return salida;
	}

	@Override
	@Transactional
	public ResultBean descargarFicheros(String codigos) {
		ResultBean salida = new ResultBean(Boolean.FALSE);
		ArrayList<File> listaFicheros = new ArrayList<>();

		String[] todos = codigos.split("-");

		for (String codigo : todos) {
			listaFicheros.add(buscarFicherosSubidos(codigo));
			listaFicheros.add(buscarFicherosResultado(codigo));
		}
		salida.addAttachment("zip", empaquetarZIP(listaFicheros));
		return salida;
	}

	private File buscarFicherosSubidos(String codigo){
		FileResultBean fichero = null;
		ActualizacionMFVO registro = actualizacionDAO.buscarActualizacion(new Long(codigo));
		if (registro.getFicheroSubido() != null) {
			try {
				fichero = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.TIPO_ACTUALIZACIONMF, ConstantesGestorFicheros.SUBTIPO_ACTUALIZACIONMF_SUBIDO, new Fecha(registro.getFechaAlta()), registro.getFicheroSubido());
			} catch (OegamExcepcion e) {
				e.printStackTrace();
			}
			if (fichero != null && fichero.getFiles() != null) {
				return fichero.getFiles().get(0);
			}
		}
		return null;
	}

	private File buscarFicherosResultado(String codigo){
		FileResultBean fichero = null;
		ActualizacionMFVO registro = actualizacionDAO.buscarActualizacion(new Long(codigo));
		if (registro.getFicheroSubido() != null) {
			try {
				fichero = gestorDocumentos.buscarFicheroPorNumExpTipo(ConstantesGestorFicheros.TIPO_ACTUALIZACIONMF, ConstantesGestorFicheros.SUBTIPO_ACTUALIZACIONMF_SUBIDO, new Fecha(registro.getFechaFin()), registro.getFicheroResultado());
			} catch (OegamExcepcion e) {
				e.printStackTrace();
			}
			if (fichero != null && fichero.getFiles() != null) {
				return fichero.getFiles().get(0);
			}
		}
		return null;
	}

	private File empaquetarZIP(ArrayList<File> listaFicheros){
		File zipGenerado = null;
		FicheroBean ficherozip = new FicheroBean();
		ficherozip.setListaFicheros(listaFicheros);
		ficherozip.setNombreZip("ficherosActualizacion.zip");
		try {
			zipGenerado = gestorDocumentos.empaquetarEnZipFile(ficherozip);
		} catch (IOException | OegamExcepcion e) {
			e.printStackTrace();
		}
		return zipGenerado;
	}

}