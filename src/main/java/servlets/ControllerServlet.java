package servlets;

import beans.Point;
import beans.PointsArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Этот метод может быть пустым, но он должен присутствовать
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Извлекаем параметры запроса
        String[] xValues = request.getParameterValues("x");
        String yValue = request.getParameter("y");
        String radiusValue = request.getParameter("radius");

        // Проверяем, содержатся ли параметры запроса
        if (xValues != null && yValue != null && radiusValue != null) {
            try {
                // Получаем или создаем объект PointsArray
                PointsArray pointsArray = (PointsArray) request.getSession().getAttribute("pointsArray");
                if (pointsArray == null) {
                    pointsArray = new PointsArray();
                    request.getSession().setAttribute("pointsArray", pointsArray);
                }

                // Добавляем каждую точку в PointsArray
                for (String xValue : xValues) {
                    double x = Double.parseDouble(xValue);
                    double y = Double.parseDouble(yValue);
                    double radius = Double.parseDouble(radiusValue);

                    Point point = new Point(x, y, radius);
                    pointsArray.addPoint(point);
                }

                // Передаем PointsArray в AreaCheckServlet
                request.getRequestDispatcher("/AreaCheckServlet").forward(request, response);
            } catch (NumberFormatException e) {
                // Обработка ошибок парсинга чисел
                e.printStackTrace(); // Лучше заменить на логирование
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format");
            }
        } else {
            // Если параметры отсутствуют, перенаправляем на страницу с веб-формой
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
