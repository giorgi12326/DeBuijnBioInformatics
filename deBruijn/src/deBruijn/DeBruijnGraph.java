package deBruijn;

import java.util.*;

public class DeBruijnGraph {
    static Map<String, List<String>> graph = new HashMap<>();
    static Map<String, Integer> outDegree = new HashMap<>();
    static Map<String, Integer> inDegree = new HashMap<>();

    public static Map<String, List<String>> buildDeBruijnGraph(String genome, int k) {
        for (int i = 0; i <= genome.length() - k; i++) {
            String kmer = genome.substring(i, i + k);
            String prefix = kmer.substring(0, k - 1);
            String suffix = kmer.substring(1);
            graph.computeIfAbsent(prefix, x -> new ArrayList<>()).add(suffix);
        }
        return graph;
    }
    public static void generateInAndOutDegrees(){
        for (String node : graph.keySet()) {
            outDegree.put(node, graph.get(node).size());
            for (String neighbor : graph.get(node)) {
                inDegree.put(neighbor, inDegree.getOrDefault(neighbor, 0) + 1);
            }
        }
    }
    public static List<String> DFS(String startNode){
        Stack<String> stack = new Stack<>();
        List<String> path = new ArrayList<>();
        stack.push(startNode);

        while (!stack.isEmpty()) {
            String currentNode = stack.peek();
            if (graph.containsKey(currentNode) && !graph.get(currentNode).isEmpty()) {
                String nextNode = graph.get(currentNode).remove(0);
                stack.push(nextNode);
            } else {
                path.add(stack.pop());
            }
        }
        return path;
    }

    public static List<String> findEulerianPath(Map<String, List<String>> graph) {
        generateInAndOutDegrees();


        String startNode = null;
        for (String node : outDegree.keySet()) {
            if (outDegree.get(node) > inDegree.getOrDefault(node, 0)) {
                startNode = node;
                break;
            }
        }

        List<String> path = DFS(startNode);
        Collections.reverse(path);

        return path;
    }

    public static String reconstructGenome(List<String> path, int k) {
        StringBuilder genome = new StringBuilder(path.get(0));

        for (int i = 1; i < path.size(); i++) {
            genome.append(path.get(i).charAt(k - 2));
        }

        return genome.toString();
    }
}
