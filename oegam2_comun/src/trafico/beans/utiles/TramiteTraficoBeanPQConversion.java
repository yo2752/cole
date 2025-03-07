package trafico.beans.utiles;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import trafico.beans.TramiteTraficoBean;
import trafico.beans.daos.BeanPQTramiteBuscarResult;

@Component
public class TramiteTraficoBeanPQConversion {

	@Autowired
	UtilesFecha utilesFecha;

	public TramiteTraficoBean convertirCursorToBean(BeanPQTramiteBuscarResult bean) {

		TramiteTraficoBean tramite = new TramiteTraficoBean(true);
		BeanPQTramiteBuscarResult lineaResultado = bean;

		tramite.setNumExpediente(lineaResultado.getNUM_EXPEDIENTE());
		tramite.setTipoTramite(lineaResultado.getTIPO_TRAMITE());
		tramite.setIdContrato(lineaResultado.getID_CONTRATO());
		tramite.setNumColegiado(lineaResultado.getNUM_COLEGIADO());

		tramite.getVehiculo().setIdVehiculo(lineaResultado.getID_VEHICULO());
		tramite.getVehiculo().setMatricula(lineaResultado.getMatricula());
		tramite.getVehiculo().setBastidor(lineaResultado.getBastidor());
		tramite.getVehiculo().getTipoVehiculoBean().setTipoVehiculo(lineaResultado.getTIPO_VEHICULO());

		tramite.getTasa().setCodigoTasa(lineaResultado.getCODIGO_TASA());
		tramite.getTasa().setTipoTasa(lineaResultado.getTIPO_TASA());

		if (lineaResultado.getESTADO() != null){
			BigDecimal estado = lineaResultado.getESTADO();
			tramite.setEstado(estado.toString());
		}

		tramite.setReferenciaPropia(lineaResultado.getREF_PROPIA());

		if (lineaResultado.getFECHA_ALTA() != null){
			Timestamp fecha = lineaResultado.getFECHA_ALTA();
			tramite.setFechaCreacion((utilesFecha.getFechaFracionada(fecha)));
		}
		if (lineaResultado.getFECHA_PRESENTACION() != null){
			Timestamp fecha = lineaResultado.getFECHA_PRESENTACION();
			tramite.setFechaPresentacion((utilesFecha.getFechaFracionada(fecha)));
		}
		if (lineaResultado.getFECHA_ULT_MODIF() != null){
			Timestamp fecha = lineaResultado.getFECHA_ULT_MODIF();
			tramite.setFechaUltModif((utilesFecha.getFechaFracionada(fecha)));
		}
		if (lineaResultado.getFECHA_IMPRESION() != null){
			Timestamp fecha = lineaResultado.getFECHA_IMPRESION();
			tramite.setFechaImpresion((utilesFecha.getFechaFracionada(fecha)));
		}

		tramite.setAnotaciones(lineaResultado.getANOTACIONES());
		tramite.setRespuesta(lineaResultado.getRESPUESTA());

		return tramite;
	}

}