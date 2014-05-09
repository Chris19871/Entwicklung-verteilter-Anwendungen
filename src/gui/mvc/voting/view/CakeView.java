package gui.mvc.voting.view;

import gui.mvc.voting.model.IPollChangeListener;
import gui.mvc.voting.model.PollData;
import gui.mvc.voting.model.PollModel;
import gui.mvc.voting.view.widget.AAEnablingJPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * View für ein {@link PollModel}, welche die prozentualen Stimmenanteile aller
 * Kandidaten in Form eines Tortendiagramms darstellt.
 * 
 * @author Patrick Fries
 */
public class CakeView extends AAEnablingJPanel implements IPollChangeListener
{
    /** Abstand, den der Kuchen zu den Rändern mindestens einhalten soll. */
    public static final int BORDER_WIDTH = 12;

    /** Modell, dessen Inhalte dargestellt werden. */
    private final PollModel pollModel;

    /**
     * Pufferspeicher für die Farben, die zum Zeichnen der Tortenstücke
     * verwendet werden.
     */
    private final ArrayList<Color> candidateColors;
    /** Dient der Erzeugung von zufälligen Farben für die Tortenstücke. */
    private final Random rand;

    /**
     * Initialisiert ein neues CakeView-Objekt mit den übergebenen Argumenten.
     * 
     * @param pollModel
     *            Modell, dessen Inhalte dargestellt werden sollen.
     */
    public CakeView(final PollModel pollModel)
    {
        this.pollModel = pollModel;
        this.rand = new Random();
        this.candidateColors = new ArrayList<Color>();

        for (int i = 0; i < pollModel.getPollData().getNumberOfCandidates(); i++)
        {
            this.candidateColors.add(this.generateRandomColor());
        }

        pollModel.addPollChangeListener(this);
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);

        final int rawDiameter = Math.min(this.getWidth(), this.getHeight());
        final int bordredDiameter = rawDiameter - (2 * CakeView.BORDER_WIDTH);
        final int centerX = this.getWidth() / 2;
        final int centerY = this.getHeight() / 2;
        final int x = centerX - (bordredDiameter / 2);
        final int y = centerY - (bordredDiameter / 2);

        int currentStartAngle = 0;
        final int[] arcAngles = this.calculateArcAngles();
        for (int i = 0; i < arcAngles.length; i++)
        {
            final int currentArcAngle = arcAngles[i];
            if (currentArcAngle == 0)
            {
                continue;
            }

            // Kreisbogen zeichnen
            g.setColor(this.candidateColors.get(i));
            g.fillArc(x, y, bordredDiameter, bordredDiameter, currentStartAngle, currentArcAngle);
            g.setColor(Color.BLACK);
            g.drawArc(x, y, bordredDiameter, bordredDiameter, currentStartAngle, currentArcAngle);

            if (g instanceof Graphics2D)
            {
                final Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(Color.BLACK);
                g2d.translate(centerX, centerY);

                // Begrenzungslinien für Kreisbogen zeichnen
                g2d.rotate(Math.toRadians(currentStartAngle) * -1);
                g2d.drawLine(0, 0, bordredDiameter / 2, 0);
                g2d.rotate(Math.toRadians(currentArcAngle) * -1);
                g2d.drawLine(0, 0, bordredDiameter / 2, 0);
                g2d.rotate(Math.toRadians(currentArcAngle));
                g2d.rotate(Math.toRadians(currentStartAngle));

                // Kandidatennamen Zeichnen
                final String candidateName = this.pollModel.getPollData().getAnswer(i);
                final int candidateNameWidth = g2d.getFontMetrics().stringWidth(candidateName);
                final float posX = ((bordredDiameter / 2.0f) - candidateNameWidth) / 2.0f;
                final float posY = g2d.getFontMetrics().getAscent() / 2.0f;
                final double fontAngle = Math.toRadians(currentStartAngle + (currentArcAngle / 2.0));
                if ((fontAngle > Math.toRadians(90)) && (fontAngle < Math.toRadians(270)))
                {
                    g2d.rotate((fontAngle + Math.toRadians(180)) * -1);
                    g2d.drawString(candidateName, (posX + candidateNameWidth) * -1, posY);

                }
                else
                {
                    g2d.rotate(fontAngle * -1);
                    g2d.drawString(candidateName, posX, posY);
                }
            }
            currentStartAngle += currentArcAngle;
        }
    }

    /**
     * Hilfsmethode, die eine zufällige Farbe erzeugt und zurückliefert.
     * 
     * @return Siehe Methodenbeschreibung.
     */
    private Color generateRandomColor()
    {
        return new Color(this.rand.nextInt(256), this.rand.nextInt(256), this.rand.nextInt(256));
    }

    /**
     * Hilfsmethode, die Winkelstücke für alle Kandidaten des {@link PollModel}s
     * berechnet und dabei Rundungsungenauigkeiten ausgeleicht.
     * 
     * @return Winkelstücke für die Kandidaten des {@link PollModel}s
     */
    private int[] calculateArcAngles()
    {
        int numberOfAnglesGreaterZero = 0;
        int remainingDegreesToDistribute = 360;
        int indexOfLastAngleGreaterZero = 0;

        final int[] angles = new int[this.pollModel.getPollData().getAnswersCount()];
        for (int i = 0; i < angles.length; i++)
        {
            angles[i] = (this.pollModel.getPollData().getAnswerPercentage(i) * 360) / 100;

            if (angles[i] > 0)
            {
                numberOfAnglesGreaterZero++;
                remainingDegreesToDistribute -= angles[i];
                indexOfLastAngleGreaterZero = i;
            }
        }

        // Sollte bei der vorherigen Aufteilung ein Rest geblieben sein, so wird
        // dieser zumächst anteilig auf alle Kandidaten mit
        // Prozentwerten größer 0 verteilt. Sollte danach immer noch ein Rest
        // bestehen, so wird dieser Rest zum Winkelstück des
        // letzten Kandidaten mit einem Prozentwert größer 0 gezählt.
        if ((remainingDegreesToDistribute != 0) && (numberOfAnglesGreaterZero > 0))
        {
            final int remainForAllAnglesGreaterZero = remainingDegreesToDistribute / numberOfAnglesGreaterZero;
            final int additionalRemainForLastAngleGreaterZero = remainingDegreesToDistribute % numberOfAnglesGreaterZero;

            for (int i = 0; i < angles.length; i++)
            {
                if (angles[i] > 0)
                {
                    angles[i] += remainForAllAnglesGreaterZero;
                }
            }
            angles[indexOfLastAngleGreaterZero] += additionalRemainForLastAngleGreaterZero;
        }
        return angles;
    }

    @Override
    public void voteChanged(PollData data)
    {
        this.repaint();
    }

    @Override
    public void answerAdded(PollData data)
    {
        this.candidateColors.add(this.generateRandomColor());
        this.repaint();
    }
}
