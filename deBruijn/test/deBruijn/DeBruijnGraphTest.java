package deBruijn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DeBruijnGraphTest {

    private DeBruijnGraph graph;
    private String genome;
    private int k;

    @BeforeEach
    public void setUp() {
        genome = "GATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAAT";
        k = 4;
        DeBruijnGraph.buildDeBruijnGraph(genome, k);
    }


    @Test
    public void testBuildDeBruijnGraph() {
        Map<String, List<String>> graph = DeBruijnGraph.buildDeBruijnGraph(genome, k);
        assertNotNull(graph);
        assertTrue(graph.containsKey("GAT"));
        assertFalse(graph.get("GAT").isEmpty());
    }

    @Test
    public void testGenerateInAndOutDegrees() {
        DeBruijnGraph.generateInAndOutDegrees();
        assertFalse(DeBruijnGraph.outDegree.isEmpty());
        assertFalse(DeBruijnGraph.inDegree.isEmpty());

        assertTrue(DeBruijnGraph.outDegree.containsKey("GAT"));
        assertTrue(DeBruijnGraph.inDegree.containsKey("ATC"));
    }

    @Test
    public void testDFS() {
        String startNode = "GAT";
        List<String> path = DeBruijnGraph.DFS(startNode);

        assertNotNull(path);
        assertTrue(path.contains(startNode));
    }



    @Test
    public void testReconstructGenome() {
        List<String> path = DeBruijnGraph.findEulerianPath(DeBruijnGraph.graph);
        String reconstructedGenome = DeBruijnGraph.reconstructGenome(path, k);

        assertNotNull(reconstructedGenome);
        assertEquals(genome, reconstructedGenome);
    }
}
