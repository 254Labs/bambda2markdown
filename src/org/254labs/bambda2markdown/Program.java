package org.labs254.bambda2markdown;

import java.io.IOException;

import static org.labs254.bambda2markdown.BambdaMetadataParser.*;

public class Program {
    private Program() {}

    public static void main(String[] args) {
        String inputFile = "resources/examples/ExampleBambda.java";
        String outputFile = "output/markdown/example_bambda.md";

        try {
            System.out.println("Reading file: " + inputFile);
            String bambdaFile = readBambdaFile(inputFile);
            System.out.println("Parsing metadata from the file.");
            String annotations = parseMetadata(bambdaFile);
            System.out.println("Writing metadata to markdown file: " + outputFile);
            writeMetadataToMarkdownFile(annotations, outputFile);
            System.out.println("Appending Bambda code to markdown file:" + outputFile);
            appendJavaCodeToMarkdownFile(bambdaFile, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
