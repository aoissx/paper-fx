package net.aoissx.mc.paperfx;

import net.aoissx.mc.paperfx.commands.Fx;
import net.aoissx.mc.paperfx.db.Database;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Paper_fx extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        // Database
        Database db = new Database();
        db.Init();
        // Commands
        Objects.requireNonNull(getCommand("fx")).setExecutor(new Fx());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
