/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primeroelmejor;

import java.util.LinkedList;
import primeroelmejor.manager.NodeManager;

/**
 *
 * @author angelrg
 */
public class Node {

    String name;
    LinkedList<ConnetionN> nextNodes = new LinkedList<>();
    NodeManager nodeManager = NodeManager.getInstance();

    public Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public LinkedList<ConnetionN> getNextNodes() {
        return nextNodes;
    }

    public void addConnection(double distancia, LinkedList<Node> nodes, String nameNextNode) {

        Node node = nodeManager.findNode(nodes, nameNextNode);
        if (this.nextNodes.isEmpty()) {
            this.nextNodes.add(new ConnetionN(distancia, node));
        } else {
            for (int i = 0; i < this.nextNodes.size(); i++) {
                if (this.nextNodes.get(i).distancia > distancia) {
                    this.nextNodes.add(i, new ConnetionN(distancia, node));
                    break;
                }
                if ((i + 1) == this.nextNodes.size()) {
                    this.nextNodes.addLast(new ConnetionN(distancia, node));
                    break;
                }
            }
        }
    }

    public void printInfo() {
        System.out.println(">>>>>>>>>>" + this.name + "<<<<<<<<<<");
        if (!this.nextNodes.isEmpty()) {
            for (ConnetionN nextNode : this.nextNodes) {
                nextNode.printInfo();
            }
            System.out.println("--------------------------------");
        }
        System.out.println("");
    }

    public void printAboutMe() {
        System.out.println(">>>>>>>>>>" + this.name + "<<<<<<<<<<");
    }
}
