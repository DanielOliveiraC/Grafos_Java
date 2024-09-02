package com.grafo.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedPseudograph;

import com.grafo.DAO.GrafoDAO;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

public class GrafoPlotter {
    GrafoDAO grafoDAO = new GrafoDAO();
    public Graph<String, DefaultWeightedEdge> converterParaJGraphT(Grafo grafo) {
        // Cria um grafo ponderado não direcionado
        Graph<String, DefaultWeightedEdge> jGraphTGraph = new WeightedPseudograph<>(DefaultWeightedEdge.class);

        // Adiciona os vértices
        for (Integer id : grafo.getAdjacencias().keySet()) {
            jGraphTGraph.addVertex(String.valueOf(id));
        }

        // Adiciona as arestas com pesos
        for (Map.Entry<Integer, List<Aresta>> entry : grafo.getAdjacencias().entrySet()) {
            Integer origem = entry.getKey();
            for (Aresta aresta : entry.getValue()) {
                DefaultWeightedEdge edge = jGraphTGraph.addEdge(String.valueOf(origem), String.valueOf(aresta.getDestino()));
                if (edge != null) {
                    ((AbstractBaseGraph<String, DefaultWeightedEdge>) jGraphTGraph).setEdgeWeight(edge, aresta.getPeso());
                }
            }
        }

        return jGraphTGraph;
    }

