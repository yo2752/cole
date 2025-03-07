package trafico.modelo.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.springframework.beans.factory.annotation.Autowired;

import colas.modelo.ModeloSolicitud;
import escrituras.beans.ResultBean;
import oegam.constantes.ConstantesPQ;
import procesos.daos.BeanPQCrearSolicitud;
import trafico.modelo.interfaz.ModeloImportacionMasivaInterface;
import utilidades.web.OegamExcepcion;

public class ModeloImportacionMasivaImpl implements ModeloImportacionMasivaInterface {

	@Autowired
	private UtilesColegiado utilesColegiado;

	public ModeloImportacionMasivaImpl() {
	}

	@Override
	public ResultBean crearSolicitud(String nombreFichero, String tipoTramiteTrafico, String idTramite) throws OegamExcepcion {
		BeanPQCrearSolicitud solicitudBean = new BeanPQCrearSolicitud();

		solicitudBean.setP_PROCESO(ProcesosEnum.IMPORTACIONMASIVA.getNombreEnum());
		solicitudBean.setP_TIPO_TRAMITE(tipoTramiteTrafico);
		solicitudBean.setP_ID_USUARIO(utilesColegiado.getIdUsuarioSessionBigDecimal());
		solicitudBean.setP_XML_ENVIAR(nombreFichero);
		solicitudBean.setP_ID_TRAMITE(new BigDecimal(idTramite));

		HashMap<String,Object> resul = new ModeloSolicitud().crearSolicitudProcesos2(solicitudBean);
		return (ResultBean) resul.get(ConstantesPQ.RESULTBEAN);
	}
}