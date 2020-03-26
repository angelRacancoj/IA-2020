package primeroelmejor.manager;

import java.util.LinkedList;
import java.util.Random;
import primeroelmejor.Node;

/**
 *
 * @author angelrg
 */
public class NodeManager {

    public static NodeManager instancia;
    public static int wordLeght = 3;

    private NodeManager() {
    }

    public static NodeManager getInstance() {
        if (instancia == null) {
            instancia = new NodeManager();
        }
        return instancia;
    }

    public Node findNode(LinkedList<Node> nodes, String nombre) {
        for (Node node : nodes) {
            if (node.getName().equalsIgnoreCase(nombre)) {
                return node;
            }
        }
        return null;
    }

    public String randomName(LinkedList<Node> nodes) {
        String name = newName();
        while (findNode(nodes, name) != null) {
            name = newName();
        }
        return name;
    }

    private String newName() {
        String name = "";
        for (int i = 0; i < wordLeght; i++) {
            name += String.valueOf(randomChar());
        }
        return name;
    }

    public char randomChar() {
        Random rnd = new Random();
        char c = (char) (rnd.nextInt(26) + 'a');
        return c;
    }
}
