import java.util.ArrayList;
import java.util.List;

class Persona {

    public String streetAddress, postcode, city;

    public String companyName, position;
    public int annualIncome;

    @Override
    public String toString() {
        return "Persona{" +
                "streetAddress='" + streetAddress + '\'' +
                ", postcode='" + postcode + '\'' +
                ", city='" + city + '\'' +
                ", companyName='" + companyName + '\'' +
                ", position='" + position + '\'' +
                ", annualIncome=" + annualIncome +
                '}';
    }
}

class PersonaBuilder {
    protected Persona persona = new Persona();

    public PersonaAddressBuilder lives() {
        return new PersonaAddressBuilder(persona);
    }

    public PersonaJobBuilder works() {
        return new PersonaJobBuilder(persona);
    }

    protected Persona build() {
        return persona;
    }


}

class PersonaAddressBuilder extends PersonaBuilder {
    public PersonaAddressBuilder(Persona persona) {
        this.persona = persona;
    }

    public PersonaAddressBuilder at(String streetAddress) {
        persona.streetAddress = streetAddress;
        return this;
    }

    public PersonaAddressBuilder in(String city) {
        persona.city = city;
        return this;
    }

    public PersonaAddressBuilder withPostcode(String postcode) {
        persona.postcode = postcode;
        return this;
    }
}

class PersonaJobBuilder extends PersonaBuilder {
    public PersonaJobBuilder(Persona persona) {
        this.persona = persona;
    }

    public PersonaJobBuilder at(String companyName) {
        persona.companyName = companyName;
        return this;
    }

    public PersonaJobBuilder asA(String position) {
        persona.position = position;
        return this;
    }

    public PersonaJobBuilder earning(int annualIncome) {
        persona.annualIncome = annualIncome;
        return this;
    }
}

class Field {
    public String name, type;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String
    toString() {
       return String.format("public %s %s;", type, name);
    }
}

class Class {
    public String name;
    public List<Field> fields = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String nl = System.lineSeparator();
        stringBuilder.append("public class " + name).append(nl)
                .append("{").append(nl);
        for (Field field : fields)
            stringBuilder.append("  " + field).append(nl);
        return stringBuilder.append("}").append(nl).toString();

    }
}


class CodeBuilder
{
    private Class theClass = new Class();

    public CodeBuilder(String rootName)
    {
        theClass.name = rootName;
    }

    public CodeBuilder addField(String name, String type)
    {
        theClass.fields.add(new Field(name, type));
        return this;
    }

    @Override
    public String toString() {
        return theClass.toString();
    }
}

public class BuilderFacade {
    public static void main(String[] args) {
        PersonaBuilder personaBuilder = new PersonaBuilder();
        Persona persona = personaBuilder
                .lives()
                    .at("Calle Princesa")
                    .in("Barcelona")
                    .withPostcode("08003")
                .works()
                    .asA("Developer")
                    .at("ThoughtWorks")
                    .earning(25000)
                .build();
        System.out.println(persona);

        CodeBuilder codeBuilder = new CodeBuilder("Game");
        codeBuilder.addField("age", "int")
                .addField("name", "String").toString();
        System.out.println(codeBuilder);

    }
}
