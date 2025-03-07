package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import org.gestoresmadrid.core.modelos.model.dao.TipoImpuestoDao;
import org.gestoresmadrid.core.modelos.model.vo.TipoImpuestoVO;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoImpuesto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ConceptoDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoImpuestoDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoImpuestoImpl implements ServicioTipoImpuesto{

	private static final long serialVersionUID = 1731628420409457088L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoImpuestoImpl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private TipoImpuestoDao tipoImpuestoDao;
	
	@Override
	@Transactional(readOnly=true)
	public ResultBean buscarTipoImpuestoPorConceptoYModelo(ConceptoDto concepto, ModeloDto modelo) {
		ResultBean resultado = null;
		try {
			TipoImpuestoVO tipoImpuestoVO = tipoImpuestoDao.getTipoimpuestoPorConceptoYModelo(concepto.getConcepto(),modelo.getModelo(),modelo.getVersion());
			if(tipoImpuestoVO != null){
				resultado = new ResultBean(false);
				resultado.addAttachment("tipoImpuesto", conversor.transform(tipoImpuestoVO, TipoImpuestoDto.class));
			}else{
				resultado = new ResultBean(true, "No se ha encontrado ningún tipo de impuesto valido para el concepto y modelo seleccionado.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener el tipo de impuesto por el concepto y el modelo, error: ",e);
			resultado = new ResultBean(true, "Ha sucedido un error a la hora de obtener el tipo de impuesto por el concepto y el modelo");
		}
		return resultado;
	}
	
	@Override
	@Transactional(readOnly=true)
	public TipoImpuestoDto buscarTipoImpuestoPorConcepto(String concepto) {
		TipoImpuestoVO tipoImpuestoVO = tipoImpuestoDao.getTipoimpuestoPorConcepto(concepto);
		if(tipoImpuestoVO != null){
			return conversor.transform(tipoImpuestoVO, TipoImpuestoDto.class);
		}
		return null;
	}
}
