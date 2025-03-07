//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.8-b130911.1802 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2023.03.14 a las 10:44:31 AM CET 
//


package trafico.beans.jaxb.transmision;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}DNI_TRANSMITENTE"/>
 *         &lt;element ref="{}SEXO_TRANSMITENTE"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_TRANSMITENTE"/>
 *         &lt;element ref="{}APELLIDO2_TRANSMITENTE"/>
 *         &lt;element ref="{}NOMBRE_TRANSMITENTE"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_TRANSMITENTE"/>
 *         &lt;element ref="{}TELEFONO_TRANSMITENTE"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}LETRA_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}PISO_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_TRANSMITENTE"/>
 *         &lt;element ref="{}PROVINCIA_TRANSMITENTE"/>
 *         &lt;element ref="{}MUNICIPIO_TRANSMITENTE"/>
 *         &lt;element ref="{}PUEBLO_TRANSMITENTE"/>
 *         &lt;element ref="{}CP_TRANSMITENTE"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}AUTONOMO_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}CODIGO_IAE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}MOTIVO_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_INICIO_TUTELA_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_FIN_TUTELA_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}CONCEPTO_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DNI_REPRESENTANTE_TRANSMITENTE"/>
 *         &lt;element ref="{}NOMBRE_APELLIDOS_REPRESENTANTE_TRANSMITENTE"/>
 *         &lt;element ref="{}DOCUMENTOS_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_REPRESENTANTE_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DNI_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}SEXO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TELEFONO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}LETRA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PISO_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PROVINCIA_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}MUNICIPIO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PUEBLO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}CP_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_PRIMER_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DNI_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}SEXO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO1_RAZON_SOCIAL_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}APELLIDO2_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_NACIMIENTO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TELEFONO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}SIGLAS_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NOMBRE_VIA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NUMERO_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}LETRA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}ESCALERA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PISO_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PUERTA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PROVINCIA_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}MUNICIPIO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}PUEBLO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}CP_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}BLOQUE_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}KM_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}HM_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}DIRECCION_ACTIVA_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_NIF_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}TIPO_DOCUMENTO_SUSTITUTIVO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}INDEFINIDO_SEGUNDO_COTITULAR_TRANSMITENTE" minOccurs="0"/>
 *         &lt;element ref="{}NUMERO_TITULARES" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "DATOS_TRANSMITENTE")
public class DATOSTRANSMITENTE {

