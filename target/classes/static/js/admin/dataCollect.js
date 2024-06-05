//метод для сбора данных для отправки на сервер
function dataCollection(film,genres,countries,actors,directors,screenwriters){
	film["name"]=$("#name").val()
	film["des"]=$("#des").val()
	film["release_date"]=$("#data_release").val()
	film["length"]=$("#length").val()
	film["image"]=$("#url").val()
	
	//получить все жанры фильма
	let select_genres=$(".genre").children("div")
	for(let i=0;i<select_genres.length;i++){
		let genre={}
		genre["name"]=select_genres[i].querySelector("label").textContent
		genres.push(genre)
	}
	
	let select_countries=$(".country").children("div")
	for(let i=0;i<select_countries.length;i++){
		let country={}
		country["name"]=select_countries[i].querySelector("label").textContent
		countries.push(country)
	}
	
	let select_actors=$(".actor").children("div")
	for(let i=0;i<select_actors.length;i++){
		let actor={}
		actor["name"]=select_actors[i].querySelector("label").textContent
		actors.push(actor)
	}
	
	let select_directors=$(".director").children("div")
	for(let i=0;i<select_directors.length;i++){
		let director={}
		director["name"]=select_directors[i].querySelector("label").textContent
		directors.push(director)
	}
	
	let select_screenwriters=$(".screenwriter").children("div")
	for(let i=0;i<select_screenwriters.length;i++){
		let screenwriter={}
		screenwriter["name"]=select_screenwriters[i].querySelector("label").textContent
		screenwriters.push(screenwriter)
	}
} 

function dataCollectionStaff(staff,films){
	staff["name"]=$("#name").val()
	staff["birthday"]=$("#birthday").val()
	staff["brief_biography"]=$("#brief_biography").val()
	staff["photo"]=$("#url").val()
	
	let select_films=$(".film").children("div")
	for(let i=0;i<select_films.length;i++){
		let film={}
		film["name"]=select_films[i].querySelector("label").textContent
		films.push(film)
	}
}

//Метод для валидация и последующего вызова метода сохранения
$("input:text").on('input',function(){
	this.style.borderColor = "black";
})

$("textarea").on('input',function(){
	this.style.borderColor = "black";
})

function valid(){
	let dataStaff = $(".staff").children();
	let check = true;
	for (let i = 0; i < dataStaff.length; i++) {
		if (dataStaff[i].tagName.toLowerCase() === 'p') {
			let textarea = dataStaff[i].querySelector('textarea');
			if (textarea) {
				console.log(textarea.value)
				if (textarea.value == " ") {
					textarea.style.borderColor = "red";
					if (check) {
						alert("Заполните все поля");
					}
					check == false;
				}
			}
		}
		else if (dataStaff[i].tagName.toLowerCase() === 'input') {
			if (dataStaff[i].value === "") {
				dataStaff[i].style.borderColor = "red";
				if (check) {
					alert("Заполните все поля");
				}
				check = false;
			}
		}
	}
	if(check) saveStaff();
}
