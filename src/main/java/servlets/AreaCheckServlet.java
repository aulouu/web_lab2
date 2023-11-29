package servlets;

import beans.Hit;
import beans.Results;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@WebServlet(name = "areaCheck", value = "/areaCheck")
public class AreaCheckServlet extends HttpServlet {
    private static final double X_MIN = -5, X_MAX = 3;
    private static final double Y_MIN = -3, Y_MAX = 3;
    private static final double R_MIN = 1, R_MAX = 5;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String xStr = req.getParameter("x");
        String yStr = req.getParameter("y");
        String rStr = req.getParameter("r");

        double y;
        double r;

        try {
            y = Double.parseDouble(yStr);
            r = Double.parseDouble(rStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Wrong type arguments!");
        }

        // Разбиваем строку xStr на массив значений xValues
        String[] xValues = xStr.split(",");

        Results results = (Results) req.getSession().getAttribute("results");
        if (results == null) {
            results = new Results();
        }

        long startTime = System.nanoTime();
        // Проходим по каждому значению x
        for (String xValue : xValues) {
            double x;

            try {
                x = Double.parseDouble(xValue);
            } catch (NumberFormatException e) {
                // Обработка ошибок, если значение x не является числом
                throw new RuntimeException("Invalid value for x: " + xValue);
            }

            Hit hit = new Hit();
            hit.setX(x);
            hit.setY(y);
            hit.setR(r);
            hit.setResult(isHit(x, y, r));
            hit.setCurrentTime(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss", Locale.forLanguageTag("RU"))));
            DecimalFormat df = new DecimalFormat("#.###");
            hit.setExecutionTime("" + (System.nanoTime() - startTime) * 0.001);
            results.addHit(hit);
        }

        req.getSession().setAttribute("results", results);
        resp.sendRedirect(req.getContextPath() + "/result.jsp");
    }

    private boolean isHit (double x, double y, double r){
            if (x > X_MAX || x < X_MIN) {
                return false;
            }
            if (y > Y_MAX || y < Y_MIN) {
                return false;
            }
            if (r > R_MAX || r < R_MIN) {
                return false;
            }
            return isCircle(x, y, r) || isRectangle(x, y, r) || isTriangle(x, y, r);
        }

        private boolean isRectangle ( double x, double y, double r){
            return y >= -r / 2 && x >= -r && x <= 0 && y <= 0;
        }

        private boolean isCircle ( double x, double y, double r){
            return x * x + y * y <= r * r && x >= 0 && y >= 0;
        }

        private boolean isTriangle ( double x, double y, double r){
            return x <= 0 && y >= 0 && y <= 2 * x + r;
        }

}