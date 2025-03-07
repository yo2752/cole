package org.gestoresmadrid.oegamComun.impr.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv;
import org.gestoresmadrid.core.docPermDistItv.model.vo.DocPermDistItvVO;
import org.gestoresmadrid.oegamComun.impr.service.ServicioDocImpr;
import org.gestoresmadrid.oegamComun.impr.service.ServicioComunDocPrmDstvFicha;
import org.gestoresmadrid.oegamComun.impr.service.ServicioPersistenciaDocPrmDstvFicha;
import org.gestoresmadrid.oegamComun.impr.view.bean.ResultadoDocImprBean;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioComunDocPrmDstvFichaImpl implements ServicioComunDocPrmDstvFicha {

	private static final long serialVersionUID = -5407703331460647894L;

	private static final Logger log = LoggerFactory.getLogger(ServicioComunDocPrmDstvFichaImpl.class);
	
	@Autowired
	Utiles utiles;
	
	@Autowired
	ServicioPersistenciaDocPrmDstvFicha servicioPersistenciaDocPrmDstvFicha;
	

	@Override
	public ResultadoDocImprBean generarDocImpr(Long idContrato, Date fechaImpr, String tipoImpr, String jefatura, Long idUsuario, String tipoTramite, String refDocumento) {
		ResultadoDocImprBean resultado = new ResultadoDocImprBean(Boolean.FALSE);
		try {
			DocPermDistItvVO docImpr = rellenarDocImpr(idContrato, fechaImpr, jefatura, tipoImpr, tipoTramite, refDocumento, idUsuario);
			docImpr.setIdDocPermDistItv(servicioPersistenciaDocPrmDstvFicha.guardar(docImpr));
			docImpr.setDocIdPerm(generarDocId(docImpr.getIdDocPermDistItv(), fechaImpr));
			servicioPersistenciaDocPrmDstvFicha.guardarDocId(docImpr, idUsuario);
			resultado.setDocId(docImpr.getIdDocPermDistItv());
			resultado.setsDocId(docImpr.getDocIdPerm());
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de generar el docImpr para el contrato: " + idContrato + " del tipo: " + tipoImpr + ",error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de generar el docImpr para el contrato: " + idContrato + " del tipo: " + tipoImpr);
		}
		return resultado;
	}
	
	private String generarDocId(Long idDocPermDistItv, Date fechaAlta) {
		String docIdImpresion = new SimpleDateFormat("yyyyMMdd").format(fechaAlta).substring(0, 4) + "-";
		String secuencial = utiles.rellenarCeros(idDocPermDistItv.toString(), ServicioDocImpr.longitudSecuencialDocPermDstvEitv);
		docIdImpresion += secuencial;
		return docIdImpresion;
	}
	
	private DocPermDistItvVO rellenarDocImpr(Long idContrato, Date fechaImpr, String jefatura, String tipoImpr, String tipoTramite, String refDocumento, Long idUsuario) {
		DocPermDistItvVO docPermDistItvVO = new DocPermDistItvVO();
		docPermDistItvVO.setIdUsuario(idUsuario);
		docPermDistItvVO.setEstado(new BigDecimal(EstadoPermisoDistintivoItv.Iniciado.getValorEnum()));
		docPermDistItvVO.setFechaAlta(fechaImpr);
		docPermDistItvVO.setIdContrato(idContrato);
		docPermDistItvVO.setJefatura(jefatura);
		docPermDistItvVO.setTipo(tipoImpr);
		docPermDistItvVO.setEsDemanda("N");
		docPermDistItvVO.setRefDocumento(refDocumento);
		return docPermDistItvVO;
	}
}
