package su.daycube;

import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Ultimate color utility supporting both simple colors and gradients
 * with minimal performance overhead.
 */
public final class ColorUtil {

    // Pattern constants
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<(#[A-Fa-f0-9]{6})>(.*?)</(#[A-Fa-f0-9]{6})>");
    private static final Pattern HEX_PATTERN = Pattern.compile("(&#([A-Fa-f0-9]{6}))");

    // Reflection for hex color support (1.16+)
    private static final boolean HEX_SUPPORTED;

    static {
        boolean supported;
        try {
            ChatColor.of(Color.BLACK); // Test hex color support
            supported = true;
        } catch (NoSuchMethodError | IllegalArgumentException e) {
            supported = false;
        }
        HEX_SUPPORTED = supported;
    }

    private ColorUtil() {
        throw new AssertionError("Utility class cannot be instantiated");
    }

    /**
     * Colorizes text with support for:
     * - Legacy colors (&a, &b, etc)
     * - Hex colors (&#FFFFFF)
     * - Gradients (<#FF0000>text</#00FF00>)
     *
     * @param text The text to colorize
     * @return Colorized text, or null if input was null
     */
    @Nullable
    public static String colorize(@Nullable String text) {
        if (text == null) return null;

        // Process gradients first
        text = processGradients(text);

        // Process hex colors if supported
        if (HEX_SUPPORTED) {
            text = processHexColors(text);
        }

        // Process legacy colors last
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colorizes a list of strings
     *
     * @param texts List of texts to colorize
     * @return Colorized list, or null if input was null
     */
    @Nullable
    public static List<String> colorize(@Nullable List<String> texts) {
        if (texts == null) return null;

        List<String> result = new ArrayList<>(texts.size());
        for (String text : texts) {
            result.add(colorize(text));
        }
        return result;
    }

    // Private processing methods

    @NotNull
    private static String processGradients(@NotNull String text) {
        if (!HEX_SUPPORTED) return text;

        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            try {
                Color start = Color.decode(matcher.group(1));
                String content = matcher.group(2);
                Color end = Color.decode(matcher.group(3));

                String gradient = createGradient(content, start, end);
                matcher.appendReplacement(buffer, gradient);
            } catch (IllegalArgumentException e) {
                // Skip invalid gradients
                matcher.appendReplacement(buffer, matcher.group(0));
            }
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    @NotNull
    private static String processHexColors(@NotNull String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            try {
                ChatColor color = ChatColor.of(matcher.group(1).substring(1));
                matcher.appendReplacement(buffer, color.toString());
            } catch (IllegalArgumentException e) {
                // Skip invalid hex colors
                matcher.appendReplacement(buffer, matcher.group(0));
            }
        }

        matcher.appendTail(buffer);
        return buffer.toString();
    }

    @NotNull
    private static String createGradient(@NotNull String text, @NotNull Color start, @NotNull Color end) {
        if (text.isEmpty()) return text;

        List<String> characters = splitColoredCharacters(text);
        if (characters.isEmpty()) return text;

        StringBuilder gradient = new StringBuilder();
        int length = characters.size();

        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            Color color = interpolate(start, end, ratio);
            ChatColor chatColor = ChatColor.of(color);

            String character = characters.get(i);
            gradient.append(chatColor).append(character);
        }

        return gradient.toString();
    }

    @NotNull
    private static List<String> splitColoredCharacters(@NotNull String text) {
        List<String> result = new ArrayList<>();
        StringBuilder colors = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '§' && i + 1 < text.length()) {
                colors.append(c).append(text.charAt(i + 1));
                i++; // Skip next character
            } else {
                result.add(colors.toString() + c);
                colors.setLength(0); // Reset colors
            }
        }

        return result;
    }

    @NotNull
    private static Color interpolate(@NotNull Color start, @NotNull Color end, float ratio) {
        int red = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
        int green = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
        int blue = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));

        return new Color(
                Math.max(0, Math.min(255, red)),
                Math.max(0, Math.min(255, green)),
                Math.max(0, Math.min(255, blue))
        );
    }
}
