//----------------------------------------------------------------------------//
//                                                                            //
//                        V i e w P a r a m e t e r s                         //
//                                                                            //
//  Copyright (C) Herve Bitteur 2000-2009. All rights reserved.               //
//  This software is released under the GNU General Public License.           //
//  Please contact users@audiveris.dev.java.net to report bugs & suggestions. //
//----------------------------------------------------------------------------//
//
package omr.glyph.ui;

import omr.constant.Constant;
import omr.constant.ConstantSet;

import org.jdesktop.application.AbstractBean;
import org.jdesktop.application.Action;

import java.awt.event.ActionEvent;

/**
 * Class <code>ViewParameters</code> handles parameters for GlyphLagView,
 * using properties referenced through their programmatic name to avoid typos.
 *
 * @author Herv&eacute Bitteur
 * @version $Id$
 */
public class ViewParameters
    extends AbstractBean
{
    //~ Static fields/initializers ---------------------------------------------

    /** Specific application parameters */
    private static final Constants constants = new Constants();

    /** Should the slur circles be painted  */
    public static final String CIRCLE_PAINTING = "circlePainting";

    /** Should the letter boxes be painted */
    public static final String LETTER_BOX_PAINTING = "letterBoxPainting";

    /** Should the stick lines be painted */
    public static final String LINE_PAINTING = "linePainting";

    //~ Methods ----------------------------------------------------------------

    //-------------//
    // getInstance //
    //-------------//
    public static ViewParameters getInstance ()
    {
        return Holder.INSTANCE;
    }

    //-------------------//
    // setCirclePainting //
    //-------------------//
    public void setCirclePainting (boolean value)
    {
        boolean oldValue = constants.circlePainting.getValue();
        constants.circlePainting.setValue(value);
        firePropertyChange(CIRCLE_PAINTING, oldValue, value);
    }

    //------------------//
    // isCirclePainting //
    //------------------//
    public boolean isCirclePainting ()
    {
        return constants.circlePainting.getValue();
    }

    //----------------------//
    // setLetterBoxPainting //
    //----------------------//
    public void setLetterBoxPainting (boolean value)
    {
        boolean oldValue = constants.letterBoxPainting.getValue();
        constants.letterBoxPainting.setValue(value);
        firePropertyChange(LETTER_BOX_PAINTING, oldValue, value);
    }

    //---------------------//
    // isLetterBoxPainting //
    //---------------------//
    public boolean isLetterBoxPainting ()
    {
        return constants.letterBoxPainting.getValue();
    }

    //-----------------//
    // setLinePainting //
    //-----------------//
    public void setLinePainting (boolean value)
    {
        boolean oldValue = constants.linePainting.getValue();
        constants.linePainting.setValue(value);
        firePropertyChange(LINE_PAINTING, oldValue, value);
    }

    //----------------//
    // isLinePainting //
    //----------------//
    public boolean isLinePainting ()
    {
        return constants.linePainting.getValue();
    }

    //---------------//
    // toggleCircles //
    //---------------//
    /**
     * Action that toggles the display of approximating circles in selected
     * slur-shaped glyphs
     * @param e the event that triggered this action
     */
    @Action(selectedProperty = CIRCLE_PAINTING)
    public void toggleCircles (ActionEvent e)
    {
    }

    //---------------//
    // toggleLetters //
    //---------------//
    /**
     * Action that toggles the display of letter boxes in selected glyphs
     * @param e the event that triggered this action
     */
    @Action(selectedProperty = LETTER_BOX_PAINTING)
    public void toggleLetters (ActionEvent e)
    {
    }

    //-------------//
    // toggleLines //
    //-------------//
    /**
     * Action that toggles the display of mean line in selected sticks
     * @param e the event that triggered this action
     */
    @Action(selectedProperty = LINE_PAINTING)
    public void toggleLines (ActionEvent e)
    {
    }

    //~ Inner Classes ----------------------------------------------------------

    //-----------//
    // Constants //
    //-----------//
    private static final class Constants
        extends ConstantSet
    {
        //~ Instance fields ----------------------------------------------------

        /** Should the lines be painted */
        final Constant.Boolean linePainting = new Constant.Boolean(
            false,
            "Should the stick lines be painted");

        /** Should the circles be painted */
        final Constant.Boolean circlePainting = new Constant.Boolean(
            true,
            "Should the slur circles be painted");

        /** Should the letter boxes be painted */
        final Constant.Boolean letterBoxPainting = new Constant.Boolean(
            true,
            "Should the letter boxes be painted");
    }

    //--------//
    // Holder //
    //--------//
    private static class Holder
    {
        //~ Static fields/initializers -----------------------------------------

        public static final ViewParameters INSTANCE = new ViewParameters();
    }
}
