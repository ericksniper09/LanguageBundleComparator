package org.defoix;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
	    File language23_1 = new File("C:\\Users\\eod\\Desktop\\New folder (2)\\fr\\23.1.0.properties");
	    File language23_2 = new File("C:\\Users\\eod\\Desktop\\New folder (2)\\fr\\23.2.properties");
	    File languagePR_ISSUE = new File("C:\\Users\\eod\\Desktop\\New folder (2)\\fr\\ISSUE.properties");

        Map<String, Map<GitVersion, String>> languageBundle = new HashMap<>();

        extractLanguageBunle(languageBundle, language23_1, GitVersion.V23_1);
        extractLanguageBunle(languageBundle, language23_2, GitVersion.V23_2);
        extractLanguageBunle(languageBundle, languagePR_ISSUE, GitVersion.PR_ISSUE);

        Map<String, Map<GitVersion, String>> languageBundleChanged = new HashMap<>();
        languageBundle.forEach((k, v) -> {
            if (new HashSet<>(v.values()).size() != 1) {
                languageBundleChanged.put(k, v);
            }
        });
    }

    private static void extractLanguageBunle(Map<String, Map<GitVersion, String>> languageBundle, File file, GitVersion gitVersion) {
        try (Scanner sc = new Scanner(file)){
            while (sc.hasNext()) {
                String line = sc.nextLine();
                if (!line.split("=")[1].trim().isEmpty()) {
                    Map<GitVersion, String>  gitVersionStringMap = new HashMap<>();
                    String[] splitted = line.split("=");
                    if (languageBundle.containsKey(splitted[0])) {
                        gitVersionStringMap = languageBundle.get(splitted[0]);
                        gitVersionStringMap.put(gitVersion, splitted[1]);
                    } else {
                        gitVersionStringMap.put(gitVersion, splitted[1]);
                        languageBundle.put(splitted[0], gitVersionStringMap);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
