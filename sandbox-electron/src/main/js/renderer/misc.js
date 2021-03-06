// misc.js
(function() {

'use strict';

function parseCellLabel(label) {
  var xLabel = label.match(/[a-z]+/i)[0];
  var yLabel = label.match(/[0-9]+/i)[0];
  var x = 0;
  for (var i = 0; i < xLabel.split('').length; i++) {
    x *= 26;
    x += xLabel.split('')[i].toUpperCase().charCodeAt() - 64;
  }
  var y = parseInt(yLabel, 10);
  return {
    x: x - 1,
    y: y - 1
  };
}

function buildCellLabel(x, y) {
  var s = '';
  x += 1;
  while (x > 0) {
    s = String.fromCharCode((x - 1) % 26 + 65) + s;
    x = Math.floor((x - 1) / 26);
  }
  s = s + (y + 1);
  return s;
}

function parseExcelFunction(func) {
  var Parsimmon=require('parsimmon');
  var regex = Parsimmon.regex;
  var string = Parsimmon.string;
  var optWhitespace = Parsimmon.optWhitespace;
  var lazy = Parsimmon.lazy;
  var alt = Parsimmon.alt;
  var seq = Parsimmon.seq;

  var parser = lazy(function() {
    return alt(functions, paren, ref, num, bool, literal, operator, comma).skip(optWhitespace).many();
  });
  var paren = lazy(function() {
    return lparen.then(parser).skip(rparen);
  });

  var plus = string('+');
  var minus = string('-');
  var mul = alt(string('*'), string('×'));
  var div = alt(string('/'), string('÷'));

  var operator = alt(plus, minus, mul, div)
    .map(function(n) { return {type: 'operator', value: n}; });

  var ref = regex(/[a-z]+[0-9]+/i)
    .map(function(n) { return {type:'reference', value: n}; });
  var num = alt(regex(/-?[1-9][0-9]*(\.[0-9]+)?/))
    .map(function(n) { return {type: 'num', value: parseFloat(n)}; });
  var bool = regex(/true|false/i)
    .map(function(n) { return {type:'bool', value: n.toLowerCase()}; });
  // TODO <literal> should match a string including escaped double-quote. eg. " foo """ bar "
  var literal = regex(/".*"/i)
    .map(function(n) { return {type:'literal', value: n}; });
  var comma = string(',')
    .map(function(n) { return {type:'comma', value: n}; });

  // for (var s in formulajs) { console.log('    regex(/' + s + '/i),'); }
  var functions = seq(alt(
    regex(/BETADIST/i),
    regex(/BETAINV/i),
    regex(/BINOMDIST/i),
    regex(/ISOCEILING/i),
    regex(/CEILING/i),
    regex(/CEILINGMATH/i),
    regex(/CEILINGPRECISE/i),
    regex(/CHIDIST/i),
    regex(/CHIDISTRT/i),
    regex(/CHIINV/i),
    regex(/CHIINVRT/i),
    regex(/CHITEST/i),
    regex(/CONFIDENCE/i),
    regex(/COVAR/i),
    regex(/COVARIANCEP/i),
    regex(/COVARIANCES/i),
    regex(/CRITBINOM/i),
    regex(/EXPONDIST/i),
    regex(/ERFCPRECISE/i),
    regex(/ERFPRECISE/i),
    regex(/FDIST/i),
    regex(/FDISTRT/i),
    regex(/FINVRT/i),
    regex(/FINV/i),
    regex(/FLOOR/i),
    regex(/FLOORMATH/i),
    regex(/FLOORPRECISE/i),
    regex(/FTEST/i),
    regex(/GAMMADIST/i),
    regex(/GAMMAINV/i),
    regex(/GAMMALNPRECISE/i),
    regex(/HYPGEOMDIST/i),
    regex(/LOGINV/i),
    regex(/LOGNORMINV/i),
    regex(/LOGNORMDIST/i),
    regex(/MODE/i),
    regex(/MODEMULT/i),
    regex(/MODESNGL/i),
    regex(/NEGBINOMDIST/i),
    regex(/NETWORKDAYSINTL/i),
    regex(/NORMDIST/i),
    regex(/NORMINV/i),
    regex(/NORMSDIST/i),
    regex(/NORMSINV/i),
    regex(/PERCENTILE/i),
    regex(/PERCENTILEEXC/i),
    regex(/PERCENTILEINC/i),
    regex(/PERCENTRANK/i),
    regex(/PERCENTRANKEXC/i),
    regex(/PERCENTRANKINC/i),
    regex(/POISSON/i),
    regex(/POISSONDIST/i),
    regex(/QUARTILE/i),
    regex(/QUARTILEEXC/i),
    regex(/QUARTILEINC/i),
    regex(/RANK/i),
    regex(/RANKAVG/i),
    regex(/RANKEQ/i),
    regex(/SKEWP/i),
    regex(/STDEV/i),
    regex(/STDEVP/i),
    regex(/STDEVS/i),
    regex(/TDIST/i),
    regex(/TDISTRT/i),
    regex(/TINV/i),
    regex(/TTEST/i),
    regex(/VAR/i),
    regex(/VARP/i),
    regex(/VARS/i),
    regex(/WEIBULL/i),
    regex(/WEIBULLDIST/i),
    regex(/WORKDAYINTL/i),
    regex(/ZTEST/i),
    regex(/FINDFIELD/i),
    regex(/DAVERAGE/i),
    regex(/DCOUNT/i),
    regex(/DCOUNTA/i),
    regex(/DGET/i),
    regex(/DMAX/i),
    regex(/DMIN/i),
    regex(/DPRODUCT/i),
    regex(/DSTDEV/i),
    regex(/DSTDEVP/i),
    regex(/DSUM/i),
    regex(/DVAR/i),
    regex(/DVARP/i),
    regex(/BESSELI/i),
    regex(/BESSELJ/i),
    regex(/BESSELK/i),
    regex(/BESSELY/i),
    regex(/BIN2DEC/i),
    regex(/BIN2HEX/i),
    regex(/BIN2OCT/i),
    regex(/BITAND/i),
    regex(/BITLSHIFT/i),
    regex(/BITOR/i),
    regex(/BITRSHIFT/i),
    regex(/BITXOR/i),
    regex(/COMPLEX/i),
    regex(/CONVERT/i),
    regex(/DEC2BIN/i),
    regex(/DEC2HEX/i),
    regex(/DEC2OCT/i),
    regex(/DELTA/i),
    regex(/ERF/i),
    regex(/ERFC/i),
    regex(/GESTEP/i),
    regex(/HEX2BIN/i),
    regex(/HEX2DEC/i),
    regex(/HEX2OCT/i),
    regex(/IMABS/i),
    regex(/IMAGINARY/i),
    regex(/IMARGUMENT/i),
    regex(/IMCONJUGATE/i),
    regex(/IMCOS/i),
    regex(/IMCOSH/i),
    regex(/IMCOT/i),
    regex(/IMDIV/i),
    regex(/IMEXP/i),
    regex(/IMLN/i),
    regex(/IMLOG10/i),
    regex(/IMLOG2/i),
    regex(/IMPOWER/i),
    regex(/IMPRODUCT/i),
    regex(/IMREAL/i),
    regex(/IMSEC/i),
    regex(/IMSECH/i),
    regex(/IMSIN/i),
    regex(/IMSINH/i),
    regex(/IMSQRT/i),
    regex(/IMCSC/i),
    regex(/IMCSCH/i),
    regex(/IMSUB/i),
    regex(/IMSUM/i),
    regex(/IMTAN/i),
    regex(/OCT2BIN/i),
    regex(/OCT2DEC/i),
    regex(/OCT2HEX/i),
    regex(/AND/i),
    regex(/CHOOSE/i),
    regex(/FALSE/i),
    regex(/IF/i),
    regex(/IFERROR/i),
    regex(/IFNA/i),
    regex(/NOT/i),
    regex(/OR/i),
    regex(/TRUE/i),
    regex(/XOR/i),
    regex(/SWITCH/i),
    regex(/ABS/i),
    regex(/ACOS/i),
    regex(/ACOSH/i),
    regex(/ACOT/i),
    regex(/ACOTH/i),
    regex(/AGGREGATE/i),
    regex(/ARABIC/i),
    regex(/ASIN/i),
    regex(/ASINH/i),
    regex(/ATAN/i),
    regex(/ATAN2/i),
    regex(/ATANH/i),
    regex(/BASE/i),
    regex(/COMBIN/i),
    regex(/COMBINA/i),
    regex(/COS/i),
    regex(/COSH/i),
    regex(/COT/i),
    regex(/COTH/i),
    regex(/CSC/i),
    regex(/CSCH/i),
    regex(/DECIMAL/i),
    regex(/DEGREES/i),
    regex(/EVEN/i),
    regex(/EXP/i),
    regex(/FACT/i),
    regex(/FACTDOUBLE/i),
    regex(/GCD/i),
    regex(/INT/i),
    regex(/ISO/i),
    regex(/LCM/i),
    regex(/LN/i),
    regex(/LOG/i),
    regex(/LOG10/i),
    regex(/MDETERM/i),
    regex(/MINVERSE/i),
    regex(/MMULT/i),
    regex(/MOD/i),
    regex(/MROUND/i),
    regex(/MULTINOMIAL/i),
    regex(/MUNIT/i),
    regex(/ODD/i),
    regex(/PI/i),
    regex(/POWER/i),
    regex(/PRODUCT/i),
    regex(/QUOTIENT/i),
    regex(/RADIANS/i),
    regex(/RAND/i),
    regex(/RANDBETWEEN/i),
    regex(/ROMAN/i),
    regex(/ROUND/i),
    regex(/ROUNDDOWN/i),
    regex(/ROUNDUP/i),
    regex(/SEC/i),
    regex(/SECH/i),
    regex(/SERIESSUM/i),
    regex(/SIGN/i),
    regex(/SIN/i),
    regex(/SINH/i),
    regex(/SQRT/i),
    regex(/SQRTPI/i),
    regex(/SUBTOTAL/i),
    regex(/ADD/i),
    regex(/MINUS/i),
    regex(/DIVIDE/i),
    regex(/MULTIPLY/i),
    regex(/GTE/i),
    regex(/LT/i),
    regex(/LTE/i),
    regex(/EQ/i),
    regex(/NE/i),
    regex(/POW/i),
    regex(/SUM/i),
    regex(/SUMIF/i),
    regex(/SUMIFS/i),
    regex(/SUMPRODUCT/i),
    regex(/SUMSQ/i),
    regex(/SUMX2MY2/i),
    regex(/SUMX2PY2/i),
    regex(/SUMXMY2/i),
    regex(/TAN/i),
    regex(/TANH/i),
    regex(/TRUNC/i),
    regex(/ASC/i),
    regex(/BAHTTEXT/i),
    regex(/CHAR/i),
    regex(/CLEAN/i),
    regex(/CODE/i),
    regex(/CONCATENATE/i),
    regex(/DBCS/i),
    regex(/DOLLAR/i),
    regex(/EXACT/i),
    regex(/FIND/i),
    regex(/FIXED/i),
    regex(/HTML2TEXT/i),
    regex(/LEFT/i),
    regex(/LEN/i),
    regex(/LOWER/i),
    regex(/MID/i),
    regex(/NUMBERVALUE/i),
    regex(/PRONETIC/i),
    regex(/PROPER/i),
    regex(/REGEXEXTRACT/i),
    regex(/REGEXMATCH/i),
    regex(/REGEXREPLACE/i),
    regex(/REPLACE/i),
    regex(/REPT/i),
    regex(/RIGHT/i),
    regex(/SEARCH/i),
    regex(/SPLIT/i),
    regex(/SUBSTITUTE/i),
    regex(/T/i),
    regex(/TEXT/i),
    regex(/TRIM/i),
    regex(/UNICHAR/i),
    regex(/UNICODE/i),
    regex(/UPPER/i),
    regex(/VALUE/i),
    regex(/DATE/i),
    regex(/DATEVALUE/i),
    regex(/DAY/i),
    regex(/DAYS/i),
    regex(/DAYS360/i),
    regex(/EDATE/i),
    regex(/EOMONTH/i),
    regex(/HOUR/i),
    regex(/INTERVAL/i),
    regex(/ISOWEEKNUM/i),
    regex(/MINUTE/i),
    regex(/MONTH/i),
    regex(/NETWORKDAYS/i),
    regex(/NOW/i),
    regex(/SECOND/i),
    regex(/TIME/i),
    regex(/TIMEVALUE/i),
    regex(/TODAY/i),
    regex(/WEEKDAY/i),
    regex(/WEEKNUM/i),
    regex(/WORKDAY/i),
    regex(/YEAR/i),
    regex(/YEARFRAC/i),
    regex(/ACCRINT/i),
    regex(/ACCRINTM/i),
    regex(/AMORDEGRC/i),
    regex(/AMORLINC/i),
    regex(/COUPDAYBS/i),
    regex(/COUPDAYS/i),
    regex(/COUPDAYSNC/i),
    regex(/COUPNCD/i),
    regex(/COUPNUM/i),
    regex(/COUPPCD/i),
    regex(/CUMIPMT/i),
    regex(/CUMPRINC/i),
    regex(/DB/i),
    regex(/DDB/i),
    regex(/DISC/i),
    regex(/DOLLARDE/i),
    regex(/DOLLARFR/i),
    regex(/DURATION/i),
    regex(/EFFECT/i),
    regex(/FV/i),
    regex(/FVSCHEDULE/i),
    regex(/INTRATE/i),
    regex(/IPMT/i),
    regex(/IRR/i),
    regex(/ISPMT/i),
    regex(/MDURATION/i),
    regex(/MIRR/i),
    regex(/NOMINAL/i),
    regex(/NPER/i),
    regex(/NPV/i),
    regex(/ODDFPRICE/i),
    regex(/ODDFYIELD/i),
    regex(/ODDLPRICE/i),
    regex(/ODDLYIELD/i),
    regex(/PDURATION/i),
    regex(/PMT/i),
    regex(/PPMT/i),
    regex(/PRICE/i),
    regex(/PRICEDISC/i),
    regex(/PRICEMAT/i),
    regex(/PV/i),
    regex(/RATE/i),
    regex(/RECEIVED/i),
    regex(/RRI/i),
    regex(/SLN/i),
    regex(/SYD/i),
    regex(/TBILLEQ/i),
    regex(/TBILLPRICE/i),
    regex(/TBILLYIELD/i),
    regex(/VDB/i),
    regex(/XIRR/i),
    regex(/XNPV/i),
    regex(/YIELD/i),
    regex(/YIELDDISC/i),
    regex(/YIELDMAT/i),
    regex(/CELL/i),
    regex(/ERROR/i),
    regex(/INFO/i),
    regex(/ISBLANK/i),
    regex(/ISBINARY/i),
    regex(/ISERR/i),
    regex(/ISERROR/i),
    regex(/ISEVEN/i),
    regex(/ISFORMULA/i),
    regex(/ISLOGICAL/i),
    regex(/ISNA/i),
    regex(/ISNONTEXT/i),
    regex(/ISNUMBER/i),
    regex(/ISODD/i),
    regex(/ISREF/i),
    regex(/ISTEXT/i),
    regex(/N/i),
    regex(/NA/i),
    regex(/SHEET/i),
    regex(/SHEETS/i),
    regex(/TYPE/i),
    regex(/MATCH/i),
    regex(/AVEDEV/i),
    regex(/AVERAGE/i),
    regex(/AVERAGEA/i),
    regex(/AVERAGEIF/i),
    regex(/AVERAGEIFS/i),
    regex(/BETA/i),
    regex(/BINOM/i),
    regex(/CHISQ/i),
    regex(/COLUMN/i),
    regex(/COLUMNS/i),
    regex(/CORREL/i),
    regex(/COUNT/i),
    regex(/COUNTA/i),
    regex(/COUNTIN/i),
    regex(/COUNTBLANK/i),
    regex(/COUNTIF/i),
    regex(/COUNTIFS/i),
    regex(/COUNTUNIQUE/i),
    regex(/COVARIANCE/i),
    regex(/DEVSQ/i),
    regex(/EXPON/i),
    regex(/F/i),
    regex(/FISHER/i),
    regex(/FISHERINV/i),
    regex(/FORECAST/i),
    regex(/FREQUENCY/i),
    regex(/GAMMA/i),
    regex(/GAMMALN/i),
    regex(/GAUSS/i),
    regex(/GEOMEAN/i),
    regex(/GROWTH/i),
    regex(/HARMEAN/i),
    regex(/HYPGEOM/i),
    regex(/INTERCEPT/i),
    regex(/KURT/i),
    regex(/LARGE/i),
    regex(/LINEST/i),
    regex(/LOGEST/i),
    regex(/LOGNORM/i),
    regex(/MAX/i),
    regex(/MAXA/i),
    regex(/MEDIAN/i),
    regex(/MIN/i),
    regex(/MINA/i),
    regex(/NEGBINOM/i),
    regex(/NORM/i),
    regex(/PEARSON/i),
    regex(/PERMUT/i),
    regex(/PERMUTATIONA/i),
    regex(/PHI/i),
    regex(/PROB/i),
    regex(/ROW/i),
    regex(/ROWS/i),
    regex(/RSQ/i),
    regex(/SKEW/i),
    regex(/SLOPE/i),
    regex(/SMALL/i),
    regex(/STANDARDIZE/i),
    regex(/STDEVA/i),
    regex(/STDEVPA/i),
    regex(/STEYX/i),
    regex(/TRANSPOSE/i),
    regex(/TREND/i),
    regex(/TRIMMEAN/i),
    regex(/VARA/i),
    regex(/VARPA/i),
    regex(/Z/i),
    regex(/UNIQUE/i),
    regex(/FLATTEN/i),
    regex(/ARGS2ARRAY/i),
    regex(/REFERENCE/i),
    regex(/JOIN/i),
    regex(/NUMBERS/i),
    regex(/NUMERAL/i)
  ), paren)
    .map(function(n) { return {type: 'function', value: n}; });

  var lparen = string('(');
  var rparen = string(')');

  return parser.parse(func).value;
}

function buildCellReference(label) {
  var position = parseCellLabel(label);
  var func = '';
  func += '(';
  func += '  ExcelStore.getCell(' + position.x + ',' + position.y + ') ? ';
  func += '    (';
  func += '      ExcelStore.getCell(' + position.x + ',' + position.y + ').func ?';
  func += '        ExcelStore.getCell(' + position.x + ',' + position.y + ').func(ExcelStore)';
  func += '      : ExcelStore.getCell(' + position.x + ',' + position.y + ').value';
  func += '    )';
  func += '  : ""';
  func += ')';
  return func;
}

function buildFunctionFromTokens(tokens) {
  if (!tokens) {
    return '';
  }

  var func = '';
  for (var i = 0; i < tokens.length; i++) {
    var token = tokens[i];
    if (token.type === 'function') {
      func += 'formulajs.' + token.value[0].toUpperCase() + '( ' + buildFunctionFromTokens(token.value[1]) + ' )';
    } else if (token.type === 'reference') {
      func += buildCellReference(token.value);
    } else {
      func += token.value;
    }
  }
  return func;
}

function buildFormulaJsFunction(tokens) {
  if (!tokens) {
    return null;
  }
  return new Function('ExcelStore', 'return ' + buildFunctionFromTokens(tokens));
}

module.exports = {
  parseCellLabel: parseCellLabel,
  buildCellLabel: buildCellLabel,
  parseExcelFunction: parseExcelFunction,
  buildFormulaJsFunction: buildFormulaJsFunction
};

})();
