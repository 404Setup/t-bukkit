package one.tranic.t.bukkit.command.source;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import one.tranic.t.base.TBase;
import one.tranic.t.base.command.Operator;
import one.tranic.t.base.command.source.CommandSource;
import one.tranic.t.base.message.Message;
import one.tranic.t.bukkit.TBukkit;
import one.tranic.t.bukkit.player.BukkitPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class BukkitSource implements CommandSource<CommandSender, Player> {
    private final CommandSender commandSender;
    private final String[] args;
    private final BukkitPlayer player;

    public BukkitSource(CommandSender commandSender, String[] args) {
        this.commandSender = commandSender;
        this.args = args;
        this.player = commandSender instanceof Player ? new BukkitPlayer(commandSender) : null;
    }

    @Override
    public Operator getOperator() {
        if (player != null)
            return new Operator(player.getUsername(), player.getUniqueId());
        return TBase.console();
    }

    @Override
    public CommandSender getSource() {
        return commandSender;
    }

    @Override
    public boolean isPlayer() {
        return player != null;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public int argSize() {
        return args.length;
    }

    @Override
    public @Nullable Locale getLocale() {
        return player != null ? player.getLocale() : Locale.getDefault();
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSender.hasPermission(permission);
    }

    @Override
    public void sendMessage(String message) {
        commandSender.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        commandSender.sendMessage(Message.toString(message));
    }

    @Override
    @SuppressWarnings("all")
    public void showBossBar(@NotNull BossBar bossBar) {
        if (isPlayer()) TBukkit.adventure().player(player.getSourcePlayer()).showBossBar(bossBar);

    }

    @Override
    @SuppressWarnings("all")
    public void hideBossBar(@NotNull BossBar bossBar) {
        if (isPlayer()) TBukkit.adventure().player(player.getSourcePlayer()).hideBossBar(bossBar);
    }

    @Override
    public void clearBossBars() {
        // Unsupported
    }

    @Override
    @SuppressWarnings("all")
    public void showTitle(@NotNull Title title) {
        if (isPlayer()) TBukkit.adventure().player(player.getSourcePlayer()).showTitle(title);
    }

    @Override
    @SuppressWarnings("all")
    public void clearTitle() {
        if (isPlayer()) TBukkit.adventure().player(player.getSourcePlayer()).clearTitle();
    }

    @Override
    public @Nullable BukkitPlayer asPlayer() {
        return player;
    }
}
