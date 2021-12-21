package Game;

public class Port {
    private final int portType;
    private final Resource resource;
    
    public Port(int portType, Resource resource){
        this.portType=portType;
        this.resource=resource;
    }


    //Getters et Setters
    public int getPortType() {return portType;}
    public Resource getResource() {return resource;}
}
