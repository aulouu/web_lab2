function validateY(y) {
    const yValue = parseFloat(y);
    const isNumericAndInRange = !isNaN(yValue) && yValue >= -3 && yValue <= 3;
    const isLengthLessThan15 = y.length < 15;
    return isNumericAndInRange && isLengthLessThan15;
}
