package exam2025;

//Humidity reading cell (inside/outside + dehumidifier state).

import Feeds.EnvironmentalSystem;
import Feeds.HumidityFeed;

public class HumidityPanel extends EnvironmentalSystem.ReadingPanel {

    private final EnvironmentalSystem envSystem;
    private final HumidityFeed humidityFeed;

    public HumidityPanel(EnvironmentalSystem envSystem,
                         HumidityFeed humidityFeed) {
        super();
        this.envSystem = envSystem;
        this.humidityFeed = humidityFeed;
    }

    @Override
    public void updateReading() {
        int inside = humidityFeed.getInsideHumidity();
        int outside = humidityFeed.getOutsideHumidity();
        int diff = inside - outside;

        boolean warning = diff > 10;

        // Control dehumidifier
        if (warning) {
            envSystem.turnDehumudifierOn(true);
        } else {
            envSystem.turnDehumudifierOn(false);
        }

        boolean dehumOn = envSystem.isDehumudifierOn();

        String txt = String.format(
                "Humidity: inside %d%%, outside %d%%, diff %d%% – dehumidifier %s",
                inside, outside, diff,
                dehumOn ? "ON" : "OFF"
        );

        updateDisplay(txt, warning);
    }
}

