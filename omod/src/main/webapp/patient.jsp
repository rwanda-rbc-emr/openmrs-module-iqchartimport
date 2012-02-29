<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>

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
	<spring:message code="@MODULE_ID@.patient.programs" />
</b>
<div class="box" style="margin-bottom: 20px">
<%--
	<table width="100%" cellspacing="0">
		<tr>
			<td style="font-weight: bold" width="300"><spring:message code="Program.dateEnrolled" /></td>
			<td><openmrs:formatDate date="${patient.enrollDate}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="Program.dateCompleted" /></td>
			<td><openmrs:formatDate date="${patient.exitDate}" /></td>
		</tr>
		<tr>
			<td style="font-weight: bold"><spring:message code="@MODULE_ID@.patient.exitReason" /></td>
			<td>
				<c:choose>
					<c:when test="${patient.exitCode == 1}">Transferred</c:when>
					<c:when test="${patient.exitCode == 2}">Deceased</c:when>
					<c:when test="${patient.exitCode == 3}">Lost</c:when>
					<c:otherwise><c:out value="${patient.exitReasonOther}" /></c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
--%>
</div>

<b class="boxHeader">
	<spring:message code="@MODULE_ID@.patient.encounters" />
</b>
<div class="box">
<%--
	<table id="iqobs" width="100%" cellspacing="0">
		<thead>
			<tr>
				<th><spring:message code="general.dateCreated" /></th>
				<th><spring:message code="@MODULE_ID@.patient.obsType" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${patientObs}" var="obs" varStatus="status">
				<tr class="<c:choose><c:when test="${status.index % 2 == 0}">evenRow</c:when><c:otherwise>oddRow</c:otherwise></c:choose>">
					<td><openmrs:formatDate date="${obs.date}" /></td>
					<td>
						<c:choose>
							<c:when test="${obs['class'].simpleName == 'IQCD4Obs'}">CD4</c:when>
							<c:when test="${obs['class'].simpleName == 'IQHeightObs'}">Height</c:when>
							<c:when test="${obs['class'].simpleName == 'IQWeightObs'}">Weight</c:when>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
--%>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>