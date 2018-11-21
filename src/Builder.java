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
        String i = String.join("", Collections.nCopies(ident * identSize, " "));
        stringBuilder.append(String.format("%s<%s>%s", i, name, newLine));
        if (text != null && !text.isEmpty()) {
            stringBuilder.append(String.join("", Collections.nCopies(identSize * (ident + 1), " ")))
                    .append(text)
                    .append(newLine);
        }

        for (HtmlElement e : elements)
            stringBuilder.append(e.toStringImpl(ident + 1));

        stringBuilder.append(String.format("%s</%s>%s", i, name, newLine));
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

    public HtmlBuilder addChild(String childName, String childText) {
        HtmlElement e = new HtmlElement(childName, childText);
        root.elements.add(e);
        return this;
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


class Person {

    public String name, position;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}

class PersonBuilder<SELF extends PersonBuilder<SELF>> {
    protected Person person = new Person();

    public SELF withName(String name) {
        person.name = name;
        return self();
    }

    public Person build() {
        return person;
    }

    protected SELF self() {
        return (SELF) this;
    }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> {

    public EmployeeBuilder worksAt(String position) {
        person.position = position;
        return self();
    }

    @Override
    protected EmployeeBuilder self() {
        return this;
    }
}




public class Builder {

    public static void main(String[] args) {
        HtmlBuilder htmlBuilder = new HtmlBuilder("ul");
        htmlBuilder
                .addChild("li", "Hello")
                .addChild("li", "World");
        System.out.println(htmlBuilder);

        EmployeeBuilder employeeBuilder = new EmployeeBuilder();
        Person person = employeeBuilder
                .withName("Sergio")
                .worksAt("Dev")
                .build();
        System.out.println(person);
    }

}
