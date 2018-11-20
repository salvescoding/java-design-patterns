import java.util.ArrayList;
import java.util.Collections;



    class HtmlElement {
        public String name, text;
        public ArrayList<HtmlElement> elements = new ArrayList<>();
        private final int identSize = 2;
        private final String newLine = System.lineSeparator();

        public HtmlElement(String name, String text) {
            this.name = name;
            this.text = text;
        }

        public HtmlElement() {
        }

        private String toStringImpl(int ident) {
            StringBuilder stringBuilder = new StringBuilder();
            String i= String.join("", Collections.nCopies(ident * identSize, " "));
            stringBuilder.append(String.format("%s<%s>%s", i, name, newLine));
            if (text != null && !text.isEmpty()) {
                stringBuilder.append(String.join("", Collections.nCopies(identSize * (ident+1), " ")))
                .append(text)
                .append(newLine);
            }

            for (HtmlElement e : elements)
                stringBuilder.append(e.toStringImpl(ident + 1));

            stringBuilder.append(String.format("%s</%s>%s", i, name, newLine ));
            return stringBuilder.toString();

        }

        @Override
        public String toString() {
            return toStringImpl(0);
        }
    }

    class HtmlBuilder {
        private String rootName;
        private HtmlElement root = new HtmlElement();

        public HtmlBuilder(String rootName) {
            this.rootName = rootName;
            root.name = rootName;
        }

        public void addChild(String childName, String childText) {
            HtmlElement e = new HtmlElement(childName, childText);
            root.elements.add(e);
        }

        public void clear() {
            root = new HtmlElement();
            root.name = rootName;
        }

        @Override
        public String toString() {
           return root.toString();
        }
    }

public class Builder {

    public static void main(String[] args) {
       HtmlBuilder htmlBuilder = new HtmlBuilder("ul");
       htmlBuilder.addChild("li", "Hello");
       htmlBuilder.addChild("li", "World");

       System.out.println(htmlBuilder);
    }


}