    public void plotarGrafo(Grafo grafo) {
    // Converte o grafo para o tipo esperado pelo JGraphT
    Graph<String, DefaultWeightedEdge> jGraphTGraph = converterParaJGraphT(grafo);

    // Cria o adaptador usando o grafo convertido
    JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(jGraphTGraph);

    // Configura o layout do gráfico
    mxGraph graph = graphAdapter;
    graph.getModel().beginUpdate();
    try {
        // Itera sobre as arestas para configurar os rótulos com os pesos
        for (DefaultWeightedEdge edge : jGraphTGraph.edgeSet()) {
            String rotulo = String.valueOf(jGraphTGraph.getEdgeWeight(edge));
            mxCell cell = (mxCell) graphAdapter.getEdgeToCellMap().get(edge);
            cell.setValue(rotulo);  // Define o peso como rótulo da aresta
        }

        // Configurações de estilo
        mxStylesheet stylesheet = graph.getStylesheet();
        Map<String, Object> edgeStyle = new HashMap<>();
        edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW); 
        edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE); 
        edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000"); 
        edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, "2"); 
        edgeStyle.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
        edgeStyle.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE);

        stylesheet.getDefaultEdgeStyle().putAll(edgeStyle);
        graph.setEdgeLabelsMovable(false);  // Impede que o rótulo se mova separadamente da aresta
        graph.setCellsEditable(false);  // Impede edição de células

    } finally {
        graph.getModel().endUpdate();
    }

    // Define o layout do grafo como circular
    mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
    layout.execute(graphAdapter.getDefaultParent());

    // Cria o componente gráfico
    mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
    JFrame frame = new JFrame("Visualização do Grafo");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.getContentPane().add(graphComponent);
    frame.pack();
    frame.setVisible(true);
}


    public void plotarMatrizAdjacencia(Grafo grafo) {
        Graph<String, DefaultWeightedEdge> jGraphTGraph = converterParaJGraphT(grafo);
    
        // Obtenha a lista de vértices
        List<String> vertices = jGraphTGraph.vertexSet().stream().toList();
    
        // Cria a tabela modelo para a matriz de adjacência
        DefaultTableModel model = new DefaultTableModel(vertices.size(), vertices.size() + 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede edição das células
            }
        };
    
        // Define os nomes das colunas
        String[] columnNames = new String[vertices.size() + 1];
        columnNames[0] = ""; // Coluna vazia para os rótulos das linhas
        for (int i = 0; i < vertices.size(); i++) {
            columnNames[i + 1] = vertices.get(i); // Ajuste no índice
        }
        model.setColumnIdentifiers(columnNames);
    
        // Preenche a matriz com os pesos das arestas ou 0 se não houver aresta
        for (int i = 0; i < vertices.size(); i++) {
            String origem = vertices.get(i);
            model.setValueAt(origem, i, 0); // Ajuste no índice
            for (int j = 0; j < vertices.size(); j++) {
                String destino = vertices.get(j);
                DefaultWeightedEdge edge = jGraphTGraph.getEdge(origem, destino);
                if (edge != null) {
                    model.setValueAt(jGraphTGraph.getEdgeWeight(edge), i, j + 1); // j + 1 para compensar a coluna de rótulo
                } else {
                    model.setValueAt(0, i, j + 1); // j + 1 para compensar a coluna de rótulo
                }
            }
        }
    
        // Cria a tabela visual com a matriz de adjacência
        JTable table = new JTable(model);
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);
    
        // Define o renderer customizado para a tabela
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
    
        // Adiciona a tabela a um JScrollPane para caso a tabela seja grande
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
    
        // Configura o frame para exibir a matriz de adjacência
        JFrame frame = new JFrame("Matriz de Adjacência");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(scrollPane);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.pack();
        frame.setVisible(true);
    }
    

    public void plotarCaminhamentoDFS(Grafo grafo, List<Integer> caminhoDFS) {
        // Converte o grafo para o tipo esperado pelo JGraphT
        Graph<String, DefaultWeightedEdge> jGraphTGraph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    
        // Adiciona os vértices ao grafo JGraphT
        for (Integer vertice : grafo.getAdjacencias().keySet()) {
            jGraphTGraph.addVertex(vertice.toString());
        }
    
        // Adiciona as arestas ao grafo JGraphT
        for (Integer vertice : grafo.getAdjacencias().keySet()) {
            for (Aresta aresta : grafo.getAdjacencias().get(vertice)) {
                DefaultWeightedEdge edge = jGraphTGraph.addEdge(vertice.toString(), String.valueOf(aresta.getDestino()));
                if (edge != null) {
                    ((AbstractBaseGraph<String, DefaultWeightedEdge>) jGraphTGraph).setEdgeWeight(edge, aresta.getPeso());
                }
            }
        }
    
        // Cria o adaptador usando o grafo convertido
        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(jGraphTGraph);
    
        
        Map<String, Object> edgeStyle = new HashMap<>();
        mxGraph graph = graphAdapter;
        graph.getModel().beginUpdate();
        try {
            graph.setEdgeLabelsMovable(false);
            graph.setCellsEditable(false);
            edgeStyle.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW); 
            edgeStyle.put(mxConstants.STYLE_ENDARROW, mxConstants.NONE); 
            edgeStyle.put(mxConstants.STYLE_STROKECOLOR, "#000000"); 
            edgeStyle.put(mxConstants.STYLE_STROKEWIDTH, "2"); 
            edgeStyle.put(mxConstants.STYLE_LABEL_POSITION, mxConstants.ALIGN_CENTER);
            edgeStyle.put(mxConstants.STYLE_VERTICAL_LABEL_POSITION, mxConstants.ALIGN_MIDDLE);
    
            // Destaca as arestas que foram percorridas no DFS
            for (int i = 0; i < caminhoDFS.size() - 1; i++) {
                String origem = caminhoDFS.get(i).toString();
                String destino = caminhoDFS.get(i + 1).toString();
                DefaultWeightedEdge edge = jGraphTGraph.getEdge(origem, destino);
                if (edge != null) {
                    mxCell cell = (mxCell) graphAdapter.getEdgeToCellMap().get(edge);
                    if (cell != null) {
                        cell.setStyle("strokeColor=green;strokeWidth=2");
                    }
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }
    
        // Define o layout do grafo como circular
        mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
    
        // Cria o componente gráfico
        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);
        JFrame frame = new JFrame("Caminhamento em Profundidade (DFS)");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(graphComponent);
        frame.pack();
        frame.setVisible(true);
    }
    
    
    
}
