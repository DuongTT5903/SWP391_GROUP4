<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
         <jsp:include page="./staffHeader.jsp" />
        <title>Medical Examination History</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                margin: 0;
                padding: 20px;
                background-color: #f5f7fa;
            }
            .container {
                max-width: 1200px;
                margin: 0 auto;
                background: white;
                padding: 25px;
                border-radius: 8px;
                box-shadow: 0 2px 15px rgba(0,0,0,0.1);
            }
            h1 {
                color: #2c3e50;
                margin-bottom: 25px;
                padding-bottom: 10px;
                border-bottom: 2px solid #3498db;
            }
            .filter-container {
                background: #f8f9fa;
                padding: 20px;
                border-radius: 8px;
                margin-bottom: 25px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }
            .filter-form {
                display: flex;
                flex-wrap: wrap;
                align-items: flex-end;
                gap: 20px;
            }
            .filter-group {
                flex: 1;
                min-width: 220px;
            }
            .filter-group label {
                display: block;
                margin-bottom: 8px;
                font-weight: 600;
                color: #2c3e50;
            }
            .filter-control {
                width: 100%;
                padding: 10px;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 14px;
                height: 40px;
                box-sizing: border-box;
            }
            .filter-actions {
                display: flex;
                align-items: center;
                height: 40px;
                margin-bottom: 5px;
            }
            .btn {
                padding: 10px 20px;
                border-radius: 4px;
                cursor: pointer;
                font-weight: 600;
                font-size: 14px;
                height: 40px;
                display: inline-flex;
                align-items: center;
                justify-content: center;
            }
            .btn-primary {
                background-color: #3498db;
                color: white;
                border: none;
                transition: background-color 0.3s;
            }
            .btn-primary:hover {
                background-color: #2980b9;
            }
            .btn-edit {
                background-color: #f39c12;
                color: white;
                border: none;
                transition: background-color 0.3s;
            }
            .btn-edit:hover {
                background-color: #e67e22;
            }
            .btn-save {
                background-color: #2ecc71;
                color: white;
                border: none;
                transition: background-color 0.3s;
                display: none;
            }
            .btn-save:hover {
                background-color: #27ae60;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                box-shadow: 0 1px 3px rgba(0,0,0,0.1);
            }
            th, td {
                padding: 15px;
                text-align: left;
                border-bottom: 1px solid #ecf0f1;
            }
            th {
                background-color: #2c3e50;
                color: white;
                font-weight: 600;
            }
            tr:nth-child(even) {
                background-color: #f8f9fa;
            }
            tr:hover {
                background-color: #f1f7fd;
            }
            .action-link {
                color: #3498db;
                text-decoration: none;
                font-weight: 500;
                transition: color 0.3s;
                margin-right: 10px;
            }
            .action-link:hover {
                color: #21618c;
                text-decoration: underline;
            }
            .no-data {
                text-align: center;
                padding: 20px;
                color: #7f8c8d;
                font-style: italic;
            }
            .prescription-text {
                display: inline-block;
                max-width: 300px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            .prescription-edit {
                width: 100%;
                padding: 8px;
                border: 1px solid #ddd;
                border-radius: 4px;
                resize: vertical;
                min-height: 60px;
                display: none;
            }
            .action-buttons {
                display: flex;
                gap: 10px;
            }
            @media (max-width: 768px) {
                .filter-form {
                    flex-direction: column;
                }
                .filter-group {
                    width: 100%;
                }
                .filter-actions {
                    width: 100%;
                }
                .btn {
                    width: 100%;
                }
                .action-buttons {
                    flex-direction: column;
                }
            }
        </style>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h1>Medical Examination History</h1>

            <div class="filter-container">
                <form method="get" class="filter-form">
                    <div class="filter-group">
                        <label for="filter-date">Date</label>
                        <input type="date" id="filter-date" name="date" value="${param.date}" class="filter-control">
                    </div>
                    <div class="filter-group">
                        <label for="filter-service">Service</label>
                        <select id="filter-service" name="serviceId" class="filter-control">
                            <option value="">All Services</option>
                            <c:forEach items="${services}" var="sv">
                                <option value="${sv.serviceID}" 
                                        ${param.serviceId eq sv.serviceID.toString() ? 'selected' : ''}>
                                    ${sv.serviceName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="filter-group">
                        <label for="filter-medicine">Medicine</label>
                        <input type="text" id="filter-medicine" name="medicineName" value="${param.medicineName}" 
                               class="filter-control" placeholder="Enter medicine name">
                    </div>
                    <div class="filter-actions">
                        <button type="submit" class="btn btn-primary">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" viewBox="0 0 16 16" style="margin-right: 8px;">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                            </svg>
                            Filter
                        </button>
                    </div>
                </form>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Customer</th>
                        <th>Date</th>
                        <th>Services</th>
                        <th>Prescription</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty examinations}">
                            <tr>
                                <td colspan="6" class="no-data">No examinations found matching your criteria</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${examinations}" var="exam">
                                <tr id="row-${exam.examinationId}">
                                    <td>${exam.examinationId}</td>
                                    <td>${exam.reservation.customerName}</td>
                                    <td><fmt:formatDate value="${exam.creationDate}" pattern="yyyy-MM-dd"/></td>
                                    <td>
                                        <c:forEach items="${exam.services}" var="s" varStatus="loop">
                                            • ${s.serviceName}<c:if test="${!loop.last}"><br></c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <div class="prescription-container">
                                            <span class="prescription-text" id="prescription-${exam.examinationId}">
                                                <c:choose>
                                                    <c:when test="${not empty exam.prescription}">
                                                        ${fn:length(exam.prescription) > 30 ? 
                                                          fn:substring(exam.prescription, 0, 30) += '...' : exam.prescription}
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                            <textarea class="prescription-edit" id="edit-${exam.examinationId}">${exam.prescription}</textarea>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="detail?id=${exam.examinationId}" class="action-link">View</a>
                                            <button class="btn btn-edit" data-id="${exam.examinationId}">Edit</button>
                                            <button class="btn btn-save" data-id="${exam.examinationId}">Save</button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <script>
            $(document).ready(function () {
                // Handle Edit button
                $('.btn-edit').click(function () {
                    var examId = $(this).data('id');
                    $('#prescription-' + examId).hide();
                    $('#edit-' + examId).show().focus();
                    $(this).hide();
                    $('.btn-save[data-id="' + examId + '"]').show();
                });

                // Handle Save button
                $('.btn-save').click(function () {
                    var examId = $(this).data('id');
                    var newPrescription = $('#edit-' + examId).val();

                    $.ajax({
                        url: '${pageContext.request.contextPath}/staff/examination/edit',
                        type: 'POST',
                        data: {
                            id: examId,
                            prescription: newPrescription
                        },
                        success: function (response) {
                            // Update UI
                            var displayText = newPrescription.length > 30 ?
                                    newPrescription.substring(0, 30) + '...' : newPrescription;
                            if (!displayText)
                                displayText = '-';

                            $('#prescription-' + examId).text(displayText);
                            $('#prescription-' + examId).show();
                            $('#edit-' + examId).hide();
                            $('.btn-save[data-id="' + examId + '"]').hide();
                            $('.btn-edit[data-id="' + examId + '"]').show();

                            alert('Prescription updated successfully!');
                        },
                        error: function (xhr) {
                            alert('Error updating prescription: ' + xhr.responseText);
                        }
                    });
                });
            });
        </script>
        <div class="text-center mt-4">
            <a href="${pageContext.request.contextPath}/staff/reservationlist" class="btn btn-secondary">Back to Reservation List</a>
        </div>
    </body>
</html>