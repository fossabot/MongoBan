package one.tranic.mongoban.common.commands;

import one.tranic.mongoban.api.MongoBanAPI;
import one.tranic.mongoban.api.command.Command;
import one.tranic.mongoban.api.command.source.SourceImpl;
import one.tranic.mongoban.common.Collections;

import java.util.List;

// Todo
public class ViewCommand<C extends SourceImpl<?, ?>> extends Command<C> {

    public ViewCommand() {
        setName("view");
        setPermission("mongoban.command.view");
    }

    @Override
    public void execute(C source) {

    }

    @Override
    public List<String> suggest(C source) {
        String[] args = source.getArgs();
        int size = source.argSize();
        if (size == 1) return Collections.newUnmodifiableList("--target", "--type", "--strict");
        if (size > 1) {
            String previousArg = args[size - 2];
            String currentArg = args[size - 1];
            if ("--target".equals(previousArg)) return List.of();
            if ("--type".equals(previousArg)) {
                return MongoBanAPI.TIME_SUGGEST.stream()
                        .filter(time -> time.startsWith(currentArg))
                        .toList();
            }
            if ("--strict".equals(previousArg)) {
                return MongoBanAPI.REASON_SUGGEST.stream()
                        .filter(reason -> reason.startsWith(currentArg))
                        .toList();
            }
            if (MongoBanAPI.FLAG_LIST.contains(previousArg)) {
                return MongoBanAPI.FLAG_LIST.stream()
                        .filter(flag -> flag.startsWith(currentArg))
                        .toList();
            }
        }
        return MongoBanAPI.EMPTY_LIST;
    }
}