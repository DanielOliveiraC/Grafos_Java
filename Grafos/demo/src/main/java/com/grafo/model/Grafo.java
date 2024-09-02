package com.grafo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Grafo
 */
public class Grafo{
    private Map<Integer, List<Aresta>> adjacencias;
    private int numVertices;

    public Grafo() {
        adjacencias = new HashMap<>();
        numVertices = 0;
    }


    
    
    // Getters e Setters
    
    /** 
     * @return Map<Integer, List<Aresta>>
     */
    public Map<Integer, List<Aresta>> getAdjacencias() {
        return adjacencias;
    }
    
    /** 
     * @param adjacencias
     */
    public void setAdjacencias(Map<Integer, List<Aresta>> adjacencias) {
        this.adjacencias = adjacencias;
    }
    
    /** 
     * @return int
     */
    public int getNumVertices() {
        return numVertices;
    }
    
    /** 
     * @param numVertices
     */
    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public Set<Integer> getVertices() {
        return adjacencias.keySet();
    }
    public List<Aresta> getArestas(int vertice) {
        return adjacencias.get(vertice);
    }
    public int getGrau(int vertice) {
        List<Aresta> arestas = adjacencias.get(vertice);
        return arestas != null ? arestas.size() : 0;
    }


    // Algoritmo DFS
    public void dfsTexto(int verticeInicial) {
        Set<Integer> visitados = new HashSet<>();
        Stack<Integer> pilha = new Stack<>();
    
        pilha.push(verticeInicial);
    
        System.out.print("DFS: ");
        while (!pilha.isEmpty()) {
            int verticeAtual = pilha.pop();
    
            if (!visitados.contains(verticeAtual)) {
                visitados.add(verticeAtual);
                System.out.print(verticeAtual + " ");
                    
                for (Aresta aresta : adjacencias.get(verticeAtual)) {
                        
                    if (!visitados.contains(aresta.destino)) {
                        pilha.push(aresta.destino);
                            
                    }
                }
            }
        }
    }

    public List<Integer> Depth_First_Search(int verticeInicial) {
        Set<Integer> visitados_no = new HashSet<>();
        Stack<Integer> pilha = new Stack<>();
        List<Integer> caminho_final = new ArrayList<>();
        
        pilha.push(verticeInicial);

        while (!pilha.isEmpty()) {
            int verticeAtual = pilha.pop();

            if (!visitados_no.contains(verticeAtual)) {
                visitados_no.add(verticeAtual);
                caminho_final.add(verticeAtual);

                for (Aresta aresta : adjacencias.get(verticeAtual)) {
                    if (!visitados_no.contains(aresta.getDestino())) {
                        pilha.push(aresta.getDestino());
                    }
                }
            }
        }

        return caminho_final;
    }

    public void removerVertice(int id) {
        adjacencias.values().forEach(list -> list.removeIf(aresta -> aresta.destino == id));
        adjacencias.remove(id);
        numVertices--;
    }

}
