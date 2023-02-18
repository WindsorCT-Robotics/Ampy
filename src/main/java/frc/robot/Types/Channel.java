package frc.robot.Types;

public class Channel {
    private final int value;

    public Channel(int value) {
        this.value = value;
    }

    public int getChannel() {
        return value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    public boolean equals(Channel other) {
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
        Channel other = (Channel) obj;
        return equals(other);
    }
}

