package org.gestoresmadrid.oegam2.trafico.ivtm.controller.action;

import java.util.Date;

import javax.annotation.Resource;

import org.gestoresmadrid.core.pagination.model.component.ModelPagination;
import org.gestoresmadrid.oegam2.pagination.controller.action.AbstractPaginatedListAction;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmConsultaMatriculacionDto;
import org.gestoresmadrid.oegam2comun.trafico.ivtm.view.beans.ConsultaIvtmViewBean;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;

import escrituras.beans.ResultBean;
import trafico.utiles.constantes.ConstantesIVTM;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class ConsultaIVTMAction extends AbstractPaginatedListAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5143709428580916879L;

	@Autowired
	ServicioIvtmMatriculacion servicioIvtmMatriculacion;	
	
	ConsultaIvtmViewBean consultaIvtmViewBean;
		
	@Resource
	private ModelPagination modeloConsultaIvtmPaginatedImpl;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private IvtmConsultaMatriculacionDto  ivtmConsultaDto;
	
	private static final ILoggerOegam LOG = LoggerOegam.getLogger(ConsultaIVTMAction.class);

	public String consultar(){
		
		ResultBean resultado = null;		
		
		try{
			//Se inicializa el objeto ivtmConsulta
			if(null == ivtmConsultaDto){
				ivtmConsultaDto = new IvtmConsultaMatriculacionDto();
			}
			ivtmConsultaDto.setNumColegiado(utilesColegiado.getNumColegiadoSession());		
			ivtmConsultaDto.setFechaReq(new Date());
			ivtmConsultaDto.setIdPeticion((servicioIvtmMatriculacion.generarIdPeticion(utilesColegiado.getNumColegiadoSession())));
			
			resultado = servicioIvtmMatriculacion.encolarConsultar(ivtmConsultaDto, utilesColegiado.getIdUsuarioSessionBigDecimal());
				
			if (resultado.getError()){
				ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
				//Se guarda el trámite
				//modeloIvtmConsulta.guardarDatosConsulta(ivtmConsultaDto);
				
				addActionError(ConstantesIVTM.TEXTO_IVTM_ENCOLADO_ALTA_ERROR);
			}
			else{
				ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA);
				//Se guarda el trámite
				//servicioIvtmMatriculacion.guardarDatosConsulta(ivtmConsultaDto);
				addActionMessage(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA);	
			}
		}
		catch (Exception e) {
			ivtmConsultaDto.setMensaje(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR);
			addActionError(ConstantesIVTM.TEXTO_IVTM_SOLICITUD_CONSULTA_ERROR); 
		}
		finally{
			if (null != resultado && !resultado.getError()){
				//modeloIvtmConsulta.guardarDatosConsulta(ivtmConsultaDto);
			}
		}
		
		return 	SUCCESS;	
		
	}
			
	
	@Override
	protected String getResultadoPorDefecto() {
		// TODO Auto-generated method stub
		return SUCCESS;
	}

	@Override 
	protected ModelPagination getModelo() {
		// TODO Auto-generated method stub
		return modeloConsultaIvtmPaginatedImpl;
	}

	@Override
	protected ILoggerOegam getLog() {
		// TODO Auto-generated method stub
		return LOG;
	}

	@Override
	protected void cargaRestricciones() {
		if (consultaIvtmViewBean == null) {
			consultaIvtmViewBean = new ConsultaIvtmViewBean();
		}
		if (!utilesColegiado.tienePermisoAdmin() && !utilesColegiado.tienePermisoColegio()) {
			consultaIvtmViewBean.setNumColegiado(utilesColegiado.getNumColegiadoSession());
		}		
	}

	@Override
	protected void cargarFiltroInicial() {
		if (consultaIvtmViewBean == null) {
			consultaIvtmViewBean = new ConsultaIvtmViewBean();
		}		
		consultaIvtmViewBean.setFechaBusqueda(utilesFecha.getFechaFracionadaActual());			
	}

	@Override
	protected Object getBeanCriterios() {
		// TODO Auto-generated method stub
		return consultaIvtmViewBean;
	}

	@Override
	protected void setBeanCriterios(Object object) {
		this.consultaIvtmViewBean = (ConsultaIvtmViewBean) object;
		
	}
	
	public ConsultaIvtmViewBean getConsultaIvtmViewBean() {
		return consultaIvtmViewBean;
	}

	public void setConsultaIvtmViewBean(ConsultaIvtmViewBean consultaIvtmViewBean) {
		this.consultaIvtmViewBean = consultaIvtmViewBean;
	}

}
