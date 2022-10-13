import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class thiago_velasco_trab4_prog
{
    class Move implements Comparable<Move> // A classe Move foi criada para gerar variáveis de localização das peças que serão movimentadas numa jogada. 
    // Esta classe implementa a interface comparativa, para que as movimentações sorteadas funcionem com coerência.
    {
        int de; // Especifica a localização de onde a peça saiu.
        int vazio; // Especifica a localização da outra peça, a qual foi retirada e passou a ser um espaço vazio. 
        int para; // Especifica a localização para onde a peça foi direcionada.
        
        // Criando o costrutor para a classe Move:
        Move(int de, int vazio, int para)
        {
            // As variáveis da classe Move serão atribuídas aos valores das variáveis do construtor, pelo método this.
            this.de = de; 
            this.vazio = vazio;
            this.para = para;
        }
        // Printando as movimentações de cada jogada
        public String toString()
        {
            return("(" + this.de + "," + this.vazio + "," + this.para + ")\n");
        }
        @Override // Método comparativo da classe Move implementado, utilizando ordem crescente. 
        // É verificado se a localização da variável: de, é menor que a localização da mesma variável nas próximas tentaivas de jogadas. 
        // Caso seja, terá prioridade sobre as outras e esta será a escolhida da vez durante a resolução do jogo. 
        // Esta lógica, foi implementada a fim de que o algoritmo seja resolvido de forma mais rápida. 
        // Uma jogada pode ser iniciada a partir de pontos diferentes, porém se a localização de uma peça no tabuleiro, for menor que as outras, 
        // eliminam-se as possibilidades de outras jogadas.
        public int compareTo(Move m)
        {
            return Integer.valueOf(this.de).compareTo(Integer.valueOf(m.de));
        }

    }
    ArrayList<ArrayList<Integer>> tabela; // O vetor ArrayList permite criar um array dinâmico, neste caso, criei uma ArrayList de ArrayList para gerar uma matriz dinâmica, 
    // do tipo inteiro, pois o tabuleiro foi representado somente com os números: 0 e 1.
    ArrayList<Move> ListaMovimentos; // Array dinâmico de todos os movimentos feitos durante o game armazenado na classe Move.
    ArrayList<ArrayList<ArrayList<Integer>>> unsuccessfulTabela; // Memorização de jogadas fracassadas, para que o programa também seja resolvido de maneira mais rápida, 
    // baseado no método de programação dinâmica. Por ser um jogo complexo, o mesmo problema é resolvido mais de uma vez e, pode haver caminhos que levem ao fracasso. 
    // Então, essas jogadas são armazenadas deste modo.   
    
    thiago_velasco_trab4_prog(ArrayList<ArrayList<Integer>> tabela) // Construtor da classe Resta Um.
    {
        this.tabela = tabela;
        ListaMovimentos = new ArrayList<>();
        unsuccessfulTabela = new ArrayList<>();
    }
    // Função para printar cada jogada realizada.
    private void printOutput()
    {
        for(Move move : ListaMovimentos)
        {
            System.out.println(move); 
        }
    }
    // Função para printar o tabuleiro
    private void displayTabela()
    {
        for(ArrayList<Integer> linha: tabela)
        {
            for(int i : linha)
            {
                if(i == -1)
                {
                    System.out.print("- "); // Será impresso o sinal de subtração, nas posições dos quadrantes das extremidades, os quais não são utilizadas no jogo.
                }
                else
                {
                    System.out.print(Integer.toString(i) + " "); // Impressão do espaçamento associado às posições que fazem parte do jogo.        
                }
            }
            System.out.println();
        }
    }
    
    // Modificando o posicionamento após a jogada. A localização da variável: de, inicialmente com valor: 1 para a ser: 0.
    // A variável vazio, inicialmente com valor: 1, passa a valer: 0.
    // A varável para, incialmente com valor: 0, passa a valer: 1
    private void fazerMovimentacao(Move move)
    {
        // A lógica de chegar ao valor da linha é simplesmente dividir o valor do elemento por 7 e basear-se somente na parte inteira. 
        // Por exemplo, o número 10 está na linha 1 (10/7 = 1,42).
        // A lógica de chegar ao valor da colunha é basear-se no resto da divisão por 7. Ou seja, 10/7 tem como resto, o número 3, então estará na terceira coluna. 
        tabela.get(move.de/7).set(move.de %7, 0);
        tabela.get(move.vazio/7).set(move.vazio %7, 0);
        tabela.get(move.para/7).set(move.para %7, 1);
        ListaMovimentos.add(move); // Adicionando cada movimento no Array ListaMovimentos.
    }
    
    // Caso seja necessário desfazer uma jogada
    // É feito exatamente o oposto de quando uma jogada é feita, ou seja, os valores de 0 e 1, são invertidos.
    private void desfazerMovimentacao(Move move)
    {
        tabela.get(move.de/7).set(move.de %7, 1);
        tabela.get(move.vazio/7).set(move.vazio %7, 1);
        tabela.get(move.para/7).set(move.para %7, 0);
        ListaMovimentos.remove(ListaMovimentos.size()-1); // É removido o último movimento pelo número de indexação, através do método .size()-1.
    }
    // Método de todas as possibilidades de movimentações disponíveis para jogar.
    private ArrayList<Move> computarPossibilidades()
    {
        ArrayList<Move> possibilidades = new ArrayList<>(); // Para todo espaço vazio que será encontrado, é preciso checar se há duas peças em sequência para qualquer direção. 
        for(int i=0;i<tabela.size();i++)
        {
            for(int j=0;j<tabela.get(i).size();j++)
            {
                if(tabela.get(i).get(j) == 0)
                {
                    if(i-2 >=0) // Possibilidades de jogadas nos inícios das linhas, eliminando as chances de que as localizações com valor -1, sejam selecionadas, 
                    //já que não fazem parte do jogo.
                    {
                        if((tabela.get(i-2).get(j) == 1) && (tabela.get(i-1).get(j) == 1)) // Além disso, há necessidade de verificar se há duas peças em posições,
                        // de forma sequencial.
                        {
                            possibilidades.add(new Move((i-2)*7+j,(i-1)*7+j,i*7+j)); // Multiplicou-se por 7, para chegar ao valor das posição na matriz 7x7. 
                            //Por exemplo: i = 2, j = 3 - Posições: 3,10,17 
                        }
                    }
                    if(j-2 >=0) // Possibilidades de jogada no início da coluna, eliminando as chances de que as localizações com valor -1, sejam selecionadas,
                    // já que não fazem parte do jogo.
                    {
                       if((tabela.get(i).get(j-2) == 1) && (tabela.get(i).get(j-1) == 1)) // Além disso, há necessidade de verificar se há duas peças em posições,
                       // de forma sequencial.
                       {
                           possibilidades.add(new Move(i*7 + j-2, i*7 + j-1, i*7+j)); // multiplicou por 7, para chegar ao valor da posição na matriz 7x7
                       } 
                    }
                    if (i+2 <=6) // Possibilidades de jogadas nos finais das linhas, eliminando as chances de que as localizações com valor -1, sejam selecionadas,
                    // já que não fazem parte do jogo.
                    {
                        if((tabela.get(i+2).get(j) == 1) && (tabela.get(i+1).get(j)== 1)) // Além disso, há necessidade de verificar se há duas peças em posições,
                        // de forma sequencial.
                        {
                            possibilidades.add(new Move((i+2)*7+j,(i+1)*7+j,i*7+j)); 
                        }
                    }
                    if(j+2 <=6) // Possibilidades de jogadas nos finais das colunas, eliminando as chances de que as localizações com valor -1, sejam selecionadas,
                    // já que não fazem parte do jogo.
                    {
                        if((tabela.get(i).get(j+2) == 1) && (tabela.get(i).get(j+1) == 1)) // Além disso, há necessidade de verificar se há duas peças em posições,
                        // de forma sequencial.
                        {
                            possibilidades.add(new Move(i*7+j+2, i*7+j+1, i*7+j));
                        }
                    }
                }
            }
        }
        return possibilidades;
    }
    private int getCount() // Função que faz a contagem das peças com valor "1" do jogo.
    {
        int count = 0;
        for(int i=0;i<tabela.size();i++)
        {
            for(int j=0;j<tabela.get(i).size();j++)
            {
                if(tabela.get(i).get(j) == 1)
                {
                    count++;
                }
            }
        }
        return count;
    }
    private ArrayList<ArrayList<Integer>> deepCopy(ArrayList<ArrayList<Integer>> input) // Função com o objetivo de que cópias sejam geradas de versões antigas, 
    // para que sejam unificas novas versões posteriores. Tudo é armazenado, sem perdas. Foi criada para que a tabela de jogadas sem sucesso seja atualizada.
    {
        ArrayList<ArrayList<Integer>> novaTabela = new ArrayList<>();
        for(ArrayList<Integer> linha : input)
        {
            ArrayList<Integer> copiaLinha = new ArrayList<>();
            for (Integer i : linha)
            {
                copiaLinha.add(Integer.valueOf(i));
            }
            novaTabela.add(copiaLinha);
        }
        return novaTabela;  
    }

    public void tabelainicial() // Função para que seja impresso o tabuleiro inicial, utilizando a contagem de 32 peças.
    {
        if(getCount() == 32){ 
            System.out.printf("%s","Tabuleiro inicial: \n");
            displayTabela();
            System.out.printf("\n");
            System.out.printf("%s", "Loading...\n");
            printOutput();
            System.out.printf("\n");
        }
    }
    
    public boolean solve() // Checando se é preciso fazer mais jogadas para terminar o jogo, ou se finalizou o mesmo.
    {
        if(unsuccessfulTabela.contains(tabela))
        {
            return false;
        }
        if(getCount() == 1 && tabela.get(3).get(3) == 1) // Quando houver somente uma peça, a útlima restante, será impresso o tabuleiro final, 
        // com a peça restante final no meio do tabuleiro.
        {
            System.out.printf("%s", "Tabuleiro final: \n");
            displayTabela();
            System.out.printf("\n");
            System.out.printf("%s", "Jogadas: \n");
            printOutput();
            return true;
            
        }
        else
        {
            ArrayList<Move> moves = computarPossibilidades();
            Collections.sort(moves); // É sorteado a lista de todas as movimentações, para que o algoritmo seja resolvido mais rapidamente.
            for(Move move : moves)
            {
                fazerMovimentacao(move); // Pelo método recursivo, o algoritmo aceita ou não a movimentação de jogada proposta.
                if(solve())
                {
                    return true;
                }
                else
                {
                    desfazerMovimentacao(move); // Caso não seja, a jogada é desfeita.
                }
            }
        }
        if(!unsuccessfulTabela.contains(tabela))
        {
            unsuccessfulTabela.add(deepCopy(tabela)); // A função deepCopy é chamada para que haja as atualizações na tabela de jogadas sem sucesso.
        }
        return false;
    }
    public static void main(String args[]) // Função main para rodar o programa.
    {
        ArrayList<ArrayList<Integer>> tabela = new ArrayList<>();
        Integer[] linha1 = {-1,-1,1,1,1,-1,-1};
        Integer[] linha2 = {-1,-1,1,1,1,-1,-1};
        Integer[] linha3 = {1,1,1,1,1,1,1};
        Integer[] linha4 = {1,1,1,0,1,1,1};
        Integer[] linha5 = {1,1,1,1,1,1,1};
        Integer[] linha6 = {-1,-1,1,1,1,-1,-1};
        Integer[] linha7 = {-1,-1,1,1,1,-1,-1};
        
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha1)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha2)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha3)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha4)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha5)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha6)));
        tabela.add(new ArrayList<Integer>(Arrays.asList(linha7)));
        new thiago_velasco_trab4_prog(tabela).tabelainicial();
        new thiago_velasco_trab4_prog(tabela).solve();
    }
}