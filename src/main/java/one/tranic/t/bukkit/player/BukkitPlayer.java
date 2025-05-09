package one.tranic.t.bukkit.player;

import net.kyori.adventure.text.Component;
import one.tranic.t.base.player.BedrockPlayer;
import one.tranic.t.base.player.Location;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.Locale;
import java.util.UUID;

public class BukkitPlayer implements one.tranic.t.base.player.Player<Player> {
    private final Player player;

    public BukkitPlayer(Player player) {
        this.player = player;
    }

    public BukkitPlayer(CommandSender commandSender) {
        this.player = (Player) commandSender;
    }

    /**
     * Creates an instance of {@link BukkitPlayer} from the given {@link Player} instance.
     *
     * @param player The player instance to base the {@link BukkitPlayer} on. Can be {@code null}.
     * @return A new {@link BukkitPlayer} instance if the given player is not {@code null},
     * otherwise {@code null}.
     */
    public static @Nullable BukkitPlayer createPlayer(@Nullable Player player) {
        if (player == null) return null;
        return new BukkitPlayer(player);
    }

    /**
     * Creates a {@link BukkitPlayer} instance from a {@link UUID}.
     *
     * @param uuid the unique identifier of the player; must not be null
     * @return a {@link BukkitPlayer} instance if a corresponding player is found, or null if no player is found
     */
    public static @Nullable BukkitPlayer createPlayer(@NotNull UUID uuid) {
        Player p = Bukkit.getPlayer(uuid);
        return createPlayer(p);
    }

    /**
     * Creates a BukkitPlayer instance for the specified username if the player is found.
     *
     * @param username The username of the player to create a BukkitPlayer instance for.
     *                 Must not be null.
     * @return A BukkitPlayer instance corresponding to the player with the provided username,
     * or null if no player with the given username is found.
     */
    public static @Nullable BukkitPlayer createPlayer(@NotNull String username) {
        Player p = Bukkit.getPlayer(username);
        return createPlayer(p);
    }

    @Override
    public @NotNull String getUsername() {
        return player.getName();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public @NotNull String getConnectedHost() {
        @Nullable InetSocketAddress addr = player.getAddress();
        if (addr == null) return "127.0.0.1";
        return addr.getAddress().getHostAddress();
    }

    @Override
    public @NotNull Locale getLocale() {
        return Locale.forLanguageTag(player.getLocale());
    }

    @Override
    public @Nullable Location getLocation() {
        @NotNull var l = player.getLocation();
        return new Location(l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
    }

    @Override
    public long getPing() {
        if (isBedrockPlayer()) {
            long ping = BedrockPlayer.getPing(getUniqueId());
            if (ping != -1) return ping;
        }
        return player.getPing();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public @Nullable String getClientBrand() {
        if (isBedrockPlayer()) return BedrockPlayer.getPlatform(getUniqueId());
        return "Java Edition";
    }

    @Override
    public Player getSourcePlayer() {
        return player;
    }

    @Override
    public boolean kick() {
        player.kickPlayer("Losing connection to server");
        return true;
    }

    @Override
    public boolean kick(String reason) {
        return kick(Component.text(reason));
    }

    @Override
    public boolean kick(@NotNull Component reason) {
        player.kickPlayer(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(reason));
        return true;
    }

    @Override
    public void sendMessage(@NotNull String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        player.sendMessage(net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer.legacySection().serialize(message));
    }
}
