package org.gestoresmadrid.oegam2comun.transporte.model.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.gestoresmadrid.core.transporte.model.vo.PoderTransporteVO;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioConsultaPoderesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioPoderesTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.PoderesTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.beans.ResultadoTransporteBean;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioConsultaPoderesTransporteImpl implements ServicioConsultaPoderesTransporte {

	private static final long serialVersionUID = -1663907810431566718L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioConsultaPoderesTransporteImpl.class);

	@Autowired
	Conversor conversor;

	@Autowired
	ServicioPoderesTransporte servicioPoderesTransporte;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Override
	public List<PoderesTransporteBean> convertirListaEnBeanPantalla(List<PoderTransporteVO> lista) {
		if (lista != null && !lista.isEmpty()) {
			List<PoderesTransporteBean> listaBean = new ArrayList<>();
			for (PoderTransporteVO poderTransporteVO : lista) {
				PoderesTransporteBean poderesTransporteBean = conversor.transform(poderTransporteVO, PoderesTransporteBean.class);
				String desContrato = poderTransporteVO.getContrato().getProvincia().getNombre() + " - " + poderTransporteVO.getContrato().getVia();
				poderesTransporteBean.setContrato(desContrato);
				poderesTransporteBean.setPoderdante(poderTransporteVO.getNombrePoderdante() + " " + poderTransporteVO.getApellido1Poderdante() + " " + poderTransporteVO.getApellido1Poderdante());
				listaBean.add(poderesTransporteBean);
			}
			return listaBean;
		}
		return Collections.emptyList();
	}

	@Override
	public ResultadoTransporteBean descargarPoderesBloque(String idsPoderesTransporte) {
		ResultadoTransporteBean resultado = new ResultadoTransporteBean(Boolean.FALSE);
		FileOutputStream out = null;
		ZipOutputStream zip = null;
		String url = null;
		try {
			if (idsPoderesTransporte != null && !idsPoderesTransporte.isEmpty()) {
				String[] idsPoderes = idsPoderesTransporte.split("_");
				Boolean sonVariosPoderes = false;
				url = gestorPropiedades.valorPropertie("RUTA_ARCHIVOS_TEMP") + System.currentTimeMillis() + ".zip";
				if(idsPoderes.length > 1){
					sonVariosPoderes = Boolean.TRUE;
					File ficheroDestino = new File(url);
					out = new FileOutputStream(ficheroDestino);
					zip = new ZipOutputStream(out);
				}
				for (String idPoderTransporte : idsPoderes) {
					PoderTransporteDto poderTransporte = servicioPoderesTransporte.getPoderTransporteDto(Long.parseLong(idPoderTransporte));
					ResultadoTransporteBean resultImprimir = servicioPoderesTransporte.descargarPdf(poderTransporte);
					if (resultImprimir.getError()) {
						resultado.addError();
						resultado.addListaError(resultImprimir.getMensaje());
						resultado.setError(Boolean.TRUE);
						break;
					} else {
						if (sonVariosPoderes) {
							addZipEntryFromFile(zip,resultImprimir.getFichero());
						} else {
							resultado.setFichero(resultImprimir.getFichero());
							resultado.setNombreFichero(resultImprimir.getNombreFichero());
						}
					}
				}
				if (sonVariosPoderes) {
					zip.close();
					File fichero = new File(url);
					resultado.setNombreFichero(NOMBRE_ZIP + ConstantesGestorFicheros.EXTENSION_ZIP);
					resultado.setFichero(fichero);
					resultado.setEsZip(Boolean.TRUE);
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Debe seleccionar algún poder.");
			}
		} catch (FileNotFoundException e) {
			log.error("Ha sucedido un error a la hora de imprimir los poderes, error: ");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los poderes seleccionados.");
		} catch (IOException e) {
			log.error("Ha sucedido un error a la hora de imprimir los poderes, error: ");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir los poderes seleccionados.");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return resultado;
	}

	@Override
	public void borrarZip(File ficheroZip) {
		try {
			if(ficheroZip != null){
				ficheroZip.delete();
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar el zip temporal, error: ",e);
		}
	}

	public void addZipEntryFromFile(ZipOutputStream zip, File file) {
		if (file != null && file.exists()) {
			FileInputStream is = null;
			try {
				is = new FileInputStream(file);
				ZipEntry zipEntry = new ZipEntry(file.getName());
				zip.putNextEntry(zipEntry);
				byte[] buffer = new byte[2048];
				int byteCount;
				while (-1 != (byteCount = is.read(buffer))) {
					zip.write(buffer, 0, byteCount);
				}
				zip.closeEntry();
				if (file.lastModified() > 0) {
					zipEntry.setTime(file.lastModified());
				}
			} catch (IOException e) {
				log.error("Error al añadir una entrada al zip del fichero: " + file.getName(), e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						log.error("Error cerrando FileInputStream", e);
					}
				}
			}
		}
	}
}