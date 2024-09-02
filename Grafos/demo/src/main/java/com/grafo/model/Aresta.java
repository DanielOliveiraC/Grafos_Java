package com.grafo.model;

/**
 * Aresta
 */
public class Aresta {
    int destino;
    int peso;

    public Aresta(int destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    
    // Getters e Setters

    /** 
     * @return int
     */
    public int getDestino() {
        return destino;
    }
    
    /** 
     * @param destino
     */
    public void setDestino(int destino) {
        this.destino = destino;
    }
    
    /** 
     * @return int
     */
    public int getPeso() {
        return peso;
    }
    
    /** 
     * @param peso
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Destino: " + destino + ", Peso: " + peso;
    }
}