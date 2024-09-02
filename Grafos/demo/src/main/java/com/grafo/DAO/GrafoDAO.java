package com.grafo.DAO;

import java.util.*;

import javax.swing.JFrame;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.grafo.model.Aresta;
import com.grafo.model.Grafo;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.swing.mxGraphComponent;

/**
 * GrafoDAO
 */
public class GrafoDAO {
    Grafo grafo = new Grafo();
    ArestaDAO arestaDAO = new ArestaDAO(grafo);
    public void inserirVertice(int id) {
        if (!grafo.getAdjacencias().containsKey(id)) {
            grafo.getAdjacencias().put(id, new ArrayList<>());
            grafo.setNumVertices(grafo.getNumVertices() + 1);
        }
    }

    
    
    
    
    public static void plotarGrafo(Graph<String, DefaultEdge> grafo) {
        // Adaptando o grafo para JGraphX
        JGraphXAdapter<String, DefaultEdge> graphAdapter = new JGraphXAdapter<>(grafo);

        // Configurando o layout do grafo (circular, neste caso)
        mxCircleLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        // Criando um componente Swing para mostrar o grafo
        JFrame frame = new JFrame();
        frame.getContentPane().add(new mxGraphComponent(graphAdapter));
        frame.setTitle("Plotagem de Grafo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public boolean VerificaGrafoConexo(Grafo grafo) {
        Set<Integer> vertices = grafo.getVertices();
        if (vertices.isEmpty()) {
            return true; // Um grafo vazio é considerado conexo
        }

        // Inicia a busca a partir de um vértice arbitrário
        Integer startVertex = vertices.iterator().next();
        Set<Integer> visited = new HashSet<>();

        // Executa a busca em profundidade (DFS)
        dfs(startVertex, grafo, visited);

        // Verifica se todos os vértices foram visitados
        return visited.size() == vertices.size();
    }

    // Implementa a busca em profundidade (DFS)
    private void dfs(Integer startVertex, Grafo grafo, Set<Integer> visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(startVertex);

        while (!stack.isEmpty()) {
            Integer vertex = stack.pop();
            if (!visited.contains(vertex)) {
                visited.add(vertex);

                for (Aresta aresta : grafo.getArestas(vertex)) {
                    Integer neighbor = aresta.getDestino();
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
    }

    
    public void exibirConectividade(Grafo grafo) {
        boolean conexo = VerificaGrafoConexo(grafo);
        System.out.println("O grafo é conexo? " + (conexo ? "Sim" : "Não"));
    }
    

}
