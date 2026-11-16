import * as lib from "../lib.js"

const paiBotoes = document.getElementById("botoes");
const paiAbas = document.getElementById("abas");

function criarBotes(array){
    var html = "";
    array.forEach(element => {
        html += `<a href="${element.id}">${element.nome}</a>`;
    });

    paiBotoes.innerHTML = html;
}

const result = await lib.get("/sistema/abas");
if(result.response?.ok){
    const array = await result.response.json();
    criarBotes(array);
}else{
    paiAbas.innerHTML = "<h1>" + result.message + "<\h1>";
}