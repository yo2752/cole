package trafico.beans.daos;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class EvolucionTasaCursor {

	private String CODIGO_TASA;
	private Timestamp FECHA_HORA;
	private BigDecimal ID_USUARIO;
	private String ACCION;
	private BigDecimal NUM_EXPEDIENTE;
	private BigDecimal PRECIO_ANT;
	private String PRECIO_NUE;
	private Timestamp FECHA_VIGENCIA_ANT;
	private Timestamp FECHA_VIGENCIA_NUE;
	private String APELLIDOS_NOMBRE;
	private String MOTIVO_BLOQUEO;

	private int INDICE;

	@Autowired
	UtilesFecha utilesFecha;

	public EvolucionTasaCursor() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String getCODIGO_TASA() {
		return CODIGO_TASA;
	}

	public void setCODIGO_TASA(String cODIGOTASA) {
		CODIGO_TASA = cODIGOTASA;
	}

	public Timestamp getFECHA_HORA() {
		return FECHA_HORA;
	}

	public void setFECHA_HORA(Timestamp fECHAHORA) {
		FECHA_HORA = fECHAHORA;
	}

	public BigDecimal getID_USUARIO() {
		return ID_USUARIO;
	}

	public void setID_USUARIO(BigDecimal iDUSUARIO) {
		ID_USUARIO = iDUSUARIO;
	}

	public String getACCION() {
		return ACCION;
	}

	public void setACCION(String aCCION) {
		ACCION = aCCION;
	}

	public BigDecimal getNUM_EXPEDIENTE() {
		return NUM_EXPEDIENTE;
	}

	public void setNUM_EXPEDIENTE(BigDecimal nUMEXPEDIENTE) {
		NUM_EXPEDIENTE = nUMEXPEDIENTE;
	}

	public BigDecimal getPRECIO_ANT() {
		return PRECIO_ANT;
	}

	public void setPRECIO_ANT(BigDecimal pRECIOANT) {
		PRECIO_ANT = pRECIOANT;
	}

	public String getPRECIO_NUE() {
		return PRECIO_NUE;
	}

	public void setPRECIO_NUE(String pRECIONUE) {
		PRECIO_NUE = pRECIONUE;
	}

	public Timestamp getFECHA_VIGENCIA_ANT() {
		return FECHA_VIGENCIA_ANT;
	}

	public void setFECHA_VIGENCIA_ANT(Timestamp fECHAVIGENCIAANT) {
		FECHA_VIGENCIA_ANT = fECHAVIGENCIAANT;
	}

	public Timestamp getFECHA_VIGENCIA_NUE() {
		return FECHA_VIGENCIA_NUE;
	}

	public void setFECHA_VIGENCIA_NUE(Timestamp fECHAVIGENCIANUE) {
		FECHA_VIGENCIA_NUE = fECHAVIGENCIANUE;
	}

	public String getAPELLIDOS_NOMBRE() {
		return APELLIDOS_NOMBRE;
	}

	public void setAPELLIDOS_NOMBRE(String aPELLIDOSNOMBRE) {
		APELLIDOS_NOMBRE = aPELLIDOSNOMBRE;
	}

	public int getINDICE() {
		return INDICE;
	}

	public void setINDICE(int iNDICE) {
		INDICE = iNDICE;
	}

	public String getMOTIVO_BLOQUEO() {
		return MOTIVO_BLOQUEO;
	}

	public void setMOTIVO_BLOQUEO(String mOTIVO_BLOQUEO) {
		MOTIVO_BLOQUEO = mOTIVO_BLOQUEO;
	}

	public String getACCION_RESUMEN() {
		String resumen = "";
		if ("CREACION".equals(getACCION()) || "DESASIGNACION".equals(getACCION()) || "CAMBIO DE FORMATO".equals(getACCION()) || "DESBLOQUEO".equals(getACCION())) {
			resumen = dobleCelda(getACCION(), "");
		} else {
			resumen = dobleCelda(getACCION(), getHTML_RESUMEN());
		}
		return resumen;
	}

	public String getHTML_RESUMEN() {
		String mensaje = "";
		mensaje += "<img style='cursor:help;' src='img/mostrar.gif' onmouseover='mostrarVentana(event, " + this.getINDICE() + ",1)' onmouseout='ocultarVentana()'>";
		if ("ASIGNACION".equals(getACCION())) {
			mensaje += this.getHTML_ASIGNACION();
		} else if ("CAMBIO".equals(getACCION())) {
			mensaje += this.getHTML_CAMBIO();
		} else if ("BLOQUEO".equals(getACCION())) {
			mensaje += this.getHTML_MOTIVO();
		}
		return mensaje;
	}

	public String getHTML_ASIGNACION() {
		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getINDICE() + "' style='display:none;'>";
		if ((null == this.getNUM_EXPEDIENTE() || "".equals(this.getNUM_EXPEDIENTE().toString()))) {
			mensaje += "No hay cambio de asignación";
		} else {
			mensaje += "<center><table>";
			mensaje += "<tr>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:220px;'>Número de Expediente</td>";
			mensaje += "</tr>";
			if (null != this.getNUM_EXPEDIENTE() && !"".equals(this.getNUM_EXPEDIENTE().toString())) {
				mensaje += "<tr>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";text-align:center;'>" + this.getNUM_EXPEDIENTE() + "</td>";
				mensaje += "</tr>";
			}
			mensaje += "</table></center>";
		}
		mensaje += "</span>";

		return mensaje;
	}

	public String getHTML_MOTIVO() {
		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getINDICE() + "' style='display:none;'>";
		if (StringUtils.isBlank(this.getMOTIVO_BLOQUEO())) {
			mensaje += "No se indico el motivo";
		} else {
			mensaje += "<center><table>";
			mensaje += "<tr>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:220px;'>Motivo</td>";
			mensaje += "</tr>";
			if (StringUtils.isNotBlank(this.getMOTIVO_BLOQUEO())) {
				mensaje += "<tr>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";text-align:center;'>" + this.getMOTIVO_BLOQUEO() + "</td>";
				mensaje += "</tr>";
			}
			mensaje += "</table></center>";
		}
		mensaje += "</span>";

		return mensaje;
	}

	public String getHTML_CAMBIO() {
		String colorFondoTitulo = "#A52642";
		String colorFondoNormal = "#F5E9EB";
		String colorTextoTitulo = "white";
		String colorTextoNormal = "black";

		String mensaje = "<span id='detalles" + this.getINDICE() + "' style='display:none;'>";
		if ((null == this.getPRECIO_ANT() || "".equals(this.getPRECIO_ANT()))
				&& (null == this.getPRECIO_NUE() || "".equals(this.getPRECIO_NUE()))
				&& (null == this.getFECHA_VIGENCIA_ANT() || "".equals(this.getFECHA_VIGENCIA_ANT()))
				&& (null == this.getFECHA_VIGENCIA_NUE() || "".equals(this.getFECHA_VIGENCIA_NUE()))) {
			mensaje += "No hay cambios de precio o fecha de vigencia";
		} else {
			mensaje += "<table>";
			mensaje += "<tr>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:70px;text-align:center;'>Atributo</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:90px;text-align:center;'>Anterior</td>";
			mensaje += "<td style='background-color:" + colorFondoTitulo + ";color:" + colorTextoTitulo + ";width:90px;text-align:center;'>Nuevo</td>";
			mensaje += "</tr>";
			if (null != this.getPRECIO_ANT() && !"".equals(this.getPRECIO_ANT()) && null != this.getPRECIO_NUE() && !"".equals(this.getPRECIO_NUE())) {
				mensaje += "<tr>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Precio</td>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";text-align:center;'>" + this.getPRECIO_ANT() + "€</td>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";text-align:center;'>" + this.getPRECIO_NUE() + "€</td>";
				mensaje += "</tr>";
			}
			if (null != this.getFECHA_VIGENCIA_ANT() && null != this.getFECHA_VIGENCIA_NUE()) {
				mensaje += "<tr>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>Fecha vigencia</td>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + utilesFecha.getFechaFracionada(this.getFECHA_VIGENCIA_ANT()).toString() + "</td>";
				mensaje += "<td style='background-color:" + colorFondoNormal + ";color:" + colorTextoNormal + ";'>" + utilesFecha.getFechaFracionada(this.getFECHA_VIGENCIA_NUE()).toString() + "</td>";
				mensaje += "</tr>";
			}
			mensaje += "</table>";
		}
		mensaje += "</span>";

		return mensaje;
	}

	public String dobleCelda(String cambio, String mas) {
		String mensaje = "";
		mensaje += "<table>";
		mensaje += "<tr>";
		mensaje += "<td width='160px' style='text-align:right;border-width:0px;font-size: 1.0em;'>";
		mensaje += cambio;
		mensaje += "</td>";
		mensaje += "<td width='40px' style='text-align:right;border-width:0px;'>";
		mensaje += mas;
		mensaje += "</td>";
		mensaje += "</tr>";
		mensaje += "</table>";

		return mensaje;
	}

}