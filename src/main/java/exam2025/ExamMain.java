package exam2025;

import Feeds.EnvironmentalSystem;
import Feeds.HumidityFeed;
import Feeds.PollutionFeed;
import Feeds.TemperatureFeed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExamMain {

    // Ideal temperatures for the three wards
    private static final double NORTH_IDEAL = 23.0;
    private static final double CENTRAL_IDEAL = 22.0;
    private static final double SOUTH_IDEAL = 20.0;

    public static void main(String[] args) {

        //Environmental system and feeds
        EnvironmentalSystem envSystem = new EnvironmentalSystem();
        TemperatureFeed tempFeed = envSystem.getTempFeed();
        HumidityFeed humidityFeed = envSystem.getHumidityFeed();
        PollutionFeed pollutionFeed = envSystem.getPollutionFeed();

        //Build GUI
        JFrame frame = new JFrame("Andressey Hospital Environment Monitor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Grid: 5 rows (3 temp + humidity + pollution), 1 column
        JPanel gridPanel = new JPanel(new GridLayout(5, 1));

        TemperaturePanel northPanel =
                new TemperaturePanel(envSystem, tempFeed, 0, "North Ward", NORTH_IDEAL);
        TemperaturePanel centralPanel =
                new TemperaturePanel(envSystem, tempFeed, 1, "Central Ward", CENTRAL_IDEAL);
        TemperaturePanel southPanel =
                new TemperaturePanel(envSystem, tempFeed, 2, "South Ward", SOUTH_IDEAL);

        HumidityPanel humidityPanel =
                new HumidityPanel(envSystem, humidityFeed);

        PollutionPanel pollutionPanel =
                new PollutionPanel(envSystem, pollutionFeed);

        // Add cells to grid
        gridPanel.add(northPanel);
        gridPanel.add(centralPanel);
        gridPanel.add(southPanel);
        gridPanel.add(humidityPanel);
        gridPanel.add(pollutionPanel);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setSize(800, 400);
        frame.setVisible(true);

        //Timer to update readings every second
        EnvironmentalSystem.ReadingPanel[] allPanels = new EnvironmentalSystem.ReadingPanel[]{
                northPanel, centralPanel, southPanel, humidityPanel, pollutionPanel
        };

        Timer timer = new Timer(1000, new ActionListener() {
            private int seconds = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                // Update all cells from sensors
                for (EnvironmentalSystem.ReadingPanel p : allPanels) {
                    p.updateReading();
                }

                // Repaint GUI
                frame.repaint();

                seconds++;

                // Every 30 seconds we log to console (System.out.println)
                if (seconds % 30 == 0) {
                    logToConsole(envSystem, northPanel, centralPanel, southPanel,
                            humidityPanel, pollutionPanel);
                }
            }
        });
        timer.start();
    }

     //Requirement 3 log current readings and scanner status to the console

    private static void logToConsole(EnvironmentalSystem envSystem,
                                     TemperaturePanel northPanel,
                                     TemperaturePanel centralPanel,
                                     TemperaturePanel southPanel,
                                     HumidityPanel humidityPanel,
                                     PollutionPanel pollutionPanel) {

        System.out.println("Environment Status");
        System.out.println(northPanel.getStatusText());
        System.out.println(centralPanel.getStatusText());
        System.out.println(southPanel.getStatusText());
        System.out.println(humidityPanel.getStatusText());
        System.out.println(pollutionPanel.getStatusText());

        // Scanner status strings from the library
        System.out.println("Ultrasound scanner: " + envSystem.getUltrasoundScannerStatus());
        System.out.println("CT scanner: " + envSystem.getCTScannerStatus());
    }
}