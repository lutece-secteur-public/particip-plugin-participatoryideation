<jsp:useBean id="manageideationIdee" scope="session" class="fr.paris.lutece.plugins.participatoryideation.web.IdeeJspBean" />
<% String strContent = manageideationIdee.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>