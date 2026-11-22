import * as lib from "../lib.js";

const titulo = document.getElementById("titulo");
const campo_nome = document.getElementById("nome");
const titulo_cpf_cnpj = document.getElementById("cpf_cnpj");
const campo_cpf_cnpj = document.getElementById("cpf_cnpj_valor");
const campo_email = document.getElementById("email");
const campo_telefone = document.getElementById("telefone");
const campo_endereco = document.getElementById("endereco");
const campo_cidade = document.getElementById("cidade");
const campo_estado = document.getElementById("estado");
const campo_ativo = document.getElementById("ativo");
const ativo_label = document.getElementById("ativo-label");

const tipo = document.getElementById("tipo");

const enviar = document.getElementById("salvar");
const botao_excluir = document.getElementById("excluir");
const botao_fechar = document.getElementById("fechar-edicao");
const botao_adicionar = document.getElementById("adicionar-cliente");
const erro = document.getElementById("salvar_erro");

const overlay = document.getElementById("overlay");

let clienteAtual = null;
let modo = null;

async function atualizarCliente() {
    const cpf_cnpj = campo_cpf_cnpj.value;
    const json = {
        id: clienteAtual.id,
        nome: campo_nome.value,
        email: campo_email.value,
        telefone: campo_telefone.value,
        cpf: tipo.value == 'cpf' ? cpf_cnpj : null,
        cnpj: tipo.value == 'cnpj' ? cpf_cnpj : null,
        endereco: campo_endereco.value,
        cidade: campo_cidade.value,
        estado: campo_estado.value,
        ativo: campo_ativo.checked
    };

    const result = await lib.post("atualizar_cliente", json);

    if (result.response?.ok) {
        esconder();
        window.location.reload();
    } else {
        erro.textContent = result.message;
    }

    enviar.disabled = false;
}

async function adicionarCliente() {
    const cpf_cnpj = campo_cpf_cnpj.value;
    const json = {
        nome: campo_nome.value,
        email: campo_email.value,
        telefone: campo_telefone.value,
        cpf: tipo.value == 'cpf' ? cpf_cnpj : null,
        cnpj: tipo.value == 'cnpj' ? cpf_cnpj : null,
        endereco: campo_endereco.value,
        cidade: campo_cidade.value,
        estado: campo_estado.value,
    };
    const result = await lib.post("adicionar_cliente", json);

    if (result.response?.ok) {
        esconder();
        window.location.reload();
    } else {
        erro.textContent = result.message;
    }

    enviar.disabled = false;
}

export function selecaoCliente(cliente) {
    titulo.textContent = "Editar Cliente";
    modo = "editar";
    clienteAtual = cliente;

    campo_nome.value = cliente.nome;
    campo_email.value = cliente.email;
    campo_telefone.value = cliente.telefone;
    campo_endereco.value = cliente.endereco;
    campo_cidade.value = cliente.cidade;
    campo_estado.value = cliente.estado;
    campo_ativo.checked = cliente.ativo;

    if (cliente.cpf) {
        campo_cpf_cnpj.value = cliente.cpf;
        tipo.value = "cpf";
        titulo_cpf_cnpj.textContent = "CPF";
    } else {
        campo_cpf_cnpj.value = cliente.cnpj;
        tipo.value = "cnpj";
        titulo_cpf_cnpj.textContent = "CNPJ";
    }

    mostrar();
}

function adicaoCliente(){
    titulo.textContent = "Adicionar Cliente";
    botao_excluir.classList.add("hidden");
    ativo_label.classList.add("hidden");
    modo = "adicionar";

    campo_nome.value = "";
    campo_cpf_cnpj.value = "";
    campo_email.value = "";
    campo_telefone.value = "";
    campo_endereco.value = "";
    campo_cidade.value = "";
    campo_estado.value = "";

    campo_ativo.classList.add("hidden");

    mostrar();
}

async function excluirCliente(){
    botao_excluir.disabled = true;

    const json = {
        id: clienteAtual.id
    };
    const result = await lib.post("excluir_cliente", json);

    if (result.response?.ok) {
        esconder();
        window.location.reload();
    } else {
        erro.textContent = result.message;
    }

    botao_excluir.disabled = false;
}

function mostrar() {
    overlay.classList.remove("hidden");
    erro.textContent = "";
}

function esconder() {
    clienteAtual = null;
    modo = null;

    overlay.classList.add("hidden");
    botao_excluir.classList.remove("hidden");
    campo_ativo.classList.remove("hidden");
    ativo_label.classList.remove("hidden");
}

enviar.addEventListener('click', () => {
    console.log(modo);
    enviar.disabled = true;
    erro.textContent = "";

    if (modo === "editar") {
        atualizarCliente();
    } else if (modo === "adicionar") {
        adicionarCliente();
    }
});

botao_excluir.addEventListener('click', excluirCliente);
botao_adicionar.addEventListener('click', adicaoCliente);
botao_fechar.addEventListener('click', esconder);
tipo.addEventListener('change', () => {
    titulo_cpf_cnpj.textContent = tipo.options[tipo.selectedIndex].text;
});

titulo_cpf_cnpj.textContent = tipo.options[tipo.selectedIndex].text;
