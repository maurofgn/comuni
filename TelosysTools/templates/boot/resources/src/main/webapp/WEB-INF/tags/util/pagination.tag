<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="thispage"
	type="org.springframework.data.domain.Page" required="true"
	rtexprvalue="true" description="The current Page"%>

<c:set var="pageNumber" value="${thispage.number}" />
<c:set var="maxPages" value="${thispage.totalPages}" />
<c:set var="size" value="${thispage.size}" />
<spring:url value="/images/resultset_first.png" var="first_image_url" />
<spring:url value="/images/resultset_previous.png" var="previous_image_url" />
<spring:url value="/images/resultset_next.png" var="next_image_url" />
<spring:url value="/images/resultset_last.png" var="last_image_url" />

<div class="span12">
	<ul class="pager text-right">
		<c:choose>
			<c:when test="${pageNumber ne 0}">
				<spring:url value="" var="first">
					<spring:param name="page" value="0" />
					<spring:param name="size" value="${size}" />
				</spring:url>
				<spring:message code="list_first" var="first_label" text="First" htmlEscape="false" />
				<li><a href="${first}"><img alt="${fn:escapeXml(first_label)}" src="${first_image_url}"></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><img alt="${fn:escapeXml(first_label)}" src="${first_image_url}"></a></li>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${pageNumber gt 0}">
				<spring:url value="" var="previous">
					<spring:param name="page" value="${pageNumber - 1}" />
					<spring:param name="size" value="${size}" />
				</spring:url>
				<spring:message code="list_previous" var="previous_label" text="Previous" htmlEscape="false" />
				<li><a href="${previous}"><img alt="${fn:escapeXml(previous_label)}" src="${previous_image_url}"></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><img alt="${fn:escapeXml(previous_label)}" src="${previous_image_url}" ></a></li>
				
			</c:otherwise>
		</c:choose>
		
		<c:out value=" " />
		<spring:message code="list_page" text="Page {0} of {1}" arguments="${pageNumber + 1},${maxPages}" argumentSeparator="," />
		<c:out value=" " />
		
		<c:choose>
			<c:when test="${pageNumber lt (maxPages-1)}">
				<spring:url value="" var="next">
					<spring:param name="page" value="${pageNumber + 1}" />
					<spring:param name="size" value="${size}" />
				</spring:url>
				<spring:message code="list_next" var="next_label" text="Next" htmlEscape="false" />
				<li><a href="${next}"><img alt="${fn:escapeXml(next_label)}" src="${next_image_url}"></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><img alt="${fn:escapeXml(next_label)}" src="${next_image_url}"></a></li>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${pageNumber ne (maxPages-1)}">
				<spring:url value="" var="last">
					<spring:param name="page" value="${maxPages-1}" />
					<spring:param name="size" value="${size}" />
				</spring:url>
				<spring:message code="list_last" var="last_label" text="Last" htmlEscape="false" />
				<li><a href="${last}"><img alt="${fn:escapeXml(last_label)}" src="${last_image_url}"></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><img alt="${fn:escapeXml(last_label)}" src="${last_image_url}"></a></li>
			</c:otherwise>
		</c:choose>
		
	</ul>
	
	<spring:message code="list_size" var="list_size" text="Records per page" htmlEscape="false" />
	<c:out value="${list_size} " />
	<c:forEach var="i" begin="5" end="25" step="5">
		<c:choose>
			<c:when test="${size == i}">
				<c:out value="${i}" />
			</c:when>
			<c:otherwise>
				<spring:url value="" var="sizeUrl">
					<spring:param name="page" value="0" />
					<spring:param name="size" value="${i}" />
				</spring:url>
				<a href="${sizeUrl}">${i}</a>
			</c:otherwise>
		</c:choose>
<%-- 		<c:out value=" " /> --%>
	</c:forEach>
</div>
