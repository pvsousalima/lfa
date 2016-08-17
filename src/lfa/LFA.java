/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfa;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author pvsousalima
 */
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
            } else {
//                for (int i = 0; i < automato.size(); i++) {
//                    System.out.println(automato.get(i));
//                }
            }

            // parser para o automato (pegar transicoes e estados)
            AutomataParser ap = new AutomataParser();
            String[] estados = ap.getStates(automato);
            ap.mount(estados, automato.get(0), automato.get(1));
        }

    }

    // le um arquivo e o transforma em string
    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding); // retorna a string
    }

}
