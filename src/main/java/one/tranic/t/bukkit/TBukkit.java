package one.tranic.t.bukkit;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import one.tranic.t.base.TInterface;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.base.player.Player;
import one.tranic.t.bukkit.command.source.BukkitConsoleSource;
import one.tranic.t.bukkit.player.BukkitPlayer;
import one.tranic.t.utils.Collections;
import one.tranic.t.utils.minecraft.Platform;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class TBukkit implements TInterface<CommandSender, org.bukkit.entity.Player> {
    private static TBukkit instance;
    private final Plugin plugin;
    private final Platform[] supportedPlatforms = new Platform[]{Platform.Spigot};
    private boolean initialized = false;
    private BukkitAudiences adventure;

    public TBukkit(Plugin plugin) {
        this.plugin = plugin;
        enable();
        instance = this;
    }

    public static TBukkit getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Tried to access TBukkit when the plugin was disabled!");
        }
        return instance;
    }

    public static @NotNull BukkitAudiences adventure() {
        if (TBukkit.getInstance().adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return TBukkit.getInstance().adventure;
    }

    @Override
    public void enable() {
        if (initialized) return;
        adventure = BukkitAudiences.create(plugin);
        initialized = true;
    }

    @Override
    public void disable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
        initialized = false;
    }

    @Override
    public Platform[] getSupportedPlatforms() {
        return supportedPlatforms;
    }

    @Override
    public SystemCommandSource<CommandSender, org.bukkit.entity.Player> getConsoleSource() {
        return new BukkitConsoleSource();
    }

    @Override
    public @Nullable Player<org.bukkit.entity.Player> getPlayer(@NotNull String name) {
        return BukkitPlayer.createPlayer(Bukkit.getPlayer(name));
    }

    @Override
    public @Nullable Player<org.bukkit.entity.Player> getPlayer(@NotNull UUID uuid) {
        return BukkitPlayer.createPlayer(Bukkit.getPlayer(uuid));
    }

    @Override
    public @NotNull List<Player<org.bukkit.entity.Player>> getOnlinePlayers() {
        final List<Player<org.bukkit.entity.Player>> players = Collections.newArrayList();
        for (var p : Bukkit.getOnlinePlayers())
            players.add(BukkitPlayer.createPlayer(p));
        return players;
    }

    @Override
    public @NotNull List<org.bukkit.entity.Player> getPlatformOnlinePlayers() {
        return Collections.newArrayList(Bukkit.getOnlinePlayers());
    }

    @Override
    public @NotNull List<String> getOnlinePlayersName() {
        final List<String> players = Collections.newArrayList();
        for (var p : Bukkit.getOnlinePlayers())
            players.add(p.getName());
        return players;
    }
}
