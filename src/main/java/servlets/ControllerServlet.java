package servlets;

import beans.Hit;
import beans.Results;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


@MultipartConfig
@WebServlet(name = "controller", value = "/controller")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
//        String command = req.getParameter("command");
//        if (command == "clear") {
//            context.setAttribute("results", new Results());
//        }
        Results results = (Results) context.getAttribute("results");

        if (results == null) {
            results = new Results();
            context.setAttribute("results", results);
        }

        // Получаем параметры из объекта FormData
        Map<String, String[]> parameterMap = req.getParameterMap();
        String[] xValues = parameterMap.get("x");
        String yValue = parameterMap.get("y")[0];
        String rValue = parameterMap.get("r")[0];

        // Переводим массив строк X в строку с разделителями, например, запятыми
        String xStr = String.join(",", xValues);

        long startTime = System.nanoTime();
        results.setStartTime(startTime);  // Устанавливаем время начала в объекте Results

        req.setAttribute("results", results);

        double x = Double.parseDouble(Arrays.toString(xValues));
        double y = Double.parseDouble(yValue);
        double r = Double.parseDouble(rValue);
        if (xStr == null || yValue == null || rValue == null || xStr.isEmpty() || yValue.isEmpty() || rValue.isEmpty() || y > 3 || y < -3 || Double.isNaN(y) || yValue.length() > 14 || r > 5 || r < 1 || x > 3 || x < -5) {
            req.getSession().setAttribute("results", results);
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
            return;
        }

        req.setAttribute("startTime", startTime);
        req.setAttribute("results", results);
        context.getRequestDispatcher("/areaCheck").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Results results = (Results) context.getAttribute("results");

        if (results == null) {
            results = new Results();
            context.setAttribute("results", results);
        }

        req.setAttribute("results", results);
        getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}