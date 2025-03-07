package org.gestoresmadrid.oegam2comun.modelos.model.service.impl;

import java.util.Collections;
import java.util.List;

import org.gestoresmadrid.core.modelos.model.dao.TipoDocumentoEscrDao;
import org.gestoresmadrid.core.modelos.model.vo.ModeloVO;
import org.gestoresmadrid.core.modelos.model.vo.TipoDocumentoEscrVO;
import org.gestoresmadrid.oegam2comun.modelos.model.service.ServicioTipoDocumentoEscr;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.ModeloDto;
import org.gestoresmadrid.oegam2comun.modelos.view.dto.TipoDocumentoEscrDto;
import org.gestoresmadrid.oegamComun.conversor.Conversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioTipoDocumentoEscrImpl implements ServicioTipoDocumentoEscr{

	private static final long serialVersionUID = 5906868478423811782L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioTipoDocumentoEscrImpl.class);
	
	@Autowired
	private Conversor conversor;
	
	@Autowired
	private TipoDocumentoEscrDao tipoDocumentoEscrDao;
	
	@Override
	@Transactional
	public List<TipoDocumentoEscrDto> getListaDocumentosPorModelo(ModeloDto modelo) {
		try{
			if(modelo != null){
				List<TipoDocumentoEscrVO> lista = tipoDocumentoEscrDao.getListaPorModelo(conversor.transform(modelo, ModeloVO.class)); 
				if(lista != null && !lista.isEmpty()){
					return conversor.transform(lista, TipoDocumentoEscrDto.class);
				}
			}else{
				log.error("Error a la hora de obtener la lista de documentos de escrituras por modelo, el modelo es nulo");
			}
		}catch(Exception e){
			log.error("Error a la hora de obtener la lista de documentos de escrituras por modelo, error: ",e);
		}
		return Collections.emptyList();
	}

}
