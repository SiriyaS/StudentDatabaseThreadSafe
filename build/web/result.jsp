<%-- 
    Document   : result
    Created on : Nov 7, 2021, 9:55:11 PM
    Author     : siriya_s
--%>

<%@page import="java.util.List"%>
<%@page import="model.Student"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Result</title>
        <style>
            table {
              border-collapse: collapse;
              width: 100%;
            }

            td, th {
              border: 1px solid #dddddd;
              text-align: left;
              padding: 8px;
            }

            tr:nth-child(even) {
              background-color: #dddddd;
            }
        </style>
    </head>
    <body>
        <h1>Student Database Manager</h1>
        <br><br>
        <em>Response message:</em>
        <br><br>
        <%out.println(request.getAttribute("message"));%>
        <br>
        
        <%List<Student> std = 
                (List<Student>)request.getAttribute("list");
        if(std != null) {%>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>GPA</th>
            </tr>
            <%for(Student s:std){%>
                <%-- Arranging data in tabular form
                --%>
                <tr>
                    <td><%out.print(s.getId());%></td>
                    <td><%out.print(s.getName());%></td>
                    <td><%out.print(s.getGPA());%></td>
                </tr>
            <%}%>
        </table>
        <%}%>
    </body>
</html>
