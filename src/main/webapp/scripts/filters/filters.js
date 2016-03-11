'use strict';

jiraPluginApp.filter('isChecked', function() {

  var filterFunction = function(input) {
    return (input === true || input === 1 || input === '1') ? true : false;
  };

  return filterFunction;
});

//jiraPluginApp.filter('isInObj', function() {
//  var filterFunction = function(input, obj) {
//    for (var index = 0; index < input.length; index++) {
//      for (var indexTeam = 0; indexTeam < obj.length; indexTeam++) {
//        if (input[index].login === obj[indexTeam].devName) {
//          return true;
//        }
//      }
//    }
//  };
//
//  return filterFunction;
//});