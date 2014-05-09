package gui.mvc.voting.view;

import gui.mvc.voting.model.IPollChangeListener;
import gui.mvc.voting.model.PollData;
import gui.mvc.voting.model.PollModel;
import gui.mvc.voting.view.widget.AAEnablingJPanel;

import java.awt.Graphics;

/**
 * View für ein {@link PollModel}, welche die prozentualen Stimmenanteile eines
 * Kandidaten in Form eines Balkens darstellt.
 * 
 * @author Patrick Fries
 */
public class BarView extends AAEnablingJPanel implements IPollChangeListener
{
    /** Abstand, den die Balken zu den Rändern mindestens einhalten sollen. */
    public static final int BORDER_WIDTH = 4;
    /**
     * Maximale Breite, die für die Darstellung des Kandidatennamens verwendet
     * werden soll.
     */
    public static final int TEXT_WIDTH = 120;

    /** Modell, dessen Inhalte dargestellt werden. */
    private final PollModel pollModel;
    /** Indexposition des betreffenden Kandidaten aus dem Modell. */
    private final int candidateIndex;

    /**
     * Initialisiert ein neues BarView-Objekt mit den übergebenen Argumenten.
     * 
     * @param pollModel
     *            Modell, aus dem die Stimmanteile eines Kandidaten angezeigt
     *            werden.
     * @param candidateIndex
     *            Indexposition des betreffenden Kandidaten aus dem Modell.
     */
    public BarView(final PollModel pollModel, final int candidateIndex)
    {
        this.pollModel = pollModel;
        this.candidateIndex = candidateIndex;

        this.pollModel.addPollChangeListener(this);
    }

    @Override
    protected void paintComponent(final Graphics g)
    {
        super.paintComponent(g);
        final int centerY = this.getHeight() / 2;

        // Kandidatennamen zeichnen
        final int nameX = BarView.BORDER_WIDTH;
        final int nameY = centerY + (g.getFontMetrics().getAscent() / 2);

        g.drawString(this.pollModel.getPollData().getAnswer(this.candidateIndex), nameX, nameY);

        // Balken zeichnen
        final int barX = nameX + BarView.TEXT_WIDTH;
        final int barY = BarView.BORDER_WIDTH;
        final int barHeight = this.getHeight() - (2 * BarView.BORDER_WIDTH);
        final int barWidthMax = this.getWidth() - barX - BarView.BORDER_WIDTH;
        final int filledBarWidth = (barWidthMax * this.pollModel.getPollData().getAnswerPercentage(this.candidateIndex)) / 100;
        g.fillRect(barX, barY, filledBarWidth, barHeight);
        g.drawRect(barX, barY, barWidthMax, barHeight);
    }

    @Override
    public void voteChanged(PollData data)
    {
        this.repaint();
    }

    @Override
    public void answerAdded(PollData data)
    {
        // TODO Auto-generated method stub

    }
}
