package primeroelmejor;

import java.util.LinkedList;
import primeroelmejor.manager.BestFirst;
import primeroelmejor.manager.NodeManager;

/**
 *
 * @author angelrg
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        LinkedList<Node> nodes = new LinkedList<>();
        LinkedList<ConnetionN> route;
        NodeManager nodeManager = NodeManager.getInstance();
        BestFirst bestFirst = new BestFirst();

        nodes.add(new Node("AA"));
        nodes.add(new Node("AB"));
        nodes.add(new Node("AC"));
        nodes.add(new Node("AD"));
        nodes.add(new Node("AE"));
        nodes.add(new Node("AF"));
        nodes.add(new Node("AG"));
        nodes.add(new Node("AH"));
        nodes.add(new Node("BA"));
        nodes.add(new Node("BB"));
        nodes.add(new Node("BC"));
        nodes.add(new Node("BD"));
        nodes.add(new Node("BE"));
        nodes.add(new Node("BF"));
        nodes.add(new Node("BG"));
        nodes.add(new Node("BH"));
        nodeManager.findNode(nodes, "AA").addConnection(5, nodes, "AE");
        nodeManager.findNode(nodes, "AA").addConnection(3, nodes, "AH");
        nodeManager.findNode(nodes, "AA").addConnection(7, nodes, "BA");

        nodeManager.findNode(nodes, "AE").addConnection(8, nodes, "AB");
        nodeManager.findNode(nodes, "AE").addConnection(1, nodes, "AC");
        nodeManager.findNode(nodes, "AC").addConnection(6, nodes, "BC");
        nodeManager.findNode(nodes, "AC").addConnection(2, nodes, "BE");

        nodeManager.findNode(nodes, "AH").addConnection(5, nodes, "BB");
        nodeManager.findNode(nodes, "AH").addConnection(4, nodes, "BD");
        nodeManager.findNode(nodes, "AH").addConnection(1, nodes, "AD");
        nodeManager.findNode(nodes, "BB").addConnection(7, nodes, "AG");
        nodeManager.findNode(nodes, "BB").addConnection(3, nodes, "AF");

        nodeManager.findNode(nodes, "BA").addConnection(8, nodes, "BH");
        nodeManager.findNode(nodes, "BH").addConnection(1, nodes, "BF");
        nodeManager.findNode(nodes, "BH").addConnection(9, nodes, "BG");

        for (Node node : nodes) {
            node.printInfo();
        }

        Node node = nodeManager.findNode(nodes, "AA");
        route = bestFirst.findRoute(node, "BE");
        node.printAboutMe();
        double leght = 0;

        for (ConnetionN route1 : route) {
            leght += route1.distancia;
            route1.printInfo();
        }

        System.out.println("Distancia Total: " + leght);
    }

}
