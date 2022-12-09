package encoder;

public class Address {

    public int level;
    public int displacement;

    public Address() {
        level = 0;
        displacement = 0;
    }

    public Address(int level, int displacement) {
        this.level = level;
        this.displacement = displacement;
    }

    public Address(Address address, int increment) {
        this.level = address.level;
        this.displacement = address.displacement + increment;
    }

    public Address(Address address) {
        this.level = address.level + 1;
        this.displacement = 0;
    }

    public String toString() {
        return "level=" + level + " displacement=" + displacement;
    }
}
