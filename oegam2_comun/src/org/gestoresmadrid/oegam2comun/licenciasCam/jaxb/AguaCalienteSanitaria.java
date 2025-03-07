package org.gestoresmadrid.oegam2comun.licenciasCam.jaxb;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for agua_caliente_sanitaria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="agua_caliente_sanitaria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agua_caliente_sanitaria" type="{http://licencias/mensajes}string2type"/>
 *         &lt;element name="tipo_combustible" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="potencia_agua_caliente" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *         &lt;element name="acumulador_calor" type="{http://licencias/mensajes}string1type" minOccurs="0"/>
 *         &lt;element name="potencia_acumulador" type="{http://licencias/mensajes}decimal103type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agua_caliente_sanitaria", propOrder = {
    "aguaCalienteSanitaria",
    "tipoCombustible",
    "potenciaAguaCaliente",
    "acumuladorCalor",
    "potenciaAcumulador"
})
public class AguaCalienteSanitaria {

    @XmlElement(name = "agua_caliente_sanitaria", required = true)
    protected String aguaCalienteSanitaria;
    @XmlElement(name = "tipo_combustible")
    protected String tipoCombustible;
    @XmlElement(name = "potencia_agua_caliente")
    protected BigDecimal potenciaAguaCaliente;
    @XmlElement(name = "acumulador_calor")
    protected String acumuladorCalor;
    @XmlElement(name = "potencia_acumulador")
    protected BigDecimal potenciaAcumulador;

    /**
     * Gets the value of the aguaCalienteSanitaria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAguaCalienteSanitaria() {
        return aguaCalienteSanitaria;
    }

    /**
     * Sets the value of the aguaCalienteSanitaria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAguaCalienteSanitaria(String value) {
        this.aguaCalienteSanitaria = value;
    }

    /**
     * Gets the value of the tipoCombustible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoCombustible() {
        return tipoCombustible;
    }

    /**
     * Sets the value of the tipoCombustible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoCombustible(String value) {
        this.tipoCombustible = value;
    }

    /**
     * Gets the value of the potenciaAguaCaliente property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaAguaCaliente() {
        return potenciaAguaCaliente;
    }

    /**
     * Sets the value of the potenciaAguaCaliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaAguaCaliente(BigDecimal value) {
        this.potenciaAguaCaliente = value;
    }

    /**
     * Gets the value of the acumuladorCalor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcumuladorCalor() {
        return acumuladorCalor;
    }

    /**
     * Sets the value of the acumuladorCalor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcumuladorCalor(String value) {
        this.acumuladorCalor = value;
    }

    /**
     * Gets the value of the potenciaAcumulador property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getPotenciaAcumulador() {
        return potenciaAcumulador;
    }

    /**
     * Sets the value of the potenciaAcumulador property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setPotenciaAcumulador(BigDecimal value) {
        this.potenciaAcumulador = value;
    }

}
