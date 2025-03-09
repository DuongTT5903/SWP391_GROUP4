<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>FeedbackDetail</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="css/style.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.css"
              integrity="sha512-NtU/Act0MEcVPyqC153eyoq9L+UHkd0s22FjIaKByyA6KtZPrkm/O5c5xzaia4pyCfReCS634HyQ7tJwKNxC/g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            #tablesample_length {
                display: none;
            }
        </style>
    </head>
    <body>
        <jsp:include page="./headermanager.jsp" />
        <div class="container">
            <h1 style="text-align: center; background-color: lightgray; width: 100%; position: relative;">Feedback Detail</h1>
            <div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Status
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="feedbackdetail?filters=&filterr=${param['filterr']}">All</a>
                        <a class="dropdown-item" href="feedbackdetail?filters=1&filterr=${param['filterr']}">Active</a>
                        <a class="dropdown-item" href="feedbackdetail?filters=0&filterr=${param['filterr']}">Inactive</a>
                    </div>
                </div>
                <div class="btn-group">
                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Rated Star
                    </button>
                    <div class="dropdown-menu">
                        <a class="dropdown-item" href="feedbackdetail?filterr=&filters=${param['filters']}">All</a>
                        <a class="dropdown-item" href="feedbackdetail?filterr=1&filters=${param['filters']}">1</a>
                        <a class="dropdown-item" href="feedbackdetail?filterr=2&filters=${param['filters']}">2</a>
                        <a class="dropdown-item" href="feedbackdetail?filterr=3&filters=${param['filters']}">3</a>
                        <a class="dropdown-item" href="feedbackdetail?filterr=4&filters=${param['filters']}">4</a>
                        <a class="dropdown-item" href="feedbackdetail?filterr=5&filters=${param['filters']}">5</a>
                    </div>
                </div>
            </div>

            <div style="width: 100%;">
                <table class="table-striped table-hover" cellspacing="0" style="width: 100%;font-size: 18px; padding: 5px;" id="tablesample">
                    <thead>
                        <tr style="cursor: pointer; font-size: 15px; border-bottom: 1px solid #ccc; text-align: center;">
                            <th>ID<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                            <th>Full Name</th>

                            <th>Service Name</th>
                            <th>Rated Star<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                            <th>Feedback</th>
                            <th>Image</th>
                            <th>Status<i class="fa fa-caret-down" aria-hidden="true"></i></th>
                            <th width="3%">Edit</th>
                            <th width="3%">Delete</th>
                            <th width="3%">Switch</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="f" items="${flist}">
                            <tr>
                                <td>${f.id}</td>
                                <td>${f.user.name}</td>

                                <td>${f.services.serviceName}</td>
                                <td>${f.rated}<i class="fa fa-star" style="color: red;" aria-hidden="true"></i></td>
                                <td>${f.feedbackDetail}</td>
                                <td><img data-toggle="modal" data-target="#Show${f.id}" style="cursor: pointer;" src="${f.imgLink}" width="200px"></td>
                                    <c:if test="${f.status}">
                                    <td><span class="btn btn-success" style="font-size: 15px">Active</span></td>
                                </c:if>
                                <c:if test="${!f.status}">
                                    <td><span class="btn btn-danger" style="font-size: 15px">Inactive</span></td>
                                </c:if>
                                <td><a class="btn btn-primary" data-toggle="modal" data-target="#EditModalUP${f.id}"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
                                <td><a class="btn btn-danger" href="feedbackdetail?action=delete&fid=${f.id}"><i class="fa fa-trash-o" aria-hidden="true"></i></a></td>
                                        <c:if test="${f.status}">
                                    <td><a class="btn btn-warning" href="feedbackdetail?action=switch&fid=${f.id}&fstatus=0">Inactive</a></td>
                                </c:if>
                                <c:if test="${!f.status}">
                                    <td><a class="btn btn-success" href="feedbackdetail?action=switch&fid=${f.id}&fstatus=1">Active</a></td>
                                </c:if>
                            </tr>

                        <div class="modal fade" id="Show${f.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Detail</h5>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <div class="form-group col-md-6">
                                                <h4>ID : ${f.id} </h4>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <h4>UserName : ${f.user.name} </h4>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Service Name:</label>
                                                <p>${f.services.serviceName}</p>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Creation date:</label>
                                                <p>${f.creationDate}</p>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Rated:</label>
                                                <p>${f.rated}</p>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Email:</label>
                                                <p>${f.user.email}</p>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Phone:</label>
                                                <p>${f.user.phone}</p>
                                            </div>
                                            <div class="form-group col-md-6">
                                                <label class="control-label">Status: </label>
                                                <c:if test="${f.status}">
                                                    <span class="label label-success" style="font-size: 15px;">Active</span>
                                                </c:if>
                                                <c:if test="${!f.status}">
                                                    <span class="label label-danger" style="font-size: 15px;">Inactive</span>
                                                </c:if>
                                            </div>
                                            <div class="form-group col-md-12">
                                                <label class="control-label">Content: </label>
                                                <p>${f.feedbackDetail}</p>
                                            </div>
                                            <div class="form-group col-md-12">
                                                <label class="control-label">Image:</label><br>
                                                <img src="${f.imgLink}" width="50%">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    </tbody>
                </table>

                <c:forEach var="f" items="${flist}">
                    <div class="modal fade" id="EditModalUP${f.id}" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="editModalLabel">Edit Feedback</h5>
                                </div>
                                <div class="modal-body">
                                    <form action="feedbackdetail" method="post">
                                        <input type="hidden" name="action" value="edit">
                                        <input type="hidden" name="fid" value="${f.id}">
                                        <div class="form-group">
                                            <label for="content">Content</label>
                                            <textarea class="form-control" id="content" name="content" rows="3">${f.feedbackDetail}</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label for="frate">Rated</label>
                                            <input type="number" class="form-control" id="frate" name="frate" value="${f.rated}" max="5">
                                        </div>
                                        <div class="form-group">
                                            <label for="fcreate">Creation Date</label>
                                            <input type="date" class="form-control" id="fcreate" name="fcreate" value="${f.creationDate}">
                                        </div>
                                        <div class="form-group">
                                            <label for="fstatus">Status</label><br>
                                            <input type="radio" name="fstatus" value="1" ${f.status ? 'checked' : ''}> Active
                                            <input type="radio" name="fstatus" value="0" ${!f.status ? 'checked' : ''}> Inactive
                                        </div>
                                        <button type="submit" class="btn btn-primary">Save</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="d-flex justify-content-between mt-3">
                <a href="${pageContext.request.contextPath}/homepage" class="btn btn-secondary">Back to Home</a>
            </div>
        </div>

        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.1/js/dataTables.bootstrap5.min.js"></script>
        <script>
            $(document).ready(function () {
                $("#tablesample").DataTable();
            });
        </script>

        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4xF86dIHNDz0W1" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </body>
</html>
