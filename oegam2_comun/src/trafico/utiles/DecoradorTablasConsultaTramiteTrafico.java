package trafico.utiles;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.model.enumerados.EstadoPresentacionJPT;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.tasas.view.beans.Tasa;

import hibernate.entities.trafico.Vehiculo;
import trafico.beans.ConsultaTramiteTraficoFilaTablaResultadoBean;

public class DecoradorTablasConsultaTramiteTrafico extends TableDecorator {

	public DecoradorTablasConsultaTramiteTrafico() {}

	public String addRowClass() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();

		String impreso = row.getEstado();

		if (impreso != null && (impreso.equals(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Excel_Impreso.getNombreEnum())
				|| impreso.equals(EstadoTramiteTrafico.Finalizado_Telematicamente_Revisado.getNombreEnum()))) {
			return "enlaceImpreso impreso";
		} else if (impreso != null && !impreso.isEmpty()
				&& EstadoTramiteTrafico.Anulado.getNombreEnum().equals(impreso)) {
			return "enlaceAnulado anulado";
		}

		return null;
	}

	public String getBastidor() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		String salida = "";
		if (row.getVehiculo() == null || row.getVehiculo().isEmpty()) {
			return "";
		}
		for (int i = 0; i < row.getVehiculo().size(); i++) {
			Vehiculo v = row.getVehiculo().get(i);
			if (v.getBastidor() == null || v.getBastidor().equals("")) {
				salida += " - <br>";
			} else {
				salida += v.getBastidor() + "<br>";
			}
		}
		if (salida.equals(" - <br>")) {
			salida = "";
		}
		if (salida.length() > 4) {
			salida = salida.substring(0, salida.length() - 4);
		}
		return salida;
	}

	public String getMatricula() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		if (row.getVehiculo() == null || row.getVehiculo().isEmpty()) {
			return "";
		}
		String salida = "";
		for (int i = 0; i < row.getVehiculo().size(); i++) {
			Vehiculo v = row.getVehiculo().get(i);
			if (v.getMatricula() == null || v.getMatricula().equals("")) {
				salida += " - <br>";
			} else {
				salida += v.getMatricula() + "<br>";
			}
		}
		if (salida.equals(" - <br>")) {
			salida = "";
		}
		if (salida.length() > 4) {
			salida = salida.substring(0, salida.length() - 4);
		}
		return salida;
	}

	public String getCodigoTasa() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		if (row.getTasa() == null || row.getTasa().isEmpty()) {
			return "";
		}
		String salida = "";
		for (int i = 0; i < row.getTasa().size(); i++) {
			Tasa t = row.getTasa().get(i);
			if (t.getCodigoTasa() == null || t.getCodigoTasa().equals("")) {
				salida += " - <br>";
			} else {
				salida += t.getCodigoTasa() + "<br>";
			}
		}
		if (salida.equals(" - <br>")) {
			salida = "";
		}
		if (salida.length() > 4) {
			salida = salida.substring(0, salida.length() - 4);
		}
		return salida;
	}

	public String getTipoTasa() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		if (row.getTasa() == null || row.getTasa().isEmpty()) {
			return "";
		}
		String salida = "";
		for (int i = 0; i < row.getTasa().size(); i++) {
			Tasa t = row.getTasa().get(i);
			if (t.getTipoTasa() == null || t.getTipoTasa().equals("")) {
				salida += " - <br>";
			} else {
				salida += t.getTipoTasa() + "<br>";
			}
		}
		if (salida.equals(" - <br>")) {
			salida = "";
		}
		if (salida.length() > 4) {
			salida = salida.substring(0, salida.length() - 4);
		}
		return salida;
	}

	public String getPresentadoJpt() {
		ConsultaTramiteTraficoFilaTablaResultadoBean row = (ConsultaTramiteTraficoFilaTablaResultadoBean) getCurrentRowObject();
		if (row.getPresentadoJpt() == null) {
			return EstadoPresentacionJPT.No_Presentado.getNombreEnum();
		}
		return EstadoPresentacionJPT.convertirValorANombre(row.getPresentadoJpt());
	}
}