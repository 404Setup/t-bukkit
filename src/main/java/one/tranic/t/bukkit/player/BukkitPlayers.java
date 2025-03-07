package one.tranic.t.bukkit.player;

import one.tranic.t.base.player.Player;
import one.tranic.t.utils.Collections;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class BukkitPlayers {
    public static @Nullable Player<?> getPlayer(@NotNull String name) {
        return BukkitPlayer.createPlayer(Bukkit.getPlayer(name));
    }

    public static @Nullable Player<?> getPlayer(@NotNull UUID uuid) {
        return BukkitPlayer.createPlayer(Bukkit.getPlayer(uuid));
    }

    public static @NotNull List<Player<?>> getOnlinePlayers() {
        final List<Player<?>> players = Collections.newArrayList();
        for (var p : Bukkit.getOnlinePlayers())
            players.add(BukkitPlayer.createPlayer(p));
        return players;
    }

    public static @NotNull List<? extends org.bukkit.entity.Player> getPlatformOnlinePlayers() {
        return Collections.newArrayList(Bukkit.getOnlinePlayers());
    }

    public static @NotNull List<String> getOnlinePlayersName() {
        final List<String> players = Collections.newArrayList();
        for (var p : Bukkit.getOnlinePlayers())
            players.add(p.getName());
        return players;
    }
}
