package functions;

public class TooManyThingsEX extends Exception {
    String error = "Remove some old items to insert a new item";

    @Override
    public String toString() {
        return error;
    }
}
