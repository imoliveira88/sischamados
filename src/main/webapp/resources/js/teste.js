var texto1 = document.getElementById("texto");
var timer = setInterval(digita, 200);

function exibeMenu(){
    var subMenu = document.getElementById("menuCham");
    if (subMenu.style.display === "none") {
    subMenu.style.display = "block";
    } else {
    subMenu.style.display = "none";
    }
}

var texto = "SisChamados 3.0";
var texto2 = "";
var posicao = 0;

function digita(){
        texto2 = texto2 + texto.charAt(posicao);
        texto1.innerHTML = texto2;
        posicao = posicao + 1;
        if (posicao === texto.length){
            posicao = 0;
            texto2 = "";
        }
}