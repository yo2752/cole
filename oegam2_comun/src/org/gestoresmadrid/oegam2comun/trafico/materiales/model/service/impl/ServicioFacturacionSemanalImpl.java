package org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.gestoresmadrid.core.docPermDistItv.model.dao.DocPermDistItvDao;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.TipoDistintivo;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.core.tipoPermDistItv.model.enumerado.TipoDocumentoImprimirEnum;
import org.gestoresmadrid.core.trafico.materiales.model.dao.IncidenciaDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.MaterialStockDao;
import org.gestoresmadrid.core.trafico.materiales.model.dao.StockMaterialDao;
import org.gestoresmadrid.core.trafico.materiales.model.enumerados.TipoDocumentoEnum;
import org.gestoresmadrid.core.trafico.materiales.model.vo.IncidenciaVO;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.materiales.model.service.ServicioStockMateriales;
import org.gestoresmadrid.oegam2comun.presentacion.jpt.enumerados.JefaturasJPTEnum;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioConsultaMateriales;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioFacturacionSemanal;
import org.gestoresmadrid.oegam2comun.trafico.materiales.model.service.ServicioGenerarExcelStockSemanal;
import org.gestoresmadrid.oegam2comun.trafico.materiales.view.dto.FacturacionSemanalBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import escrituras.beans.ResultBean;
import trafico.utiles.ConstantesPDF;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioFacturacionSemanalImpl implements ServicioFacturacionSemanal {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2774397583950911794L;
	
	private static final String RECIPIENT  = "resumen.stock.impre.correo.recipient";
	private static final String SUBJECT    = "resumen.stock.impre.correo.subject";
	
	private static final String TEXTO_BODY   = "Informe estadistico y de stock para DGT.";
	private static final String TEXTO_FECHAS = "Semana del %s al %s.";
	

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioFacturacionSemanalImpl.class);

	@Resource MaterialStockDao  materialStockDao;
	@Resource StockMaterialDao stockMaterialDao;
	@Resource DocPermDistItvDao docPermDistItvDao;
	@Resource IncidenciaDao     incidenciaDao;

	@Autowired ServicioConsultaMateriales       servicioConsultaMateriales;
	@Autowired ServicioCorreo                   servicioCorreo;
	@Autowired ServicioStockMateriales servicioStockMateriales;

	@Resource(name = "ServicioGenerarExcelStockSemanalImpl")
	ServicioGenerarExcelStockSemanal servicioGenerarExcelStockSemanalXls;
	
	@Resource(name = "ServicioGenerarExcelStockSemanalXlsxImpl")
	ServicioGenerarExcelStockSemanal servicioGenerarExcelStockSemanalXlsx;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	@Transactional(readOnly=true)
	public HashMap<String, HashMap<String, Long>> obtenerFacturacionSemanalStock(String jefaturaProvincial,	Long tipoDocumento) {
		HashMap<String, HashMap<String, Long>> lineasFacturacionSemanalStock = null;
		if(!"SI".equals(gestorPropiedades.valorPropertie("stock.material.nuevo"))) {
			lineasFacturacionSemanalStock = materialStockDao.obtenerFacturacionSemanalStock(jefaturaProvincial, tipoDocumento);	
		}else {
			lineasFacturacionSemanalStock = stockMaterialDao.obtenerFacturacionSemanalStock(jefaturaProvincial, tipoDocumento);
		}
		
		return lineasFacturacionSemanalStock;
	}

	@Override
	@Transactional(readOnly=true)
	public HashMap<String, HashMap<String, HashMap<String, Long>>> obenerFacturacionSemanalImpresion(
			String jefaturaProvincial, Long tipoDocumento, Date fecDesde, Date fecHasta) {
		
		HashMap<String, HashMap<String, HashMap<String, Long>>> impresiones = 
				new HashMap<String, HashMap<String, HashMap<String, Long>>>();
		
		List<DocPermDistItvVO> documentos = docPermDistItvDao.findDocByJefaturaDistintivoFechaImpr(jefaturaProvincial, tipoDocumento, fecDesde, fecHasta);
		
		for(DocPermDistItvVO item: documentos) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String jefatura = JefaturasJPTEnum.convertirJefatura(item.getJefatura());
			String fechaImp = df.format(item.getFechaImpresion()); 
			String tipo     = item.getTipo();
			
			List<String> noDSTV = 
					new ArrayList<String>(Arrays.asList(
							TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), 
							TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(),
							TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum())); 
			
			String tipoDistintivo = "";
			if ( noDSTV.contains(tipo) ) {
				tipoDistintivo = item.getTipoDistintivo();
			} else {
				TipoDistintivo distintivo = TipoDistintivo.convertir(item.getTipoDistintivo());
				tipoDistintivo = distintivo.getNombreEnum();
			}
			
			if (impresiones.containsKey(jefatura)) {
				HashMap<String, HashMap<String, Long>> valueFecha = impresiones.get(jefatura);
				if (valueFecha.containsKey(fechaImp)) {
					HashMap<String, Long> valueMaterial = valueFecha.get(fechaImp);
					if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipo)) {
						if (!valueMaterial.containsKey(tipoDistintivo)) {
							valueMaterial.put(tipoDistintivo, (long) item.getListaTramitesDistintivo().size());
						} else {
							long unidades = valueMaterial.get(tipoDistintivo) + (long) item.getListaTramitesDistintivo().size();
							valueMaterial.put(tipoDistintivo, unidades);
						}
					} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipo)) {
						if (!valueMaterial.containsKey(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum())) {
							valueMaterial.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
						} else {
							long unidades = valueMaterial.get(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum()) + item.getListaTramitesPermiso().size();
							valueMaterial.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), unidades);
						}
					} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipo)) {
						if (!valueMaterial.containsKey(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum())) {
							valueMaterial.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
						} else {
							long unidades = valueMaterial.get(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum()) + (long) item.getListaTramitesEitvAsList().size();
							valueMaterial.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), unidades);
						}
					} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(tipo)) {
						if (!valueMaterial.containsKey(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum())) {
							valueMaterial.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
						} else {
							long unidades = valueMaterial.get(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum()) + (long) item.getListaTramitesPermiso().size();
							valueMaterial.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), unidades);
						}
						
						if (!valueMaterial.containsKey(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum())) {
							valueMaterial.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
						} else {
							long unidades = valueMaterial.get(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum()) + (long)  item.getListaTramitesEitvAsList().size();
							valueMaterial.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), unidades);
						}
						
					}
				} else {
					HashMap<String, Long> material = new HashMap<String, Long>();
					if (TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum().equals(tipo)) {
						material.put(tipoDistintivo, (long) item.getListaTramitesDistintivo().size());
					} else if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(tipo)) {
						material.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
					} else if (TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum().equals(tipo)) {
						material.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
					} else if (TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum().equals(tipo)) {
						material.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
						material.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
					}
					
					valueFecha.put(fechaImp, material);
					
					impresiones.put(jefatura, valueFecha);
				}
				
			} else {
				HashMap<String, Long> material = new HashMap<String, Long>();
				if (tipo.equals(TipoDocumentoImprimirEnum.DISTINTIVO.getValorEnum())) {
					material.put(tipoDistintivo, (long) item.getListaTramitesDistintivo().size());
				} else if (tipo.equals(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum())) {
					material.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
				} else if (tipo.equals(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum())) {
					material.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
				} else if (tipo.equals(TipoDocumentoImprimirEnum.FICHA_PERMISO.getValorEnum())) {
					material.put(TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum(), (long) item.getListaTramitesPermiso().size());
					material.put(TipoDocumentoImprimirEnum.FICHA_TECNICA.getValorEnum(), (long) item.getListaTramitesEitvAsList().size());
				}
				
				
				HashMap<String, HashMap<String, Long>> fecha = new HashMap<String, HashMap<String, Long>>();
				fecha.put(fechaImp, material);
				
				impresiones.put(jefatura, fecha);
			}
			
		}
		
		// Añade las incidencias
		List<IncidenciaVO> incidencias = incidenciaDao.findIncidenciaByJefaturaAndEstadoAndFecha(jefaturaProvincial, fecDesde, fecHasta);

		impresiones = addIncidenciasToInforme(impresiones, incidencias);
		
		return impresiones;
	}

	private HashMap<String, HashMap<String, HashMap<String, Long>>> addIncidenciasToInforme(
			HashMap<String, HashMap<String, HashMap<String, Long>>> impresiones, List<IncidenciaVO> incidencias) {
		@SuppressWarnings("unchecked")
		HashMap<String, HashMap<String, HashMap<String, Long>>> impresionesWorkCopy =
				(HashMap<String, HashMap<String, HashMap<String, Long>>>) impresiones.clone();
		
		for (IncidenciaVO incidencia: incidencias) {
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			
			String jefatura   = incidencia.getJefaturaProvincial().getDescripcion();
			String fecha      = df.format(incidencia.getFecha()); 
			String material   = incidencia.getMaterialVO().getTipo();
			
			if (impresionesWorkCopy.containsKey(jefatura)) {
				
				HashMap<String, HashMap<String, Long>> valueFecha = impresionesWorkCopy.get(jefatura);
				if (valueFecha.containsKey(fecha)) {
					HashMap<String, Long> valueMaterial = valueFecha.get(fecha);
					
					if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material)) {
						if (valueMaterial.containsKey(TIPO_INCIDENCIA_PERMISO) ) {
							long numIncidencias = valueMaterial.get(TIPO_INCIDENCIA_PERMISO) + (long) incidencia.getListaSeriales().size();
							valueMaterial.put(TIPO_INCIDENCIA_PERMISO, numIncidencias);
						} else {
							valueMaterial.put(TIPO_INCIDENCIA_PERMISO, (long) incidencia.getListaSeriales().size());
						}
						
					} else {
						if (valueMaterial.containsKey(TIPO_INCIDENCIA_DISTINTIVO) ) {
							long numIncidencias = valueMaterial.get(TIPO_INCIDENCIA_DISTINTIVO) + (long) incidencia.getListaSeriales().size();
							valueMaterial.put(TIPO_INCIDENCIA_DISTINTIVO, numIncidencias);
						} else {
							valueMaterial.put(TIPO_INCIDENCIA_DISTINTIVO, (long) incidencia.getListaSeriales().size());
						}
					}
					
					valueFecha.put(fecha, valueMaterial);
					
				} else {
					HashMap<String, Long> lineaInci = new HashMap<String, Long>();
					
					if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material)) {
						lineaInci.put(TIPO_INCIDENCIA_PERMISO, (long) incidencia.getListaSeriales().size());
					} else {
						lineaInci.put(TIPO_INCIDENCIA_DISTINTIVO, (long) incidencia.getListaSeriales().size());
					}
					
					valueFecha.put(fecha, lineaInci);
				}
				
				impresionesWorkCopy.put(jefatura, valueFecha);
			} else {
				HashMap<String, Long> lineaInci = new HashMap<String, Long>();
				if (TipoDocumentoImprimirEnum.PERMISO_CIRCULACION.getValorEnum().equals(material)) {
					lineaInci.put(TIPO_INCIDENCIA_PERMISO, (long) incidencia.getListaSeriales().size());
				} else {
					lineaInci.put(TIPO_INCIDENCIA_DISTINTIVO, (long) incidencia.getListaSeriales().size());
				}
				
				HashMap<String, HashMap<String, Long>> lineaFecha = new HashMap<String, HashMap<String, Long>>();
				lineaFecha.put(fecha, lineaInci);
				
				impresionesWorkCopy.put(jefatura, lineaFecha);
			}

		}
		
		return impresionesWorkCopy;
	}

	@Override
	public Boolean[] establecerDocumentosAMostrar(Long tipoDocumento) {
		Boolean[] documentos = new Boolean[3]; // pos 0: distintivos, pos 1: permisos, pos 2: fichas técnicas
		
		for(int index = 0; index < documentos.length; index++) {
			documentos[index] = Boolean.FALSE;
		}
		
		if (tipoDocumento == null) {
			for(int index = 0; index < documentos.length; index++) {
				documentos[index] = Boolean.TRUE;
			}
		} else if (TipoDocumentoEnum.Distintivo.getValorEnum().equals(tipoDocumento)) {
			documentos[0] = Boolean.TRUE;
		} else if (TipoDocumentoEnum.PermisoConducir.getValorEnum().equals(tipoDocumento)) {
			documentos[1] = Boolean.TRUE;
		} else if (TipoDocumentoEnum.FichaTecnica.getValorEnum().equals(tipoDocumento)) {
			documentos[2] = Boolean.TRUE;
		}
		
		return documentos;
	}

	@Override
	@Transactional(readOnly=true)
	public ResultBean executeStockSemanal() {
		log.info("executeStockSemanal");
		
		ResultBean resultado = null;
		if(!"SI".equals(gestorPropiedades.valorPropertie("stock.material.nuevo"))) {
			if (servicioConsultaMateriales.obtenerTodosTiposMaterial().length == 0) {
				resultado = new ResultBean(Boolean.TRUE);
				log.error("No se han encontrado materiales.");
				resultado.setMensaje("No se han encontrado materiales.");
				return resultado;
			}
		}else {
			if(servicioStockMateriales.obtenerTodosTiposMaterial().length == 0) {
				resultado = new ResultBean(Boolean.TRUE);
				log.error("No se han encontrado materiales.");
				resultado.setMensaje("No se han encontrado materiales.");
				return resultado;
			}
		}
		
		
		String fileName = "RESUMEN_FACTURACION_SEMANAL_"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + ConstantesPDF.EXTENSION_XLS;

		Long tipoDocumento = null;
		String jefaturaProvincial = "";
		
		DateTime today = new DateTime();
		DateTime sameDayLastWeek = today.minusWeeks(1);
		DateTime mondayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.MONDAY);
		DateTime fridayLastWeek = sameDayLastWeek.withDayOfWeek(DateTimeConstants.FRIDAY);		

		Date fecInforme = today.toDate(); 
		Date fecDesde   = mondayLastWeek.toDate();
		Date fecHasta   = fridayLastWeek.toDate();
		
		FacturacionSemanalBean facturacionSemanalBean = new FacturacionSemanalBean();
		facturacionSemanalBean.setDocumentos(establecerDocumentosAMostrar(tipoDocumento));
		facturacionSemanalBean.setLineasFacturacionSemanalStock(obtenerFacturacionSemanalStock(jefaturaProvincial, tipoDocumento));
		if(!"SI".equals("stock.material.nuevo")) {
			facturacionSemanalBean.setTipos(servicioConsultaMateriales.obtenerTodosTiposMaterial());	
		}else {
			facturacionSemanalBean.setTipos(servicioStockMateriales.obtenerTodosTiposMaterial());
		}
		
		facturacionSemanalBean.setLineasFacturacionSemanalImpresion(obenerFacturacionSemanalImpresion(jefaturaProvincial, tipoDocumento, fecDesde, fecHasta));
		
		ByteArrayInputStream bis = null;
		if (ServicioGenerarExcelStockSemanal.EXCEL_TYPE.equals("XLSX")) {
			bis = servicioGenerarExcelStockSemanalXlsx.generarExcelWithData(facturacionSemanalBean, fileName, fecInforme); 
		} else {
			bis = servicioGenerarExcelStockSemanalXls.generarExcelWithData(facturacionSemanalBean, fileName, fecInforme); 
		}
		
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yyyy"); 
		
		String subject = gestorPropiedades.valorPropertie(SUBJECT);
		String recipient = gestorPropiedades.valorPropertie(RECIPIENT);
		String direccionesOcultas = "";

		String textoBody = TEXTO_BODY;
		String textoFechas = String.format(TEXTO_FECHAS, dt.format(fecDesde), dt.format(fecHasta));
		String lineasVacias = "</BR></BR>";
		
		String textoMensaje = textoBody + lineasVacias + textoFechas + lineasVacias + lineasVacias;
		
		FicheroBean[] ficheros = new FicheroBean[1];
		
		try {
			
			FicheroBean fichero = obtenerAttacheds(fileName, bis);
			ficheros[0] = fichero;
		
			resultado = servicioCorreo.enviarCorreo(textoMensaje, null, null, subject, recipient, null, direccionesOcultas, null, ficheros);
			if (resultado.getError()) {
				log.error("No se enviaron correos de estadisticas semanales.");
				for (String textoError: resultado.getListaMensajes()) {
					log.error(textoError);
				}
			}
		} catch (IOException | OegamExcepcion e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
		return resultado;
	}

	private FicheroBean obtenerAttacheds(String filename, ByteArrayInputStream bis) throws IOException {
		// TODO Auto-generated method stub
		FicheroBean fichero = new FicheroBean();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		byte[] content = new byte[2048];
		
		int bytesRead = -1;  
		try {
			while( ( bytesRead = bis.read( content ) ) != -1 ) {  
			    baos.write( content, 0, bytesRead );  
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} 		

		fichero.setFicheroByte(baos.toByteArray());
		fichero.setNombreDocumento(filename);
		
		return fichero;
	}

}
