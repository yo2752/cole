package es.iam.sbae.sbintopexterna.latest;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

/**
 * This class was generated by Apache CXF 3.1.12
 * 2017-09-06T12:10:10.885+02:00
 * Generated source version: 3.1.12
 * 
 */
@WebService(targetNamespace = "http://sbintopexterna.sbae.iam.es", name = "InteroperabilidadExterna")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface InteroperabilidadExterna {

    @WebResult(name = "resultadoPeticion", targetNamespace = "http://sbintopexterna.sbae.iam.es", partName = "resultadoPeticion")
    @Action(input = "http://sbintopexterna.sbae.iam.es/InteroperabilidadExterna/realizaPeticionRequest", output = "http://sbintopexterna.sbae.iam.es/InteroperabilidadExterna/realizaPeticionResponse")
    @WebMethod
    public RespuestaPeticion realizaPeticion(
        @WebParam(partName = "peticionExterna", name = "peticionExterna", targetNamespace = "http://sbintopexterna.sbae.iam.es")
        PeticionExterna peticionExterna
    );
}
