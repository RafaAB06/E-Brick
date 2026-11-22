export async function get(path) {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), 5000);

    const result = {
        response: null,
        message: "Sucesso",
    };

    try {
        const response = await fetch(path, {
            method: 'GET',
            signal: controller.signal
        });
        if(!response.ok){
            result.message = await response.text();
        }
        result.response = response;

    } catch (error) {
        result.message = error.name === 'AbortError'
            ? 'Tempo limite de conexão excedido.'
            : error.message;
    } finally {
        clearTimeout(timeout);
    }

    return result;
}

export async function post(path, json) {
    const controller = new AbortController();
    const timeout = setTimeout(() => controller.abort(), 5000);

    const result = {
        response: null,
        message: "Sucesso",
    };

    try {
        const response =  await fetch(path, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json),
            signal: controller.signal
        });
        if(!response.ok){
            result.message = await response.text();
        }
        result.response = response;

    } catch (error) {
        result.message = error.name === 'AbortError'
            ? 'Tempo limite de conexão excedido.'
            : error.message;
    } finally {
        clearTimeout(timeout);
    }

    return result;
}

export async function criarBotoes(){
    const mains = document.getElementsByTagName("main");
    const html = `
        <aside class="sidebar p-3">
            <h4 class="text-white mb-4">&-Brick</h4>
            <nav class="mt-2" id="botoes"></nav>
            <hr class="border-secondary">
            <small class="text-secondary px-3">CONTA</small>
            <nav class="mt-2">
                <a href="/logout">Sair</a>
            </nav>
        </aside>
    `;
    mains[0].insertAdjacentHTML("beforebegin", html);

    const paiBotoes = document.getElementById("botoes");
    const status = document.getElementById("status");

    const result = await get("/abas");
    if(result.response?.ok){
        const array = await result.response.json();

        array.forEach(element => {
            var botao = document.createElement("a");//botao bonito, melhor que button feio

            botao.href = `/${element.id}`;//torcer pra nao quebrar nada
            botao.textContent = element.nome;

            paiBotoes.appendChild(botao);
        });
    }else{
        mains[0].innerHTML = `<h1>${result.message}</h1>`;
    }
}

export function mostrar(obj){
    obj.classList.remove("hidden");
}

export function esconder(obj){
    obj.classList.add("hidden");
}

export function formatarCPF(cpf){
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4");
}

export function formatarCNPJ(cnpj){
    return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, "$1.$2.$3/$4-$5");
}

export function formatarTelefone(telefone){
    return telefone.replace(/^(\d{2})(\d*)(\d{4})/, "($1) $2-$3")
}
