function leadingZeros(number) {
    return number <= 9 ? "0" + number : number.toString();
}


export function nowAsString() {
    return dateAsString(new Date());
}

export function dateAsString(date) {
    const month = leadingZeros(date.getMonth() + 1);
    const datestring = date.getFullYear() + " " + leadingZeros(date.getDate()) + "/" + month + " " +
        leadingZeros(date.getHours()) + ":" + leadingZeros(date.getMinutes());
    return datestring;

}


