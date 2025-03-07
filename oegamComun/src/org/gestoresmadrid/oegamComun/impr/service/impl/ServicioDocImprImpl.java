package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.distintivo.model.vo.DistintivoVO;
import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.enumerados.TipoImpr;
import org.gestoresmadrid.core.impr.model.vo.DocImprVO;
import org.gestoresmadrid.core.impr.model.vo.ImprVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaDocImpr;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioDocImprImpl implements ServicioDocImpr{

	private static final long serialVersionUID = 226111667286633159L;

	private static final Logger log = LoggerFactory.getLogger(ServicioDocImprImpl.class);

	@Autowired
	ServicioPersistenciaDocImpr servicioPersistenciaDocImpr;

	@Autowired
	Utiles utiles;

	@Override
	public void borrarDocImpr(Long docId) {
		try {
			servicioPersistenciaDocImpr.borrarDoc(docId);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de borrar el docId: " + docId + ",error: ",e);
		}
	}

	@Override
	public ResultadoDocImprBean generarDocImpr(Long idContrato, Date fechaAlta, String tipoImpr, String jefatura,
			Long idUsuario, String tipoTramite, String referenciaDocumento, String carpeta) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImpr = rellenarDocImpr(idContrato, fechaAlta, jefatura,tipoImpr, tipoTramite, referenciaDocumento, carpeta);
			docImpr.setId(servicioPersistenciaDocImpr.guardar(docImpr));
			docImpr.setDocImpr(generarDocId(fechaAlta, docImpr.getId()));
			servicioPersistenciaDocImpr.guardarDocId(docImpr, idUsuario);
			resultado.setDocId(docImpr.getId());
			resultado.setsDocId(docImpr.getDocImpr());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el docImpr para el contrato: " + idContrato + " del tipo: " + tipoImpr + ",error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el docImpr para el contrato: " + idContrato + " del tipo: " + tipoImpr);
		}
		return resultado;
	}

	private DocImprVO rellenarDocImpr(Long idContrato, Date fechaAlta, String jefatura, String tipoImpr, String tipoTramite, String refDocumento, String carpeta) {
		DocImprVO docImprVO = new DocImprVO();
		docImprVO.setEstado(EstadoPermisoDistintivoItv.Iniciado.getValorEnum());
		docImprVO.setFechaAlta(fechaAlta);
		docImprVO.setFechaDocumento(fechaAlta);
		docImprVO.setIdContrato(idContrato);
		docImprVO.setJefatura(jefatura);
		docImprVO.setTipo(tipoImpr);
		docImprVO.setRefDocumento(refDocumento);
		if(StringUtils.isNotBlank(tipoTramite)){
			docImprVO.setTipoTramite(tipoTramite);
		}
		docImprVO.setDemanda("N");
		docImprVO.setCarpeta(carpeta);
		return docImprVO;
	}
	private String generarDocId(Date fechaAlta, Long id) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd  HH:mm:ss");
		String docIdImpresion = formatter.format(fechaAlta).substring(0, 4) + "-";
		String secuencial = utiles.rellenarCeros(id.toString(), longitudSecuencialDocPermDstvEitv);
		docIdImpresion += secuencial;
		return docIdImpresion;
	}

	@Override
	public ResultadoDocImprBean impresionManual(Long id, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = servicioPersistenciaDocImpr.getDocImprPorId(id, Boolean.TRUE);
			validarDocImprImpresion(docImprVO, jefatura, resultado);
			if(!resultado.getError()) {
				resultado = servicioPersistenciaDocImpr.impresionManual(id, obtenerListaIds(docImprVO), idUsuario, jefatura);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir manualmente el documento con Id: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir manualmente el documento.");
		}
		return resultado;
	}

	private List<Long> obtenerListaIds(DocImprVO docImprVO) {
		List<Long> listaIds = new ArrayList<Long>();
		if(TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo()) 
			&& docImprVO.getListaDistintivos() != null && !docImprVO.getListaDistintivos().isEmpty()) {
			for (DistintivoVO distintivoBBDD : docImprVO.getListaDistintivos()) {
				listaIds.add(distintivoBBDD.getId());
			}
		} else if(docImprVO.getListaImpr() != null && !docImprVO.getListaImpr().isEmpty()){
			for (ImprVO imprBBDD : docImprVO.getListaImpr()) {
				listaIds.add(imprBBDD.getId());
			}
		}
		return listaIds;
	}

	private void validarDocImprImpresion(DocImprVO docImprVO, String jefatura, ResultadoDocImprBean resultado) {
		if (docImprVO == null) {
			resultado.setMensaje("No existen datos para el docId.");
			resultado.setError(Boolean.TRUE);
		} else if (!EstadoPermisoDistintivoItv.GENERADO_JEFATURA.getValorEnum().equals(docImprVO.getEstado())) {
			resultado.setMensaje("El documento IMPR que desea imprimir no se encuentra en estado Generado Jefatura." );
			resultado.setError(Boolean.TRUE);
		} else if (TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())
				&& (docImprVO.getListaDistintivos() == null || docImprVO.getListaDistintivos().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea imprimir no tiene distintivos asociados para su impresion." );
			resultado.setError(Boolean.TRUE);
		} else if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())
				&& (docImprVO.getListaImpr() == null || docImprVO.getListaImpr().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea imprimir no tiene permisos asociados para su impresion." );
			resultado.setError(Boolean.TRUE);
		} else if (TipoImpr.Ficha_Tecnica.getValorEnum().equals(docImprVO.getTipo())
				&& (docImprVO.getListaImpr() == null || docImprVO.getListaImpr().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea imprimir no tiene fichas tecnicas asociadas para su impresion." );
			resultado.setError(Boolean.TRUE);
		} else {
//			Long stockDisponible = 0L;
			if (TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo())) {
				//stockDisponible = servicioConsultaStock.cantidadStockPorTipo(jefatura,docImprVO.getTipoDstv());
				//comprobarStock(stockDisponible, new Long(docImprVO.getListaDistintivos().size()), resultado);
			} else if (TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo())) {
				//stockDisponible = servicioConsultaStock.cantidadStockPorTipo(jefatura,docImprVO.getTipoDstv());
				//comprobarStock(stockDisponible, new Long(docImprVO.getListaImpr().size()), resultado);
			}

		}

	}

	private void comprobarStock(Long stockDisponible, Long tamañoListaImpresion, ResultadoDocImprBean resultado) {
		if (stockDisponible > 0) {
			if (stockDisponible.compareTo(tamañoListaImpresion) < 0) {
				resultado.setMensaje("No hay stock suficiente para imprimir el documento." );
				resultado.setError(Boolean.TRUE);
			}
		} else {
			resultado.setMensaje("No hay stock suficiente para imprimir el documento." );
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	public DocImprVO getDocImprPorId(Long id, Boolean completo) {
		return servicioPersistenciaDocImpr.getDocImprPorId(id, completo);
	}

	@Override
	public ResultadoDocImprBean cambiarEstado(Long id, String estadoNuevo, Long idUsuario) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = servicioPersistenciaDocImpr.getDocImprPorId(id, Boolean.TRUE);
			if (docImprVO != null) {
				return servicioPersistenciaDocImpr.cambiarEstado(id, estadoNuevo, idUsuario, obtenerListaIds(docImprVO));
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existen datos del documento para cambiar su estado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de cambiar el estado del docId: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de cambiar el estado.");
		}
		return resultado;
	}

	@Override
	public ResultadoDocImprBean reactivar(Long id, Long idUsuario, Boolean esEntornoAm) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = servicioPersistenciaDocImpr.getDocImprPorId(id, Boolean.TRUE);
			validarDocReactivar(docImprVO, resultado);
			if(!resultado.getError()) {
				return servicioPersistenciaDocImpr.reactivar(id, idUsuario, esEntornoAm);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de reactivar el documento con id: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de reactivar el documento.");
		}
		return resultado;
	}

	private void validarDocReactivar(DocImprVO docImprVO, ResultadoDocImprBean resultado) {
		if(docImprVO == null) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("No existen datos del documento para reactivar.");
		} else if(!EstadoPermisoDistintivoItv.Finalizado_Error.getValorEnum().equals(docImprVO.getEstado())) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El Documento no se encuentra en Estado Finalizado con Error.");
		} else if(TipoImpr.Distintivo.getValorEnum().equals(docImprVO.getTipo()) 
				&& (docImprVO.getListaDistintivos() == null || docImprVO.getListaDistintivos().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea reactivar no tiene distintivos asociados." );
			resultado.setError(Boolean.TRUE);
		} else if(TipoImpr.Permiso_Circulacion.getValorEnum().equals(docImprVO.getTipo()) 
				&& (docImprVO.getListaImpr() == null || docImprVO.getListaImpr().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea reactivar no tiene permisos asociados." );
			resultado.setError(Boolean.TRUE);
		} else if(TipoImpr.Ficha_Tecnica.getValorEnum().equals(docImprVO.getTipo()) 
				&& (docImprVO.getListaImpr() == null || docImprVO.getListaImpr().isEmpty())) {
			resultado.setMensaje("El documento IMPR que desea reactivar no tiene fichas tecnicas asociadas." );
			resultado.setError(Boolean.TRUE);
		}
	}

	@Override
	public ResultadoDocImprBean imprimir(Long id, Long idUsuario, String jefatura) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocImprVO docImprVO = servicioPersistenciaDocImpr.getDocImprPorId(id, Boolean.TRUE);
			validarDocImprImpresion(docImprVO, jefatura, resultado);
			if(!resultado.getError()) {
				resultado = servicioPersistenciaDocImpr.imprimir(id, obtenerListaIds(docImprVO), idUsuario, jefatura);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de imprimir manualmente el documento con docId: " + id + ", error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de imprimir manualmente el documento.");
		}
		return resultado;
	}
}