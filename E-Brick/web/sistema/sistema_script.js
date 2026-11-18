import * as lib from "../lib.js"

const paiBotoes = document.getElementById("botoes");
const paiAbas = document.getElementById("abas");

function criarBotes(array){
    array.forEach(element => {
        var botao = document.createElement("a");//botao bonito, melhor que button feio

        botao.href = element.id;//torcer pra nao quebrar nada
        botao.textContent = element.nome;

        botao.addEventListener("click", (event) => {
            paiAbas.href = `/${element.id}.html`;
        });

        paiBotoes.appendChild(botao);
    });
}

const result = await lib.get("/sistema/abas");
if(result.response?.ok){
    const array = await result.response.json();
    criarBotes(array);
}else{
    paiBotoes.innerHTML = "<a>" + result.message + "<\a>";
}