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

	var created = document.getElementsByName('created_data')
	var created_value = "";
	for (var i = 0; i < created.length; i++) {
		if (created[i].checked) {
			created_value = created[i].value;
			break;
		}
	}
	localStorage.setItem('created_value',created_value)

	if (data_value != "" || alphabet_value != "" || created_value != "")
		search_staff_by_parameters_sort(data_value, alphabet_value, created_value, 0)
}