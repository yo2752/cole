<%@ taglib prefix="s" uri="/struts-tags"%>

<br />
<br />
<br />
<br />
<br />

<s:form action="aceptacionServicioConsultaNotificacion.action">
<div class="rbroundbox">
	<div class="rbtop">
		<div></div>
	</div>
	<div class="rbcontent" style="text-align: center;">
		<br> <br>
		<table style="text-align: left;">
			<tr>
				<td colspan="2">
					<s:if test="hasActionErrors()">
						<!--<s:actionerror cssStyle="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;"/>-->
						<ul style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
							<s:iterator value="actionErrors">
								<li><span><s:property /></span></li>							
							</s:iterator>
						</ul>
					</s:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					El ICOGAM pone en su conocimiento que esta Corporaci�n est�
					configurada como administrador de la plataforma telem�tica OEGAM,
					consultando como tal los avisos de las distintas notificaciones que
					la Tesorer�a General de la Seguridad Social emite a sus distintos
					colegiados, previamente se han apoderado con autorizaci�n de sus
					clientes.
					<br /> <br />
					Del mismo modo, el ICOGAM como administrador que es,
					�nicamente es responsable de informar de la existencia de dichas
					notificaciones, sin poder acceder al contenido de las mismas bajo
					ning�n concepto, ni que se puedan considerar como le�das.
					<br /> <br />
					Por ello, la decisi�n de rehusar la notificaci�n o
					aceptar la misma, con el consiguiente acceso, rectificaci�n,
					cancelaci�n y oposici�n a la informaci�n (Ley 15/1999) que en ella
					contiene, es decisi�n exclusiva del colegiado al que le es
					dirigida, sin poder exigir al ICOGAM responsabilidad alguna.
				</td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td width="5em">
					<s:checkbox name="aceptaServicio" id="checkAceptarServicio" onclick="checkAceptado()"></s:checkbox>
				</td>
				<td>
					<s:label for="checkAceptarServicio">He le&iacute;do y acepto los t&eacute;rminos y condiciones</s:label>
				</td>
			</tr>
		</table>

		<br /> <br />
			<input type="submit" class="boton" name="continuar"
			id="continuar" value="Continuar" disabled="disabled" style="color: #6E6E6E;" />
		<br /> <br />
	</div>
	<!-- /rbcontent -->
	<div class="rbbot">
		<div></div>
	</div>
</div>
</s:form>
<script type="text/javascript">
	function checkAceptado(aceptado) {
	    if ($('#checkAceptarServicio').is(':checked')) {
            $('#continuar').removeAttr('disabled').css("color", "#000");;
        } else {
            $('#continuar').attr('disabled', 'disabled').css("color", "#6E6E6E");
        }

	}
</script>