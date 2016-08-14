/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfa;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author pvsousalima
 */
public class InputParser {

    // remove a ultima ocorrencia de alguma substring
    public String replaceLast(String string, String substring, String replacement) {
        int index = string.lastIndexOf(substring);
        if (index == -1) {
            return string;
        }
        return string.substring(0, index) + replacement + string.substring(index + substring.length());
    }

    // parse na entrada de dados
    public ArrayList<String>  parseInput(String input) {

        // remove spaces and line breaker
        input = input.replace("\n", "");
        input = input.replace(" ", "");

        System.out.println("Automato apos processamento:");
        System.out.println(input);

        System.out.println("");

        // compilando padrao a ser encontrado no automato
        Pattern pattern = Pattern.compile("(\\{[a-zA-Z0-9,]+\\}|\\{\\(.*\\}\\}|[a-zA-Z0-9\\{\\}}]+)");
        Matcher matcher = pattern.matcher(input);

        System.out.println("Componentes separados do automato:");

        // automato com seus componentes
        ArrayList<String> automato = new ArrayList<>();

        // pega os grupos que deram match
        while (matcher.find()) {
            automato.add(matcher.group());
        }

        return automato;
    }
}
