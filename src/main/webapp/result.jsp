<%@ page import="java.util.List" %>
<%@ page import="beans.Hit" %>
<%@ page import="beans.Results" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="styles/style.css">
    <title>Web Lab №2</title>
</head>
<body>
<%
    Results results = (Results)request.getAttribute("results");
    List<Hit> hits = results.getResults();

    // Проверка на наличие результатов
    if (!hits.isEmpty()) {
        // Определение последнего времени начала
        String lastTime = hits.get(hits.size() - 1).getCurrentTime();
%>
<td id="table" rowspan="2">
    <div class="top">
        <h2>Table</h2>
    </div>
    <div id="onetable">
        <table id="restable">
            <thead>
            <th class="column-type1">X</th>
            <th class="column-type1">Y</th>
            <th class="column-type1">R</th>
            <th class="column-type2">Hit fact</th>
            <th class="column-type3">Current time</th>
            <th class="column-type3">Execution time</th>
            </thead>
            <tbody>
            <%
                // Вывод всех записей с последним временем начала
                for (Hit hit : hits) {
                    if (lastTime.equals(hit.getCurrentTime())) {
            %>
            <tr style="background-color: white">
                <th><%= hit.getX() %></th>
                <th><%= hit.getY() %></th>
                <th><%= hit.getR() %></th>
                <th><%= hit.isHit() %></th>
                <th><%= hit.getCurrentTime() %></th>
                <th><%= hit.getExecutionTime() %></th>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
</td>
<a href="index.jsp" class="button">Exit</a>
<%
} else {
%>
<p>No results available.</p>
<%
    }
%>
</body>
</html>