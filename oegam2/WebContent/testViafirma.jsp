<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.viafirma.cliente.ViafirmaClient"%>
<%@page import="org.viafirma.cliente.ViafirmaClientFactory"%>
<%@page import="org.viafirma.cliente.firma.TypeFile"%>
<%@page import="org.viafirma.cliente.firma.TypeFormatSign"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
	<%
		String url = "http://192.168.50.13/viafirma";
		ViafirmaClientFactory.init(url, url);
		ViafirmaClient client = ViafirmaClientFactory.getInstance();

	out.println("<html><body>");
		if (request.getParameter("all") != null) {
			for (int i = 0; i < 540; i++) {

				String num = "";
				if (i < 10) {
					num = "00" + i;
				} else if (i < 100) {
					num = "0" + i;
				} else {
					num = "" + i;
				}

				String clave = "Clave" + num;

				try {
					String idSign = client
							.signByServerWithTypeFileAndFormatSign(
									"kk.txt", "TEST".getBytes(), clave,
									"", TypeFile.TXT,
									TypeFormatSign.XADES_T_ENVELOPED);

					out.println("<p>");
					out.println(clave + "\n\n");
					out.println(idSign);
					out.flush();
				} catch (Exception e) {
					out.println("<p style=\"background-color:yellow\">");
					System.out.println("Clave:" + clave);
					out.println(clave + "\n\n");

					out.println(e.getMessage());
					System.out.println("Clave:" + clave + " "
							+ e.getMessage());
					out.flush();
				}
				out.println("</p><hr/>");
			}

		}
		out.println("</body></html>");
	%>
	</body>
</html>