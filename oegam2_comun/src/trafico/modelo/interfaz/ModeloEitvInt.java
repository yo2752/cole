package trafico.modelo.interfaz;

import java.math.BigDecimal;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;

public interface ModeloEitvInt {

	public void generarXmlSolicitudEitv(BigDecimal numExpediente, String numColegiado, BigDecimal idContrato, String nive, String bastidor) throws Throwable;

	void exportarFicheroEitv(ConsultaTarjeta consultaTarjetaEitv, BigDecimal numExpediente) throws Throwable;
}