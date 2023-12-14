package org.labs254.bambda2markdown;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class BambdaMetadataParser {

    private static final String BAMBDA_MARKER = "@bambda";
    private static final int BAMBDA_MARKER_LENGTH = BAMBDA_MARKER.length();
    private static final Set<String> ALLOWED_KEYS = new HashSet<>();

    static {
        ALLOWED_KEYS.add("type");
        ALLOWED_KEYS.add("author");
        ALLOWED_KEYS.add("source");
        ALLOWED_KEYS.add("createdOn");
        ALLOWED_KEYS.add("useCase");
    }

    private BambdaMetadataParser() {
    }

    static String readBambdaFile(String filePath) throws IOException {
        StringBuilder javaCode = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                javaCode.append(line).append("\n");
            }
        }

        return javaCode.toString();
    }

    static String parseMetadata(String javaCode) {
        if (!javaCode.contains(BAMBDA_MARKER)) {
            throw new MetadataParserException("Parsing metadata failed because marker " + BAMBDA_MARKER + " not found.");
        }

        String metaMarkers = javaCode.substring(0, javaCode.indexOf(BAMBDA_MARKER));

        String[] metaMarkerLines = metaMarkers.split("\\n");
        int metaMarkerLinesCount = metaMarkerLines.length;

        String[] metaMarkerKeys = new String[metaMarkerLinesCount];
        String[] metaMarkerValues = new String[metaMarkerLinesCount];

        for (int i = 0; i < metaMarkerLinesCount; i++) {
            metaMarkerKeys[i] = metaMarkerLines[i].split(":")[0].replaceFirst("@", "");
            if (!ALLOWED_KEYS.contains(metaMarkerKeys[i])) {
                throw new MetadataParserException("Parsing metadata failed because of unidentified key " + metaMarkerKeys[i]);
            }
            metaMarkerValues[i] = metaMarkerLines[i].split(":", 2)[1];
            metaMarkerLines[i] = metaMarkerKeys[i] + ":" + metaMarkerValues[i] + "<br/>";
        }
        return String.join("\n", metaMarkerLines);
    }

    static void writeMetadataToMarkdownFile(String metadata, String outputFile) throws IOException {
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(metadata + "\n");
        }
    }

    static void appendJavaCodeToMarkdownFile(String javaCode, String outputFile) throws IOException {
        try (FileWriter writer = new FileWriter(outputFile, true)) {
            writer.write("\n```java\n");
            writer.write(javaCode.substring(javaCode.indexOf(BAMBDA_MARKER) + BAMBDA_MARKER_LENGTH));
            writer.write("\n```");
        }
    }
}
