package graph;

import list.*;
import hashtable.*;

public class Graph {

  public DList vertexList;
  public HashTableChained edgeHash;

  public Graph() {
    vertexList = new DList();
    edgeHash = new HashTableChained();
  }

  public void insertVertex(Object s) {
    vertexList.insertBack(s);
  }

  public void insertEdge(Object s1, Object s2, int weight) {
    if (edgeHash.find(new VertexPair(s1, s2)) == null) {
      edgeHash.insert(new VertexPair(s1, s2), weight);
    }
  }

  public int weight(Object v1, Object v2) {
    return (Integer) edgeHash.find(new VertexPair(v1, v2)).value(); 
  }

  public Object[] leastWeights(Object m) {
    Object[] leastWeights = new Object[5];
    try {
    int index = 0;
    for (int sim = 0; sim < 50; sim++) {
      if (index >= 5) {
	break;
      }
      DListNode currNode = vertexList.front();
      while (currNode.isValidNode()) {
	if (edgeHash.find(new VertexPair(m, currNode.item())) != null &&
		(Integer) edgeHash.find(new VertexPair(m, currNode.item())).value() == sim &&
		index < 5) {
	  leastWeights[index] = (String) currNode.item();
	  index++;
	}
	currNode = currNode.next();
      }
    }
    } catch (InvalidNodeException e) {
      System.out.println(e);
    }
    return leastWeights;
  }

  public String toString() {
    int max = edgeHash.size();
    int itemNum;
    String output = "";
    DListNode currNode1 = vertexList.front();
    try {
    while (currNode1.isValidNode()) {
      DListNode currNode2 = vertexList.front();
      while (currNode2.isValidNode()) {
	Entry weight = edgeHash.find(new VertexPair(currNode1.item(), currNode2.item()));
	if (weight != null) {
	  output += currNode1.item() + ", "  + currNode2.item() + ": " + weight.value() + "\n";
	}
	currNode2 = currNode2.next();
      }
      currNode1 = currNode1.next();
    }
    } catch (InvalidNodeException e) {
      System.out.println(e);
    }
    return output;
  }

}
