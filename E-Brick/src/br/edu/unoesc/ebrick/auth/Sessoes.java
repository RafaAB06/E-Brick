
package br.edu.unoesc.ebrick.auth;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import br.edu.unoesc.ebrick.auth.entities.Sessao;
import br.edu.unoesc.ebrick.auth.entities.Usuario;

public final class Sessoes {
	private static Sessoes instancia = new Sessoes();
	public static Sessoes getInstancia() {
        return instancia;
    }
    public static final long TEMPO_SESSAO = 30L * 60 * 1000;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final Map<String, Sessao> sessaoPorToken = new HashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();//pra verificar as sessoes
    //tenho certeza que essa classe viola o primeiro principio SOLID
    
    private Sessoes() {
    	scheduler.scheduleAtFixedRate(//nem sabia que tinha isso
            () -> removerExpiradas(),
            1,
            1,
            TimeUnit.MINUTES
        );
    }

    public synchronized String criarSessao(Usuario usuario) {
        byte[] bytes = new byte[64];
        String token;

        do {
            RANDOM.nextBytes(bytes);
            token = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
        } while (sessaoPorToken.containsKey(token));

        sessaoPorToken.put(token, new Sessao(usuario, token, TEMPO_SESSAO));

        return token;
    }
    
    public Usuario getUsuarioPorToken(String token) {
    	return sessaoPorToken.get(token).getUsuario();
    }

    public synchronized boolean sessaoValida(String token) {
        Sessao sessao = sessaoPorToken.get(token);
        boolean valida = true;
        
        if (sessao == null || sessao.expirou()) {
            sessaoPorToken.remove(token);
            valida = false;
        }else {
            sessao.reset();
        }

        return valida;
    }

    private synchronized void removerExpiradas() {
        Iterator<Map.Entry<String, Sessao>> iterator =
            sessaoPorToken.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Sessao> entry = iterator.next();

            if (entry.getValue().expirou()) {
                iterator.remove();
            }
        }
    }
}
