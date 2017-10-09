( function($) {
	function Validator() {
		this.clear = function() {
			alert("Cleared");
		};

		this.RequiredFieldValidator = function(value, args) {
			message.clear();
			var errors = [];
			
			if (value == null || value == undefined || !value.length) {
				errors.push({
					"code" : "err.cmm.001",
					"arguments" : [ args.column.name ]
				});
				message.setErrors(errors);
				
				return {
					valid : false,
					msg : args.column.name + " is a required field"
				};
			} else {
				return {
					valid : true,
					msg : null
				};
			}
		};

		this.UniqueValidator = function(value, args) {
			message.clear();
			var errors = [];
			var id = args.column.id;
			var items = args.grid.getData().getItems();

			for ( var v in items) {
				if (args.item[id] != items[v][id]
						&& (items[v][id]).toString().toLowerCase() == (value).toString().toLowerCase()) {
					errors.push({
						"code" : "err.cmm.011",
						"arguments" : [ value ]
					});
					message.setErrors(errors);
					
					return {
						valid : false,
						msg : "The value is duplicate. Please use another."
					};
				}
			}

			if (value == null || value == undefined || !value.length) {
				errors.push({
					"code" : "err.cmm.001",
					"arguments" : [ args.column.name ]
				});
				message.setErrors(errors);
				
				return {
					valid : false,
					msg : args.column.name + " is a required field"
				};
			} else {
				return {
					valid : true,
					msg : null
				};
			}
		};
	}

	$.extend((window.common || (window.common = {})), {
		"Validator" : new Validator()
	});
})(jQuery);