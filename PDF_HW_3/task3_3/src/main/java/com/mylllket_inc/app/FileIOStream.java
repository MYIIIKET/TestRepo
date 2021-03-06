package com.mylllket_inc.app;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIOStream {
    private FileInputStream inputStream;
    private String path;

    public FileIOStream(String path) throws FileNotFoundException {
        File f = new File(path);
        if (f.exists() && !f.isDirectory()) {
            this.path = path;
        } else throw new FileNotFoundException();
    }

    public String read() throws IOException {
        inputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "Cp1251");
        int data = inputStreamReader.read();

        StringBuilder result = new StringBuilder();
        while (data != -1) {
            result.append((char) data);
            data = inputStreamReader.read();
        }
        inputStream.close();
        return result.toString();
    }

    public boolean checkOnPicSequence(String text) {
        Pattern pattern = Pattern.compile("\\(Рис\\. \\d\\)[\\S\\s]*?\\(Рис\\. \\d\\)");
        Matcher matcher = pattern.matcher(text);

        matcher.find();
        while (matcher.find() != false) {
            if (matcher.group().charAt(6) == matcher.group().charAt(matcher.group().length() - 2)) {
                return true;
            }
        }
        return false;
    }

    public String[] getSentencesWithPics(String text) {
        StringBuilder result = new StringBuilder();
        Pattern patternSentence = Pattern.compile("[А-ЯA-Z].*[.!?]");
        Pattern patternPic = Pattern.compile("\\(Рис\\. \\d\\)?");
        Matcher matcherPic;

        Matcher matcherSentence = patternSentence.matcher(text);

        while (matcherSentence.find() != false) {
            matcherPic = patternPic.matcher(matcherSentence.group());
            if (matcherPic.find())
                result.append("Sentence: " + matcherSentence.group() + "\n");
        }
        return result.toString().split("\n");
    }

    public void printSentences(String[] text) {
        for (int i = 0; i < text.length; i++) {
            System.out.println(text[i]);
        }
    }
}
