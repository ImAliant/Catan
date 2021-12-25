package Game;

public class Port {
    private final int portType; // 0 = 2 ressouces demandé spécifique / 1 = 3 ressouces demandé
    private final Resource resource; //Resource spécifié si type "2:1"

    public Port(Resource resource){ // port type "2:1"
          this.portType = 0;
          this.resource = resource;
        }

    public Port(){ // port type "3:1"
        this.portType=1;
        this.resource=null;
    }

    public String toString(){
        if(portType==1){
            return "3:1";
        }
        return "2:1 ("+resource.toString()+")";
    }

    //Getters et Setters
    public int getPortType() {return portType;}
    public Resource getResource() {return resource;}
}
