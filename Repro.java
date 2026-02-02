public class Repro {
    sealed interface Result permits Ok, Err {}
    record Ok(Object v) implements Result {}
    record Err(Object e) implements Result {}

    Result getRes() { return new Ok("test"); }

    Result trigger() {
        if (getRes() instanceof Err(var e)) return new Err(e);

        return switch (getRes()) {
            case Err e -> e;
            case Ok v1 -> switch (getRes()) {
                case Err e -> e;
                case Ok v2 -> new Ok("win");
            };
        };
    }

    public static void main(String[] args) {
        System.out.println(new Repro().trigger());
    }
}
