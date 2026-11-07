import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

// draws a live flight information board (like an airport departures screen),
// listing each aircraft currently on site with its status, location and gate
public class FlightBoard implements drawable {
    private final ArrayList<Aircraft> aircraftsOnSite;
    private static final int ROW_HEIGHT = 18;
    private static final int HEADER_HEIGHT = 40;
    private static final int PADDING = 10;
    private static final int BOARD_WIDTH = 320;

    public FlightBoard(ArrayList<Aircraft> aircraftsOnSite) {
        this.aircraftsOnSite = aircraftsOnSite;
    }

    // mirrors the exact "Current status" text each plane already draws next to itself
    private String resolveStatus(Aircraft aircraft) {
        return aircraft.getStatus() == null ? "UNKNOWN" : aircraft.getStatus();
    }

    private String resolveGate(Aircraft aircraft) {
        AirwayGate gate = aircraft.getAssignedGate();
        return gate == null ? "-" : gate.getGateID();
    }

    private String trim(String value, int maxLength) {
        if (value == null) {
            return "";
        }
        return value.length() > maxLength ? value.substring(0, maxLength) : value;
    }

    // panelWidth/panelHeight are the size of the area the board should be anchored within,
    // the board itself is drawn pinned to the bottom right corner of that area.
    // drawn in the pixel-art style: chunky bevelled frame, dark LED-style screen with
    // scanline stripes, and all text in the 3x5 pixel font
    @Override
    public void visualRepresentation(Graphics drawer, int panelWidth, int panelHeight) {
        int rowCount = aircraftsOnSite == null ? 0 : aircraftsOnSite.size();
        int boardHeight = HEADER_HEIGHT + Math.max(rowCount, 1) * ROW_HEIGHT + PADDING;
        int x = panelWidth - BOARD_WIDTH - PADDING;
        int y = panelHeight - boardHeight - PADDING;

        // frame and screen
        PixelArt.bevelBox(drawer, x - 4, y - 4, BOARD_WIDTH + 8, boardHeight + 8, 4,
                new Color(70, 74, 86), new Color(120, 124, 138), new Color(30, 32, 40));
        drawer.setColor(new Color(14, 16, 22));
        drawer.fillRect(x, y, BOARD_WIDTH, boardHeight);
        // faint stripe on every other row, like an old dot-matrix board
        drawer.setColor(new Color(24, 28, 36));
        for (int i = 0; i < Math.max(rowCount, 1); i += 2) {
            drawer.fillRect(x, y + HEADER_HEIGHT - 4 + i * ROW_HEIGHT, BOARD_WIDTH, ROW_HEIGHT);
        }

        Color shadow = new Color(0, 0, 0);
        PixelArt.shadowText(drawer, "FLIGHT BOARD", x + PADDING, y + 6, 2, new Color(255, 200, 70), shadow);
        // blinking "live" dot in the corner
        if ((System.currentTimeMillis() / 600) % 2 == 0) {
            drawer.setColor(new Color(230, 60, 50));
            drawer.fillRect(x + BOARD_WIDTH - PADDING - 6, y + 8, 6, 6);
        }

        int headerY = y + 24;
        PixelArt.text(drawer, String.format("%-10s%-8s%-13s%s", "FLIGHT", "TYPE", "STATUS", "GATE"),
                x + PADDING, headerY, 2, new Color(240, 220, 90));

        Color rowColor = new Color(110, 235, 130);
        if (rowCount == 0) {
            PixelArt.text(drawer, "NO ACTIVE FLIGHTS", x + PADDING, headerY + ROW_HEIGHT, 2, rowColor);
        } else {
            for (int i = 0; i < rowCount; i++) {
                Aircraft aircraft = aircraftsOnSite.get(i);
                String type = aircraft instanceof CommercialPlane ? "PAX" : aircraft instanceof CargoPlane ? "CARGO" : "N/A";
                String row = String.format("%-10s%-8s%-13s%s",
                        trim(aircraft.getAircraftID(), 9),
                        type,
                        trim(resolveStatus(aircraft), 12),
                        resolveGate(aircraft));
                PixelArt.text(drawer, row, x + PADDING, headerY + ROW_HEIGHT * (i + 1), 2, rowColor);
            }
        }
    }
}
