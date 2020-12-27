package sd.nosql.utils;

public class RaftOperation {

    public enum Operation {
        GET,
        SET,
        DEL;
    }

    public static Operation getRaftOperation(String operation) {
        if (operation.contains("get")) {
            return Operation.GET;
        } else if (operation.contains("set")) {
            return Operation.SET;
        } else if (operation.contains("del")) {
            return Operation.DEL;
        } else {
            return null;
        }
    }
}