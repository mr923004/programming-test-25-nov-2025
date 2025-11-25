package exam2025;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class ReadingPanel extends JPanel {

    private final JLabel textLabel;
    private String lastText = "";

    public ReadingPanel() {
        setLayout(new BorderLayout());
        textLabel = new JLabel("");
        add(textLabel, BorderLayout.CENTER);
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.WHITE);
    }

     //Called once per second to refresh this cell from the sensors.

    public abstract void updateReading();


     //Helper for subclasses to update both the label and warning colour.

    protected void updateDisplay(String text, boolean warning) {
        this.lastText = text;
        textLabel.setText(text);
        if (warning) {
            setBackground(Color.RED);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public String getStatusText() {
        return lastText;
    }
}