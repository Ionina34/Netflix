const state = {};
const carouselList = document.querySelector('.carousel__list');
const carouselItems = document.querySelectorAll('.carousel__item');
const caruselInfo = document.querySelectorAll('.containerText')
const elems = Array.from(carouselItems);
const info = Array.from(caruselInfo);
var carusel = new Map(JSON.parse(localStorage.getItem('carusel')))

// Получаем ссылку на элемент, внутри которого будет проверяться положение курсора
const targetElement=document.getElementById('container-carusel');

// Функция анимации
function animate(){
	var newActive = elems.find((elem) => elem.dataset.pos == 1);
		var isItem = newActive.closest('.carousel__item');

		if (!isItem || newActive.classList.contains('carousel__item_active')) {
			return;
		};
		update(newActive);
}

// Инициализируем переменную для хранения идентификатора интервала
let animationInterval= setInterval(animate, 3000);;

// Обработчик события mouseleave
targetElement.addEventListener('mousemove', () => {
    // Останавливаем анимацию, если она запущена
    if (animationInterval) {
        clearInterval(animationInterval);
        animationInterval = null; // Сброс переменной
    }
});

// Обработчик события mousemove
targetElement.addEventListener('mouseleave', () => {
    // Запускаем анимацию, если она не запущена
    if (!animationInterval) {
		animate()
        animationInterval = setInterval(animate, 3000); // Запускаем анимацию каждые 3000 миллисекунд
    }
});

carouselList.addEventListener('click', function(event) {
	var newActive = event.target;
	var isItem = newActive.closest('.carousel__item');

	if (!isItem || newActive.classList.contains('carousel__item_active')) {
		return;
	};
	update(newActive);
});

const update = function(newActive) {
	const newActivePos = newActive.dataset.pos;
	if (newActivePos !== '0') {
		const current = elems.find((elem) => elem.dataset.pos == 0);
		const prev = elems.find((elem) => elem.dataset.pos == -1);
		const next = elems.find((elem) => elem.dataset.pos == 1);
		const first = elems.find((elem) => elem.dataset.pos == -2);
		const last = elems.find((elem) => elem.dataset.pos == 2);
		const zad1 = elems.find((elem) => elem.dataset.pos == -3);
		const zad2 = elems.find((elem) => elem.dataset.pos == 3);
		const zad3 = elems.find((elem) => elem.dataset.pos == -4);
		const zad4 = elems.find((elem) => elem.dataset.pos == 4);
		const zad5 = elems.find((elem) => elem.dataset.pos == 5);



		current.classList.remove('carousel__item_active');

		[current, prev, next, first, last, zad1, zad2, zad3, zad4, zad5].forEach(item => {
			var itemPos = item.dataset.pos;

			item.dataset.pos = getPos(itemPos, newActivePos)
		});

		const currentInfo = info.find((elem) => elem.dataset.pos == 0);
		const prevInfo = info.find((elem) => elem.dataset.pos == -1);
		const nextInfo = info.find((elem) => elem.dataset.pos == 1);
		const firstInfo = info.find((elem) => elem.dataset.pos == -2);
		const lastInfo = info.find((elem) => elem.dataset.pos == 2);
		const zad1Info = info.find((elem) => elem.dataset.pos == -3);
		const zad2Info = info.find((elem) => elem.dataset.pos == 3);
		const zad3Info = info.find((elem) => elem.dataset.pos == -4);
		const zad4Info = info.find((elem) => elem.dataset.pos == 4);
		const zad5Info = info.find((elem) => elem.dataset.pos == 5);

		[currentInfo, prevInfo, nextInfo, firstInfo, lastInfo, zad1Info, zad2Info, zad3Info, zad4Info, zad5Info].forEach(item => {
			var itemPos = item.dataset.pos;

			item.dataset.pos = getPos(itemPos, newActivePos)
			carusel.set(item.id, item.dataset.pos);
		});
		localStorage.setItem('carusel', JSON.stringify(Array.from(carusel.entries())));
	}
};

const getPos = function(current, active) {
	if (active > 0) {
		if (current > 0) {
			return current - 1;
		}
		else {
			{
				if (current !== "-4") {
					return Number(current) - 1;
				}
				else
					return 5;
			}
		}
	}
	else {
		if (current >= 0) {
			if (current !== "5") {
				return Number(current) + 1;
			}
			else
				return -4;
		}
		else {
			return Number(current) + 1;
		}
	}
}
