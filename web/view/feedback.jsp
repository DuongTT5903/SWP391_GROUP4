<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Feedback</title>
    </head>
    <body>
        <h1>Give Feedback</h1>

        <!-- Sidebar với service search, categories, links... -->
        <div id="sidebar">
            <!-- search box -->
            <!-- categories -->
            <!-- static contacts/links -->
        </div>

        <div id="mainContent">
            <form action="<%= request.getContextPath()%>/feedback" method="post">
                <!-- Nếu feedback cho 1 dịch vụ cụ thể -->
                <%
                    String serviceIDParam = request.getParameter("serviceID");
                    if (serviceIDParam != null) {
                %>
                <input type="hidden" name="serviceID" value="<%= serviceIDParam%>" />
                <%
                    }
                %>

                <label for="feedbackDetail">Your Feedback:</label><br>
                <textarea name="feedbackDetail" rows="4" cols="50"></textarea><br>

                <label for="rated">Rating (1-5):</label>
                <select name="rated">
                    <option value="1">1 Star</option>
                    <option value="2">2 Stars</option>
                    <option value="3">3 Stars</option>
                    <option value="4">4 Stars</option>
                    <option value="5">5 Stars</option>
                </select><br>

                <label for="imgLink">Image Link:</label>
                <input type="text" name="imgLink" /><br>

                <input type="submit" value="Send Feedback" />
            </form>
        </div>
    </body>
</html>
