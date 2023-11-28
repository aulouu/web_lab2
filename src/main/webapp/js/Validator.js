export default class Validator {
    static regex = /^-?\d+(\.\d+)?$/;

    static isValid(x, y, r) {
        return this.isValidX(x) && this.isValidY(y) && this.isValidR(r)
    }

    static isValidR(val) {
        return val.length !== 0;
    }

    static isValidX(val) {
        if (typeof val === 'string') {
            // Если x является строкой, разбиваем строку по запятой
            const values = val.split(',').map(element => element.trim()); // Разбиваем и удаляем пробелы
            return values.every(element => this.isValidXElement(element));
        } else if (Array.isArray(val)) {
            // Если x является массивом, просто проверяем каждый элемент
            return val.every(element => this.isValidXElement(element));
        } else {
            // Проверяем, является ли val числом и находится ли в пределах [-5, 3]
            return  val >= -5 && val <= 3;
        }
    }

    static isValidXElement(val) {
        // Проверяем, является ли val числом и находится ли в пределах [-5, 3]
        return val >= -5 && val <= 3;
    }

    static isValidY(val) {
        if (Number(val) === 0) {
            return true
        }
        if (!Number(val)) {
            return false
        }
        return !(!Number(val) < -3 || !Number(val) > 3);
    }
}
