/*
** Linguagens Formais e Automatos - GCC 122
** Turma: 10A
** Alunos: Pedro Victor de Sousa Lima
**         Gabriela Aparecida Santiago
**         Raquel Barbosa Romao
 */
package lfa;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public ArrayList<String> parseInput(String input) {

        // remove spaces and line breaker
        input = input.replaceAll("\n", "");
        input = input.replaceAll("\t", "");
        input = input.replaceAll(" ", "");

        // compilando padrao a ser encontrado no automato
        Pattern pattern = Pattern.compile("(\\{[a-zA-Z0-9,]+\\}|\\{\\(.*\\}\\}|[a-zA-Z0-9\\{\\}}]+)");
        Matcher matcher = pattern.matcher(input);

        // automato com seus componentes
        ArrayList<String> automato = new ArrayList<>();

        // pega os grupos que deram match
        while (matcher.find()) {
            automato.add(matcher.group());
        }

        return automato;
    }
}
