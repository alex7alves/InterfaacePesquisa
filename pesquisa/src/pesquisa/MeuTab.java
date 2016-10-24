
package pesquisa;

/**
 *
 * @author Alex Alves
 */

import javax.swing.text.*;
public class MeuTab  extends StyledEditorKit  {
     public static final int TamanhoTAB=30;
     
    @Override
    public ViewFactory getViewFactory() {
        return new NovoViewF();
    }
    
    static class NovoViewF implements ViewFactory {

        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new MudarTab(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            return new LabelView(elem);
        }
    }


    static class MudarTab extends ParagraphView {

        public MudarTab(Element elem) {
            super(elem);
        }

        @Override
        public float nextTabStop(float x, int tabOffset) {
            TabSet tabs = getTabSet();
            if(tabs == null) {
                // a tab every 72 pixels.
                return (float)(getTabBase() + (((int)x / TamanhoTAB + 1) *TamanhoTAB));
            }

            return super.nextTabStop(x, tabOffset);
        }

    }
}
