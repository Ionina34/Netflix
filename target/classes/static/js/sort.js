let checked = null;

function sort() {
	var data = document.getElementsByName('data')
	var data_value = "";
	for (var i = 0; i < data.length; i++) {
		if (data[i].checked) {
			data_value = data[i].value;
			break;
		}
	}
	localStorage.setItem('data_value',data_value);

	var alphabet = document.getElementsByName('alphabet')
	var alphabet_value = "";
	for (var i = 0; i < alphabet.length; i++) {
		if (alphabet[i].checked) {
			alphabet_value = alphabet[i].value;
			break;
		}
	}
	localStorage.setItem('alphabet_value',alphabet_value)

	var selectedGenre = document.getElementById("genre").value
	localStorage.setItem('selectedGenre',selectedGenre)

	var selectedCountry = document.getElementById("country").value
	localStorage.setItem('selectedCountry',selectedCountry)

	if (data_value != "" || alphabet_value != "" || selectedGenre != "" || selectedCountry != "")
		search_films_by_parameters_sort(data_value, alphabet_value, selectedGenre, selectedCountry, 0)
}