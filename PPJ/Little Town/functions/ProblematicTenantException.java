package functions;

public class ProblematicTenantException extends Exception{
    String error;
    public ProblematicTenantException(String error){
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
