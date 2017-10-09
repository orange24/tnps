$( function($) {
	function Converter() {
		this.clear = function() {
			alert("Cleared");
		};

		this.dateFormat = function(format, date) {
			// <!-- Verifying: Parameters. -->
			if (!date && system && system.currentTimeMillis)
				date = system.currentTimeMillis;
			if (!date)
				return "Date cannot be null.";
			if (!format)
				return "Format cannot be null.";

			// <!-- Preparing: Data. -->
			var dayOfMonth = date.getDate();
			var month = date.getMonth() + 1;
			var monthName = "";
			var year = date.getFullYear();
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var seconds = date.getSeconds();

			// <!-- Checking: Correct Data. -->
			if (month > 12)
				month = 1;

			switch (month) {
				case 1:
					monthName = "January";
					break;
				case 2:
					monthName = "February";
					break;
				case 3:
					monthName = "March";
					break;
				case 4:
					monthName = "April";
					break;
				case 5:
					monthName = "May";
					break;
				case 6:
					monthName = "June";
					break;
				case 7:
					monthName = "July";
					break;
				case 8:
					monthName = "August";
					break;
				case 9:
					monthName = "September";
					break;
				case 10:
					monthName = "October";
					break;
				case 11:
					monthName = "November";
					break;
				case 12:
					monthName = "December";
					break;
			}

			return format.replace(/dd/g, (dayOfMonth.toString().length < 2 ? "0" : "") + dayOfMonth)
					.replace(/MMMMM/g, monthName)
					.replace(/MMM/g, monthName.substring(0, 3).toUpperCase())
					.replace(/MM/g, (month.toString().length < 2 ? "0" : "") + month)
					.replace(/yyyy/g, year)
					.replace(/yy/g, year.toString().substring(2))
					.replace(/HH/g, (hours.toString().length < 2 ? "0" : "") + hours)
					.replace(/mm/g, (minutes.toString().length < 2 ? "0" : "") + minutes)
					.replace(/ss/g, (seconds.toString().length < 2 ? "0" : "") + seconds);
		};

		/**
		 * numberFormat is a NumberFormat that converts numbers into strings using the decimal numbering system. 
		 * This is the formatter that provides standard number formatting and parsing services for most usage scenarios 
		 * in most locales.
		 * 
		 * Special Pattern Characters
		 * 		0	Digit
		 * 		#	Digit, zero shows as absent 
		 * 		.	Decimal separator or monetary decimal separator 
		 * 		-	Minus sign 
		 * 		,	Grouping separator 
		 * 
		 * Limitation
		 * 		- No prefix or suffix is allowed except leading negation symbol. So $#,##0.00 or #,###.##USD will not 
		 * 		  yield expected outcome. Use '$'+format('#,##0.00', 123.45) or format('#,##0.00', 456.789) + 'USD'</li>
		 * 		- No scientific/engineering formatting.
		 * 		- Not for date or phone formation.
		 * 
		 * @param mask - a number format pattern, for example, "#,##0.00;
		 * @param value - a number
		 * @return number formatted, for example 
		 * 		numberFormat("#,##0.####", 1234567.800);	->	1,234,567.8
		 * 		numberFormat("#,##0.00##", 1234567.895);	->	1,234,567.895
		 * 		numberFormat("#,##0.00", 1234567.895);		->	1,234,567.90
		 */
		this.numberFormat = function(mask, value) {
			if (value == undefined) {
				value = null;
			}

			if (!mask || isNaN(+value)) {
				return value; //return as it is.
			}

			//convert any string to number according to formation sign.
			var value = mask.charAt(0) == '-' ? -value : +value;
			var isNegative = value < 0 ? value = -value : 0; //process only abs(), and turn on flag.

			//search for separator for grp & decimal, anything not digit, not +/- sign, not #.
			var result = mask.match(/[^\d\-\+#]/g);
			var Decimal = (result && result[result.length - 1]) || '.'; //treat the right most symbol as decimal
			var Group = (result && result[1] && result[0]) || ','; //treat the left most symbol as group separator

			//split the decimal for the format string if any.
			var mask = mask.split(Decimal);
			//Fix the decimal first, toFixed will auto fill trailing zero.
			value = value.toFixed(mask[1] && mask[1].length);
			value = +(value) + ''; //convert number to string to trim off *all* trailing decimal zero(es)

			//fill back any trailing zero according to format
			var pos_trail_zero = mask[1] && mask[1].lastIndexOf('0'); //look for last zero in format
			var part = value.split('.');
			//integer will get !part[1]
			if (!part[1] || part[1] && part[1].length <= pos_trail_zero) {
				value = (+value).toFixed(pos_trail_zero + 1);
			}
			var szSep = mask[0].split(Group); //look for separator
			mask[0] = szSep.join(''); //join back without separator for counting the pos of any leading 0.

			var pos_lead_zero = mask[0] && mask[0].indexOf('0');
			if (pos_lead_zero > -1) {
				while (part[0].length < (mask[0].length - pos_lead_zero)) {
					part[0] = '0' + part[0];
				}
			} else if (+part[0] == 0) {
				part[0] = '';
			}

			value = value.split('.');
			value[0] = part[0];

			//process the first group separator from decimal (.) only, the rest ignore.
			//get the length of the last slice of split result.
			var pos_separator = (szSep[1] && szSep[szSep.length - 1].length);
			if (pos_separator) {
				var integer = value[0];
				var str = '';
				var offset = integer.length % pos_separator;
				for ( var i = 0, l = integer.length; i < l; i++) {

					str += integer.charAt(i); //ie6 only support charAt for sz.
					//-pos_separator so that won't trail separator on full length
					if (!((i - offset + 1) % pos_separator) && i < l - pos_separator) {
						str += Group;
					}
				}
				value[0] = str;
			}

			value[1] = (mask[1] && value[1]) ? Decimal + value[1] : "";
			return (isNegative ? '-' : '') + value[0] + value[1]; //put back any negation and combine integer and fraction.
		};
	}

	$.extend((window.common || (window.common = {})), {
		"Converter" : new Converter()
	});
});