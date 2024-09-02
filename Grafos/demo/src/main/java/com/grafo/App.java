package com.grafo;

import java.util.List;
import java.util.Scanner;

import com.grafo.DAO.ArestaDAO;
import com.grafo.DAO.GrafoDAO;
import com.grafo.model.Grafo;
import com.grafo.model.GrafoPlotter;

public class App {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        GrafoDAO grafoDAO = new GrafoDAO();
        ArestaDAO arestaDAO = new ArestaDAO(grafo);

        Scanner scanner = new Scanner(System.in);
        GrafoPlotter plotter = new GrafoPlotter();

    

        int opcao;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Inserir nó");
            System.out.println("2. Excluir nó");
            System.out.println("3. Inserir aresta");
            System.out.println("4. Excluir aresta");
            System.out.println("5. Plotar grafo");
            System.out.println("6. Plotar matriz de adjacência");
            System.out.println("7. Plotar caminho DFS");
            System.out.println("8. Verificar Grafo Convexo");
            System.out.println("9. Obter grau de vertice");
            System.out.println("------------------------");
            System.out.println("10. Usar grafo teste");
            System.out.println("------------------------");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a nova linha

            switch (opcao) {
                case 1:
                    System.out.println("ID do Vértice: ");
                    int id = scanner.nextInt();
                    grafoDAO.inserirVertice(id);
                    break;
                case 2:
                    System.out.println("ID do Vértice: ");
                    int id_re = scanner.nextInt();
                    grafo.removerVertice(id_re);
                    break;
                case 3:
                    System.out.println("Nó origem: ");
                    int origem = scanner.nextInt();
                    System.out.println("Nó destino: ");
                    int destino = scanner.nextInt();
                    System.out.println("Peso: ");
                    int peso = scanner.nextInt();
                    arestaDAO.inserirAresta(origem, destino, peso);
                    break;
                case 4:
                    System.out.println("Nó origem: ");
                    int origem_ex = scanner.nextInt();
                    System.out.println("Nó destino: ");
                    int destino_ex = scanner.nextInt();
                    arestaDAO.removerAresta(origem_ex, destino_ex);
                    break;
                case 5:
                    plotter.plotarGrafo(grafo);
                    break;
                case 6:
                    plotter.plotarMatrizAdjacencia(grafo);
                    break;
                case 7:
                    System.out.println("Nó inicial: ");
                    int no_inicio = scanner.nextInt();
                    System.out.println("1 - Forma numérica\n2 - Forma gráfica\nDigite: ");
                    int form = scanner.nextInt();
                    if (form == 1){
                        grafo.dfsTexto(no_inicio);
                    } else {
                        List<Integer> caminhoDFS = grafo.Depth_First_Search(no_inicio);
                        plotter.plotarCaminhamentoDFS(grafo, caminhoDFS);
                    }
                    break;
                case 8:
                    if (grafoDAO.VerificaGrafoConexo(grafo)){
                        System.out.println("Conexo");
                    } else {
                        System.out.println("Não conexo");
                    }
                    
                    break;
                case 9:
                    System.out.println("Verificar grau do nó: ");
                    int id_no = scanner.nextInt();
                    grafo.getGrau(id_no);
                    break;
                case 10:
                    grafoDAO.inserirVertice(1);
                    grafoDAO.inserirVertice(2);
                    grafoDAO.inserirVertice(3);
                    grafoDAO.inserirVertice(4);
                    grafoDAO.inserirVertice(5);
                    grafoDAO.inserirVertice(6);
                    grafoDAO.inserirVertice(7);
                    grafoDAO.inserirVertice(8);
                    grafoDAO.inserirVertice(9);
            
                    arestaDAO.inserirAresta(1, 2, 5);
                    arestaDAO.inserirAresta(1, 3, 10);
                    arestaDAO.inserirAresta(2, 4, 15);
                    arestaDAO.inserirAresta(2, 5, 20);
                    arestaDAO.inserirAresta(3, 6, 25);
                    arestaDAO.inserirAresta(3, 7, 30);
                    arestaDAO.inserirAresta(4, 8, 35);
                    arestaDAO.inserirAresta(5, 9, 40);
                    arestaDAO.inserirAresta(6, 8, 45);
                    arestaDAO.inserirAresta(7, 9, 50);
                    arestaDAO.inserirAresta(8, 9, 55);
                    arestaDAO.inserirAresta(9, 6, 7);
                    arestaDAO.inserirAresta(8, 2, 15);
                    arestaDAO.inserirAresta(4, 5, 3);
                    System.out.println("Grafo teste ativado!");
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcao != 0);
        scanner.close();

    }
}   
