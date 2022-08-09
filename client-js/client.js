function consult() {
	$.ajax({
		url: "http://localhost:8080/payment-methods",
		type: "GET",
		success: function (response) {
			fillTable(response);
		}
	});
}

function register() {
	var paymentMethodJson = JSON.stringify({
		"description": $("#description-field").val(),
	});

	$.ajax({
		url: "http://localhost:8080/payment-methods",
		type: "POST",
		data: paymentMethodJson,
		contentType: "application/json",
		success: function (response) {
			alert("Forma de pagamento adicionada!");
			consult();
		},
		error: function (error) {
			if (error.status === 400) {
				var problem = JSON.parse(error.responseText);
				alert(problem.userMessage);
			} else {
				alert("Erro ao cadastrar forma de pagamento!");
			}
		}
	});
}

function remove(paymentMethod) {
	var url = "http://localhost:8080/payment-methods/" + paymentMethod.id;

	$.ajax({
		url: url,
		type: "DELETE",
		success: function(response) {
			consult();
			alert("Forma de pagamento removida!");
		},
		error: function(error) {
			if (error.status >= 400 && error.status <= 499) {
				var problem = JSON.parse(error.responseText);
				alert(problem.userMessage);
			} else {
				alert("Erro ao remover forma de pagamento");
			}
		}
	});
}

function fillTable(paymentMethods) {
	$("#table tbody tr").remove();

	$.each(paymentMethods, function (i, paymentMethod) {
		var row = $("<tr>");

		var actionLink = $("<a href='#'>").text("Excluir").click(function (event) {
			event.preventDefault();
			remove(paymentMethod);
		});

		row.append(
			$("<td>").text(paymentMethod.id),
			$("<td>").text(paymentMethod.description),
			$("<td>").append(actionLink)
		);

		row.appendTo("#table");
	});
}

$("#btn-consult").click(consult);
$("#btn-register").click(register);
