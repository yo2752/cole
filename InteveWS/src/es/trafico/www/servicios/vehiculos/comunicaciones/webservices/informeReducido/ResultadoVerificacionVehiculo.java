
package es.trafico.www.servicios.vehiculos.comunicaciones.webservices.informeReducido;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para resultadoVerificacionVehiculo.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * <p>
 * <pre>
 * &lt;simpleType name="resultadoVerificacionVehiculo"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SIN_INCIDENCIAS"/&gt;
 *     &lt;enumeration value="CON_INCIDENCIAS"/&gt;
 *     &lt;enumeration value="CON_AVISOS"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "resultadoVerificacionVehiculo")
@XmlEnum
public enum ResultadoVerificacionVehiculo {

    SIN_INCIDENCIAS,
    CON_INCIDENCIAS,
    CON_AVISOS;

    public String value() {
        return name();
    }

    public static ResultadoVerificacionVehiculo fromValue(String v) {
        return valueOf(v);
    }

}
