package one.tranic.mongoban.api.command.wrap;

import one.tranic.mongoban.common.command.sources.PaperSource;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

/**
 * A wrapper class that adapts a {@link one.tranic.mongoban.api.command.s.Command}
 * for use in the Bukkit command system.
 * <p>
 * This class integrates commands defined in the
 * {@link one.tranic.mongoban.api.command.s.Command} structure, making them function
 * within the Bukkit platform by implementing the {@link Command} interface.
 * <p>
 * The class uses a {@link PaperSource} to represent and execute commands with the
 * associated {@link CommandSender}.
 */
public class BukkitWrap extends Command {
    private final one.tranic.mongoban.api.command.s.Command<PaperSource> command;

    public BukkitWrap(one.tranic.mongoban.api.command.s.Command<PaperSource> command) {
        super(command.getName());
        if (command.getPermission() != null) setPermission(command.getPermission());
        if (command.getDescription() != null) setDescription(command.getDescription());
        if (command.getUsage() != null) setUsage(command.getUsage());

        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        command.execute(new PaperSource(sender, args));
        return true;
    }

    @Override
    public @NotNull java.util.List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return command.suggest(new PaperSource(sender, args));
    }
}