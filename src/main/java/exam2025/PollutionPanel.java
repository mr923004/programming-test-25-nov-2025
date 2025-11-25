package exam2025;

import Feeds.EnvironmentalSystem;
import Feeds.PollutionFeed;

import java.text.DecimalFormat;

// Air pollution reading cell.

public class PollutionPanel extends EnvironmentalSystem.ReadingPanel {

    private final EnvironmentalSystem envSystem;
    private final PollutionFeed pollutionFeed;
    private final DecimalFormat oneDp = new DecimalFormat("0.0");

    public PollutionPanel(EnvironmentalSystem envSystem,
                          PollutionFeed pollutionFeed) {
        super();
        this.envSystem = envSystem;
        this.pollutionFeed = pollutionFeed;
    }

    @Override
    public void updateReading() {
        float pollution = pollutionFeed.getPollution();

        boolean warning = pollution > 5.0f;

        // Control air purifier
        if (warning) {
            envSystem.turnAirPurifierOn(true);
        } else {
            envSystem.turnAirPurifierOn(false);
        }

        boolean purifierOn = envSystem.isAirPurifierOn();

        String txt = String.format(
                "PM2.5: %s μg/m³ – air purifier %s",
                oneDp.format(pollution),
                purifierOn ? "ON" : "OFF"
        );

        updateDisplay(txt, warning);
    }
}
