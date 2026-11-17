import * as lib from "../lib.js";

const usuario_input = document.getElementById('usuario_input')
const senha_input = document.getElementById('senha_input')
const login_erro = document.getElementById('login_erro');

document.getElementById('submit').addEventListener('click', handle_login)

async function handle_login() {//tudo async nessa bomba
    const login = usuario_input.value
    if(!login) return;

    const senha = senha_input.value;//deixa validar a senha vazia mesmo

    const json = {
        login: login,
        senha: senha
    };

    const result = await lib.post("/login", json);
    if(result.response.ok){
        window.location.href = "/sistema/index.html";
    }else{
        login_erro.textContent = result.message;
    }
}