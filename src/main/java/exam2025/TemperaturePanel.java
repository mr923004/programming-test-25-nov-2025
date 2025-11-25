package exam2025;

import Feeds.EnvironmentalSystem;
import Feeds.TemperatureFeed;

import java.text.DecimalFormat;

 //Temperature reading cell for a single ward

public class TemperaturePanel extends EnvironmentalSystem.ReadingPanel {

    private final EnvironmentalSystem envSystem;
    private final TemperatureFeed tempFeed;
    private final int wardId;
    private final String wardName;
    private final double idealTemp;
    private final DecimalFormat oneDp = new DecimalFormat("0.0");

    public TemperaturePanel(EnvironmentalSystem envSystem,
                            TemperatureFeed tempFeed,
                            int wardId,
                            String wardName,
                            double idealTemp) {
        super();
        this.envSystem = envSystem;
        this.tempFeed = tempFeed;
        this.wardId = wardId;
        this.wardName = wardName;
        this.idealTemp = idealTemp;
    }

    @Override
    public void updateReading() {
        float current = tempFeed.getTemperature(wardId);

        //control logic for heating

        boolean heatingOn = envSystem.isHeatingOn(wardId);
        if (current < idealTemp - 0.5) {
            envSystem.turnHeatingOn(wardId, true);
            heatingOn = true;
        } else if (current > idealTemp + 0.5) {
            envSystem.turnHeatingOn(wardId, false);
            heatingOn = false;
        }

        boolean warning = Math.abs(current - idealTemp) > 0.5;

        String txt = String.format(
                "%s: %s°C (ideal %s°C) – heating %s",
                wardName,
                oneDp.format(current),
                oneDp.format(idealTemp),
                heatingOn ? "ON" : "OFF"
        );

        updateDisplay(txt, warning);
    }
}