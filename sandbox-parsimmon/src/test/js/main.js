// main.js
(function() {

'use strict';

var main = require('../../main/js/main.js');

console.log(main.parseExcelFunction('IF(TRUE, B3, J10)')); // OK
console.log(main.parseExcelFunction('IF(A2=10, B3, J10)')); // NG
console.log(main.parseExcelFunction('IF(A2, B3, J10)')); // OK

})();
