package one.tranic.t.bukkit.command.warp;

import one.tranic.t.base.command.simple.SimpleCommand;
import one.tranic.t.bukkit.command.source.BukkitSource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


/**
 * A wrapper class that adapts a {@link SimpleCommand}
 * for use in the Bukkit command system.
 * <p>
 * This class integrates commands defined in the
 * {@link SimpleCommand} structure, making them function
 * within the Bukkit platform by implementing the {@link Command} interface.
 * <p>
 * The class uses a {@link BukkitSource} to represent and execute commands with the
 * associated {@link CommandSender}.
 */
public class BukkitWrap extends Command {
    private final SimpleCommand<BukkitSource> command;

    public BukkitWrap(SimpleCommand<BukkitSource> command) {
        super(command.getName());
        if (command.getPermission() != null) setPermission(command.getPermission());
        if (command.getDescription() != null) setDescription(command.getDescription());
        if (command.getUsage() != null) setUsage(command.getUsage());

        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        command.execute(new BukkitSource(sender, args));
        return true;
    }

    @Override
    public @NotNull java.util.List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return command.suggest(new BukkitSource(sender, args));
    }
}
