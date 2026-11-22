import * as lib from "../lib.js";
import * as ec from "./edicao_clientes.js";

await lib.criarBotoes();

const input = document.getElementById("pesquisa");
const dropdown = document.getElementById("ordem");
const paiBotoesQtd = document.getElementById("qtds");
const clientes = document.getElementById("clientes");
const qtds = [25, 50, 100];

var cardPorCliente = {};

var paginaAtual = 0;
var dados_pesquisa = {
    pesquisa: "",
    ordem: dropdown.value,
    inicio: 0,
    qtd: 25
};

function mudarQuantiaMostrada(qtd) {
    dados_pesquisa.qtd = qtd;

    buscarClientes();
}

function mudarPagina(p) {
    paginaAtual = p;
    dados_pesquisa.inicio = paginaAtual * dados_pesquisa.qtd;

    buscarClientes();
}

function CPFouCNPJ(cliente){
    if(cliente.cpf){
        return { nome: "CPF", valor: lib.formatarCPF(cliente.cpf)};
    }else return { nome: "CNPJ", valor: lib.formatarCNPJ(cliente.cnpj)};
    //que horror
}

function criarCard(cliente) {
    const cpf_cnpj = CPFouCNPJ(cliente);
    const str = `${cpf_cnpj.nome}: ${cpf_cnpj.valor}`

    clientes.insertAdjacentHTML("beforeend", `
        <div id="card-${cliente.id}" class="item roundy sombra">
            <h3> ${cliente.nome} </h3>
            <hr style="background-color: gray">
            <p> ${str} </p>
            <p> Telefone: ${lib.formatarTelefone(cliente.telefone)} </p>
            <p> Cidade: ${cliente.cidade} </p>
            <p> Estado: ${cliente.estado} </p>
            <p>${cliente.ativo ? "Ativo" : "Inativo"} </p>
        </div>
    `);

    const card = document.getElementById(`card-${cliente.id}`);
    card.addEventListener('click', () => {ec.selecaoCliente(cliente)});
    cardPorCliente.card = cliente
}

async function buscarClientes() {
    cardPorCliente = {};//IMPORTANTISSIMO HEIN
    clientes.innerHTML = "";

    const result = await lib.post("buscar_clientes", dados_pesquisa);

    if (result.response.ok) {
        const array = await result.response.json();
        array.forEach(element => {
            criarCard(element);
        });
    } else {
        clientes.innerHTML = `<h1>Erro: ${result.response.message}</h1>`;
    }
}

input.addEventListener("keydown", function (event) {
    if (event.key === "Enter") {
        dados_pesquisa.pesquisa = event.target.value;

        buscarClientes();
    }
});

dropdown.addEventListener("change", function (event) {
    dados_pesquisa.ordem = event.target.value;

    buscarClientes();
});

qtds.forEach(element => {
    const b = document.createElement("button");
    b.type = "button";
    b.className = "btn btn-outline-primary";
    b.textContent = element;
    paiBotoesQtd.appendChild(b);
    b.addEventListener("click", () => mudarQuantiaMostrada(element));
});

buscarClientes();
