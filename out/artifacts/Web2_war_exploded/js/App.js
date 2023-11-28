import Validator from "./Validator.js";

export default class App {
    constructor(graph) {
        this.graph = graph;
        this.submitButton = document.querySelector("input[type=submit]");
        this.resetButton = document.querySelector("input[type=reset]");
        this.table = document.getElementById('table-body');
        this.submitButton.disabled = true;
        this.svgElem = document.querySelector("svg");
        this.selectedXValues = [];
    }

    init() {
        const submitBtn = this.submitButton;
        document.addEventListener('DOMContentLoaded', () => {
            this.graph.restoreDots();
            const yInput = document.getElementById('y');
            const xCheckboxes = document.querySelectorAll("input[name='x']");

            xCheckboxes.forEach((checkbox) => {
                checkbox.addEventListener('change', () => {
                    if (checkbox.checked) {
                        this.selectedXValues.push(checkbox.value);
                    } else {
                        const index = this.selectedXValues.indexOf(checkbox.value);
                        if (index !== -1) {
                            this.selectedXValues.splice(index, 1);
                        }
                    }
                    submitBtn.disabled = !Validator.isValidX(this.selectedXValues);
                });
            });

            yInput.addEventListener('input', function () {
                const yValue = parseFloat(yInput.value.replace(',', '.')); // Заменим запятую на точку и преобразуем в число
                submitBtn.disabled = !Validator.isValidY(yValue);
                if (submitBtn.disabled || isNaN(yValue)) {
                    yInput.setCustomValidity("Enter a valid number between -3 and 3");
                    yInput.reportValidity();
                } else {
                    yInput.setCustomValidity("");
                    yInput.reportValidity();
                }
            });

        });

        this.submitButton.addEventListener('click', (event) => {
            event.preventDefault();
            const y = parseFloat(document.getElementById('y').value.replace(',', '.')); // Заменим запятую на точку и преобразуем в число
            const r = document.getElementById('r').value;

            // Use this.selectedXValues array instead of xInput.value
            const xValues = this.selectedXValues;

            this.graph.drawDot(xValues, y, r, false, 100 / r);

            const formData = new FormData();
            formData.append("y", y);
            formData.append("r", r);
            formData.append("x", xValues.join(","));

            fetch("/Web2-1.0-SNAPSHOT/controller", {
                method: "POST",
                body: formData,
            })
                .then((response) => response.text())
                .then((data) => {
                    document.write(data);
                });
        });

        this.resetButton.addEventListener('click', (event) => {
            event.preventDefault();
            this.table.innerHTML = '';
        });

        this.svgElem.addEventListener('click', (event) => {
            this.graph.svgPoint.x = event.clientX;
            this.graph.svgPoint.y = event.clientY;
            const point = this.graph.svgPoint.matrixTransform(document.querySelector('svg')
                .getScreenCTM()
                .inverse());
            const r = document.getElementById("r");
            const valOfDiv = 100 / r.value;
            this.graph.drawDot([point.x.toString()], point.y, r.value, true, valOfDiv);

            const formData = new FormData();
            formData.append("x", `${((point.x - 150) / valOfDiv).toFixed(2)}`);
            formData.append("y", `${((150 - point.y) / valOfDiv).toFixed(2)}`);
            formData.append("r", r.value);

            fetch("/Web2-1.0-SNAPSHOT/controller", {
                method: "POST",
                body: formData,
            })
                .then((response) => response.text())
                .then((data) => {
                    document.write(data);
                });
        });

    }
}