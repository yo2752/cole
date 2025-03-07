package trafico.modelo.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.gestoresmadrid.oegam2comun.trafico.dto.ivtm.IvtmMatriculacionDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import hibernate.dao.trafico.TramiteTrafMatrDAO;
import hibernate.entities.trafico.TramiteTrafMatr;
import hibernate.entities.trafico.TramiteTrafico;
import trafico.dto.matriculacion.TramiteTrafMatrDto;
import trafico.modelo.interfaz.ModeloMatrInterface;
//TODO MPC. Cambio IVTM
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.enumerados.TipoTramiteMatriculacion;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloMatrImpl implements ModeloMatrInterface {

	//TODO MPC. Cambio IVTM
	private IVTMModeloMatriculacionInterface modeloIvtm = new IVTMModeloMatriculacionImpl();
	private TramiteTrafMatrDAO tramiteMatrDao;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ModeloMatrImpl.class);

	@Autowired
	UtilesFecha utilesFecha;

	public ModeloMatrImpl(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		tramiteMatrDao = new TramiteTrafMatrDAO();
	}

	@Override
	public TramiteTrafMatrDto actualizar(TramiteTrafMatrDto tramiteDto) throws OegamExcepcion {
		TramiteTrafMatr tramite;

		try {
			tramite = convertDtoToTraTrafMat(tramiteDto);
		} catch (ParseException e) {
			log.error("Error al actualizar el tramite numExpediente ="+tramiteDto.getNumExpediente());
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al parsear de fecha a date");
		}
		try {
			tramite = tramiteMatrDao.actualizar(tramite);
		} catch (Exception e) {
			log.error("Error al actualizar el tramite numExpediente ="+tramiteDto.getNumExpediente());
			log.error(e.getMessage());
			throw new OegamExcepcion("Error: al actualizar el tramite");
		}
		TramiteTrafMatrDto resultado = convertTraTrafMatToDto(tramite);

		return resultado;
	}

	@Override
	public TramiteTrafMatrDto recuperarDtoMatrPorNumExpediente(BigDecimal numExpediente) {
		TramiteTrafMatrDto tramiteDto= new TramiteTrafMatrDto();
		TramiteTrafico tramite = tramiteMatrDao.getTramiteTrafMatr(numExpediente.longValue());

		if (tramite != null) {
			tramiteDto = convertTraTrafMatToDto(tramite.getTramiteTrafMatr());
		}
		return tramiteDto;
	}

	@Override
	public TramiteTrafMatrDto convertTraTrafMatToDto(TramiteTrafMatr tramite) {
		TramiteTrafMatrDto tramiteMatr = new TramiteTrafMatrDto();

		if (tramite.getADeducir576() != null)
			tramiteMatr.setaDeducir576(tramite.getADeducir576());
		if (tramite.getBaseImponible576() != null)
			tramiteMatr.setBaseImponible576(tramite.getBaseImponible576());
		if (tramite.getBaseImpoReducida576() != null)
			tramiteMatr.setBaseImpoReducida576(tramite.getBaseImpoReducida576());
		if (tramite.getCausaHechoImpon576() != null && !tramite.getCausaHechoImpon576().equals(""))
			tramiteMatr.setCausaHechoImpon576(tramite.getCausaHechoImpon576());
		if (tramite.getCuota576() != null)
			tramiteMatr.setCuota576(tramite.getCuota576());
		if (tramite.getCuotaIngresar576() != null)
			tramiteMatr.setCuotaIngresar576(tramite.getCuotaIngresar576());
		if (tramite.getDeduccionLineal576() != null)
			tramiteMatr.setDeduccionLineal576(tramite.getDeduccionLineal576());
		if (tramite.getEjercicio576() != null)
			tramiteMatr.setEjercicio576(tramite.getEjercicio576());
		if (tramite.getEntregaFactMatriculacion() != null && !tramite.getEntregaFactMatriculacion().equals(""))
			tramiteMatr.setEntregaFactMatriculacion(tramite.getEntregaFactMatriculacion());
		if (tramite.getExento576() != null && !tramite.getExento576().equals(""))
			tramiteMatr.setExento576(tramite.getExento576());
		if (tramite.getFechaPago576() != null)
			tramiteMatr.setFechaPago576(utilesFecha.getFechaConDate(tramite.getFechaPago576()));
		if (tramite.getIdNoSujeccion06() != null && !tramite.getIdNoSujeccion06().equals(""))
			tramiteMatr.setIdNoSujeccion06(tramite.getIdNoSujeccion06());
		if (tramite.getIdReduccion05() != null && !tramite.getIdReduccion05().equals(""))
			tramiteMatr.setIdReduccion05(tramite.getIdReduccion05());
		if (tramite.getImporte576() != null)
			tramiteMatr.setImporte576(tramite.getImporte576());
		if (tramite.getLiquidacion576() != null)
			tramiteMatr.setLiquidacion576(tramite.getLiquidacion576());
		if (tramite.getNDeclaracionComp576() != null && !tramite.getNDeclaracionComp576().equals(""))
			tramiteMatr.setnDeclaracionComp576(tramite.getNDeclaracionComp576());
		if (tramite.getNrc576() != null && !tramite.getNrc576().equals(""))
			tramiteMatr.setNrc576(tramite.getNrc576());
		if (tramite.getNumExpediente() != 0)
			tramiteMatr.setNumExpediente(tramite.getNumExpediente());
		if (tramite.getObservaciones576()!=null && !tramite.getObservaciones576().equals(""))
			tramiteMatr.setObservaciones576(tramite.getObservaciones576());
		if (tramite.getReduccion576()!=null && !tramite.getReduccion576().equals(""))
			tramiteMatr.setReduccion576(tramite.getReduccion576());
		if (tramite.getTipoGravamen576()!=null)
			tramiteMatr.setTipoGravamen576(tramite.getTipoGravamen576());
		if (tramite.getTramiteTrafico()!=null)
			tramiteMatr.setTramiteTrafico(tramite.getTramiteTrafico());
		if (tramite.getTipoTramiteMatr()!=null && !tramite.getTipoTramiteMatr().equals("")){
			tramiteMatr.setTipoTramiteMatriculacion(TipoTramiteMatriculacion.convertir(tramite.getTipoTramiteMatr()));
		}
		if (tramite.getJustificadoIvtm()!=null && !tramite.getJustificadoIvtm().isEmpty()){
			tramiteMatr.setJustificadoIvtm(tramite.getJustificadoIvtm());
		}
		//TODO MPC. Cambio IVTM.
		IvtmMatriculacionDto ivtmDto = modeloIvtm.buscarIvtmNumExp(new BigDecimal(tramite.getNumExpediente()));
		if (ivtmDto != null)
			tramiteMatr.setIvtmMatriculacionDto(ivtmDto);
		return tramiteMatr;
	}

	@Override
	public TramiteTrafMatr convertDtoToTraTrafMat(TramiteTrafMatrDto tramiteTrafMatrDto) throws ParseException {
		TramiteTrafMatr tramiteMatr = new TramiteTrafMatr();

		if (tramiteTrafMatrDto.getaDeducir576() != null)
			tramiteMatr.setADeducir576(tramiteTrafMatrDto.getaDeducir576());
		if (tramiteTrafMatrDto.getBaseImponible576() != null)
			tramiteMatr.setBaseImponible576(tramiteTrafMatrDto.getBaseImponible576());
		if (tramiteTrafMatrDto.getBaseImpoReducida576() != null)
			tramiteMatr.setBaseImpoReducida576(tramiteTrafMatrDto.getBaseImpoReducida576());
		if (tramiteTrafMatrDto.getCausaHechoImpon576() != null)
			tramiteMatr.setCausaHechoImpon576(tramiteTrafMatrDto.getCausaHechoImpon576());
		if (tramiteTrafMatrDto.getCuota576() != null)
			tramiteMatr.setCuota576(tramiteTrafMatrDto.getCuota576());
		if (tramiteTrafMatrDto.getCuotaIngresar576() != null)
			tramiteMatr.setCuotaIngresar576(tramiteTrafMatrDto.getCuotaIngresar576());
		if (tramiteTrafMatrDto.getDeduccionLineal576() != null)
			tramiteMatr.setDeduccionLineal576(tramiteTrafMatrDto.getDeduccionLineal576());
		if (tramiteTrafMatrDto.getEjercicio576() != null)
			tramiteMatr.setEjercicio576(tramiteTrafMatrDto.getEjercicio576());
		if (tramiteTrafMatrDto.getEntregaFactMatriculacion() != null
				&& !tramiteTrafMatrDto.getEntregaFactMatriculacion().equals(""))
			tramiteMatr.setEntregaFactMatriculacion(tramiteTrafMatrDto.getEntregaFactMatriculacion());
		if (tramiteTrafMatrDto.getExento576() != null && !tramiteTrafMatrDto.getExento576().equals(""))
			tramiteMatr.setExento576(tramiteTrafMatrDto.getExento576());
		if (tramiteTrafMatrDto.getFechaPago576() != null)
			tramiteMatr.setFechaPago576(tramiteTrafMatrDto.getFechaPago576().getDate());
		if (tramiteTrafMatrDto.getIdNoSujeccion06() != null && !tramiteTrafMatrDto.getIdNoSujeccion06().equals(""))
			tramiteMatr.setIdNoSujeccion06(tramiteTrafMatrDto.getIdNoSujeccion06());
		if (tramiteTrafMatrDto.getIdReduccion05() != null && !tramiteTrafMatrDto.getIdReduccion05().equals(""))
			tramiteMatr.setIdReduccion05(tramiteTrafMatrDto.getIdReduccion05());
		if (tramiteTrafMatrDto.getImporte576() != null)
			tramiteMatr.setImporte576(tramiteTrafMatrDto.getImporte576());
		if (tramiteTrafMatrDto.getLiquidacion576() != null)
			tramiteMatr.setLiquidacion576(tramiteTrafMatrDto.getLiquidacion576());
		if (tramiteTrafMatrDto.getnDeclaracionComp576() != null
				&& !tramiteTrafMatrDto.getnDeclaracionComp576().equals(""))
			tramiteMatr.setNDeclaracionComp576(tramiteTrafMatrDto.getnDeclaracionComp576());
		if (tramiteTrafMatrDto.getNrc576() != null && !tramiteTrafMatrDto.getNrc576().equals(""))
			tramiteMatr.setNrc576(tramiteTrafMatrDto.getNrc576());
		if (tramiteTrafMatrDto.getNumExpediente() != 0)
			tramiteMatr.setNumExpediente(tramiteTrafMatrDto.getNumExpediente());
		if (tramiteTrafMatrDto.getObservaciones576() != null && !tramiteTrafMatrDto.getObservaciones576().equals(""))
			tramiteMatr.setObservaciones576(tramiteTrafMatrDto.getObservaciones576());
		if (tramiteTrafMatrDto.getReduccion576() != null && !tramiteTrafMatrDto.getReduccion576().equals(""))
			tramiteMatr.setReduccion576(tramiteTrafMatrDto.getReduccion576());
		if (tramiteTrafMatrDto.getTipoGravamen576() != null)
			tramiteMatr.setTipoGravamen576(tramiteTrafMatrDto.getTipoGravamen576());
		if (tramiteTrafMatrDto.getTramiteTrafico() != null)
			tramiteMatr.setTramiteTrafico(tramiteTrafMatrDto.getTramiteTrafico());
		if (tramiteTrafMatrDto.getTipoTramiteMatriculacion() != null) {
			tramiteMatr.setTipoTramiteMatr(tramiteTrafMatrDto.getTipoTramiteMatriculacion().getValorEnum());
		}
		if (tramiteTrafMatrDto.getJustificadoIvtm() != null && !tramiteTrafMatrDto.getJustificadoIvtm().isEmpty()) {
			tramiteMatr.setJustificadoIvtm(tramiteTrafMatrDto.getJustificadoIvtm());
		}
		//TODO MPC. Cambio IVTM.
		// El pasar a tramite no lleva bean de IVTM
//		IvtmMatriculacionDto ivtmDto = modeloIvtm.buscarIvtmNumExp(new BigDecimal(tramite.getNumExpediente()));
//		if(ivtmDto!=null)
//			tramiteMatr.setIvtmMatriculacionDto(ivtmDto);
		return tramiteMatr;
	}

}