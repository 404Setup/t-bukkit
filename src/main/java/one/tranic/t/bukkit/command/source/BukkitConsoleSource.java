package one.tranic.t.bukkit.command.source;

import net.kyori.adventure.text.Component;
import one.tranic.t.base.command.source.SystemCommandSource;
import one.tranic.t.base.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BukkitConsoleSource extends SystemCommandSource<CommandSender, Player> {
    public BukkitConsoleSource() {
    }

    @Override
    public void broadcastMessage(@NotNull Component message) {
        Bukkit.broadcastMessage(Message.toString(message));
    }

    @Override
    public void broadcastMessage(@NotNull String message) {
        Bukkit.broadcastMessage(message);
    }

    @Override
    public CommandSender getSource() {
        return Bukkit.getConsoleSender();
    }

    @Override
    public void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        Bukkit.getConsoleSender().sendMessage(Message.toString(message));
    }
}
