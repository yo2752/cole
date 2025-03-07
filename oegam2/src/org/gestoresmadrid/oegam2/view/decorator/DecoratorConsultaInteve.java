package org.gestoresmadrid.oegam2.view.decorator;

import org.displaytag.decorator.TableDecorator;
import org.gestoresmadrid.core.trafico.solInfo.model.enumerados.EstadoTramiteSolicitudInformacion;
import org.gestoresmadrid.oegam2comun.trafico.solInfo.view.beans.ConsultaInteveDto;

public class DecoratorConsultaInteve extends TableDecorator {

	public String getBastidor() {

		if (getCurrentRowObject() instanceof ConsultaInteveDto) {
			ConsultaInteveDto tramiteInteve = (ConsultaInteveDto) getCurrentRowObject();
			if (tramiteInteve.getBastidor() != null && tramiteInteve.getBastidor().length > 0) {
				String[] bastidor = tramiteInteve.getBastidor();
				StringBuffer salida2 = new StringBuffer();
				for (int i = 0; i < bastidor.length; i++) {
					if (bastidor[i] != null) {
						salida2.append(bastidor[i] + "\n");
					}

				}
				if ("".equals(salida2.toString())) {
					return "-";
				}
				return salida2.toString();

			} else {
				return "-";
			}

		}
		return null;
	}

	public String getMatricula() {
		if (getCurrentRowObject() instanceof ConsultaInteveDto) {
			ConsultaInteveDto tramiteInteve = (ConsultaInteveDto) getCurrentRowObject();
			if (tramiteInteve.getMatricula() != null && tramiteInteve.getMatricula().length > 0) {

				String[] matricula = tramiteInteve.getMatricula();
				StringBuffer salida2 = new StringBuffer();
				for (int i = 0; i < matricula.length; i++) {
					if (matricula[i] != null) {
						salida2.append(matricula[i] + "\n");
					}

				}
				if ("".equals(salida2.toString())) {
					return "-";
				}
				return salida2.toString();
			} else {
				return "-";
			}

		}
		return null;
	}

	public String getTasa() {
		if (getCurrentRowObject() instanceof ConsultaInteveDto) {
			ConsultaInteveDto tramiteInteve = (ConsultaInteveDto) getCurrentRowObject();
			if (tramiteInteve.getTasa() != null && tramiteInteve.getTasa().length > 0) {
				String[] tasa = tramiteInteve.getTasa();
				StringBuffer salida2 = new StringBuffer();
				for (int i = 0; i < tasa.length; i++) {
					if (tasa[i] != null) {
						salida2.append(tasa[i] + "\n");
					}
				}
				if ("".equals(salida2.toString())) {
					return "-";
				}
				return salida2.toString();

			} else {
				return "-";
			}

		}
		return null;
	}

	public String addRowClass() {
		ConsultaInteveDto tramiteInteve = (ConsultaInteveDto) getCurrentRowObject();
		if (EstadoTramiteSolicitudInformacion.Finalizado_PDF.getValorEnum()
				.equals(EstadoTramiteSolicitudInformacion.convertirNombre(tramiteInteve.getEstado()))) {
			return "enlaceImpreso impreso";
		}
		return null;
	}
}