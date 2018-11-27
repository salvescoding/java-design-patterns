import java.util.Arrays;

class Address implements Cloneable {
    public String streetName;
    public int houseNumber;

    public Address(String streetName, int houseNumber) {
        this.streetName = streetName;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                '}';
    }

    // Deep copy
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new Address(streetName, houseNumber);
    }
}

class People implements Cloneable {
    public String[] names;
    public Address address;

    public People(String[] names, Address address) {
        this.names = names;
        this.address = address;
    }

    @Override
    public String toString() {
        return "People{" +
                "names=" + Arrays.toString(names) +
                ", address=" + address +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new People(
                names.clone(), (Address) address.clone()
        );
    }
}

class Prototype {
    public static void main(String[] args) throws CloneNotSupportedException {
        People sergio = new People(new String[]{"Sergio", "Alves"}, new Address("Princesa", 23));
        People marise = (People) sergio.clone();
        marise.names[0] = "Maria";
        System.out.println(sergio);
        System.out.println(marise);

    }
}