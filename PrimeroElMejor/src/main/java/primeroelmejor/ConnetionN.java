package primeroelmejor;

/**
 *
 * @author angelrg
 */
public class ConnetionN {

    double distancia;
    Node nextNode;

    public ConnetionN(double distancia, Node nextNode) {
        this.distancia = distancia;
        this.nextNode = nextNode;
    }

    public double getDistancia() {
        return distancia;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void printInfo() {
        System.out.println("Destino: " + this.nextNode.getName() + ", Distancia: " + this.distancia);
    }

}
