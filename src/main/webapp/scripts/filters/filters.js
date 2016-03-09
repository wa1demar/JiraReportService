'use strict';

jiraPluginApp.filter('isChecked', function() {

  var filterFunction = function(input) {
    return (input === true || input === 1 || input === '1') ? true : false;
  };

  return filterFunction;
});