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
            parser.parseInput(input);

        }

    }

    // le um arquivo e o transforma em string
    public String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);

    }

}
