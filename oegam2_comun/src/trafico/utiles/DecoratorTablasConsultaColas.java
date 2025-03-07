package trafico.utiles;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.modelos.model.enumerados.EstadoModelos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;

import colas.daos.PeticionesPendientesResultTransform;
import hibernate.entities.trafico.Vehiculo;

public class DecoratorTablasConsultaColas  extends TableDecorator {

	public DecoratorTablasConsultaColas() {
	}

	public String addRowClass() {
		PeticionesPendientesResultTransform row = (PeticionesPendientesResultTransform) getCurrentRowObject();

		String impreso = row.getEstado();

		if (impreso != null && (impreso.equals(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getNombreEnum()))) {
			return "enlaceImpreso impreso";
		} else if (impreso != null && !impreso.isEmpty()
				&& EstadoTramiteTrafico.Anulado.getNombreEnum().equals(impreso)) {
			return "enlaceAnulado anulado";
		}
		return null;
	}

	public String getBastidor(){
		PeticionesPendientesResultTransform row = (PeticionesPendientesResultTransform) getCurrentRowObject();

		if(ProcesosEnum.MODELO_600_601.getNombreEnum().equals(row.getProceso())){
			return "";
		}
		if (row.getVehiculo() == null || row.getVehiculo().isEmpty()){
			return "";
		}
		String salida="";

		for (int i=0;i<row.getVehiculo().size();i++){
			Vehiculo v = row.getVehiculo().get(i);

			if (v.getBastidor()==null || v.getBastidor().equals("")){
				salida +=" - <br>";
			}else {
				salida +=v.getBastidor()+"<br>";
			}
		}
		if (salida.equals(" - <br>")){
			salida = "";
		}
		if (salida.length()>4){
			salida= salida.substring(0, salida.length()-4);
		}
		return salida;
	}

	public String getMatricula(){
		PeticionesPendientesResultTransform row = (PeticionesPendientesResultTransform) getCurrentRowObject();
		if(ProcesosEnum.MODELO_600_601.getNombreEnum().equals(row.getProceso())){
			return "";
		}

		if (row.getVehiculo() == null || row.getVehiculo().size()==0){
			return "";
		}
		String salida="";
		for (int i=0;i<row.getVehiculo().size();i++){
			Vehiculo v = row.getVehiculo().get(i);
			if (v.getMatricula()==null || v.getMatricula().equals("")){
				salida +=" - <br>";
			} else {
				salida +=v.getMatricula()+"<br>";
			}
		}
		if (salida.equals(" - <br>")){
			salida = "";
		}
		if (salida.length()>4){
			salida= salida.substring(0, salida.length()-4);
		}
		return salida;
	}

	public String getEstadoTramite(){
		PeticionesPendientesResultTransform row = (PeticionesPendientesResultTransform) getCurrentRowObject();
		if(ProcesosEnum.MODELO_600_601.getNombreEnum().equals(row.getProceso())){
			return EstadoModelos.convertirTexto(row.getCodEstado());
		}
		return row.getEstadoTramite();
	}
}