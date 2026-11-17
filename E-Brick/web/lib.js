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
        result.response = await fetch(path, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(json),
            signal: controller.signal
        });

    } catch (error) {
        result.message = error.name === 'AbortError'
            ? 'Tempo limite de conexão excedido.'
            : error.message;
    } finally {
        clearTimeout(timeout);
    }

    return result;
}
