toggle = document.getElementById("mostrar_senha")
input = document.getElementById("senha_input")

var escondido = true

toggle.addEventListener("click", () => {
    if(escondido){
        toggle.textContent = "Esconder";
        input.type = "text";
    }else{
        toggle.textContent = "Mostrar";
        input.type = "password";
    }

    escondido = !escondido;
});
//meu deus como js é terrível, maldito seja o desenvolvimento web
