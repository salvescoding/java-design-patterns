import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum Color {
    RED, BLUE, GREEN, ORANGE
}

enum Size {
    SMALL, MEDIUM, LARGE, HUGE
}

public class Product {
    public String name;
    public Color color;

    public Size size;
    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

}

interface Specification<T> {
    boolean isSatisfied(T item);

}
class ColorSpecification implements Specification<Product> {


    private Color color;

    public ColorSpecification(Color color) { this.color = color; }
    @Override
    public boolean isSatisfied(Product item) {
        return item.color == color;
    }

}
class SizeSpecification implements  Specification<Product> {


    private Size size;

    public SizeSpecification(Size size) { this.size = size; }
    @Override
    public boolean isSatisfied(Product item) { return item.size == size; }

}
interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> specification);

}
class BetterFilter implements Filter<Product> {
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> specification) {
        return items.stream().filter(p -> specification.isSatisfied(p));
    }
}

class AndSpecification<T> implements Specification<T> {

    private Specification<T> first, second;

    public AndSpecification(Specification<T> first, Specification<T> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}

class DemoProduct {
    public static void main(String[] args) {
        Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.HUGE);
        Product house = new Product("House", Color.BLUE, Size.HUGE);

        List<Product> products = Arrays.asList(apple, tree, house);

        BetterFilter betterFilter = new BetterFilter();
        System.out.println("Green Products (new): ");
        betterFilter.filter(products, new ColorSpecification(Color.GREEN))
                .forEach(p -> System.out.println(p.name));

        System.out.println("Huge Blue products: ");
        betterFilter.filter(products, new AndSpecification<>(
                new ColorSpecification(Color.BLUE), new SizeSpecification(Size.HUGE))).forEach(p -> System.out.println(p.name));
    }


}
