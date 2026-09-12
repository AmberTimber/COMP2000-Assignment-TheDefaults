import java.awt.Color;
import java.awt.Font;
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
    // the board itself is drawn pinned to the bottom right corner of that area
    @Override
    public void visualRepresentation(Graphics drawer, int panelWidth, int panelHeight) {
        int rowCount = aircraftsOnSite == null ? 0 : aircraftsOnSite.size();
        int boardHeight = HEADER_HEIGHT + Math.max(rowCount, 1) * ROW_HEIGHT + PADDING;
        int x = panelWidth - BOARD_WIDTH - PADDING;
        int y = panelHeight - boardHeight - PADDING;

        drawer.setColor(new Color(20, 20, 20, 230));
        drawer.fillRect(x, y, BOARD_WIDTH, boardHeight);
        drawer.setColor(Color.WHITE);
        drawer.drawRect(x, y, BOARD_WIDTH, boardHeight);

        drawer.setFont(new Font("Monospaced", Font.BOLD, 14));
        drawer.drawString("FLIGHT BOARD", x + PADDING, y + 18);

        drawer.setFont(new Font("Monospaced", Font.BOLD, 11));
        int headerY = y + 34;
        drawer.setColor(Color.YELLOW);
        drawer.drawString(String.format("%-10s%-8s%-13s%s", "FLIGHT", "TYPE", "STATUS", "GATE"), x + PADDING, headerY);

        drawer.setFont(new Font("Monospaced", Font.PLAIN, 11));
        drawer.setColor(Color.GREEN);
        if (rowCount == 0) {
            drawer.drawString("No active flights", x + PADDING, headerY + ROW_HEIGHT);
        } else {
            for (int i = 0; i < rowCount; i++) {
                Aircraft aircraft = aircraftsOnSite.get(i);
                String type = aircraft instanceof CommercialPlane ? "PAX" : aircraft instanceof CargoPlane ? "CARGO" : "N/A";
                String row = String.format("%-10s%-8s%-13s%s",
                        trim(aircraft.getAircraftID(), 9),
                        type,
                        resolveStatus(aircraft),
                        resolveGate(aircraft));
                drawer.drawString(row, x + PADDING, headerY + ROW_HEIGHT * (i + 1));
            }
        }
    }
}
