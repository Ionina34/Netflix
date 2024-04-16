document.getElementById('container-carusel').style.display = "none";

document.getElementById('main').onclick=function(){
	localStorage.setItem("main","reload");
}
	
document.addEventListener('DOMContentLoaded', function() {
	var carusel = new Map(JSON.parse(localStorage.getItem('carusel')))
	if (carusel.size!==0 && performance.navigation.type!==performance.navigation.TYPE_RELOAD
	&& localStorage.getItem('main')===null) {
		for (var i = 0, len = elems.length; i < len; i++) {
			elems[i].dataset.pos = carusel.get(elems[i].id);
			info[i].dataset.pos = carusel.get(info[i].id);
		}
	}
	else {
		for (var i = 0, len = elems.length; i < len; i++) {
			elems[i].dataset.pos = i - 4;
			info[i].dataset.pos = i - 4;
		}
		SaveState();
	}
	document.getElementById('container-carusel').style.display = "block";
});

function SaveState() {
	localStorage.clear();
	var carusel = new Map();
	for (var item of elems) {
		carusel.set(item.id, item.dataset.pos);
	}
	localStorage.setItem('carusel', JSON.stringify(Array.from(carusel.entries())));
}

