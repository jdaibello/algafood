function findRestaurants() {
	$.ajax({
		url: "http://localhost:8080/restaurants",
		type: "GET",
		success: function (response) {
			$("#content").text(JSON.stringify(response));
		}
	});
}

$("#button").click(findRestaurants);