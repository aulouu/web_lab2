import Validator from "./Validator.js";

export default class App {
    constructor(graph) {
        this.graph = graph;
        this.submitButton = document.querySelector("input[type=submit]");
        this.resetButton = document.querySelector("input[type=reset]");
        this.table = document.getElementById('table-body');
        this.submitButton.disabled = false;
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
                        let index_i = 0;
                        const index = this.selectedXValues.indexOf(checkbox.value);
                        for (let i = 0; i < xCheckboxes.length; i++) {
                            if (xCheckboxes.item(i).value === checkbox.value) {
                                index_i = i;
                                break;
                            }
                        }

                        if (index !== -1) {
                            this.selectedXValues.splice(index, 1);
                            if (this.selectedXValues.length === 0) {
                                xCheckboxes.item(index_i).setCustomValidity("You must choose at least one value");
                                xCheckboxes.item(index_i).reportValidity();
                            }
                        }
                    }
                    submitBtn.disabled = !Validator.isValidX(this.selectedXValues);
                });
            });

            yInput.addEventListener('input', function () {
                const yValue = parseFloat(yInput.value.replace(',', '.')); // Заменим запятую на точку и преобразуем в число
                submitBtn.disabled = !Validator.isValidY(yValue);
                if (submitBtn.disabled || isNaN(yValue)) {
                    yInput.setCustomValidity("Enter a valid number between -3 and 3 with max length 14");
                    yInput.reportValidity();
                } else {
                    yInput.setCustomValidity("");
                    yInput.reportValidity();
                }
            });

        });

        this.resetButton.addEventListener('click', event => {
            event.preventDefault();
            this.graph.clearDots();
            // fetch("/controller?" + new URLSearchParams({command: "clear"}))
            //     .then(r => {
            //         return r.json();
            //     })
        })

        this.submitButton.addEventListener('click', (event) => {
            if (!Validator.isValid(this.selectedXValues, parseFloat(document.getElementById('y').value.replace(',', '.')), document.getElementById('r').value)) {
                event.preventDefault();
                if (!Validator.isValidY(parseFloat(document.getElementById('y').value.replace(',', '.')))) {
                    const yInput = document.getElementById('y');
                    yInput.setCustomValidity("Enter a valid number between -3 and 3 with max length 14");
                    yInput.reportValidity();
                }
                if (!Validator.isValidX(this.selectedXValues)) {
                    const xCheckboxes = document.querySelectorAll("input[name='x']");
                    xCheckboxes.item(0).setCustomValidity("You must choose at least one value");
                    xCheckboxes.item(0).reportValidity();
                }
                return;
            }

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
