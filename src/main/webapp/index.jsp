<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.ParseException" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Point Check</title>
    <link rel="icon" href="images/icon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="Styles/styles.css">
    <script src="js/graph.js"></script>
    <script src="js/validator.js"></script>
    <script>
        function validateForm() {
            // Получаем значение Y из формы
            var yValue = document.getElementById("y").value;
            var xValue = document.getElementById("x").valueOf()
            // Возвращаем результат валидации
            return validateY(yValue);
        }


    window.onload = function () {
            drawGraph();
        };
        function handleClick(event) {
            alert("Click coordinates: X = " + event.clientX + ", Y = " + event.clientY);
        }
    </script>
</head>
<body>
<header>
    <h1>Лабораторная работа № 2</h1>
    <p>Состанов Тимур Айратович</p>
    <p>Группа P3214</p>
    <p>Вариант 2468</p>
</header>

<form action="${pageContext.request.contextPath}/ControllerServlet" method="post" onsubmit="return validateForm()">
    <fieldset>
        <legend>X-координата:</legend>
        <%
            for (int i = -5; i <= 3; i++) {
        %>
        <input type="checkbox" id="x<%= i %>" name="x" value="<%= i %>">
        <label for="x<%= i %>"><%= i %></label>
        <%
            }
        %>
    </fieldset>
    <br>

    <label for="y">Y-координата:</label>
    <input type="text" id="y" name="y" required>
    <br>

    <label for="radius">Радиус области:</label>
    <select id="radius" name="radius" required>
        <%
            for (int i = 1; i <= 5; i++) {
        %>
        <option value="<%= i %>"><%= i %></option>
        <%
            }
        %>
    </select>
    <br>
    <br>

    <input type="submit" value="Проверить">
</form>

<div>
    <canvas id="canvas" width="400" height="400" onclick="handleClick(event)"></canvas>
</div>

<table border="1">
    <caption>Результаты предыдущих проверок</caption>
    <tr>
        <th>X-координата</th>
        <th>Y-координата</th>
        <th>Радиус</th>
        <th>Результат</th>
        <th>Время начала</th>
        <th>Время выполнения (мс)</th>
    </tr>
    <%
        beans.PointsArray pointsArray = (beans.PointsArray) request.getSession().getAttribute("pointsArray");
        if (pointsArray != null) {
            java.util.List<beans.Point> points = pointsArray.getPoints();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (beans.Point point : points) {
                String startTimeStr = point.getTime();
                long endTime = (point.getScriptTime() != 0) ? point.getScriptTime() : 0;
                Date startTime = null;

                try {
                    startTime = dateFormat.parse(startTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long executionTime = (startTime != null) ? (endTime - startTime.getTime()) : 0;
    %>
    <tr>
        <td><%= point.getX() %></td>
        <td><%= point.getY() %></td>
        <td><%= point.getRadius() %></td>
        <td><%= point.getStatus() ? "Попадание" : "Промах" %></td>
        <td><%= (startTime != null) ? dateFormat.format(startTime) : "" %></td>
        <td><%= executionTime %></td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