    @XmlElement(name = "DNI_TRANSMITENTE", required = true)
    protected String dnitransmitente;
    @XmlElement(name = "SEXO_TRANSMITENTE", required = true)
    protected String sexotransmitente;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_TRANSMITENTE", required = true)
    protected String apellido1RAZONSOCIALTRANSMITENTE;
    @XmlElement(name = "APELLIDO2_TRANSMITENTE", required = true)
    protected String apellido2TRANSMITENTE;
    @XmlElement(name = "NOMBRE_TRANSMITENTE", required = true)
    protected String nombretransmitente;
    @XmlElement(name = "FECHA_NACIMIENTO_TRANSMITENTE", required = true)
    protected String fechanacimientotransmitente;
    @XmlElement(name = "TELEFONO_TRANSMITENTE", required = true)
    protected String telefonotransmitente;
    @XmlElement(name = "SIGLAS_DIRECCION_TRANSMITENTE", required = true)
    protected String siglasdirecciontransmitente;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_TRANSMITENTE", required = true)
    protected String nombreviadirecciontransmitente;
    @XmlElement(name = "NUMERO_DIRECCION_TRANSMITENTE", required = true)
    protected String numerodirecciontransmitente;
    @XmlElement(name = "LETRA_DIRECCION_TRANSMITENTE", required = true)
    protected String letradirecciontransmitente;
    @XmlElement(name = "ESCALERA_DIRECCION_TRANSMITENTE", required = true)
    protected String escaleradirecciontransmitente;
    @XmlElement(name = "PISO_DIRECCION_TRANSMITENTE", required = true)
    protected String pisodirecciontransmitente;
    @XmlElement(name = "PUERTA_DIRECCION_TRANSMITENTE", required = true)
    protected String puertadirecciontransmitente;
    @XmlElement(name = "PROVINCIA_TRANSMITENTE", required = true)
    protected String provinciatransmitente;
    @XmlElement(name = "MUNICIPIO_TRANSMITENTE", required = true)
    protected String municipiotransmitente;
    @XmlElement(name = "PUEBLO_TRANSMITENTE", required = true)
    protected String pueblotransmitente;
    @XmlElement(name = "CP_TRANSMITENTE", required = true)
    protected String cptransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_TRANSMITENTE")
    protected String fechacaducidadniftransmitente;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_TRANSMITENTE")
    protected String tipodocumentosustitutivotransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_TRANSMITENTE")
    protected String fechacaducidaddocumentosustitutivotransmitente;
    @XmlElement(name = "INDEFINIDO_TRANSMITENTE")
    protected String indefinidotransmitente;
    @XmlElement(name = "BLOQUE_DIRECCION_TRANSMITENTE")
    protected String bloquedirecciontransmitente;
    @XmlElement(name = "KM_DIRECCION_TRANSMITENTE")
    protected String kmdirecciontransmitente;
    @XmlElement(name = "HM_DIRECCION_TRANSMITENTE")
    protected String hmdirecciontransmitente;
    @XmlElement(name = "DIRECCION_ACTIVA_TRANSMITENTE")
    protected String direccionactivatransmitente;
    @XmlElement(name = "AUTONOMO_TRANSMITENTE")
    protected String autonomotransmitente;
    @XmlElement(name = "CODIGO_IAE_TRANSMITENTE")
    protected String codigoiaetransmitente;
    @XmlElement(name = "NOMBRE_REPRESENTANTE_TRANSMITENTE")
    protected String nombrerepresentantetransmitente;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_REPRESENTANTE_TRANSMITENTE")
    protected String apellido1RAZONSOCIALREPRESENTANTETRANSMITENTE;
    @XmlElement(name = "APELLIDO2_REPRESENTANTE_TRANSMITENTE")
    protected String apellido2REPRESENTANTETRANSMITENTE;
    @XmlElement(name = "MOTIVO_REPRESENTANTE_TRANSMITENTE")
    protected String motivorepresentantetransmitente;
    @XmlElement(name = "FECHA_INICIO_TUTELA_TRANSMITENTE")
    protected String fechainiciotutelatransmitente;
    @XmlElement(name = "FECHA_FIN_TUTELA_TRANSMITENTE")
    protected String fechafintutelatransmitente;
    @XmlElement(name = "CONCEPTO_REPRESENTANTE_TRANSMITENTE")
    protected String conceptorepresentantetransmitente;
    @XmlElement(name = "DNI_REPRESENTANTE_TRANSMITENTE", required = true)
    protected String dnirepresentantetransmitente;
    @XmlElement(name = "NOMBRE_APELLIDOS_REPRESENTANTE_TRANSMITENTE", required = true)
    protected String nombreapellidosrepresentantetransmitente;
    @XmlElement(name = "DOCUMENTOS_REPRESENTANTE_TRANSMITENTE")
    protected String documentosrepresentantetransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_REPRESENTANTE_TRANSMITENTE")
    protected String fechacaducidadnifrepresentantetransmitente;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_TRANSMITENTE")
    protected String tipodocumentosustitutivorepresentantetransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_REPRESENTANTE_TRANSMITENTE")
    protected String fechacaducidaddocumentosustitutivorepresentantetransmitente;
    @XmlElement(name = "INDEFINIDO_REPRESENTANTE_TRANSMITENTE")
    protected String indefinidorepresentantetransmitente;
    @XmlElement(name = "DNI_PRIMER_COTITULAR_TRANSMITENTE")
    protected String dniprimercotitulartransmitente;
    @XmlElement(name = "SEXO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String sexoprimercotitulartransmitente;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_PRIMER_COTITULAR_TRANSMITENTE")
    protected String apellido1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE;
    @XmlElement(name = "APELLIDO2_PRIMER_COTITULAR_TRANSMITENTE")
    protected String apellido2PRIMERCOTITULARTRANSMITENTE;
    @XmlElement(name = "NOMBRE_PRIMER_COTITULAR_TRANSMITENTE")
    protected String nombreprimercotitulartransmitente;
    @XmlElement(name = "FECHA_NACIMIENTO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String fechanacimientoprimercotitulartransmitente;
    @XmlElement(name = "TELEFONO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String telefonoprimercotitulartransmitente;
    @XmlElement(name = "SIGLAS_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String siglasdireccionprimercotitulartransmitente;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String nombreviadireccionprimercotitulartransmitente;
    @XmlElement(name = "NUMERO_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String numerodireccionprimercotitulartransmitente;
    @XmlElement(name = "LETRA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String letradireccionprimercotitulartransmitente;
    @XmlElement(name = "ESCALERA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String escaleradireccionprimercotitulartransmitente;
    @XmlElement(name = "PISO_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String pisodireccionprimercotitulartransmitente;
    @XmlElement(name = "PUERTA_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String puertadireccionprimercotitulartransmitente;
    @XmlElement(name = "PROVINCIA_PRIMER_COTITULAR_TRANSMITENTE")
    protected String provinciaprimercotitulartransmitente;
    @XmlElement(name = "MUNICIPIO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String municipioprimercotitulartransmitente;
    @XmlElement(name = "PUEBLO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String puebloprimercotitulartransmitente;
    @XmlElement(name = "CP_PRIMER_COTITULAR_TRANSMITENTE")
    protected String cpprimercotitulartransmitente;
    @XmlElement(name = "BLOQUE_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String bloquedireccionprimercotitulartransmitente;
    @XmlElement(name = "KM_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String kmdireccionprimercotitulartransmitente;
    @XmlElement(name = "HM_DIRECCION_PRIMER_COTITULAR_TRANSMITENTE")
    protected String hmdireccionprimercotitulartransmitente;
    @XmlElement(name = "DIRECCION_ACTIVA_PRIMER_COTITULAR_TRANSMITENTE")
    protected String direccionactivaprimercotitulartransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_PRIMER_COTITULAR_TRANSMITENTE")
    protected String fechacaducidadnifprimercotitulartransmitente;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String tipodocumentosustitutivoprimercotitulartransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String fechacaducidaddocumentosustitutivoprimercotitulartransmitente;
    @XmlElement(name = "INDEFINIDO_PRIMER_COTITULAR_TRANSMITENTE")
    protected String indefinidoprimercotitulartransmitente;
    @XmlElement(name = "DNI_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String dnisegundocotitulartransmitente;
    @XmlElement(name = "SEXO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String sexosegundocotitulartransmitente;
    @XmlElement(name = "APELLIDO1_RAZON_SOCIAL_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String apellido1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE;
    @XmlElement(name = "APELLIDO2_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String apellido2SEGUNDOCOTITULARTRANSMITENTE;
    @XmlElement(name = "NOMBRE_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String nombresegundocotitulartransmitente;
    @XmlElement(name = "FECHA_NACIMIENTO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String fechanacimientosegundocotitulartransmitente;
    @XmlElement(name = "TELEFONO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String telefonosegundocotitulartransmitente;
    @XmlElement(name = "SIGLAS_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String siglasdireccionsegundocotitulartransmitente;
    @XmlElement(name = "NOMBRE_VIA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String nombreviadireccionsegundocotitulartransmitente;
    @XmlElement(name = "NUMERO_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String numerodireccionsegundocotitulartransmitente;
    @XmlElement(name = "LETRA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String letradireccionsegundocotitulartransmitente;
    @XmlElement(name = "ESCALERA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String escaleradireccionsegundocotitulartransmitente;
    @XmlElement(name = "PISO_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String pisodireccionsegundocotitulartransmitente;
    @XmlElement(name = "PUERTA_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String puertadireccionsegundocotitulartransmitente;
    @XmlElement(name = "PROVINCIA_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String provinciasegundocotitulartransmitente;
    @XmlElement(name = "MUNICIPIO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String municipiosegundocotitulartransmitente;
    @XmlElement(name = "PUEBLO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String pueblosegundocotitulartransmitente;
    @XmlElement(name = "CP_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String cpsegundocotitulartransmitente;
    @XmlElement(name = "BLOQUE_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String bloquedireccionsegundocotitulartransmitente;
    @XmlElement(name = "KM_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String kmdireccionsegundocotitulartransmitente;
    @XmlElement(name = "HM_DIRECCION_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String hmdireccionsegundocotitulartransmitente;
    @XmlElement(name = "DIRECCION_ACTIVA_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String direccionactivasegundocotitulartransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_NIF_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String fechacaducidadnifsegundocotitulartransmitente;
    @XmlElement(name = "TIPO_DOCUMENTO_SUSTITUTIVO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String tipodocumentosustitutivosegundocotitulartransmitente;
    @XmlElement(name = "FECHA_CADUCIDAD_DOCUMENTO_SUSTITUTIVO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String fechacaducidaddocumentosustitutivosegundocotitulartransmitente;
    @XmlElement(name = "INDEFINIDO_SEGUNDO_COTITULAR_TRANSMITENTE")
    protected String indefinidosegundocotitulartransmitente;
    @XmlElement(name = "NUMERO_TITULARES")
    protected String numerotitulares;

    /**
     * Obtiene el valor de la propiedad dnitransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNITRANSMITENTE() {
        return dnitransmitente;
    }

    /**
     * Define el valor de la propiedad dnitransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNITRANSMITENTE(String value) {
        this.dnitransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad sexotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOTRANSMITENTE() {
        return sexotransmitente;
    }

    /**
     * Define el valor de la propiedad sexotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOTRANSMITENTE(String value) {
        this.sexotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALTRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALTRANSMITENTE() {
        return apellido1RAZONSOCIALTRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALTRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALTRANSMITENTE(String value) {
        this.apellido1RAZONSOCIALTRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2TRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2TRANSMITENTE() {
        return apellido2TRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido2TRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2TRANSMITENTE(String value) {
        this.apellido2TRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad nombretransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBRETRANSMITENTE() {
        return nombretransmitente;
    }

    /**
     * Define el valor de la propiedad nombretransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBRETRANSMITENTE(String value) {
        this.nombretransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOTRANSMITENTE() {
        return fechanacimientotransmitente;
    }

    /**
     * Define el valor de la propiedad fechanacimientotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOTRANSMITENTE(String value) {
        this.fechanacimientotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOTRANSMITENTE() {
        return telefonotransmitente;
    }

    /**
     * Define el valor de la propiedad telefonotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOTRANSMITENTE(String value) {
        this.telefonotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONTRANSMITENTE() {
        return siglasdirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad siglasdirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONTRANSMITENTE(String value) {
        this.siglasdirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONTRANSMITENTE() {
        return nombreviadirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad nombreviadirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONTRANSMITENTE(String value) {
        this.nombreviadirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONTRANSMITENTE() {
        return numerodirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad numerodirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONTRANSMITENTE(String value) {
        this.numerodirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad letradirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONTRANSMITENTE() {
        return letradirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad letradirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONTRANSMITENTE(String value) {
        this.letradirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONTRANSMITENTE() {
        return escaleradirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad escaleradirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONTRANSMITENTE(String value) {
        this.escaleradirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONTRANSMITENTE() {
        return pisodirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad pisodirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONTRANSMITENTE(String value) {
        this.pisodirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONTRANSMITENTE() {
        return puertadirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad puertadirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONTRANSMITENTE(String value) {
        this.puertadirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciatransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIATRANSMITENTE() {
        return provinciatransmitente;
    }

    /**
     * Define el valor de la propiedad provinciatransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIATRANSMITENTE(String value) {
        this.provinciatransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad municipiotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOTRANSMITENTE() {
        return municipiotransmitente;
    }

    /**
     * Define el valor de la propiedad municipiotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOTRANSMITENTE(String value) {
        this.municipiotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLOTRANSMITENTE() {
        return pueblotransmitente;
    }

    /**
     * Define el valor de la propiedad pueblotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLOTRANSMITENTE(String value) {
        this.pueblotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad cptransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPTRANSMITENTE() {
        return cptransmitente;
    }

    /**
     * Define el valor de la propiedad cptransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPTRANSMITENTE(String value) {
        this.cptransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadniftransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFTRANSMITENTE() {
        return fechacaducidadniftransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidadniftransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFTRANSMITENTE(String value) {
        this.fechacaducidadniftransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOTRANSMITENTE() {
        return tipodocumentosustitutivotransmitente;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOTRANSMITENTE(String value) {
        this.tipodocumentosustitutivotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE() {
        return fechacaducidaddocumentosustitutivotransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOTRANSMITENTE(String value) {
        this.fechacaducidaddocumentosustitutivotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOTRANSMITENTE() {
        return indefinidotransmitente;
    }

    /**
     * Define el valor de la propiedad indefinidotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOTRANSMITENTE(String value) {
        this.indefinidotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONTRANSMITENTE() {
        return bloquedirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad bloquedirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONTRANSMITENTE(String value) {
        this.bloquedirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONTRANSMITENTE() {
        return kmdirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad kmdirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONTRANSMITENTE(String value) {
        this.kmdirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdirecciontransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONTRANSMITENTE() {
        return hmdirecciontransmitente;
    }

    /**
     * Define el valor de la propiedad hmdirecciontransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONTRANSMITENTE(String value) {
        this.hmdirecciontransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivatransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVATRANSMITENTE() {
        return direccionactivatransmitente;
    }

    /**
     * Define el valor de la propiedad direccionactivatransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVATRANSMITENTE(String value) {
        this.direccionactivatransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad autonomotransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAUTONOMOTRANSMITENTE() {
        return autonomotransmitente;
    }

    /**
     * Define el valor de la propiedad autonomotransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAUTONOMOTRANSMITENTE(String value) {
        this.autonomotransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoiaetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCODIGOIAETRANSMITENTE() {
        return codigoiaetransmitente;
    }

    /**
     * Define el valor de la propiedad codigoiaetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCODIGOIAETRANSMITENTE(String value) {
        this.codigoiaetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombrerepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREREPRESENTANTETRANSMITENTE() {
        return nombrerepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad nombrerepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREREPRESENTANTETRANSMITENTE(String value) {
        this.nombrerepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTETRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE() {
        return apellido1RAZONSOCIALREPRESENTANTETRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALREPRESENTANTETRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALREPRESENTANTETRANSMITENTE(String value) {
        this.apellido1RAZONSOCIALREPRESENTANTETRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2REPRESENTANTETRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2REPRESENTANTETRANSMITENTE() {
        return apellido2REPRESENTANTETRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido2REPRESENTANTETRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2REPRESENTANTETRANSMITENTE(String value) {
        this.apellido2REPRESENTANTETRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad motivorepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVOREPRESENTANTETRANSMITENTE() {
        return motivorepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad motivorepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVOREPRESENTANTETRANSMITENTE(String value) {
        this.motivorepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechainiciotutelatransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAINICIOTUTELATRANSMITENTE() {
        return fechainiciotutelatransmitente;
    }

    /**
     * Define el valor de la propiedad fechainiciotutelatransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAINICIOTUTELATRANSMITENTE(String value) {
        this.fechainiciotutelatransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechafintutelatransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHAFINTUTELATRANSMITENTE() {
        return fechafintutelatransmitente;
    }

    /**
     * Define el valor de la propiedad fechafintutelatransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHAFINTUTELATRANSMITENTE(String value) {
        this.fechafintutelatransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptorepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTOREPRESENTANTETRANSMITENTE() {
        return conceptorepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad conceptorepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTOREPRESENTANTETRANSMITENTE(String value) {
        this.conceptorepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad dnirepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIREPRESENTANTETRANSMITENTE() {
        return dnirepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad dnirepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIREPRESENTANTETRANSMITENTE(String value) {
        this.dnirepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreapellidosrepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE() {
        return nombreapellidosrepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad nombreapellidosrepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREAPELLIDOSREPRESENTANTETRANSMITENTE(String value) {
        this.nombreapellidosrepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad documentosrepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDOCUMENTOSREPRESENTANTETRANSMITENTE() {
        return documentosrepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad documentosrepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDOCUMENTOSREPRESENTANTETRANSMITENTE(String value) {
        this.documentosrepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifrepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFREPRESENTANTETRANSMITENTE() {
        return fechacaducidadnifrepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifrepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFREPRESENTANTETRANSMITENTE(String value) {
        this.fechacaducidadnifrepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivorepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE() {
        return tipodocumentosustitutivorepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivorepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE(String value) {
        this.tipodocumentosustitutivorepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE() {
        return fechacaducidaddocumentosustitutivorepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivorepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOREPRESENTANTETRANSMITENTE(String value) {
        this.fechacaducidaddocumentosustitutivorepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidorepresentantetransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOREPRESENTANTETRANSMITENTE() {
        return indefinidorepresentantetransmitente;
    }

    /**
     * Define el valor de la propiedad indefinidorepresentantetransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOREPRESENTANTETRANSMITENTE(String value) {
        this.indefinidorepresentantetransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad dniprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNIPRIMERCOTITULARTRANSMITENTE() {
        return dniprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad dniprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNIPRIMERCOTITULARTRANSMITENTE(String value) {
        this.dniprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad sexoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOPRIMERCOTITULARTRANSMITENTE() {
        return sexoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad sexoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.sexoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE() {
        return apellido1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE(String value) {
        this.apellido1RAZONSOCIALPRIMERCOTITULARTRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2PRIMERCOTITULARTRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2PRIMERCOTITULARTRANSMITENTE() {
        return apellido2PRIMERCOTITULARTRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido2PRIMERCOTITULARTRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2PRIMERCOTITULARTRANSMITENTE(String value) {
        this.apellido2PRIMERCOTITULARTRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREPRIMERCOTITULARTRANSMITENTE() {
        return nombreprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad nombreprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREPRIMERCOTITULARTRANSMITENTE(String value) {
        this.nombreprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE() {
        return fechanacimientoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechanacimientoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.fechanacimientoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOPRIMERCOTITULARTRANSMITENTE() {
        return telefonoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad telefonoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.telefonoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return siglasdireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad siglasdireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.siglasdireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return nombreviadireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad nombreviadireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.nombreviadireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return numerodireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad numerodireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.numerodireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad letradireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return letradireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad letradireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.letradireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return escaleradireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad escaleradireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.escaleradireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return pisodireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad pisodireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.pisodireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return puertadireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad puertadireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.puertadireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciaprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIAPRIMERCOTITULARTRANSMITENTE() {
        return provinciaprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad provinciaprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIAPRIMERCOTITULARTRANSMITENTE(String value) {
        this.provinciaprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad municipioprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOPRIMERCOTITULARTRANSMITENTE() {
        return municipioprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad municipioprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.municipioprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad puebloprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLOPRIMERCOTITULARTRANSMITENTE() {
        return puebloprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad puebloprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.puebloprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad cpprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPPRIMERCOTITULARTRANSMITENTE() {
        return cpprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad cpprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPPRIMERCOTITULARTRANSMITENTE(String value) {
        this.cpprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return bloquedireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad bloquedireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.bloquedireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return kmdireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad kmdireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.kmdireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdireccionprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONPRIMERCOTITULARTRANSMITENTE() {
        return hmdireccionprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad hmdireccionprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONPRIMERCOTITULARTRANSMITENTE(String value) {
        this.hmdireccionprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivaprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE() {
        return direccionactivaprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad direccionactivaprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVAPRIMERCOTITULARTRANSMITENTE(String value) {
        this.direccionactivaprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE() {
        return fechacaducidadnifprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFPRIMERCOTITULARTRANSMITENTE(String value) {
        this.fechacaducidadnifprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE() {
        return tipodocumentosustitutivoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.tipodocumentosustitutivoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE() {
        return fechacaducidaddocumentosustitutivoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.fechacaducidaddocumentosustitutivoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidoprimercotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOPRIMERCOTITULARTRANSMITENTE() {
        return indefinidoprimercotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad indefinidoprimercotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOPRIMERCOTITULARTRANSMITENTE(String value) {
        this.indefinidoprimercotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad dnisegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDNISEGUNDOCOTITULARTRANSMITENTE() {
        return dnisegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad dnisegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDNISEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.dnisegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad sexosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSEXOSEGUNDOCOTITULARTRANSMITENTE() {
        return sexosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad sexosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSEXOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.sexosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE() {
        return apellido1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.apellido1RAZONSOCIALSEGUNDOCOTITULARTRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad apellido2SEGUNDOCOTITULARTRANSMITENTE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE() {
        return apellido2SEGUNDOCOTITULARTRANSMITENTE;
    }

    /**
     * Define el valor de la propiedad apellido2SEGUNDOCOTITULARTRANSMITENTE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAPELLIDO2SEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.apellido2SEGUNDOCOTITULARTRANSMITENTE = value;
    }

    /**
     * Obtiene el valor de la propiedad nombresegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBRESEGUNDOCOTITULARTRANSMITENTE() {
        return nombresegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad nombresegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBRESEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.nombresegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechanacimientosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE() {
        return fechanacimientosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechanacimientosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHANACIMIENTOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.fechanacimientosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad telefonosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTELEFONOSEGUNDOCOTITULARTRANSMITENTE() {
        return telefonosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad telefonosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTELEFONOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.telefonosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad siglasdireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return siglasdireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad siglasdireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSIGLASDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.siglasdireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreviadireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return nombreviadireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad nombreviadireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMBREVIADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.nombreviadireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad numerodireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return numerodireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad numerodireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMERODIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.numerodireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad letradireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return letradireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad letradireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLETRADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.letradireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad escaleradireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return escaleradireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad escaleradireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESCALERADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.escaleradireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad pisodireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return pisodireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad pisodireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPISODIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.pisodireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad puertadireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return puertadireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad puertadireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUERTADIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.puertadireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad provinciasegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPROVINCIASEGUNDOCOTITULARTRANSMITENTE() {
        return provinciasegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad provinciasegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPROVINCIASEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.provinciasegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad municipiosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE() {
        return municipiosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad municipiosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMUNICIPIOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.municipiosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad pueblosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPUEBLOSEGUNDOCOTITULARTRANSMITENTE() {
        return pueblosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad pueblosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPUEBLOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.pueblosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad cpsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCPSEGUNDOCOTITULARTRANSMITENTE() {
        return cpsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad cpsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCPSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.cpsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad bloquedireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return bloquedireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad bloquedireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLOQUEDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.bloquedireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad kmdireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return kmdireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad kmdireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.kmdireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad hmdireccionsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE() {
        return hmdireccionsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad hmdireccionsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHMDIRECCIONSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.hmdireccionsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionactivasegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE() {
        return direccionactivasegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad direccionactivasegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDIRECCIONACTIVASEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.direccionactivasegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidadnifsegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE() {
        return fechacaducidadnifsegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidadnifsegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADNIFSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.fechacaducidadnifsegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipodocumentosustitutivosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPODOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE() {
        return tipodocumentosustitutivosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad tipodocumentosustitutivosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPODOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.tipodocumentosustitutivosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad fechacaducidaddocumentosustitutivosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE() {
        return fechacaducidaddocumentosustitutivosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad fechacaducidaddocumentosustitutivosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFECHACADUCIDADDOCUMENTOSUSTITUTIVOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.fechacaducidaddocumentosustitutivosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad indefinidosegundocotitulartransmitente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE() {
        return indefinidosegundocotitulartransmitente;
    }

    /**
     * Define el valor de la propiedad indefinidosegundocotitulartransmitente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setINDEFINIDOSEGUNDOCOTITULARTRANSMITENTE(String value) {
        this.indefinidosegundocotitulartransmitente = value;
    }

    /**
     * Obtiene el valor de la propiedad numerotitulares.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNUMEROTITULARES() {
        return numerotitulares;
    }

    /**
     * Define el valor de la propiedad numerotitulares.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNUMEROTITULARES(String value) {
        this.numerotitulares = value;
    }

}
