package su.daycube;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ColorUtil {

    /**
     * Applies color codes to the given value.
     *
     * @param value the value to colorize
     * @param <T>   the type of the value
     * @return the colorized value
     */
    @Nullable
    public static <T> T colorize(@Nullable T value) {
        if (value != null) {
            if (value instanceof String) {
                return (T) formatColorCodes((String) value);
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                if (!list.isEmpty() && list.get(0) instanceof String) {
                    list.replaceAll(o -> formatColorCodes((String) o));
                    return (T) list;
                }
            }
        }
        return value;
    }

    /**
     * Removes color codes from the given value.
     *
     * @param value the value to uncolorize
     * @param <T>   the type of the value
     * @return the uncolorized value
     */
    @Nullable
    public static <T> T uncolorize(@Nullable T value) {
        if (value != null) {
            if (value instanceof String) {
                return (T) ((String) value).replace(ChatColor.COLOR_CHAR, '&');
            } else if (value instanceof List) {
                List<Object> list = (List<Object>) value;
                if (!list.isEmpty() && list.get(0) instanceof String) {
                    list.replaceAll(o -> ((String) o).replace(ChatColor.COLOR_CHAR, '&'));
                    return (T) list;
                }
            }
        }
        return value;
    }

    /**
     * Formats the color codes in the given string.
     *
     * @param str the string to format
     * @return the formatted string
     */
    @Contract("null -> null")
    private static String formatColorCodes(@Nullable String str) {
        if (str == null) return null;
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    /**
     * Formats the color codes in the given list of strings.
     *
     * @param listStr the list of strings to format
     * @return the formatted list of strings
     */
    @Contract("null -> null")
    private static List<String> formatColorCodes(@Nullable List<String> listStr) {
        if (listStr == null) return null;
        return listStr.stream().filter(Objects::nonNull)
                .map(ColorUtil::formatColorCodes).collect(Collectors.toList());
    }
}
