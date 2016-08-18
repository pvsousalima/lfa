/*
** Linguagens Formais e Automatos - GCC 122
** Turma: 10A
** Alunos: Pedro Victor de Sousa Lima
**         Gabriela Aparecida Santiago
**         Raquel Barbosa Romao
 */
package lfa;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class LFA {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        if (args.length < 2) {
            System.out.println("Parametros invalidos");
        } else {

            // novo objeto trabalho LFA
            LFA lfa = new LFA();

            // le o arquivo contendo o automato
            String input = lfa.readFile(args[0], Charset.forName("UTF-8"));

            // parser da entrada de dados
            InputParser parser = new InputParser();
            ArrayList<String> automato = parser.parseInput(input);

            // verifica o tamanho do automato
            if (automato.size() != 5) {
                System.out.println("Automato invalido");
                return;
            }
            
            PrintWriter writer = new PrintWriter(args[1], "UTF-8");
            
            // parser para o automato (pegar transicoes e estados)
            AutomataParser ap = new AutomataParser();
            String[] estados = ap.getStates(automato);

            // monta o automato e extrai os dados e faz as conversoes  
            ap.mount(estados, automato.get(0), automato.get(1), automato.get(3), writer);
        }

    }

    // le um arquivo e o transforma em string
    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding); 
    }

}
