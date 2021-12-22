
public class Port extends Intersection {
    private int portType; // 1 = 2 ressouces demandé / 2 = 3 ressouces demandé
    private Resource resource; // 5 tout / de -1 a 4  : specifique

    public Port(Building building, Resource resource){ // port type 1
          super(building);
          this.portType = 1;
          this.resource = resource;
        }

    public Port(Building building){ // port type 2
        super(building);
        this.portType=2;
        this.resource=5;

}


}
