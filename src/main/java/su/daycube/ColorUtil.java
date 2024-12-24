package su.daycube;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColorUtil {

    @Nullable
    public static Object colorize(@Nullable Object value) {
        if (value != null) {
            if (value instanceof String) {
                return formatColorCodes((String) value);
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                if (!list.isEmpty() && list.get(0) instanceof String) {
                    list.replaceAll(o -> formatColorCodes((String) o));
                    return list;
                }
            }
        }
        return value;
    }

    @Nullable
    public static Object uncolorize(@Nullable Object value) {
        if (value != null) {
            if (value instanceof String) {
                return ((String) value).replace(ChatColor.COLOR_CHAR, '&');
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                if (!list.isEmpty() && list.get(0) instanceof String) {
                    list.replaceAll(o -> ((String) o).replace(ChatColor.COLOR_CHAR, '&'));
                    return list;
                }
            }
        }
        return value;
    }

    @Contract("null -> null")
    public static String formatColorCodes(@Nullable String str) {
        if (str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    @Contract("null -> null")
    public static List<String> formatColorCodes(@Nullable List<String> listStr) {
        if (listStr == null) return null;
        return listStr.stream().filter(Objects::nonNull)
                .map(ColorUtil::formatColorCodes).collect(Collectors.toList());
    }
}
