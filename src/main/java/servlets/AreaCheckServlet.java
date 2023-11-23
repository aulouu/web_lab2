package servlets;

import beans.Point;
import beans.PointsArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Извлекаем объект PointsArray из атрибутов запроса
        PointsArray pointsArray = (PointsArray) request.getSession().getAttribute("pointsArray");

        // Проверяем, что PointsArray не равен null и содержит точки
        if (pointsArray != null && !pointsArray.getPoints().isEmpty()) {
            // Выполняем вычисления для каждой точки
            for (Point point : pointsArray.getPoints()) {
                boolean isInside = checkIfInsideArea(point);

                // Устанавливаем результаты в атрибуты запроса для передачи на JSP-страницу
                request.setAttribute("point", point);
                request.setAttribute("isInside", isInside);
            }
        } else {
            // Если PointsArray равен null или не содержит точек, устанавливаем значения по умолчанию
            request.setAttribute("point", new Point());
            request.setAttribute("isInside", false);
        }

        // Перенаправляем запрос на JSP-страницу для отображения результатов
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private boolean checkIfInsideArea(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getRadius();

        if (x >= 0 && y >= 0 && (x * x + y * y <= r * r)) {
            return true;
        } else if (x <= 0 && y >= 0 && (2 * x + r <= y)) {
            return true;
        } else if (x <= 0 && y <= 0 && (x >= -r && y >= (double) -r / 2)) {
            return true;
        } else if (x >= 0 && y <= 0 && (x <= r && y >= -r)) {
            return true;
        }
        return false;
    }

}
