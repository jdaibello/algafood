function query() {
	$.ajax({
		url: "http://localhost:8080/payment-methods",
		type: "GET",
		success: function (response) {
			fillTable(response);
		}
	});
}

function fillTable(paymentMethods) {
	$("#table tbody tr").remove();

	$.each(paymentMethods, function (i, paymentMethod) {
		var row = $("<tr>");

		row.append(
			$("<td>").text(paymentMethod.id),
			$("<td>").text(paymentMethod.description)
		);

		row.appendTo("#table");
	});
}

$("#btn-query").click(query);
