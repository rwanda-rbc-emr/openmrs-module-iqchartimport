
<%-- Import spring form library with non default prefix to avoid clash in OpenMRS 1.7+ --%>
<%@ taglib prefix="sform" uri="http://www.springframework.org/tags/form" %>

<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />

<h2>
	<spring:message code="@MODULE_ID@.title" />
</h2>

<ul id="menu">
	<li class="first<c:if test='<%= request.getRequestURI().contains("upload") %>'> active</c:if>">
		<a href="upload.form"><spring:message code="@MODULE_ID@.menu.uploadDatabase"/></a>
	</li>
	<li <c:if test='<%= request.getRequestURI().contains("/preview") %>'>class="active"</c:if>>
		<a href="options.form"><spring:message code="@MODULE_ID@.menu.options"/></a>
	</li>
</ul>
