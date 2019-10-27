var tabela2 = document.getElementById("mb");
var texto1 = document.getElementById("texto");
var timer = setInterval(digita, 100);

var texto = "SisChamados 2.0";
var texto2 = "";
var posicao = 0;

function digita(){
        texto2 = texto2 + texto.charAt(posicao);
        texto1.innerHTML = texto2;
        posicao = posicao + 1;
        if(posicao == texto.lenght){
            posicao = 0;
            texto2 = "";
        }
}