package com.grafo.DAO;

import com.grafo.model.Aresta;
import com.grafo.model.Grafo;
import java.util.ArrayList;

public class ArestaDAO {
    private Grafo grafo; 

    // Construtor que recebe o grafo como argumento
    public ArestaDAO(Grafo grafo) {
        this.grafo = grafo;
    }

    public void inserirAresta(int origem, int destino, int peso) {
        // Verifica se o vértice de origem existe, caso contrário, inicializa
        if (!grafo.getAdjacencias().containsKey(origem)) {
            grafo.getAdjacencias().put(origem, new ArrayList<>());
            grafo.setNumVertices(grafo.getNumVertices() + 1);
        }
        // Verifica se o vértice de destino existe, caso contrário, inicializa
        if (!grafo.getAdjacencias().containsKey(destino)) {
            grafo.getAdjacencias().put(destino, new ArrayList<>());
            grafo.setNumVertices(grafo.getNumVertices() + 1);
        }

        // Adiciona a aresta
        grafo.getAdjacencias().get(origem).add(new Aresta(destino, peso));
    }

    public void removerAresta(int origem, int destino) {
        if (grafo.getAdjacencias().containsKey(origem)) {
            grafo.getAdjacencias().get(origem).removeIf(aresta -> aresta.getDestino() == destino);
        }
        if (grafo.getAdjacencias().containsKey(destino)) {
            grafo.getAdjacencias().get(destino).removeIf(aresta -> aresta.getDestino() == origem);
        }
    }
}
