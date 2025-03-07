package administracion.decorators;

import org.displaytag.decorator.TableDecorator;

import administracion.beans.CertificateBean;

public class DecoradorTablaKeyStores extends TableDecorator {

	public String addRowClass() {
		CertificateBean row = (CertificateBean) getCurrentRowObject();

		long diasValidezRestantes = row.getDiasValidezRestantes();

		if (diasValidezRestantes <= 0) {
			return "enlaceImpreso impreso";
		}
		return null;
	}

}