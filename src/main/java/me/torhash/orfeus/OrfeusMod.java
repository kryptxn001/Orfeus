package me.torhash.orfeus;

import me.torhash.orfeus.util.SessionUtils;
import net.fabricmc.api.ModInitializer;

public class OrfeusMod implements ModInitializer {
    @Override
    public void onInitialize() {
        SessionUtils.createSession("krystof.spirek@gmail.com","xxx");
        System.out.println("Orfeus Loaded!");
    }
}
