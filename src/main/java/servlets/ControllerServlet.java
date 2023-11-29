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
//            var userData = req.getServletContext().getAttribute("userData");
//            context.setAttribute("results", null);
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

        if (yValue == null || rValue == null || xStr.isEmpty() || yValue.isEmpty() || rValue.isEmpty()) {
            req.setAttribute("results", results);
            context.getRequestDispatcher("/error.jsp").forward(req, resp);
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