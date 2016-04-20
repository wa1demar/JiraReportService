'use strict';

var getDatesArrayWithoutWeekends = function (startDate, endDate) {
    var result = [];

    var currentDate = new Date(startDate);
    while (currentDate.getTime() <= endDate.getTime()) {
        //not weekend
        if (currentDate.getDay() !== 0 && currentDate.getDay() !== 6) {
            result.push(new Date(currentDate));
        }
        currentDate.setDate(currentDate.getDate() + 1);
    }

    return result;
};

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

function colorLuminance(hex, lum) {

    // validate hex string
    hex = String(hex).replace(/[^0-9a-f]/gi, '');
    if (hex.length < 6) {
        hex = hex[0]+hex[0]+hex[1]+hex[1]+hex[2]+hex[2];
    }
    lum = lum || 0;

    // convert to decimal and change luminosity
    var rgb = "#", c, i;
    for (i = 0; i < 3; i++) {
        c = parseInt(hex.substr(i*2,2), 16);
        c = Math.round(Math.min(Math.max(0, c + (c * lum)), 255)).toString(16);
        rgb += ("00"+c).substr(c.length);
    }

    return rgb;
}