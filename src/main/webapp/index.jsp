<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="styles/style.css">
    <link rel="icon" href="images/favicon.ico">
    <title>Web Lab №2</title>
</head>

<body>
<table id="page">
    <tbody>
    <tr>
        <th id="head" colspan="2">
            Minkova Alina <br>
            Sostanov Timur <br>
            Group P3214, Variant 2468
        </th>
    </tr>
    <tr>
        <td id="graph">
            <div class="top">
                <h2>Graph</h2>
            </div>
            <div id="image">
                <svg width="300" height="300" xmlns="http://www.w3.org/2000/svg">
                    <line stroke="#000720" x1="0" x2="300" y1="150" y2="150"></line>
                    <line stroke="#000720" x1="150" x2="150" y1="0" y2="300"></line>
                    <polygon points="150,0 144,15 156,15" fill="#cc97ff" stroke="#c282ff"></polygon>
                    <polygon points="300,150 285,156 285,144" fill="#cc97ff" stroke="#c282ff"></polygon>

                    <line stroke="#000720" x1="200" x2="200" y1="155" y2="145"></line>
                    <line stroke="#000720" x1="250" x2="250" y1="155" y2="145"></line>

                    <line stroke="#000720" x1="50" x2="50" y1="155" y2="145"></line>
                    <line stroke="#000720" x1="100" x2="100" y1="155" y2="145"></line>

                    <line stroke="#000720" x1="145" x2="155" y1="100" y2="100"></line>
                    <line stroke="#000720" x1="145" x2="155" y1="50" y2="50"></line>

                    <line stroke="#000720" x1="145" x2="155" y1="200" y2="200"></line>
                    <line stroke="#000720" x1="145" x2="155" y1="250" y2="250"></line>


                    <text x="195" y="140">R/2</text>
                    <text x="248" y="140">R</text>

                    <text x="40" y="140">-R</text>
                    <text x="90" y="140">-R/2</text>

                    <text x="160" y="105">R/2</text>
                    <text x="160" y="55">R</text>

                    <text x="160" y="205">-R/2</text>
                    <text x="160" y="255">-R</text>

                    <text x="160" y="10">Y</text>
                    <text x="290" y="140">X</text>

                    <polygon fill="#cc97ff"
                             stroke="#c282ff"
                             fill-opacity="0.5"
                             points="150,150 150,200 50,200 50,150"></polygon>


                    <polygon fill="#cc97ff"
                             stroke="#c282ff"
                             fill-opacity="0.5"
                             points="150,150 100,150 150,50"></polygon>


                    <path d="M 150 50 A 150 150, 90, 0, 1, 250 150 L 150 150 Z"
                          fill-opacity="0.5"
                          fill="#cc97ff"
                          stroke="#c282ff"></path>
                </svg>
            </div>
        </td>
        <td id="table" rowspan="2">
            <div class="top">
                <h2>Table</h2>
            </div>
            <div id="result-table">
                <table id="restable">
                    <thead>
                    <th class="column-type1">X</th>
                    <th class="column-type1">Y</th>
                    <th class="column-type1">R</th>
                    <th class="column-type2">Hit fact</th>
                    <th class="column-type3">Current time</th>
                    <th class="column-type3">Execution time</th>
                    </thead>
                    <tbody id="table-body">
                    <jsp:useBean id="results" scope="application" class="beans.Results"/>
                    <c:forEach var="hit" items="${results.getResults()}">
                        <tr>
                            <td class="table-result">${hit.getX()}</td>
                            <td class="table-result">${hit.getY()}</td>
                            <td class="table-result">${hit.getR()}</td>
                            <td class="table-result">${hit.isHit()}</td>
                            <td class="table-result">${hit.getCurrentTime()}</td>
                            <td class="table-result">${hit.getExecutionTime()}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td id="coordinates">
            <div class="top">
                <h2>Coordinates</h2>
            </div>
            <form id="inpform" action="" method="POST">
                <div id="input">
                    <table id="input-table">
                        <tbody>
                        <tr>
                            <td class="label">
                                <label>X:</label>
                            </td>
                            <td>
                                <div class="value">
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="-5"> -5
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="-4"> -4
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="-3"> -3
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="-2"> -2
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="-1"> -1
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="0"> 0
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="1"> 1
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="2"> 2
                                    </label>
                                    <label class="xbox-label">
                                        <input class="x" type="checkbox" name="x" value="3"> 3
                                    </label>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="label">
                                <label>Y:</label>
                            </td>
                            <td>
                                <input class="value" id="y" type="text" name="y"
                                       placeholder="from -3 to 3">
                            </td>
                        </tr>
                        <tr>
                            <td class="label">
                                <label>R:</label>
                            </td>
                            <td>
                                <div class="value">
                                    <select name="r" id="r">
                                        <option value="1" class="option">1</option>
                                        <option value="2" class="option">2</option>
                                        <option value="3" class="option">3</option>
                                        <option value="4" class="option">4</option>
                                        <option value="5" class="option">5</option>
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div id="buttons">
                                    <input class="button" id="validate_button" type="submit" value="Submit">
                                    <input class="button" id="reset_button" type="reset" value="Reset">
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </form>
        </td>
    </tr>
    <tr>
        <td class="end" colspan="2">
            ITMO, 2023
        </td>
    </tr>
    </tbody>
</table>
<script type="text/javascript"
        src="https://code.jquery.com/jquery-3.5.1.min.js"
        integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
        crossorigin="anonymous">
</script>
<script type=module src="${pageContext.request.contextPath}/js/Index.js"></script>
</body>

</html>