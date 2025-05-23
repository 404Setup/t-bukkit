package one.tranic.t.bukkit;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import one.tranic.t.base.TBase;
import one.tranic.t.base.command.source.CommandSource;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.base.player.Player;
import one.tranic.t.base.player.Players;
import one.tranic.t.bukkit.command.source.BukkitConsoleSource;
import one.tranic.t.bukkit.player.BukkitPlayers;
import one.tranic.t.utils.Reflect;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TBukkit {
    private static boolean initialized = false;
    private static BukkitAudiences adventure;

    public static @NotNull BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    public static void init(Plugin plugin) {
        if (initialized) return;
        adventure = BukkitAudiences.create(plugin);
        try {
            Reflect.setNotNullStaticField(TBase.class, "getConsoleSourceSupplier", (Supplier<CommandSource<?, ?>>) TBukkit::getBukkitConsoleSource);
            Reflect.setNotNullStaticField(Players.class, "getPlayerWithStringMethod", (Function<String, Player<?>>) BukkitPlayers::getPlayer);
            Reflect.setNotNullStaticField(Players.class, "getPlayerWithUUIDMethod", (Function<UUID, Player<?>>) BukkitPlayers::getPlayer);
            Reflect.setNotNullStaticField(Players.class, "getOnlinePlayersMethod", (Supplier<List<Player<?>>>) BukkitPlayers::getOnlinePlayers);
            Reflect.setNotNullStaticField(Players.class, "getPlatformOnlinePlayersMethod", (Supplier<List<?>>) BukkitPlayers::getPlatformOnlinePlayers);
            Reflect.setNotNullStaticField(Players.class, "getOnlinePlayersNameMethod", (Supplier<List<String>>) BukkitPlayers::getOnlinePlayersName);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        initialized = true;
    }

    public static void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        initialized = false;
    }

    public static SystemCommandSource<?, ?> getBukkitConsoleSource() {
        return new BukkitConsoleSource();
    }
}
