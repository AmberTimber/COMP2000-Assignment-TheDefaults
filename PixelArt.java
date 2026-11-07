import java.awt.Color;
import java.awt.Graphics;
import java.util.Map;

// shared helpers for the 2D pixel-art look: a fixed grid size, deterministic noise for
// textures, a tiny 3x5 bitmap font, string-based sprites and chunky bevelled panels
public final class PixelArt {
    public static final int PX = 5; // size of one "pixel" on the world grid

    private PixelArt() {
    }

    // cheap deterministic noise so textures look random but are identical every frame
    public static int hash(int x, int y) {
        int h = x * 73856093 ^ y * 19349663;
        h ^= h >>> 13;
        h *= 0x5bd1e995;
        h ^= h >>> 15;
        return h & 0x7fffffff;
    }

    // fills one grid cell
    public static void cell(Graphics g, int col, int row, Color color) {
        g.setColor(color);
        g.fillRect(col * PX, row * PX, PX, PX);
    }

    // fills every grid cell in [x, x+w) x [y, y+h) (pixel coordinates, snapped to the grid),
    // picking a colour per cell from the palette using noise: palette[0] is the base colour
    // and later entries appear progressively less often
    public static void texture(Graphics g, int x, int y, int w, int h, Color[] palette, int seed) {
        int startCol = Math.floorDiv(x, PX);
        int endCol = Math.floorDiv(x + w - 1, PX);
        int startRow = Math.floorDiv(y, PX);
        int endRow = Math.floorDiv(y + h - 1, PX);
        for (int col = startCol; col <= endCol; col++) {
            for (int row = startRow; row <= endRow; row++) {
                int n = hash(col + seed, row - seed);
                Color c = palette[0];
                for (int i = palette.length - 1; i > 0; i--) {
                    if (n % (5 + i * 4) == 0) {
                        c = palette[i];
                        break;
                    }
                }
                cell(g, col, row, c);
            }
        }
    }

    // ---------- sprites ----------

    // draws a sprite given as rows of characters; each character is looked up in the palette
    // ('.' or any unmapped character is transparent) and drawn as a size x size block
    public static void sprite(Graphics g, String[] rows, int x, int y, int size, Map<Character, Color> palette) {
        for (int r = 0; r < rows.length; r++) {
            for (int c = 0; c < rows[r].length(); c++) {
                Color color = palette.get(rows[r].charAt(c));
                if (color != null) {
                    g.setColor(color);
                    g.fillRect(x + c * size, y + r * size, size, size);
                }
            }
        }
    }

    // chunky panel with a light top-left edge and dark bottom-right edge
    public static void bevelBox(Graphics g, int x, int y, int w, int h, int border, Color fill, Color light, Color dark) {
        g.setColor(dark);
        g.fillRect(x, y, w, h);
        g.setColor(light);
        g.fillRect(x, y, w - border, h - border);
        g.setColor(fill);
        g.fillRect(x + border, y + border, w - border * 2, h - border * 2);
    }

    // ---------- 3x5 bitmap font ----------

    private static final String[] DIGITS = {
        "111101101101111", "010110010010111", "111001111100111", "111001111001111", "101101111001001",
        "111100111001111", "111100111101111", "111001010010010", "111101111101111", "111101111001111"
    };
    private static final String[] LETTERS = {
        "010101111101101", "110101110101110", "011100100100011", "110101101101110", "111100110100111", // A-E
        "111100110100100", "011100101101011", "101101111101101", "111010010010111", "001001001101010", // F-J
        "101101110101101", "100100100100111", "101111111101101", "111101101101101", "010101101101010", // K-O
        "110101110100100", "010101101110011", "110101110101101", "011100010001110", "111010010010010", // P-T
        "101101101101111", "101101101101010", "101101111111101", "101101010101101", "101101010010010", // U-Y
        "111001010100111"                                                                              // Z
    };

    private static String glyph(char ch) {
        char c = Character.toUpperCase(ch);
        if (c >= '0' && c <= '9') return DIGITS[c - '0'];
        if (c >= 'A' && c <= 'Z') return LETTERS[c - 'A'];
        switch (c) {
            case ' ': return "000000000000000";
            case '.': return "000000000000010";
            case ',': return "000000000010100";
            case ':': return "000010000010000";
            case '-': return "000000111000000";
            case '_': return "000000000000111";
            case '+': return "000010111010000";
            case '/': return "001001010100100";
            case '(': return "010100100100010";
            case ')': return "010001001001010";
            case '!': return "010010010000010";
            case '\'': return "010010000000000";
            case '%': return "101001010100101";
            case '#': return "101111101111101";
            default: return "110001010000010"; // '?'
        }
    }

    // width in pixels of text drawn at this scale (each glyph is 3 wide plus a 1 gap)
    public static int textWidth(String text, int scale) {
        return text.isEmpty() ? 0 : (text.length() * 4 - 1) * scale;
    }

    // draws text with its top-left corner at (x, y); each font pixel is scale x scale
    public static void text(Graphics g, String text, int x, int y, int scale, Color color) {
        g.setColor(color);
        for (int i = 0; i < text.length(); i++) {
            String bits = glyph(text.charAt(i));
            int gx = x + i * 4 * scale;
            for (int b = 0; b < 15; b++) {
                if (bits.charAt(b) == '1') {
                    g.fillRect(gx + (b % 3) * scale, y + (b / 3) * scale, scale, scale);
                }
            }
        }
    }

    // text with a 1-pixel dark drop shadow so it stays readable over any background
    public static void shadowText(Graphics g, String text, int x, int y, int scale, Color color, Color shadow) {
        text(g, text, x + scale / 2 + 1, y + scale / 2 + 1, scale, shadow);
        text(g, text, x, y, scale, color);
    }
}
