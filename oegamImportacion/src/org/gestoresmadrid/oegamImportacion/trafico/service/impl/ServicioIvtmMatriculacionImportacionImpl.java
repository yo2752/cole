package org.gestoresmadrid.oegamImportacion.trafico.service.impl;

import java.math.BigDecimal;

import org.gestoresmadrid.core.ivtmMatriculacion.model.dao.IvtmMatriculacionDao;
import org.gestoresmadrid.core.ivtmMatriculacion.model.vo.IvtmMatriculacionVO;
import org.gestoresmadrid.core.model.enumerados.TipoSINO;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.DatosIvtm;
import org.gestoresmadrid.oegamConversiones.jaxb.matw.DatosIvtmMatw;
import org.gestoresmadrid.oegamImportacion.trafico.service.ServicioIvtmMatriculacionImportacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioIvtmMatriculacionImportacionImpl implements ServicioIvtmMatriculacionImportacion {


	private static final long serialVersionUID = -2583765403283483952L;

	public static final ILoggerOegam log = LoggerOegam.getLogger(ServicioIvtmMatriculacionImportacionImpl.class);

	@Autowired
	private IvtmMatriculacionDao ivtmMatriculacionDao;

	@Override
	@Transactional
	public String guardarIvtm(IvtmMatriculacionVO ivtmMatriculacionVO) {
		String mensajeResultado = "";
		try {
			ivtmMatriculacionDao.guardarOActualizar(ivtmMatriculacionVO);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de guardar el IVTM, error: ", e);
			mensajeResultado = "Ha sucedido un error a la hora de guardar el IVTM.";
		}
		return mensajeResultado;
	}

	@Override
	@Transactional
	public DatosIvtm obtenerDatosParaImportacion(BigDecimal numExpediente, boolean tienePermisoIVTM) {
		DatosIvtm ivtm = null;
		try {
			IvtmMatriculacionVO ivtmVO = ivtmMatriculacionDao.getIvtmPorExpediente(numExpediente);
			if(ivtmVO != null) {
				ivtm = new DatosIvtmMatw();
				if (ivtmVO.getFechaPago() != null) {
					ivtm.setFECHAALTAAUTOLIQUIDACIONIMVTM(ivtmVO.getFechaPago().toString());
				}
				ivtm.setCUOTAAPAGARIMVTM(ivtmVO.getImporte());
				if (tienePermisoIVTM) {
					ivtm.setBONOMEDIOAMBIENTE(ivtmVO.getBonmedam() != null && ivtmVO.getBonmedam() 
							? TipoSINO.SI.value() : TipoSINO.NO.value());
					ivtm.setCODIGOGESTOR(ivtmVO.getCodGestor());
					if (ivtmVO.getCodigoRespuestaPago() != null) {
						ivtm.setCODIGORESPUESTAPAGO(ivtmVO.getCodigoRespuestaPago());
					}
					ivtm.setDIGITOCONTROL(ivtmVO.getDigitoControl());
					ivtm.setEMISOR(ivtmVO.getEmisor());
					ivtm.setEXENTOPAGO(ivtmVO.getExento() != null && ivtmVO.getExento() ? 
							TipoSINO.SI.value() : TipoSINO.NO.value());
					ivtm.setIBAN(ivtmVO.getIban());
					ivtm.setIMPORTEANUAL(ivtmVO.getImporteAnual());
					ivtm.setPORCBONOMEDIOAMBIENTE(ivtmVO.getPorcentajebonmedam().toString());
					ivtm.setNRC(ivtmVO.getNrc());
					if (ivtmVO.getCodigoRespuestaPago() != null)
						ivtm.setCODIGORESPUESTAPAGO(ivtmVO.getCodigoRespuestaPago());
					ivtm.setIDPETICIONIVTM(ivtmVO.getIdPeticion() != null ? ivtmVO.getIdPeticion().toString() : "");
				}
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener los datos del IVTM, error: ",e);
			ivtm = null;
		}
		return ivtm;
	}

}
