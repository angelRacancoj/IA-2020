package primeroelmejor.manager;

import java.util.LinkedList;
import primeroelmejor.ConnetionN;
import primeroelmejor.Node;

/**
 *
 * @author angelrg
 */
public class BestFirst {

    public LinkedList<ConnetionN> findRoute(Node node, String destiny) {
        LinkedList<ConnetionN> route = new LinkedList<>();
        for (ConnetionN nextNode : node.getNextNodes()) {
            if (nextNode.getNextNode().getName().equalsIgnoreCase(destiny)) {
                route.add(nextNode);
                return route;
            } else {
                LinkedList<ConnetionN> routeAux = findRoute(nextNode.getNextNode(), destiny);
                if (!routeAux.isEmpty()) {
                    route.addAll(routeAux);
                    route.addFirst(nextNode);
                    return route;
                }
            }
        }
        return route;
    }

}
