package org.gestoresmadrid.oegam2comun.transporte.model.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gestoresmadrid.oegam2comun.transporte.model.service.ServicioImpresionTransporte;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.NotificacionTransporteDto;
import org.gestoresmadrid.oegam2comun.transporte.view.dto.PoderTransporteDto;
import org.gestoresmadrid.oegamComun.contrato.view.dto.ContratoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import escrituras.beans.ResultBean;
import trafico.beans.utiles.CampoPdfBean;
import trafico.utiles.ConstantesPDF;
import trafico.utiles.PdfMaker;
import trafico.utiles.UtilResources;
import utilidades.informes.ReportExporter;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

@Component
public class ServicioImpresionTransporteImpl implements ServicioImpresionTransporte {

	private static final long serialVersionUID = -5519101003849793649L;

	private ILoggerOegam log = LoggerOegam.getLogger(ServicioImpresionTransporteImpl.class);

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultBean impresionPoder(PoderTransporteDto poderTransporte) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			List<CampoPdfBean> listaCampos = new ArrayList<>();
			PdfMaker pdfMarker = new PdfMaker();
			String ruta = gestorPropiedades.valorPropertie("poder.transporte.plantilla");
			UtilResources util = new UtilResources();
			byte[] bytePdf = pdfMarker.abrirPdf(util.getFilePath(ruta + "Plantilla_Poder_Transporte.pdf"));
			listaCampos.addAll(generarCamposPdf(poderTransporte));
			bytePdf = pdfMarker.setCampos(bytePdf, listaCampos);
			if (bytePdf != null) {
				resultado.addAttachment("ficheroPoderTransPdf", bytePdf);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora de imprimir el poder de transporte en pdf.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el poder de transporte en pdf, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el poder de transporte en pdf.");
		}
		return resultado;
	}

	private Collection<CampoPdfBean> generarCamposPdf(PoderTransporteDto poderTransporte) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(poderTransporte.getFechaAlta());
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<CampoPdfBean>();
		camposFormateados.add(new CampoPdfBean("NOMBRE_PODERDANTE", convertirNombrePoderdante(poderTransporte), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NIF_PODERDANTE", poderTransporte.getNifPoderdante(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NOMBRE_EMPRESA", poderTransporte.getNombreEmpresa(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NIF_EMPRESA", poderTransporte.getNifEmpresa(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("DOMICILIO_EMPRESA", generarDomicilio(poderTransporte), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("VIA_EMPRESA", poderTransporte.getDescTipoVia() +  " " + poderTransporte.getNombreVia(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NUMERO", poderTransporte.getNumeroVia(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("COD_POSTAL", poderTransporte.getCodPostal(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NOMBRE_COLEGIADO", poderTransporte.getContrato().getColegiadoDto().getUsuario().getApellidosNombre(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("NIF_COLEGIADO", poderTransporte.getContrato().getColegiadoDto().getUsuario().getNif(), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("DIA",  String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("MES", utilesFecha.convertirMesNumberToDesc(calendar.get(Calendar.MONTH) + 1), false, false, ConstantesPDF._11));
		camposFormateados.add(new CampoPdfBean("ANIO", String.valueOf(calendar.get(Calendar.YEAR)), false, false, ConstantesPDF._11));
		return camposFormateados;
	}

	private String generarDomicilio(PoderTransporteDto poderTransporte) {
		String domicilio = "";
		if(poderTransporte.getPueblo() != null && !poderTransporte.getPueblo().isEmpty()){
			domicilio = poderTransporte.getPueblo() + "(" + poderTransporte.getNombreProvincia() + ")";
		} else if(poderTransporte.getNombreMunicipio() != null && !poderTransporte.getNombreMunicipio().isEmpty()){
			domicilio = poderTransporte.getNombreMunicipio() + "(" + poderTransporte.getNombreProvincia() + ")";
		} else {
			domicilio = poderTransporte.getNombreProvincia();
		}
		return domicilio;
	}

	private String convertirNombrePoderdante(PoderTransporteDto poderTransporte) {
		String nombrePoderdante  = "";
		if (poderTransporte.getApellido2Poderdante() != null && !poderTransporte.getApellido2Poderdante().isEmpty()) {
			nombrePoderdante = poderTransporte.getApellido1Poderdante() + " " + poderTransporte.getApellido2Poderdante() + ", " + poderTransporte.getNombrePoderdante();
		} else {
			nombrePoderdante = poderTransporte.getApellido1Poderdante() + ", " + poderTransporte.getNombrePoderdante();
		}
		return nombrePoderdante;
	}

	@Override
	public ResultBean imprimirListadoNotificaciones(List<NotificacionTransporteDto> listaNotificaciones, ContratoDto contrato, Date fecha) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			ReportExporter re = new ReportExporter();
			UtilResources util = new UtilResources();
			String ruta = gestorPropiedades.valorPropertie("transporte.plantillas.ruta");
			String rutaMarcaAgua = util.getFilePath(gestorPropiedades.valorPropertie(ConstantesPDF.RUTA_PLANTILLAS) + ConstantesPDF.RUTA_MARCA_AGUA);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("NIF", contrato.getColegiadoDto().getUsuario().getNif());
			params.put("NOMBRE", contrato.getColegiadoDto().getUsuario().getApellidosNombre());
			params.put("NUM_COLEGIADO", contrato.getColegiadoDto().getNumColegiado());
			params.put("DIA", calendar.get(Calendar.DAY_OF_MONTH));
			params.put("MES", utilesFecha.convertirMesNumberToDesc(calendar.get(Calendar.MONTH) + 1));
			params.put("ANIO", calendar.get(Calendar.YEAR));
			params.put("IMG_DIR", ruta);
			params.put("RUTA_MARCA_AGUA", rutaMarcaAgua);
			params.put("SUBREPORT_DIR", ruta);
			byte[] byteFinal = re.generarInforme(ruta, "notificaciones", "pdf", null, "cabecera", params, crearListaAux(listaNotificaciones));
			if (byteFinal != null) {
				byte[] firma = new UtilesViafirma().firmarNotificacionesTransporte(byteFinal, contrato.getColegiadoDto().getAlias());
				if (firma != null) {
					resultado.addAttachment("ficheroNotPdf", firma);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora de firmar el pdf.");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir el pdf de notificaciones, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir el pdf de notificaciones.");
		}
		return resultado;
	}

	private Collection<NotificacionTransporteDto> crearListaAux(List<NotificacionTransporteDto> listaNotificaciones) {
		List<NotificacionTransporteDto> listaAux = new ArrayList<>();
		listaAux.add(new NotificacionTransporteDto());
		listaAux.addAll(listaNotificaciones);
		return listaAux;
	}

}