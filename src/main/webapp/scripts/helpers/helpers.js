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