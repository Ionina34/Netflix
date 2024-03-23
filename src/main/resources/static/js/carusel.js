element=document.getElementById("content-1");
const gap1=element.getElementsByTagName('*').length;

element=document.getElementById("content-2");
const gap2=element.getElementsByTagName('*').length;

element=document.getElementById("content-3");
const gap3=element.getElementsByTagName('*').length;

for(i=1;i<=3;i++){
    if(i==1)
        var gap=gap1
    else if(i==2)
        gap=gap2
    else gap=gap3
    
    const carousel = document.getElementById("carousel-"+i),
    content = document.getElementById("content-"+i),
    next = document.getElementById("next-"+i),
    prev = document.getElementById("prev-"+i);
    
    
    next.addEventListener("click", e => {
        carousel.scrollBy(width + gap+i, 0);
        if (carousel.scrollWidth !== 0) {
            prev.style.display = "flex";
  }
  if (content.scrollWidth - width - gap <= carousel.scrollLeft + width) {
      next.style.display = "none";
    }
});
prev.addEventListener("click", e => {
    carousel.scrollBy(-(width + gap), 0);
    if (carousel.scrollLeft - width - gap <= 0) {
        prev.style.display = "none";
    }
    if (!content.scrollWidth - width - gap <= carousel.scrollLeft + width) {
        next.style.display = "flex";
    }
});

let width = carousel.offsetWidth;
window.addEventListener("resize", e => (width = carousel.offsetWidth));
}
