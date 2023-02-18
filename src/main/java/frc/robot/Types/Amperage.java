package frc.robot.Types;

public class Amperage {
    private final double value;

    public Amperage(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean equals(Amperage other) {
        return other.value == value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Amperage other = (Amperage) obj;
        return equals(other);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
