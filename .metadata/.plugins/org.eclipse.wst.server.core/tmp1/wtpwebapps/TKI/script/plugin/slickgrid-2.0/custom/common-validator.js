jQuery.fn.ForceNumericOnly = function(state, totalLength, decimalLength) {
	return this.each( function() {
		if (state) {
			$(this).keyup( function() {
				var input = this.value;
				if (input.length) {
					if (input != "-" && input != ".") {
						while (isNaN(input)) {
							input = input.substring(0, input.length - 1);
							$(this).val(input);
						}
					} else if (input.indexOf(".") != -1 && !(/[0-9]/g).test(input[0])) {
						$(this).val("0" + input);
					}
				}
			});
			
			$(this).blur( function() {
				if ($(this).val() == "-" || $(this).val() == ".") {
					$(this).val("");
				}
			});
			
			$(this).bind("contextmenu", function(e) {
				return false;
			});
			
			$(this).unbind("keydown").bind("keydown", function(e) {
				var key = e.charCode || e.keyCode || 0;

				// home (35), end (36), left (37), right (39), backspace (8), delete (46) and escape (27)
				var ctk = (key == 35 || key == 36 || key == 37 || key == 39 || key == 8 || key == 46 || key == 27);

				// allow backspace, tab, delete, arrows, escape, numbers and keypad numbers ONLY
				var ret = (ctk
						|| key == 9
						|| key == 109
						|| key == 173
						|| (key >= 37 && key <= 40)
						|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));

				//support decimalLength in numeric data
				if (totalLength != undefined && decimalLength != undefined) {
					var fLength = parseInt(totalLength) - parseInt(decimalLength);
					var v = $(this).val();
					var l = v.length;
					l = (ret && !(ctk) ? l + 1 : l);

					if ((key == 110 || key == 190) && v.indexOf(".") == -1 && decimalLength > 0) {//dot
						ret = true;
					} else if ((key == 109 || key == 173) && v.indexOf("-") == -1) {//-
						ret = true;
					} else if (v.indexOf(".") != -1) {
						var b = v.split(".");
						if (b[1].length + 1 > decimalLength && !ctk) {
							return false;
						}
					}

					if ((($(this).val().indexOf("-") != -1) && v.length - 1 == fLength)
							|| (($(this).val().indexOf("-") == -1) && v.length == fLength)) {
						if (key == 110 || key == 190) {//dot
							ret = true;
						$(this).attr("maxlength", parseInt(totalLength) + 1);

						if ($(this).val().indexOf("-") != -1) {
							$(this).attr("maxlength", parseInt(totalLength) + 1 + 1);
						} else {
							$(this).attr("maxlength", parseInt(totalLength) + 1);
						}

						} else {
							ret = false;
						}

					} else if (v.length <= fLength) {
						if ($(this).val().indexOf("-") != -1) {
							$(this).attr("maxlength", fLength + 1);
						} else {
							$(this).attr("maxlength", fLength);
						}
					}
				} else {
					$(this).attr("maxlength", totalLength);
				}

				if (ctk) {
					ret = true;
				}
				return ret;
			});
			
		} else {
			$(this).unbind("keydown");
		}
	});
};