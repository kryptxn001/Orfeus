package me.torhash.orfeus.util;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.sun.org.apache.xpath.internal.operations.Or;
import me.torhash.orfeus.Orfeus;
import net.minecraft.client.util.Session;
import java.net.Proxy;

public class SessionUtils {
    public static boolean createSession(String email, String password) {
        YggdrasilUserAuthentication auth =
                (YggdrasilUserAuthentication)new YggdrasilAuthenticationService(
                        Proxy.NO_PROXY, "").createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(email);
        auth.setPassword(password);
        try
        {
            auth.logIn();
            Orfeus.Instance.orfeussession = new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang");

        }catch(AuthenticationUnavailableException e)
        {
            return false;
        }catch(AuthenticationException e)
        {
            return false;

        }catch(NullPointerException e)
        {
            return false;
        }
        return true;
    }
}
