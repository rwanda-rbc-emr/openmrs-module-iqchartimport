<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localInclude.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
    $('#encounters').dataTable({"aaSorting": []});
    
	$('#obsDialog').jqm({ajax: '@href', trigger: 'a.obsDialogLink'});
});
</script>

<div class="jqmWindow" id="obsDialog">
	Please wait...
</div>

<div style="margin-bottom: 20px">
	<input type="button" value="<spring:message code="general.back" />" onclick="location.href='preview.form'" />
</div>
		
<b class="boxHeader">
	<spring:message code="@MODULE_ID@.patient.details" />
</b>
<div class="box" style="margin-bottom: 20px">
	<table width="100%" cellspacing="0">
		<c:if test="${patient.dead}">
			<tr>
				<td colspan="2" style="font-weight: bold; color: red; text-align: center"><spring:message code="Patient.patientDeceased" /></td>
			</tr>
		</c:if>
		<tr>
			<td style="font-weight: bold"><spring:message code="@MODULE_ID@.general.tracnetID" /></td>
			<td>${patient.patientIdentifier.identifier}</td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="general.name" /></td>
			<td><c:out value="${patient.personName.familyName}" />, <c:out value="${patient.personName.givenName}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Person.birthdate" /></td>
			<td>
				<openmrs:formatDate date="${patient.birthdate}" />
				<c:if test="${patient.birthdateEstimated}">
					(<spring:message code="Person.birthdateEstimated" />)
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Patient.gender" /></td>
			<td>
				<c:choose>
					<c:when test="${patient.gender == 'M'}">
						<spring:message code="Patient.gender.male" />
					</c:when>
					<c:when test="${patient.gender == 'F'}">
						<spring:message code="Patient.gender.female" />
					</c:when>
				</c:choose>
			</td>
		</tr>
	</table>
</div>

<b class="boxHeader">
	<spring:message code="patientDashboard.overview" />
</b>
<div class="box" style="margin-bottom: 20px">
	<table width="100%" cellspacing="0">
		<tr>
			<td style="font-"></td>
			<td></td>
		</tr>
	</table>
</div>

<b class="boxHeader">
	<spring:message code="Program.header" />
</b>
<div class="box" style="margin-bottom: 20px">
	<table width="100%" cellspacing="0">
		<tr>
			<th><spring:message code="Program.program"/></th>
			<th><spring:message code="Program.dateEnrolled" /></th>
			<th><spring:message code="Program.dateCompleted" /></th>
			<th><spring:message code="Program.state" /></th>
		</tr>
		<c:forEach items="${patientPrograms}" var="program">
			<tr>
				<td><openmrs_tag:concept conceptId="${program.program.concept.conceptId}"/></td>
				<td><openmrs:formatDate date="${program.dateEnrolled}" type="medium" /></td>
				<td>			
					<c:choose>
						<c:when test="${not empty program.dateCompleted}">
							<openmrs:formatDate date="${program.dateCompleted}" type="medium" />
						</c:when>
						<c:otherwise>
							<i><spring:message code="Program.stillEnrolled"/></i>
						</c:otherwise>								
					</c:choose>
				</td>
				<td>&nbsp;</td>
			</tr>
		</c:forEach>
	</table>
</div>

<b class="boxHeader">
	<spring:message code="Encounter.header" />
</b>
<div class="box">
	<table id="encounters" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th><spring:message code="Encounter.datetime"/></th>
				<th><spring:message code="Encounter.type"/></th>
				<th><spring:message code="Encounter.location"/></th>
				<th><spring:message code="Encounter.enterer"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${encounters}" var="encounter">
				<tr>
					<td>
						<a href="observations.form?tracnetID=${patient.patientIdentifier.identifier}&amp;timestamp=${encounter.encounterDatetime.time}" class="obsDialogLink">
							<openmrs:formatDate date="${encounter.encounterDatetime}" type="small" />
						</a>
					</td>
					<td>${encounter.encounterType.name}</td>
					<td>${encounter.location.name}</td>
					<td>${encounter.creator.personName}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>