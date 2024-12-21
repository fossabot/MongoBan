package one.tranic.mongoban.api.command.source;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import one.tranic.mongoban.api.command.MessageSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;

/**
 * This class represents a source implementation for BungeeCord command senders.
 *
 * @deprecated BungeeCord is considered outdated. It is recommended to use more modern proxies such as Velocity.
 * <p>
 * Developing plugins on modern platforms like Paper and Velocity is easier and provides better support and features.
 */
@Deprecated
public class BungeeSource implements SourceImpl<CommandSender> {
    private final CommandSender commandSender;
    private final String[] args;
    private final boolean isPlayer;

    /**
     * Constructs a new BungeeSource instance.
     *
     * @param commandSender the CommandSender instance
     * @param args          the arguments passed with the command
     * @deprecated BungeeCord support is deprecated; consider migrating to modern platforms like Velocity.
     */
    @Deprecated
    public BungeeSource(CommandSender commandSender, String[] args) {
        this.commandSender = commandSender;
        this.args = args;
        this.isPlayer = commandSender instanceof ProxiedPlayer;
    }

    @Override
    public CommandSender getSource() {
        return commandSender;
    }

    @Override
    public boolean isPlayer() {
        return isPlayer;
    }

    @Override
    public boolean kick() {
        if (!isPlayer) return false;
        ((ProxiedPlayer) commandSender).disconnect();
        return true;
    }

    @Override
    public boolean kick(String reason) {
        if (!isPlayer) return false;
        ((ProxiedPlayer) commandSender).disconnect(reason);
        return true;
    }

    @Override
    public boolean kick(@NotNull Component reason) {
        if (!isPlayer) return false;
        ((ProxiedPlayer) commandSender).disconnect(LegacyComponentSerializer.legacySection().serialize(reason));
        return true;
    }

    @Override
    public String getName() {
        return commandSender.getName();
    }

    @Override
    public @Nullable UUID getUniqueId() {
        return isPlayer ? ((ProxiedPlayer) commandSender).getUniqueId()
                : null;
    }

    @Override
    public String[] getArgs() {
        return args;
    }

    @Override
    public @Nullable Locale locale() {
        return isPlayer ? ((ProxiedPlayer) commandSender).getLocale() : Locale.getDefault();
    }

    @Override
    public void sendMessage(String message) {
        commandSender.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        MessageSender.bungeeAdventure().sender(commandSender).sendMessage(message);
    }
}
